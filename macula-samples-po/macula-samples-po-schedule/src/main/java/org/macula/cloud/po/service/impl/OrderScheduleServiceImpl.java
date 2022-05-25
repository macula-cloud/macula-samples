package org.macula.cloud.po.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.core.annotation.RedisLock;
import org.macula.cloud.core.annotation.RedisLock.LockFailedPolicy;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;
import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.feign.GbssOpenApiClient;
import org.macula.cloud.po.repository.PoCheckMasterRepository;
import org.macula.cloud.po.repository.PoProcessMasterRepository;
import org.macula.cloud.po.repository.SapDailyUplPoRepository;
import org.macula.cloud.po.repository.SapDailyUplPoV2Repository;
import org.macula.cloud.po.service.OrderCheckingService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.service.OrderScheduleService;
import org.macula.cloud.po.type.PoProcessSource;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OrderScheduleServiceImpl implements OrderScheduleService {

	private OrderCheckingService orderCheckingService;
	private OrderProcessingService orderProcessingService;
	private PoCheckMasterRepository poCheckMasterRepository;
	private PoProcessMasterRepository poProcessMasterRepository;
	private SapDailyUplPoRepository sapDailyUplPoRepository;
	private SapDailyUplPoV2Repository sapDailyUplPoV2Repository;
	private GbssOpenApiClient gbssClient;
	private OMSConfig config;
	private static ExecutorService EXECUTORS = Executors.newScheduledThreadPool(20);

	/**
	 * 获取GBSS订单上传列表
	 */
	@Override
	@RedisLock(prefix = "oms:job", value = "'oms-order-schedule-upl-po-pull'", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Long doPullGBSSUploadSchedule(Long startId, Pageable pageable) {
		Long latestId = poProcessMasterRepository.getMaxGbssUploadId(PoProcessSource.PO_SOURCE_UPL);
		if (latestId == null) {
			latestId = startId != null ? startId : config.getMinUploadId();
		}
		int pageSize = pageable.getPageSize();
		int pageIndex = pageable.getPageNumber();
		long count = 0;
		Page<SapDailyUplPo> uploadData;
		do {
			uploadData = null;
			ExecuteResponse<Page<SapDailyUplPo>> uploadDataResponse = gbssClient.getGbssUploadDataList(PoProcessSource.PO_SOURCE_UPL, latestId + 1,
					pageIndex, pageSize);
			if (uploadDataResponse != null && uploadDataResponse.isSuccess()) {
				uploadData = uploadDataResponse.getReturnObject();
				for (SapDailyUplPo upload : uploadData) {
					String poNo = upload.getPoNo();
					// 是否已经存在，如果存在，表示需要重推
					boolean existed = sapDailyUplPoRepository.existsByPoNo(poNo);
					sapDailyUplPoRepository.saveAndFlush(upload);
					StatusStartRequest request = null;
					if (existed) {
						request = StatusStartRequest.builder().poNo(poNo).status(PoProcessStatus.PO_HAND_REQUEST)
								.statusTime(SystemUtils.getCurrentTime()).poSource(PoProcessSource.PO_SOURCE_UPL).refSourceId(upload.getId())
								.comments("收到UPL重推订单！").build();
					} else {
						request = StatusStartRequest.builder().poNo(poNo).status(PoProcessStatus.PO_UPL_REQUEST)
								.statusTime(SystemUtils.getCurrentTime()).poSource(PoProcessSource.PO_SOURCE_UPL).refSourceId(upload.getId())
								.comments("收到UPL订单！").build();
					}
					Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
					if (log.isInfoEnabled()) {
						log.info("StatusStartRequest {} handleResult {}", request, handleResult);
					}
					if (handleResult != null && handleResult.booleanValue()) {
						EXECUTORS.execute(() -> {
							CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
						});
					}
				}
				pageIndex++;
				count += uploadData.getNumberOfElements();
			}
		} while (uploadData != null && uploadData.hasNext());
		return count;
	}

	/**
	 * 获取GBSS订单上传列表2
	 */
	@Override
	@RedisLock(prefix = "oms:job", value = "'oms-order-schedule-upl2-po-pull'", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Long doPullGBSSUpload2Schedule(Long startId, Pageable pageable) {
		Long latestId = poProcessMasterRepository.getMaxGbssUploadId(PoProcessSource.PO_SOURCE_UPL2);
		if (latestId == null) {
			latestId = config.getMinUpload2Id();
		}
		int pageSize = 100;
		int pageIndex = 0;
		long count = 0;
		Page<SapDailyUplPoV2> uploadData;
		do {
			uploadData = null;
			ExecuteResponse<Page<SapDailyUplPoV2>> uploadDataResponse = gbssClient.getGbssUpload2DataList(PoProcessSource.PO_SOURCE_UPL2,
					latestId + 1, pageIndex, pageSize);
			if (uploadDataResponse != null && uploadDataResponse.isSuccess()) {
				uploadData = uploadDataResponse.getReturnObject();
				for (SapDailyUplPoV2 upload : uploadData) {
					String poNo = upload.getPoNo();
					// 是否已经存在，如果存在，表示需要重推
					boolean existed = sapDailyUplPoRepository.existsByPoNo(poNo);
					StatusStartRequest request = null;
					if (existed) {
						request = StatusStartRequest.builder().poNo(poNo).status(PoProcessStatus.PO_HAND_REQUEST)
								.statusTime(SystemUtils.getCurrentTime()).poSource(PoProcessSource.PO_SOURCE_UPL2).refSourceId(upload.getId())
								.comments("收到UPL重推订单！").build();
					} else {
						request = StatusStartRequest.builder().poNo(poNo).status(PoProcessStatus.PO_UPL_REQUEST)
								.statusTime(SystemUtils.getCurrentTime()).poSource(PoProcessSource.PO_SOURCE_UPL2).refSourceId(upload.getId())
								.comments("收到UPL订单！").build();
					}
					Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
					if (log.isInfoEnabled()) {
						log.info("StatusStartRequest {} handleResult {}", request, handleResult);
					}
					if (handleResult != null && handleResult.booleanValue()) {
						EXECUTORS.execute(() -> {
							CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
						});
					}
					sapDailyUplPoV2Repository.saveAndFlush(upload);
				}
				pageIndex++;
				count += uploadData.getNumberOfElements();
			}
		} while (uploadData != null && uploadData.hasNext());
		return count;
	}

	/**
	 * 处理上传SAP失败的订单任务
	 */
	@Override
	@RedisLock(prefix = "oms:job", value = "'oms-order-schedule-upload'", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Long doUploadFailedOrderSchedule() {
		List<String> orders = poProcessMasterRepository.findNeedRetryOrders(PoProcessStatus.getRetryGroup());
		for (String poNo : orders) {
			// 更新日志记录状态
			StatusStartRequest request = StatusStartRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_RETRY_REQUEST).comments("重推SAP订单！").build();
			// 发送执行业务事件请求 
			Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
			if (log.isInfoEnabled()) {
				log.info("StatusStartRequest {} handleResult {}", request, handleResult);
			}
			if (handleResult != null && handleResult.booleanValue()) {
				EXECUTORS.execute(() -> {
					CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
				});
			}
		}
		return (long) orders.size();
	}

	/**
	 * 处理定时对账任务
	 */
	@Override
	@RedisLock(prefix = "oms:job", value = "'oms-order-schedule-check'", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Long doCheckBillingSchedule(Date beginTime, Integer poCheckRange, Integer poValidateRange) {
		if (beginTime == null) {
			beginTime = SystemUtils.getCurrentTime();
		}
		if (poCheckRange == null) {
			poCheckRange = config.getPoCheckRange();
		}
		if (poValidateRange == null) {
			poValidateRange = config.getPoValidateRange();
		}

		long count = 0;
		while (true) {
			Date startTime = orderCheckingService.getCurrentCheckingStartTime(beginTime);
			Date endTime = orderCheckingService.getCurrentCheckingEndTime(startTime, poCheckRange);
			if (log.isInfoEnabled()) {
				log.info(String.format("%tF %tT ~ %tF %tT 对账时间", startTime, startTime, endTime, endTime));
			}

			if (!orderCheckingService.validateCheckingTime(startTime, endTime, poValidateRange)) {
				log.info(" X 对账时间不满足设定 > " + poValidateRange + "s");
				break;
			}

			// 调用微服务获取GBSS业务平台对账的数据
			List<PoCheckDetail> gbssDetails = orderCheckingService.getGBSSBillingData(startTime, endTime);
			// 查询本地需要对账的数据
			List<PoCheckDetail> omsDetails = orderCheckingService.findLocaleBillingData(startTime, endTime);
			// 合并两者对账的数据
			List<PoCheckDetail> mixupDetails = orderCheckingService.matchUpCheckDetails(gbssDetails, omsDetails);
			// 更新对账明细
			orderCheckingService.updateCheckingResult(startTime, endTime, mixupDetails);
			count += mixupDetails.size();
		}
		return count;
	}

	/**
	 * 定时任务:OMS对账失败数据进行重新对账
	 */
	@Override
	@RedisLock(prefix = "oms:job", value = "'oms-order-schedule-recheck'", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Long doCheckBillingAgainSchedule() {
		List<PoCheckMaster> poCheckMasterList = poCheckMasterRepository.findAllBySynStatus();
		if (poCheckMasterList != null && !poCheckMasterList.isEmpty()) {
			for (PoCheckMaster poCheckMaster : poCheckMasterList) {
				Date startTime = poCheckMaster.getStartTime();
				Date endTime = poCheckMaster.getEndTime();
				try {
					if (log.isInfoEnabled()) {
						log.info(String.format("%tF %tT ~ %tF %tT 重新对账时间", startTime, startTime, endTime, endTime));
					}
					// 查询本地需要对账的数据
					List<PoCheckDetail> omsDetails = orderCheckingService.findLocaleBillingData(startTime, endTime);
					// 调用微服务获取GBSS业务平台对账的数据
					List<PoCheckDetail> gbssDetails = orderCheckingService.getGBSSBillingData(startTime, endTime);

					/**
					 * 此处操作：要把之前对账过得数据中,在页面进行过忽略对账操作的数据移除,不再参与对账(还有多个遗漏和疑问,需要进一步讨论……)
					 */
					// 获得本地对账数据中,非忽略对账的数据,参与后面的对账
					List<PoCheckDetail> newestPoCheckDetail = omsDetails.stream().filter(c -> !c.getOmsPoStatus().equals("2"))
							.collect(Collectors.toList());

					// 提取出本地对账数据中的所有忽略数据
					List<PoCheckDetail> huLue = omsDetails.stream().filter(c -> c.getOmsPoStatus().equals("2")).collect(Collectors.toList());
					// 提取所有忽略数据中的单号集合
					List<String> listPoNo = huLue.stream().map(PoCheckDetail::getOmsPoNo).collect(Collectors.toList());

					// 移除GBSS业务平台中,同样不需要参与对账的数据
					for (PoCheckDetail poCheckDetail : gbssDetails) {
						for (String poNo : listPoNo) {
							if (poCheckDetail.getGbssPoNo().equals(poNo)) {
								// 移除GBSS对账数据中,不需要参与对账的被忽略的数据
								gbssDetails.remove(poCheckDetail);
							}
						}
					}

					// 合并两者对账的数据
					List<PoCheckDetail> mixupDetails = orderCheckingService.matchUpCheckDetails(gbssDetails, newestPoCheckDetail);
					// 更新对账明细
					orderCheckingService.updateCheckingResult(startTime, endTime, mixupDetails);
					// 更新对账主表的对账次数(重新对账一次就+1)
					poCheckMaster.setCheckNumber(poCheckMaster.getCheckNumber() + 1);
					poCheckMasterRepository.save(poCheckMaster);
				} catch (Exception e) {
					throw new OMSException(String.format("=====当前重新对账数据失败: %tF %tT ~ %tF %tT ", startTime, startTime, endTime, endTime));
				}
			}
		}
		return (long) poCheckMasterList.size();
	}
}

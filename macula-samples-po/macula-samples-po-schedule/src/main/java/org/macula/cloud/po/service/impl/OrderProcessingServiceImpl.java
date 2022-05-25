package org.macula.cloud.po.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.core.annotation.RedisLock;
import org.macula.cloud.core.annotation.RedisLock.LockFailedPolicy;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.domain.PoAddrDetail;
import org.macula.cloud.po.domain.PoHeader;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.domain.PoProcessDetail;
import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.po.domain.PosAccTranDetail;
import org.macula.cloud.po.event.OrderProcessFailedEvent;
import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.event.OrderProcessSuccessEvent;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.feign.GbssOpenApiClient;
import org.macula.cloud.po.repository.MkpDlpPoRelateRepository;
import org.macula.cloud.po.repository.PoAddrDetailRepository;
import org.macula.cloud.po.repository.PoDetailDiscountRepository;
import org.macula.cloud.po.repository.PoDetailRepository;
import org.macula.cloud.po.repository.PoHeaderRepository;
import org.macula.cloud.po.repository.PoInvoiceInfoRepository;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.repository.PoPaymentDetailRepository;
import org.macula.cloud.po.repository.PoProcessDetailRepository;
import org.macula.cloud.po.repository.PoProcessMasterRepository;
import org.macula.cloud.po.repository.PosAccTranDetailRepository;
import org.macula.cloud.po.sap.bapi.DealerOrderZrebBapi;
import org.macula.cloud.po.service.OrderPackageService;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.type.PoProcessSource;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.type.PoStatusConstants;
import org.macula.cloud.po.vo.DealerOrderVo;
import org.macula.cloud.po.vo.OrderUploadMessage;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.macula.cloud.po.vo.PoStatusChange;
import org.macula.cloud.po.vo.StatusChangeRequest;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.macula.cloud.sap.SAPExecution;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OrderProcessingServiceImpl implements OrderProcessingService {

	private PoProcessMasterRepository poProcessMasterRepository;
	private PoProcessDetailRepository poProcessDetailRepository;
	private PoMasterRepository poMasterRepository;
	private PoDetailRepository poDetailRepository;
	private PoDetailDiscountRepository poDetailDiscountRepository;
	private PosAccTranDetailRepository posAccTranDetailRepository;
	private PoPaymentDetailRepository poPaymentDetailRepository;
	private MkpDlpPoRelateRepository mkpDlpPoRelateRepository;
	private PoInvoiceInfoRepository poInvoiceInfoRepository;
	private PoAddrDetailRepository poAddrDetailRepository;
	private PoHeaderRepository poHeaderRepository;
	private OrderPackageService orderPackageService;
	private OrderProcessControlService processControlService;
	private GbssOpenApiClient gbssOpenApiClient;
	private SAPExecution jcoExecution;
	private Producer producer;
	private OMSConfig config;

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 0.记录处理请求
	 */
	@Override
	@RedisLock(prefix = "oms:processing", value = "#poNo", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Boolean handleStartRequest(String poNo, StatusStartRequest request) {
		if (log.isInfoEnabled()) {
			log.info(" === HandleProcessRequest -> {} ", request);
		}
		PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);

		if (poProcessMaster == null) {
			poProcessMaster = new PoProcessMaster();
			poProcessMaster.setPoNo(poNo);
		}
		String currentStatus = poProcessMaster == null ? null : poProcessMaster.getStatus();
		String requestStatus = request.getStatus();
		boolean shouldChange = PoProcessStatus.shouldStartRequest(currentStatus, requestStatus);
		if (shouldChange) {
			poProcessMaster.setStatus(request.getStatus());
			poProcessMaster.setStatusTime(request.getStatusTime());
			poProcessMaster.setComments(request.getComments());
		}
		String poSource = poProcessMaster.getPoSource();
		if (poSource == null || PoProcessSource.shouldUpdate(request.getPoSource())) {
			poProcessMaster.setPoSource(request.getPoSource());
		}
		if (request.getRefSourceId() != null) {
			poProcessMaster.setRefSourceId(request.getRefSourceId());
		}
		if (request.getRefSourceNo() != null) {
			poProcessMaster.setRefSourceNo(request.getRefSourceNo());
		}
		if (request.getPoProcessCode() != null) {
			poProcessMaster.setPoProcessCode(request.getPoProcessCode());
		}
		poProcessMasterRepository.saveAndFlush(poProcessMaster);
		PoProcessDetail detail = new PoProcessDetail();
		detail.setPoNo(poNo);
		detail.setProcState(request.getStatus());
		detail.setProcTime(request.getStatusTime());
		detail.setComments(request.getComments());
		poProcessDetailRepository.save(detail);
		return shouldChange;
	}

	/**
	 * 1.记录状态变化
	 */
	@Override
	@RedisLock(prefix = "oms:processing", value = "#poNo", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Boolean handleStatusChange(String poNo, StatusChangeRequest request) {
		if (log.isInfoEnabled()) {
			log.info(" === HandleStatusChange -> {} ", request.toString());
		}
		PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);
		if (poProcessMaster == null) {
			return false;
		}
		if (request.getPoProcessCode() != null) {
			poProcessMaster.setPoProcessCode(request.getPoProcessCode());
		}
		poProcessMaster.setStatus(request.getStatus());
		poProcessMaster.setStatusTime(request.getStatusTime());
		if (PoProcessStatus.hasError(request.getStatus()) && !PoProcessStatus.hasPause(request.getStatus())) {
			poProcessMaster.setErrorTimes(poProcessMaster.getErrorTimes() + 1);
		}
		String comments = request.getComments();
		if (!PoProcessStatus.hasPause(request.getStatus())) {
			poProcessMaster.setComments(comments);
		}
		if (request.getResultId() != null) {
			poProcessMaster.setSendMessageId(request.getResultId());
			comments += request.getResultId();
		}
		poProcessMaster = poProcessMasterRepository.saveAndFlush(poProcessMaster);
		PoProcessDetail detail = new PoProcessDetail();
		detail.setPoNo(poNo);
		detail.setProcState(request.getStatus());
		detail.setProcTime(request.getStatusTime());
		detail.setComments(comments);
		poProcessDetailRepository.save(detail);
		return true;
	}

	/**
	 * 从本地数据库获取并组装PoResultDetailVo
	 */
	@Override
	public PoResultDetailVo loadLocalPoResultDetailVo(String poNo) {
		if (log.isDebugEnabled()) {
			log.debug("1. loadLocalPoResultDetailVo:{}, ", poNo);
		}
		PoResultDetailVo vo = new PoResultDetailVo();
		// 1：订货信息主表
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		vo.setPoMaster(poMaster);
		// 2:保存订单明细PoDetail
		vo.setPoDetails(poDetailRepository.findByPoNoOrderByLineNoAsc(poNo));
		// 3:订单折扣明细
		vo.setPoDetailDiscounts(poDetailDiscountRepository.findByPoNo(poNo));
		// 4：自营店交易流水表/服务中心
		List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository.findByPosTranDateAndPosStoreNoAndRefDocNo(poMaster.getPoDate(),
				poMaster.getPoStoreNo(), poMaster.getPoNo());
		vo.setPosAccTranDetails(posAccTranDetailList);
		// 5：订货支付信息
		vo.setPoPaymentDetails(poPaymentDetailRepository.findByPoNo(poNo));
		// 6:纷享绘订单
		vo.setMkpDlpPoRelate(mkpDlpPoRelateRepository.findByPoNo(poNo));
		// 7:企业发票
		vo.setPoInvoiceInfo(poInvoiceInfoRepository.findByPoAppNo(vo.getPoMaster().getPoAppNo()));
		// 8:订货配送地址
		vo.setPoAddrDetail(poAddrDetailRepository.findByPoNo(poNo));
		// 9:解析SAP需要的其他字段数据
		vo.setPoHeader(poHeaderRepository.findByPoNo(poNo));
		return vo;
	}

	/**
	 * 2.从业务平台获取PoResultDetailVo
	 */
	@Override
	public PoResultDetailVo loadGbssPoResultDetailVo(String poNo) {
		if (log.isDebugEnabled()) {
			log.debug("2. loadGbssPoResultDetailVo:{}, ", poNo);
		}
		ExecuteResponse<PoResultDetailVo> gbssOrderResultDetail = gbssOpenApiClient.getGbssOrderResultDetail(poNo);
		if (gbssOrderResultDetail == null || !gbssOrderResultDetail.isSuccess()) {
			throw new OMSException("gbssOpenApiClient.getGbssOrderResultDetail error: "
					+ (gbssOrderResultDetail == null ? null : gbssOrderResultDetail.getExceptionMessage()));
		}
		PoResultDetailVo returnObject = gbssOrderResultDetail.getReturnObject();
		return returnObject;
	}

	/**
	 * 3.保存从业务平台获取到的PoResultDetailVo到本地数据库
	 */
	@Override
	public PoResultDetailVo persistancePoResultDetailVo(PoResultDetailVo vo) {
		if (log.isDebugEnabled()) {
			log.debug("3. persistancePoResultDetailVo:{}, ", vo.getPoMaster().getPoNo());
		}
		// 1：订货信息主表
		if (vo.getPoMaster() != null) {
			poMasterRepository.save(vo.getPoMaster());
		}
		// 2:保存订单明细PoDetail
		if (vo.getPoDetails() != null) {
			poDetailRepository.saveAll(vo.getPoDetails());
		}
		// 3:订单折扣明细
		if (vo.getPoDetailDiscounts() != null) {
			poDetailDiscountRepository.saveAll(vo.getPoDetailDiscounts());
		}
		// 4：自营店交易流水表/服务中心
		if (vo.getPosAccTranDetails() != null) {
			posAccTranDetailRepository.saveAll(vo.getPosAccTranDetails());
		}
		// 5：订货支付信息
		if (vo.getPoPaymentDetails() != null) {
			poPaymentDetailRepository.saveAll(vo.getPoPaymentDetails());
		}
		// 6:纷享绘订单
		if (vo.getMkpDlpPoRelate() != null) {
			mkpDlpPoRelateRepository.save(vo.getMkpDlpPoRelate());
		}
		// 7:企业发票
		if (vo.getPoInvoiceInfo() != null) {
			poInvoiceInfoRepository.save(vo.getPoInvoiceInfo());
		}
		// 8:订货配送地址
		if (vo.getPoAddrDetail() != null) {
			poAddrDetailRepository.save(vo.getPoAddrDetail());
		}
		// 9:解析SAP需要的其他字段数据
		if (vo.getPoHeader() != null) {
			PoMaster m = vo.getPoMaster();
			PoHeader poHeader = vo.getPoHeader();
			m.cloneId(poHeader);
			poHeader.setPoNo(m.getPoNo());
			poHeader.setOrderDealerNo(m.getOrderDealerNo());
			poHeader.setCustomerDealerNo(m.getOrderCustomerNo());
			poHeader.setDeliveryDealerNo(vo.getPoAddrDetail().getDeliveryDealerNo());
			poHeader.setLastUpdatedBy(vo.getPoMaster().getLastUpdatedBy());
			poHeader.setLastUpdatedTime(vo.getPoMaster().getLastUpdatedTime());
			poHeaderRepository.save(poHeader);
		}
		return vo;
	}

	/**
	 * 4.将PoResultDetailVo转化为SAP上传格式
	 */
	@Override
	public DealerOrderZrebBapi translatePoResultDetailVo2Bapi(String poNo, PoResultDetailVo vo) {
		if (log.isDebugEnabled()) {
			log.debug("4. translatePoResultDetailVo2Bapi:{}, ", vo.getPoMaster().getPoNo());
		}
		DealerOrderVo dealerOrderVo = orderPackageService.packageSapOrderVo(poNo, vo);
		DealerOrderZrebBapi bapi = new DealerOrderZrebBapi();
		bapi.setPOrderHeader(dealerOrderVo.getPOrderHeader());
		bapi.setTOrderItemsList(dealerOrderVo.gettOrderItemsList());
		bapi.setTConditionList(dealerOrderVo.gettConditionList());
		return bapi;
	}

	/**
	 *5. 执行订单上传SAP接口
	 */
	@Override
	public DealerOrderZrebBapi executeUploadBapi(String poNo, DealerOrderZrebBapi bapi) {
		if (log.isDebugEnabled()) {
			log.debug("5. executeUploadBapi:{}, ", poNo);
		}
		String prefix = StringUtils.trim(config.getSapMock());
		if (StringUtils.isNotBlank(prefix) && StringUtils.length(prefix) == 1) {
			if (log.isErrorEnabled()) {
				log.error(" Mock SAP PoNo:{}, ", prefix + bapi.getPOrderHeader().getPurchNoC());
			}
			bapi.getPOrderHeader().setPurchNoC(prefix + bapi.getPOrderHeader().getPurchNoC());
		}
		return jcoExecution.execute(poNo, bapi);
	}

	/**
	 * 6.回写本地SAP订单号
	 */
	@Override
	public boolean executeLocaleResultCallback(String poNo, String sapPostingDocNo) {
		if (log.isDebugEnabled()) {
			log.debug("6. sendBapiResultCallback:{}, ", poNo);
		}
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		poMaster.setSapPostingDate(SystemUtils.getCurrentTime());
		poMaster.setSapPostingDocNo(sapPostingDocNo);
		poMaster.setSapPostingFlag("Y");
		poMasterRepository.save(poMaster);

		PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);
		if (poProcessMaster != null) {
			poProcessMaster.setSalesDocument(sapPostingDocNo);
			poProcessMasterRepository.save(poProcessMaster);
		}

		return true;
	}

	/**
	 * 7. 回写GBSS系统SAP订单号（发送MQ消息）
	 */
	@Override
	public String executeGbssResultCallback(String poNo, OrderUploadMessage uploadResult) {
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		if (poMaster == null) {
			throw new OMSException(String.format("PoMaster %s is null  !", poNo));
		}

		String poProcessCode = poMaster.getPoProcessCode();
		if (StringUtils.isNotBlank(poMaster.getSapPostingDocNo())) {
			uploadResult.updateSuccess(poMaster.getSapPostingDocNo(), poMaster.getSapPostingDate());
		}
		byte[] messageContent;
		try {
			messageContent = objectMapper.writeValueAsBytes(uploadResult);
		} catch (JsonProcessingException e) {
			throw new OMSException("OrderUploadMessage translate to json error!", e);
		}
		Message message = new Message(config.getCallbackTopic(), poProcessCode, poNo, messageContent);
		SendResult result = producer.send(message);
		String messageId = result.getMessageId();
		if (StringUtils.isNotEmpty(messageId)) {
			PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);
			if (poProcessMaster != null) {
				poProcessMaster.setSendMessageId(messageId);
				poProcessMasterRepository.save(poProcessMaster);
			}
		}
		return messageId;
	}

	/**
	 * 8. GBSS变更状态更新到OMS
	 */
	@Override
	@ServiceInvokeProxy(key = "args[0]", description = "'更新订单状态'", source = "'MQ'", sourceMessage = "args[1]", target = "'OMS'", targetMethod = "'handlePoStatusChange'")
	public PoStatusChange handlePoStatusChange(String poNo, PoStatusChange poStatusChange) {
		//PoMaster poMaster = poMasterRepository.findByPoNo(poStatusChange.getPoNo());
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		String sapPostingDocNo = null;
		// 1:单号对应的数据存在OMS,修改对应的状态
		if (poMaster != null) {
			poStatusChange.setOldStatus(poMaster.getPoStatus());
			poMaster.setPoStatus(poStatusChange.getPoStatus());
			poMasterRepository.save(poMaster);
			sapPostingDocNo = poMaster.getSapPostingDocNo();

			// 看当前单号是否有辅单
			PoMaster byRefSelectedNo = poMasterRepository.findByRefSelectedNo(poNo);
			// 如果有辅单,就同步更新它的状态
			if (byRefSelectedNo != null) {
				byRefSelectedNo.setPoStatus(poStatusChange.getPoStatus());
				poMasterRepository.save(byRefSelectedNo);
				// 更新辅单在流程记录表中的最新记录状态
				String comments = String.format("更新订单状态 %s -> %s ，%s", poStatusChange.getOldStatus(), poStatusChange.getPoStatus(), poStatusChange);
				String processStatus = PoProcessStatus.CHANGED;
				handleStatusChange(byRefSelectedNo.getPoNo(), StatusChangeRequest.builder().poNo(byRefSelectedNo.getPoNo()).status(processStatus)
						.statusTime(SystemUtils.getCurrentTime()).comments(comments).build());
				if (sapPostingDocNo == null) {
					CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(byRefSelectedNo.getPoNo()));
				}
			}
		} else { // 2:如果没有这个订单的数据,表明它是一个主单的冲销单,去GBSS同步过来
			// 根据当前的poNo,去GBSS业务平台获取当前冲销单的所有对应的数据并保存到OMS
			PoResultDetailVo poResultDetailVo = loadGbssPoResultDetailVo(poNo);
			// 执行保存到本系统操作
			persistancePoResultDetailVo(poResultDetailVo);
		}
		// 同步修改po_addr_detail的地址信息
		changePoAddrDetail(poNo);
		// 更新主单在流程记录表中的最新记录状态
		String comments = String.format("更新订单状态 %s -> %s ，%s", poStatusChange.getOldStatus(), poStatusChange.getPoStatus(), poStatusChange);
		String processStatus = PoProcessStatus.CHANGED;
		handleStatusChange(poNo,
				StatusChangeRequest.builder().poNo(poNo).status(processStatus).statusTime(SystemUtils.getCurrentTime()).comments(comments).build());
		if (sapPostingDocNo == null) {
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
		}
		return poStatusChange;
	}

	/**
	 * GBSS变更状态更新到OMS后,再同步修改可能的地址变更
	 */
	@Override
	public void changePoAddrDetail(String poNo) {
		// 根据当前的poNo,去GBSS业务平台获取修改状态后最新的地址数据
		PoResultDetailVo poResultDetailVo = loadGbssPoResultDetailVo(poNo);
		// 解析出最新的地址信息
		PoAddrDetail poAddrDetail = poResultDetailVo.getPoAddrDetail();
		if (poAddrDetail != null) {
			// 查询出OMS对应的地址信息
			PoAddrDetail byPoNo = poAddrDetailRepository.findByPoNo(poNo);
			if (byPoNo != null) {
				poAddrDetail.setId(byPoNo.getId());
				// 将OMS中换为最新的地址信息
				poAddrDetailRepository.save(poAddrDetail);
			}
		}
	}

	/**
	 * 获取PoProcessMaster日志
	 */
	@Override
	public PoProcessMaster getProcessMaster(String poNo) {
		return poProcessMasterRepository.findByPoNo(poNo);
	}

	@Override
	@RedisLock(prefix = "oms:processing", value = "#poNo", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public void doProcessingLogic(String poNo) {
		if (log.isInfoEnabled()) {
			log.info("Processing doProcessingLogic: {}", poNo);
		}

		try {
			doProcessInternal(poNo);
		} catch (Exception ex) {
			String message = String.format("订单[ %s ] 处理出错%s ,  %s", poNo, ex.getMessage(), ExceptionUtils.getRootCauseMessage(ex));
			log.error(message);
			StatusChangeRequest statusRequest = StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.ERROR).comments(StringUtils.substring(message, 0, 255)).build();
			handleStatusChange(poNo, statusRequest);
			// 发出失败信息
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessFailedEvent(poNo));
		}
	}

	// 根据poNo从业务平台回去po详情
	protected void doProcessInternal(String poNo) throws OMSException {
		PoResultDetailVo vo = null;
		String stepErrorMessage = null;
		String poProcessCode = null;
		String omsPostingDocNo = null;
		try {
			PoProcessMaster poProcessMaster = getProcessMaster(poNo);
			if (poProcessMaster == null || !PoProcessStatus.hasRequest(poProcessMaster.getStatus())) {
				log.error("Po {} needn't process！", poNo);
				return;
			}
			stepErrorMessage = "从GBSS获取数据失败！";
			vo = loadGbssPoResultDetailVo(poNo);
			if (vo != null && vo.getPoMaster() != null) {
				poProcessCode = vo.getPoMaster().getPoProcessCode();
			}
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_GET_GBSS_DATA).poProcessCode(poProcessCode).comments("从GBSS获取数据成功！").build());
			stepErrorMessage = "保存订单信息失败！";
			// 保存从GBSS获取回来的数据到OMS
			persistancePoResultDetailVo(vo);
			// 更新流程日志记录表
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_SAVE_LOCAL).comments(" 保存订单信息成功！").build());

			stepErrorMessage = "获取本地订单信息失败！";
			vo = (vo == null) ? loadLocalPoResultDetailVo(poNo) : vo;
			if (vo == null || vo.getPoMaster() == null || !Arrays.asList(PoStatusConstants.PO_STATUS_NEW).contains(vo.getPoMaster().getPoStatus())) {
				handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.CHANGED).comments("非新增状态，不需要上传！").build());
				return;
			}

			// 需要开启上传SAP控制
			if (!processControlService.shouldSAPUploadProcessing()) {
				handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.ERROR).comments("暂停SAP上传处理，标记为失败！").build());
				log.error("== SHOULD NOT DO SAP UPLOAD  ==");
				log.error("Po {} 按设定不执行上传SAP", poNo);
				return;
			}

			stepErrorMessage = "转换SAP格式失败！";
			DealerOrderZrebBapi bapi = translatePoResultDetailVo2Bapi(poNo, vo);
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_DATA_PACKAGE).comments("转换SAP格式成功！").build());
			stepErrorMessage = "执行SAP接口失败！";
			bapi = executeUploadBapi(poNo, bapi);
			if (bapi.getPoHeader() != null) {
				omsPostingDocNo = bapi.getPoHeader().getSalesdocument();
			}

			if (StringUtils.isEmpty(omsPostingDocNo)) {
				stepErrorMessage = bapi.getErrorMessage();
				stepErrorMessage = StringUtils.mid(stepErrorMessage, 0, 255);
				throw new OMSException(stepErrorMessage);
			}
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_UPLOAD_SAP).comments("执行SAP接口成功！").build());

			if (!StringUtils.isEmpty(omsPostingDocNo)) {
				stepErrorMessage = "回写本地SAP单号失败！";
				executeLocaleResultCallback(poNo, omsPostingDocNo);
				handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.PO_SAP_REWRITEN).comments("回写本地SAP单号" + omsPostingDocNo + "成功！").build());
			}

			stepErrorMessage = "发送更新SAP单号到MQ失败！";
			String messageId = executeGbssResultCallback(poNo, new OrderUploadMessage(poNo));
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime()).status(PoProcessStatus.SUCCESS)
					.resultId(messageId).comments("发送更新SAP单号" + omsPostingDocNo + "到MQ成功！").build());
			// 发出成功信息
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessSuccessEvent(poNo));
		} catch (Exception ex) {
			String errorMessage = stepErrorMessage;
			String messageId = executeGbssResultCallback(poNo,
					new OrderUploadMessage(poNo).updateFailed(StringUtils.substring(errorMessage, 0, 255)));
			if (!(ex instanceof OMSException)) {
				errorMessage += " -> " + ExceptionUtils.getRootCauseMessage(ex);
			}
			throw new OMSException(errorMessage + " \r\nMQ-ID:" + messageId, ex);
		}
	}
}

package org.macula.cloud.po.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.feign.GbssOpenApiClient;
import org.macula.cloud.po.repository.PoCheckDetailRepository;
import org.macula.cloud.po.repository.PoCheckMasterRepository;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.service.OrderCheckingService;
import org.macula.cloud.po.type.PoStatusConstants;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderCheckingServiceImpl implements OrderCheckingService {

	private PoMasterRepository poMasterRepository;
	private PoCheckMasterRepository poCheckMasterRepository;
	private PoCheckDetailRepository poCheckDetailRepository;
	private GbssOpenApiClient gbssOpenApiClient;

	/**
	 * 获取当前对账开始时间
	 */
	@Override
	public Date getCurrentCheckingStartTime(Date beginTime) {
		PoCheckMaster checkMaster = poCheckMasterRepository.findFirstByOrderByEndTimeDesc();
		if (checkMaster != null && checkMaster.getEndTime() != null && (beginTime == null || checkMaster.getEndTime().before(beginTime))) {
			return checkMaster.getEndTime();
		}
		return beginTime;
	}

	/**
	 * 获取当前对账结束时间
	 */
	@Override
	public Date getCurrentCheckingEndTime(Date startTime, int poCheckRange) {
		return DateUtils.addSeconds(startTime, poCheckRange);
	}

	/**
	 * 校验对账区间是否有效
	 */
	@Override
	public boolean validateCheckingTime(Date startTime, Date endTime, int poValidateRange) {
		Date currentTime = SystemUtils.getCurrentTime();
		return endTime.after(startTime) && currentTime.after(DateUtils.addSeconds(endTime, poValidateRange));
	}

	/**
	 * 从GBSS获取对账数据(根据时间范围内的销售单据输入时间查询GBSS数据)
	 * startTime:开始:销售单据输入时间
	 * endTime:结束:销售单据输入时间
	 */
	@Override
	public List<PoCheckDetail> getGBSSBillingData(Date start, Date end) {
		String startTime = DateFormatUtils.format(start, "yyyy-MM-dd HH:mm:ss");
		String endTime = DateFormatUtils.format(end, "yyyy-MM-dd HH:mm:ss");
		ExecuteResponse<List<PoCheckDetail>> gbssBillingData = gbssOpenApiClient.getGbssBillingData(startTime, endTime);
		if (gbssBillingData == null || !gbssBillingData.isSuccess()) {
			throw new OMSException(
					"gbssOpenApiClient.getGbssBillingData error: " + (gbssBillingData == null ? null : gbssBillingData.getExceptionMessage()));
		}
		List<PoCheckDetail> list = gbssBillingData.getReturnObject();
		return list;
	}

	/**
	 * 从本地获取对账数据(根据时间范围内的销售单据输入时间查询本地数据)
	 * startTime:开始:销售单据输入时间
	 * endTime:结束:销售单据输入时间
	 */
	@Override
	public List<PoCheckDetail> findLocaleBillingData(Date start, Date end) {
		List<PoCheckDetail> result = new ArrayList<PoCheckDetail>();
		List<PoMaster> masters = poMasterRepository.findCheckingBillings(start, end);
		for (PoMaster poMaster : masters) {
			PoCheckDetail detail = new PoCheckDetail();
			detail.setOmsPoNo(poMaster.getPoNo());
			detail.setOmsPoAmount(poMaster.getPaymentTotalAmt());
			detail.setOmsPoStatus(poMaster.getPoStatus());
			detail.setOmsSapDocNo(poMaster.getSapPostingDocNo());
			result.add(detail);
		}
		return result;
	}

	/**
	 * 合并对账信息
	 * @param gbssDetails：GBSS业务平台对账数据
	 * @param localeDetails：OMS对账数据
	 * @return
	 */
	@Override
	public List<PoCheckDetail> matchUpCheckDetails(List<PoCheckDetail> gbssDetails, List<PoCheckDetail> localeDetails) {
		List<PoCheckDetail> mixupDetails = new ArrayList<PoCheckDetail>();
		Map<String, PoCheckDetail> localeMap = new HashMap<String, PoCheckDetail>();
		for (PoCheckDetail poCheckDetail : localeDetails) {
			localeMap.put(poCheckDetail.getOmsPoNo(), poCheckDetail);
		}

		for (PoCheckDetail gbssDetail : gbssDetails) {
			PoCheckDetail detail = new PoCheckDetail();
			detail.setGbssPoNo(gbssDetail.getGbssPoNo());
			detail.setGbssPoAmount(gbssDetail.getGbssPoAmount());
			detail.setGbssPoStatus(gbssDetail.getGbssPoStatus());
			detail.setGbssSapDocNo(gbssDetail.getGbssSapDocNo());
			String poNo = gbssDetail.getGbssPoNo();
			PoCheckDetail omsDetail = localeMap.remove(poNo);
			if (omsDetail != null) {
				detail.setOmsPoNo(omsDetail.getOmsPoNo());
				detail.setOmsPoAmount(omsDetail.getOmsPoAmount());
				detail.setOmsPoStatus(omsDetail.getOmsPoStatus());
				detail.setOmsSapDocNo(omsDetail.getOmsSapDocNo());
			}
			boolean matched = checkDetailMatched(detail);
			detail.setSynStatus(matched ? "1" : "0");
			mixupDetails.add(detail);
		}
		if (!localeMap.isEmpty()) {
			for (PoCheckDetail omsDetail : localeMap.values()) {
				PoCheckDetail detail = new PoCheckDetail();
				detail.setOmsPoNo(omsDetail.getOmsPoNo());
				detail.setOmsPoAmount(omsDetail.getOmsPoAmount());
				detail.setOmsPoStatus(omsDetail.getOmsPoStatus());
				detail.setOmsSapDocNo(omsDetail.getOmsSapDocNo());
				detail.setSynStatus("0");
				mixupDetails.add(detail);
			}
		}
		return mixupDetails;
	}

	/**
	 * 创建对账信息记录(汇总对账明细到对账主表当中去)
	 * @param startTime
	 * @param endTime
	 * @param matchUpDetails
	 * @return
	 */
	@Override
	public PoCheckMaster updateCheckingResult(Date startTime, Date endTime, List<PoCheckDetail> matchUpDetails) {
		PoCheckMaster checkMaster = poCheckMasterRepository.findByStartTimeAndEndTime(startTime, endTime);
		if (checkMaster == null) {
			checkMaster = new PoCheckMaster();
			checkMaster.setStartTime(startTime);
			checkMaster.setEndTime(endTime);
			checkMaster.setCheckNumber(0);
			checkMaster = poCheckMasterRepository.save(checkMaster);
		}
		Long checkMasterId = checkMaster.getId();

		BigDecimal gbssTotalAmount = BigDecimal.ZERO; // GBSS支付金额总和
		BigDecimal omsTotalAmount = BigDecimal.ZERO; // GOMS支付金额总和
		long gbssPoCount = 0; // GBSS订单总量
		long omsPoCount = 0; // OMS订单总量
		boolean globalMatched = true;

		List<String> unMatchedList = new ArrayList<String>();

		for (PoCheckDetail poCheckDetail : matchUpDetails) {
			poCheckDetail.setCheckMasterId(checkMasterId);
			if (poCheckDetail.getGbssPoNo() != null) {
				gbssPoCount++; // GBSS订单总量
			}
			if (poCheckDetail.getOmsPoNo() != null) {
				omsPoCount++; // OMS订单总量
			}
			if (poCheckDetail.getGbssPoAmount() != null) {
				gbssTotalAmount = gbssTotalAmount.add(poCheckDetail.getGbssPoAmount()); // GBSS支付金额总和
			}
			if (poCheckDetail.getOmsPoAmount() != null) {
				omsTotalAmount = omsTotalAmount.add(poCheckDetail.getOmsPoAmount()); // GOMS支付金额总和
			}
			if (!StringUtils.equals(poCheckDetail.getSynStatus(), "1")) {
				unMatchedList.add(poCheckDetail.getGbssPoNo() != null ? poCheckDetail.getGbssPoNo() : poCheckDetail.getOmsPoNo());
			}
			globalMatched = globalMatched && StringUtils.equals(poCheckDetail.getSynStatus(), "1");
		}
		// 先删除原来的对账明细
		poCheckDetailRepository.deleteCheckMasterId(checkMaster.getId());
		// 再保存最新的对账明细
		poCheckDetailRepository.saveAll(matchUpDetails);
		//globalMatched = globalMatched && gbssTotalAmount.equals(omsTotalAmount) && gbssPoCount == omsPoCount;
		globalMatched = globalMatched && gbssTotalAmount.compareTo(omsTotalAmount) == 0 && gbssPoCount == omsPoCount;
		checkMaster.setGbssPoAmount(gbssTotalAmount);
		checkMaster.setGbssPoCount(gbssPoCount);
		checkMaster.setOmsPoAmount(omsTotalAmount);
		checkMaster.setOmsPoCount(omsPoCount);
		checkMaster.setSynStatus(globalMatched ? "1" : "0");
		return poCheckMasterRepository.save(checkMaster);
	}

	/**
	 * 更新对账状态
	 * @param poNo
	 */
	@Override
	public void updateCheckDetailAgain(String poNo) {
		// 根据poNo查询对账明细
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		PoCheckDetail poCheckDetail = poCheckDetailRepository.findByGbssPoNo(poNo);
		if (poCheckDetail != null) {
			poCheckDetail.setOmsPoNo(poMaster.getPoNo()); // 重新拉取的单号
			poCheckDetail.setOmsPoStatus(poMaster.getPoStatus()); // 重新拉取的状态
			poCheckDetail.setOmsPoAmount(poMaster.getPaymentTotalAmt()); // 重新拉取的支付金额
			poCheckDetail.setSynStatus(checkDetailMatched(poCheckDetail) ? "1" : "0"); // 对账状态设置为1:一致
			poCheckDetailRepository.save(poCheckDetail); // 更新对账明细数据

			// 查询同一对账主表ID下所有对账明细集合(因为当前的明细只是其中一条,可能其他数据存在对账不一致的,根据情况要更改对账主表的对账状态)
			List<PoCheckDetail> list = poCheckDetailRepository.findByCheckMasterId(poCheckDetail.getCheckMasterId());
			// 获取到对账明细集合中对账状态的集合
			List<String> synStatusList = list.stream().map(PoCheckDetail::getSynStatus).collect(Collectors.toList());
			// 判断对账状态集合中是否有为0:不一致的数据
			if (!synStatusList.contains("0")) { // 如果所有对账状态都是非0,说明当前的对账数据都是一致的,更改对账主表的对账状态
				// 查询对应的对账主表数据
				PoCheckMaster poCheckMaster = poCheckMasterRepository.findById(poCheckDetail.getCheckMasterId()).orElse(new PoCheckMaster());
				poCheckMaster.setSynStatus("1"); // 修改状态为1:对账一致
				poCheckMasterRepository.save(poCheckMaster); // 更新对账主表数据
			}
		}
	}

	/**
	 * 方法说明：admin前端页面调用,执行对账忽略操作
	 * @param gbssPoNo:对账主明细表的gbssPoNo,必传参数
	 * @param checkMasterId:对账主表的ID
	 * @return
	 */
	@Override
	public PoCheckDetail successByHand(String gbssPoNo, Long checkMasterId) {
		/*		// 1:更改poMaster状态
				PoMaster poMaster = poMasterRepository.findByPoNo(gbssPoNo);
				poMaster.setPoStatus(PoProcessStatus.SUCCESS_BY_HAND); // 状态改为81
				poMasterRepository.save(poMaster);*/

		// 2:更改PoCheckDetail对账明细表的状态
		PoCheckDetail poCheckDetail = poCheckDetailRepository.findByGbssPoNo(gbssPoNo);
		poCheckDetail.setSynStatus("2"); // 对账状态设置为2:手动忽略对账
		poCheckDetailRepository.save(poCheckDetail);

		// 3:查询所有跟当前对账主表ID相关的对账明细
		List<PoCheckDetail> byCheckMasterId = poCheckDetailRepository.findByCheckMasterId(checkMasterId);
		// 获取所有对账明细中的转态值集合
		List<String> synStatusList = byCheckMasterId.stream().map(PoCheckDetail::getSynStatus).collect(Collectors.toList());

		// 4:更改对账主表的忽略总单量和忽略总金额
		PoCheckMaster poCheckMaster = poCheckMasterRepository.findById(checkMasterId).get();
		poCheckMaster.setIgnorePoCount(poCheckMaster.getIgnorePoCount() + 1L); // 忽略订单量加1
		BigDecimal ignorePoAmount = poCheckMaster.getIgnorePoAmount();
		poCheckMaster.setIgnorePoAmount(ignorePoAmount.add(poCheckDetail.getGbssPoAmount())); // 忽略总金额

		// 5:判断所有对账明细中是否还有对账不一致的数据,如果此次忽略后,所有明细都为对账一致,则对账主表要更改状态为一致
		if (!synStatusList.contains("0")) {
			poCheckMaster.setSynStatus("1"); // 修改状态为1:对账一致
			poCheckMasterRepository.save(poCheckMaster);
		} else {
			poCheckMasterRepository.save(poCheckMaster);
		}
		return poCheckDetail;
	}

	private boolean checkDetailMatched(PoCheckDetail omsDetail) {
		boolean matched = omsDetail.getOmsPoAmount().compareTo(omsDetail.getGbssPoAmount()) == 0 // 判断总金额是否一致
				&& StringUtils.equals(omsDetail.getOmsPoStatus(), omsDetail.getGbssPoStatus()) // 判断状态是否一致
				&& StringUtils.equals(omsDetail.getOmsSapDocNo(), omsDetail.getGbssSapDocNo()) // 判断SPA返回单号是否一致
		;
		if (matched && !PoStatusConstants.PO_STATUS_DELETE.equals(omsDetail.getOmsPoStatus())) {
			matched = matched && StringUtils.isNotBlank(omsDetail.getOmsSapDocNo()) && StringUtils.isNotBlank(omsDetail.getGbssSapDocNo()); // 判断SAP返回单号是否为null
		}
		return matched;
	}

}

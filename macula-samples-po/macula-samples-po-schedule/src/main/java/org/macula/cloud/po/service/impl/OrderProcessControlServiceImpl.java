package org.macula.cloud.po.service.impl;

import java.util.Calendar;

import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.domain.SysParamInfo;
import org.macula.cloud.po.repository.SysParamInfoRepository;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderProcessControlServiceImpl implements OrderProcessControlService {

	private SysParamInfoRepository sysParamInfoRepository;
	private ConversionService conversionService;

	@Override
	public boolean shouldGlobalProcessing() {
		return getSysParamValue("GLOBAL_PROCESS_FLAG");
	}

	@Override
	public boolean shouldMQPaidProcessing() {
		return shouldGlobalProcessing() && getSysParamValue("MQ_PAID_PROCESS_FLAG");
	}

	@Override
	public boolean shouldMQRefundProcessing() {
		return shouldGlobalProcessing() && getSysParamValue("MQ_REFUND_PROCESS_FLAG");
	}

	@Override
	public boolean shouldMQUpdateProcessing() {
		return shouldGlobalProcessing() && getSysParamValue("MQ_OPER_PROCESS_FLAG");
	}

	@Override
	public boolean shouldSAPUploadProcessing() {
		return shouldGlobalProcessing() && !isDoingDailyEndNow() && getSysParamValue("SAP_UPLOAD_PROCESS_FLAG");
	}

	@Override
	public boolean shouldPullingUploadPoSchedule() {
		return shouldGlobalProcessing() && true;
	}

	@Override
	public boolean shouldRetryingPoUploadSchedule() {
		return shouldGlobalProcessing() && getSysParamValue("UPL_SCHEDULE_PROCESS_FLAG");
	}

	@Override
	public boolean shouldCheckingPoSchedule() {
		return shouldGlobalProcessing() && getSysParamValue("CHECKING_SCHEDULE_PROCESS_FLAG");
	}

	@Override
	public boolean shouldRecheckingPoSchedule() {
		return shouldGlobalProcessing() && getSysParamValue("RECHECKING_SCHEDULE_PROCESS_FLAG");
	}

	protected boolean getSysParamValue(String code) {
		SysParamInfo paramInfo = sysParamInfoRepository.findByParaCode(code);
		if (paramInfo != null && paramInfo.getParaValue() != null) {
			return conversionService.convert(paramInfo.getParaValue(), Boolean.class);
		}
		return true;
	}

	@Override
	public boolean isDoingDailyEndNow() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(SystemUtils.getCurrentInstant().toEpochMilli());
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour < 4;
	}

}

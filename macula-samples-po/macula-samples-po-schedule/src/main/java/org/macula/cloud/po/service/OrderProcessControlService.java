package org.macula.cloud.po.service;

public interface OrderProcessControlService {

	/**
	 * 是否全局支持处理，如果为false，则所有与上传相关的动作均不处理
	 */
	boolean shouldGlobalProcessing();

	/**
	 * 是否处理监听到的MQ Paid消息
	 */
	boolean shouldMQPaidProcessing();

	/**
	 * 是否处理监听到的MQ Refund消息
	 */
	boolean shouldMQRefundProcessing();

	/**
	 * 是否处理监听到的MQ Opera消息
	 * @return
	 */
	boolean shouldMQUpdateProcessing();

	/**
	 * 是否处理SAP订单上传消息
	 */
	boolean shouldSAPUploadProcessing();

	/**
	 * 是否同步SAP_DAILY_UPL_PO任务
	 */
	boolean shouldPullingUploadPoSchedule();

	/**
	 * 是否执行重推任务
	 */
	boolean shouldRetryingPoUploadSchedule();

	/**
	 * 是否执行对账任务
	 */
	boolean shouldCheckingPoSchedule();

	/**
	 * 是否执行重新对账任务
	 * @return
	 */
	boolean shouldRecheckingPoSchedule();

	/**
	 * 是否正在做日结，如果正在做日结，不推送SAP
	 */
	boolean isDoingDailyEndNow();
}

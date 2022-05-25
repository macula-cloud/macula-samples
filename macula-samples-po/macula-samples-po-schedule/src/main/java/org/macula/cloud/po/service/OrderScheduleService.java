package org.macula.cloud.po.service;

import java.util.Date;

import org.springframework.data.domain.Pageable;

/**
 * 定时任务相关服务接口
 */
public interface OrderScheduleService {

	/**
	 * 获取GBSS订单上传列表
	 */
	Long doPullGBSSUploadSchedule(Long startId, Pageable pageable);

	/**
	 * 获取GBSS订单上传列表2
	 */
	Long doPullGBSSUpload2Schedule(Long startId, Pageable pageable);

	/**
	 * 处理上传SAP失败的订单任务
	 */
	Long doUploadFailedOrderSchedule();

	/**
	 * 处理定时对账任务
	 */
	Long doCheckBillingSchedule(Date beginTime, Integer poCheckRange, Integer poValidateRange1);

	/**
	 * 定时任务:OMS对账失败数据进行重新对账
	 */
	Long doCheckBillingAgainSchedule();

}

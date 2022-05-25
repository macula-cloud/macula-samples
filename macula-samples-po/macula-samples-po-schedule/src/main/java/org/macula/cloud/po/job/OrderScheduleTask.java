//package org.macula.cloud.po.job;
//
//import java.util.Date;
//import java.util.Map;
//
//import org.apache.commons.lang3.time.DateUtils;
//import org.macula.cloud.po.service.OrderProcessControlService;
//import org.macula.cloud.po.service.OrderScheduleService;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Component;
//
//import io.choerodon.asgard.schedule.annotation.JobParam;
//import io.choerodon.asgard.schedule.annotation.JobTask;
//import io.choerodon.core.iam.ResourceLevel;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class OrderScheduleTask {
//
//	private OrderScheduleService orderScheduleService;
//	private OrderProcessControlService processControlService;
//
//	/**
//	 * 定时任务:重新从GBSS拉取SAP_DAILY_UPL_PO信息
//	 */
//	@JobTask(code = "oms-order-schedule-upl-po-pull", description = "OMS定时获取GBSS的UPLOAD列表", level = ResourceLevel.SITE, params = {
//			@JobParam(name = "pageSize", description = "单页大小", type = Integer.class, defaultValue = "100"),
//			@JobParam(name = "startId", description = "开始ID", type = Long.class, defaultValue = "1000000") })
//	public Map<String, Object> doPullGBSSUploadDataList(Map<String, Object> data) {
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("Start OMS定时获取GBSS的UPLOAD列表....");
//			}
//
//			if (!processControlService.shouldPullingUploadPoSchedule()) {
//				log.error("== SHOULD NOT PULL  GBSS UPLOAD SCHEDULE ==");
//				return data;
//			}
//
//			Integer pageSize = (Integer) data.get("pageSize");
//			if (pageSize == null) {
//				pageSize = 100;
//			}
//			Long startId = (Long) data.get("startId");
//			long count = orderScheduleService.doPullGBSSUploadSchedule(startId, PageRequest.of(0, pageSize));
//			data.put("COUNT", count);
//			if (log.isInfoEnabled()) {
//				log.info("End OMS定时获取GBSS的UPLOAD列表:  {}", count);
//			}
//		} catch (Throwable ex) {
//			log.error("OMS定时获取GBSS的UPLOAD列表失败！", ex);
//		}
//		return data;
//	}
//
//	/**
//	 * 定时任务:重新从GBSS拉取SAP_DAILY_UPL_PO_V2信息
//	 */
//	@JobTask(code = "oms-order-schedule-upl2-po-pull", description = "OMS定时获取GBSS的UPLOAD V2列表")
//	public Map<String, Object> doPullGBSSUpload2DataList(Map<String, Object> data) {
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("Start OMS定时获取GBSS的UPLOAD2列表....");
//			}
//
//			if (!processControlService.shouldPullingUploadPoSchedule()) {
//				log.error("== SHOULD NOT PULL GBSS UPLOAD2  SCHEULE==");
//				return data;
//			}
//			Integer pageSize = (Integer) data.get("pageSize");
//			if (pageSize == null) {
//				pageSize = 100;
//			}
//			Long startId = (Long) data.get("startId");
//
//			long count = orderScheduleService.doPullGBSSUpload2Schedule(startId, PageRequest.of(0, pageSize));
//			data.put("COUNT", count);
//			if (log.isInfoEnabled()) {
//				log.info("End OMS定时获取GBSS的UPLOAD2列表:  {}", count);
//			}
//		} catch (Throwable ex) {
//			log.error("OMS定时获取GBSS的UPLOAD2列表失败！", ex);
//		}
//		return data;
//	}
//
//	@JobTask(code = "oms-order-schedule-upload", description = "OMS失败订单定时重新推送")
//	public Map<String, Object> doUploadFailedOrderSchedule(Map<String, Object> data) {
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("Start OMS订单扫描重新推送SAP....");
//			}
//
//			if (!processControlService.shouldRetryingPoUploadSchedule()) {
//				log.error("== SHOULD NOT RETRY FAILED ORDER SCHEDULE  ==");
//				return data;
//			}
//
//			long count = orderScheduleService.doUploadFailedOrderSchedule();
//			data.put("COUNT", count);
//			if (log.isInfoEnabled()) {
//				log.info("End OMS订单扫描重新推送SAP COUNT:  {}", count);
//			}
//		} catch (Throwable ex) {
//			log.error("OMS失败订单定时重新推送失败！", ex);
//		}
//		return data;
//	}
//
//	/**
//	 * 定时任务:定时对账:GBSS和OMS对账
//	 * @param data
//	 * @return
//	 */
//	@JobTask(code = "oms-order-schedule-check", description = "GBSS和OMS定时对账", level = ResourceLevel.SITE, params = {
//			@JobParam(name = "beginTime", description = "对账起点时间", type = String.class),
//			@JobParam(name = "poCheckRange", description = "对账区间段（秒）", type = Integer.class),
//			@JobParam(name = "poValidateRange", description = "对账安全时间（秒）", type = Integer.class) })
//	public Map<String, Object> doCheckBillingSchedule(Map<String, Object> data) {
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("Start GBSS和OMS定时对账....");
//			}
//
//			if (!processControlService.shouldCheckingPoSchedule()) {
//				log.error("== SHOULD NOT CHECK BILLING SCHEDULE  ==");
//				return data;
//			}
//
//			Integer poCheckRange = (Integer) data.get("poCheckRange");
//			Integer poValidateRange = (Integer) data.get("poValidateRange");
//			Date beginTime = null;
//			try {
//				beginTime = DateUtils.parseDate((String) data.get("beginTime"), "yyyy-MM-dd");
//			} catch (Exception ex) {
//				beginTime = null;
//			}
//			long page = 0, count = 0;
//			while (page >= 0) {
//				page = orderScheduleService.doCheckBillingSchedule(beginTime, poCheckRange, poValidateRange);
//				if (page > 0) {
//					count += page;
//				}
//			}
//			data.put("COUNT", count);
//			if (log.isInfoEnabled()) {
//				log.info("End GBSS和OMS定时对账 COUNT:  {}", count);
//			}
//		} catch (Throwable ex) {
//			log.error("GBSS和OMS定时对账失败！", ex);
//		}
//		return data;
//	}
//
//	/**
//	 * 定时任务:重新从GBSS拉取对账失败的数据
//	 * @param data
//	 * @return
//	 */
//	@JobTask(code = "oms-order-schedule-recheck", description = "OMS订单扫描重新获取对账数据")
//	public Map<String, Object> doCheckBillingFailedSchedule(Map<String, Object> data) {
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("Start OMS订单扫描重新获取对账数据....");
//			}
//			if (!processControlService.shouldRecheckingPoSchedule()) {
//				log.error("== SHOULD NOT RECHECK BILLING SCHEDULE  ==");
//				return data;
//			}
//
//			long count = orderScheduleService.doCheckBillingAgainSchedule();
//			data.put("COUNT", count);
//			if (log.isInfoEnabled()) {
//				log.info("End OMS订单扫描重新获取对账数据 COUNT:  {}", count);
//			}
//		} catch (Throwable ex) {
//			log.error("OMS订单扫描重新获取对账数据失败！", ex);
//		}
//		return data;
//	}
//
//}

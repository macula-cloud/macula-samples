package org.macula.cloud.po.job;

import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.macula.cloud.po.service.OrderScheduleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class OrderQuartzSchedule {

	private OrderScheduleService orderScheduleService;
	private OrderProcessControlService processControlService;
	private OMSConfig config;

	/**
	 * 定时任务:重新从GBSS拉取SAP_DAILY_UPL_PO信息
	 */
	@Scheduled(cron = "5 0/1 * * * ?")
	public void doPullGBSSUploadDataList() {
		try {
			if (log.isInfoEnabled()) {
				log.info("Start OMS定时获取GBSS的UPLOAD列表....");
			}

			if (!processControlService.shouldPullingUploadPoSchedule()) {
				log.error("== SHOULD NOT PULL  GBSS UPLOAD SCHEDULE ==");
				log.info("End OMS定时获取GBSS的UPLOAD列表（按设定不执行）");
				return;
			}

			Integer pageSize = 100;
			Long startId = config.getMinUploadId();
			Long count = orderScheduleService.doPullGBSSUploadSchedule(startId, PageRequest.of(0, pageSize));
			if (log.isInfoEnabled()) {
				if (count != null) {
					log.info("End OMS定时获取GBSS的UPLOAD列表:  {}", count);
				} else {
					log.info("End OMS定时获取GBSS的UPLOAD列表:  {}，已有任务在执行！", count);
				}
			}
		} catch (Throwable ex) {
			log.error("OMS定时获取GBSS的UPLOAD列表失败！", ex);
		}
	}

	/**
	 * 定时任务:重新从GBSS拉取SAP_DAILY_UPL_PO_V2信息
	 */
	public void doPullGBSSUpload2DataList() {
		try {
			if (log.isInfoEnabled()) {
				log.info("Start OMS定时获取GBSS的UPLOAD2列表....");
			}

			if (!processControlService.shouldPullingUploadPoSchedule()) {
				log.error("== SHOULD NOT PULL  GBSS UPLOAD SCHEDULE ==");
				log.info("End OMS定时获取GBSS的UPLOAD2列表（按设定不执行）");
				return;
			}

			Integer pageSize = 100;
			Long startId = config.getMinUpload2Id();
			Long count = orderScheduleService.doPullGBSSUpload2Schedule(startId, PageRequest.of(0, pageSize));
			if (log.isInfoEnabled()) {
				if (count != null) {
					log.info("End OMS定时获取GBSS的UPLOAD2列表:  {}", count);
				} else {
					log.info("End OMS定时获取GBSS的UPLOAD2列表:  {}，已有任务在执行！", count);
				}
			}
		} catch (Throwable ex) {
			log.error("OMS定时获取GBSS的UPLOAD列表失败！", ex);
		}
	}

	/**
	 * 定时任务:OMS失败订单定时重新推送
	 */
	@Scheduled(cron = "0/30 * * 4-23 * ?")
	public void doUploadFailedOrderSchedule() {
		try {
			if (log.isInfoEnabled()) {
				log.info("Start OMS订单扫描重新推送SAP....");
			}
			if (!processControlService.shouldRetryingPoUploadSchedule()) {
				log.error("== SHOULD NOT RETRY FAILED ORDER SCHEDULE  ==");
				log.info("End OMS订单扫描重新推送SAP（按设定不执行）");
				return;
			}
			Long count = orderScheduleService.doUploadFailedOrderSchedule();
			if (log.isInfoEnabled()) {
				if (count != null) {
					log.info("End OMS订单扫描重新推送SAP COUNT:  {}", count);
				} else {
					log.info("End OMS订单扫描重新推送SAP COUNT:  {}，已有任务在执行！", count);
				}
			}
		} catch (Throwable ex) {
			log.error("OMS失败订单定时重新推送失败！", ex);
		}
	}

	/**
	 * 定时任务:GBSS和OMS定时对账
	 */
	@Scheduled(cron = "45 * * 5-23 * ?")
	public void doCheckBillingSchedule() {
		try {
			if (log.isInfoEnabled()) {
				log.info("Start GBSS和OMS定时对账....");
			}

			if (!processControlService.shouldCheckingPoSchedule()) {
				log.error("== SHOULD NOT CHECK BILLING SCHEDULE  ==");
				log.info("End GBSS和OMS定时对账（按设定不执行）");
				return;
			}
			Integer poCheckRange = config.getPoCheckRange();
			Integer poValidateRange = config.getPoValidateRange();
			Long count = orderScheduleService.doCheckBillingSchedule(null, poCheckRange, poValidateRange);
			if (log.isInfoEnabled()) {
				if (count != null) {
					log.info("End GBSS和OMS定时对账 COUNT:  {}", count);
				} else {
					log.info("End GBSS和OMS定时对账 COUNT:  {}，已有任务在执行！", count);
				}
			}
		} catch (Throwable ex) {
			log.error("GBSS和OMS定时对账失败！", ex);
		}
	}

	/**
	 * 定时任务:重新从GBSS拉取对账失败的数据
	 */
	@Scheduled(cron = "55 0/1 * 4-23 * ?")
	public void doCheckBillingFailedSchedule() {
		try {
			if (log.isInfoEnabled()) {
				log.info("Start OMS订单扫描重新获取对账数据....");
			}
			if (!processControlService.shouldRecheckingPoSchedule()) {
				log.error("== SHOULD NOT RECHECK BILLING SCHEDULE  ==");
				log.info("End OMS订单扫描重新获取对账数据（按设定不执行）");
				return;
			}

			Long count = orderScheduleService.doCheckBillingAgainSchedule();
			if (log.isInfoEnabled()) {
				if (count != null) {
					log.info("End OMS订单扫描重新获取对账数据 COUNT:  {}", count);
				} else {
					log.info("End OMS订单扫描重新获取对账数据 COUNT:  {}，已有任务在执行！", count);
				}
			}
		} catch (Throwable ex) {
			log.error("OMS订单扫描重新获取对账数据失败！", ex);
		}
	}

}

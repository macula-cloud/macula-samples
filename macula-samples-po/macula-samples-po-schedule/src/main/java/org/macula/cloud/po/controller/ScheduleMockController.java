package org.macula.cloud.po.controller;

import org.macula.cloud.po.job.OrderQuartzSchedule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/oms/job")
@AllArgsConstructor
public class ScheduleMockController {

	private OrderQuartzSchedule task;

	@GetMapping("/check-billing-failed")
	public void doCheckBillingFailedSchedule() {
		task.doCheckBillingFailedSchedule();
	}

	@GetMapping("/check-billing-schedule")
	public void doCheckBillingSchedule() {
		task.doCheckBillingSchedule();
	}

	@GetMapping("/pull-gbss-upload2")
	public void doPullGBSSUpload2DataList() {
		task.doPullGBSSUpload2DataList();
	}

	@GetMapping("/pull-gbss-upload")
	public void doPullGBSSUploadDataList() {
		task.doPullGBSSUploadDataList();
	}

	@GetMapping("/upload-failed-order")
	public void doUploadFailedOrderSchedule() {
		task.doUploadFailedOrderSchedule();
	}

}

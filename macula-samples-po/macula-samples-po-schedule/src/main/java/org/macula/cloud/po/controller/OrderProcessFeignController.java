package org.macula.cloud.po.controller;

import java.util.Date;
import java.util.List;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.repository.PoCheckMasterRepository;
import org.macula.cloud.po.service.OrderCheckingService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/oms/process")
@AllArgsConstructor
public class OrderProcessFeignController {

	private OrderProcessingService orderProcessingService;
	private OrderCheckingService orderCheckingService;
	private PoCheckMasterRepository poCheckMasterRepository;

	@GetMapping("/{poNo}")
	@Permission(permissionWithin = true)
	public ResponseEntity<?> processPo(@PathVariable("poNo") String poNo) {
		StatusStartRequest request = StatusStartRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
				.status(PoProcessStatus.PO_HAND_REQUEST).comments("接收到手工处理单！").build();

		// 发送执行业务事件请求 
		Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
		if (log.isInfoEnabled()) {
			log.info("StatusStartRequest {} handleResult {}", request, handleResult);
		}
		if (handleResult != null && handleResult.booleanValue()) {
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
		}
		return ResponseEntity.ok(orderProcessingService.getProcessMaster(poNo));
	}

	/**
	 * 方法说明：admin前端页面调用,执行重新对账的接口
	 * id:对账主表的ID,必传参数
	 * @param id
	 * @return
	 */
	@GetMapping("/afreshChecking/{id}")
	@Permission(permissionWithin = true)
	public ResponseEntity<?> afreshChecking(@PathVariable("id") Long id) {
		PoCheckMaster poCheckMaster = poCheckMasterRepository.findById(id).get();
		Date startTime = poCheckMaster.getStartTime();
		Date endTime = poCheckMaster.getEndTime();
		// 查询业务平台对账数据
		List<PoCheckDetail> gbssDetails = orderCheckingService.getGBSSBillingData(startTime, endTime);
		// 查询OMS对账数据
		List<PoCheckDetail> omsDetails = orderCheckingService.findLocaleBillingData(startTime, endTime);
		// 合并对账数据
		List<PoCheckDetail> matchupDetails = orderCheckingService.matchUpCheckDetails(gbssDetails, omsDetails);
		PoCheckMaster newPoCheckMaster = orderCheckingService.updateCheckingResult(startTime, endTime, matchupDetails);
		return ResponseEntity.ok(newPoCheckMaster);
	}

	/**
	 * 方法说明：admin前端页面调用,执行对账忽略操作
	 * @param gbssPoNo:对账主明细表的gbssPoNo,必传参数
	 * @param checkMasterId:对账主表的ID
	 * @return
	 */
	@GetMapping("/successByHand/{gbssPoNo}/{checkMasterId}")
	@Permission(permissionWithin = true)
	public ResponseEntity<?> successByHand(@PathVariable("gbssPoNo") String gbssPoNo, @PathVariable("checkMasterId") Long checkMasterId) {
		PoCheckDetail poCheckDetail = orderCheckingService.successByHand(gbssPoNo, checkMasterId);
		return ResponseEntity.ok(poCheckDetail);
	}

}

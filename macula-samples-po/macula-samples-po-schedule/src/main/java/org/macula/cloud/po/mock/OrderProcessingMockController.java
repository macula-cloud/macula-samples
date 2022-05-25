package org.macula.cloud.po.mock;

import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.service.OrderPackageService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.vo.DealerOrderVo;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/oms/processing")
@AllArgsConstructor
public class OrderProcessingMockController {

	private OrderProcessingService orderProcessingService;
	private OrderPackageService orderPackageService;

	private SaveDealerOrderThreadHelpServiceImpl oldService;

	@GetMapping("/{poNo}/{status}")
	public boolean doProcessingLogic(@PathVariable("poNo") String poNo, @PathVariable("status") String status) {
		StatusStartRequest request = StatusStartRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
				.status(PoProcessStatus.PO_RETRY_REQUEST).comments("重推SAP订单！").build();
		Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
		if (log.isInfoEnabled()) {
			log.info("StatusStartRequest {} handleResult {}", request, handleResult);
		}
		if (handleResult != null && handleResult.booleanValue()) {
			orderProcessingService.doProcessingLogic(poNo);
		}
		return handleResult;
	}

	@GetMapping("/new/{poNo}")
	public DealerOrderVo doNewLogic(@PathVariable("poNo") String poNo) throws Exception {
		PoResultDetailVo result = orderProcessingService.loadLocalPoResultDetailVo(poNo);
		return orderPackageService.packageSapOrderVo(poNo, result);
	}

	@GetMapping("/old/{poNo}")
	public DealerOrderVo doOldLogic(@PathVariable("poNo") String poNo) throws Exception {
		return oldService.action(poNo);
	}

}

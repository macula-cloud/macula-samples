package org.macula.cloud.po.feign;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.security.feign.OAuth2Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign接口说明:主要是页面手动操作的比如手动对账异常数据的重新拉取,SAP数据重新推送等
 */
@OAuth2Api
@FeignClient(name = "macula-samples-po-schedule", fallbackFactory = OrderScheduleFeignClientFallbackFactory.class)
public interface OrderScheduleFeignClient {

	/**
	 * 该接口可同时供：手动对账异常数据的重新拉取,SAP数据重新推送
	 * @param poNo
	 */
	@GetMapping("/api/oms/process/{poNo}")
	ResponseEntity<PoProcessMaster> handleOrderUpload(@PathVariable("poNo") String poNo);

	/**
	 * 重新对账接口(微服务调用OMS)
	 * @param id:对账主表的ID,必传参数
	 */
	@GetMapping("/api/oms/process/afreshChecking/{id}")
	ResponseEntity<PoCheckMaster> afreshChecking(@PathVariable("id") Long id);

	/**
	 * 对账忽略操作：调用OMS微服务更改状态为81
	 * gbssPoNo：对账主明细表的gbssPoNo,必传参数
	 */
	@GetMapping("/api/oms/process/successByHand/{gbssPoNo}/{checkMasterId}")
	ResponseEntity<PoCheckDetail> successByHand(@PathVariable("gbssPoNo") String gbssPoNo, @PathVariable("checkMasterId") Long checkMasterId);
}

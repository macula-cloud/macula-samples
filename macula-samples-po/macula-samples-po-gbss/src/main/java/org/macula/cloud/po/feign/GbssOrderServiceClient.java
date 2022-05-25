package org.macula.cloud.po.feign;

import java.util.List;

import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "macula-samples-po-gbss", path = "/api/macula-samples/po", fallbackFactory = GbssOrderServiceClientFallbackFactory.class)
public interface GbssOrderServiceClient {

	@GetMapping("/gbss/order")
	@ServiceInvokeProxy(key = "args[0]", description = "'获取PO详情'", target = "'macula-samples-po-gbss'", targetMethod = "'getPurchaseOrder'")
	Object getPurchaseOrder(@RequestParam("poNo") String poNo);

	@GetMapping("/gbss/po")
	ResponseEntity<PoResultDetailVo> getPoResultDetailVo(@RequestParam("poNo") String poNo);

	/**
	 * 根据poNo从GBSS查询所有相关联的数据
	 * @param poNo
	 * @return
	 */
	@GetMapping("/gbss/getResultDetailVo")
	@ServiceInvokeProxy(key = "args[0]", description = "'获取PO详情'", target = "'macula-samples-po-gbss'", targetMethod = "'getResultDetailVo'")
	PoResultDetailVo getResultDetailVo(@RequestParam("poNo") String poNo);

	/**
	 * 从GBSS获取对账数据(根据时间范围内的销售单据输入时间查询GBSS数据)
	 * startTime:开始:销售单据输入时间
	 * endTime:结束:销售单据输入时间
	 */
	@GetMapping("/gbss/takeGBSSBillingData")
	@ServiceInvokeProxy(key = "args[0]", description = "'定时对账获取GBSS数据'", target = "'macula-samples-po-gbss'", targetMethod = "'getResultDetailVo'")
	List<PoCheckDetail> takeGBSSBillingData(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);
}

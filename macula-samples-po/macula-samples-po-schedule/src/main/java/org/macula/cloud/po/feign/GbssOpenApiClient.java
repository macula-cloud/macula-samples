package org.macula.cloud.po.feign;

import java.util.List;

import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.macula.cloud.security.feign.OpenApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gbss-esb-openapi", url = "${gbss.esb.url}/front/api/gbss", fallbackFactory = GbssOpenApiClientFallbackFactory.class)
@OpenApi(clientId = "openapi_oms")
public interface GbssOpenApiClient {

	@GetMapping("/po/order/allPo")
	@ServiceInvokeProxy(key = "args[0]", description = "'获取PO详情'", target = "'gbss-esb-openapi'", targetMethod = "'front/api/gbss/po/order/allPo'")
	ExecuteResponse<PoResultDetailVo> getGbssOrderResultDetail(@RequestParam("orderNo") String orderNo);

	@GetMapping("/po/order/poCheckDetails")
	@ServiceInvokeProxy(key = "args", description = "'获取对账数据'", target = "'gbss-esb-openapi'", targetMethod = "'front/api/gbss/po/order/poCheckDetails'")
	ExecuteResponse<List<PoCheckDetail>> getGbssBillingData(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

	@GetMapping("/po/order/sapDailyUplPos")
	@ServiceInvokeProxy(key = "args", description = "'获取上传列表'", target = "'gbss-esb-openapi'", targetMethod = "'front/api/gbss/po/order/sapDailyUplPos'")
	ExecuteResponse<Page<SapDailyUplPo>> getGbssUploadDataList(@RequestParam("sapDailyUplType") String v1, @RequestParam("startId") long startId,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize);

	@GetMapping("/po/order/sapDailyUplPos")
	@ServiceInvokeProxy(key = "args", description = "'获取上传列表'", target = "'gbss-esb-openapi'", targetMethod = "'front/api/gbss/po/order/sapDailyUplPos'")
	ExecuteResponse<Page<SapDailyUplPoV2>> getGbssUpload2DataList(@RequestParam("sapDailyUplType") String v2, @RequestParam("startId") long startId,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize);

}

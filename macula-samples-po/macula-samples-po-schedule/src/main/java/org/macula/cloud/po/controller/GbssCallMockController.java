package org.macula.cloud.po.controller;

import java.util.List;

import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.feign.GbssOpenApiClient;
import org.macula.cloud.po.repository.SapDailyUplPoRepository;
import org.macula.cloud.po.type.PoProcessSource;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/oms/gbss")
@AllArgsConstructor
public class GbssCallMockController {

	private GbssOpenApiClient gbssOpenApiClient;
	private SapDailyUplPoRepository repository;

	@GetMapping("/esb/{poNo}")
	public ResponseEntity<?> executeFeignClient(@PathVariable("poNo") String poNo) {
		ExecuteResponse<PoResultDetailVo> response = gbssOpenApiClient.getGbssOrderResultDetail(poNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/esb/billing")
	public ResponseEntity<?> executeFeignClient() {
		ExecuteResponse<List<PoCheckDetail>> response = gbssOpenApiClient.getGbssBillingData("2020-01-01 00:10:00", "2020-01-02 09:10:00");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/esb/upl")
	public ResponseEntity<?> executeUplFeignClient() {
		ExecuteResponse<Page<SapDailyUplPo>> response = gbssOpenApiClient.getGbssUploadDataList(PoProcessSource.PO_SOURCE_UPL, 1L, 1, 2);
		for (SapDailyUplPo entity : response.getReturnObject()) {
			repository.save(entity);
		}
		return ResponseEntity.ok(response);
	}
}

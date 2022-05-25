package org.macula.cloud.logistics.controller;

import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionParseRequest;
import org.macula.cloud.cainiao.DivisionParseResponse;
import org.macula.cloud.cainiao.ParseDivisionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/logistics/address")
@AllArgsConstructor
public class AddressMappingManagerController {
	private CainiaoLinkService linkService;

	@Permission(permissionPublic = true)
	@GetMapping(value = "/mapping/{address}", produces = { "application/xml" })
	public ResponseEntity<ParseDivisionResult> mappingAddress(@PathVariable("address") String address) {
		ParseDivisionResult result = null;
		int retryTimes = 0;
		DivisionParseResponse response = null;
		do {
			response = linkService.getDivisionParse(DivisionParseRequest.of(address, linkService.getConfig().getVersion()));
			if (response != null && response.isSuccess()) {
				result = response.getParseDivisionResult();
			}
		} while (retryTimes++ < 5 && (response == null || !response.isSuccess()));
		return ResponseEntity.ok(result);
	}
}

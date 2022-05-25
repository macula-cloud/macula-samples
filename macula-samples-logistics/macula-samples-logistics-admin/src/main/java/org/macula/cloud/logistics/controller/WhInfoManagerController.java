package org.macula.cloud.logistics.controller;

import java.util.List;

import org.macula.cloud.logistics.domain.WhInfo;
import org.macula.cloud.logistics.service.WhInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/logistics/whinfo")
@AllArgsConstructor
public class WhInfoManagerController {

	private WhInfoService whInfoService;

	@Permission(permissionPublic = true)
	@GetMapping
	public ResponseEntity<List<WhInfo>> listDcInfos() {
		return ResponseEntity.ok(whInfoService.findWhInfos());
	}

	@Permission(permissionLogin = true)
	@GetMapping(value = "/update")
	public List<WhInfo> updateWhInfo() {
		return whInfoService.updateWhInfo();
	}

}

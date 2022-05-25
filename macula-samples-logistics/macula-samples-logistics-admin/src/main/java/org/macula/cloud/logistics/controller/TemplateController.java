package org.macula.cloud.logistics.controller;

import org.macula.cloud.logistics.service.TemplateService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import io.choerodon.core.enums.OperationType;
import io.choerodon.core.enums.ScopeType;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 
 */
@RestController
@RequestMapping("/v1/templates")
@AllArgsConstructor
public class TemplateController {

	private TemplateService templateService;

	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("下载导入地址的模板文件")
	@GetMapping("/addresses")
	public ResponseEntity<Resource> downloadAddressesTemplates() {
		return templateService.downloadTemplates("addressTemplate", "地址导入模板");
	}

	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("下载导入配置的模板文件")
	@GetMapping("/dc_settings")
	public ResponseEntity<Resource> downloadDcSettingsTemplates() {
		return templateService.downloadTemplates("dcSettingsTemplate", "配置导入模板");
	}

}

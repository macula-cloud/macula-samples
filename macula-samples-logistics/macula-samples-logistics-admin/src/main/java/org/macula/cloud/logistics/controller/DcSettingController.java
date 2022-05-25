package org.macula.cloud.logistics.controller;

import java.util.List;

import org.macula.cloud.logistics.domain.HomeDcSetting;
import org.macula.cloud.logistics.domain.SettingUrlHistory;
import org.macula.cloud.logistics.domain.StoreDcSetting;
import org.macula.cloud.logistics.service.DcSettingService;
import org.macula.cloud.logistics.service.SettingUrlHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.choerodon.core.annotation.Permission;
import io.choerodon.core.enums.OperationType;
import io.choerodon.core.enums.ScopeType;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 
 
 */
@RestController
@RequestMapping("/v1/dc_settings")
@AllArgsConstructor
public class DcSettingController {

	private DcSettingService dcSettingService;
	private SettingUrlHistoryService settingUrlHistoryService;

	@PostMapping("/import")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("导入配送设置")
	public ResponseEntity<?> importDCSetting(@RequestPart MultipartFile multipartFile) {
		dcSettingService.importHomeAndStoreSetting(multipartFile);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/export")
	@Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
	@ApiOperation("导出配送设置")
	public ResponseEntity<?> exportDCSetting() {
		dcSettingService.exportDCSettingExcel();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{code}/store")
	@ApiOperation("查询店铺配送设置")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	public ResponseEntity<List<StoreDcSetting>> queryStoreDcSettings(@PathVariable("code") String code) {
		return new ResponseEntity<>(dcSettingService.queryStoreDcSettings(code), HttpStatus.OK);
	}

	@GetMapping("/{code}/home")
	@ApiOperation("查询家居配送设置")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	public ResponseEntity<List<HomeDcSetting>> queryHomeDcSettings(@PathVariable("code") String code) {
		return new ResponseEntity<>(dcSettingService.queryHomeDcSettings(code), HttpStatus.OK);
	}

	@PostMapping("/{code}/store")
	@ApiOperation("更新店铺配送设置")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	public ResponseEntity<StoreDcSetting> insertOrUpdateStoreDcSettings(@PathVariable("code") String code,
			@RequestBody StoreDcSetting storeDcSettingDTO) {
		return new ResponseEntity<>(dcSettingService.insertOrUpdateStoreDcSettings(code, storeDcSettingDTO), HttpStatus.OK);
	}

	@PostMapping("/{code}/home")
	@ApiOperation("更新家居配送设置")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	public ResponseEntity<HomeDcSetting> insertOrUpdateHomeDcSettings(@PathVariable("code") String code,
			@RequestBody HomeDcSetting homeDcSettingDTO) {
		return new ResponseEntity<>(dcSettingService.insertOrUpdateHomeDcSettings(code, homeDcSettingDTO), HttpStatus.OK);
	}

	@GetMapping("/url")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("获取最新的下载地址")
	public ResponseEntity<SettingUrlHistory> getLatestUrl() {
		return new ResponseEntity<>(settingUrlHistoryService.getLatestUrl(), HttpStatus.OK);
	}

}
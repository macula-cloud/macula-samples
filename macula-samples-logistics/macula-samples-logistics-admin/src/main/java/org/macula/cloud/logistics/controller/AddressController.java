package org.macula.cloud.logistics.controller;

import java.util.List;

import org.macula.cloud.logistics.domain.AddressUrlHistory;
import org.macula.cloud.logistics.domain.CnAddress;
import org.macula.cloud.logistics.service.AddressUrlHistoryService;
import org.macula.cloud.logistics.service.CnAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/v1/addresses")
@AllArgsConstructor
public class AddressController {

	private CnAddressService addressService;
	private AddressUrlHistoryService addressUrlHistoryService;

	@GetMapping
	@Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
	@ApiOperation("根据父亲code 查询地址列表")
	public ResponseEntity<List<CnAddress>> list(@RequestParam("parent_code") String parentCode) {
		return new ResponseEntity<>(addressService.listByParentCode(parentCode), HttpStatus.OK);
	}

	@GetMapping("/{code}")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("根据编码查询地址")
	public ResponseEntity<CnAddress> query(@PathVariable("code") String code) {
		return new ResponseEntity<>(addressService.query(code), HttpStatus.OK);
	}

	@PutMapping("/{code}")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("更新地址")
	public ResponseEntity<CnAddress> update(@PathVariable("code") String code, @RequestBody CnAddress cnAddressDTO) {
		return new ResponseEntity<>(addressService.update(code, cnAddressDTO), HttpStatus.OK);
	}

	@GetMapping("/export")
	@Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
	@ApiOperation("导出地址")
	public ResponseEntity<?> exportAddress() {
		addressService.exportAddressExcel();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/import")
	@Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
	@ApiOperation("导入地址")
	public List<CnAddress> importAddress(@RequestPart MultipartFile multipartFile) {
		return addressService.importAddressExcel(multipartFile);
	}

	@GetMapping("/url")
	@Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
	@ApiOperation("获取最新的下载地址")
	public ResponseEntity<AddressUrlHistory> getLatestUrl() {
		return new ResponseEntity<>(addressUrlHistoryService.getLatestUrl(), HttpStatus.OK);
	}
}

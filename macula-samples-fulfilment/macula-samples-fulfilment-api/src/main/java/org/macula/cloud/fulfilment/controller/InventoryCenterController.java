package org.macula.cloud.fulfilment.controller;

import org.macula.cloud.fulfilment.service.InventoryCenterService;
import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "库存中心服务", value = "库存中心服务")
@RestController
@RequestMapping("/api/macula-samples/stock/center")
@Slf4j
public class InventoryCenterController {

	@Autowired
	private InventoryCenterService inventoryService;

	@GetMapping(value = "/status")
	@ApiOperation(value = "库存状态")
	public ResponseEntity<InventoryResponse> inventoryStatus(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.status(request);
		log.info("Response: {}", JSON.toString(response));
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/reserve")
	@ApiOperation(value = "库存锁定")
	public ResponseEntity<InventoryResponse> inventoryReserve(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.reserve(request);
		log.info("Response: {}", JSON.toString(response));
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/commit")
	@ApiOperation(value = "库存确认")
	public ResponseEntity<InventoryResponse> inventoryCommit(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.commit(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/release")
	@ApiOperation(value = "库存确认")
	public ResponseEntity<InventoryResponse> inventoryRelease(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.release(request);
		return ResponseEntity.ok(response);
	}
}

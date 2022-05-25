package org.macula.cloud.logistics.controller;

import org.macula.cloud.logistics.service.AddressCenterService;
import org.macula.cloud.logistics.vo.AddressResponse;
import org.macula.cloud.logistics.vo.LogisticsRequest;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "地址中心服务", value = "地址中心服务")
@RestController
@RequestMapping("/api/logistics/address")
@Slf4j
public class AddressCenterController {

	@Autowired
	private AddressCenterService addressCenterService;

	@GetMapping(value = "/children")
	@ApiOperation(value = "下层地址信息获取")
	public ResponseEntity<AddressResponse> getChildren(@RequestBody LogisticsRequest request) {
		AddressResponse response = addressCenterService.getChildren(request);
		log.info("Response: {}", JSON.toString(response));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/details")
	@ApiOperation(value = "地址信息获取")
	public ResponseEntity<AddressResponse> getAddress(@RequestBody LogisticsRequest request) {
		AddressResponse response = addressCenterService.getAddress(request);
		log.info("Response: {}", JSON.toString(response));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/home_rdc")
	@ApiOperation(value = "地址信息获取")
	public ResponseEntity<AddressResponse> getHomeRDCMapping(@RequestBody LogisticsRequest request) {
		AddressResponse response = addressCenterService.getHomeRDCMapping(request);
		log.info("Response: {}", JSON.toString(response));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/store_rdc")
	@ApiOperation(value = "地址信息获取")
	public ResponseEntity<AddressResponse> getStoreRDCMapping(@RequestBody LogisticsRequest request) {
		AddressResponse response = addressCenterService.getStoreRDCMapping(request);
		log.info("Response: {}", JSON.toString(response));
		return ResponseEntity.ok(response);
	}

}

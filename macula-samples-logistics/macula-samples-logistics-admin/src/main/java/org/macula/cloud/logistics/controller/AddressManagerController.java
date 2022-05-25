package org.macula.cloud.logistics.controller;

import org.macula.cloud.logistics.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/logistics/address")
public class AddressManagerController {

	private AddressService addressService;

	@GetMapping("/sync")
	@Permission(permissionWithin = true)
	public ResponseEntity<?> sync() {
		addressService.syncAddress();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

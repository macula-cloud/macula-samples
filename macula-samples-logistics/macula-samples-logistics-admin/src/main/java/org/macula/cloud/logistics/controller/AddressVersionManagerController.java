package org.macula.cloud.logistics.controller;

import java.util.List;

import org.macula.cloud.logistics.domain.AddressVersion;
import org.macula.cloud.logistics.service.AddressVersionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/logistics/address")
@AllArgsConstructor
public class AddressVersionManagerController {

	private AddressVersionService addressVersionService;

	@Permission(permissionLogin = true)
	@GetMapping(value = "/version/update")
	public List<AddressVersion> updateAddressVersion() {
		addressVersionService.updateAddressVersion();
		return addressVersionService.findAddressVersions();
	}
}

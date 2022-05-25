package org.macula.cloud.logistics.controller;

import java.util.HashMap;
import java.util.Map;

import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionParseRequest;
import org.macula.cloud.cainiao.DivisionParseResponse;
import org.macula.cloud.cainiao.ParseDivisionResult;
import org.macula.cloud.logistics.service.AddressDivisionUpdate;
import org.macula.cloud.logistics.service.AddressVersionService;
import org.macula.cloud.logistics.service.Gbss2CainiaoUpdate;
import org.macula.cloud.logistics.service.HomeAddressUpdate;
import org.macula.cloud.logistics.service.PoAreaInfoUpdate;
import org.macula.cloud.logistics.service.StoreAddressUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;

@RestController
@RequestMapping("/api/logistics")
public class AddressUpdateController {

	@Autowired
	private CainiaoLinkService service;

	@Autowired
	private AddressDivisionUpdate addressDivisionUpdater;

	@Autowired
	private StoreAddressUpdate storeAddressUpdater;

	@Autowired
	private HomeAddressUpdate homeAddressUpdater;

	@Autowired
	private AddressVersionService addressVersionService;

	@Autowired
	private Gbss2CainiaoUpdate gbss2CainiaoUpdater;

	@Autowired
	private PoAreaInfoUpdate poAreaInfoUpdater;

	private Map<String, DivisionParseResponse> cache = new HashMap<String, DivisionParseResponse>();

	@GetMapping(value = "/address/update")
	public ResponseEntity<String> addressUpdate() {
		addressDivisionUpdater.syncAddress();
		return ResponseEntity.ok("Update addressUpdate OK");
	}

	@Permission(permissionPublic = true)
	@GetMapping(value = "/address/{address}")
	public ResponseEntity<ParseDivisionResult> parseStoreAddress(@PathVariable("address") String address) {
		DivisionParseResponse response;
		if (cache.containsKey(address)) {
			response = cache.get(address);
		} else {
			int retryTimes = 0;
			do {
				response = service.getDivisionParse(DivisionParseRequest.of(address, "LATEST"));
			} while (retryTimes++ < 5 && (response == null || !response.isSuccess()));
			cache.put(address, response);
		}
		return ResponseEntity.ok(response.getParseDivisionResult());
	}

	@GetMapping(value = "/address/store/update")
	public ResponseEntity<String> updateStoreAddress() {
		storeAddressUpdater.updateAllStoreAddress();
		return ResponseEntity.ok("Update Store Address OK");
	}

	@GetMapping(value = "/address/store/updateunmappings")
	public ResponseEntity<String> updateStoreAddressUnmappings() {
		storeAddressUpdater.updateUnloadMappingStoreAddress();
		return ResponseEntity.ok("Update StoreAddressUnmappings OK");
	}

	@GetMapping(value = "/address/home/update")
	public ResponseEntity<String> updateHomeAddress() {
		homeAddressUpdater.updateAllHomeAddress();
		return ResponseEntity.ok("Update Home Address OK");
	}

	@GetMapping(value = "/address/home/updateunmappings")
	public ResponseEntity<String> updateHomeAddressUnmappings() {
		homeAddressUpdater.updateUnloadMappingHomeAddress();
		return ResponseEntity.ok("Update HomeAddressUnmappings OK");
	}

	@GetMapping(value = "/address/version/update")
	public ResponseEntity<String> updateAddressVersion() {
		addressVersionService.updateAddressVersion();
		return ResponseEntity.ok("Update Address Version OK");
	}

	@GetMapping(value = "/address/gbss/update")
	public ResponseEntity<String> updateGbss2Cainiao() {
		gbss2CainiaoUpdater.updateGbss2CainiaoMapping();
		return ResponseEntity.ok("Update Gbss 2 Cainiao OK");
	}

	@GetMapping(value = "/address/poarea/update")
	public ResponseEntity<String> updatePoAreaInfo() {
		poAreaInfoUpdater.updateGbss2CainiaoMapping();
		return ResponseEntity.ok("Update PoAreaInfo 2 Cainiao OK");
	}
}

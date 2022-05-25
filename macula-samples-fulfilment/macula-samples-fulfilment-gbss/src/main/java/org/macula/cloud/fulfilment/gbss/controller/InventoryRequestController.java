package org.macula.cloud.fulfilment.gbss.controller;

import org.macula.cloud.fulfilment.gbss.service.PoInventoryRequestTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/macula-samples/stock/gbss")
public class InventoryRequestController {

	@Autowired
	private PoInventoryRequestTest poInventoryRequestTest;

	@RequestMapping("/t1/{pageIndex}/{pageSize}")
	public ResponseEntity<String> doInventoryRequest(@PathVariable("pageIndex") int page, @PathVariable("pageSize") int size) {
		poInventoryRequestTest.doInventoryRequest(page, size);
		return ResponseEntity.ok("OK");
	}
}

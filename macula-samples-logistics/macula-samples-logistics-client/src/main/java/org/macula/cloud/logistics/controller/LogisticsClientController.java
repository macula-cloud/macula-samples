package org.macula.cloud.logistics.controller;

import org.macula.cloud.logistics.service.LogisticsClientService;
import org.macula.cloud.logistics.vo.Division2RDCResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsClientController {

	@Autowired
	private LogisticsClientService clientService;

	@GetMapping("/api/logistics/address/{divisionId}")
	public ResponseEntity<Division2RDCResponse> getDivision2RDC(@PathVariable("divisionId") String divisionId) {
		return ResponseEntity.ok(clientService.getDivision2RDC(divisionId));
	}
}

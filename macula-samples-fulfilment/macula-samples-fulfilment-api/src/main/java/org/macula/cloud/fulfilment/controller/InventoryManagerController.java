package org.macula.cloud.fulfilment.controller;

import org.macula.cloud.fulfilment.domain.RdcStockStatus;
import org.macula.cloud.fulfilment.repository.RdcStockStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "库存中心数据服务", value = "库存中心数据服务")
@RestController
@RequestMapping("/api/macula-samples/stock")
public class InventoryManagerController {

	@Autowired
	private RdcStockStatusRepository rdcStockStatusRepository;

	@PostMapping(value = "/rdc_stock_status/insert")
	@ApiOperation(value = "增加RDC Status数据")
	public ResponseEntity<Boolean> insertRdcStockStatus(@RequestBody RdcStockStatus stockStatus) {
		rdcStockStatusRepository.save(stockStatus);
		return ResponseEntity.ok(true);
	}

}

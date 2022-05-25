package org.macula.cloud.fulfilment.feign;

import org.macula.cloud.fulfilment.gbss.domain.RdcStockStatus;
import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "macula-samples-stock-api", path = "/api/macula-samples/stock/center", fallbackFactory = InventoryCenterClientFallbackFactory.class)
public interface InventoryCenterClient {

	@PostMapping(value = "/status")
	ResponseEntity<InventoryResponse> inventoryStatus(@RequestBody InventoryRequest request);

	@PostMapping(value = "/reserve")
	ResponseEntity<InventoryResponse> inventoryReserve(@RequestBody InventoryRequest request);

	@PostMapping(value = "/commit")
	ResponseEntity<InventoryResponse> inventoryCommit(@RequestBody InventoryRequest request);

	@PostMapping(value = "/release")
	ResponseEntity<InventoryResponse> inventoryRelease(@RequestBody InventoryRequest request);

	@PostMapping(value = "/rdc_stock_status/insert")
	ResponseEntity<Boolean> insertRdcStockStatus(@RequestBody RdcStockStatus stockStatus);
}

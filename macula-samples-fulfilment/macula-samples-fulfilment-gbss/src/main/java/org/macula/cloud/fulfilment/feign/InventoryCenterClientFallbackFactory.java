package org.macula.cloud.fulfilment.feign;

import org.macula.cloud.fulfilment.gbss.domain.RdcStockStatus;
import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InventoryCenterClientFallbackFactory implements FallbackFactory<InventoryCenterClient> {

	@Override
	public InventoryCenterClient create(final Throwable cause) {

		return new InventoryCenterClient() {

			@Override
			public ResponseEntity<InventoryResponse> inventoryStatus(InventoryRequest request) {
				log.error("Call InventoryCenterClient.inventoryStatus error:", cause);
				return null;
			}

			@Override
			public ResponseEntity<InventoryResponse> inventoryReserve(InventoryRequest request) {
				log.error("Call InventoryCenterClient.inventoryReserve error:", cause);
				return null;
			}

			@Override
			public ResponseEntity<InventoryResponse> inventoryCommit(InventoryRequest request) {
				log.error("Call InventoryCenterClient.inventoryCommit error:", cause);
				return null;
			}

			@Override
			public ResponseEntity<InventoryResponse> inventoryRelease(InventoryRequest request) {
				return null;
			}

			@Override
			public ResponseEntity<Boolean> insertRdcStockStatus(RdcStockStatus stockStatus) {
				return ResponseEntity.status(500).body(false);
			}

		};
	}

}

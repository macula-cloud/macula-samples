package org.macula.cloud.fulfilment.gbss.service;

import java.util.List;

import org.macula.cloud.fulfilment.feign.InventoryCenterClient;
import org.macula.cloud.fulfilment.gbss.domain.PoAppDetail;
import org.macula.cloud.fulfilment.gbss.domain.PoAppMaster;
import org.macula.cloud.fulfilment.gbss.repository.PoAppDetailRepository;
import org.macula.cloud.fulfilment.gbss.repository.PoAppMasterRepository;
import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;
import org.macula.cloud.fulfilment.vo.ProductQuantity;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PoInventoryRequestTest {

	@Autowired
	private InventoryCenterClient inventoryClient;

	@Autowired
	private PoAppMasterRepository masterRepository;

	@Autowired
	private PoAppDetailRepository detailRepository;

	public void doInventoryRequest(int page, int size) {
		log.info("Scheduled PoAppMaster InventoryRequest invoke {} - {}", page, size);
		Pageable pageRequest = PageRequest.of(page, size);
		Page<PoAppMaster> poMasters = masterRepository.findAll(pageRequest);

		poMasters.forEach(m -> {
			List<PoAppDetail> details = detailRepository.findByPoAppNo(m.getPoAppNo());
			log.info("Should reserve: {}", JSON.toString(m));
			log.info("{}", JSON.toString(details));

			InventoryRequest inventoryRequest = new InventoryRequest();
			inventoryRequest.setAccDate("20200215");
			details.forEach(d -> {
				ProductQuantity pq = ProductQuantity.of(d.getProductCode(), m.getDeliveryWhCode(), m.getDeliveryWhLocCode(), d.getPoAppQty());
				inventoryRequest.addProductQuantity(pq);
			});
			ResponseEntity<InventoryResponse> response = inventoryClient.inventoryReserve(inventoryRequest);
			log.info("Response: {}", JSON.toString(response));
		});
	}

}

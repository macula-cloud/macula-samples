package org.macula.cloud.fulfilment.test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;

import org.macula.cloud.fulfilment.service.InventoryCenterService;
import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;
import org.macula.cloud.fulfilment.vo.ProductQuantity;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;

// @Component
public class StaticInventoryRequestTest {

	@Autowired
	private ExecutorService executors;

	@Autowired
	private InventoryCenterService inventoryService;

	public void testRequest() {
		for (int i = 0; i < 1000; i++) {

			executors.submit(new Runnable() {

				@Override
				public void run() {
					InventoryRequest inventoryRequest = new InventoryRequest();
					inventoryRequest.setAccDate("20200215");

					ProductQuantity pq1 = new ProductQuantity();
					pq1.setProductCode("18002-03");
					pq1.setLocCode("1001");
					pq1.setQuantity(new BigDecimal(2));
					pq1.setWhCode("HC03");

					ProductQuantity pq2 = new ProductQuantity();
					pq2.setProductCode("18013-02");
					pq2.setLocCode("1001");
					pq2.setQuantity(new BigDecimal(3));
					pq2.setWhCode("HC02");

					inventoryRequest.setDetails(Sets.newHashSet(pq1, pq2));

					InventoryResponse response = inventoryService.reserve(inventoryRequest);
					System.out.println(JSON.toJSONString(response));
					// redisInventoryService.mysql2Redis();

				}
			});
		}
	}
}

package org.macula.cloud.fulfilment.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "macula-samples-stock-api", path = "/api/macula-samples/stock/service", fallbackFactory = StockServiceClientFallbackFactory.class)
public interface StockServiceClient {

}

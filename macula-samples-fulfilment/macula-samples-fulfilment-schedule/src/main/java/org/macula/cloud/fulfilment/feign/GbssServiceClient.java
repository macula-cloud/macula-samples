package org.macula.cloud.fulfilment.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "macula-samples-stock-gbss", path = "/api/macula-samples/stock/gbss", fallbackFactory = GbssServiceClientFallbackFactory.class)
public interface GbssServiceClient {

}

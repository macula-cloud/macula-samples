package org.macula.cloud.logistics.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

// @Component
@Slf4j
public class StockServiceClientFallbackFactory implements FallbackFactory<StockServiceClient> {

	@Override
	public StockServiceClient create(Throwable cause) {
		log.error("Call StockServiceClient error:", cause);
		return new StockServiceClient() {
		};
	}

}

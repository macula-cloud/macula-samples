package org.macula.cloud.logistics.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

// @Component
@Slf4j
public class GbssServiceClientFallbackFactory implements FallbackFactory<GbssServiceClient> {

	@Override
	public GbssServiceClient create(Throwable cause) {
		log.error("Call GbssServiceClient error:", cause);
		return new GbssServiceClient() {
		};
	}

}

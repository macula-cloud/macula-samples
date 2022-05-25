package org.macula.cloud.fulfilment;

import org.macula.cloud.fulfilment.log.stream.StockEventStreamChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
@EnableBinding(StockEventStreamChannel.class)
public class FulfilmentLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulfilmentLogApplication.class, args);
	}
}
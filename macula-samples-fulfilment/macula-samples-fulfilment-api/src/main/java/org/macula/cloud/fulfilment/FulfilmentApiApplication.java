package org.macula.cloud.fulfilment;

import org.macula.cloud.fulfilment.stream.StockEventStreamChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
@EnableBinding(StockEventStreamChannel.class)
public class FulfilmentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulfilmentApiApplication.class, args);
	}
}

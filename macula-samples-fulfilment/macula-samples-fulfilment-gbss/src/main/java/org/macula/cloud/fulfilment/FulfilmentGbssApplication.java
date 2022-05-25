package org.macula.cloud.fulfilment;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
@EnableFeignClients
public class FulfilmentGbssApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulfilmentGbssApplication.class, args);
	}
}
package org.macula.cloud.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
@EnableFeignClients
public class LogisticsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsClientApplication.class, args);
	}
}

package org.macula.cloud.stock;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableAsync
@EnableFeignClients
@EnableScheduling
@EnableSecurityAccess
@EnableResourceServer
@SpringCloudApplication
public class StockScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockScheduleApplication.class, args);
	}
}
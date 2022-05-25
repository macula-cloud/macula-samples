package org.macula.cloud.stock;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableAsync
@EntityScan
@EnableScheduling
@EnableFeignClients
@EnableSecurityAccess
@EnableResourceServer
@SpringCloudApplication
public class StockAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockAdminApplication.class, args);
	}
}
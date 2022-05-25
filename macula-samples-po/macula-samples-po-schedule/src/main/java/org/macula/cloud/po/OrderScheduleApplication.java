package org.macula.cloud.po;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringCloudApplication
@EnableCaching
@EnableScheduling
@EnableFeignClients
@EnableJpaRepositories
@EntityScan
@EnableAsync
@EnableJpaAuditing
@EnableResourceServer
@EnableSecurityAccess
public class OrderScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderScheduleApplication.class, args);
	}
}
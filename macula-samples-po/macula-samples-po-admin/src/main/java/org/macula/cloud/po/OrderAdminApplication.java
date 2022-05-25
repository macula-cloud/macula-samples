package org.macula.cloud.po;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringCloudApplication
@EnableFeignClients
@EnableResourceServer
@EnableSecurityAccess
@EnableJpaRepositories
@EnableJpaAuditing
@EntityScan
public class OrderAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderAdminApplication.class, args);
	}
}
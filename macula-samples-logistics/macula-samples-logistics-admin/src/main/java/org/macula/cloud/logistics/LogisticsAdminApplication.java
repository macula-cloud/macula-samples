package org.macula.cloud.logistics;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableJpaRepositories
@EnableJpaAuditing
@EnableSecurityAccess
@SpringCloudApplication
public class LogisticsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsAdminApplication.class, args);
	}
}
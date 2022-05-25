package org.macula.cloud.logistics;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
@EnableFeignClients
@EnableSecurityAccess
public class LogisticsScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsScheduleApplication.class, args);
	}
}
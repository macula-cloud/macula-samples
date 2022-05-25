package org.macula.cloud.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
public class LogisticsLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsLogApplication.class, args);
	}
}
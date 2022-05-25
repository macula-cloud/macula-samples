package org.macula.cloud.po;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringCloudApplication
@EnableJpaRepositories
@EntityScan(basePackages = "org.macula.cloud.po")
public class OrderGbssApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderGbssApplication.class, args);
	}
}
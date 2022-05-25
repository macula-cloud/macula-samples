package org.macula.cloud.stock;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.macula.cloud.stock.stream.StockChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@EnableJpaAuditing
@EnableSecurityAccess
@EnableJpaRepositories
@EnableResourceServer
@SpringCloudApplication
@EnableBinding(StockChannel.class)
public class StockLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockLogApplication.class, args);
	}
}

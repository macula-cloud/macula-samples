package org.macula.cloud.stock;

import org.macula.cloud.security.access.EnableSecurityAccess;
import org.macula.cloud.stock.stream.StockChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

@EnableAsync
@EnableScheduling
@EnableSecurityAccess
@EnableResourceServer
@SpringCloudApplication
@EnableBinding(StockChannel.class)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class })
public class StockApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockApiApplication.class, args);
	}
}

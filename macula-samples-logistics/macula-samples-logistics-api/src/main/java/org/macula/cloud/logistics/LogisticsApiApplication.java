package org.macula.cloud.logistics;

import org.macula.cloud.logistics.stream.LogisticsEventStreamChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
@EnableBinding(LogisticsEventStreamChannel.class)
public class LogisticsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsApiApplication.class, args);
	}
}

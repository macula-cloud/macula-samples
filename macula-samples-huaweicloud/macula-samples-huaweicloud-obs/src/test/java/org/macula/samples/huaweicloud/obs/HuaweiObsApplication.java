package org.macula.samples.huaweicloud.obs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HuaweiObsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HuaweiObsApplication.class, args);
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}
}

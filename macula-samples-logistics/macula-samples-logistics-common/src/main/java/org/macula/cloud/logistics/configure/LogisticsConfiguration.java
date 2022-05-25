package org.macula.cloud.logistics.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ LogisticsConfig.class })
public class LogisticsConfiguration {

}

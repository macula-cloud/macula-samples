package org.macula.cloud.po.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@EnableConfigurationProperties({ OMSConfig.class, MessageConfig.class })
public class OMSConfiguration {

}

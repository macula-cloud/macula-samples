package org.macula.samples.huaweicloud.obs.configure;

import javax.annotation.PostConstruct;

import com.obs.services.ObsClient;
import lombok.extern.slf4j.Slf4j;
import org.macula.samples.huaweicloud.obs.webdav.WebdavClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties({
		ObsConfig.class,
		WebdavConfig.class })
public class ObsAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Huaweicloud OBS Plugin] Auto Configure.");
	}

	@Bean
	@ConditionalOnMissingBean
	public ObsClient obsClientBean(ObsConfig config) {
		log.trace("[Macula] |- Bean [Huaweicloud ObsClient] Auto Configure.");
		return new ObsClient(config.getAk(), config.getSk(), config);
	}

	@Bean
	@ConditionalOnMissingBean
	public WebdavClient webdavClientBean(WebdavConfig config) {
		log.trace("[Macula] |- Bean [WebdavClient] Auto Configure.");
		return new WebdavClient(config.getEndpoint(), config.getUsername(), config.getPassword());
	}
}

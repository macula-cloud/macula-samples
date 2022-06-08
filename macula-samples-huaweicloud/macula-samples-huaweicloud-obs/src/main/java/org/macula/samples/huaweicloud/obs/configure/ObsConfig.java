package org.macula.samples.huaweicloud.obs.configure;

import com.obs.services.ObsConfiguration;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("huaweicloud.obs")
public class ObsConfig extends ObsConfiguration {

	@Getter
	@Setter
	private String ak;

	@Getter
	@Setter
	private String sk;

	@Getter
	@Setter
	private String bucket;
}

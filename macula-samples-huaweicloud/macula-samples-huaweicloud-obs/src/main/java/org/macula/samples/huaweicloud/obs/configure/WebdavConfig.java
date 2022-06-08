package org.macula.samples.huaweicloud.obs.configure;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("huaweicloud.webdav")
public class WebdavConfig {

	private String username;
	private String password;
	private String endpoint;

}

package org.macula.cloud.po.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "macula.cloud.sms")
public class MessageConfig {
	/**
	 * url
	 */
	private String url;
	/**
	 * 登入名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
}

package org.macula.cloud.logistics.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public abstract class LogisticsRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 请求创建时间
	 */
	private long timestamp = System.currentTimeMillis();

	/**
	 * 请求地址时间(不允许为空)
	 */
	private Date accDate = new Date();

}

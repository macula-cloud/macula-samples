package org.macula.cloud.logistics.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class LogisticsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 执行返回代码
	 */
	private String code;

	/**
	 * 执行返回信息
	 */
	private String message;

	/**
	 * 执行返回时间
	 */
	private long timestamp = System.currentTimeMillis();

	/**
	 * 原始请求
	 */
	private LogisticsRequest requset;
}

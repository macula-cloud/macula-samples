package org.macula.cloud.fulfilment.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class InventoryResponse implements Serializable {

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
	 * 原库存请求
	 */
	private InventoryRequest request;

	public static InventoryResponse success(InventoryRequest request) {
		InventoryResponse response = new InventoryResponse();
		response.setRequest(request);
		response.setCode("0");
		return response;
	}

	public static InventoryResponse failed(String code, String message, InventoryRequest request) {
		InventoryResponse response = new InventoryResponse();
		response.setRequest(request);
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

}

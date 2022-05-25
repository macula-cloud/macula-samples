package org.macula.cloud.fulfilment.event;

import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;

/**
 * 订单库存锁定事件。
 * 该事件会在锁定成功后产生。
 */
public class InventoryReserveEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private InventoryRequest request;

	private InventoryResponse response;

	/**
	 * @return the request
	 */
	public InventoryRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(InventoryRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public InventoryResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(InventoryResponse response) {
		this.response = response;
	}
}

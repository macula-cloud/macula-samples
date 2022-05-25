package org.macula.cloud.logistics.event;

import org.macula.cloud.logistics.vo.AddressResponse;
import org.macula.cloud.logistics.vo.LogisticsRequest;

/**
 * 订单库存锁定事件。
 * 该事件会在锁定成功后产生。
 */
public class InventoryReserveEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private LogisticsRequest request;

	private AddressResponse response;

	/**
	 * @return the request
	 */
	public LogisticsRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(LogisticsRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public AddressResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(AddressResponse response) {
		this.response = response;
	}
}

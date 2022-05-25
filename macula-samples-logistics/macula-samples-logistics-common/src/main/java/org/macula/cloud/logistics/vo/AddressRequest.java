package org.macula.cloud.logistics.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressRequest extends LogisticsRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 配送地址，不允许为空
	 */
	private String divisionId;

	/**
	 * 请求下层层次
	 */
	private int level = 1;

}

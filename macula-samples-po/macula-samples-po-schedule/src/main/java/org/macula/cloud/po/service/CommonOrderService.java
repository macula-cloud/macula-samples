package org.macula.cloud.po.service;

import java.util.Arrays;
import java.util.List;

public class CommonOrderService {

	/** 服务中心销售订单类型 */
	public static final List<String> POS_PUR_ORDER_TYPES = Arrays.asList("G301", "G302", "G303", "G304", "G311", "G310");
	/** 服务中心退货订单 */
	public static final List<String> POS_RETURN_ORDER_TYPES = Arrays.asList("G305");

	public Boolean isPosPurchaseOrder(String poProcessCode) {
		return POS_PUR_ORDER_TYPES.contains(poProcessCode);
	}

	public Boolean isPosReturnOrder(String poProcessCode) {
		return POS_RETURN_ORDER_TYPES.contains(poProcessCode);
	}
}

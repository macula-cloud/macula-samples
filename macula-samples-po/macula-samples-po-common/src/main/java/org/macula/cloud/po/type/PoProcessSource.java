package org.macula.cloud.po.type;

import java.util.Arrays;
import java.util.List;

public class PoProcessSource {
	/** 来自MQ的订单处理 */
	public final static String PO_SOURCE_MQ = "MQ";
	/** 来自UPL的订单处理 */
	public final static String PO_SOURCE_UPL = "V1";
	/** 来自UPL2的订单处理 */
	public final static String PO_SOURCE_UPL2 = "V2";
	/** 来自对账 */
	public static final String PO_SOURCE_CHECK = "BL";

	/** 最终更新的类型	 */
	private static final List<String> VALID_PO_SOURCES = Arrays.asList(PO_SOURCE_UPL, PO_SOURCE_UPL2);

	public static boolean shouldUpdate(String status) {
		return status != null && VALID_PO_SOURCES.contains(status);
	}
}

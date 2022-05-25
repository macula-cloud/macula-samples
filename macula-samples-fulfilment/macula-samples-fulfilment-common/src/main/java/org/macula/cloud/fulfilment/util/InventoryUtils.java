package org.macula.cloud.fulfilment.util;

import java.util.Date;

import cn.hutool.core.date.DateUtil;

public class InventoryUtils {

	private static final String KEY_PREFIX = "RDC";

	public static String getStockRedisKey(Date accDate, String productCode, String whCode, String locCode) {
		String day = DateUtil.format(accDate, "yyyyMMdd");
		return getStockRedisKey(day, productCode, whCode, locCode);
	}

	public static String getStockRedisKey(String day, String productCode, String whCode, String locCode) {
		return String.format("%s%s(%s):%s:%s", KEY_PREFIX, day, locCode, productCode, whCode);
	}
}

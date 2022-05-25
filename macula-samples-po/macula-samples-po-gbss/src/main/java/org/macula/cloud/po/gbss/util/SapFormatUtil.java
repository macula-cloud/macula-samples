/**
 * 
 */
package org.macula.cloud.po.gbss.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * <b>SapFormatUtil</b> 是提供给SAP传送时格式化数据，由于这两个类不是线程安全的，加了同步控制
 * </p>
 *
 
 
 
 *
 */
public class SapFormatUtil {

	private static DecimalFormat numFormat = new DecimalFormat("###.##");

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	static {
		numFormat.setGroupingUsed(false);
	}

	public synchronized static String formatNumber(BigDecimal num) {
		return numFormat.format(num);
	}

	public synchronized static String formatDate(Date date) {
		return formatter.format(date);
	}

	public static String subByteLen(String s, int length) throws Exception {
		if (s == null || "".equals("")) {
			return "";
		}
		byte[] bytes = s.getBytes("Unicode");
		int n = 0, i = 2;
		for (; i < bytes.length && n < length; i++) {
			if (i % 2 == 1) {
				n++;
			} else {
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		if (i % 2 == 1) {
			if (bytes[i - 1] != 0) {
				i = i - 1;
			} else {
				i = i + 1;
			}
		}
		return new String(bytes, 0, i, "Unicode");
	}

}

package org.macula.cloud.po.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OmsUtils {

	/**
	 * 按分隔符分解订单号，对于U单主动拆分成A和B
	 */
	public static List<String> getOrderNoList(String body) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isNotEmpty(body)) {
			String[] arrays = body.split(",| |\n|\r|\t|，|　");
			for (String n : arrays) {
				n = StringUtils.trim(n);
				if (StringUtils.isNotBlank(n)) {
					if (n.endsWith("U")) {
						n = n.substring(0, n.length() - 1);
						result.add(n + "A");
						result.add(n + "B");
					} else {
						result.add(n);
					}

				}
			}
		}
		return result;
	}
}

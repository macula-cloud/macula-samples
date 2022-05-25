/**
 * 
 */
package org.macula.cloud.po.gbss.type;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * <b>TypeProcessCode</b> 交易系统类型配置常量
 * </p>
 * 
 
 
 
 * 
 */
public class TypeProcessCode {
	/** 个人普通优惠购货 */
	public final static String PREFERENTIAL_PURCHASE = "01";
	/** 个人推荐 */
	public final static String BUSINESS_TYPE_PERSONRECOMMEND = "02";
	/** 零售购货 */
	public final static String RETAIL_PURCHASE = "10";
	/** 五分钟支付时间限制 */
	public final static int ORDER_LIMIT_TIME = 5 * 60 * 1000;
	/** 产品类型：产品(P) */
	public final static String PRODUCT_TYPE_P = "P";
	/** 产品类型：辅销品(M) */
	public final static String PRODUCT_TYPE_M = "M";
	/** 流程代码 */
	public final static String APP_PROCESS_CODE = "G001";
	/** RDC库区代码 */
	public final static String APP_WHLOC_CODE = "1001";
	/** SHOP: 专卖店配送 */
	public final static String APP_DELIVERY_TYPE_SHOP = "08";
	/** POS: 服务中心配送 */
	public final static String APP_DELIVERY_TYPE_POS = "09";
	/** JP:家居配送 */
	public final static String APP_DELIVERY_TYPE_JP = "10";
	/** 申请优惠消费者消费价格必须大于498元 */
	public final static double PERSONRECOMMEND_PRICE_LIMIT = 498;
	/**订单管理中分页的页数*/
	public final static int PAGENUM = 10;

	public final static Map<String, String> produceTypeMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("00", "其他");
			put("01", "无限极保健食品系列");
			put("02", "植雅个人护理品系列");
			put("04", "帮得佳家居用品系列");
			put("V1", "维雅抗自由基保湿系列");
			put("V2", "维雅抗自由基保湿系列(升级)");
			put("V3", "维雅抗自由基滋润系列");
			put("V4", "维雅特殊护理系列");
		}
	};

	/**
	 * 根据类型获取类型名称
	 * @param value
	 * @return
	 */
	public static String getProductSubTypeName(String value) {
		if (StringUtils.isNotEmpty(value)) {
			String mapvalue = produceTypeMap.get(value);
			if (mapvalue != null) {
				return mapvalue;
			} else {
				return produceTypeMap.get("00");
			}
		}
		return null;
	}

	/**
	 * 	//得到三个月前得时间
	 */

	public static Date beforeThree() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONDAY);
		int year = calendar.get(Calendar.YEAR);
		int date = calendar.get(Calendar.DATE);
		if (month - 3 > 1)
			calendar.set(year, month - 3, date);
		else
			calendar.set(year - 1, 12 + month - 3, date);
		return calendar.getTime();

	}

	/**
	 * MD5加密
	 * @param arg0
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5String(String arg0) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(arg0.getBytes());
		byte b[] = md5.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

}

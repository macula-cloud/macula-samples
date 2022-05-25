/**
 * 
 */
package org.macula.cloud.po.type;

/**
 * TypeSapOrderType:sap订单类型
 
 
 
 */
public class TypeSapOrderType {

	/**
	 * 总公司普通单
	 */
	public static final int ZNF3 = 0;

	/**
	 * 总公司预定／记欠单
	 */
	public static final int ZYD1 = 1;

	/**
	 * 总公司免费单
	 */
	public static final int ZFRE = 2;

	/**
	 * 服务中心普通单
	 */
	public static final int ZNF4 = 3;

	/**
	 * 服务中心免费单
	 */
	public static final int ZGFR = 5;
	/**
	 * 服务中心退货单
	 */
	public static final int ZRE2 = 6;

	// 2012-11-15 add by yx start
	/**
	 * 总部 月刊单
	 */
	public static final int ZNF6 = 7;
	// 2012-11-15 add by yx end 

	/**
	 * G306退货单
	 */
	public static final int ZRE7 = 8;

	/**
	 * G308服务中心退货(店铺销售)
	 */
	public static final int ZRE8 = 9;

	/**
	 * G501专卖店退货，因怡瑞召回使用的
	 */
	public static final int ZREA = 10;
	/**
	 * G012纷享花绘订单
	 */
	public static final int ZRE1 = 11;//add by zhuohr
	/**
	 * G904质量补损
	 */
	public static final int ZFD2 = 12;//add by kevin.wang

	/**
	 * G905一般补损退货
	 */
	public static final int ZRE4 = 13;

	/**
	 * G908无参照质量补损退货
	 */
	public static final int ZRE6 = 14;

	/**
	 * G018全球购
	 */
	public static final int ZNF8 = 15;
}

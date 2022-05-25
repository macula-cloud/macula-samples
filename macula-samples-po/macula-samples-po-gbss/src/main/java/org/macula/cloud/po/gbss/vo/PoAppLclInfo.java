/**
 * PoAppLclInfo.java 2011-7-28
 */
package org.macula.cloud.po.gbss.vo;

import java.math.BigDecimal;

/**
 * <p>
 * <b>PoAppLclInfo</b> 是辅单产品列表
 * </p>
 *
 
 
 
 *
 */
public class PoAppLclInfo {

	/**
	 * 产品代码
	 */
	private String productCode;

	/**
	 * 申请购买数量
	 */
	private int poAppQty;
	/**
	 * 可供购买数量
	 */
	private int poSupplyQty;
	/**
	 * 产品名称
	 */
	private String productFullName;
	/**
	 * 类型
	 */
	private String productSubType;
	/**
	 * RDC地址
	 */
	private String whCode;
	/**
	 * 正式订单号与辅单订单号
	 */
	private String poAppNo;
	/**
	 * 订单类别 1 正式订单 2 辅单
	 */
	private String orderType;

	/**
	 * 53月刊
	 */
	private String poLclType;

	public PoAppLclInfo() {
	}

	/**
	 * @param productCode
	 * @param poAppQty
	 * @param poSupplyQty
	 * @param productFullName
	 * @param productSubType
	 * @param whCode
	 * @param poAppNo
	 * @param orderType
	 */
	public PoAppLclInfo(String productCode, BigDecimal poAppQty, String productFullName, String productSubType, String whCode, String poAppNo,
			String orderType) {
		super();
		this.productCode = productCode;
		if (poAppQty != null) {
			this.poAppQty = poAppQty.intValue();
		}
		this.productFullName = productFullName;
		this.productSubType = productSubType;
		this.whCode = whCode;
		this.poAppNo = poAppNo;
		this.orderType = orderType;
	}

	public PoAppLclInfo(String productCode, BigDecimal poAppQty, String productFullName, String productSubType, String whCode, String poAppNo,
			String orderType, String poLclType) {
		super();
		this.productCode = productCode;
		if (poAppQty != null) {
			this.poAppQty = poAppQty.intValue();
		}
		this.productFullName = productFullName;
		this.productSubType = productSubType;
		this.whCode = whCode;
		this.poAppNo = poAppNo;
		this.orderType = orderType;
		this.poLclType = poLclType;
	}

	public PoAppLclInfo(String productCode, BigDecimal poAppQty, BigDecimal poSupplyQty, String productFullName, String productSubType, String whCode,
			String poAppNo, String orderType) {
		super();
		this.productCode = productCode;
		if (poAppQty != null) {
			this.poAppQty = poAppQty.intValue();
		}
		if (poSupplyQty != null) {
			this.poSupplyQty = poSupplyQty.intValue();
		}
		this.productFullName = productFullName;
		this.productSubType = productSubType;
		this.whCode = whCode;
		this.poAppNo = poAppNo;
		this.orderType = orderType;
	}

	public PoAppLclInfo(String productCode, BigDecimal poAppQty, BigDecimal poSupplyQty, String productFullName, String productSubType, String whCode,
			String poAppNo, String orderType, String poLclType) {
		super();
		this.productCode = productCode;
		if (poAppQty != null) {
			this.poAppQty = poAppQty.intValue();
		}
		if (poSupplyQty != null) {
			this.poSupplyQty = poSupplyQty.intValue();
		}
		this.productFullName = productFullName;
		this.productSubType = productSubType;
		this.whCode = whCode;
		this.poAppNo = poAppNo;
		this.orderType = orderType;
		this.poLclType = poLclType;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the poAppQty
	 */
	public int getPoAppQty() {
		return poAppQty;
	}

	/**
	 * @param poAppQty the poAppQty to set
	 */
	public void setPoAppQty(int poAppQty) {
		this.poAppQty = poAppQty;
	}

	/**
	 * @return the productFullName
	 */
	public String getProductFullName() {
		return productFullName;
	}

	/**
	 * @param productFullName the productFullName to set
	 */
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}

	/**
	 * @return the poAppNo
	 */
	public String getPoAppNo() {
		return poAppNo;
	}

	/**
	 * @param poAppNo the poAppNo to set
	 */
	public void setPoAppNo(String poAppNo) {
		this.poAppNo = poAppNo;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the poSupplyQty
	 */
	public int getPoSupplyQty() {
		return poSupplyQty;
	}

	/**
	 * @param poSupplyQty the poSupplyQty to set
	 */
	public void setPoSupplyQty(int poSupplyQty) {
		this.poSupplyQty = poSupplyQty;
	}

	/**
	 * @return the productSubType
	 */
	public String getProductSubType() {
		return productSubType;
	}

	/**
	 * @param productSubType the productSubType to set
	 */
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	/**
	 * @return the whCode
	 */
	public String getWhCode() {
		return whCode;
	}

	/**
	 * @param whCode the whCode to set
	 */
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getPoLclType() {
		return poLclType;
	}

	public void setPoLclType(String poLclType) {
		this.poLclType = poLclType;
	}

}

package org.macula.cloud.po.gbss.sap;

/**
 * <p>
 * <b>PlantTranDetail</b>是Dn单类(非数据库类)
 * </p>
 * NOTE: 从旧系统DsServer移植过来 by xgd 2012-05-25
 * 
 
 
 */

public class PlantDnDetail {

	/** 交货单单号 */
	private String dnNo;

	/** 行号 */
	// int -> String, modify by xgd 2012-5-29
	private String lineNo;

	/** 主项目的lineNo */
	private int refLineNo;

	/** 产品代码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 产品类别 */
	private String productType;

	/** 产品单位 */
	private String unitMeasure;

	/** 库区代码 */
	private String locCode;

	/** 产品批号 */
	private String lotNo;

	/** 申请数量*发货数量 */
	private double dnQty;

	/** 交易数量 *实际收数量*/
	private double tranQty;

	/** 备注 */
	private String comments;

	/**
	 * @return Returns the dnNo.
	 */
	public String getDnNo() {
		return dnNo;
	}

	/**
	 * @param dnNo The dnNo to set.
	 */
	public void setDnNo(String dnNo) {
		this.dnNo = dnNo;
	}

	/**
	 * @return Returns the lineNo.
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * @param lineNo The lineNo to set.
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * @return Returns the productCode.
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode The productCode to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return Returns the refLineNo.
	 */
	public int getRefLineNo() {
		return refLineNo;
	}

	/**
	 * @param refLineNo The refLineNo to set.
	 */
	public void setRefLineNo(int refLineNo) {
		this.refLineNo = refLineNo;
	}

	/**
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return Returns the productType.
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType The productType to set.
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return Returns the unitMeasure.
	 */
	public String getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * @param unitMeasure The unitMeasure to set.
	 */
	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	/**
	 * @return Returns the locCode.
	 */
	public String getLocCode() {
		return locCode;
	}

	/**
	 * @param locCode The locCode to set.
	 */
	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	/**
	 * @return Returns the lotNo.
	 */
	public String getLotNo() {
		return lotNo;
	}

	/**
	 * @param lotNo The lotNo to set.
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	/**
	 * @return Returns the dnQty.
	 */
	public double getDnQty() {
		return dnQty;
	}

	/**
	 * @param dnQty The dnQty to set.
	 */
	public void setDnQty(double dnQty) {
		this.dnQty = dnQty;
	}

	/**
	 * @return Returns the tranQty.
	 */
	public double getTranQty() {
		return tranQty;
	}

	/**
	 * @param tranQty The tranQty to set.
	 */
	public void setTranQty(double tranQty) {
		this.tranQty = tranQty;
	}

	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}

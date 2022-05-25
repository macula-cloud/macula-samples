package org.macula.cloud.po.gbss.sap;

/**
 * <p>
 * <b>PlantStock</b>是服务中心库存操作调用SAP接口 参数VO类

 * </p>
 * 
 
 
 
 */
public class PlantStock {
	/** 产品代码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 店代码 */
	private String whCode;

	/** 库存地代码 */
	private String locCode;

	/** 库存地名称 */
	private String locName;

	/** 批次 */
	private String lotNo;

	/** 单位 */
	private String unitMeasure;

	/** 非限制使用的估价的库存 */
	private int thisQty;

	/** 在运库存 (从一库存地到另一库存地) */
	private int cumlm;

	/** 质量检验中的库存 */
	private int cinsm;

	/** 全部限制批次的总计库存 */
	private int ceinm;

	/** 冻结的库存 */
	private int cspem;

	/** 收货数量或需求数量 */
	private int wkbst;

	/** 物料类型 */
	private String mtart;

	/** 物料类型描述 */
	private String mtbez;

	/** 物料组 */
	private String matkl;

	/** 物料组描述 */
	private String wgbez;

	/** 生产日期 */
	private String hsdat;

	/** 到期日期 */
	private String vfdat;

	/** 剩余天数 */
	private String remainDays;

	/**
	 * @return the ceinm.
	 */
	public int getCeinm() {
		return ceinm;
	}

	/**
	 * @param ceinm 
	 *					the ceinm to set.
	 */
	public void setCeinm(int ceinm) {
		this.ceinm = ceinm;
	}

	/**
	 * @return the cinsm.
	 */
	public int getCinsm() {
		return cinsm;
	}

	/**
	 * @param cinsm 
	 *					the cinsm to set.
	 */
	public void setCinsm(int cinsm) {
		this.cinsm = cinsm;
	}

	/**
	 * @return the cspem.
	 */
	public int getCspem() {
		return cspem;
	}

	/**
	 * @param cspem 
	 *					the cspem to set.
	 */
	public void setCspem(int cspem) {
		this.cspem = cspem;
	}

	/**
	 * @return the cumlm.
	 */
	public int getCumlm() {
		return cumlm;
	}

	/**
	 * @param cumlm 
	 *					the cumlm to set.
	 */
	public void setCumlm(int cumlm) {
		this.cumlm = cumlm;
	}

	/**
	 * @return the locCode.
	 */
	public String getLocCode() {
		return locCode;
	}

	/**
	 * @param locCode 
	 *					the locCode to set.
	 */
	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	/**
	 * @return the lotNo.
	 */
	public String getLotNo() {
		return lotNo;
	}

	/**
	 * @param lotNo 
	 *					the lotNo to set.
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	/**
	 * @return the matkl.
	 */
	public String getMatkl() {
		return matkl;
	}

	/**
	 * @param matkl 
	 *					the matkl to set.
	 */
	public void setMatkl(String matkl) {
		this.matkl = matkl;
	}

	/**
	 * @return the mtart.
	 */
	public String getMtart() {
		return mtart;
	}

	/**
	 * @param mtart 
	 *					the mtart to set.
	 */
	public void setMtart(String mtart) {
		this.mtart = mtart;
	}

	/**
	 * @return the productCode.
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode 
	 *					the productCode to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the thisQty.
	 */
	public int getThisQty() {
		return thisQty;
	}

	/**
	 * @param thisQty 
	 *					the thisQty to set.
	 */
	public void setThisQty(int thisQty) {
		this.thisQty = thisQty;
	}

	/**
	 * @return the unitMeasure.
	 */
	public String getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * @param unitMeasure 
	 *					the unitMeasure to set.
	 */
	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	/**
	 * @return the whCode.
	 */
	public String getWhCode() {
		return whCode;
	}

	/**
	 * @param whCode 
	 *					the whCode to set.
	 */
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	/**
	 * @return the locName.
	 */
	public String getLocName() {
		return locName;
	}

	/**
	 * @param locName 
	 *					the locName to set.
	 */
	public void setLocName(String locName) {
		this.locName = locName;
	}

	/**
	 * @return the mtbez.
	 */
	public String getMtbez() {
		return mtbez;
	}

	/**
	 * @param mtbez 
	 *					the mtbez to set.
	 */
	public void setMtbez(String mtbez) {
		this.mtbez = mtbez;
	}

	/**
	 * @return the productName.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName 
	 *					the productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the wgbez.
	 */
	public String getWgbez() {
		return wgbez;
	}

	/**
	 * @param wgbez 
	 *					the wgbez to set.
	 */
	public void setWgbez(String wgbez) {
		this.wgbez = wgbez;
	}

	/**
	 * @return the wkbst.
	 */
	public int getWkbst() {
		return wkbst;
	}

	/**
	 * @param wkbst 
	 *					the wkbst to set.
	 */
	public void setWkbst(int wkbst) {
		this.wkbst = wkbst;
	}

	/**
	 * @return the hsdat.
	 */
	public String getHsdat() {
		if (hsdat.equals("0000-00-00")) {
			hsdat = "";
		}
		return hsdat;
	}

	/**
	 * @param hsdat 
	 *					the hsdat to set.
	 */
	public void setHsdat(String hsdat) {
		this.hsdat = hsdat;
	}

	/**
	 * @return the remainDays.
	 */
	public String getRemainDays() {
		if (remainDays.equals("0")) {
			remainDays = "";
		}
		return remainDays;
	}

	/**
	 * @param remainDays 
	 *					the remainDays to set.
	 */
	public void setRemainDays(String remainDays) {
		this.remainDays = remainDays;
	}

	/**
	 * @return the vfdat.
	 */
	public String getVfdat() {
		if (vfdat.equals("0000-00-00")) {
			vfdat = "";
		}
		return vfdat;
	}

	/**
	 * @param vfdat 
	 *					the vfdat to set.
	 */
	public void setVfdat(String vfdat) {
		this.vfdat = vfdat;
	}
}

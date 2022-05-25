/**
 * 
 */
package org.macula.cloud.po.gbss.sap;

/**
 * <p>
 * <b>TOrderItems</b> 传入订单明细
 * </p>
 *
 
 
 
 *
 */

public class TOrderItems {

	/**
	 * 销售订单行项目
	 */
	private String itmNumber;

	/**
	 * 项目类型
	 */
	private String itemCateg;

	/**
	 * 物料编码
	 */
	private String material;

	/**
	 * 订购数量
	 */
	private String targetQty;

	/**
	 * 成交价
	 */
	private String kwertPr00;

	/**
	 *  工厂编码
	 */
	private String plant;

	/**
	 * 仓码
	 */
	private String storeLoc;

	/**
	 * 备注
	 */
	private String beizhu;

	/**
	 * 交货日期
	 */
	private String reqDate;

	/**
	 * 上层项目号
	 */
	private String hgLvItem;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * @return the itmNumber
	 */
	public String getItmNumber() {
		return itmNumber;
	}

	/**
	 * @param itmNumber the itmNumber to set
	 */
	public void setItmNumber(String itmNumber) {
		this.itmNumber = itmNumber;
	}

	/**
	 * @return the itemCateg
	 */
	public String getItemCateg() {
		return itemCateg;
	}

	/**
	 * @param itemCateg the itemCateg to set
	 */
	public void setItemCateg(String itemCateg) {
		this.itemCateg = itemCateg;
	}

	/**
	 * @return the material
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * @return the plant
	 */
	public String getPlant() {
		return plant;
	}

	/**
	 * @param plant the plant to set
	 */
	public void setPlant(String plant) {
		this.plant = plant;
	}

	/**
	 * @return the storeLoc
	 */
	public String getStoreLoc() {
		return storeLoc;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	/**
	 * @param storeLoc the storeLoc to set
	 */
	public void setStoreLoc(String storeLoc) {
		this.storeLoc = storeLoc;
	}

	/**
	 * @return the hgLvItem
	 */
	public String getHgLvItem() {
		return hgLvItem;
	}

	/**
	 * @param hgLvItem the hgLvItem to set
	 */
	public void setHgLvItem(String hgLvItem) {
		this.hgLvItem = hgLvItem;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the targetQty
	 */
	public String getTargetQty() {
		return targetQty;
	}

	/**
	 * @param targetQty
	 *            the targetQty to set
	 */
	public void setTargetQty(String targetQty) {
		this.targetQty = targetQty;
	}

	/**
	 * @return the kwertPr00
	 */
	public String getKwertPr00() {
		return kwertPr00;
	}

	/**
	 * @param kwertPr00
	 *            the kwertPr00 to set
	 */
	public void setKwertPr00(String kwertPr00) {
		this.kwertPr00 = kwertPr00;
	}

	/**
	 * @return the reqDate
	 */
	public String getReqDate() {
		return reqDate;
	}

	/**
	 * @param reqDate
	 *            the reqDate to set
	 */
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

}

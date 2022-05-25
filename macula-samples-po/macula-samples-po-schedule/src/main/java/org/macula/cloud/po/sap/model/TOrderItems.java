/**
 * 
 */
package org.macula.cloud.po.sap.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 * <p>
 * <b>TOrderItems</b> 传入订单明细
 * </p>
 *
 
 
 
 *
 */
@BapiStructure
public class TOrderItems {

	/**
	 * 销售订单行项目
	 */
	@Parameter("ITM_NUMBER")
	private String itmNumber;

	/**
	 * 项目类型
	 */
	@Parameter("ITEM_CATEG")
	private String itemCateg;

	/**
	 * 物料编码
	 */
	@Parameter("MATERIAL")
	private String material;

	/**
	 * 订购数量
	 */
	@Parameter("TARGET_QTY")
	private BigDecimal targetQty;

	/**
	 * 成交价
	 */
	@Parameter("KWERT_PR00")
	private BigDecimal kwertPr00;

	/**
	 *  工厂编码
	 */
	@Parameter("PLANT")
	private String plant;

	/**
	 * 仓码
	 */
	@Parameter("STORE_LOC")
	private String storeLoc;

	/**
	 * 交货日期
	 */
	@Parameter("REQ_DATE")
	private Date reqDate;

	/**
	 * 上层项目号
	 */
	@Parameter("HG_LV_ITEM")
	private String hgLvItem;

	/**
	 * 币种
	 */
	@Parameter("CURRENCY")
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

	public BigDecimal getTargetQty() {
		return targetQty;
	}

	public void setTargetQty(BigDecimal targetQty) {
		this.targetQty = targetQty;
	}

	/**
	 * @return the kwertPr00
	 */
	public BigDecimal getKwertPr00() {
		return kwertPr00;
	}

	/**
	 * @param kwertPr00
	 *            the kwertPr00 to set
	 */
	public void setKwertPr00(BigDecimal kwertPr00) {
		this.kwertPr00 = kwertPr00;
	}

	/**
	 * @return the reqDate
	 */
	public Date getReqDate() {
		return reqDate;
	}

	/**
	 * @param reqDate
	 *            the reqDate to set
	 */
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

}

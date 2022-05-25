package org.macula.cloud.fulfilment.gbss.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PO_APP_DETAIL")
public class PoAppDetail extends AbstractAuditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7528971144580984355L;

	/**序号*/
	@Id
	@SequenceGenerator(name = "PO_APP_DETAIL_ID_GENERATOR", sequenceName = "SEQ_PO_APP_DETAIL_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PO_APP_DETAIL_ID_GENERATOR")
	private Long id;

	/**
	 * 订货申请单号
	 */
	@Column(name = "PO_APP_NO")
	private String poAppNo;

	/**
	 * 本地行号
	 */
	@Column(name = "LINE_NO")
	private int lineNo;

	/**
	 * 产品代码
	 */
	@Column(name = "PRODUCT_CODE")
	private String productCode;

	/**
	 * 产品属性
	 */
	@Column(name = "PRODUCT_ATTR")
	private String productAttr;

	/**
	 * 申请购买数量
	 */
	@Column(name = "PO_APP_QTY")
	private BigDecimal poAppQty;

	/**
	 * 申请购买价格
	 */
	@Column(name = "PO_APP_PRICE")
	private BigDecimal poAppPrice;

	/**
	 * 申请购买点数
	 */
	@Column(name = "PO_APP_POINT")
	private BigDecimal poAppPoint;

	/**
	 * 可供购买数量
	 */
	@Column(name = "PO_SUPPLY_QTY")
	private BigDecimal poSupplyQty;

	/**
	 * 可供购买价格
	 */
	@Column(name = "PO_SUPPLY_PRICE")
	private BigDecimal poSupplyPrice;

	/**
	 * 可供购买点数
	 */
	@Column(name = "PO_SUPPLY_POINT")
	private BigDecimal poSupplyPoint;

	/**
	 * 促销代码
	 */
	@Column(name = "PROM_CODE")
	private String promCode;

	/**
	 * 促销产品属性
	 */
	@Column(name = "PROM_PRODUCT_ATTR")
	private String promProductAttr;

	/**
	 * 产品原价格
	 */
	@Column(name = "PRODUCT_PRICE")
	private BigDecimal productPrice;

	/**
	 * 产品原点数
	 */
	@Column(name = "PRODUCT_POINT")
	private BigDecimal productPoint;

	/**
	 * 产品BOM代码
	 */
	@Column(name = "PRODUCT_BOM_CODE")
	private String productBomCode;

	/**
	 * 是否界面录入
	 */
	@Column(name = "IS_MANUAL_INPUT")
	private boolean isManualInput;

	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 产品类型
	 */
	@Column(name = "PRODUCT_TYPE")
	private String productType;

	/**
	 * 是否延迟收货产品
	 */
	@Column(name = "IS_OWE")
	private boolean isOwe;

	/**
	 * 延迟收货单号
	 */
	@Column(name = "PO_OWE_NO")
	private String poOweNo;

	/**
	 * 延迟收货活动编号
	 */
	@Column(name = "OWE_CONFIG_NO")
	private String oweConfigNo;

	/**
	 * 预计到仓日期
	 */
	@Column(name = "ESTIMATE_ARRIVAL_DATE")
	private String estimateArrivalDate;

	/**
	 * 单位产品重量
	 */
	@Column(name = "PRODUCT_UNIT_WEIGHT")
	private BigDecimal productUnitWeight;

	/**
	 * 是否直销产品
	 */
	@Column(name = "IS_DIRECT_SALE")
	private boolean isDirectSale;

	/**
	 * 产品增值税率
	 */
	@Column(name = "VAT_RATE")
	private BigDecimal vatRate;

	/**
	 * 产品消费税率
	 */
	@Column(name = "CONSUM_TAX_RATE")
	private BigDecimal consumTaxRate;

	/**
	 * 产品综合税率
	 */
	@Column(name = "COMPRE_TAX_RATE")
	private BigDecimal compreTaxRate;

	/**
	 * 产品运费总金额
	 */
	@Column(name = "TRANSPORT_AMT")
	private BigDecimal transportAmt;

	/**
	 * 产品增值税金额
	 */
	@Column(name = "VAT_AMT")
	private BigDecimal vatAmt;

	/**
	 * 产品消费税金额
	 */
	@Column(name = "CONSUM_TAX_AMT")
	private BigDecimal consumTaxAmt;

	/**
	 * 产品综合税金额
	 */
	@Column(name = "COMPRE_TAX_AMT")
	private BigDecimal compreTaxAmt;

	/**
	 * 活动优惠价格
	 */
	@Column(name = "PROM_PRICE")
	private BigDecimal promPrice;

	/**
	 * 活动优惠点数
	 */
	@Column(name = "PROM_POINT")
	private BigDecimal promPoint;

	/**
	 * 活动优惠数量
	 */
	@Column(name = "PROM_QTY")
	private BigDecimal promQty;

	/**
	 * 活动单位优差
	 */
	@Column(name = "PROM_REBATE")
	private BigDecimal promRebate;

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Persistable#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
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
	 * @return the lineNo
	 */
	public int getLineNo() {
		return lineNo;
	}

	/**
	 * @param lineNo the lineNo to set
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
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
	 * @return the productAttr
	 */
	public String getProductAttr() {
		return productAttr;
	}

	/**
	 * @param productAttr the productAttr to set
	 */
	public void setProductAttr(String productAttr) {
		this.productAttr = productAttr;
	}

	/**
	 * @return the poAppQty
	 */
	public BigDecimal getPoAppQty() {
		return poAppQty;
	}

	/**
	 * @param poAppQty the poAppQty to set
	 */
	public void setPoAppQty(BigDecimal poAppQty) {
		this.poAppQty = poAppQty;
	}

	/**
	 * @return the poAppPrice
	 */
	public BigDecimal getPoAppPrice() {
		return poAppPrice;
	}

	/**
	 * @param poAppPrice the poAppPrice to set
	 */
	public void setPoAppPrice(BigDecimal poAppPrice) {
		this.poAppPrice = poAppPrice;
	}

	/**
	 * @return the poAppPoint
	 */
	public BigDecimal getPoAppPoint() {
		return poAppPoint;
	}

	/**
	 * @param poAppPoint the poAppPoint to set
	 */
	public void setPoAppPoint(BigDecimal poAppPoint) {
		this.poAppPoint = poAppPoint;
	}

	/**
	 * @return the poSupplyQty
	 */
	public BigDecimal getPoSupplyQty() {
		return poSupplyQty;
	}

	/**
	 * @param poSupplyQty the poSupplyQty to set
	 */
	public void setPoSupplyQty(BigDecimal poSupplyQty) {
		this.poSupplyQty = poSupplyQty;
	}

	/**
	 * @return the poSupplyPrice
	 */
	public BigDecimal getPoSupplyPrice() {
		return poSupplyPrice;
	}

	/**
	 * @param poSupplyPrice the poSupplyPrice to set
	 */
	public void setPoSupplyPrice(BigDecimal poSupplyPrice) {
		this.poSupplyPrice = poSupplyPrice;
	}

	/**
	 * @return the poSupplyPoint
	 */
	public BigDecimal getPoSupplyPoint() {
		return poSupplyPoint;
	}

	/**
	 * @param poSupplyPoint the poSupplyPoint to set
	 */
	public void setPoSupplyPoint(BigDecimal poSupplyPoint) {
		this.poSupplyPoint = poSupplyPoint;
	}

	/**
	 * @return the promCode
	 */
	public String getPromCode() {
		return promCode;
	}

	/**
	 * @param promCode the promCode to set
	 */
	public void setPromCode(String promCode) {
		this.promCode = promCode;
	}

	/**
	 * @return the promProductAttr
	 */
	public String getPromProductAttr() {
		return promProductAttr;
	}

	/**
	 * @param promProductAttr the promProductAttr to set
	 */
	public void setPromProductAttr(String promProductAttr) {
		this.promProductAttr = promProductAttr;
	}

	/**
	 * @return the productPrice
	 */
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * @return the productPoint
	 */
	public BigDecimal getProductPoint() {
		return productPoint;
	}

	/**
	 * @param productPoint the productPoint to set
	 */
	public void setProductPoint(BigDecimal productPoint) {
		this.productPoint = productPoint;
	}

	/**
	 * @return the productBomCode
	 */
	public String getProductBomCode() {
		return productBomCode;
	}

	/**
	 * @param productBomCode the productBomCode to set
	 */
	public void setProductBomCode(String productBomCode) {
		this.productBomCode = productBomCode;
	}

	/**
	 * @return the isManualInput
	 */
	public boolean isManualInput() {
		return isManualInput;
	}

	/**
	 * @param isManualInput the isManualInput to set
	 */
	public void setManualInput(boolean isManualInput) {
		this.isManualInput = isManualInput;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the isOwe
	 */
	public boolean isOwe() {
		return isOwe;
	}

	/**
	 * @param isOwe the isOwe to set
	 */
	public void setOwe(boolean isOwe) {
		this.isOwe = isOwe;
	}

	/**
	 * @return the poOweNo
	 */
	public String getPoOweNo() {
		return poOweNo;
	}

	/**
	 * @param poOweNo the poOweNo to set
	 */
	public void setPoOweNo(String poOweNo) {
		this.poOweNo = poOweNo;
	}

	/**
	 * @return the oweConfigNo
	 */
	public String getOweConfigNo() {
		return oweConfigNo;
	}

	/**
	 * @param oweConfigNo the oweConfigNo to set
	 */
	public void setOweConfigNo(String oweConfigNo) {
		this.oweConfigNo = oweConfigNo;
	}

	/**
	 * 获取： 预计到仓日期
	 */
	public String getEstimateArrivalDate() {
		return estimateArrivalDate;
	}

	/**
	 * 设置： 预计到仓日期
	 */
	public void setEstimateArrivalDate(String estimateArrivalDate) {
		this.estimateArrivalDate = estimateArrivalDate;
	}

	/**
	 * 获取： 单位产品重量
	 */
	public BigDecimal getProductUnitWeight() {
		return productUnitWeight;
	}

	/**
	 * 设置： 单位产品重量
	 */
	public void setProductUnitWeight(BigDecimal productUnitWeight) {
		this.productUnitWeight = productUnitWeight;
	}

	/**
	 * 获取：是否直销产品
	 */
	public boolean isDirectSale() {
		return isDirectSale;
	}

	/**
	 * 设置：是否直销产品
	 */
	public void setDirectSale(boolean isDirectSale) {
		this.isDirectSale = isDirectSale;
	}

	/**
	 * 获取： 产品增值税率
	 */
	public BigDecimal getVatRate() {
		return vatRate;
	}

	/**
	 * 设置： 产品增值税率
	 */
	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	/**
	 * 获取： 产品消费税率
	 */
	public BigDecimal getConsumTaxRate() {
		return consumTaxRate;
	}

	/**
	 * 设置： 产品消费税率
	 */
	public void setConsumTaxRate(BigDecimal consumTaxRate) {
		this.consumTaxRate = consumTaxRate;
	}

	/**
	 * 获取： 产品综合税率
	 */
	public BigDecimal getCompreTaxRate() {
		return compreTaxRate;
	}

	/**
	 * 设置： 产品综合税率
	 */
	public void setCompreTaxRate(BigDecimal compreTaxRate) {
		this.compreTaxRate = compreTaxRate;
	}

	/**
	 * 获取： 产品运费总金额
	 */
	public BigDecimal getTransportAmt() {
		return transportAmt;
	}

	/**
	 * 设置： 产品运费总金额
	 */
	public void setTransportAmt(BigDecimal transportAmt) {
		this.transportAmt = transportAmt;
	}

	/**
	 * 获取： 产品增值税金额
	 */
	public BigDecimal getVatAmt() {
		return vatAmt;
	}

	/**
	 * 设置： 产品增值税金额
	 */
	public void setVatAmt(BigDecimal vatAmt) {
		this.vatAmt = vatAmt;
	}

	/**
	 * 获取： 产品消费税金额
	 */
	public BigDecimal getConsumTaxAmt() {
		return consumTaxAmt;
	}

	/**
	 * 设置： 产品消费税金额
	 */
	public void setConsumTaxAmt(BigDecimal consumTaxAmt) {
		this.consumTaxAmt = consumTaxAmt;
	}

	/**
	 * 获取： 产品综合税金额
	 */
	public BigDecimal getCompreTaxAmt() {
		return compreTaxAmt;
	}

	/**
	 * 设置： 产品综合税金额
	 */
	public void setCompreTaxAmt(BigDecimal compreTaxAmt) {
		this.compreTaxAmt = compreTaxAmt;
	}

	/**
	 * 获取：活动优惠价格
	 */
	public BigDecimal getPromPrice() {
		return promPrice;
	}

	/**
	 * 设置：活动优惠价格
	 */
	public void setPromPrice(BigDecimal promPrice) {
		this.promPrice = promPrice;
	}

	/**
	 * 获取：活动优惠点数
	 */
	public BigDecimal getPromPoint() {
		return promPoint;
	}

	/**
	 * 设置：活动优惠点数
	 */
	public void setPromPoint(BigDecimal promPoint) {
		this.promPoint = promPoint;
	}

	/**
	 * 获取：活动优惠数量
	 */
	public BigDecimal getPromQty() {
		return promQty;
	}

	/**
	 * 设置：活动优惠数量
	 */
	public void setPromQty(BigDecimal promQty) {
		this.promQty = promQty;
	}

	/**
	 * 获取：活动单位优差
	 */
	public BigDecimal getPromRebate() {
		return promRebate;
	}

	/**
	 * 设置：活动单位优差
	 */
	public void setPromRebate(BigDecimal promRebate) {
		this.promRebate = promRebate;
	}
}

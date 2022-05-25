package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 订货信息明细
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_DETAIL")
public class PoDetail extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	// 备注
	@Column(name = "COMMENTS")
	private String comments;

	// 行号
	@Column(name = "LINE_NO")
	private BigDecimal lineNo;

	// 销售单号
	@Column(name = "PO_NO")
	private String poNo;

	// 产品属性
	@Column(name = "PRODUCT_ATTR")
	private String productAttr;

	// 产品BOM代码
	@Column(name = "PRODUCT_BOM_CODE")
	private String productBomCode;

	// 产品代码
	@Column(name = "PRODUCT_CODE")
	private String productCode;

	// 产品原点数
	@Column(name = "PRODUCT_POINT")
	private BigDecimal productPoint;

	// 产品原优惠价
	@Column(name = "PRODUCT_PRICE")
	private BigDecimal productPrice;

	// 产品类型
	@Column(name = "PRODUCT_TYPE")
	private String productType;

	// 促销代码
	@Column(name = "PROM_CODE")
	private String promCode;

	// 促销产品属性
	@Column(name = "PROM_PRODUCT_ATTR")
	private String promProductAttr;

	// 购买点数
	@Column(name = "SALE_POINT")
	private BigDecimal salePoint;

	// 购买单价
	@Column(name = "SALE_PRICE")
	private BigDecimal salePrice;

	// 订货产品数量
	@Column(name = "SALE_QTY")
	private BigDecimal saleQty;

	// 购买优差
	@Column(name = "SALE_REBATE")
	private BigDecimal saleRebate;

	// 单位产品重量
	@Column(name = "PRODUCT_UNIT_WEIGHT")
	private BigDecimal productUnitWeight;

	// 是否直销产品
	@Column(name = "IS_DIRECT_SALE")
	private boolean isDirectSale;

	// 产品增值税率
	@Column(name = "VAT_RATE")
	private BigDecimal vatRate;

	// 产品消费税率
	@Column(name = "CONSUM_TAX_RATE")
	private BigDecimal consumTaxRate;

	// 产品综合税率
	@Column(name = "COMPRE_TAX_RATE")
	private BigDecimal compreTaxRate;

	// 产品运费总金额
	@Column(name = "TRANSPORT_AMT")
	private BigDecimal transportAmt;

	// 产品增值税金额
	@Column(name = "VAT_AMT")
	private BigDecimal vatAmt;

	// 产品消费税金额
	@Column(name = "CONSUM_TAX_AMT")
	private BigDecimal consumTaxAmt;

	// 产品综合税金额
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
}
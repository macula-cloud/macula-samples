package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * 订货折扣明细表
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_DETAIL_DISCOUNT")
public class PoDetailDiscount extends LegacyAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 175323589412888494L;

	/**
	 * 订货单号
	 */
	@Column(name = "PO_NO")
	private String poNo;

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
	 * 优差折扣金额（单品）
	 */
	@Column(name = "REBATE_DIS_AMT_PRODUCT")
	private BigDecimal rebateDisAmtProduct = BigDecimal.ZERO;

	/**
	 * 优差折扣金额（电子券分摊）
	 */
	@Column(name = "REBATE_DIS_AMT_COUPON")
	private BigDecimal rebateDisAmtCoupon = BigDecimal.ZERO;

	/**
	 * 优差折扣金额（整单减分摊）
	 */
	@Column(name = "REBATE_DIS_AMT_WHOLE")
	private BigDecimal rebateDisAmtWhole = BigDecimal.ZERO;

	/**
	 * 公司折扣金额（单品）
	 */
	@Column(name = "COMPANY_DIS_AMT_PRODUCT")
	private BigDecimal companyDisAmtProduct = BigDecimal.ZERO;

	/**
	 * 公司折扣金额（电子券分摊）
	 */
	@Column(name = "COMPANY_DIS_AMT_COUPON")
	private BigDecimal companyDisAmtCoupon = BigDecimal.ZERO;

	/**
	 * 公司折扣金额（整单减分摊）
	 */
	@Column(name = "COMPANY_DIS_AMT_WHOLE")
	private BigDecimal companyDisAmtWhole = BigDecimal.ZERO;

	@Column(name = "COMMENTS")
	private String comments;

}
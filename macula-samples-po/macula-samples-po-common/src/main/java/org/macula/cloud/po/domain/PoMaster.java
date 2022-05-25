package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 订货信息主表
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_MASTER")
public class PoMaster extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	// 购货人姓名
	@Column(name = "ORDER_DEALER_NAME")
	private String orderDealerName;

	// 购货人卡号
	@Column(name = "ORDER_DEALER_NO")
	private String orderDealerNo;

	// 购货类型
	@Column(name = "ORDER_TYPE")
	private String orderType;

	// 支付文件序号
	@Column(name = "PAYMENT_DOC_NO")
	private String paymentDocNo;

	// 支付备注
	@Column(name = "PAYMENT_MEMO")
	private String paymentMemo;

	// 付款状态
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	// 付款完成时间
	@Column(name = "PAYMENT_TIME")
	private Timestamp paymentTime;

	// 支付金额
	@Column(name = "PAYMENT_TOTAL_AMT")
	private BigDecimal paymentTotalAmt;

	// 销售订货申请单号
	@Column(name = "PO_APP_NO")
	private String poAppNo;

	// 销售分公司
	@Column(name = "PO_BRANCH_NO")
	private String poBranchNo;

	// 销售日期
	@Temporal(TemporalType.DATE)
	@Column(name = "PO_DATE")
	private Date poDate;

	// 销售单据输入用户
	@Column(name = "PO_ENTRY_BY")
	private String poEntryBy;

	// 销售办理来源
	@Column(name = "PO_ENTRY_CLASS")
	private String poEntryClass;

	// 销售办代办人
	@Column(name = "PO_ENTRY_DEALER_NO")
	private String poEntryDealerNo;

	// 销售单据输入备注
	@Column(name = "PO_ENTRY_MEMO")
	private String poEntryMemo;

	// 销售单据输入时间
	@Column(name = "PO_ENTRY_TIME")
	private Timestamp poEntryTime;

	// 团购单号
	@Column(name = "PO_GROUP_NO")
	private String poGroupNo;

	// 销售辅单申请单号
	@Column(name = "PO_LCL_NO")
	private String poLclNo;

	// 销售单号
	@Column(name = "PO_NO")
	private String poNo;

	// 记欠申请号
	@Column(name = "PO_OWE_NO")
	private String poOweNo;

	// 销售月份
	@Column(name = "PO_PERIOD")
	private String poPeriod;

	// 定价属性
	@Column(name = "PO_PRICE_ATTR")
	private String poPriceAttr;

	// 定价价格组
	@Column(name = "PO_PRICE_GROUP_TYPE")
	private String poPriceGroupType;

	// 订货流程代码
	@Column(name = "PO_PROCESS_CODE")
	private String poProcessCode;

	// 销售促销标示
	@Column(name = "PO_PROM_FLAG")
	private String poPromFlag;

	// 销售大区代号
	@Column(name = "PO_REGION_NO")
	private String poRegionNo;

	// 退货约束标志
	@Column(name = "PO_RETURN_RESTRICT_FLAG")
	private String poReturnRestrictFlag;

	// 销售单据状态
	@Column(name = "PO_STATUS")
	private String poStatus;

	// 销售店铺
	@Column(name = "PO_STORE_NO")
	private String poStoreNo;

	// 销售单类型
	@Column(name = "PO_TYPE")
	private String poType;

	// 整单促销代码
	@Column(name = "PO_WHOLE_PROM_CODE")
	private String poWholePromCode;

	// 参考源订货单日期
	@Temporal(TemporalType.DATE)
	@Column(name = "REF_PO_DATE")
	private Date refPoDate;

	// 参考源订货单号
	@Column(name = "REF_PO_NO")
	private String refPoNo;

	// 参考源订货单月份
	@Column(name = "REF_PO_PERIOD")
	private String refPoPeriod;

	// 参考单据号
	@Column(name = "REF_SELECTED_NO")
	private String refSelectedNo;

	// SAP记账日期
	@Temporal(TemporalType.DATE)
	@Column(name = "SAP_POSTING_DATE")
	private Date sapPostingDate;

	// SAP记账凭证
	@Column(name = "SAP_POSTING_DOC_NO")
	private String sapPostingDocNo;

	// SAP记账标志
	@Column(name = "SAP_POSTING_FLAG")
	private String sapPostingFlag;

	// 计分合计折扣点数
	@Column(name = "TOTAL_CALC_DISCOUNT_POINT")
	private BigDecimal totalCalcDiscountPoint;

	// 计分合计总点数
	@Column(name = "TOTAL_CALC_POINT")
	private BigDecimal totalCalcPoint;

	// 计分合计总优差
	@Column(name = "TOTAL_CALC_REBATE")
	private BigDecimal totalCalcRebate;

	// 整单订货总金额
	@Column(name = "TOTAL_SALE_AMT")
	private BigDecimal totalSaleAmt;

	// 整单使用优惠券
	@Column(name = "TOTAL_SALE_COUPON_AMT")
	private BigDecimal totalSaleCouponAmt;

	// 整单销售折让
	@Column(name = "TOTAL_SALE_DISCOUNT_AMT")
	private BigDecimal totalSaleDiscountAmt;

	// 整单订货净金额
	@Column(name = "TOTAL_SALE_NET_AMT")
	private BigDecimal totalSaleNetAmt;

	// 整单非产品金额
	@Column(name = "TOTAL_SALE_NON_PRODUCT_AMT")
	private BigDecimal totalSaleNonProductAmt;

	// 整单产品金额
	@Column(name = "TOTAL_SALE_PRODUCT_AMT")
	private BigDecimal totalSaleProductAmt;

	// 订单实际运费
	@Column(name = "TOTAL_TRANSPORT_AMT")
	private BigDecimal totalTransportAmt;

	/** 购货人对应的分公司  **/
	@Column(name = "ORDER_DEALER_BRANCH_NO")
	private String orderDealerBranchNo;

	/**
	 * 所属公司编码
	 */
	@Column(name = "COMPANY_NO")
	private String companyNo;

	/**
	 * 销售顾客号
	 */
	@Column(name = "ORDER_CUSTOMER_NO")
	private String orderCustomerNo;

	/**
	 * 订单总重量
	 */
	@Column(name = "TOTAL_WEIGHT")
	private BigDecimal totalWeight;

	/**
	 * 利润中心编号
	 */
	@Column(name = "PROFIT_CENTER_NO")
	private String profitCenterNo;

	/**
	 * 计分合计直销产品优差
	 */
	@Column(name = "TOTAL_CALC_REBATE_DS")
	private BigDecimal totalCalcRebateDs;

	/**
	 * 订单增值税总金额
	 */
	@Column(name = "TOTAL_VAT_AMT")
	private BigDecimal totalVatAmt;

	/**
	 * 订单消费税总金额
	 */
	@Column(name = "TOTAL_CONSUM_TAX_AMT")
	private BigDecimal totalConsumTaxAmt;

	/**
	 * 订单综合税总金额
	 */
	@Column(name = "TOTAL_COMPRE_TAX_AMT")
	private BigDecimal totalCompreTaxAmt;

	/**
	 * 总优差折让金额
	 */
	@Column(name = "TOTAL_REBATE_DIS_AMT")
	private BigDecimal totalRebateDisAmt;

	// 备注
	@Column(name = "COMMENTS")
	private String comments;

}
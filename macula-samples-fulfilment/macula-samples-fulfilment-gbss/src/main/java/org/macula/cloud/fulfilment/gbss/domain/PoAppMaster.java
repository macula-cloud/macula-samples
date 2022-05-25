package org.macula.cloud.fulfilment.gbss.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PO_APP_MASTER")
public class PoAppMaster extends AbstractAuditable<Long> {

	private static final long serialVersionUID = -2227698220939926151L;

	/**
	 * 序号
	 */
	@Id
	@SequenceGenerator(name = "PO_APP_MASTER_ID_GENERATOR", sequenceName = "SEQ_PO_APP_MASTER_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PO_APP_MASTER_ID_GENERATOR")
	private Long id;

	/**
	 * 订货申请单号
	 */
	@Column(name = "PO_APP_NO")
	private String poAppNo;

	/**
	 * 订单号
	 */
	@Column(name = "PO_NO")
	private String poNo;

	/**
	 * 购货人卡号
	 */
	@Column(name = "ORDER_DEALER_NO")
	private String orderDealerNo;

	/**
	 * 购货人姓名
	 */
	@Column(name = "ORDER_DEALER_NAME")
	private String orderDealerName;

	/**
	 * 购货人手机
	 */
	@Column(name = "ORDER_DEALER_MOBILE")
	private String orderDealerMobile;

	/**
	 * 销售店铺号
	 */
	@Column(name = "PO_STORE_NO")
	private String poStoreNo;

	/**
	 * 订单申请日期
	 */
	@Column(name = "PO_APP_DATE")
	private Date poAppDate;

	/**
	 * 申请单类型
	 */
	@Column(name = "PO_APP_TYPE")
	private String poAppType;

	/**
	 * 申请单状态
	 */
	@Column(name = "PO_APP_TRAN_STATUS")
	private String poAppTranStatus;

	/**
	 * 申请价格组
	 */
	@Column(name = "PO_APP_PRICE_GROUP_TYPE")
	private String poAppPriceGroupType;

	/**
	 * 申请定价属性
	 */
	@Column(name = "PO_APP_PRICE_ATTR")
	private String poAppPriceAttr;

	/**
	 * 申请推荐属性
	 */
	@Column(name = "PO_APP_CARD_ATTR")
	private String poAppCardAttr;

	/**
	 * 团购单号
	 */
	@Column(name = "PO_GROUP_NO")
	private String poGroupNo;

	/**
	 * 预定活动编号
	 */
	@Column(name = "PO_PRE_CONFIG_NO")
	private String poPreConfigNo;

	/**
	 * 预定活动转单单号
	 */
	@Column(name = "PO_PRE_TRAN_APP_NO")
	private String poPreTranAppNo;

	/**
	 * 流程代码
	 */
	@Column(name = "PO_PROCESS_CODE")
	private String poProcessCode;

	/**
	 * 销售促销表示
	 */
	@Column(name = "PO_PROM_FLAG")
	private String poPromFlag;

	/**
	 * 整单促销代码
	 */
	@Column(name = "PO_WHOLE_PROM_CODE")
	private String poWholePromCode;

	/**
	 * 订货总点数
	 */
	@Column(name = "PO_TOTAL_POINT")
	private BigDecimal poTotalPoint;

	/**
	 * 订货总金额
	 */
	@Column(name = "PO_TOTAL_AMT")
	private BigDecimal poTotalAmt;

	/**
	 * 订货产品金额
	 */
	@Column(name = "PO_PRODUCT_AMT")
	private BigDecimal poProductAmt;

	/**
	 * 订货非产品金额
	 */
	@Column(name = "PO_NON_PRODUCT_AMT")
	private BigDecimal poNonProductAmt;

	/**
	 * 订货运费总值
	 */
	@Column(name = "PO_TRANSPORT_AMT")
	private BigDecimal poTransportAmt;

	/**
	 * 销售办理来源
	 */
	@Column(name = "PO_ENTRY_CLASS")
	private String poEntryClass;

	/**
	 * 销售办代办人
	 */
	@Column(name = "PO_ENTRY_DEALER_NO")
	private String poEntryDealerNo;

	/**
	 * 销售单据备注
	 */
	@Column(name = "PO_ENTRY_MEMO")
	private String poEntryMemo;

	/**
	 * 是否已扣库存
	 */
	@Column(name = "HOLD_STOCK_STATUS")
	private String holdStockStatus;

	/**
	 * 扣库存的文件序号
	 */
	@Column(name = "HOLD_STOCK_DOC_NO")
	private String holdStockDocNo;

	/**
	 * 扣库存开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HOLD_STOCK_BEGIN_TIME")
	private Date holdStockBeginTime;

	/**
	 * 支付状态
	 */
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	/**
	 * 付款完成时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PAYMENT_TIME")
	private Date paymentTime;

	/**
	 * 支付文件序号
	 */
	@Column(name = "PAYMENT_DOC_NO")
	private String paymentDocNo;

	/**
	 * 电子付款交易方式
	 */
	@Column(name = "PAYMENT_EPAY_AGENT_CODE")
	private String paymentEpayAgentCode;

	/**
	 * 电子付款交易号
	 */
	@Column(name = "PAYMENT_EPAY_TRAN_NO")
	private String paymentEpayTranNo;

	/**
	 * 付款备注
	 */
	@Column(name = "PAYMENT_MEMO")
	private String paymentMemo;

	/**
	 * 特批状态
	 */
	@Column(name = "SPECIAL_STATUS")
	private String specialStatus;

	/**
	 * 特批时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SPECIAL_TIME")
	private Date specialTime;

	/**
	 * 特批人
	 */
	@Column(name = "SPECIAL_BY")
	private String specialBy;

	/**
	 * 配送RDC
	 */
	@Column(name = "DELIVERY_WH_CODE")
	private String deliveryWhCode;

	/**
	 * 配送RDC库区代码
	 */
	@Column(name = "DELIVERY_WH_LOC_CODE")
	private String deliveryWhLocCode;

	/**
	 * 计划配送日期
	 */
	@Column(name = "DELIVERY_DAYS")
	private int deliveryDays;

	/**
	 * 提货类型
	 */
	@Column(name = "DELIVERY_TYPE")
	private String deliveryType;

	/**
	 * 提货地点
	 */
	@Column(name = "DELIVERY_DEALER_NO")
	private String deliveryDealerNo;

	/**
	 * 收货地址ID
	 */
	@Column(name = "ADDR_SEND_ID")
	private String addrSendId;

	/**
	 * 收货地址区域ID
	 */
	@Column(name = "ADDR_AREA_CODE")
	private String addrAreaCode;

	/**
	 * 收货地址省
	 */
	@Column(name = "ADDR_PROVINCE")
	private String addrProvince;

	/**
	 * 收货地址市(县)
	 */
	@Column(name = "ADDR_CITY")
	private String addrCity;

	/**
	 * 收货地址区
	 */
	@Column(name = "ADDR_COUNTY")
	private String addrCounty;

	/**
	 * 收货地址镇
	 */
	@Column(name = "ADDR_DISTRICT")
	private String addrDistrict;

	/**
	 * 收货详细地址
	 */
	@Column(name = "ADDR_DETAIL")
	private String addrDetail;

	/**
	 * 收货人1姓名
	 */
	@Column(name = "R01_FULL_NAME")
	private String r01FullName;

	/**
	 * 收货人1身份证号
	 */
	@Column(name = "R01_CERTIFICATE_NO")
	private String r01CertificateNo;

	/**
	 * 收货人1电话
	 */
	@Column(name = "R01_TELES")
	private String r01Teles;

	/**
	 * 收货人2姓名
	 */
	@Column(name = "R02_FULL_NAME")
	private String r02FullName;

	/**
	 * 收货人2身份证号
	 */
	@Column(name = "R02_CERTIFICATE_NO")
	private String r02CertificateNo;

	/**
	 * 收货人2电话
	 */
	@Column(name = "R02_TELES")
	private String r02Teles;

	/**
	 * 收货人3姓名
	 */
	@Column(name = "R03_FULL_NAME")
	private String r03FullName;

	/**
	 * 收货人3身份证号
	 */
	@Column(name = "R03_CERTIFICATE_NO")
	private String r03CertificateNo;

	/**
	 * 收货人3电话
	 */
	@Column(name = "R03_TELES")
	private String r03Teles;

	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 总部申请处理状态
	 */
	@Column(name = "HQ_TRAN_STATUS")
	private String hqTranStatus;

	/**
	 * 总部申请复核时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HQ_CHECKED_TIME")
	private Date hqCheckedTime;

	/**
	 * 总部申请复核用户
	 */
	@Column(name = "HQ_CHECKED_BY")
	private String hqCheckedBy;

	/**
	 * 总部申请复核备注
	 */
	@Column(name = "HQ_CHECKED_MEMO")
	private String hqCheckedMemo;

	/**
	 * 总部申请审核时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HQ_AUDITED_TIME")
	private Date hqAuditedTime;

	/**
	 * 总部申请审核用户
	 */
	@Column(name = "HQ_AUDITED_BY")
	private String hqAuditedBy;

	/**
	 * 总部申请审核备注
	 */
	@Column(name = "HQ_AUDITED_MEMO")
	private String hqAuditedMemo;

	/**
	 * 总部申请月份
	 */
	@Column(name = "HQ_SO_PERIOD")
	private String hqSoPeriod;

	/**
	 * 总部扣除运费标志
	 */
	@Column(name = "HQ_TRANSPORT_FLAG")
	private String hqTransportFlag;

	/**
	 * 订货申请日期
	 */
	@Column(name = "APP_PERIOD")
	private String appPeriod;

	/**
	 * 释放库存时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASE_STOCK_TIME")
	private Date releaseStockTime;

	/**
	 * 会员申请编号
	 */
	@Column(name = "DEALER_APP_NO")
	private String dealerAppNo;

	/**
	 * 订单概要
	 */
	@Column(name = "ORDER_DESC")
	private String orderDesc;

	/**
	 * 游客编号
	 */
	@Column(name = "VISITOR_NO")
	private String visitorNo;

	/**
	 * 游客姓名
	 */
	@Column(name = "VISITOR_NAME")
	private String visitorName;

	/**
	 * 游客手机
	 */
	@Column(name = "VISITOR_MOBILE")
	private String visitorMobile;

	/**
	 * 微信openId
	 */
	@Column(name = "WECHAT_OPEN_ID")
	private String wechatOpenId;

	/**
	 * 整单预订金
	 */
	@Transient
	private BigDecimal poPreDepositAmt = BigDecimal.ZERO;

	/**
	 * 原交易流水号
	 */
	@Transient
	private String refTranNo;

	/**
	 * 首次订货辅销品金额
	 */
	@Column(name = "PO_FST_NON_PRODUCT_AMT")
	private BigDecimal poFstNonProductAmt;

	/**
	 * 首次订货规则代码
	 */
	@Column(name = "PO_FST_RULE_CODE")
	private String poFstRuleCode;

	/**
	 * 所属公司编号
	 */
	@Column(name = "COMPANY_NO")
	private String companyNo;

	/**
	 * 订货申请合并单号
	 */
	@Column(name = "PO_APP_UNION_NO")
	private String poAppUnionNo;

	/**
	 * 订货申请参考单号
	 */
	@Column(name = "REF_PO_APP_NO")
	private String refPoAppNo;

	/**
	 * 普通顾客号
	 */
	@Column(name = "ORDER_CUSTOMER_NO")
	private String orderCustomerNo;

	/**
	 * 订单总重量
	 */
	@Column(name = "TOTAL_WEIGHT")
	private BigDecimal totalWeight;

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

	/**
	 * 整单销售折让
	 */
	@Column(name = "TOTAL_SALE_DISCOUNT_AMT")
	private BigDecimal totalSaleDiscountAmt;

	/**
	 * 订单产品总金额
	 */
	@Transient
	private BigDecimal poProductTotalAmt = BigDecimal.ZERO;

	/**
	 * 计分合计总优差
	 */
	@Transient
	private BigDecimal totalCalcRebate = BigDecimal.ZERO;

	/**
	 * 整单使用优惠券
	 */
	@Transient
	private BigDecimal totalSaleCouponAmt = BigDecimal.ZERO;
	/**
	 * 是否原路返回
	 */
	@Column(name = "IS_BACKTRACT")
	private boolean isBacktract;

	/**
	 * 获取：订单产品总金额
	 */
	public BigDecimal getPoProductTotalAmt() {
		return poProductTotalAmt;
	}

	/**
	 * 设置：订单产品总金额
	 */
	public void setPoProductTotalAmt(BigDecimal poProductTotalAmt) {
		this.poProductTotalAmt = poProductTotalAmt;
	}

	/**
	 * 获取：订货申请参考单号
	 */
	public String getRefPoAppNo() {
		return refPoAppNo;
	}

	/**
	 * 设置：订货申请参考单号
	 */
	public void setRefPoAppNo(String refPoAppNo) {
		this.refPoAppNo = refPoAppNo;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Persistable#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the poNo
	 */
	public String getPoNo() {
		return poNo;
	}

	/**
	 * @param poNo the poNo to set
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	/**
	 * @return the orderDealerNo
	 */
	public String getOrderDealerNo() {
		return orderDealerNo;
	}

	/**
	 * @param orderDealerNo the orderDealerNo to set
	 */
	public void setOrderDealerNo(String orderDealerNo) {
		this.orderDealerNo = orderDealerNo;
	}

	/**
	 * @return the poAppDate
	 */
	public Date getPoAppDate() {
		return poAppDate;
	}

	/**
	 * @param poAppDate the poAppDate to set
	 */
	public void setPoAppDate(Date poAppDate) {
		this.poAppDate = poAppDate;
	}

	/**
	 * @return the poAppType
	 */
	public String getPoAppType() {
		return poAppType;
	}

	/**
	 * @param poAppType the poAppType to set
	 */
	public void setPoAppType(String poAppType) {
		this.poAppType = poAppType;
	}

	/**
	 * @return the poAppTranStatus
	 */
	public String getPoAppTranStatus() {
		return poAppTranStatus;
	}

	/**
	 * @param poAppTranStatus the poAppTranStatus to set
	 */
	public void setPoAppTranStatus(String poAppTranStatus) {
		this.poAppTranStatus = poAppTranStatus;
	}

	/**
	 * @return the poAppPriceGroupType
	 */
	public String getPoAppPriceGroupType() {
		return poAppPriceGroupType;
	}

	/**
	 * @param poAppPriceGroupType the poAppPriceGroupType to set
	 */
	public void setPoAppPriceGroupType(String poAppPriceGroupType) {
		this.poAppPriceGroupType = poAppPriceGroupType;
	}

	/**
	 * @return the poAppPriceAttr
	 */
	public String getPoAppPriceAttr() {
		return poAppPriceAttr;
	}

	/**
	 * @param poAppPriceAttr the poAppPriceAttr to set
	 */
	public void setPoAppPriceAttr(String poAppPriceAttr) {
		this.poAppPriceAttr = poAppPriceAttr;
	}

	/**
	 * @return the poAppCardAttr
	 */
	public String getPoAppCardAttr() {
		return poAppCardAttr;
	}

	/**
	 * @param poAppCardAttr the poAppCardAttr to set
	 */
	public void setPoAppCardAttr(String poAppCardAttr) {
		this.poAppCardAttr = poAppCardAttr;
	}

	/**
	 * @return the poGroupNo
	 */
	public String getPoGroupNo() {
		return poGroupNo;
	}

	/**
	 * @param poGroupNo the poGroupNo to set
	 */
	public void setPoGroupNo(String poGroupNo) {
		this.poGroupNo = poGroupNo;
	}

	/**
	 * @return the poPreConfigNo
	 */
	public String getPoPreConfigNo() {
		return poPreConfigNo;
	}

	/**
	 * @param poPreConfigNo the poPreConfigNo to set
	 */
	public void setPoPreConfigNo(String poPreConfigNo) {
		this.poPreConfigNo = poPreConfigNo;
	}

	/**
	 * @return the poPreTranAppNo
	 */
	public String getPoPreTranAppNo() {
		return poPreTranAppNo;
	}

	/**
	 * @param poPreTranAppNo the poPreTranAppNo to set
	 */
	public void setPoPreTranAppNo(String poPreTranAppNo) {
		this.poPreTranAppNo = poPreTranAppNo;
	}

	/**
	 * @return the poProcessCode
	 */
	public String getPoProcessCode() {
		return poProcessCode;
	}

	/**
	 * @param poProcessCode the poProcessCode to set
	 */
	public void setPoProcessCode(String poProcessCode) {
		this.poProcessCode = poProcessCode;
	}

	/**
	 * @return the poPromFlag
	 */
	public String getPoPromFlag() {
		return poPromFlag;
	}

	/**
	 * @param poPromFlag the poPromFlag to set
	 */
	public void setPoPromFlag(String poPromFlag) {
		this.poPromFlag = poPromFlag;
	}

	/**
	 * @return the poWholePromCode
	 */
	public String getPoWholePromCode() {
		return poWholePromCode;
	}

	/**
	 * @param poWholePromCode the poWholePromCode to set
	 */
	public void setPoWholePromCode(String poWholePromCode) {
		this.poWholePromCode = poWholePromCode;
	}

	/**
	 * @return the poTotalPoint
	 */
	public BigDecimal getPoTotalPoint() {
		return poTotalPoint;
	}

	/**
	 * @param poTotalPoint the poTotalPoint to set
	 */
	public void setPoTotalPoint(BigDecimal poTotalPoint) {
		this.poTotalPoint = poTotalPoint;
	}

	/**
	 * @return the poTotalAmt
	 */
	public BigDecimal getPoTotalAmt() {
		return poTotalAmt;
	}

	/**
	 * @param poTotalAmt the poTotalAmt to set
	 */
	public void setPoTotalAmt(BigDecimal poTotalAmt) {
		this.poTotalAmt = poTotalAmt;
	}

	/**
	 * @return the poProductAmt
	 */
	public BigDecimal getPoProductAmt() {
		return poProductAmt;
	}

	/**
	 * @param poProductAmt the poProductAmt to set
	 */
	public void setPoProductAmt(BigDecimal poProductAmt) {
		this.poProductAmt = poProductAmt;
	}

	/**
	 * @return the poNonProductAmt
	 */
	public BigDecimal getPoNonProductAmt() {
		return poNonProductAmt;
	}

	/**
	 * @param poNonProductAmt the poNonProductAmt to set
	 */
	public void setPoNonProductAmt(BigDecimal poNonProductAmt) {
		this.poNonProductAmt = poNonProductAmt;
	}

	/**
	 * @return the poTransportAmt
	 */
	public BigDecimal getPoTransportAmt() {
		return poTransportAmt;
	}

	/**
	 * @param poTransportAmt the poTransportAmt to set
	 */
	public void setPoTransportAmt(BigDecimal poTransportAmt) {
		this.poTransportAmt = poTransportAmt;
	}

	/**
	 * @return the poEntryClass
	 */
	public String getPoEntryClass() {
		return poEntryClass;
	}

	/**
	 * @param poEntryClass the poEntryClass to set
	 */
	public void setPoEntryClass(String poEntryClass) {
		this.poEntryClass = poEntryClass;
	}

	/**
	 * @return the poEntryDealerNo
	 */
	public String getPoEntryDealerNo() {
		return poEntryDealerNo;
	}

	/**
	 * @param poEntryDealerNo the poEntryDealerNo to set
	 */
	public void setPoEntryDealerNo(String poEntryDealerNo) {
		this.poEntryDealerNo = poEntryDealerNo;
	}

	/**
	 * @return the poEntryMemo
	 */
	public String getPoEntryMemo() {
		return poEntryMemo;
	}

	/**
	 * @param poEntryMemo the poEntryMemo to set
	 */
	public void setPoEntryMemo(String poEntryMemo) {
		this.poEntryMemo = poEntryMemo;
	}

	/**
	 * @return the holdStockStatus
	 */
	public String getHoldStockStatus() {
		return holdStockStatus;
	}

	/**
	 * @param holdStockStatus the holdStockStatus to set
	 */
	public void setHoldStockStatus(String holdStockStatus) {
		this.holdStockStatus = holdStockStatus;
	}

	/**
	 * @return the holdStockDocNo
	 */
	public String getHoldStockDocNo() {
		return holdStockDocNo;
	}

	/**
	 * @param holdStockDocNo the holdStockDocNo to set
	 */
	public void setHoldStockDocNo(String holdStockDocNo) {
		this.holdStockDocNo = holdStockDocNo;
	}

	/**
	 * @return the holdStockBeginTime
	 */
	public Date getHoldStockBeginTime() {
		return holdStockBeginTime;
	}

	/**
	 * @param holdStockBeginTime the holdStockBeginTime to set
	 */
	public void setHoldStockBeginTime(Date holdStockBeginTime) {
		this.holdStockBeginTime = holdStockBeginTime;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentTime
	 */
	public Date getPaymentTime() {
		return paymentTime;
	}

	/**
	 * @param paymentTime the paymentTime to set
	 */
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	/**
	 * @return the paymentDocNo
	 */
	public String getPaymentDocNo() {
		return paymentDocNo;
	}

	/**
	 * @param paymentDocNo the paymentDocNo to set
	 */
	public void setPaymentDocNo(String paymentDocNo) {
		this.paymentDocNo = paymentDocNo;
	}

	/**
	 * @return the paymentEpayAgentCode
	 */
	public String getPaymentEpayAgentCode() {
		return paymentEpayAgentCode;
	}

	/**
	 * @param paymentEpayAgentCode the paymentEpayAgentCode to set
	 */
	public void setPaymentEpayAgentCode(String paymentEpayAgentCode) {
		this.paymentEpayAgentCode = paymentEpayAgentCode;
	}

	/**
	 * @return the paymentEpayTranNo
	 */
	public String getPaymentEpayTranNo() {
		return paymentEpayTranNo;
	}

	/**
	 * @param paymentEpayTranNo the paymentEpayTranNo to set
	 */
	public void setPaymentEpayTranNo(String paymentEpayTranNo) {
		this.paymentEpayTranNo = paymentEpayTranNo;
	}

	/**
	 * @return the paymentMemo
	 */
	public String getPaymentMemo() {
		return paymentMemo;
	}

	/**
	 * @param paymentMemo the paymentMemo to set
	 */
	public void setPaymentMemo(String paymentMemo) {
		this.paymentMemo = paymentMemo;
	}

	/**
	 * @return the specialStatus
	 */
	public String getSpecialStatus() {
		return specialStatus;
	}

	/**
	 * @param specialStatus the specialStatus to set
	 */
	public void setSpecialStatus(String specialStatus) {
		this.specialStatus = specialStatus;
	}

	/**
	 * @return the specialTime
	 */
	public Date getSpecialTime() {
		return specialTime;
	}

	/**
	 * @param specialTime the specialTime to set
	 */
	public void setSpecialTime(Date specialTime) {
		this.specialTime = specialTime;
	}

	/**
	 * @return the specialBy
	 */
	public String getSpecialBy() {
		return specialBy;
	}

	/**
	 * @param specialBy the specialBy to set
	 */
	public void setSpecialBy(String specialBy) {
		this.specialBy = specialBy;
	}

	/**
	 * @return the deliveryWhCode
	 */
	public String getDeliveryWhCode() {
		return deliveryWhCode;
	}

	/**
	 * @param deliveryWhCode the deliveryWhCode to set
	 */
	public void setDeliveryWhCode(String deliveryWhCode) {
		this.deliveryWhCode = deliveryWhCode;
	}

	/**
	 * @return the deliveryWhLocCode
	 */
	public String getDeliveryWhLocCode() {
		return deliveryWhLocCode;
	}

	/**
	 * @param deliveryWhLocCode the deliveryWhLocCode to set
	 */
	public void setDeliveryWhLocCode(String deliveryWhLocCode) {
		this.deliveryWhLocCode = deliveryWhLocCode;
	}

	/**
	 * @return the deliveryDays
	 */
	public int getDeliveryDays() {
		return deliveryDays;
	}

	/**
	 * @param deliveryDays the deliveryDays to set
	 */
	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the deliveryDealerNo
	 */
	public String getDeliveryDealerNo() {
		return deliveryDealerNo;
	}

	/**
	 * @param deliveryDealerNo the deliveryDealerNo to set
	 */
	public void setDeliveryDealerNo(String deliveryDealerNo) {
		this.deliveryDealerNo = deliveryDealerNo;
	}

	/**
	 * @return the addrSendId
	 */
	public String getAddrSendId() {
		return addrSendId;
	}

	/**
	 * @param addrSendId the addrSendId to set
	 */
	public void setAddrSendId(String addrSendId) {
		this.addrSendId = addrSendId;
	}

	/**
	 * @return the addrAreaCode
	 */
	public String getAddrAreaCode() {
		return addrAreaCode;
	}

	/**
	 * @param addrAreaCode the addrAreaCode to set
	 */
	public void setAddrAreaCode(String addrAreaCode) {
		this.addrAreaCode = addrAreaCode;
	}

	/**
	 * @return the addrProvince
	 */
	public String getAddrProvince() {
		return addrProvince;
	}

	/**
	 * @param addrProvince the addrProvince to set
	 */
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	/**
	 * @return the addrCity
	 */
	public String getAddrCity() {
		return addrCity;
	}

	/**
	 * @param addrCity the addrCity to set
	 */
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	/**
	 * @return the addrCounty
	 */
	public String getAddrCounty() {
		return addrCounty;
	}

	/**
	 * @param addrCounty the addrCounty to set
	 */
	public void setAddrCounty(String addrCounty) {
		this.addrCounty = addrCounty;
	}

	/**
	 * @return the addrDistrict
	 */
	public String getAddrDistrict() {
		return addrDistrict;
	}

	/**
	 * @param addrDistrict the addrDistrict to set
	 */
	public void setAddrDistrict(String addrDistrict) {
		this.addrDistrict = addrDistrict;
	}

	/**
	 * @return the addrDetail
	 */
	public String getAddrDetail() {
		return addrDetail;
	}

	/**
	 * @param addrDetail the addrDetail to set
	 */
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	/**
	 * @return the r01FullName
	 */
	public String getR01FullName() {
		return r01FullName;
	}

	/**
	 * @param r01FullName the r01FullName to set
	 */
	public void setR01FullName(String r01FullName) {
		this.r01FullName = r01FullName;
	}

	/**
	 * @return the r01CertificateNo
	 */
	public String getR01CertificateNo() {
		return r01CertificateNo;
	}

	/**
	 * @param r01CertificateNo the r01CertificateNo to set
	 */
	public void setR01CertificateNo(String r01CertificateNo) {
		this.r01CertificateNo = r01CertificateNo;
	}

	/**
	 * @return the r01Teles
	 */
	public String getR01Teles() {
		return r01Teles;
	}

	/**
	 * @param r01Teles the r01Teles to set
	 */
	public void setR01Teles(String r01Teles) {
		this.r01Teles = r01Teles;
	}

	/**
	 * @return the r02FullName
	 */
	public String getR02FullName() {
		return r02FullName;
	}

	/**
	 * @param r02FullName the r02FullName to set
	 */
	public void setR02FullName(String r02FullName) {
		this.r02FullName = r02FullName;
	}

	/**
	 * @return the r02CertificateNo
	 */
	public String getR02CertificateNo() {
		return r02CertificateNo;
	}

	/**
	 * @param r02CertificateNo the r02CertificateNo to set
	 */
	public void setR02CertificateNo(String r02CertificateNo) {
		this.r02CertificateNo = r02CertificateNo;
	}

	/**
	 * @return the r02Teles
	 */
	public String getR02Teles() {
		return r02Teles;
	}

	/**
	 * @param r02Teles the r02Teles to set
	 */
	public void setR02Teles(String r02Teles) {
		this.r02Teles = r02Teles;
	}

	/**
	 * @return the r03FullName
	 */
	public String getR03FullName() {
		return r03FullName;
	}

	/**
	 * @param r03FullName the r03FullName to set
	 */
	public void setR03FullName(String r03FullName) {
		this.r03FullName = r03FullName;
	}

	/**
	 * @return the r03CertificateNo
	 */
	public String getR03CertificateNo() {
		return r03CertificateNo;
	}

	/**
	 * @param r03CertificateNo the r03CertificateNo to set
	 */
	public void setR03CertificateNo(String r03CertificateNo) {
		this.r03CertificateNo = r03CertificateNo;
	}

	/**
	 * @return the r03Teles
	 */
	public String getR03Teles() {
		return r03Teles;
	}

	/**
	 * @param r03Teles the r03Teles to set
	 */
	public void setR03Teles(String r03Teles) {
		this.r03Teles = r03Teles;
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
	 * @return the hqTranStatus
	 */
	public String getHqTranStatus() {
		return hqTranStatus;
	}

	/**
	 * @param hqTranStatus the hqTranStatus to set
	 */
	public void setHqTranStatus(String hqTranStatus) {
		this.hqTranStatus = hqTranStatus;
	}

	/**
	 * @return the hqCheckedTime
	 */
	public Date getHqCheckedTime() {
		return hqCheckedTime;
	}

	/**
	 * @param hqCheckedTime the hqCheckedTime to set
	 */
	public void setHqCheckedTime(Date hqCheckedTime) {
		this.hqCheckedTime = hqCheckedTime;
	}

	/**
	 * @return the hqCheckedBy
	 */
	public String getHqCheckedBy() {
		return hqCheckedBy;
	}

	/**
	 * @param hqCheckedBy the hqCheckedBy to set
	 */
	public void setHqCheckedBy(String hqCheckedBy) {
		this.hqCheckedBy = hqCheckedBy;
	}

	/**
	 * @return the hqCheckedMemo
	 */
	public String getHqCheckedMemo() {
		return hqCheckedMemo;
	}

	/**
	 * @param hqCheckedMemo the hqCheckedMemo to set
	 */
	public void setHqCheckedMemo(String hqCheckedMemo) {
		this.hqCheckedMemo = hqCheckedMemo;
	}

	/**
	 * @return the hqAuditedTime
	 */
	public Date getHqAuditedTime() {
		return hqAuditedTime;
	}

	/**
	 * @param hqAuditedTime the hqAuditedTime to set
	 */
	public void setHqAuditedTime(Date hqAuditedTime) {
		this.hqAuditedTime = hqAuditedTime;
	}

	/**
	 * @return the hqAuditedBy
	 */
	public String getHqAuditedBy() {
		return hqAuditedBy;
	}

	/**
	 * @param hqAuditedBy the hqAuditedBy to set
	 */
	public void setHqAuditedBy(String hqAuditedBy) {
		this.hqAuditedBy = hqAuditedBy;
	}

	/**
	 * @return the hqAuditedMemo
	 */
	public String getHqAuditedMemo() {
		return hqAuditedMemo;
	}

	/**
	 * @param hqAuditedMemo the hqAuditedMemo to set
	 */
	public void setHqAuditedMemo(String hqAuditedMemo) {
		this.hqAuditedMemo = hqAuditedMemo;
	}

	/**
	 * @return the hqSoPeriod
	 */
	public String getHqSoPeriod() {
		return hqSoPeriod;
	}

	/**
	 * @param hqSoPeriod the hqSoPeriod to set
	 */
	public void setHqSoPeriod(String hqSoPeriod) {
		this.hqSoPeriod = hqSoPeriod;
	}

	/**
	 * @return the hqTransportFlag
	 */
	public String getHqTransportFlag() {
		return hqTransportFlag;
	}

	/**
	 * @param hqTransportFlag the hqTransportFlag to set
	 */
	public void setHqTransportFlag(String hqTransportFlag) {
		this.hqTransportFlag = hqTransportFlag;
	}

	/**
	 * @return the appPeriod
	 */
	public String getAppPeriod() {
		return appPeriod;
	}

	/**
	 * @param appPeriod the appPeriod to set
	 */
	public void setAppPeriod(String appPeriod) {
		this.appPeriod = appPeriod;
	}

	/**
	 * @return the releaseStockTime
	 */
	public Date getReleaseStockTime() {
		return releaseStockTime;
	}

	/**
	 * @param releaseStockTime the releaseStockTime to set
	 */
	public void setReleaseStockTime(Date releaseStockTime) {
		this.releaseStockTime = releaseStockTime;
	}

	/**
	 * @return the dealerAppNo
	 */
	public String getDealerAppNo() {
		return dealerAppNo;
	}

	/**
	 * @param dealerAppNo the dealerAppNo to set
	 */
	public void setDealerAppNo(String dealerAppNo) {
		this.dealerAppNo = dealerAppNo;
	}

	/**
	 * @return the poStoreNo
	 */
	public String getPoStoreNo() {
		return poStoreNo;
	}

	/**
	 * @param poStoreNo the poStoreNo to set
	 */
	public void setPoStoreNo(String poStoreNo) {
		this.poStoreNo = poStoreNo;
	}

	/**
	 * @return the poPreDepositAmt
	 */
	public BigDecimal getPoPreDepositAmt() {
		return poPreDepositAmt;
	}

	/**
	 * @param poPreDepositAmt the poPreDepositAmt to set
	 */
	public void setPoPreDepositAmt(BigDecimal poPreDepositAmt) {
		this.poPreDepositAmt = poPreDepositAmt;
	}

	/**
	 * @return the orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}

	/**
	 * @param orderDesc the orderDesc to set
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	/**
	 * @return the visitorNo
	 */
	public String getVisitorNo() {
		return visitorNo;
	}

	/**
	 * @param visitorNo the visitorNo to set
	 */
	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	/**
	 * @return the wechatOpenId
	 */
	public String getWechatOpenId() {
		return wechatOpenId;
	}

	/**
	 * @param wechatOpenId the wechatOpenId to set
	 */
	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}

	/**
	 * @return the orderDealerName
	 */
	public String getOrderDealerName() {
		return orderDealerName;
	}

	/**
	 * @param orderDealerName the orderDealerName to set
	 */
	public void setOrderDealerName(String orderDealerName) {
		this.orderDealerName = orderDealerName;
	}

	/**
	 * @return the orderDealerMobile
	 */
	public String getOrderDealerMobile() {
		return orderDealerMobile;
	}

	/**
	 * @param orderDealerMobile the orderDealerMobile to set
	 */
	public void setOrderDealerMobile(String orderDealerMobile) {
		this.orderDealerMobile = orderDealerMobile;
	}

	/**
	 * @return the visitorName
	 */
	public String getVisitorName() {
		return visitorName;
	}

	/**
	 * @param visitorName the visitorName to set
	 */
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	/**
	 * @return the visitorMobile
	 */
	public String getVisitorMobile() {
		return visitorMobile;
	}

	/**
	 * @param visitorMobile the visitorMobile to set
	 */
	public void setVisitorMobile(String visitorMobile) {
		this.visitorMobile = visitorMobile;
	}

	/**
	 * 获取：原交易流水号
	 */
	public String getRefTranNo() {
		return refTranNo;
	}

	/**
	 * 设置：原交易流水号
	 */
	public void setRefTranNo(String refTranNo) {
		this.refTranNo = refTranNo;
	}

	/**
	 * @return the poFstNonProductAmt
	 */
	public BigDecimal getPoFstNonProductAmt() {
		return poFstNonProductAmt;
	}

	/**
	 * @param poFstNonProductAmt the poFstNonProductAmt to set
	 */
	public void setPoFstNonProductAmt(BigDecimal poFstNonProductAmt) {
		this.poFstNonProductAmt = poFstNonProductAmt;
	}

	/**
	 * @return the poFstRuleCode
	 */
	public String getPoFstRuleCode() {
		return poFstRuleCode;
	}

	/**
	 * @param poFstRuleCode the poFstRuleCode to set
	 */
	public void setPoFstRuleCode(String poFstRuleCode) {
		this.poFstRuleCode = poFstRuleCode;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getPoAppUnionNo() {
		return poAppUnionNo;
	}

	public void setPoAppUnionNo(String poAppUnionNo) {
		this.poAppUnionNo = poAppUnionNo;
	}

	public String getOrderCustomerNo() {
		return orderCustomerNo;
	}

	public void setOrderCustomerNo(String orderCustomerNo) {
		this.orderCustomerNo = orderCustomerNo;
	}

	/**
	 * 获取： 订单总重量
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置： 订单总重量
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 获取： 是否原路返回
	 */
	public boolean isBacktract() {
		return isBacktract;
	}

	/**
	 * 设置： 是否原路返回
	 */
	public void setBacktract(boolean isBacktract) {
		this.isBacktract = isBacktract;
	}

	/**
	 * 获取： 订单增值税总金额
	 */
	public BigDecimal getTotalVatAmt() {
		return totalVatAmt;
	}

	/**
	 * 设置： 订单增值税总金额
	 */
	public void setTotalVatAmt(BigDecimal totalVatAmt) {
		this.totalVatAmt = totalVatAmt;
	}

	/**
	 * 获取： 订单消费税总金额
	 */
	public BigDecimal getTotalConsumTaxAmt() {
		return totalConsumTaxAmt;
	}

	/**
	 * 设置： 订单消费税总金额
	 */
	public void setTotalConsumTaxAmt(BigDecimal totalConsumTaxAmt) {
		this.totalConsumTaxAmt = totalConsumTaxAmt;
	}

	/**
	 * 获取： 订单综合税总金额
	 */
	public BigDecimal getTotalCompreTaxAmt() {
		return totalCompreTaxAmt;
	}

	/**
	 * 设置： 订单综合税总金额
	 */
	public void setTotalCompreTaxAmt(BigDecimal totalCompreTaxAmt) {
		this.totalCompreTaxAmt = totalCompreTaxAmt;
	}

	/**
	 * 获取：总优差折让金额
	 */
	public BigDecimal getTotalRebateDisAmt() {
		return totalRebateDisAmt;
	}

	/**
	 * 设置：总优差折让金额
	 */
	public void setTotalRebateDisAmt(BigDecimal totalRebateDisAmt) {
		this.totalRebateDisAmt = totalRebateDisAmt;
	}

	/**
	 * 获取：整单销售折让
	 */
	public BigDecimal getTotalSaleDiscountAmt() {
		return totalSaleDiscountAmt;
	}

	/**
	 * 设置：整单销售折让
	 */
	public void setTotalSaleDiscountAmt(BigDecimal totalSaleDiscountAmt) {
		this.totalSaleDiscountAmt = totalSaleDiscountAmt;
	}

	/**
	 * 获取：计分合计总优差
	 */
	public BigDecimal getTotalCalcRebate() {
		return totalCalcRebate;
	}

	/**
	 * 设置：计分合计总优差
	 */
	public void setTotalCalcRebate(BigDecimal totalCalcRebate) {
		this.totalCalcRebate = totalCalcRebate;
	}

	/**
	 * 获取：整单使用优惠券
	 */
	public BigDecimal getTotalSaleCouponAmt() {
		return totalSaleCouponAmt;
	}

	/**
	 * 设置：整单使用优惠券
	 */
	public void setTotalSaleCouponAmt(BigDecimal totalSaleCouponAmt) {
		this.totalSaleCouponAmt = totalSaleCouponAmt;
	}
}

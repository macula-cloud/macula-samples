package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * 订货发票信息表:从大平台起，订单信息附带发票信息
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_INVOICE_INFO")
public class PoInvoiceInfo extends LegacyAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订货申请单号
	 */
	@Column(name = "PO_APP_NO")
	private String poAppNo;

	/**
	 * 购货人卡号
	 */
	@Column(name = "DEALER_NO")
	private String dealerNo;

	/**
	 * 发票类型
	 */
	@Column(name = "INVOICE_TYPE")
	private String invoiceType;

	/**
	 * 发票抬头
	 */
	@Column(name = "INVOICE_TITLE")
	private String invoiceTitle;

	/**
	 * 收票人手机号
	 */
	@Column(name = "INVOICE_MOBILE")
	private String invoiceMobile;

	/**
	 * 收票人邮箱
	 */
	@Column(name = "INVOICE_EMAIL")
	private String invoiceEmail;

	/**
	 * 收票人邮箱
	 */
	@Column(name = "INVOICE_PHOTO_ID")
	private String invoicePhotoId;

	/**
	 * 纳税人识别号
	 */
	@Column(name = "TAX_PAYER_CODE")
	private String taxPayerCode;

	/**
	 * 收件人姓名
	 */
	@Column(name = "RECIPIENT_NAME")
	private String recipientName;

	/**
	 * 收件人地区编号
	 */
	@Column(name = "RECIPIENT_AREA_CODE")
	private String recipientAreaCode;

	/**
	 * 收件人详细地址
	 */
	@Column(name = "RECIPIENT_ADDR_TAIL")
	private String recipientAddrTail;

	/**
	 * 收件人联系电话
	 */
	@Column(name = "RECIPIENT_TELE")
	private String recipientTele;

	/**
	 * 发票地址
	 */
	@Column(name = "INVOICE_ADDR_TAIL")
	private String invoiceAddrTail;

	/**
	 * 开户行名称
	 */
	@Column(name = "BANK_NAME")
	private String bankName;

	/**
	 * 账号
	 */
	@Column(name = "BANK_ACCOUNT")
	private String bankAccount;

	/**
	 * 单位联系电话
	 */
	@Column(name = "CONTACT_TELE")
	private String contactTele;

	/**
	 * 发票状态
	 */
	@Column(name = "INVOICE_STATUS")
	private String invoiceStatus;

	/**
	 * 发票申请次数
	 */
	@Column(name = "INVOICE_APP_COUNT")
	private String invoiceAppCount;

	@Column(name = "PO_NO")
	private String poNo;

	@Column(name = "PO_PROCESS_CODE")
	private String poProcessCode;

	@Column(name = "PO_DATE")
	private Date poDate;
}

/**
 * PosReplenishMaster.java 2012-04-25
 */
package org.macula.cloud.po.gbss.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>PosReplenishMaster</b> 是自营店补货申请主表
 * </p>
 * 
 
 
 
 * 
 */
@Entity
@Table(name = "POS_REPLENISH_MASTER")
@DynamicInsert
@DynamicUpdate
public class PosReplenishMaster extends LegacyUpdateable<Long> {
	private static final long serialVersionUID = 1L;

	/**POS自营店号*/
	@Column(name = "POS_STORE_NO", nullable = false, length = 15)
	@javax.validation.constraints.Size(min = 1, max = 15)
	private String posStoreNo;

	/**POS仓库代码*/
	@Column(name = "POS_WH_CODE", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String posWhCode;

	/**补货申请单号*/
	@Column(name = "APP_NO", nullable = false, length = 10)
	@javax.validation.constraints.Size(min = 1, max = 10)
	private String appNo;

	/**申请处理类型 */
	@Column(name = "APP_TRAN_TYPE", nullable = true)
	private String appTranType = "01";

	/**补货申请日期*/
	@Column(name = "APP_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date appDate;

	/**补货发货仓代码*/
	@Column(name = "APP_FROM_WH_CODE", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String appFromWhCode;

	/**补货收货仓代码*/
	@Column(name = "APP_TO_WH_CODE", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String appToWhCode;

	/**申请处理状态*/
	@Column(name = "APP_TRAN_STATUS", nullable = false, length = 2)
	@javax.validation.constraints.Size(min = 1, max = 2)
	private String appTranStatus;

	/**申请单成品总金额*/
	@Column(name = "APP_TOTAL_PRODUCT_AMT", nullable = false, length = 11)
	private BigDecimal appTotalProductAmt;

	/**申请单辅销品总金额*/
	@Column(name = "APP_TOTAL_NON_PRODUCT_AMT", nullable = false, length = 11)
	private BigDecimal appTotalNonProductAmt;

	/**申请单总金额*/
	@Column(name = "APP_TOTAL_AMT", nullable = false, length = 11)
	private BigDecimal appTotalAmt;

	/**申请单总点数*/
	@Column(name = "APP_TOTAL_POINT", nullable = false, length = 11)
	private BigDecimal appTotalPoint;

	/**申请单总重量*/
	@Column(name = "APP_TOTAL_WEIGHT", nullable = false, length = 11)
	private BigDecimal appTotalWeight;

	/**SAP币种代码*/
	@Column(name = "SAP_CURRENCY_CODE", nullable = false, length = 3)
	@javax.validation.constraints.Size(min = 1, max = 3)
	private String sapCurrencyCode;

	/**SAP运输方式 */
	@Column(name = "SAP_SHIPMENT_TYPE", nullable = false, length = 2)
	@javax.validation.constraints.Size(min = 1, max = 2)
	private String sapShipmentType;

	/**SAP转储单类型*/
	@Column(name = "SAP_TRAN_TYPE", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String sapTranType;

	/**SAP转储单单号*/
	@Column(name = "SAP_TRAN_DOC_NO", nullable = true, length = 12)
	private String sapTranDocNo;

	/**SAP转储备注*/
	@Column(name = "SAP_TRAN_MEMO", nullable = true, length = 200)
	private String sapTranMemo;

	/**SAP处理标志*/
	@Column(name = "SAP_TRAN_FLAG", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String sapTranFlag;

	/**复核时间*/
	@Column(name = "CHECKED_TIME", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkedTime;

	/**复核用户*/
	@Column(name = "CHECKED_BY", nullable = true, length = 20)
	private String checkedBy;

	/**复核备注*/
	@Column(name = "CHECKED_MEMO", nullable = true, length = 200)
	private String checkedMemo;

	/**审核时间*/
	@Column(name = "AUDITED_TIME", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditedTime;

	/**审核用户*/
	@Column(name = "AUDITED_BY", nullable = true, length = 20)
	private String auditedBy;

	/**审核备注*/
	@Column(name = "AUDITED_MEMO", nullable = true, length = 200)
	private String auditedMemo;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**RDC库区代码
	 * RDC库区编号，一般为4位；
	 * 如果格式为：1001-n，表示虚拟库存的虚拟业务库	
	 */
	@Column(name = "WH_LOC_CODE", length = 20)
	private String whLocCode;

	/**
	 * @return posStoreNo POS自营店号
	 */
	public String getPosStoreNo() {
		return this.posStoreNo;
	}

	/**
	 * @param posStoreNo POS自营店号的设置
	 */
	public void setPosStoreNo(String posStoreNo) {
		this.posStoreNo = posStoreNo;
	}

	/**
	 * @return posWhCode POS仓库代码
	 */
	public String getPosWhCode() {
		return this.posWhCode;
	}

	/**
	 * @param posWhCode POS仓库代码的设置
	 */
	public void setPosWhCode(String posWhCode) {
		this.posWhCode = posWhCode;
	}

	/**
	 * @return appNo 补货申请单号
	 */
	public String getAppNo() {
		return this.appNo;
	}

	/**
	 * @param appNo 补货申请单号的设置
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * @return appDate 补货申请日期
	 */
	public Date getAppDate() {
		return this.appDate;
	}

	/**
	 * @param appDate 补货申请日期的设置
	 */
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	/**
	 * @return appFromWhCode 补货发货仓代码
	 */
	public String getAppFromWhCode() {
		return this.appFromWhCode;
	}

	/**
	 * @param appFromWhCode 补货发货仓代码的设置
	 */
	public void setAppFromWhCode(String appFromWhCode) {
		this.appFromWhCode = appFromWhCode;
	}

	/**
	 * @return appToWhCode 补货收货仓代码
	 */
	public String getAppToWhCode() {
		return this.appToWhCode;
	}

	/**
	 * @param appToWhCode 补货收货仓代码的设置
	 */
	public void setAppToWhCode(String appToWhCode) {
		this.appToWhCode = appToWhCode;
	}

	/**
	 * @return appTranStatus 申请处理状态
	 */
	public String getAppTranStatus() {
		return this.appTranStatus;
	}

	/**
	 * @param appTranStatus 申请处理状态的设置
	 */
	public void setAppTranStatus(String appTranStatus) {
		this.appTranStatus = appTranStatus;
	}

	/**
	 * @return appTotalProductAmt 申请单成品总金额
	 */
	public BigDecimal getAppTotalProductAmt() {
		return this.appTotalProductAmt;
	}

	/**
	 * @param appTotalProductAmt 申请单成品总金额的设置
	 */
	public void setAppTotalProductAmt(BigDecimal appTotalProductAmt) {
		this.appTotalProductAmt = appTotalProductAmt;
	}

	/**
	 * @return appTotalNonProductAmt 申请单辅销品总金额
	 */
	public BigDecimal getAppTotalNonProductAmt() {
		return this.appTotalNonProductAmt;
	}

	/**
	 * @param appTotalNonProductAmt 申请单辅销品总金额的设置
	 */
	public void setAppTotalNonProductAmt(BigDecimal appTotalNonProductAmt) {
		this.appTotalNonProductAmt = appTotalNonProductAmt;
	}

	/**
	 * @return appTotalAmt 申请单总金额
	 */
	public BigDecimal getAppTotalAmt() {
		return this.appTotalAmt;
	}

	/**
	 * @param appTotalAmt 申请单总金额的设置
	 */
	public void setAppTotalAmt(BigDecimal appTotalAmt) {
		this.appTotalAmt = appTotalAmt;
	}

	/**
	 * @return appTotalPoint 申请单总点数
	 */
	public BigDecimal getAppTotalPoint() {
		return this.appTotalPoint;
	}

	/**
	 * @param appTotalPoint 申请单总点数的设置
	 */
	public void setAppTotalPoint(BigDecimal appTotalPoint) {
		this.appTotalPoint = appTotalPoint;
	}

	/**
	 * @return sapCurrencyCode SAP币种代码
	 */
	public String getSapCurrencyCode() {
		return this.sapCurrencyCode;
	}

	/**
	 * @param sapCurrencyCode SAP币种代码的设置
	 */
	public void setSapCurrencyCode(String sapCurrencyCode) {
		this.sapCurrencyCode = sapCurrencyCode;
	}

	/**
	 * @return sapTranType SAP转储单类型
	 */
	public String getSapTranType() {
		return this.sapTranType;
	}

	/**
	 * @param sapTranType SAP转储单类型的设置
	 */
	public void setSapTranType(String sapTranType) {
		this.sapTranType = sapTranType;
	}

	/**
	 * @return sapTranDocNo SAP转储单单号
	 */
	public String getSapTranDocNo() {
		return this.sapTranDocNo;
	}

	/**
	 * @param sapTranDocNo SAP转储单单号的设置
	 */
	public void setSapTranDocNo(String sapTranDocNo) {
		this.sapTranDocNo = sapTranDocNo;
	}

	/**
	 * @return sapTranMemo SAP转储备注
	 */
	public String getSapTranMemo() {
		return this.sapTranMemo;
	}

	/**
	 * @param sapTranMemo SAP转储备注的设置
	 */
	public void setSapTranMemo(String sapTranMemo) {
		this.sapTranMemo = sapTranMemo;
	}

	/**
	 * @return sapTranFlag SAP处理标志
	 */
	public String getSapTranFlag() {
		return this.sapTranFlag;
	}

	/**
	 * @param sapTranFlag SAP处理标志的设置
	 */
	public void setSapTranFlag(String sapTranFlag) {
		this.sapTranFlag = sapTranFlag;
	}

	/**
	 * @return checkedTime 复核时间
	 */
	public Date getCheckedTime() {
		return this.checkedTime;
	}

	/**
	 * @param checkedTime 复核时间的设置
	 */
	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}

	/**
	 * @return checkedBy 复核用户
	 */
	public String getCheckedBy() {
		return this.checkedBy;
	}

	/**
	 * @param checkedBy 复核用户的设置
	 */
	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	/**
	 * @return checkedMemo 复核备注
	 */
	public String getCheckedMemo() {
		return this.checkedMemo;
	}

	/**
	 * @param checkedMemo 复核备注的设置
	 */
	public void setCheckedMemo(String checkedMemo) {
		this.checkedMemo = checkedMemo;
	}

	/**
	 * @return auditedTime 审核时间
	 */
	public Date getAuditedTime() {
		return this.auditedTime;
	}

	/**
	 * @param auditedTime 审核时间的设置
	 */
	public void setAuditedTime(Date auditedTime) {
		this.auditedTime = auditedTime;
	}

	/**
	 * @return auditedBy 审核用户
	 */
	public String getAuditedBy() {
		return this.auditedBy;
	}

	/**
	 * @param auditedBy 审核用户的设置
	 */
	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

	/**
	 * @return auditedMemo 审核备注
	 */
	public String getAuditedMemo() {
		return this.auditedMemo;
	}

	/**
	 * @param auditedMemo 审核备注的设置
	 */
	public void setAuditedMemo(String auditedMemo) {
		this.auditedMemo = auditedMemo;
	}

	/**
	 * @return comments 备注
	 */
	public String getComments() {
		return this.comments;
	}

	/**
	 * @param comments 备注的设置
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the appTotalWeight
	 */
	public BigDecimal getAppTotalWeight() {
		return appTotalWeight;
	}

	/**
	 * @param appTotalWeight the appTotalWeight to set
	 */
	public void setAppTotalWeight(BigDecimal appTotalWeight) {
		this.appTotalWeight = appTotalWeight;
	}

	/**
	 * @return the sapShipmentType
	 */
	public String getSapShipmentType() {
		return sapShipmentType;
	}

	/**
	 * @param sapShipmentType the sapShipmentType to set
	 */
	public void setSapShipmentType(String sapShipmentType) {
		this.sapShipmentType = sapShipmentType;
	}

	/**
	 * @return the appTranType
	 */
	public String getAppTranType() {
		return appTranType;
	}

	/**
	 * @param appTranType the appTranType to set
	 */
	public void setAppTranType(String appTranType) {
		this.appTranType = appTranType;
	}

	public String getWhLocCode() {
		return whLocCode;
	}

	public void setWhLocCode(String whLocCode) {
		this.whLocCode = whLocCode;
	}
}

package org.macula.cloud.po.gbss.domain;

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
 * The persistent class for the PO_STORE_ADDRESS database table.
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_STORE_ADDRESS")
public class PoStoreAddress extends LegacyUpdateable<Long> {
	private static final long serialVersionUID = 1L;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "ADDR_AREA_CODE")
	private String addrAreaCode;

	@Column(name = "ADDR_DETAIL")
	private String addrDetail;

	@Column(name = "ADDR_SEND_ID")
	private String addrSendId;

	private String comments;

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "INACTIVE_DATE")
	private Date inactiveDate;

	@Column(name = "IS_DEFAULT")
	private boolean defaultt;

	@Column(name = "IS_MAX_ACTIVE")
	private String isMaxActive;

	@Column(name = "R01_CERTIFICATE_NO")
	private String r01CertificateNo;
	@Column(name = "R01_FULL_NAME")
	private String r01FullName;

	@Column(name = "R01_TELES")
	private String r01Teles;

	@Column(name = "R02_CERTIFICATE_NO")
	private String r02CertificateNo;

	@Column(name = "R02_FULL_NAME")
	private String r02FullName;

	@Column(name = "R02_TELES")
	private String r02Teles;

	@Column(name = "R03_CERTIFICATE_NO")
	private String r03CertificateNo;

	@Column(name = "R03_FULL_NAME")
	private String r03FullName;

	@Column(name = "R03_TELES")
	private String r03Teles;

	@Column(name = "STORE_NO")
	private String storeNo;

	public PoStoreAddress() {
	}

	public String getAddrAreaCode() {
		return this.addrAreaCode;
	}

	public void setAddrAreaCode(String addrAreaCode) {
		this.addrAreaCode = addrAreaCode;
	}

	public String getAddrDetail() {
		return this.addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getAddrSendId() {
		return this.addrSendId;
	}

	public void setAddrSendId(String addrSendId) {
		this.addrSendId = addrSendId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getInactiveDate() {
		return this.inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public String getR01CertificateNo() {
		return this.r01CertificateNo;
	}

	public void setR01CertificateNo(String r01CertificateNo) {
		this.r01CertificateNo = r01CertificateNo;
	}

	public String getR01FullName() {
		return this.r01FullName;
	}

	public void setR01FullName(String r01FullName) {
		this.r01FullName = r01FullName;
	}

	public String getR01Teles() {
		return this.r01Teles;
	}

	public void setR01Teles(String r01Teles) {
		this.r01Teles = r01Teles;
	}

	public String getR02CertificateNo() {
		return this.r02CertificateNo;
	}

	public void setR02CertificateNo(String r02CertificateNo) {
		this.r02CertificateNo = r02CertificateNo;
	}

	public String getR02FullName() {
		return this.r02FullName;
	}

	public void setR02FullName(String r02FullName) {
		this.r02FullName = r02FullName;
	}

	public String getR02Teles() {
		return this.r02Teles;
	}

	public void setR02Teles(String r02Teles) {
		this.r02Teles = r02Teles;
	}

	public String getR03CertificateNo() {
		return this.r03CertificateNo;
	}

	public void setR03CertificateNo(String r03CertificateNo) {
		this.r03CertificateNo = r03CertificateNo;
	}

	public String getR03FullName() {
		return this.r03FullName;
	}

	public void setR03FullName(String r03FullName) {
		this.r03FullName = r03FullName;
	}

	public String getR03Teles() {
		return this.r03Teles;
	}

	public void setR03Teles(String r03Teles) {
		this.r03Teles = r03Teles;
	}

	/**
	 * @return the storeNo
	 */
	public String getStoreNo() {
		return storeNo;
	}

	/**
	 * @param storeNo the storeNo to set
	 */
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	/**
	 * @return the defaultt
	 */
	public boolean isDefaultt() {
		return defaultt;
	}

	/**
	 * @param defaultt the defaultt to set
	 */
	public void setDefaultt(boolean defaultt) {
		this.defaultt = defaultt;
	}

	public String getIsMaxActive() {
		return isMaxActive;
	}

	public void setIsMaxActive(String isMaxActive) {
		this.isMaxActive = isMaxActive;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
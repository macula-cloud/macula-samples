package org.macula.cloud.logistics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractAuditable;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_HOME_WH_AREA")
public class PoHomeWhArea extends AbstractAuditable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "AREA_ATTR")
	private String areaAttr;

	@Column(name = "AREA_CODE")
	private String areaCode;

	private String comments;

	@Column(name = "CREATED_MODE")
	private String createdMode;

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "INACTIVE_DATE")
	private Date inactiveDate;

	@Column(name = "IS_STORE_LOCATE")
	private boolean storeLocate;

	@Column(name = "JP_MAX_ACTIVE_DAY")
	private int jpMaxActiveDay;

	@Column(name = "JP_MIN_ACTIVE_DAY")
	private int jpMinActiveDay;

	@Column(name = "SEND_ARRIVE_TIME_01")
	private int sendArriveTime01;

	@Column(name = "SEND_ARRIVE_TIME_02")
	private int sendArriveTime02;

	@Column(name = "SEND_DISTANCE")
	private BigDecimal sendDistance;

	@Column(name = "SEND_FEE")
	private BigDecimal sendFee;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "WH_MEMO")
	private String whMemo;

	public PoHomeWhArea() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAreaAttr() {
		return areaAttr;
	}

	public void setAreaAttr(String areaAttr) {
		this.areaAttr = areaAttr;
	}

	public String getCreatedMode() {
		return createdMode;
	}

	public void setCreatedMode(String createdMode) {
		this.createdMode = createdMode;
	}

	public BigDecimal getSendFee() {
		return sendFee;
	}

	public void setSendFee(BigDecimal sendFee) {
		this.sendFee = sendFee;
	}

	public String getWhMemo() {
		return whMemo;
	}

	public void setWhMemo(String whMemo) {
		this.whMemo = whMemo;
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

	/**
	 * @return the storeLocate
	 */
	public boolean isStoreLocate() {
		return storeLocate;
	}

	/**
	 * @param storeLocate the storeLocate to set
	 */
	public void setStoreLocate(boolean storeLocate) {
		this.storeLocate = storeLocate;
	}

	/**
	 * @return the jpMaxActiveDay
	 */
	public int getJpMaxActiveDay() {
		return jpMaxActiveDay;
	}

	/**
	 * @param jpMaxActiveDay the jpMaxActiveDay to set
	 */
	public void setJpMaxActiveDay(int jpMaxActiveDay) {
		this.jpMaxActiveDay = jpMaxActiveDay;
	}

	/**
	 * @return the jpMinActiveDay
	 */
	public int getJpMinActiveDay() {
		return jpMinActiveDay;
	}

	/**
	 * @param jpMinActiveDay the jpMinActiveDay to set
	 */
	public void setJpMinActiveDay(int jpMinActiveDay) {
		this.jpMinActiveDay = jpMinActiveDay;
	}

	/**
	 * @return the sendArriveTime01
	 */
	public int getSendArriveTime01() {
		return sendArriveTime01;
	}

	/**
	 * @param sendArriveTime01 the sendArriveTime01 to set
	 */
	public void setSendArriveTime01(int sendArriveTime01) {
		this.sendArriveTime01 = sendArriveTime01;
	}

	/**
	 * @return the sendArriveTime02
	 */
	public int getSendArriveTime02() {
		return sendArriveTime02;
	}

	/**
	 * @param sendArriveTime02 the sendArriveTime02 to set
	 */
	public void setSendArriveTime02(int sendArriveTime02) {
		this.sendArriveTime02 = sendArriveTime02;
	}

	public BigDecimal getSendDistance() {
		return this.sendDistance;
	}

	public void setSendDistance(BigDecimal sendDistance) {
		this.sendDistance = sendDistance;
	}

	public String getWhCode() {
		return this.whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

}
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
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_STORE_WH_AREA")
public class PoStoreWhArea extends AbstractPersistable<Long> implements Serializable {

	private static final long serialVersionUID = -9087187261001214780L;

	@Id
	private Long id;

	@Column(name = "AREA_CODE")
	private String areaCode;

	@Column(name = "IS_STORE_LOCATE")
	private boolean storeLocate;

	@Column(name = "SEND_ARRIVE_TIME_02")
	private int sendArriveTime02;

	@Column(name = "SEND_ARRIVE_TIME_01")
	private int sendArriveTime01;

	@Column(name = "SEND_FEE")
	private BigDecimal sendFee;

	@Column(name = "SEND_DISTANCE")
	private BigDecimal sendDistance;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "AREA_ATTR")
	private String areaAttr;

	@Column(name = "STORE_MAX_ACTIVE_DAY")
	private int storeMaxActiveDay;

	@Column(name = "STORE_MIN_ACTIVE_DAY")
	private int storeMinActiveDay;

	@Column(name = "WH_MEMO")
	private String whMemo;

	@Column(name = "CREATED_MODE")
	private String createdMode;

	@Column(name = "COMMENTS")
	private String comments;

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "INACTIVE_DATE")
	private Date inactiveDate;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public int getSendArriveTime02() {
		return sendArriveTime02;
	}

	public void setSendArriveTime02(int sendArriveTime02) {
		this.sendArriveTime02 = sendArriveTime02;
	}

	public int getSendArriveTime01() {
		return sendArriveTime01;
	}

	public void setSendArriveTime01(int sendArriveTime01) {
		this.sendArriveTime01 = sendArriveTime01;
	}

	public BigDecimal getSendFee() {
		return sendFee;
	}

	public void setSendFee(BigDecimal sendFee) {
		this.sendFee = sendFee;
	}

	public BigDecimal getSendDistance() {
		return sendDistance;
	}

	public void setSendDistance(BigDecimal sendDistance) {
		this.sendDistance = sendDistance;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getAreaAttr() {
		return areaAttr;
	}

	public void setAreaAttr(String areaAttr) {
		this.areaAttr = areaAttr;
	}

	public int getStoreMaxActiveDay() {
		return storeMaxActiveDay;
	}

	public void setStoreMaxActiveDay(int storeMaxActiveDay) {
		this.storeMaxActiveDay = storeMaxActiveDay;
	}

	public int getStoreMinActiveDay() {
		return storeMinActiveDay;
	}

	public void setStoreMinActiveDay(int storeMinActiveDay) {
		this.storeMinActiveDay = storeMinActiveDay;
	}

	public String getWhMemo() {
		return whMemo;
	}

	public void setWhMemo(String whMemo) {
		this.whMemo = whMemo;
	}

	public String getCreatedMode() {
		return createdMode;
	}

	public void setCreatedMode(String createdMode) {
		this.createdMode = createdMode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

}

package org.macula.cloud.fulfilment.gbss.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "RDC_STOCK_STATUS")
public class RdcStockStatus extends AbstractPersistable<Long> implements Serializable {

	private static final long serialVersionUID = 6025798256491880236L;

	@Id
	@SequenceGenerator(name = "RDC_STOCK_STATUS_ID_GENERATOR", sequenceName = "SEQ_RDC_STOCK_STATUS_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RDC_STOCK_STATUS_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "ACC_DATE")
	private Date accDate;

	@Column(name = "THIS_ADJ_QTY_02")
	private BigDecimal thisAdjQty02;

	@Column(name = "THIS_ADJ_QTY_01")
	private BigDecimal thisAdjQty01;

	@Column(name = "THIS_PO_QTY")
	private BigDecimal thisPoQty;

	@Column(name = "LAST_BALANCE_QTY")
	private BigDecimal lastBalanceQty;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "WH_LOC_CODE")
	private String whLocCode;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "BALANCE_QTY")
	private BigDecimal balanceQty;

	@Column(name = "REF_LAST_PO_NO")
	private String refLastPoNo;

	@Column(name = "COMMENTS")
	private String comments;

	/**
	 * 最后更新人
	 */
	@Column(name = "LAST_UPDATED_BY")
	private String lastModifiedBy;

	/**
	 * 最后更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_TIME")
	private Date lastModifiedDate;

	@Transient
	private String createdBy;

	@Transient
	private Date createdDate;

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public Date getAccDate() {
		return accDate;
	}

	public void setAccDate(Date accDate) {
		this.accDate = accDate;
	}

	public BigDecimal getThisAdjQty02() {
		return thisAdjQty02;
	}

	public void setThisAdjQty02(BigDecimal thisAdjQty02) {
		this.thisAdjQty02 = thisAdjQty02;
	}

	public BigDecimal getThisAdjQty01() {
		return thisAdjQty01;
	}

	public void setThisAdjQty01(BigDecimal thisAdjQty01) {
		this.thisAdjQty01 = thisAdjQty01;
	}

	public BigDecimal getThisPoQty() {
		return thisPoQty;
	}

	public void setThisPoQty(BigDecimal thisPoQty) {
		this.thisPoQty = thisPoQty;
	}

	public BigDecimal getLastBalanceQty() {
		return lastBalanceQty;
	}

	public void setLastBalanceQty(BigDecimal lastBalanceQty) {
		this.lastBalanceQty = lastBalanceQty;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getWhLocCode() {
		return whLocCode;
	}

	public void setWhLocCode(String whLocCode) {
		this.whLocCode = whLocCode;
	}

	public BigDecimal getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(BigDecimal balanceQty) {
		this.balanceQty = balanceQty;
	}

	public String getRefLastPoNo() {
		return refLastPoNo;
	}

	public void setRefLastPoNo(String refLastPoNo) {
		this.refLastPoNo = refLastPoNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.data.domain.Auditable#getCreatedBy()
	 */
	public String getCreatedBy() {

		return createdBy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.data.domain.Auditable#setCreatedBy(java.lang.Object)
	 */
	public void setCreatedBy(final String createdBy) {

		this.createdBy = createdBy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.data.domain.Auditable#getCreatedDate()
	 */
	public DateTime getCreatedDate() {

		return null == createdDate ? null : new DateTime(createdDate);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.data.domain.Auditable#setCreatedDate(org.joda.time
	 * .DateTime)
	 */
	public void setCreatedDate(final DateTime createdDate) {

		this.createdDate = null == createdDate ? null : createdDate.toDate();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.data.domain.Auditable#getLastModifiedBy()
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.data.domain.Auditable#setLastModifiedBy(java.lang
	 * .Object)
	 */
	public void setLastModifiedBy(final String lastModifiedBy) {

		this.lastModifiedBy = lastModifiedBy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.data.domain.Auditable#getLastModifiedDate()
	 */
	public DateTime getLastModifiedDate() {

		return null == lastModifiedDate ? null : new DateTime(lastModifiedDate);
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.data.domain.Auditable#setLastModifiedDate(org.joda
	 * .time.DateTime)
	 */
	public void setLastModifiedDate(final DateTime lastModifiedDate) {

		this.lastModifiedDate = null == lastModifiedDate ? null : lastModifiedDate.toDate();
	}

}

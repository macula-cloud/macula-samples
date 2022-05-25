package org.macula.cloud.fulfilment.gbss.domain;

import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "RDC_EX_STOCK_STATUS")
public class RdcExStockStatus extends AbstractPersistable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@Id
	@SequenceGenerator(name = "RDC_EX_STOCK_STATUS_ID_GENERATOR", sequenceName = "SEQ_RDC_EX_STOCK_STATUS_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RDC_EX_STOCK_STATUS_ID_GENERATOR")
	private Long id;

	/**
	 * 记账日期
	 */
	@Column(name = "ACC_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date accDate;

	/**
	 * RDC代码
	 */
	@Column(name = "WH_CODE", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String whCode;

	/**
	 * RDC库区代码
	 */
	@Column(name = "WH_LOC_CODE", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String whLocCode;

	/**
	 * 产品代码
	 */
	@Column(name = "PRODUCT_CODE", nullable = false, length = 10)
	@javax.validation.constraints.Size(min = 1, max = 10)
	private String productCode;

	/**
	 * 上日结余数量
	 */
	@Column(name = "LAST_BALANCE_QTY", nullable = false, length = 15)
	private BigDecimal lastBalanceQty;

	/**
	 * 当日产生数量
	 */
	@Column(name = "THIS_GEN_QTY", nullable = false, length = 15)
	private BigDecimal thisGenQty;

	/**
	 * 当日领用数量
	 */
	@Column(name = "THIS_USE_QTY", nullable = false, length = 15)
	private BigDecimal thisUseQty;

	/**
	 * 当日结余数量
	 */
	@Column(name = "BALANCE_QTY", nullable = false, length = 15)
	private BigDecimal balanceQty;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**
	 * 最后更新时间
	 */
	@Column(name = "LAST_UPDATED_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedTime;

	/**
	 * 最后更新用户
	 */
	@Column(name = "LAST_UPDATED_BY", nullable = false, length = 20)
	@javax.validation.constraints.Size(min = 1, max = 20)
	private String lastUpdatedBy;

	/**
	 * @return id 序号
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id 序号的设置
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return accDate 记账日期
	 */
	public Date getAccDate() {
		return this.accDate;
	}

	/**
	 * @param accDate 记账日期的设置
	 */
	public void setAccDate(Date accDate) {
		this.accDate = accDate;
	}

	/**
	 * @return whCode RDC代码
	 */
	public String getWhCode() {
		return this.whCode;
	}

	/**
	 * @param whCode RDC代码的设置
	 */
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	/**
	 * @return whLocCode RDC库区代码
	 */
	public String getWhLocCode() {
		return this.whLocCode;
	}

	/**
	 * @param whLocCode RDC库区代码的设置
	 */
	public void setWhLocCode(String whLocCode) {
		this.whLocCode = whLocCode;
	}

	/**
	 * @return productCode 产品代码
	 */
	public String getProductCode() {
		return this.productCode;
	}

	/**
	 * @param productCode 产品代码的设置
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the lastBalanceQty
	 */
	public BigDecimal getLastBalanceQty() {
		return lastBalanceQty;
	}

	/**
	 * @param lastBalanceQty the lastBalanceQty to set
	 */
	public void setLastBalanceQty(BigDecimal lastBalanceQty) {
		this.lastBalanceQty = lastBalanceQty;
	}

	/**
	 * @return the thisGenQty
	 */
	public BigDecimal getThisGenQty() {
		return thisGenQty;
	}

	/**
	 * @param thisGenQty the thisGenQty to set
	 */
	public void setThisGenQty(BigDecimal thisGenQty) {
		this.thisGenQty = thisGenQty;
	}

	/**
	 * @return the thisUseQty
	 */
	public BigDecimal getThisUseQty() {
		return thisUseQty;
	}

	/**
	 * @param thisUseQty the thisUseQty to set
	 */
	public void setThisUseQty(BigDecimal thisUseQty) {
		this.thisUseQty = thisUseQty;
	}

	/**
	 * @return the balanceQty
	 */
	public BigDecimal getBalanceQty() {
		return balanceQty;
	}

	/**
	 * @param balanceQty the balanceQty to set
	 */
	public void setBalanceQty(BigDecimal balanceQty) {
		this.balanceQty = balanceQty;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
	 * @return lastUpdatedTime 最后更新时间
	 * @see org.springframework.data.domain.Auditable#getLastModifiedDate()
	 */
	public DateTime getLastModifiedDate() {
		return null == lastUpdatedTime ? null : new DateTime(lastUpdatedTime);
	}

	/**
	 * @param lastUpdatedTime 最后更新时间的设置
	 * @see org.springframework.data.domain.Auditable#setLastModifiedDate(org.joda.time.DateTime)
	 */
	public void setLastModifiedDate(final DateTime lastModifiedDate) {
		this.lastUpdatedTime = null == lastModifiedDate ? null : lastModifiedDate.toDate();
	}

	/**
	 * @return lastUpdatedBy 最后更新用户
	 * @see org.springframework.data.domain.Auditable#getLastModifiedBy()
	 */
	public String getLastModifiedBy() {
		return this.lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedBy 最后更新用户的设置
	 * @see org.springframework.data.domain.Auditable#setLastModifiedBy(java.lang.Object)
	 */
	public void setLastModifiedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

}

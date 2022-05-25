package org.macula.cloud.po.gbss.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;
import org.macula.cloud.po.domain.PoMaster;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * <b>POS_DELIVERY_MASTER</b> 是自营店交货主信息
 * </p>
 * 
 
 
 
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "POS_DELIVERY_MASTER")
public class PosDeliveryMaster extends LegacyUpdateable<Long> {
	private static final long serialVersionUID = 1L;

	/**POS交货单号*/
	@Column(name = "POS_DELIVERY_NO", nullable = false, length = 15)
	private String posDeliveryNo;

	/**POS交货日期*/
	@Column(name = "POS_DELIVERY_DATE", nullable = false)
	private Date posDeliveryDate;

	/**POS自营店号*/
	@Column(name = "POS_STORE_NO", nullable = false, length = 15)
	private String posStoreNo;

	/**订单单号*/
	@Column(name = "PO_NO", nullable = false, length = 15)
	private String poNo;

	/**SAP记账标志*/
	@Column(name = "SAP_POSTING_FLAG", nullable = true)
	private String sapPostingFlag;

	/**SAP记账日期*/
	@Column(name = "SAP_POSTING_DATE", nullable = true)
	private Date sapPostingDate;

	/**SAP记账凭证*/
	@Column(name = "SAP_POSTING_DOC_NO", nullable = true)
	private String sapPostingDocNo;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = PoMaster.class, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PO_NO", referencedColumnName = "PO_NO", insertable = false, updatable = false)
	private PoMaster poMaster;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, targetEntity = PosDeliveryDetail.class, cascade = { CascadeType.ALL })
	@JoinColumn(name = "POS_DELIVERY_NO", referencedColumnName = "POS_DELIVERY_NO")
	private List<PosDeliveryDetail> pddList = new ArrayList<PosDeliveryDetail>();

	/**
	 * @return posDeliveryNo POS交货单号
	 */
	public String getPosDeliveryNo() {
		return this.posDeliveryNo;
	}

	/**
	 * @param posDeliveryNo POS交货单号的设置
	 */
	public void setPosDeliveryNo(String posDeliveryNo) {
		this.posDeliveryNo = posDeliveryNo;
	}

	/**
	 * @return posDeliveryDate POS交货日期
	 */
	public Date getPosDeliveryDate() {
		return this.posDeliveryDate;
	}

	/**
	 * @param posDeliveryDate POS交货日期的设置
	 */
	public void setPosDeliveryDate(Date posDeliveryDate) {
		this.posDeliveryDate = posDeliveryDate;
	}

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
	 * @return poNo 订单单号
	 */
	public String getPoNo() {
		return this.poNo;
	}

	/**
	 * @param poNo 订单单号的设置
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	/**
	 * @return sapPostingDocNo SAP记账凭证
	 */
	public String getSapPostingDocNo() {
		return this.sapPostingDocNo;
	}

	/**
	 * @param sapPostingDocNo SAP记账凭证的设置
	 */
	public void setSapPostingDocNo(String sapPostingDocNo) {
		this.sapPostingDocNo = sapPostingDocNo;
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
	 * @return the pddList
	 */
	public List<PosDeliveryDetail> getPddList() {
		return pddList;
	}

	/**
	 * @param pddList the pddList to set
	 */
	public void setPddList(List<PosDeliveryDetail> pddList) {
		this.pddList = pddList;
	}

	/**
	 * @return the sapPostingFlag
	 */
	public String getSapPostingFlag() {
		return sapPostingFlag;
	}

	/**
	 * @param sapPostingFlag the sapPostingFlag to set
	 */
	public void setSapPostingFlag(String sapPostingFlag) {
		this.sapPostingFlag = sapPostingFlag;
	}

	/**
	 * @return the sapPostingDate
	 */
	public Date getSapPostingDate() {
		return sapPostingDate;
	}

	/**
	 * @param sapPostingDate the sapPostingDate to set
	 */
	public void setSapPostingDate(Date sapPostingDate) {
		this.sapPostingDate = sapPostingDate;
	}

	/**
	 * @return the poMaster
	 */
	public PoMaster getPoMaster() {
		return poMaster;
	}

	/**
	 * @param poMaster the poMaster to set
	 */
	public void setPoMaster(PoMaster poMaster) {
		this.poMaster = poMaster;
	}

}

package org.macula.cloud.fulfilment.gbss.domain;

import javax.persistence.*;

/**
 * The persistent class for the WH_INFO database table.
 */
@Entity
@Table(name = "WH_INFO")
public class WhInfo extends AbstractAuditable<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "WH_INFO_ID_GENERATOR", sequenceName = "SEQ_WH_INFO_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WH_INFO_ID_GENERATOR")
	private Long id;

	@Column(name = "ADDR_DETAIL")
	private String addrDetail;

	@Column(name = "ADDR_ZIP_CODE")
	private String addrZipCode;

	private String comments;

	@Column(name = "CONTACT_FAX")
	private String contactFax;

	@Column(name = "CONTACT_TEL")
	private String contactTel;

	@Column(name = "POS_REF_WH_CODE")
	private String posRefWhCode;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "WH_NAME")
	private String whName;

	@Column(name = "WH_TYPE")
	private String whType;

	@Column(name = "ESTIMATE_ARRIVAL_DAY")
	private Integer estimateArrivalDay;

	public WhInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddrDetail() {
		return this.addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getAddrZipCode() {
		return this.addrZipCode;
	}

	public void setAddrZipCode(String addrZipCode) {
		this.addrZipCode = addrZipCode;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContactFax() {
		return this.contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getPosRefWhCode() {
		return this.posRefWhCode;
	}

	public void setPosRefWhCode(String posRefWhCode) {
		this.posRefWhCode = posRefWhCode;
	}

	public String getWhCode() {
		return this.whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getWhName() {
		return this.whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getWhType() {
		return this.whType;
	}

	public void setWhType(String whType) {
		this.whType = whType;
	}

	public Integer getEstimateArrivalDay() {
		return estimateArrivalDay;
	}

	public void setEstimateArrivalDay(Integer estimateArrivalDay) {
		this.estimateArrivalDay = estimateArrivalDay;
	}
}
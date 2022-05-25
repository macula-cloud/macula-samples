package org.macula.cloud.logistics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "LC_WH_INFO")
public class WhInfo extends AbstractAuditable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "WH_NAME")
	private String whName;

	@Column(name = "WH_TYPE")
	private String whType;

	@Column(name = "POS_REF_WH_CODE")
	private String posRefWhCode;

	@Column(name = "ADDR_DETAIL")
	private String addrDetail;

	@Column(name = "ADDR_ZIP_CODE")
	private String addrZipCode;

	@Column(name = "CONTACT_FAX")
	private String contactFax;

	@Column(name = "CONTACT_TEL")
	private String contactTel;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "ESTIMATE_ARRIVAL_DAY")
	private Integer estimateArrivalDay;

}
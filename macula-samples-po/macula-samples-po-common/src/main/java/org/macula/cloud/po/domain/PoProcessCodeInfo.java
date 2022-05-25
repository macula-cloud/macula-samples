package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.core.domain.AbstractAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the PO_PROCESS_CODE_INFO database table.
 */
@Getter
@Setter
@Entity
@Table(name = "PO_PROCESS_CODE_INFO")
public class PoProcessCodeInfo extends AbstractAuditable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "IS_FOR_INV")
	private BigDecimal isForInv;

	@Column(name = "IS_FOR_PO")
	private BigDecimal isForPo;

	@Column(name = "PO_PROCESS_CODE")
	private String poProcessCode;

	@Column(name = "PO_PROCESS_DESC")
	private String poProcessDesc;

	@Column(name = "SAP_PROCESS_CODE")
	private String sapProcessCode;

	@Column(name = "PO_TYPE")
	private String poType;

	@Column(name = "SO_TYPE")
	private String soType;

	@Column(name = "COMMENTS")
	private String comments;
}
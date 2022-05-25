package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the SAP_DS_MAPPING database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name = "SAP_DS_MAPPING")
public class SapDsMapping extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "DS_CODE")
	private String dsCode;

	@Column(name = "DS_DESC")
	private String dsDesc;

	@Column(name = "IS_ENABLED")
	private BigDecimal isEnabled;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_TIME")
	private Timestamp lastUpdatedTime;

	@Column(name = "MAPPING_TYPE")
	private String mappingType;

	@Column(name = "SAP_CODE")
	private String sapCode;

	@Column(name = "COMMENTS")
	private String comments;

}
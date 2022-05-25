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
 * The persistent class for the SYS_ENUM_INFO database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name = "SYS_ENUM_INFO")
public class SysEnumInfo extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "DATA_FULL_DESC")
	private String dataFullDesc;

	@Column(name = "DATA_SHORT_DESC")
	private String dataShortDesc;

	@Column(name = "DATA_VALUE")
	private String dataValue;

	@Column(name = "FIELD_NAME")
	private String fieldName;

	@Column(name = "IS_ENABLED")
	private BigDecimal isEnabled;

	@Column(name = "LANGUAGE_CODE")
	private String languageCode;

	@Column(name = "LINE_NO")
	private BigDecimal lineNo;

	@Column(name = "TABLE_NAME")
	private String tableName;

	@Column(name = "COMMENTS")
	private String comments;

}
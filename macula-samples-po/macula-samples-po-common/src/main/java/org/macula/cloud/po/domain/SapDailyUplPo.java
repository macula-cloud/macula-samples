package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the SAP_DAILY_UPL_PO database table.
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Table(name = "SAP_DAILY_UPL_PO")
public class SapDailyUplPo extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "PO_NO")
	private String poNo;

	@Column(name = "STEP01_END_TIME")
	private Timestamp step01EndTime;

	@Column(name = "STEP02_END_TIME")
	private Timestamp step02EndTime;

	@Column(name = "STEP03_END_TIME")
	private Timestamp step03EndTime;

	@Column(name = "SYN_ERR_CNT")
	private BigDecimal synErrCnt;

	@Column(name = "SYN_ERR_CODE")
	private String synErrCode;

	@Column(name = "SYN_ERR_MSG")
	private String synErrMsg;

	@Column(name = "SYN_STATUS")
	private String synStatus;

	@Column(name = "SYN_TYPE")
	private String synType;

	@Column(name = "COMMENTS")
	private String comments;

}
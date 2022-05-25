/**
 * DealerDeleteBase.java 2012-3-23
 */
package org.macula.cloud.po.gbss.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 会员基本信息删除资料表
 
 
 
 */
@Entity
@Getter
@Setter
@Table(name = "DEALER_DELETE_BASE")
public class DealerDeleteBase extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "APP_DATE")
	private Date appDate;

	@Column(name = "APP_PERIOD")
	private String appPeriod;

	@Column(name = "APP_STATUS")
	private String appStatus;

	private String comments;

	@Column(name = "DEALER_NO")
	private String dealerNo;

	@Column(name = "DEALER_POST_CODE")
	private BigDecimal dealerPostCode;

	@Column(name = "DEALER_TYPE")
	private String dealerType;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "IS_REGISTER_SPOUSE")
	private boolean registerSpouse;

	@Column(name = "IS_SP2_LICENCE_HOLDER")
	private boolean sp2LicenceHolder;

	@Column(name = "IS_VIP")
	private boolean vip;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "OP_LOCK_STATUS")
	private String opLockStatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "POST_EFFECTIVE_DATE")
	private Date postEffectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "POST_INACTIVE_DATE")
	private Date postInactiveDate;

	@Column(name = "SALE_BRANCH_NO")
	private String saleBranchNo;

	@Column(name = "SALE_ORG_CODE")
	private String saleOrgCode;

	@Column(name = "SALE_ZONE_NO")
	private String saleZoneNo;

	private String sex;

	@Column(name = "SPONSOR_NO")
	private String sponsorNo;

	@Column(name = "SPOUSE_NAME")
	private String spouseName;

	@Column(name = "TYPE_CHG_CLASS")
	private String typeChgClass;

	@Temporal(TemporalType.DATE)
	@Column(name = "TYPE_CHG_DATE")
	private Date typeChgDate;

	@Column(name = "TYPE_CHG_REASON")
	private String typeChgReason;

	@Temporal(TemporalType.DATE)
	@Column(name = "TYPE_EFFECTIVE_DATE")
	private Date typeEffectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TYPE_INACTIVE_DATE")
	private Date typeInactiveDate;

}

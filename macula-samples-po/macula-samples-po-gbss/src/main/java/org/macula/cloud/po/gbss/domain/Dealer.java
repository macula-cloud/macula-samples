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
 * 会员基本信息资料表
 
 
 * @version
 */
@Entity
@Getter
@Setter
@Table(name = "DEALER")
public class Dealer extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 申请日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APP_DATE")
	private Date appDate;

	/**
	 * 申请办卡月份
	 */
	@Column(name = "APP_PERIOD")
	private String appPeriod;

	/**
	 * 资格申请状态
	 */
	@Column(name = "APP_STATUS")
	private String appStatus;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;

	/**
	 * 卡号
	 */
	@Column(name = "DEALER_NO")
	private String dealerNo;

	/**
	 * 职级代码
	 */
	@Column(name = "DEALER_POST_CODE")
	private BigDecimal dealerPostCode;

	/**
	 * 身份类型
	 */
	@Column(name = "DEALER_TYPE")
	private String dealerType;

	/**
	 * 名
	 */
	@Column(name = "FIRST_NAME")
	private String firstName;

	/**
	 * 姓名
	 */
	@Column(name = "FULL_NAME")
	private String fullName;

	/**
	 * 是否夫妻联名
	 */
	@Column(name = "IS_REGISTER_SPOUSE")
	private boolean registerSpouse;

	/**
	 * 是否持有直销员证
	 */
	@Column(name = "IS_SP2_LICENCE_HOLDER")
	private boolean sp2LicenceHolder;

	/**
	 * 是否VIP客户
	 */
	@Column(name = "IS_VIP")
	private boolean vip;

	/**
	 * 姓
	 */
	@Column(name = "LAST_NAME")
	private String lastName;

	/**
	 * 业务锁状态
	 */
	@Column(name = "OP_LOCK_STATUS")
	private String opLockStatus;

	/**
	 * 交易锁01(推荐锁)
	 */
	@Column(name = "TRAN_LOCK_01")
	private String tranLock01;

	/**
	 * 职级开始日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "POST_EFFECTIVE_DATE")
	private Date postEffectiveDate;

	/**
	 * 职级失效日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "POST_INACTIVE_DATE")
	private Date postInactiveDate;

	/**
	 * 所属分公司编号
	 */
	@Column(name = "SALE_BRANCH_NO")
	private String saleBranchNo;

	/**
	 * 所属总公司编号
	 */
	@Column(name = "SALE_ORG_CODE")
	private String saleOrgCode;

	/**
	 * 销售片区编号
	 */
	@Column(name = "SALE_ZONE_NO")
	private String saleZoneNo;

	/**
	 * 性别
	 */
	@Column(name = "SEX")
	private String sex;

	/**
	 * 推荐人卡号
	 */
	@Column(name = "SPONSOR_NO")
	private String sponsorNo;

	/**
	 * 配偶姓名
	 */
	@Column(name = "SPOUSE_NAME")
	private String spouseName;

	/**
	 * 资格变化类型
	 */
	@Column(name = "TYPE_CHG_CLASS")
	private String typeChgClass;

	/**
	 * 资格变化日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TYPE_CHG_DATE")
	private Date typeChgDate;

	/**
	 * 资格变化原因
	 */
	@Column(name = "TYPE_CHG_REASON")
	private String typeChgReason;

	/**
	 * 资格开始日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TYPE_EFFECTIVE_DATE")
	private Date typeEffectiveDate;

	/**
	 * 资格失效日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TYPE_INACTIVE_DATE")
	private Date typeInactiveDate;

	/**
	 * 业务身份生效日
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SP_EFFECTIVE_DATE")
	private Date spEffectiveDate;

	/**
	 * 业务身份失效日
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SP_INACTIVE_DATE")
	private Date spInactiveDate;

	/**
	 * 首次申业月份
	 */
	@Column(name = "APP_SP_FIRST_PERIOD")
	private String appSpFirstPeriod;

}
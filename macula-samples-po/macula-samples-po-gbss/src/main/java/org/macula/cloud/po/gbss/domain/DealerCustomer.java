/**
 * DealerCustomer.java 2019年7月11日
 */
package org.macula.cloud.po.gbss.domain;

import java.io.Serializable;
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
 * 普通顾客基本信息表
 */
@Entity
@Getter
@Setter
@Table(name = "DEALER_CUSTOMER")
public class DealerCustomer extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 普消号 **/
	@Column(name = "CUSTOMER_NO", nullable = false, length = 15)
	private String customerNo;

	/** 手机号码 **/
	@Column(name = "MOBILE", nullable = false, length = 15)
	private String mobile;

	/** 亲友卡号 **/
	@Column(name = "FRIEND_DEALER_NO", nullable = false, length = 15)
	private String friendDealerNo;

	/** 普消类型 **/
	@Column(name = "CUSTORMER_TYPE", nullable = false, length = 32)
	private String customerType;

	/** 姓名 **/
	@Column(name = "FULL_NAME", nullable = true, length = 50)
	private String fullName;

	/** 姓 **/
	@Column(name = "LAST_NAME", nullable = true, length = 20)
	private String lastName;

	/** 名 **/
	@Column(name = "FIRST_NAME", nullable = true, length = 30)
	private String firstName;

	/** 注册日期 **/
	@Column(name = "APP_DATE", nullable = false)
	private Date appDate;

	/** 注册月份 **/
	@Column(name = "APP_PERIOD", nullable = false, length = 6)
	private String appPeriod;

	/** 出生日期 **/
	@Column(name = "BIRTHDAY", nullable = true)
	private Date birthday;

	/** 性别 **/
	@Column(name = "SEX", nullable = true, length = 1)
	private String sex;

	/** 地址国家 **/
	@Column(name = "ADDR_NATION_CODE", nullable = true, length = 2)
	private String addrNationCode;

	/** 所在地区编号 **/
	@Column(name = "AREA_CODE", nullable = true, length = 6)
	private String areaCode;

	/** 会员卡号 **/
	@Column(name = "DEALER_NO", nullable = true, length = 15)
	private String dealerNo;

	/** 注册录入类型 **/
	@Column(name = "APP_ENTRY_CLASS", nullable = false, length = 1)
	private String appEntryClass;

	/** 来源 **/
	@Column(name = "APP_SOURCE", nullable = false, length = 32)
	private String appSource;

	/** 状态 **/
	@Column(name = "STATUS", nullable = false, length = 32)
	private String status;

	/** 失效日期 **/
	@Column(name = "INACTIVE_DATE", nullable = true)
	private Date inactiveDate;

	/** 状态 **/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/** 注销时间 **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CANCEL_TIME")
	private Date cancelTime;

	/** 注销渠道 **/
	@Column(name = "CANCEL_ENTRY_CLASS", nullable = true, length = 32)
	private String cancelEntryClass;

	/** 注销操作人 **/
	@Column(name = "CANCEL_ENTRY_BY", nullable = true, length = 200)
	private String cancelEntryBy;

	/** 注销原因 **/
	@Column(name = "CANCEL_REASON", nullable = true, length = 200)
	private String cancelReason;

	/** 证件类型 **/
	@Column(name = "CERTIFICATE_TYPE", nullable = true, length = 2)
	private String certificateType;

	/** 证件编号 **/
	@Column(name = "CERTIFICATE_NO", nullable = true, length = 25)
	private String certificateNo;

	/** 是否实名认证 **/
	@Column(name = "IS_REALNAME_AUTH", nullable = false, length = 1)
	private boolean isRealnameAuth;

	/** 实名认证时间 **/
	@Column(name = "REALNAME_AUTH_TIME", nullable = true)
	private Date realnameAuthTime;

}
package org.macula.cloud.po.gbss.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 店铺运作信息表
 
 
 * @version
 */
@Entity
@Getter
@Setter
@Table(name = "DEALER_STORE")
public class DealerStore extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CREDIT_CLASS")
	private String creditClass;

	/**
	 * 店主卡号姓名
	 */
	@Column(name = "DEALER_FULL_NAME")
	private String dealerFullName;

	@Column(name = "DEALER_NO")
	private String dealerNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "FIRST_PO_DATE")
	private Date firstPoDate;

	@Column(name = "FIRST_PO_NO")
	private String firstPoNo;

	@Column(name = "IS_ALLOW_DIRECT_SELLING")
	private boolean allowDirectSelling;

	@Column(name = "IS_ALLOW_PO")
	private boolean allowPo;

	@Column(name = "IS_ALLOW_SO_REBATE")
	private boolean allowSoRebate;

	@Column(name = "IS_ALLOW_SO")
	private boolean allowSo;

	@Column(name = "STORE_ADDR_CITY")
	private String storeAddrCity;

	@Column(name = "STORE_ADDR_COUNTY")
	private String storeAddrCounty;

	@Column(name = "STORE_ADDR_PROVINCE")
	private String storeAddrProvince;

	@Column(name = "STORE_ADDR_TAIL_01")
	private String storeAddrTail01;

	@Column(name = "STORE_ADDR_TAIL_02")
	private String storeAddrTail02;

	@Column(name = "STORE_ADDR_ZIP_CODE")
	private String storeAddrZipCode;

	@Column(name = "STORE_AUTH_CODE")
	private String storeAuthCode;

	@Column(name = "STORE_CLASS_01")
	private String storeClass01;

	@Column(name = "STORE_FAX_NO")
	private String storeFaxNo;

	@Column(name = "STORE_MOBILE")
	private String storeMobile;

	@Column(name = "STORE_NO")
	private String storeNo;

	@Column(name = "STORE_PRINCIPAL")
	private String storePrincipal;

	@Column(name = "STORE_RUN_STATUS")
	private String storeRunStatus;

	@Column(name = "STORE_RUN_TYPE")
	private String storeRunType;

	@Column(name = "STORE_TELE")
	private String storeTele;

	@Column(name = "STORE_TELES_01")
	private String storeTeles01;

	@Column(name = "STORE_TELES_02")
	private String storeTeles02;

	/**是否服务网点*/
	@Column(name = "IS_SERVICE_SPOT")
	private boolean serviceSpot;

	/** 是否有陈列产品资格 */
	@Column(name = "IS_ALLOW_DISPLAY")
	private int isAllowDisplay;

	/**
	 * 报备二期  zdm 2016-8-8
	 */
	@Transient
	private String fullName;

	@Column(name = "STAR_LEVEL_BEGIN_PERIOD")
	private String starLevelBeginPeriod;

	@Column(name = "STAR_LEVEL_END_PERIOD")
	private String starLevelEndPeriod;

	@Column(name = "COMMENTS")
	private String comments;

}
package org.macula.cloud.logistics.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractVersionAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "LC_CN_ADDRESS_1")
public class CnAddress extends AbstractVersionAuditable<Long> {

	@Column(name = "NAME")
	private String name;
	@Column(name = "CODE")
	private String code;
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	@Column(name = "INACTIVE_DATE")
	private Date inactiveDate;
	@Column(name = "PROV")
	private String prov;
	@Column(name = "PROV_ID")
	private String provId;
	@Column(name = "CITY")
	private String city;
	@Column(name = "CITY_ID")
	private String cityId;
	@Column(name = "DISTRICT")
	private String district;
	@Column(name = "DISTRICT_ID")
	private String districtId;
	@Column(name = "TOWN")
	private String town;
	@Column(name = "TOWN_ID")
	private String townId;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "ADDRESS_TYPE")
	private Integer addressType;
	@Column(name = "PARENT_CODE")
	private String parentCode;
	@Column(name = "DELETED_FLAG")
	private Boolean deletedFlag;
	@Column(name = "LEAF_FLAG")
	private Boolean leafFlag;
	@Column(name = "SHOW_FLAG")
	private Boolean showFlag;
	@Column(name = "DOWNTOWN_FLAG")
	private Boolean downtownFlag;

}
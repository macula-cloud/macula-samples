package org.macula.cloud.logistics.domain;

import org.macula.cloud.core.domain.AbstractVersionAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name = "LC_HOME_ADDRESS")
public class HomeAddress extends AbstractVersionAuditable<Long> {

	private String type;
	private String areaCode;
	private String address;

	private String gbssProv;
	private String gbssCity;
	private String gbssDistrict;
	private String gbssTown;
	private String gbssAddr;

	private String prov;
	private String provId;
	private String city;
	private String cityId;
	private String district;
	private String districtId;
	private String town;
	private String townId;

	private String status;
}

package org.macula.cloud.logistics.domain;

import org.macula.cloud.core.domain.AbstractVersionAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name = "LC_STORE_ADDRESS")
public class StoreAddress extends AbstractVersionAuditable<Long> {

	private String areaCode;
	private String address;

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

package org.macula.cloud.logistics.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Gbss2CainiaoResponse extends LogisticsResponse {

	private static final long serialVersionUID = 1L;

	private String prov;
	private String provId;
	private String city;
	private String cityId;
	private String district;
	private String districtId;
	private String town;
	private String townId;
}

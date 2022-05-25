package org.macula.cloud.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.core.domain.AbstractVersionAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LC_GBSS_CAINIAO")
public class Gbss2Cainiao extends AbstractVersionAuditable<Long> {

	private String areaCode;
	private String areaDesc;
	private String areaMemo;
	private String areaType;
	private String parentAreaCode;
	private Date effectiveDate;
	private Date inactiveDate;
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
	private String divisionId;
	private String batchNo;

	public String divisionId() {
		if (divisionId != null) {
			return divisionId;
		}
		if (townId != null) {
			return divisionId = townId;
		}
		if (districtId != null) {
			return divisionId = districtId;
		}
		if (city != null) {
			return divisionId = districtId;
		}
		if (provId != null) {
			return divisionId = provId;
		}
		return null;
	}
}

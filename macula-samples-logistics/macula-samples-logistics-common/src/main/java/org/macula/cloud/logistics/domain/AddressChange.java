package org.macula.cloud.logistics.domain;

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
@Table(name = "LC_ADDRESS_CHANGE")
public class AddressChange extends AbstractVersionAuditable<Long> {

	private String offset;
	private String publishVersion;
	private String publishStatus;
	private String publishTime;

	private String townName;
	private String provName;
	private String changeType;
	private String cityId;
	private String townId;
	private String changeDetails;
	private String hasDetail;
	private String cityName;
	private String countyId;
	private String changeInfo;
	private String areaType;
	private String provId;
	private String countyName;
}

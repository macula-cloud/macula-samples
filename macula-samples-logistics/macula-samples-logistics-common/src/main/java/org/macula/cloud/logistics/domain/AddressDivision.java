package org.macula.cloud.logistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.core.domain.AbstractAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LC_ADDRESS_DIVISION")
public class AddressDivision extends AbstractAuditable<Long> {

	private String divisionCode;
	private int divisionLevel;
	private String pinyin;
	private String divisionName;
	private String divisionTname;
	private String divisionId;
	private String divisionAbbName;
	private boolean deleted;
	private String version;
	private String parentId;

}

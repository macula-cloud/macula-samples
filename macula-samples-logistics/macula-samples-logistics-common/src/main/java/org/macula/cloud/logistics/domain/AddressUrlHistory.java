package org.macula.cloud.logistics.domain;

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
@Table(name = "LC_ADDRESS_URL_HISTORY_1")
public class AddressUrlHistory extends AbstractVersionAuditable<Long> {

	@Column(name = "EXCEL_URL")
	private String excelUrl;
	@Column(name = "LATEST_FLAG")
	private Boolean latestFlag;

}

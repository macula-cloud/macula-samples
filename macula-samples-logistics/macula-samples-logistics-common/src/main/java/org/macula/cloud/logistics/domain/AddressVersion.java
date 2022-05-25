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
@Table(name = "LC_ADDRESS_VERSION")
public class AddressVersion extends AbstractVersionAuditable<Long> {

	private String publishTime;
	private String publishVersion;
	private String description;
	private String publishStatus;

	private String offset;
	private boolean currentVersion;

}

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
@Table(name = "LC_SETTING_URL_HISTORY_1")
public class SettingUrlHistory extends AbstractVersionAuditable<Long> {

	private String excelUrl;
	private Boolean latestFlag;
}

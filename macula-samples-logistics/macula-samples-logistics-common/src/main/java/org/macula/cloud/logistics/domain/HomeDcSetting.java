package org.macula.cloud.logistics.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "LC_HOME_DC_SETTING_1")
public class HomeDcSetting extends AbstractVersionAuditable<Long> {
	@Column(name = "ADDRESS_CODE")
	private String addressCode;
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	@Column(name = "INACTIVE_DATE")
	private Date inactiveDate;
	@Column(name = "REMARK")
	private String remark;
	@Column(name = "START_UP_COST1")
	private Long startUpCost1;
	@Column(name = "START_UP_COST2")
	private Long startUpCost2;
	@Column(name = "START_UP_COST3")
	private Long startUpCost3;
	@Column(name = "WAREHOUSE_LEVEL1")
	private String warehouseLevel1;
	@Column(name = "WAREHOUSE_LEVEL2")
	private String warehouseLevel2;
	@Column(name = "WAREHOUSE_LEVEL3")
	private String warehouseLevel3;
	@Column(name = "EDIT_FLAG")
	private Boolean editFlag;

	@Transient
	private String address;
	@Transient
	private Integer addressType;
	@Transient
	private String status;

}

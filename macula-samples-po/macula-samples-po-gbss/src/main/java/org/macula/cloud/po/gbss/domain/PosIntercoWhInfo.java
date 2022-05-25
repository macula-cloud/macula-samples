/**
 * PosIntercoWhInfo.java 2012-10-19
 */
package org.macula.cloud.po.gbss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>PosIntercoWhInfo</b> 是自营店内部往来虚拟货仓定义表
 * </p>
 *
 
 
 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "POS_INTERCO_WH_INFO")
public class PosIntercoWhInfo extends LegacyUpdateable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "POS_INTERCO_WH_INFO_ID_GENERATOR", sequenceName = "SEQ_POS_INTERCO_WH_INFO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POS_INTERCO_WH_INFO_ID_GENERATOR")
	private Long id;

	private String comments;

	@Column(name = "POS_VIRTUAL_WH_INFO")
	private String posVirtualWhInfo;

	@Column(name = "POS_WH_INFO")
	private String posWhInfo;

	public PosIntercoWhInfo() {
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosVirtualWhInfo() {
		return this.posVirtualWhInfo;
	}

	public void setPosVirtualWhInfo(String posVirtualWhInfo) {
		this.posVirtualWhInfo = posVirtualWhInfo;
	}

	public String getPosWhInfo() {
		return this.posWhInfo;
	}

	public void setPosWhInfo(String posWhInfo) {
		this.posWhInfo = posWhInfo;
	}

}

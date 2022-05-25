/**
 * PosIntercoListDetail.java 2012-10-19
 */
package org.macula.cloud.po.gbss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>PosIntercoListDetail</b> 是自营店内部往来转储单据信息表
 * </p>
 *
 
 
 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "POS_INTERCO_LIST_DETAIL")
public class PosIntercoListDetail extends LegacyUpdateable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "APP_NO")
	private String appNo;

	private String comments;

	@Column(name = "PO_NO")
	private String poNo;

	public PosIntercoListDetail() {
	}

	public String getAppNo() {
		return this.appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPoNo() {
		return this.poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

}

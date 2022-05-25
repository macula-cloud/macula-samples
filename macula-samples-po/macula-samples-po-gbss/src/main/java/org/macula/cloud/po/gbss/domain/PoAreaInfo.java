/**
 * PoAreaInfo.java 2012-11-21
 */
package org.macula.cloud.po.gbss.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>PoAreaInfo</b> 是物流配送区域定义表
 * </p>
 * 
 
 
 
 * 
 */
@Entity
@Table(name = "PO_AREA_INFO")
@DynamicInsert
@DynamicUpdate
public class PoAreaInfo extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**地区编号*/
	@Column(name = "AREA_CODE", nullable = false, length = 6)
	//	@javax.validation.constraints.Size(min = 1, max = 6)
	private String areaCode;

	/**地区类型*/
	@Column(name = "AREA_TYPE", nullable = false, length = 1)
	//	@javax.validation.constraints.Size(min = 1, max = 1)
	private String areaType;

	/**地区描述*/
	@Column(name = "AREA_DESC", nullable = false, length = 50)
	//	@javax.validation.constraints.Size(min = 1, max = 50)
	private String areaDesc;

	/**地区备注*/
	@Column(name = "AREA_MEMO", nullable = true, length = 200)
	private String areaMemo;

	/**父地区编号*/
	@Column(name = "PARENT_AREA_CODE", nullable = false, length = 6)
	//	@javax.validation.constraints.Size(min = 1, max = 6)
	private String parentAreaCode;

	/**有效开始日*/
	@Column(name = "EFFECTIVE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

	/**失效日*/
	@Column(name = "INACTIVE_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date inactiveDate;

	/**是否显示*/
	@Column(name = "IS_SHOW", nullable = false, length = 1)
	private int isShow;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**创建方式*/
	@Column(name = "CREATED_MODE", nullable = false, length = 2)
	//	@javax.validation.constraints.Size(min = 1, max = 2)
	private String createdMode;

	//	/**
	//	 * @return id 序号
	//	 */
	//	public Long getId() {
	//		return this.id;
	//	}
	//
	//	/**
	//	 * @param id 序号的设置
	//	 */
	//	public void setId(Long id) {
	//		this.id = id;
	//	}

	/**
	 * @return areaCode 地区编号
	 */
	public String getAreaCode() {
		return this.areaCode;
	}

	/**
	 * @param areaCode 地区编号的设置
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return areaType 地区类型
	 */
	public String getAreaType() {
		return this.areaType;
	}

	/**
	 * @param areaType 地区类型的设置
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return areaDesc 地区描述
	 */
	public String getAreaDesc() {
		return this.areaDesc;
	}

	/**
	 * @param areaDesc 地区描述的设置
	 */
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	/**
	 * @return areaMemo 地区备注
	 */
	public String getAreaMemo() {
		return this.areaMemo;
	}

	/**
	 * @param areaMemo 地区备注的设置
	 */
	public void setAreaMemo(String areaMemo) {
		this.areaMemo = areaMemo;
	}

	/**
	 * @return parentAreaCode 父地区编号
	 */
	public String getParentAreaCode() {
		return this.parentAreaCode;
	}

	/**
	 * @param parentAreaCode 父地区编号的设置
	 */
	public void setParentAreaCode(String parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	/**
	 * @return effectiveDate 有效开始日
	 */
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	/**
	 * @param effectiveDate 有效开始日的设置
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return inactiveDate 失效日
	 */
	public Date getInactiveDate() {
		return this.inactiveDate;
	}

	/**
	 * @param inactiveDate 失效日的设置
	 */
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	/**
	 * @return isShow 是否显示
	 */
	public int getIsShow() {
		return this.isShow;
	}

	/**
	 * @param isShow 是否显示的设置
	 */
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	/**
	 * @return comments 备注
	 */
	public String getComments() {
		return this.comments;
	}

	/**
	 * @param comments 备注的设置
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return createdMode 创建方式
	 */
	public String getCreatedMode() {
		return this.createdMode;
	}

	/**
	 * @param createdMode 创建方式的设置
	 */
	public void setCreatedMode(String createdMode) {
		this.createdMode = createdMode;
	}

}

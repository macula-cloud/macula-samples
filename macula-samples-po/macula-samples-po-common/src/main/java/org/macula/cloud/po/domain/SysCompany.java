package org.macula.cloud.po.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.core.domain.AbstractAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * 公司基本信息表
 
 
 * @version
 */
@Getter
@Setter
@Entity
@Table(name = "SYS_COMPANY")
public class SysCompany extends AbstractAuditable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司编号
	 */
	@Column(name = "COMPANY_NO")
	private String companyNo;

	/**
	 * 公司简称
	 */
	@Column(name = "COMPANY_NAME")
	private String companyName;

	/**
	 * SAP销售组织编码
	 */
	@Column(name = "SAP_SALE_ORG_NO")
	private String sapSaleOrgNo;

	/**
	 * 公司全称
	 */
	@Column(name = "COMPANY_FULL_NAME")
	private String companyFullName;

	/**
	 * 法人实体名称
	 */
	@Column(name = "LEGAL_ENTITY_NAME")
	private String legalEntityName;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;

}
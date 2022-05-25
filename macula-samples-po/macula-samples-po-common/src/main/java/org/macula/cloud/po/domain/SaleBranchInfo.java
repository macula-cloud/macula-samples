package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 销售分支机构表
 
 
 * @version
 */
@Getter
@Setter
@Entity
@Table(name = "SALE_BRANCH_INFO")
public class SaleBranchInfo extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 销售分支机构简称
	 */
	@Column(name = "SALE_BRANCH_DESC")
	private String saleBranchDesc;

	/**
	 * 销售分支机构全称
	 */
	@Column(name = "SALE_BRANCH_DESC2")
	private String saleBranchDesc2;

	/**
	 * 销售分支机构代号
	 */
	@Column(name = "SALE_BRANCH_NO")
	private String saleBranchNo;

	/**
	 * 销售机构代号
	 */
	@Column(name = "SALE_ORG_CODE")
	private String saleOrgCode;

	/**
	 * 销售大区代号
	 */
	@Column(name = "SALE_REGION_NO")
	private String saleRegionNo;

	/**
	 * SAP利润中心
	 */
	@Column(name = "SAP_PROFIT_CENTER")
	private String sapProfitCenter;

	/**
	 * 显示顺序号
	 */
	@Column(name = "SHOW_LINE_NO")
	private BigDecimal showLineNo;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;

}
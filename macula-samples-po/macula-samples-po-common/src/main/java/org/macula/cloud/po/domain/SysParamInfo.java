package org.macula.cloud.po.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统参数信息
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SYS_PARAM_INFO")
public class SysParamInfo extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 参数代码
	 */
	@Column(name = "PARA_CODE")
	private String paraCode;

	/**
	 * 参数默认值
	 */
	@Column(name = "PARA_DEFAULT")
	private String paraDefault;

	/**
	 * 参数说明
	 */
	@Column(name = "PARA_DESC")
	private String paraDesc;

	/**
	 * 参数范围
	 */
	@Column(name = "PARA_SCOPE")
	private String paraScope;

	/**
	 * 参数所属系统
	 */
	@Column(name = "PARA_SYSTEM")
	private String paraSystem;

	/**
	 * 参数类型
	 */
	@Column(name = "PARA_TYPE")
	private String paraType;

	/**
	 * 参数值
	 */
	@Column(name = "PARA_VALUE")
	private String paraValue;

	@Column(name = "COMMENTS")
	private String comments;

}
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
 * 发送短信级别表
 
 
 * @version
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_MESSAGE_LEVEL")
@Getter
@Setter
public class PoMessageLevel extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户编码
	 */
	@Column(name = "USER_ID")
	private Long userId;

	/**
	 * 用户名
	 */
	@Column(name = "USER_NAME")
	private String userName;

	/**
	 * 手机号码
	 */
	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "EMAIL")
	private String email;
	/**
	 * 级别
	 */
	@Column(name = "LEVEL")
	private String level;
	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;
}
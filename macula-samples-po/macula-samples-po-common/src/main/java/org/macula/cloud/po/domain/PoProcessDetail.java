package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractVersionAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * OMS 推送流程节点状态记录
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_PROCESS_DETAIL")
@Getter
@Setter
public class PoProcessDetail extends AbstractVersionAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	@Column(name = "PO_NO")
	private String poNo;
	/**
	 * 处理状态
	 */
	@Column(name = "PROC_STATE")
	private String procState;

	/**
	 * 处理时间
	 */
	@Column(name = "PROC_TIME")
	private Date procTime;

	/**
	 * 处理备注
	 */
	@Column(name = "COMMENTS")
	private String comments;

}
package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractVersionAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * OMS流程日志记录表
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_PROCESS_MASTER")
@Getter
@Setter
public class PoProcessMaster extends AbstractVersionAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	@Column(name = "PO_NO")
	private String poNo;

	/**
	 * 订单流程号
	 */
	@Column(name = "PO_PROCESS_CODE")
	private String poProcessCode;

	/**
	 * PO来源
	 */
	@Column(name = "PO_SOURCE")
	private String poSource;

	/**
	 * 来源id
	 */
	@Column(name = "REF_SOURCE_ID")
	private Long refSourceId;

	/**
	 * 来源id
	 */
	@Column(name = "REF_SOURCE_NO")
	private String refSourceNo;

	/**
	 * 当前状态
	 */
	@Column(name = "STATUS")
	private String status;
	/**
	 * 当前状态时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STATUS_TIME")
	private Date statusTime;

	/**
	 * SAP返回单号
	 */
	@Column(name = "SALES_DOCUMENT")
	private String salesDocument;

	/**
	 * MQ返回SAP单号发送消息ID
	 */
	@Column(name = "SEND_MQ_ID")
	private String sendMessageId;

	/**
	 * 失败次数
	 */
	@Column(name = "ERROR_TIMES")
	private int errorTimes = 0;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;

}
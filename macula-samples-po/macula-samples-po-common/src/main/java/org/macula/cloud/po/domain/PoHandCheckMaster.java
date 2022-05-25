package org.macula.cloud.po.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.AbstractVersionAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * OMS和业务平台GBSS手动对账数据主表
 
 
 * @version
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_HAND_CHECK_MASTER")
@Getter
@Setter
public class PoHandCheckMaster extends AbstractVersionAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 开始时间:取的是po_master中的：销售单据输入时间poEntryTime(查询时是闭区间)
	 */
	@Column(name = "START_TIME")
	private Date startTime;
	/**
	 * 结束时间:取的是po_master中的：销售单据输入时间poEntryTime(查询时是开区间)
	 */
	@Column(name = "END_TIME")
	private Date endTime;
	/**
	 * 业务平台订单总量
	 */
	@Column(name = "GBSS_PO_COUNT")
	private Long gbssPoCount = 0L;
	/**
	 * 业务平台订单总金额
	 */
	@Column(name = "GBSS_PO_AMOUNT")
	private BigDecimal gbssPoAmount = new BigDecimal(0);;
	/**
	 * OMS订单总量
	 */
	@Column(name = "OMS_PO_COUNT")
	private Long omsPoCount = 0L;
	/**
	 * OMS订单总金额
	 */
	@Column(name = "OMS_PO_AMOUNT")
	private BigDecimal omsPoAmount = new BigDecimal(0);
	/**
	 * 忽略订单总量
	 */
	@Column(name = "IGNORE_PO_COUNT")
	private Long ignorePoCount = 0L;
	/**
	 * 忽略对账总金额
	 */
	@Column(name = "ignore_po_amount")
	private BigDecimal ignorePoAmount = new BigDecimal(0);
	/**
	 * GBSS和OMS数据对账状态:0:不一致;1:一致
	 */
	@Column(name = "SYN_STATUS")
	private String synStatus;
	/**
	 * 重新定时对账次数(超过3次不再进行定时重新对账,并发送告警通知)
	 */
	@Column(name = "CHECK_NUMBER")
	private Integer checkNumber;

}
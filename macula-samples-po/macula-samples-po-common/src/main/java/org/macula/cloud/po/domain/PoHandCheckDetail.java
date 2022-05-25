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

/**
 * OMS和业务平台GBSS手动对账数据明细表
 
 
 * @version
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_HAND_CHECK_DETAIL")
@Getter
@Setter
public class PoHandCheckDetail extends AbstractVersionAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联MASTER ID
	 */
	@Column(name = "CHECK_MASTER_ID")
	private Long checkMasterId;
	/**
	 * 业务平台订单号
	 */
	@Column(name = "GBSS_PO_NO")
	private String gbssPoNo;
	/**
	 * 业务平台订单金额
	 */
	@Column(name = "GBSS_PO_AMOUNT")
	private BigDecimal gbssPoAmount = new BigDecimal(0);
	/**
	 * 业务平台SAP单号
	 */
	@Column(name = "GBSS_SAP_DOC_NO")
	private String gbssSapDocNo;
	/**
	 * GBSS销售单据状态
	 */
	@Column(name = "gbss_po_status")
	private String gbssPoStatus;
	/**
	 * OMS订单号
	 */
	@Column(name = "OMS_PO_NO")
	private String omsPoNo;
	/**
	 * OMS订单金额
	 */
	@Column(name = "OMS_PO_AMOUNT")
	private BigDecimal omsPoAmount = new BigDecimal(0);
	/**
	 * OMS的SAP单号
	 */
	@Column(name = "OMS_SAP_DOC_NO")
	private String omsSapDocNo;
	/**
	 * OMS销售单据状态
	 */
	@Column(name = "oms_po_status")
	private String omsPoStatus;
	/**
	 * GBSS和OMS数据对账状态:0:不一致;1:一致;2:手动忽略对账
	 */
	@Column(name = "SYN_STATUS")
	private String synStatus;
	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;
}
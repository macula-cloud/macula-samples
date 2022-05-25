package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * 自营店交易流水表
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "POS_ACC_TRAN_DETAIL")
public class PosAccTranDetail extends LegacyAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**POS交易日期*/
	@Temporal(TemporalType.DATE)
	@Column(name = "POS_TRAN_DATE", nullable = false)
	private Date posTranDate;

	/**POS自营店号*/
	@Column(name = "POS_STORE_NO", nullable = false, length = 15)
	private String posStoreNo;

	/**POS收银员*/
	@Column(name = "POS_USER_NAME", nullable = false, length = 4)
	private String posUserName;

	/**POS机号*/
	@Column(name = "POS_NO", nullable = false, length = 1)
	private String posNo = "1";

	/**POS交易项目*/
	@Column(name = "POS_TRAN_TYPE", nullable = false, length = 3)
	private String posTranType;

	/**POS交易金额*/
	@Column(name = "POS_TRAN_AMT", nullable = false, length = 11)
	private BigDecimal posTranAmt;

	/**POS支付类型*/
	@Column(name = "POS_PAYMENT_TYPE", nullable = false, length = 6)
	private String posPaymentType;

	/**POS交易状态*/
	@Column(name = "POS_TRAN_STATUS", nullable = false, length = 1)
	private String posTranStatus;

	/**参考单号*/
	@Column(name = "REF_DOC_NO", nullable = false, length = 15)
	private String refDocNo;

	/**POS交易确认时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "POS_ACK_TIME", nullable = true)
	private Date posAckTime;

	/**POS交易确认人*/
	@Column(name = "POS_ACK_BY", nullable = true, length = 20)
	private String posAckBy;

	/**SAP记账标志*/
	@Column(name = "SAP_POSTING_FLAG", nullable = false, length = 1)
	private String sapPostingFlag;

	/**SAP记账日期*/
	@Temporal(TemporalType.DATE)
	@Column(name = "SAP_POSTING_DATE", nullable = true)
	private Date sapPostingDate;

	/**SAP记账凭证*/
	@Column(name = "SAP_POSTING_DOC_NO", nullable = true, length = 20)
	private String sapPostingDocNo;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

}

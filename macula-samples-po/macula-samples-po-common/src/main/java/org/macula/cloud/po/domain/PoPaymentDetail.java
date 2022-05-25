/**
 * PoPaymentDetail.java 2016-01-26
 */
package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 订货支付信息
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_PAYMENT_DETAIL")
public class PoPaymentDetail extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = -3078081878310568212L;

	/**订货单号*/
	@Column(name = "PO_NO")
	private String poNo;

	/**支付类型*/
	@Column(name = "PO_PAYMENT_TYPE")
	private String poPaymentType;

	/**支付金额*/
	@Column(name = "PO_PAYMENT_AMT")
	private BigDecimal poPaymentAmt;

	/**支付卡号*/
	@Column(name = "PO_PAYMENT_DEALER_NO")
	private String poPaymentDealerNo;

	/**账户处理类型*/
	@Column(name = "ACC_TRAN_TYPE")
	private String accTranType;

	/**在线支付交易方式*/
	@Column(name = "EPAY_AGENT_CODE")
	private String epayAgentCode;

	/**在线支付卡号*/
	@Column(name = "EPAY_CARD_NO")
	private String epayCardNo;

	/**订单抵扣点数*/
	@Column(name = "DISCOUNT_POINT")
	private BigDecimal discountPoint;

	/**相关记录信息*/
	@Column(name = "REF_TRAN_INFO")
	private String refTranInfo;

	/**备注*/
	@Column(name = "COMMENTS")
	private String comments;

}

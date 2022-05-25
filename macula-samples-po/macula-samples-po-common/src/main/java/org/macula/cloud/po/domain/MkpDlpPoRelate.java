package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyAuditable;

import lombok.Getter;
import lombok.Setter;

/**
 * 纷享荟订单积分联系表
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "MKP_DLP_PO_RELATE")
public class MkpDlpPoRelate extends LegacyAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**销售单号*/
	@Column(name = "PO_NO")
	private String poNo;

	/**店铺编号*/
	@Column(name = "STORE_NO")
	private String storeNo;

	/**会员卡号*/
	@Column(name = "DEALER_NO")
	private String dealerNo;

	/**配送方式*/
	@Column(name = "DELIVERY_TYPE")
	private String deliveryType;

	/**现金支付总金额*/
	@Column(name = "CASH_AMT")
	private BigDecimal cashAmt;

	/**积分金额*/
	@Column(name = "TOTAL_DLP_AMT")
	private BigDecimal totalDlpAmt;

	/**积分折扣金额*/
	@Column(name = "TOTAL_DISCOUNT_AMT")
	private BigDecimal totalDiscountAmt;

	/**运费金额*/
	@Column(name = "TRANSPORT_AMT")
	private BigDecimal transportAmt;

	/**备注*/
	@Column(name = "COMMENTS")
	private String comments;

}

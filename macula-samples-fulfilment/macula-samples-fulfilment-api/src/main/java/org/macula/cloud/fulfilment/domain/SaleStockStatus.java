package org.macula.cloud.fulfilment.domain;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SALE_STOCK_STATUS")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class SaleStockStatus extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "ACC_DATE")
	private Date accDate;

	@Column(name = "THIS_ADJ_QTY_02")
	private BigDecimal thisAdjQty02;

	@Column(name = "THIS_ADJ_QTY_01")
	private BigDecimal thisAdjQty01;

	@Column(name = "THIS_PO_QTY")
	private BigDecimal thisPoQty;

	@Column(name = "LAST_BALANCE_QTY")
	private BigDecimal lastBalanceQty;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "WH_LOC_CODE")
	private String whLocCode;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "BALANCE_QTY")
	private BigDecimal balanceQty;

	@Column(name = "REF_LAST_PO_NO")
	private String refLastPoNo;

	@Column(name = "COMMENTS")
	private String comments;

}

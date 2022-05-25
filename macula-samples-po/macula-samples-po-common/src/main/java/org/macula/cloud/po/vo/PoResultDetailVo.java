package org.macula.cloud.po.vo;

import java.io.Serializable;
import java.util.List;

import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.macula.cloud.po.domain.PoAddrDetail;
import org.macula.cloud.po.domain.PoDetail;
import org.macula.cloud.po.domain.PoDetailDiscount;
import org.macula.cloud.po.domain.PoHeader;
import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.domain.PoPaymentDetail;
import org.macula.cloud.po.domain.PosAccTranDetail;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装根据poNo从GBSS查询的数据明细(多个表数据)
 */
@Getter
@Setter
public class PoResultDetailVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean directOrder;
	private String preOrderDealerNo;

	// 1:订单信息
	@JsonProperty(value = "poMasterResult")
	private PoMaster poMaster;

	// 2:订单明细信息
	@JsonProperty(value = "poDetailResults")
	private List<PoDetail> poDetails;

	// 3:订货折扣明细集合
	@JsonProperty(value = "poDetailDiscountResults")
	private List<PoDetailDiscount> poDetailDiscounts;

	// 4:自营店交易流水表
	@JsonProperty(value = "posAccTranDetailResults")
	private List<PosAccTranDetail> posAccTranDetails;

	// 5:订货支付信息
	@JsonProperty(value = "poPaymentDetailResults")
	private List<PoPaymentDetail> poPaymentDetails;

	// 6:总公司普通单 纷享荟二期
	@JsonProperty(value = "mkpDlpPoRelateResult")
	private MkpDlpPoRelate mkpDlpPoRelate;

	// 7:企业购增值税发票
	@JsonProperty(value = "poInvoiceInfoResult")
	private PoInvoiceInfo poInvoiceInfo;

	// 8:订单配送地址信息
	@JsonProperty(value = "poAddrDetailResult")
	private PoAddrDetail poAddrDetail;

	// 9:用于封装Header需要的相关字段
	@JsonProperty(value = "poHeaderResult")
	private PoHeader poHeader;

	// 10: 该订单关联订单列表
	private List<String> refPoNos;

	// 11: 该订单的参考单据号列表
	private List<String> refSelectedNos;
}

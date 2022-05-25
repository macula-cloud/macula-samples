package org.macula.cloud.po.vo;

import lombok.Getter;
import lombok.Setter;
import org.macula.cloud.po.util.ExcelColumn;

import java.math.BigDecimal;

/**
 * OMS和GBSS对账数据Excel导出用Vo
 
 
 * @version
 */
@Getter
@Setter
public class PoHandCheckDetailVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联MASTER ID
	 */
	@ExcelColumn(value = "对账主单ID", col = 1)
	private Long checkMasterId;
	/**
	 * 业务平台订单号
	 */
	@ExcelColumn(value = "业务平台订单号", col = 2)
	private String gbssPoNo;
	/**
	 * 业务平台订单金额
	 */
	@ExcelColumn(value = "业务平台订单金额", col = 3)
	private BigDecimal gbssPoAmount = new BigDecimal(0);
	/**
	 * 业务平台SAP单号
	 */
	@ExcelColumn(value = "业务平台SAP单号", col = 4)
	private String gbssSapDocNo;
	/**
	 * GBSS销售单据状态
	 */
	@ExcelColumn(value = "GBSS销售单据状态", col = 5)
	private String gbssPoStatus;
	/**
	 * OMS订单号
	 */
	@ExcelColumn(value = "OMS订单号", col = 6)
	private String omsPoNo;
	/**
	 * OMS订单金额
	 */
	@ExcelColumn(value = "OMS订单金额", col = 7)
	private BigDecimal omsPoAmount = new BigDecimal(0);
	/**
	 * OMS的SAP单号
	 */
	@ExcelColumn(value = "OMS的SAP单号", col = 8)
	private String omsSapDocNo;
	/**
	 * OMS销售单据状态
	 */
	@ExcelColumn(value = "OMS销售单据状态", col = 9)
	private String omsPoStatus;
	/**
	 * GBSS和OMS数据对账状态:0:不一致;1:一致;2:手动忽略对账
	 */
	@ExcelColumn(value = "状态", col = 10)
	private String synStatus;
	/**
	 * 备注
	 */
	@ExcelColumn(value = "备注", col = 11)
	private String comments;
}
package org.macula.cloud.po.vo;

import lombok.Getter;
import lombok.Setter;
import org.macula.cloud.po.util.ExcelColumn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OMS和GBSS对账数据Excel导出用Vo
 
 
 * @version
 */
@Getter
@Setter
public class PoHandCheckMasterExportVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 开始时间:取的是po_master中的：销售单据输入时间poEntryTime(查询时是闭区间>=)
	 */
	@ExcelColumn(value = "对账开始时间", col = 1)
	private Date startTime;
	/**
	 * 结束时间:取的是po_master中的：销售单据输入时间poEntryTime(查询时是开区间<)
	 */
	@ExcelColumn(value = "对账结束时间", col = 2)
	private Date endTime;
	/**
	 * GBSS/OMS(订单总量)
	 */
	@ExcelColumn(value = "GBSS/OMS(订单总量)", col = 3)
	private String gbssPoCountAndOmsPoCount;
	/**
	 * GBSS/OMS(订单总金额)
	 */
	@ExcelColumn(value = "GBSS/OMS(订单总金额)", col = 4)
	private String gbssPoAmountAndOmsPoAmount;
	/**
	 * 业务平台订单总量
	 */
	@ExcelColumn(value = "GBSS业务平台订单总量", col = 1)
	private Long gbssPoCount = 0L;
	/**
	 * 业务平台订单总金额
	 */
	@ExcelColumn(value = "GBSS业务平台订单总金额", col = 1)
	private BigDecimal gbssPoAmount = new BigDecimal(0);;
	/**
	 * OMS订单总量
	 */
	@ExcelColumn(value = "OMS订单总量", col = 1)
	private Long omsPoCount = 0L;
	/**
	 * OMS订单总金额
	 */
	@ExcelColumn(value = "OMS订单总金额", col = 1)
	private BigDecimal omsPoAmount = new BigDecimal(0);
	/**
	 * GBSS和OMS数据对账状态:0:不一致;1:一致
	 */
	@ExcelColumn(value = "对账结果", col = 5)
	private String synStatus;

}
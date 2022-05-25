package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.TbReturn;
import org.macula.cloud.sap.AbstractBapi;

/**
 * 
 *
 */
@Bapi("ZSD_SALESORDER_CHANGE_GBSS")
public class SapOrderSaveShipTypeBapi extends AbstractBapi {

	/**
	 * sapNo
	 */
	@Import
	@Parameter("P_SALESORDER")
	private String pSalesOrder;

	/**
	 * 提货类型
	 */
	@Import
	@Parameter("P_SHIP_TYPE")
	private String pShipType;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<TbReturn> tbReturn;

	/**
	 * 获取 sapNo
	 *
	 * @return pSalesOrder sapNo
	 */
	public String getPSalesOrder() {
		return this.pSalesOrder;
	}

	/**
	 * 设置 sapNo
	 *
	 * @param pSalesOrder sapNo
	 */
	public void setPSalesOrder(String pSalesOrder) {
		this.pSalesOrder = pSalesOrder;
	}

	/**
	 * 获取 提货类型
	 *
	 * @return pShipType 提货类型
	 */
	public String getPShipType() {
		return this.pShipType;
	}

	/**
	 * 设置 提货类型
	 *
	 * @param pShipType 提货类型
	 */
	public void setPShipType(String pShipType) {
		this.pShipType = pShipType;
	}

	/**
	 * 获取 sap返回的信息表
	 *
	 * @return tbReturn sap返回的信息表
	 */
	public List<TbReturn> getTbReturn() {
		return this.tbReturn;
	}

	/**
	 * 设置 sap返回的信息表
	 *
	 * @param tbReturn sap返回的信息表
	 */
	public void setTbReturn(List<TbReturn> tbReturn) {
		this.tbReturn = tbReturn;
	}
}

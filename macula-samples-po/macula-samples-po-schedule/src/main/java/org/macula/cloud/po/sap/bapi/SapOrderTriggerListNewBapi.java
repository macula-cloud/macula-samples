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
@Bapi("ZTRG_SD_I_111_TR_POQUERY")
public class SapOrderTriggerListNewBapi extends AbstractBapi {

	/**
	 * 订单主表
	 */
	@Import
	@Parameter(value = "I_PURCH_NO_C")
	private String ipurchNoC;

	/**
	 * 返回的poNos
	 */
	@Table
	@Parameter("TB_PO")
	private List<TbReturn> SapOrderTriggerListNew;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<TbReturn> SapOrderTriggerListNewMsg;

	/**
	 * 获取 订单主表
	 *
	 * @return ipurchNoC 订单主表
	 */
	public String getIpurchNoC() {
		return this.ipurchNoC;
	}

	/**
	 * 设置 订单主表
	 *
	 * @param ipurchNoC 订单主表
	 */
	public void setIpurchNoC(String ipurchNoC) {
		this.ipurchNoC = ipurchNoC;
	}

	/**
	 * 获取 返回的poNos
	 *
	 * @return SapOrderTriggerListNewBapi 返回的poNos
	 */
	public List<TbReturn> getSapOrderTriggerListNew() {
		return this.SapOrderTriggerListNew;
	}

	/**
	 * 设置 返回的poNos
	 *
	 * @param SapOrderTriggerListNew 返回的poNos
	 */
	public void setSapOrderTriggerListNew(List<TbReturn> SapOrderTriggerListNew) {
		this.SapOrderTriggerListNew = SapOrderTriggerListNew;
	}

	/**
	 * 获取 sap返回的信息表
	 *
	 * @return SapOrderTriggerListNewMsg sap返回的信息表
	 */
	public List<TbReturn> getSapOrderTriggerListNewMsg() {
		return this.SapOrderTriggerListNewMsg;
	}

	/**
	 * 设置 sap返回的信息表
	 *
	 * @param SapOrderTriggerListNewMsg sap返回的信息表
	 */
	public void setSapOrderTriggerListNewMsg(List<TbReturn> SapOrderTriggerListNewMsg) {
		this.SapOrderTriggerListNewMsg = SapOrderTriggerListNewMsg;
	}
}

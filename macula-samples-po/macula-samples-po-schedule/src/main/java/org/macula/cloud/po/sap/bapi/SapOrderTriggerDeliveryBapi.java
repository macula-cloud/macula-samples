package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.TbPo;
import org.macula.cloud.po.sap.model.TbReturn;
import org.macula.cloud.sap.AbstractBapi;

/**
 * 
 *
 */
@Bapi("ZTRG_SD_I_111_TR_GBCREATE")
public class SapOrderTriggerDeliveryBapi extends AbstractBapi {

	@Table
	@Parameter(value = "TB_ORDER")
	private List<TbPo> purchNoCs;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<TbReturn> SapOrderTriggerDeliveryReturn;

	/**
	 * 获取 @Table    @Parameter(value = "TB_ORDER")
	 *
	 * @return purchNoCs @Table    @Parameter(value = "TB_ORDER")
	 */
	public List<TbPo> getPurchNoCs() {
		return this.purchNoCs;
	}

	/**
	 * 设置 @Table    @Parameter(value = "TB_ORDER")
	 *
	 * @param purchNoCs @Table    @Parameter(value = "TB_ORDER")
	 */
	public void setPurchNoCs(List<TbPo> purchNoCs) {
		this.purchNoCs = purchNoCs;
	}

	/**
	 * 获取 sap返回的信息表
	 *
	 * @return SapOrderTriggerDeliveryReturn sap返回的信息表
	 */
	public List<TbReturn> getSapOrderTriggerDeliveryReturn() {
		return this.SapOrderTriggerDeliveryReturn;
	}

	/**
	 * 设置 sap返回的信息表
	 *
	 * @param SapOrderTriggerDeliveryReturn sap返回的信息表
	 */
	public void setSapOrderTriggerDeliveryReturn(List<TbReturn> SapOrderTriggerDeliveryReturn) {
		this.SapOrderTriggerDeliveryReturn = SapOrderTriggerDeliveryReturn;
	}
}

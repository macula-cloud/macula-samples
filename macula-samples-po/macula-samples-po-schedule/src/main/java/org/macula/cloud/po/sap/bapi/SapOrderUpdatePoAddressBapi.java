package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.ParameterType;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.IpoHeaderMod;
import org.macula.cloud.po.sap.model.TbReturn;
import org.macula.cloud.sap.AbstractBapi;

/**
 * 
 *
 */
@Bapi("ZTRG_SD_I_105_SO_MOD")
public class SapOrderUpdatePoAddressBapi extends AbstractBapi {

	/**
	 * 订单主表
	 */
	@Import
	@Parameter(value = "I_PO_HEADER_MOD", type = ParameterType.STRUCTURE)
	private IpoHeaderMod ipoHeaderMod;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<TbReturn> tbReturn;

	/**
	 * 获取 订单主表
	 *
	 * @return ipoHeaderMod 订单主表
	 */
	public IpoHeaderMod getIpoHeaderMod() {
		return this.ipoHeaderMod;
	}

	/**
	 * 设置 订单主表
	 *
	 * @param ipoHeaderMod 订单主表
	 */
	public void setIpoHeaderMod(IpoHeaderMod ipoHeaderMod) {
		this.ipoHeaderMod = ipoHeaderMod;
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

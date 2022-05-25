/**
 * 
 */
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
 */
@Bapi("ZTRG_SD_I_105_SO_MOD2")
public class UntriggeredOrderUpdatePoAddressBapi extends AbstractBapi {

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
	 * @return the ipoHeaderMod
	 */
	public IpoHeaderMod getIpoHeaderMod() {
		return ipoHeaderMod;
	}

	/**
	 * @param ipoHeaderMod the ipoHeaderMod to set
	 */
	public void setIpoHeaderMod(IpoHeaderMod ipoHeaderMod) {
		this.ipoHeaderMod = ipoHeaderMod;
	}

	/**
	 * @return the tbReturn
	 */
	public List<TbReturn> getTbReturn() {
		return tbReturn;
	}

	/**
	 * @param tbReturn the tbReturn to set
	 */
	public void setTbReturn(List<TbReturn> tbReturn) {
		this.tbReturn = tbReturn;
	}
}

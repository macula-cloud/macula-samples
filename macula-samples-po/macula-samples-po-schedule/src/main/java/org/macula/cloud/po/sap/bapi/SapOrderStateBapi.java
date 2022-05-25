package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.SapOrderStateItem;
import org.macula.cloud.po.sap.model.SapOrderStateTbReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZTRG_SD_I_103_SO_STATE")
public class SapOrderStateBapi extends AbstractBapi {

	@Table
	@Parameter("TB_PO")
	private List<SapOrderStateItem> sapOrderStateItemList;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<SapOrderStateTbReturn> sapOrderStateTbReturnList;

	/**
	 * 获取： bare_field_comment
	 */
	public List<SapOrderStateItem> getSapOrderStateItemList() {
		return sapOrderStateItemList;
	}

	/**
	 * 设置： bare_field_comment
	 */
	public void setSapOrderStateItemList(List<SapOrderStateItem> sapOrderStateItemList) {
		this.sapOrderStateItemList = sapOrderStateItemList;
	}

	/**
	 * 获取： sap返回的信息表
	 */
	public List<SapOrderStateTbReturn> getSapOrderStateTbReturnList() {
		return sapOrderStateTbReturnList;
	}

	/**
	 * 设置： sap返回的信息表
	 */
	public void setSapOrderStateTbReturnList(List<SapOrderStateTbReturn> sapOrderStateTbReturnList) {
		this.sapOrderStateTbReturnList = sapOrderStateTbReturnList;
	}

}

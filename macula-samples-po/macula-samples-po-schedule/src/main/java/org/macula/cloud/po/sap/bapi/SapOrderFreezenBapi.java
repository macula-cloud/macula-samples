package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.SapOrderFreezenItem;
import org.macula.cloud.po.sap.model.SapOrderFreezenTbReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZTRG_SD_I_104_SO_FRZ")
public class SapOrderFreezenBapi extends AbstractBapi {

	@Table
	@Parameter("TB_PO")
	private List<SapOrderFreezenItem> sapOrderFreezenItemList;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<SapOrderFreezenTbReturn> sapOrderFreezeTbReturnList;

	/**
	 * 获取：sap返回的信息表
	 */
	public List<SapOrderFreezenTbReturn> getSapOrderFreezeTbReturnList() {
		return sapOrderFreezeTbReturnList;
	}

	/**
	 * 设置：sap返回的信息表
	 */
	public void setSapOrderFreezeTbReturnList(List<SapOrderFreezenTbReturn> sapOrderFreezeTbReturnList) {
		this.sapOrderFreezeTbReturnList = sapOrderFreezeTbReturnList;
	}

	/**
	 * 获取：bare_field_comment
	 */
	public List<SapOrderFreezenItem> getSapOrderFreezenItemList() {
		return sapOrderFreezenItemList;
	}

	/**
	 * 设置：bare_field_comment
	 */
	public void setSapOrderFreezenItemList(List<SapOrderFreezenItem> sapOrderFreezenItemList) {
		this.sapOrderFreezenItemList = sapOrderFreezenItemList;
	}

}

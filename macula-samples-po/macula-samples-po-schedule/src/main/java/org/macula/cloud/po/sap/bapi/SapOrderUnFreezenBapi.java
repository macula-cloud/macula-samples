package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.SapOrderUnFreezenItem;
import org.macula.cloud.po.sap.model.SapOrderUnFreezenTbReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZTRG_SD_I_106_SO_UNFRZ")
public class SapOrderUnFreezenBapi extends AbstractBapi {

	@Table
	@Parameter("TB_PO")
	private List<SapOrderUnFreezenItem> sapOrderUnFreezenItemList;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<SapOrderUnFreezenTbReturn> sapOrderUnFreezeTbReturn;

	/**
	 * 获取：sap返回的信息表
	 */
	public List<SapOrderUnFreezenTbReturn> getSapOrderUnFreezeTbReturn() {
		return sapOrderUnFreezeTbReturn;
	}

	/**
	 * 设置：sap返回的信息表
	 */
	public void setSapOrderUnFreezeTbReturn(List<SapOrderUnFreezenTbReturn> sapOrderUnFreezeTbReturn) {
		this.sapOrderUnFreezeTbReturn = sapOrderUnFreezeTbReturn;
	}

	/**
	 * 获取：bare_field_comment
	 */
	public List<SapOrderUnFreezenItem> getSapOrderUnFreezenItemList() {
		return sapOrderUnFreezenItemList;
	}

	/**
	 * 设置：bare_field_comment
	 */
	public void setSapOrderUnFreezenItemList(List<SapOrderUnFreezenItem> sapOrderUnFreezenItemList) {
		this.sapOrderUnFreezenItemList = sapOrderUnFreezenItemList;
	}

}

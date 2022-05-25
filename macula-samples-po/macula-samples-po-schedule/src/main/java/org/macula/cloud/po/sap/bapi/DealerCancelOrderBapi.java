package org.macula.cloud.po.sap.bapi;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.ParameterType;
import org.macula.cloud.po.sap.model.ExReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZTRG_SD_I_107_SO_CANCEL")
public class DealerCancelOrderBapi extends AbstractBapi {

	/**
	 * P单号
	 */
	@Import
	@Parameter("IM_PURCH_NO_C")
	private String imPurchNoC;

	/**
	 * sap返回的信息表
	 */
	@Export
	@Parameter(value = "EX_RETURN", type = ParameterType.STRUCTURE)
	private ExReturn ExReturn;

	/**
	 * 获取： P单号
	 */
	public String getImPurchNoC() {
		return imPurchNoC;
	}

	/**
	 * 设置： P单号
	 */
	public void setImPurchNoC(String imPurchNoC) {
		this.imPurchNoC = imPurchNoC;
	}

	/**
	 * 获取： sap返回的信息表
	 */
	public ExReturn getExReturn() {
		return ExReturn;
	}

	/**
	 * 设置： sap返回的信息表
	 */
	public void setExReturn(ExReturn exReturn) {
		ExReturn = exReturn;
	}

}

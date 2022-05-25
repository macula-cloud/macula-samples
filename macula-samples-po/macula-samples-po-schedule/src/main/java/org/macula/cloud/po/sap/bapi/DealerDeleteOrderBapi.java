package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.TReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZSDRP0023_FRM_ZREB_DELETE_G")
public class DealerDeleteOrderBapi extends AbstractBapi {

	/**
	 * 要撤单的单号（poNo字段值）
	 */
	@Import
	@org.hibersap.annotations.Parameter("ZPO_VBELV")
	private String zpoVbelv;

	/**
	 * 撤单类型，默认赋值为1即可
	 */
	@Import
	@org.hibersap.annotations.Parameter("ZTYPE")
	private String ztype;

	/**
	 * sap返回信息报文
	 */
	@Table
	@Parameter("TB_RETURN")
	private List<TReturn> treturn;

	public String getZpoVbelv() {
		return zpoVbelv;
	}

	public void setZpoVbelv(String zpoVbelv) {
		this.zpoVbelv = zpoVbelv;
	}

	public String getZtype() {
		return ztype;
	}

	public void setZtype(String ztype) {
		this.ztype = ztype;
	}

	public List<TReturn> getTreturn() {
		return treturn;
	}

	public void setTreturn(List<TReturn> treturn) {
		this.treturn = treturn;
	}

}

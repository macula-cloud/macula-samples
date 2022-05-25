package org.macula.cloud.po.sap.bapi;

import java.util.ArrayList;
import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.ParameterType;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.DateRangeVo;
import org.macula.cloud.po.sap.model.EtData;
import org.macula.cloud.po.sap.model.ExReturnMsg;
import org.macula.cloud.po.sap.model.SelectRangeVo;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZSD_ZREB_STATUS")
public class DealerQaReturnOrderBapi extends AbstractBapi {

	/**
	 * 查询条件，退单号
	 */
	@Table
	@Parameter(value = "IM_BSTKD")
	private List<SelectRangeVo> imBstkd = new ArrayList<>();

	/**
	 * 查询条件，日期
	 */
	@Table
	@Parameter(value = "IM_AUDAT")
	private List<DateRangeVo> imAudat = new ArrayList<>();

	/**
	 * 查询条件，物料号
	 */
	@Table
	@Parameter(value = "IM_MATNR")
	private List<SelectRangeVo> imMatnr;

	/**
	 * 返回消息结果
	 */
	@Export
	@Parameter(value = "EX_RETURN_MSG", type = ParameterType.STRUCTURE)
	private ExReturnMsg exReturnMsg;

	/**
	 * 查询结果
	 */
	@Table
	@Parameter("ET_DATA")
	private List<EtData> etData;

	public List<SelectRangeVo> getImBstkd() {
		return imBstkd;
	}

	public void setImBstkd(List<SelectRangeVo> imBstkd) {
		this.imBstkd = imBstkd;
	}

	public List<DateRangeVo> getImAudat() {
		return imAudat;
	}

	public void setImAudat(List<DateRangeVo> imAudat) {
		this.imAudat = imAudat;
	}

	public ExReturnMsg getExReturnMsg() {
		return exReturnMsg;
	}

	public void setExReturnMsg(ExReturnMsg exReturnMsg) {
		this.exReturnMsg = exReturnMsg;
	}

	public List<EtData> getEtData() {
		return etData;
	}

	public void setEtData(List<EtData> etData) {
		this.etData = etData;
	}

	public List<SelectRangeVo> getImMatnr() {
		return imMatnr;
	}

	public void setImMatnr(List<SelectRangeVo> imMatnr) {
		this.imMatnr = imMatnr;
	}
}

package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.Parameter;

public class SapOrderLogisticsStateUpdateInfo {

	/**运单号*/
	@Parameter("TRANNOSAP")
	private String tranNoSap;
	/**P单号*/
	@Parameter("PURCH_NO")
	private String purchNo;
	/**快递公司*/
	@Parameter("EXPCOMP")
	private String expcomp;
	/**物流状态（实物出仓，物流运输，签收）*/
	@Parameter("LOG_STATE")
	private String logState;

	public String getTranNoSap() {
		return tranNoSap;
	}

	public void setTranNoSap(String tranNoSap) {
		this.tranNoSap = tranNoSap;
	}

	public String getPurchNo() {
		return purchNo;
	}

	public void setPurchNo(String purchNo) {
		this.purchNo = purchNo;
	}

	public String getExpcomp() {
		return expcomp;
	}

	public void setExpcomp(String expcomp) {
		this.expcomp = expcomp;
	}

	public String getLogState() {
		return logState;
	}

	public void setLogState(String logState) {
		this.logState = logState;
	}

}

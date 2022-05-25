package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class SapOrderDeliverTbReturn {
	/**运单号 */
	@Parameter("TRANNO")
	private String tranno;
	/**P单号 */
	@Parameter("PURCH_NO")
	private String purchNo;
	/**消息类型: S 成功,E 错误,W 警告,I 信息,A 中断 */
	@Parameter("TYPE")
	private String type;
	/**号码3,内部使用 */
	@Parameter("NUMBER")
	private String number;
	/**消息文本*/
	@Parameter("MESSAGE")
	private String message;
	/**交货*/
	@Parameter("VBELV")
	private String vbelv;
	/**物料凭证编号*/
	@Parameter("MBLNR")
	private String mblnr;
	/**物料凭证年度*/
	@Parameter("MJAHR")
	private String mjahr;

	public String getTranno() {
		return tranno;
	}

	public void setTranno(String tranno) {
		this.tranno = tranno;
	}

	public String getPurchNo() {
		return purchNo;
	}

	public void setPurchNo(String purchNo) {
		this.purchNo = purchNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVbelv() {
		return vbelv;
	}

	public void setVbelv(String vbelv) {
		this.vbelv = vbelv;
	}

	public String getMblnr() {
		return mblnr;
	}

	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}

	public String getMjahr() {
		return mjahr;
	}

	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}

}

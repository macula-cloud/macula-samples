package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class SapOrderLogisticsStateUpdateTbReturn {
	/**运单号 */
	@Parameter("TRANNO")
	private String tranNo;
	/**P单号 */
	@Parameter("PURCH_NO")
	private String purchNo;
	/**消息类型: S 成功,E 错误,W 警告,I 信息,A 中断 */
	@Parameter("TYPE")
	private String type;
	/**号码3,内部使用*/
	@Parameter("NUMBER")
	private String number;
	/**消息文本*/
	@Parameter("MESSAGE")
	private String message;

	public String getTranNo() {
		return tranNo;
	}

	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
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

}

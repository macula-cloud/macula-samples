/**
 * 
 */
package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 
 *
 */
@BapiStructure
public class TbReturn {

	@Parameter("PURCH_NO_C")
	private String purchNoC;

	@Parameter("TYPE")
	private String type;

	@Parameter("NUMBER")
	private String number;

	@Parameter("MESSAGE")
	private String message;

	@Parameter("TRANNOSAP")
	private String tranNoSap;

	/**
	 * @return the purchNoC
	 */
	public String getPurchNoC() {
		return purchNoC;
	}

	/**
	 * @param purchNoC the purchNoC to set
	 */
	public void setPurchNoC(String purchNoC) {
		this.purchNoC = purchNoC;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取： bare_field_comment
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置： bare_field_comment
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 获取 @Parameter("TRANNOSAP")
	 *
	 * @return tranNoSap @Parameter("TRANNOSAP")
	 */
	public String getTranNoSap() {
		return this.tranNoSap;
	}

	/**
	 * 设置 @Parameter("TRANNOSAP")
	 *
	 * @param tranNoSap @Parameter("TRANNOSAP")
	 */
	public void setTranNoSap(String tranNoSap) {
		this.tranNoSap = tranNoSap;
	}
}

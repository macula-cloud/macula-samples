package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 
 */
@BapiStructure
public class ExReturn {

	@Parameter("PURCH_NO_C")
	private String purchNoC;

	@Parameter("TYPE")
	private String type;

	@Parameter("MESSAGE")
	private String message;

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

}

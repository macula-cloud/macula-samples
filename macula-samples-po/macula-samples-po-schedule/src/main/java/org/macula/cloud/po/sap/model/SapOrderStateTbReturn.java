package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class SapOrderStateTbReturn {

	@Parameter("PURCH_NO_C")
	private String purchNoC;

	@Parameter("NUMBER")
	private String number;

	@Parameter("MESSAGE")
	private String message;

	/**
	 * @return the purchNoC
	 */
	public String getPurchNoC() {
		return purchNoC;
	}

	public void setPurchNoC(String purchNoC) {
		this.purchNoC = purchNoC;
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

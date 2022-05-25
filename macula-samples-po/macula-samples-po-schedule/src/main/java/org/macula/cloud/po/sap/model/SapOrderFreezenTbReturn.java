package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class SapOrderFreezenTbReturn {

	@Parameter("PURCH_NO_C")
	private String purchNoC;

	@Parameter("TYPE")
	private String type;

	@Parameter("MESSAGE")
	private String message;

	public String getPurchNoC() {
		return purchNoC;
	}

	public void setPurchNoC(String purchNoC) {
		this.purchNoC = purchNoC;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

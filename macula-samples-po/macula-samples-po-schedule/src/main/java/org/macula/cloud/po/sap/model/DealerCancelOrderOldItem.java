package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class DealerCancelOrderOldItem {

	@Parameter("PURCH_NO_C")
	private String purchNoC;

	public String getPurchNoC() {
		return purchNoC;
	}

	public void setPurchNoC(String purchNoC) {
		this.purchNoC = purchNoC;
	}

}

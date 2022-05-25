package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 * 
 
 
 */
@BapiStructure
public class TbPo {

	@Parameter("PURCH_NO_C")
	private String purchNoC;

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

}

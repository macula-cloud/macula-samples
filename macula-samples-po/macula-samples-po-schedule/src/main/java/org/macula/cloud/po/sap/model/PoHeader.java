package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

@BapiStructure
public class PoHeader {

	@Parameter("SALESDOCUMENT")
	private String salesdocument;

	public String getSalesdocument() {
		return salesdocument;
	}

	public void setSalesdocument(String salesdocument) {
		this.salesdocument = salesdocument;
	}
}
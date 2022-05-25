package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.SapOrderDeliverInfo;
import org.macula.cloud.po.sap.model.SapOrderDeliverTbReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZGBSS_SD_I_POST")
public class SapOrderDeliverBapi extends AbstractBapi {

	@Import
	@Table
	@Parameter(value = "IT_TRANNO")
	private List<SapOrderDeliverInfo> sapOrderDeliverInfos;

	@Export
	@Table
	@Parameter(value = "ET_TRANINFO")
	private List<SapOrderDeliverTbReturn> sapOrderDeliverTbReturns;

	public List<SapOrderDeliverInfo> getSapOrderDeliverInfos() {
		return sapOrderDeliverInfos;
	}

	public void setSapOrderDeliverInfos(List<SapOrderDeliverInfo> sapOrderDeliverInfos) {
		this.sapOrderDeliverInfos = sapOrderDeliverInfos;
	}

	public List<SapOrderDeliverTbReturn> getSapOrderDeliverTbReturns() {
		return sapOrderDeliverTbReturns;
	}

	public void setSapOrderDeliverTbReturns(List<SapOrderDeliverTbReturn> sapOrderDeliverTbReturns) {
		this.sapOrderDeliverTbReturns = sapOrderDeliverTbReturns;
	}

}

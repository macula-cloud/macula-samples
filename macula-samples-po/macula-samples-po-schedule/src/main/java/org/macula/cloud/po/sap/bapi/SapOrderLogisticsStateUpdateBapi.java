package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.SapOrderLogisticsStateUpdateInfo;
import org.macula.cloud.po.sap.model.SapOrderLogisticsStateUpdateTbReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZGBSS_LOGISTICS_STATUS_UPDATE")
public class SapOrderLogisticsStateUpdateBapi extends AbstractBapi {

	@Import
	@Table
	@Parameter(value = "IT_TRANNO")
	private List<SapOrderLogisticsStateUpdateInfo> sapOrderLogisticsStateUpdateInfos;

	@Export
	@Table
	@Parameter(value = "ET_TRANINFO")
	private List<SapOrderLogisticsStateUpdateTbReturn> sapOrderLogisticsStateUpdateTbReturns;

	public List<SapOrderLogisticsStateUpdateInfo> getSapOrderLogisticsStateUpdateInfos() {
		return sapOrderLogisticsStateUpdateInfos;
	}

	public void setSapOrderLogisticsStateUpdateInfos(List<SapOrderLogisticsStateUpdateInfo> sapOrderLogisticsStateUpdateInfos) {
		this.sapOrderLogisticsStateUpdateInfos = sapOrderLogisticsStateUpdateInfos;
	}

	public List<SapOrderLogisticsStateUpdateTbReturn> getSapOrderLogisticsStateUpdateTbReturns() {
		return sapOrderLogisticsStateUpdateTbReturns;
	}

	public void setSapOrderLogisticsStateUpdateTbReturns(List<SapOrderLogisticsStateUpdateTbReturn> sapOrderLogisticsStateUpdateTbReturns) {
		this.sapOrderLogisticsStateUpdateTbReturns = sapOrderLogisticsStateUpdateTbReturns;
	}

}

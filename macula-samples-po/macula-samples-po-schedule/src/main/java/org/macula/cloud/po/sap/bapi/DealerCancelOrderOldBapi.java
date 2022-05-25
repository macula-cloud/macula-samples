package org.macula.cloud.po.sap.bapi;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.DealerCancelOrderOldItem;
import org.macula.cloud.po.sap.model.DealerCancelOrderOldTbReturn;
import org.macula.cloud.sap.AbstractBapi;

@Bapi("ZTRG_SD_I_107_SO_DEL")
public class DealerCancelOrderOldBapi extends AbstractBapi {

	@Table
	@Parameter("TB_PO")
	private List<DealerCancelOrderOldItem> dealerCancelOrderOldItems;

	@Table
	@Parameter(value = "TB_RETURN")
	private List<DealerCancelOrderOldTbReturn> dealerCancelOrderOldTbReturns;

	public List<DealerCancelOrderOldItem> getDealerCancelOrderOldItem() {
		return dealerCancelOrderOldItems;
	}

	public void setDealerCancelOrderOldItem(List<DealerCancelOrderOldItem> dealerCancelOrderOldItems) {
		this.dealerCancelOrderOldItems = dealerCancelOrderOldItems;
	}

	public List<DealerCancelOrderOldTbReturn> getDealerCancelOrderOldTbReturn() {
		return dealerCancelOrderOldTbReturns;
	}

	public void setDealerCancelOrderOldTbReturn(List<DealerCancelOrderOldTbReturn> dealerCancelOrderOldTbReturns) {
		this.dealerCancelOrderOldTbReturns = dealerCancelOrderOldTbReturns;
	}

}

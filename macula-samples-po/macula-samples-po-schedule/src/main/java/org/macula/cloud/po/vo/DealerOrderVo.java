package org.macula.cloud.po.vo;

import java.util.List;

import org.macula.cloud.po.sap.model.POrderHeader;
import org.macula.cloud.po.sap.model.TCondition;
import org.macula.cloud.po.sap.model.TOrderItems;

/**
 * 上传订单的传入参数vo
 
 
 
 */
public class DealerOrderVo {

	/**
	 * 订单单号，多线程时能取到
	 */
	private String poNo;
	/**
	 * 订单主表
	 */
	private POrderHeader pOrderHeader;

	/**
	 * 定价
	 */
	private List<TCondition> tConditionList;

	/**
	 * 用户信息
	 */
	private List<TOrderItems> tOrderItemsList;

	public POrderHeader getPOrderHeader() {
		return pOrderHeader;
	}

	public void setPOrderHeader(POrderHeader pOrderHeader) {
		this.pOrderHeader = pOrderHeader;
	}

	public List<TCondition> gettConditionList() {
		return tConditionList;
	}

	public void settConditionList(List<TCondition> tConditionList) {
		this.tConditionList = tConditionList;
	}

	public List<TOrderItems> gettOrderItemsList() {
		return tOrderItemsList;
	}

	public void settOrderItemsList(List<TOrderItems> tOrderItemsList) {
		this.tOrderItemsList = tOrderItemsList;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

}

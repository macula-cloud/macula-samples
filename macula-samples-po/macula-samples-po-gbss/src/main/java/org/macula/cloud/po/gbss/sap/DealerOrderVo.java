/**
 * 
 */
package org.macula.cloud.po.gbss.sap;

import java.util.List;

/**
 * <p>
 * <b>DealerOrderVo</b> 上传订单的传入参数vo
 * </p>
 *
 
 
 
 *
 */
public class DealerOrderVo {

	/**
	 * 订单单号，多线程时能取到
	 */
	private String poNo;
	/**
	 * 订单主表
	 */
	private POrderHeader orderHeader;
	/**
	 * 定价
	 */
	private List<TCondition> tConditionList;
	/**
	 * 用户信息
	 */
	private List<TOrderItems> tOrderItemsList;

	public POrderHeader getOrderHeader() {
		return orderHeader;
	}

	public void setOrderHeader(POrderHeader orderHeader) {
		this.orderHeader = orderHeader;
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

package org.macula.cloud.po.gbss.sap;

import java.io.Serializable;

/**
 * <p>
 * <b>GatherGoodsMaster</b>是服务中心捡货处理调用SAP接口 参数VO类

 * </p>捡货处理用
 * 
 
 
 
 */
public class GatherGoodsMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	/**运单号*/
	private String deliverNo;
	/*经销商代码*/
	private String dealerNo;
	/**经销商姓名*/
	private String fullName;
	/**是否主单*/
	private String mainNo;
	/**订单号 */
	private String saleNo;
	/**合并单号*/
	private String uniteNo;

	/**转储单号*预留单号*销售单号*/
	private String dnNo;

	/**部门/名称*成本中心*/
	private String deparmentName;

	/**发货工厂*/
	private String whCode;

	/**送达方 配送地址*/
	private String sendAddress;

	/**地址ID*/
	private String addressID;
	/**制单日期*/
	private String dnCreateTime;

	/**交货日期*/
	private String dnArriveTime;

	private String respkDat;
	private String respkTime;

	/**状态*/
	private String status;

	/**采购组*/
	private String group;

	/**单据类型/名称*移动类型*订单类型*销售类型*/
	private String dnNoType;

	/**订单类型名称*/
	private String dnNoTypeName;

	/**配送方式*/
	private String deliverType;
	/**配送方式名称*/
	private String deliverTypeName;
	/**追加标示*/
	private String addFlag;
	/**选择标识符*/
	private String mark;
	/**新单标示，单独发货*/
	private String newTrans;
	/**是否捡货标示*/
	private Boolean checked;

	/**店铺地址*/
	private String homeCity;

	/**预订货主单标识*/
	private String masterType;

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getDnArriveTime() {
		return dnArriveTime;
	}

	public void setDnArriveTime(String dnArriveTime) {
		this.dnArriveTime = dnArriveTime;
	}

	public String getDnCreateTime() {
		return dnCreateTime;
	}

	public void setDnCreateTime(String dnCreateTime) {
		this.dnCreateTime = dnCreateTime;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getDnNo() {
		return dnNo;
	}

	public void setDnNo(String dnNo) {
		this.dnNo = dnNo;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDeparmentName() {
		return deparmentName;
	}

	public void setDeparmentName(String deparmentName) {
		this.deparmentName = deparmentName;
	}

	public String getDnNoType() {
		return dnNoType;
	}

	public void setDnNoType(String dnNoType) {
		this.dnNoType = dnNoType;
	}

	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	public String getDealerNo() {
		return dealerNo;
	}

	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDnNoTypeName() {
		return dnNoTypeName;
	}

	public void setDnNoTypeName(String dnNoTypeName) {
		this.dnNoTypeName = dnNoTypeName;
	}

	public String getDeliverTypeName() {
		return deliverTypeName;
	}

	public void setDeliverTypeName(String deliverTypeName) {
		this.deliverTypeName = deliverTypeName;
	}

	public String getDeliverNo() {
		return deliverNo;
	}

	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getAddFlag() {
		return addFlag;
	}

	public void setAddFlag(String addFlag) {
		this.addFlag = addFlag;
	}

	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	public String getUniteNo() {
		return uniteNo;
	}

	public void setUniteNo(String uniteNo) {
		this.uniteNo = uniteNo;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMainNo() {
		return mainNo;
	}

	public void setMainNo(String mainNo) {
		this.mainNo = mainNo;
	}

	public String getNewTrans() {
		return newTrans;
	}

	public void setNewTrans(String newTrans) {
		this.newTrans = newTrans;
	}

	public String getRespkDat() {
		return respkDat;
	}

	public void setRespkDat(String respkDat) {
		this.respkDat = respkDat;
	}

	public String getRespkTime() {
		return respkTime;
	}

	public void setRespkTime(String respkTime) {
		this.respkTime = respkTime;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getMasterType() {
		return masterType;
	}

	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

}

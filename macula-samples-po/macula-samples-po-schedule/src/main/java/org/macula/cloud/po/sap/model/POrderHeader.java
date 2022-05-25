/**
 * 
 */
package org.macula.cloud.po.sap.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 * <p>
 * <b>POrderHeader</b> 传入订单主表:
 * </p>
 *
 
 
 
 *
 */
@BapiStructure
public class POrderHeader {

	/**
	 * 流程类型
	 */
	@Parameter("PRCD_NAME")
	private String procdName;

	/**
	 * 销售组织
	 */
	@Parameter("SALES_ORG")
	private String salesOrg;

	/**
	 * 分销渠道
	 */
	@Parameter("DISTR_CHAN")
	private String distrChan;

	/**
	 * 产品组
	 */
	@Parameter("DIVISION")
	private String division;

	/**
	 * 销售部门
	 */
	@Parameter("SALES_OFF")
	private String salesOff;

	/**
	 * 销售组
	 */
	@Parameter("SALES_GRP")
	private String salesGrp;

	/**
	 * 销售区域
	 */
	@Parameter("SALES_DIST")
	private String salesDist;

	/**
	 * 客户组
	 */
	@Parameter("KDGRP")
	private String kdgrp;

	/**
	 * 客户组1
	 */
	@Parameter("KVGR1")
	private String kvgr1;

	/**
	 * 客户组2
	 */
	@Parameter("KVGR2")
	private String kvgr2;

	/**
	 * 客户组3
	 */
	@Parameter("KVGR3")
	private String kvgr3;

	/**
	 * 客户组4
	 */
	@Parameter("KVGR4")
	private String kvgr4;

	/**
	 * 客户组5
	 */
	@Parameter("KVGR5")
	private String kvgr5;

	/**
	 * 出货工厂
	 */
	@Parameter("P_PLANT")
	private String pPlant;

	/**
	 * 库存地点
	 */
	@Parameter("STORE_LOC")
	private String storeLoc;

	/**
	 * 备注
	 */
	@Parameter("BEIZU")
	private String beiZhu;

	/**
	 * 姓名
	 */
	@Parameter("NAME")
	private String name;

	/**
	 * 卡号
	 */
	@Parameter("NAME_2")
	private String name2;

	/**
	 * 片区
	 */
	@Parameter("HOME_CITY")
	private String homeCity;

	/**
	 * 付款方卡号
	 */
	@Parameter("PAY_KUNN")
	private String payKunn;

	/**
	 * 售达方ID
	 */
	@Parameter("PARTN_NUMB")
	private String partnNumb;

	/**
	 * 送达方
	 */
	@Parameter("PARTN_KUNN1")
	private String partnKunn1;

	/**
	 * 地址id
	 */
	@Parameter("KUNN1_NAME")
	private String kunn1Name;

	/**
	 * 行政区id
	 */
	@Parameter("KUNN1_NAME_2")
	private String kunn1Name2;

	/**
	 * 运费总值 
	 */
	@Parameter("FREIGHT_AMOUNT")
	private BigDecimal freightAmount;

	/**
	 * 提货点
	 */
	@Parameter("KUNNR_WE")
	private String kunnrWe;

	/**
	 * 地区
	 */
	@Parameter("KUNN1_REGION")
	private String kunn1Region;

	/**
	 * 城市
	 */
	@Parameter("KUNN1_CITY")
	private String kunn1City;

	/**
	 * 区域
	 */
	@Parameter("KUNN1_DISTRICT")
	private String kunn1District;

	/**
	 * 预订单的主单标志
	 */
	@Parameter("KUNN1_HOME_CITY")
	private String kunn1HomeCity;

	/**
	 * 预订的辅单标记
	 */
	@Parameter("KUNN1_HOUSE_NUM1")
	private String kunn1HouseNum1;

	/**
	 * 邮政编码
	 */
	@Parameter("KUNN1_POST_CODE1")
	private String kunn1PostCode1;

	/**
	 * 详细地址1
	 */
	@Parameter("KUNN1_STREET")
	private String kunn1Street;

	/**
	 * 详细地址2
	 */
	@Parameter("KUNN1_STR_SUPPL1")
	private String kunn1StrSuppl1;

	/**
	 * 详细地址3
	 */
	@Parameter("KUNN1_STR_SUPPL2")
	private String kunn1StrSuppl2;

	/**
	 * 详细地址4
	 */
	@Parameter("KUNN1_STR_SUPPL3")
	private String kunn1StrSuppl3;

	/**
	 * 详细地址5
	 */
	@Parameter("KUNN1_LOCATION")
	private String kunn1Location;

	/**
	 * 姓名1
	 */
	@Parameter("PARTNER_NAME1")
	private String partnerName1;

	/**
	 * 身份证1
	 */
	@Parameter("PARTNER_NAME3")
	private String partnerName3;

	/**
	 * 电话号码1
	 */
	@Parameter("PARTNER_NAME2")
	private String partnerName2;

	/**
	 * 姓名2
	 */
	@Parameter("PARTNER_STR_SUPPL1")
	private String partnerStrSuppl1;

	/**
	 * 身份证2
	 */
	@Parameter("PARTNER_STR_SUPPL3")
	private String partnerStrSuppl3;

	/**
	 * 电话号码2
	 */
	@Parameter("PARTNER_STREET")
	private String partnerStreet;

	/**
	 * 姓名3
	 */
	@Parameter("PARTNER_LOCATION")
	private String partnerLocation;

	/**
	 * 身份证3
	 */
	@Parameter("PARTNER_HOME_CITY")
	private String partnerHomeCity;

	/**
	 * 电话号码3
	 */
	@Parameter("PARTNER_CITY2")
	private String partnerCity2;

	/**
	 * 配送方式
	 */
	@Parameter("SHIP_TYPE")
	private String shipType;

	/**
	 * 订单单号
	 */
	@Parameter("POS_NUMBER")
	private String posNumber;

	/**
	 * 申请单号
	 */
	@Parameter("PURCH_NO_C")
	private String purchNoC;

	/**
	 * 申请单日期
	 */
	private Date purchDate;

	/**
	 * 订单原因
	 */
	@Parameter("ORD_REASON")
	private String ordReason = "";

	/**
	 * 团单号
	 */
	@Parameter("COLLECT_NO")
	private String collectNo;

	/**
	 * 订单日期
	 */
	@Parameter("SO_DATE")
	private Date soDate;

	/**
	 * 非试点的订单标志
	 */
	@Parameter("OLD_ORDER")
	private String oldOrder;

	/**
	 * 价格组
	 */
	@Parameter("KONDA")
	private String konda;

	/**
	 * 备用字段1
	 */
	@Parameter("FIELD1")
	private String field1 = "";

	/**
	 * 备用字段2
	 */
	@Parameter("FIELD2")
	private String field2 = "";

	/**
	 * 备用字段3
	 */
	@Parameter("FIELD3")
	private String field3 = "";

	/**
	 * 备用字段4
	 */
	@Parameter("FIELD4")
	private String field4 = "";

	/**
	 * 备用字段5
	 */
	@Parameter("FIELD5")
	private String field5 = "";

	// sap 触发发货新增字段
	/**
	 * 是否需要触发发货  1:需要   0:不需要
	 */
	@Parameter("DELIVERY_FLAG")
	private String deliveryFlag;

	/**
	 * P单创建时间  HHmmss
	 */
	@Parameter("PO_ENTRY_TIME")
	private Date poEntryTime;

	/**
	 * 计划发货日期  yyyyMMdd
	 */
	@Parameter("DELIVERY_DATE")
	private Date deliveryDate;

	/**
	 * 易售通业务伙伴卡号
	 */
	@Parameter("CARD_YST")
	private String cardYst;

	/**
	 * 易售通业务伙伴姓名
	 */
	@Parameter("NAME_YST")
	private String nameYst;

	/**
	 * 收货人1证件类型
	 */
	@Parameter("PARTNER_TYPE1")
	private String partnerType1;

	/**
	 * 收货人2证件类型
	 */
	@Parameter("PARTNER_TYPE2")
	private String partnerType2;

	/**
	 * 收货人3证件类型
	 */
	@Parameter("PARTNER_TYPE3")
	private String partnerType3;

	/**
	 * 打包类型	
	 */
	@Parameter("ZPA1")
	private String zpa1;

	/**
	 * 记录主单号码	
	 */
	@Parameter("MAIN_NUMBER")
	private String mainNumber;

	/**
	 * 订单类型
	 */
	@Parameter("ORDER_TYPE")
	private String orderType;

	/**
	 * 预留字段6	
	 */
	@Parameter("FIELD6")
	private String field6;

	/**
	 * 订单状态
	 */
	@Parameter("PO_STATE")
	private String poState;

	//	/**
	//	 * 价格日期
	//	 */
	//	@Parameter("PRICE_DATE")
	//	private String priceDate;

	//	public String getPriceDate() {
	//		return priceDate;
	//	}
	//
	//	public void setPriceDate(String priceDate) {
	//		this.priceDate = priceDate;
	//	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public Date getPoEntryTime() {
		return poEntryTime;
	}

	public void setPoEntryTime(Date poEntryTime) {
		this.poEntryTime = poEntryTime;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCardYst() {
		return cardYst;
	}

	public void setCardYst(String cardYst) {
		this.cardYst = cardYst;
	}

	public String getNameYst() {
		return nameYst;
	}

	public void setNameYst(String nameYst) {
		this.nameYst = nameYst;
	}

	public String getPartnerType1() {
		return partnerType1;
	}

	public void setPartnerType1(String partnerType1) {
		this.partnerType1 = partnerType1;
	}

	public String getPartnerType2() {
		return partnerType2;
	}

	public void setPartnerType2(String partnerType2) {
		this.partnerType2 = partnerType2;
	}

	public String getPartnerType3() {
		return partnerType3;
	}

	public void setPartnerType3(String partnerType3) {
		this.partnerType3 = partnerType3;
	}

	public String getZpa1() {
		return zpa1;
	}

	public void setZpa1(String zpa1) {
		this.zpa1 = zpa1;
	}

	public String getMainNumber() {
		return mainNumber;
	}

	public void setMainNumber(String mainNumber) {
		this.mainNumber = mainNumber;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	/**
	 * @return the procdName
	 */
	public String getProcdName() {
		return procdName;
	}

	/**
	 * @return the storeLoc
	 */
	public String getStoreLoc() {
		return storeLoc;
	}

	/**
	 * @param storeLoc the storeLoc to set
	 */
	public void setStoreLoc(String storeLoc) {
		this.storeLoc = storeLoc;
	}

	/**
	 * @return the beizhu
	 */
	public String getBeiZhu() {
		return beiZhu;
	}

	/**
	 * @param
	 */
	public void setBeiZhu(String beiZhu) {
		this.beiZhu = beiZhu;
	}

	/**
	 * @return the soDate
	 */
	public Date getSoDate() {
		return soDate;
	}

	/**
	 * @param soDate the soDate to set
	 */
	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	/**
	 * @return the oldOrder
	 */
	public String getOldOrder() {
		return oldOrder;
	}

	/**
	 * @param oldOrder the oldOrder to set
	 */
	public void setOldOrder(String oldOrder) {
		this.oldOrder = oldOrder;
	}

	/**
	 * @param procdName the procdName to set
	 */
	public void setProcdName(String procdName) {
		this.procdName = procdName;
	}

	/**
	 * @return the salesOrg
	 */
	public String getSalesOrg() {
		return salesOrg;
	}

	/**
	 * @param salesOrg the salesOrg to set
	 */
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getPartnKunn1() {
		return partnKunn1;
	}

	public void setPartnKunn1(String partnKunn1) {
		this.partnKunn1 = partnKunn1;
	}

	public String getKunn1Name() {
		return kunn1Name;
	}

	public void setKunn1Name(String kunn1Name) {
		this.kunn1Name = kunn1Name;
	}

	public String getKunn1Name2() {
		return kunn1Name2;
	}

	public void setKunn1Name2(String kunn1Name2) {
		this.kunn1Name2 = kunn1Name2;
	}

	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
	}

	public String getKunn1Region() {
		return kunn1Region;
	}

	public void setKunn1Region(String kunn1Region) {
		this.kunn1Region = kunn1Region;
	}

	public String getKunn1City() {
		return kunn1City;
	}

	public void setKunn1City(String kunn1City) {
		this.kunn1City = kunn1City;
	}

	public String getKunn1District() {
		return kunn1District;
	}

	public void setKunn1District(String kunn1Distric) {
		this.kunn1District = kunn1Distric;
	}

	public String getKunn1HomeCity() {
		return kunn1HomeCity;
	}

	public void setKunn1HomeCity(String kunn1HomeCity) {
		this.kunn1HomeCity = kunn1HomeCity;
	}

	public String getKunn1PostCode1() {
		return kunn1PostCode1;
	}

	public void setKunn1PostCode1(String kunn1PostCode1) {
		this.kunn1PostCode1 = kunn1PostCode1;
	}

	public String getKunn1Street() {
		return kunn1Street;
	}

	public void setKunn1Street(String kunn1Street) {
		this.kunn1Street = kunn1Street;
	}

	public String getKunn1StrSuppl1() {
		return kunn1StrSuppl1;
	}

	public void setKunn1StrSuppl1(String kunn1StrSuppl1) {
		this.kunn1StrSuppl1 = kunn1StrSuppl1;
	}

	public String getKunn1StrSuppl2() {
		return kunn1StrSuppl2;
	}

	public void setKunn1StrSuppl2(String kunn1StrSuppl2) {
		this.kunn1StrSuppl2 = kunn1StrSuppl2;
	}

	public String getKunn1StrSuppl3() {
		return kunn1StrSuppl3;
	}

	public void setKunn1StrSuppl3(String kunn1StrSuppl3) {
		this.kunn1StrSuppl3 = kunn1StrSuppl3;
	}

	public String getKunn1Location() {
		return kunn1Location;
	}

	public void setKunn1Location(String kunn1Location) {
		this.kunn1Location = kunn1Location;
	}

	/**
	 * @return the pPlant
	 */
	public String getpPlant() {
		return pPlant;
	}

	/**
	 * @param pPlant the pPlant to set
	 */
	public void setpPlant(String pPlant) {
		this.pPlant = pPlant;
	}

	/**
	 * @return the distrChan
	 */
	public String getDistrChan() {
		return distrChan;
	}

	/**
	 * @param distrChan the distrChan to set
	 */
	public void setDistrChan(String distrChan) {
		this.distrChan = distrChan;
	}

	/**
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param division the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @return the salesOff
	 */
	public String getSalesOff() {
		return salesOff;
	}

	/**
	 * @param salesOff the salesOff to set
	 */
	public void setSalesOff(String salesOff) {
		this.salesOff = salesOff;
	}

	/**
	 * @return the salesGrp
	 */
	public String getSalesGrp() {
		return salesGrp;
	}

	/**
	 * @param salesGrp the salesGrp to set
	 */
	public void setSalesGrp(String salesGrp) {
		this.salesGrp = salesGrp;
	}

	/**
	 * @return the salesDist
	 */
	public String getSalesDist() {
		return salesDist;
	}

	/**
	 * @param salesDist the salesDist to set
	 */
	public void setSalesDist(String salesDist) {
		this.salesDist = salesDist;
	}

	/**
	 * @return the partnNumb
	 */
	public String getPartnNumb() {
		return partnNumb;
	}

	/**
	 * @param partnNumb the partnNumb to set
	 */
	public void setPartnNumb(String partnNumb) {
		this.partnNumb = partnNumb;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * @param name2 the name2 to set
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * @return the shipType
	 */
	public String getShipType() {
		return shipType;
	}

	/**
	 * @param shipType the shipType to set
	 */
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	/**
	 * @return the posNumber
	 */
	public String getPosNumber() {
		return posNumber;
	}

	/**
	 * @param posNumber the posNumber to set
	 */
	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}

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

	/**
	 * @return the purchDate
	 */
	public Date getPurchDate() {
		return purchDate;
	}

	/**
	 * @param purchDate the purchDate to set
	 */
	public void setPurchDate(Date purchDate) {
		this.purchDate = purchDate;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	/**
	 * @return the ordReason
	 */
	public String getOrdReason() {
		return ordReason;
	}

	/**
	 * @param ordReason the ordReason to set
	 */
	public void setOrdReason(String ordReason) {
		this.ordReason = ordReason;
	}

	/**
	 * @return the collectNo
	 */
	public String getCollectNo() {
		return collectNo;
	}

	/**
	 * @param collectNo the collectNo to set
	 */
	public void setCollectNo(String collectNo) {
		this.collectNo = collectNo;
	}

	/**
	 * @return the konda
	 */
	public String getKonda() {
		return konda;
	}

	/**
	 * @param konda the konda to set
	 */
	public void setKonda(String konda) {
		this.konda = konda;
	}

	/**
	 * @return the field1
	 */
	public String getField1() {
		return field1;
	}

	/**
	 * @param field1 the field1 to set
	 */
	public void setField1(String field1) {
		this.field1 = field1;
	}

	/**
	 * @return the field2
	 */
	public String getField2() {
		return field2;
	}

	public String getKdgrp() {
		return kdgrp;
	}

	public void setKdgrp(String kdgrp) {
		this.kdgrp = kdgrp;
	}

	public String getKvgr1() {
		return kvgr1;
	}

	public void setKvgr1(String kvgr1) {
		this.kvgr1 = kvgr1;
	}

	public String getKvgr2() {
		return kvgr2;
	}

	public void setKvgr2(String kvgr2) {
		this.kvgr2 = kvgr2;
	}

	public String getKvgr3() {
		return kvgr3;
	}

	public void setKvgr3(String kvgr3) {
		this.kvgr3 = kvgr3;
	}

	public String getKvgr4() {
		return kvgr4;
	}

	public void setKvgr4(String kvgr4) {
		this.kvgr4 = kvgr4;
	}

	public String getKvgr5() {
		return kvgr5;
	}

	public void setKvgr5(String kvgr5) {
		this.kvgr5 = kvgr5;
	}

	/**
	 * @param field2 the field2 to set
	 */
	public void setField2(String field2) {
		this.field2 = field2;
	}

	/**
	 * @return the field3
	 */
	public String getField3() {
		return field3;
	}

	/**
	 * @param field3 the field3 to set
	 */
	public void setField3(String field3) {
		this.field3 = field3;
	}

	/**
	 * @return the field4
	 */
	public String getField4() {
		return field4;
	}

	/**
	 * @param field4 the field4 to set
	 */
	public void setField4(String field4) {
		this.field4 = field4;
	}

	/**
	 * @return the field5
	 */
	public String getField5() {
		return field5;
	}

	/**
	 * @param field5 the field5 to set
	 */
	public void setField5(String field5) {
		this.field5 = field5;
	}

	/**
	 * @return the partnerName1
	 */
	public String getPartnerName1() {
		return partnerName1;
	}

	/**
	 * @param partnerName1 the partnerName1 to set
	 */
	public void setPartnerName1(String partnerName1) {
		this.partnerName1 = partnerName1;
	}

	/**
	 * @return the partnerName3
	 */
	public String getPartnerName3() {
		return partnerName3;
	}

	/**
	 * @param partnerName3 the partnerName3 to set
	 */
	public void setPartnerName3(String partnerName3) {
		this.partnerName3 = partnerName3;
	}

	/**
	 * @return the partnerName2
	 */
	public String getPartnerName2() {
		return partnerName2;
	}

	/**
	 * @param partnerName2 the partnerName2 to set
	 */
	public void setPartnerName2(String partnerName2) {
		this.partnerName2 = partnerName2;
	}

	/**
	 * @return the partnerStrSuppl1
	 */
	public String getPartnerStrSuppl1() {
		return partnerStrSuppl1;
	}

	/**
	 * @param partnerStrSuppl1 the partnerStrSuppl1 to set
	 */
	public void setPartnerStrSuppl1(String partnerStrSuppl1) {
		this.partnerStrSuppl1 = partnerStrSuppl1;
	}

	/**
	 * @return the partnerStrSuppl3
	 */
	public String getPartnerStrSuppl3() {
		return partnerStrSuppl3;
	}

	/**
	 * @param partnerStrSuppl3 the partnerStrSuppl3 to set
	 */
	public void setPartnerStrSuppl3(String partnerStrSuppl3) {
		this.partnerStrSuppl3 = partnerStrSuppl3;
	}

	/**
	 * @return the partnerStreet
	 */
	public String getPartnerStreet() {
		return partnerStreet;
	}

	/**
	 * @param partnerStreet the partnerStreet to set
	 */
	public void setPartnerStreet(String partnerStreet) {
		this.partnerStreet = partnerStreet;
	}

	/**
	 * @return the partnerLocation
	 */
	public String getPartnerLocation() {
		return partnerLocation;
	}

	/**
	 * @param partnerLocation the partnerLocation to set
	 */
	public void setPartnerLocation(String partnerLocation) {
		this.partnerLocation = partnerLocation;
	}

	/**
	 * @return the partnerHomeCity
	 */
	public String getPartnerHomeCity() {
		return partnerHomeCity;
	}

	/**
	 * @param partnerHomeCity the partnerHomeCity to set
	 */
	public void setPartnerHomeCity(String partnerHomeCity) {
		this.partnerHomeCity = partnerHomeCity;
	}

	/**
	 * @return the partnerCity2
	 */
	public String getPartnerCity2() {
		return partnerCity2;
	}

	/**
	 * @param partnerCity2 the partnerCity2 to set
	 */
	public void setPartnerCity2(String partnerCity2) {
		this.partnerCity2 = partnerCity2;
	}

	/**
	 * @return the pay_kunn
	 */
	public String getPayKunn() {
		return payKunn;
	}

	/**
	 * @param
	 */
	public void setPayKunn(String payKunn) {
		this.payKunn = payKunn;
	}

	/**
	 * @return the kunn1HouseNum1
	 */
	public String getKunn1HouseNum1() {
		return kunn1HouseNum1;
	}

	/**
	 * @param kunn1HouseNum1
	 *            the kunn1HouseNum1 to set
	 */
	public void setKunn1HouseNum1(String kunn1HouseNum1) {
		this.kunn1HouseNum1 = kunn1HouseNum1;
	}

	public String getKunnrWe() {
		return kunnrWe;
	}

	public void setKunnrWe(String kunnrWe) {
		this.kunnrWe = kunnrWe;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the poState
	 */
	public String getPoState() {
		return poState;
	}

	/**
	 * @param poState the poState to set
	 */
	public void setPoState(String poState) {
		this.poState = poState;
	}

}

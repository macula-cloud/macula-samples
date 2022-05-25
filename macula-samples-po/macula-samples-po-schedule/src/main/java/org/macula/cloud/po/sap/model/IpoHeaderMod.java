/**
 * 
 */
package org.macula.cloud.po.sap.model;

import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 
 *
 */
@BapiStructure
public class IpoHeaderMod {

	/**
	 * P单号码
	 */
	@Parameter("PURCH_NO_C")
	private String purchNoC;

	/**
	 * 配送方式 
	 */
	@Parameter("SHIP_TYPE")
	private String shipType;

	/**
	 * 计划发货日期
	 */
	@Parameter("DELIVERY_DATE")
	private Date deliveryDate;

	/**
	 * 城市（与邮政城市不同） 
	 */
	@Parameter("HOME_CITY")
	private String homeCity;

	/**
	 * 地区 (州、省、县)
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
	 * 城市（与邮政城市不同） 
	 */
	@Parameter("KUNN1_HOME_CITY")
	private String kunn1HomeCity;

	/**
	 * 预订的辅单标记 
	 */
	@Parameter("KUNN1_HOUSE_NUM1")
	private String kunn1HouseNum1;

	/**
	 * 城市邮政编码
	 */
	@Parameter("KUNN1_POST_CODE1")
	private String kunn1PostCode1;

	/**
	 * 街道名(城区或街道）
	 */
	@Parameter("KUNN1_STREET")
	private String kunn1Street;

	/**
	 * 街道2
	 */
	@Parameter("KUNN1_STR_SUPPL1")
	private String kunn1StrSuppl1;

	/**
	 * 街道 3
	 */
	@Parameter("KUNN1_STR_SUPPL2")
	private String kunn1StrSuppl2;

	/**
	 * 街道 4
	 */
	@Parameter("KUNN1_STR_SUPPL3")
	private String kunn1StrSuppl3;

	/**
	 * 街道 5
	 */
	@Parameter("KUNN1_LOCATION")
	private String kunn1Location;

	/**
	 * 收货人1姓名
	 */
	@Parameter("PARTNER_NAME1")
	private String partnerName1;

	/**
	 * 收货人1联系电话
	 */
	@Parameter("PARTNER_NAME2")
	private String partnerName2;

	/**
	 * 收货人1证件号码
	 */
	@Parameter("PARTNER_NAME3")
	private String partnerName3;

	/**
	 * 收货人1证件类型
	 */
	@Parameter("PARTNER_NAME4")
	private String partnerName4;

	/**
	 * 街道2--收货人2名称
	 */
	@Parameter("PA_STR_SUPPL1")
	private String paStrSuppl1;

	/**
	 * 街道4--收货人2证件号码
	 */
	@Parameter("PA_STR_SUPPL3")
	private String paStrSuppl3;

	/**
	 * 街道名 --收货人2联系电话
	 */
	@Parameter("PARTNER_STREET")
	private String partnerStreet;

	/**
	 * 收货人2证件类型
	 */
	@Parameter("PARTNER_TYPE2")
	private String partnerType2;

	/**
	 * 街道5-收货人3名称
	 */
	@Parameter("PARTNER_LOCATION")
	private String partnerLocation;

	/**
	 * 区域-收货人地址---收货人3电话号码
	 */
	@Parameter("PARTNER_CITY2")
	private String partnerCity2;

	/**
	 * 城市（与邮政城市不同） --收货人3证件号码
	 */
	@Parameter("PA_HOME_CITY")
	private String paHomeCity;

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
	 * 提货点
	 */
	@Parameter("KUNNR_WE")
	private String kunnrWe;

	/**
	 * 行政ID
	 */
	@Parameter("KUNN1_NAME_2")
	private String kunn1Name2;

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
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the homeCity
	 */
	public String getHomeCity() {
		return homeCity;
	}

	/**
	 * @param homeCity the homeCity to set
	 */
	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	/**
	 * @return the kunn1Region
	 */
	public String getKunn1Region() {
		return kunn1Region;
	}

	/**
	 * @param kunn1Region the kunn1Region to set
	 */
	public void setKunn1Region(String kunn1Region) {
		this.kunn1Region = kunn1Region;
	}

	/**
	 * @return the kunn1City
	 */
	public String getKunn1City() {
		return kunn1City;
	}

	/**
	 * @param kunn1City the kunn1City to set
	 */
	public void setKunn1City(String kunn1City) {
		this.kunn1City = kunn1City;
	}

	/**
	 * @return the kunn1District
	 */
	public String getKunn1District() {
		return kunn1District;
	}

	/**
	 * @param kunn1District the kunn1District to set
	 */
	public void setKunn1District(String kunn1District) {
		this.kunn1District = kunn1District;
	}

	/**
	 * @return the kunn1HomeCity
	 */
	public String getKunn1HomeCity() {
		return kunn1HomeCity;
	}

	/**
	 * @param kunn1HomeCity the kunn1HomeCity to set
	 */
	public void setKunn1HomeCity(String kunn1HomeCity) {
		this.kunn1HomeCity = kunn1HomeCity;
	}

	/**
	 * @return the kunn1HouseNum1
	 */
	public String getKunn1HouseNum1() {
		return kunn1HouseNum1;
	}

	/**
	 * @param kunn1HouseNum1 the kunn1HouseNum1 to set
	 */
	public void setKunn1HouseNum1(String kunn1HouseNum1) {
		this.kunn1HouseNum1 = kunn1HouseNum1;
	}

	/**
	 * @return the kunn1PostCode1
	 */
	public String getKunn1PostCode1() {
		return kunn1PostCode1;
	}

	/**
	 * @param kunn1PostCode1 the kunn1PostCode1 to set
	 */
	public void setKunn1PostCode1(String kunn1PostCode1) {
		this.kunn1PostCode1 = kunn1PostCode1;
	}

	/**
	 * @return the kunn1Street
	 */
	public String getKunn1Street() {
		return kunn1Street;
	}

	/**
	 * @param kunn1Street the kunn1Street to set
	 */
	public void setKunn1Street(String kunn1Street) {
		this.kunn1Street = kunn1Street;
	}

	/**
	 * @return the kunn1StrSuppl1
	 */
	public String getKunn1StrSuppl1() {
		return kunn1StrSuppl1;
	}

	/**
	 * @param kunn1StrSuppl1 the kunn1StrSuppl1 to set
	 */
	public void setKunn1StrSuppl1(String kunn1StrSuppl1) {
		this.kunn1StrSuppl1 = kunn1StrSuppl1;
	}

	/**
	 * @return the kunn1StrSuppl2
	 */
	public String getKunn1StrSuppl2() {
		return kunn1StrSuppl2;
	}

	/**
	 * @param kunn1StrSuppl2 the kunn1StrSuppl2 to set
	 */
	public void setKunn1StrSuppl2(String kunn1StrSuppl2) {
		this.kunn1StrSuppl2 = kunn1StrSuppl2;
	}

	/**
	 * @return the kunn1StrSuppl3
	 */
	public String getKunn1StrSuppl3() {
		return kunn1StrSuppl3;
	}

	/**
	 * @param kunn1StrSuppl3 the kunn1StrSuppl3 to set
	 */
	public void setKunn1StrSuppl3(String kunn1StrSuppl3) {
		this.kunn1StrSuppl3 = kunn1StrSuppl3;
	}

	/**
	 * @return the kunn1Location
	 */
	public String getKunn1Location() {
		return kunn1Location;
	}

	/**
	 * @param kunn1Location the kunn1Location to set
	 */
	public void setKunn1Location(String kunn1Location) {
		this.kunn1Location = kunn1Location;
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
	 * @return the partnerName4
	 */
	public String getPartnerName4() {
		return partnerName4;
	}

	/**
	 * @param partnerName4 the partnerName4 to set
	 */
	public void setPartnerName4(String partnerName4) {
		this.partnerName4 = partnerName4;
	}

	/**
	 * @return the paStrSuppl1
	 */
	public String getPaStrSuppl1() {
		return paStrSuppl1;
	}

	/**
	 * @param paStrSuppl1 the paStrSuppl1 to set
	 */
	public void setPaStrSuppl1(String paStrSuppl1) {
		this.paStrSuppl1 = paStrSuppl1;
	}

	/**
	 * @return the paStrSuppl3
	 */
	public String getPaStrSuppl3() {
		return paStrSuppl3;
	}

	/**
	 * @param paStrSuppl3 the paStrSuppl3 to set
	 */
	public void setPaStrSuppl3(String paStrSuppl3) {
		this.paStrSuppl3 = paStrSuppl3;
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
	 * @return the partnerType2
	 */
	public String getPartnerType2() {
		return partnerType2;
	}

	/**
	 * @param partnerType2 the partnerType2 to set
	 */
	public void setPartnerType2(String partnerType2) {
		this.partnerType2 = partnerType2;
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
	 * @return the paHomeCity
	 */
	public String getPaHomeCity() {
		return paHomeCity;
	}

	/**
	 * @param paHomeCity the paHomeCity to set
	 */
	public void setPaHomeCity(String paHomeCity) {
		this.paHomeCity = paHomeCity;
	}

	/**
	 * @return the partnerType3
	 */
	public String getPartnerType3() {
		return partnerType3;
	}

	/**
	 * @param partnerType3 the partnerType3 to set
	 */
	public void setPartnerType3(String partnerType3) {
		this.partnerType3 = partnerType3;
	}

	/**
	 * @return the zpa1
	 */
	public String getZpa1() {
		return zpa1;
	}

	/**
	 * @param zpa1 the zpa1 to set
	 */
	public void setZpa1(String zpa1) {
		this.zpa1 = zpa1;
	}

	/**
	 * @return the kunnrWe
	 */
	public String getKunnrWe() {
		return kunnrWe;
	}

	/**
	 * @param kunnrWe the kunnrWe to set
	 */
	public void setKunnrWe(String kunnrWe) {
		this.kunnrWe = kunnrWe;
	}

	/**
	 * @return the kunn1Name2
	 */
	public String getKunn1Name2() {
		return kunn1Name2;
	}

	/**
	 * @param kunn1Name2 the kunn1Name2 to set
	 */
	public void setKunn1Name2(String kunn1Name2) {
		this.kunn1Name2 = kunn1Name2;
	}
}

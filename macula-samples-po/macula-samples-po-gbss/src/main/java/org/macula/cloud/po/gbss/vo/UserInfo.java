/**
 * 
 */
package org.macula.cloud.po.gbss.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <b>UserInfo</b> 是登录人的基本用户信息
 * </p>
 * 
 * 
 
 
 
 * 
 */
public class UserInfo {
	/**
	 * 卡号
	 */
	private String dealerNo;
	/**
	 * 姓名
	 */
	private String fullName;

	/**
	 * 身份证号码
	 */
	private String certificateNo;

	/**
	 * 移动电话
	 */
	private String contactMobile;
	/**
	 * 联络电话
	 */
	private String contactTele;
	/**
	 * 身份类型
	 */
	private String dealerType;
	private String dealerTypeDesc;
	/**
	 * 职级
	 */
	private String dealerPostCode;

	private String dealerPostCodeDesc;

	/**
	 * 分公司信息
	 
	 * 2015-06-29
	 */
	private String saleBranchNo;
	private String saleBranchDesc;
	/**
	 * 用户配置信息
	 */
	private Map<String, String> dealerProfiles = new HashMap<String, String>();

	public UserInfo() {
	}

	/**
	 * @param dealerNo
	 * @param fullName
	 * @param certificateNo
	 * @param contactMobile
	 * @param contactTele
	 */
	public UserInfo(String dealerNo, String fullName, String certificateNo, String contactMobile, String contactTele) {
		super();
		this.dealerNo = dealerNo;
		this.fullName = fullName;
		this.certificateNo = certificateNo;
		this.contactMobile = contactMobile;
		this.contactTele = contactTele;
	}

	/**
	 * @return the certificateNo
	 */
	public String getCertificateNo() {
		return certificateNo;
	}

	/**
	 * @param certificateNo the certificateNo to set
	 */
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	/**
	 * @return the dealerNo
	 */
	public String getDealerNo() {
		return dealerNo;
	}

	/**
	 * @param dealerNo the dealerNo to set
	 */
	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * @param contactMobile the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * @return the contactTele
	 */
	public String getContactTele() {
		return contactTele;
	}

	/**
	 * @param contactTele the contactTele to set
	 */
	public void setContactTele(String contactTele) {
		this.contactTele = contactTele;
	}

	/**
	 * @return the dealerProfiles
	 */
	public Map<String, String> getDealerProfiles() {
		return dealerProfiles;
	}

	/**
	 * @param dealerProfiles the dealerProfiles to set
	 */
	public void setDealerProfiles(Map<String, String> dealerProfiles) {
		this.dealerProfiles = dealerProfiles;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public String getDealerTypeDesc() {
		return dealerTypeDesc;
	}

	public void setDealerTypeDesc(String dealerTypeDesc) {
		this.dealerTypeDesc = dealerTypeDesc;
	}

	public String getDealerPostCodeDesc() {
		return dealerPostCodeDesc;
	}

	public void setDealerPostCodeDesc(String dealerPostCodeDesc) {
		this.dealerPostCodeDesc = dealerPostCodeDesc;
	}

	public String getDealerPostCode() {
		return dealerPostCode;
	}

	public void setDealerPostCode(String dealerPostCode) {
		this.dealerPostCode = dealerPostCode;
	}

	public String getSaleBranchNo() {
		return saleBranchNo;
	}

	public void setSaleBranchNo(String saleBranchNo) {
		this.saleBranchNo = saleBranchNo;
	}

	public String getSaleBranchDesc() {
		return saleBranchDesc;
	}

	public void setSaleBranchDesc(String saleBranchDesc) {
		this.saleBranchDesc = saleBranchDesc;
	}
}

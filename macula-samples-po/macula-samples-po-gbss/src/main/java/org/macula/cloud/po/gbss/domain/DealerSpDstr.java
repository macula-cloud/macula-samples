/**
 * DealerSpDstr.java 2012-11-20
 */
package org.macula.cloud.po.gbss.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>DealerSpDstr</b> 是经销商资料信息表
 * </p>
 * 
 
 
 
 * 
 */
@Entity
@Table(name = "DEALER_SP_DSTR")
@DynamicInsert
@DynamicUpdate
public class DealerSpDstr extends LegacyUpdateable<Long> {
	private static final long serialVersionUID = 1L;

	/**卡号*/
	@Column(name = "DEALER_NO", nullable = false, length = 15)
	@javax.validation.constraints.Size(min = 1, max = 15)
	private String dealerNo;

	/**申请经销商月份*/
	@Column(name = "APP_DSTR_PERIOD", nullable = false, length = 15)
	@javax.validation.constraints.Size(min = 1, max = 15)
	private String appDstrPeriod;

	/**申请经销商日期*/
	@Column(name = "APP_DSTR_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date appDstrDate;

	/**经销商合同开始日*/
	@Column(name = "DSTR_CONTRACT_START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dstrContractStartDate;

	/**经销商合同结束日*/
	@Column(name = "DSTR_CONTRACT_END_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dstrContractEndDate;

	/**收入明细发送形式*/
	@Column(name = "INCOME_LIST_SEND", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String incomeListSend;

	/**收入明细公章形式*/
	@Column(name = "INCOME_LIST_SEAL", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String incomeListSeal;

	/**营业执照字号名*/
	@Column(name = "LICENCE_NAME", nullable = false, length = 50)
	@javax.validation.constraints.Size(min = 1, max = 50)
	private String licenceName;

	/**营业执照编号*/
	@Column(name = "LICENCE_NO", nullable = false, length = 50)
	@javax.validation.constraints.Size(min = 1, max = 50)
	private String licenceNo;

	/**营业执照办法机构*/
	@Column(name = "LICENCE_GOVERNMENT", nullable = true, length = 150) //update by ky_qbq
	//@javax.validation.constraints.Size(min=1, max=50) update by ky_qbq
	private String licenceGovernment;

	/**营业执照注册省份*/
	@Column(name = "LICENCE_ADDR_PROVINCE", nullable = false, length = 20)
	@javax.validation.constraints.Size(min = 1, max = 20)
	private String licenceAddrProvince;

	/**营业执照注册城市*/
	@Column(name = "LICENCE_ADDR_CITY", nullable = false, length = 40)
	@javax.validation.constraints.Size(min = 1, max = 40)
	private String licenceAddrCity;

	/**营业执照注册区(县)*/
	@Column(name = "LICENCE_ADDR_COUNTY", nullable = false, length = 40)
	@javax.validation.constraints.Size(min = 1, max = 40)
	private String licenceAddrCounty;

	/**营业执照地址01*/
	@Column(name = "LICENCE_ADDR_TAIL_01", nullable = false, length = 50)
	@javax.validation.constraints.Size(min = 1, max = 50)
	private String licenceAddrTail01;

	/**营业执照细地址02*/
	@Column(name = "LICENCE_ADDR_TAIL_02", nullable = true, length = 50)
	private String licenceAddrTail02;

	/**营业执照注册地址所属分公司*/
	@Column(name = "LICENCE_BRANCH_NO", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String licenceBranchNo;

	/**营业执照注册地址所属片区*/

	@Column(name = "LICENCE_ZONE_NO", nullable = false, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String licenceZoneNo;

	/**地税纳税人名称*/

	@Column(name = "CTAX_PAYER", nullable = true, length = 50)
	private String ctaxPayer;

	/**地税机关名称*/

	@Column(name = "CTAX_GOVERNMENT", nullable = true, length = 50)
	private String ctaxGovernment;

	/**地税注册号*/

	@Column(name = "CTAX_REGISTER_NO", nullable = true, length = 30)
	private String ctaxRegisterNo;

	/**国税纳税人名称*/

	@Column(name = "NTAX_PAYER", nullable = true, length = 50)
	private String ntaxPayer;

	/**国税机关名称*/

	@Column(name = "NTAX_GOVERNMENT", nullable = true, length = 50)
	private String ntaxGovernment;

	/**国税注册号*/

	@Column(name = "NTAX_REGISTER_NO", nullable = true, length = 30)
	private String ntaxRegisterNo;

	/**续约展期申请单号*/

	@Column(name = "EXTEND_APP_NO", nullable = true, length = 15)
	private String extendAppNo;

	/**续约展期审批时间*/

	@Column(name = "EXTEND_TIME", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date extendTime;

	/**续约展期审批人*/

	@Column(name = "EXTEND_BY", nullable = true, length = 15)
	private String extendBy;

	/**经销商申请单号*/

	@Column(name = "REF_APP_NO", nullable = true, length = 15)
	private String refAppNo;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**创建输入种类*/
	@Column(name = "CREATED_ENTRY_CLASS", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String createdEntryClass;

	/** 参考源身份类型 */

	@Column(name = "REF_FROM_DEALER_TYPE", nullable = true, length = 2)
	private String refFromDealerType;

	/**是否自动续约    add by ky_qbq*/
	@Column(name = "IS_AUTO_EXTEND", nullable = true)
	private int autoExtend;

	/**英文名/评拼音*/
	@Column(name = "EN_NAME", nullable = true, length = 50)
	@javax.validation.constraints.Size(min = 1, max = 50)
	private String enName;

	/**国别*/
	@Column(name = "NATION_CODE", nullable = true, length = 3)
	@javax.validation.constraints.Size(min = 1, max = 3)
	private String nationCode;

	/**非大陆证件类型*/
	@Column(name = "CERTIFICATE_TYPE", nullable = true, length = 2)
	@javax.validation.constraints.Size(min = 1, max = 2)
	private String certificateType;

	/**境内是否有固定场所*/
	@Column(name = "IS_RESIDENCE", nullable = true, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String rensidence;

	/**在华居住情况（居住天数）*/
	@Column(name = "LIVE_DAYS", nullable = true, length = 4)
	@javax.validation.constraints.Size(min = 1, max = 4)
	private String liveDays;

	/**
	 * @return dealerNo 卡号
	 */
	public String getDealerNo() {
		return this.dealerNo;
	}

	/**
	 * @param dealerNo 卡号的设置
	 */
	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}

	/**
	 * @return appDstrPeriod 申请经销商月份
	 */
	public String getAppDstrPeriod() {
		return this.appDstrPeriod;
	}

	/**
	 * @param appDstrPeriod 申请经销商月份的设置
	 */
	public void setAppDstrPeriod(String appDstrPeriod) {
		this.appDstrPeriod = appDstrPeriod;
	}

	/**
	 * @return appDstrDate 申请经销商日期
	 */
	public Date getAppDstrDate() {
		return this.appDstrDate;
	}

	/**
	 * @param appDstrDate 申请经销商日期的设置
	 */
	public void setAppDstrDate(Date appDstrDate) {
		this.appDstrDate = appDstrDate;
	}

	/**
	 * @return dstrContractStartDate 经销商合同开始日
	 */
	public Date getDstrContractStartDate() {
		return this.dstrContractStartDate;
	}

	/**
	 * @param dstrContractStartDate 经销商合同开始日的设置
	 */
	public void setDstrContractStartDate(Date dstrContractStartDate) {
		this.dstrContractStartDate = dstrContractStartDate;
	}

	/**
	 * @return dstrContractEndDate 经销商合同结束日
	 */
	public Date getDstrContractEndDate() {
		return this.dstrContractEndDate;
	}

	/**
	 * @param dstrContractEndDate 经销商合同结束日的设置
	 */
	public void setDstrContractEndDate(Date dstrContractEndDate) {
		this.dstrContractEndDate = dstrContractEndDate;
	}

	/**
	 * @return incomeListSend 收入明细发送形式
	 */
	public String getIncomeListSend() {
		return this.incomeListSend;
	}

	/**
	 * @param incomeListSend 收入明细发送形式的设置
	 */
	public void setIncomeListSend(String incomeListSend) {
		this.incomeListSend = incomeListSend;
	}

	/**
	 * @return incomeListSeal 收入明细公章形式
	 */
	public String getIncomeListSeal() {
		return this.incomeListSeal;
	}

	/**
	 * @param incomeListSeal 收入明细公章形式的设置
	 */
	public void setIncomeListSeal(String incomeListSeal) {
		this.incomeListSeal = incomeListSeal;
	}

	/**
	 * @return licenceName 营业执照字号名
	 */
	public String getLicenceName() {
		return this.licenceName;
	}

	/**
	 * @param licenceName 营业执照字号名的设置
	 */
	public void setLicenceName(String licenceName) {
		this.licenceName = licenceName;
	}

	/**
	 * @return licenceNo 营业执照编号
	 */
	public String getLicenceNo() {
		return this.licenceNo;
	}

	/**
	 * @param licenceNo 营业执照编号的设置
	 */
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	/**
	 * @return licenceGovernment 营业执照办法机构
	 */
	public String getLicenceGovernment() {
		return this.licenceGovernment;
	}

	/**
	 * @param licenceGovernment 营业执照办法机构的设置
	 */
	public void setLicenceGovernment(String licenceGovernment) {
		this.licenceGovernment = licenceGovernment;
	}

	/**
	 * @return licenceAddrProvince 营业执照注册省份
	 */
	public String getLicenceAddrProvince() {
		return this.licenceAddrProvince;
	}

	/**
	 * @param licenceAddrProvince 营业执照注册省份的设置
	 */
	public void setLicenceAddrProvince(String licenceAddrProvince) {
		this.licenceAddrProvince = licenceAddrProvince;
	}

	/**
	 * @return licenceAddrCity 营业执照注册城市
	 */
	public String getLicenceAddrCity() {
		return this.licenceAddrCity;
	}

	/**
	 * @param licenceAddrCity 营业执照注册城市的设置
	 */
	public void setLicenceAddrCity(String licenceAddrCity) {
		this.licenceAddrCity = licenceAddrCity;
	}

	/**
	 * @return licenceAddrCounty 营业执照注册区(县)
	 */
	public String getLicenceAddrCounty() {
		return this.licenceAddrCounty;
	}

	/**
	 * @param licenceAddrCounty 营业执照注册区(县)的设置
	 */
	public void setLicenceAddrCounty(String licenceAddrCounty) {
		this.licenceAddrCounty = licenceAddrCounty;
	}

	/**
	 * @return licenceAddrTail01 营业执照地址01
	 */
	public String getLicenceAddrTail01() {
		return this.licenceAddrTail01;
	}

	/**
	 * @param licenceAddrTail01 营业执照地址01的设置
	 */
	public void setLicenceAddrTail01(String licenceAddrTail01) {
		this.licenceAddrTail01 = licenceAddrTail01;
	}

	/**
	 * @return licenceAddrTail02 营业执照细地址02
	 */
	public String getLicenceAddrTail02() {
		return this.licenceAddrTail02;
	}

	/**
	 * @param licenceAddrTail02 营业执照细地址02的设置
	 */
	public void setLicenceAddrTail02(String licenceAddrTail02) {
		this.licenceAddrTail02 = licenceAddrTail02;
	}

	/**
	 * @return licenceBranchNo 营业执照注册地址所属分公司
	 */
	public String getLicenceBranchNo() {
		return this.licenceBranchNo;
	}

	/**
	 * @param licenceBranchNo 营业执照注册地址所属分公司的设置
	 */
	public void setLicenceBranchNo(String licenceBranchNo) {
		this.licenceBranchNo = licenceBranchNo;
	}

	/**
	 * @return licenceZoneNo 营业执照注册地址所属片区
	 */
	public String getLicenceZoneNo() {
		return this.licenceZoneNo;
	}

	/**
	 * @param licenceZoneNo 营业执照注册地址所属片区的设置
	 */
	public void setLicenceZoneNo(String licenceZoneNo) {
		this.licenceZoneNo = licenceZoneNo;
	}

	/**
	 * @return ctaxPayer 地税纳税人名称
	 */
	public String getCtaxPayer() {
		return this.ctaxPayer;
	}

	/**
	 * @param ctaxPayer 地税纳税人名称的设置
	 */
	public void setCtaxPayer(String ctaxPayer) {
		this.ctaxPayer = ctaxPayer;
	}

	/**
	 * @return ctaxGovernment 地税机关名称
	 */
	public String getCtaxGovernment() {
		return this.ctaxGovernment;
	}

	/**
	 * @param ctaxGovernment 地税机关名称的设置
	 */
	public void setCtaxGovernment(String ctaxGovernment) {
		this.ctaxGovernment = ctaxGovernment;
	}

	/**
	 * @return ctaxRegisterNo 地税注册号
	 */
	public String getCtaxRegisterNo() {
		return this.ctaxRegisterNo;
	}

	/**
	 * @param ctaxRegisterNo 地税注册号的设置
	 */
	public void setCtaxRegisterNo(String ctaxRegisterNo) {
		this.ctaxRegisterNo = ctaxRegisterNo;
	}

	/**
	 * @return ntaxPayer 国税纳税人名称
	 */
	public String getNtaxPayer() {
		return this.ntaxPayer;
	}

	/**
	 * @param ntaxPayer 国税纳税人名称的设置
	 */
	public void setNtaxPayer(String ntaxPayer) {
		this.ntaxPayer = ntaxPayer;
	}

	/**
	 * @return ntaxGovernment 国税机关名称
	 */
	public String getNtaxGovernment() {
		return this.ntaxGovernment;
	}

	/**
	 * @param ntaxGovernment 国税机关名称的设置
	 */
	public void setNtaxGovernment(String ntaxGovernment) {
		this.ntaxGovernment = ntaxGovernment;
	}

	/**
	 * @return ntaxRegisterNo 国税注册号
	 */
	public String getNtaxRegisterNo() {
		return this.ntaxRegisterNo;
	}

	/**
	 * @param ntaxRegisterNo 国税注册号的设置
	 */
	public void setNtaxRegisterNo(String ntaxRegisterNo) {
		this.ntaxRegisterNo = ntaxRegisterNo;
	}

	/**
	 * @return extendAppNo 续约展期申请单号
	 */
	public String getExtendAppNo() {
		return this.extendAppNo;
	}

	/**
	 * @param extendAppNo 续约展期申请单号的设置
	 */
	public void setExtendAppNo(String extendAppNo) {
		this.extendAppNo = extendAppNo;
	}

	/**
	 * @return extendTime 续约展期审批时间
	 */
	public Date getExtendTime() {
		return this.extendTime;
	}

	/**
	 * @param extendTime 续约展期审批时间的设置
	 */
	public void setExtendTime(Date extendTime) {
		this.extendTime = extendTime;
	}

	/**
	 * @return extendBy 续约展期审批人
	 */
	public String getExtendBy() {
		return this.extendBy;
	}

	/**
	 * @param extendBy 续约展期审批人的设置
	 */
	public void setExtendBy(String extendBy) {
		this.extendBy = extendBy;
	}

	/**
	 * @return refAppNo 经销商申请单号
	 */
	public String getRefAppNo() {
		return this.refAppNo;
	}

	/**
	 * @param refAppNo 经销商申请单号的设置
	 */
	public void setRefAppNo(String refAppNo) {
		this.refAppNo = refAppNo;
	}

	/**
	 * @return comments 备注
	 */
	public String getComments() {
		return this.comments;
	}

	/**
	 * @param comments 备注的设置
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return createdEntryClass 创建输入种类
	 */
	public String getCreatedEntryClass() {
		return this.createdEntryClass;
	}

	/**
	 * @param createdEntryClass 创建输入种类的设置
	 */
	public void setCreatedEntryClass(String createdEntryClass) {
		this.createdEntryClass = createdEntryClass;
	}

	/**
	 * 参考源身份类型
	 * @return
	 */
	public String getRefFromDealerType() {
		return refFromDealerType;
	}

	/**
	 * 参考源身份类型
	 * @param refFromDealerType
	 */
	public void setRefFromDealerType(String refFromDealerType) {
		this.refFromDealerType = refFromDealerType;
	}

	public int getAutoExtend() {
		return autoExtend;
	}

	public void setAutoExtend(int autoExtend) {
		this.autoExtend = autoExtend;
	}

	/**
	 * @return the enName
	 */
	public String getEnName() {
		return enName;
	}

	/**
	 * @param enName the enName to set
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}

	/**
	 * @return the nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * @param nationCode the nationCode to set
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	/**
	 * @return the certificateType
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * @param certificateType the certificateType to set
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * @return the rensidence
	 */
	public String getRensidence() {
		return rensidence;
	}

	/**
	 * @param rensidence the rensidence to set
	 */
	public void setRensidence(String rensidence) {
		this.rensidence = rensidence;
	}

	/**
	 * @return the liveDays
	 */
	public String getLiveDays() {
		return liveDays;
	}

	/**
	 * @param liveDays the liveDays to set
	 */
	public void setLiveDays(String liveDays) {
		this.liveDays = liveDays;
	}

}

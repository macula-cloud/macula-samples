package org.macula.cloud.po.gbss.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 店铺附属信息表
 
 
 * @version
 */
@Entity
@Getter
@Setter
@Table(name = "DEALER_STORE_EXTRA")
public class DealerStoreExtra extends LegacyUpdateable<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**经销商店铺编号*/
	@Column(name = "STORE_NO", nullable = false, length = 15)
	@javax.validation.constraints.Size(min = 1, max = 15)
	private String storeNo;

	/**首次批准日期*/
	@Column(name = "FIRST_APPROVAL_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date firstApprovalDate;

	/**合同编号*/

	@Column(name = "CONTRACT_NO", nullable = true, length = 20)
	private String contractNo;

	/**合同开始日期*/

	@Column(name = "CONTRACT_START_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date contractStartDate;

	/**合同结束日期*/

	@Column(name = "CONTRACT_END_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date contractEndDate;

	/**担保经销商号*/
	@Column(name = "ASSURED_DEALER_NO", nullable = true, length = 15)
	private String assuredDealerNo;

	/**担保人姓名*/
	@Column(name = "ASSURED_NAME", nullable = true, length = 50)
	private String assuredName;

	/**经营执照种类*/

	@Column(name = "PERMIT_CLASS", nullable = true, length = 20)
	private String permitClass;

	/**营业执照字号名*/
	@Column(name = "LICENCE_NAME", nullable = true, length = 50)
	private String licenceName;

	/**营业执照编号*/
	@Column(name = "LICENCE_NO", nullable = true, length = 50)
	private String licenceNo;

	/**营业执照到期日*/
	@Column(name = "LICENCE_MATURITY", nullable = true, length = 20)
	private String licenceMaturity;

	/**营业执照范围*/
	@Column(name = "LICENCE_SCOPE", nullable = true, length = 20)
	private String licenceScope;

	/**营业执照范围备注*/
	@Column(name = "LICENCE_SCOPE_MEMO", nullable = true, length = 100)
	private String licenceScopeMemo;

	/**营业执照颁发机关*/
	@Column(name = "LICENCE_GOVERNMENT", nullable = true, length = 150)
	private String licenceGovernment;

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

	/**卫生许可证名称*/
	@Column(name = "SAN_LICENCE_NAME", nullable = true, length = 50)
	private String sanLicenceName;

	/**卫生许可证颁发机构*/
	@Column(name = "SAN_LICENCE_GOVERNMENT", nullable = true, length = 50)
	private String sanLicenceGovernment;

	/**卫生许可证号*/
	@Column(name = "SAN_LICENCE_NO", nullable = true, length = 30)
	private String sanLicenceNo;

	/**卫生许可证到期日*/

	@Column(name = "SAN_LICENCE_MATURITY", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date sanLicenceMaturity;

	/**收入明细发送形式*/
	@Column(name = "INCOME_LIST_SEND", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String incomeListSend;

	/**收入明细公章形式*/

	@Column(name = "INCOME_LIST_SEAL", nullable = false, length = 1)
	@javax.validation.constraints.Size(min = 1, max = 1)
	private String incomeListSeal;

	/**经营场区划类型*/

	@Column(name = "GROUND_AREA_CLASS", nullable = true, length = 1)
	private String groundAreaClass;

	/**经营场所面积*/
	@Column(name = "GROUND_ACREAGE", nullable = false, length = 11)
	private BigDecimal groundAcreage;

	/**经营场所体验区面积*/

	@Column(name = "GROUND_ACREAGE_EXP", nullable = false, length = 11)
	private BigDecimal groundAcreageExp = BigDecimal.ZERO;

	/**经营场所销售区面积*/
	@Column(name = "GROUND_ACREAGE_SALE", nullable = false, length = 11)
	private BigDecimal groundAcreageSale = BigDecimal.ZERO;

	/**经营场所沟通区面积*/
	@Column(name = "GROUND_ACREAGE_COMM", nullable = false, length = 11)
	private BigDecimal groundAcreageComm = BigDecimal.ZERO;

	/**经营场所培训区面积*/
	@Column(name = "GROUND_ACREAGE_TRAINING", nullable = false, length = 11)
	private BigDecimal groundAcreageTraining = BigDecimal.ZERO;

	/**经营场所到期日*/
	@Column(name = "GROUND_MATURITY", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date groundMaturity;

	/**经营场所租赁时间*/
	@Column(name = "GROUND_TENANCY", nullable = false, length = 5)
	private int groundTenancy = 999;

	/**经营场所楼层*/
	@Column(name = "GROUND_FLOOR", nullable = false, length = 3)
	private int groundFloor;

	/**经营场所物业性质*/
	@Column(name = "GROUND_OWNERSHIP", nullable = true, length = 1)
	private String groundOwnership;

	/**经营场所质量*/
	@Column(name = "GROUND_QUALITY", nullable = true, length = 1)
	private String groundQuality;

	/**场所性质*/
	@Column(name = "GROUND_TYPE", nullable = true, length = 1)
	private String groundType;

	/**经营场所类型*/
	@Column(name = "GROUND_KIND", nullable = true, length = 1)
	private String groundKind;

	/**经营场所使用性质*/
	@Column(name = "GROUND_USE", nullable = true, length = 1)
	private String groundUse;

	/**经营场所位置*/
	@Column(name = "GROUND_LOCATE", nullable = true, length = 1)
	private String groundLocate;

	/**经营场所装修*/
	@Column(name = "GROUND_FITMENT", nullable = true, length = 20)
	private String groundFitment;

	/**经营场所配备*/
	@Column(name = "GROUND_EQUIPMENT", nullable = true, length = 20)
	private String groundEquipment;

	/**评价外观*/
	@Column(name = "VISUALIZE_EVALUATE", nullable = true, length = 1)
	private String visualizeEvaluate;

	/**违规记录*/
	@Column(name = "PUNISH_RECORD", nullable = true, length = 1)
	private String punishRecord;

	/**外事关系*/
	@Column(name = "EXTERNAL_RELATION", nullable = true, length = 1)
	private String externalRelation;

	/**考核分数*/
	@Column(name = "EXAMINE_SCORE", nullable = false, length = 6)
	private BigDecimal examineScore;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**食品流通许可证名称*/
	@Column(name = "FOOD_CIR_LICENCE_NAME", nullable = true, length = 50)
	private String foodLicenceName;

	/**食品流通许可证颁发机构*/
	@Column(name = "FOOD_CIR_LICENCE_GOVERNMENT", nullable = true, length = 50)
	private String foodLicenceGovernment;

	/**食品流通许可证号*/
	@Column(name = "FOOD_CIR_LICENCE_NO", nullable = true, length = 50)
	private String foodLicenceNo;

	/**食品流通许可证到期日*/
	@Column(name = "FOOD_CIR_LICENCE_MATURITY", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date foodLicenceMaturity;

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

	/**申请单号*/
	@Column(name = "APP_NO", nullable = true, length = 20)
	private String appNo;

	/**最近店铺批准日期*/
	@Column(name = "LAST_APPROVAL_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date lastApprovalDate;

	@Column(name = "decoration_standard", nullable = true)
	private String decorationStandard;

	@Column(name = "HEALTH_FOOD_LICENCE_NO", nullable = true)
	private String healthFoodLicenceNo;

	@Column(name = "HEALTH_FOOD_LICENCE_GOVERNMENT", nullable = true)
	private String healthFoodLicenceGovernment;

	@Column(name = "HEALTH_FOOD_LICENCE_NAME", nullable = true)
	private String healthFoodLicenceName;

	@Column(name = "HEALTH_FOOD_LICENCE_MATURITY", nullable = true)
	private Date healthFoodLicenceMaturity;

	/** 个人照片 */
	@Column(name = "icon_res_id", nullable = true, length = 200)
	private String iconResId;

	/** 经度 */
	@Column(name = "LOCATION_X", nullable = false)
	private BigDecimal locationX = BigDecimal.ZERO;

	/** 纬度 */
	@Column(name = "LOCATION_Y", nullable = false)
	private BigDecimal locationY = BigDecimal.ZERO;

	/** 固定费用合计 */
	@Column(name = "TOTAL_FIXED_AMT", nullable = true, length = 11)
	private BigDecimal totalFixedAmt = BigDecimal.ZERO;

	/** 其它费用合计*/
	@Column(name = "TOTAL_OTHER_AMT", nullable = true, length = 11)
	private BigDecimal totalOtherAmt = BigDecimal.ZERO;

	/** 装修费用*/
	@Column(name = "DECORATION_AMT", nullable = true, length = 11)
	private BigDecimal decorationAmt = BigDecimal.ZERO;

	/** 租金 */
	@Column(name = "RENT_AMT", nullable = true, length = 11)
	private BigDecimal rentAmt = BigDecimal.ZERO;

	/** 薪金*/
	@Column(name = "SALARY_AMT", nullable = true, length = 11)
	private BigDecimal salaryAmt = BigDecimal.ZERO;

	/** 水电费 */
	@Column(name = "HYDROPOWER_AMT", nullable = true, length = 11)
	private BigDecimal hydropowerAmt = BigDecimal.ZERO;

	/** 店铺总楼层 */
	@Column(name = "TOTAL_FLOOR", nullable = true, length = 3)
	private BigDecimal totalFloor = BigDecimal.ZERO;

	/** 店员人数 */
	@Column(name = "CLERK_QTY", nullable = true, length = 10)
	private BigDecimal clerkQty = BigDecimal.ZERO;

	@Column(name = "CORP_LEGAL_PERSON")
	private String corpLegalPerson;

}
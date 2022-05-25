package org.macula.cloud.po.gbss.type;

/**
 * <p>
 * <b>TypeDealerStore</b> 平台管理
 * </p>
 *
 
 
 
 *
 */
public class TypeDealerStore {

	/** 平台类型  1:专卖店 */
	public final static String STORE_RUN_TYPE_01 = "1";
	/** 平台类型  4:可订货工作室*/
	public final static String STORE_RUN_TYPE_04 = "4";
	/** 平台类型  5:不可订货工作室 */
	public final static String STORE_RUN_TYPE_05 = "5";
	/** 平台类型  9:失效 */
	public final static String STORE_RUN_TYPE_09 = "9";

	/** 申请状态  INPUTING：录入中 */
	public final static String APP_STATUS_INPUTING = "INPUTING";
	/** 申请状态  SUBMITTED:新增 */
	public final static String APP_STATUS_SUBMITTED = "SUBMITTED";
	/** 申请状态  VOUCHED：推荐通过 */
	public final static String APP_STATUS_VOUCHED = "VOUCHED";
	/** 申请状态  DISVOUCHED：推荐不通过 */
	public final static String APP_STATUS_DISVOUCHED = "DISVOUCHED";
	/** 申请状态  APPROVED：审批通过 */
	public final static String APP_STATUS_APPROVED = "APPROVED";
	/** 申请状态  REJECTED：审批不通过 */
	public final static String APP_STATUS_REJECTED = "REJECTED";
	/** 申请状态  REAPPROVED：待复审 */
	public final static String APP_STATUS_IN_RE_APPROVED = "INREAPPROVED";//add by zhuohr
	/** 申请状态  REREJECTED：复审不通过 */
	public final static String APP_STATUS_RE_REJECTED = "REREJECTED";//add by zhuohr
	/** 申请状态  1ST_APPROVED: 初审通过 */
	public final static String APP_STATUS_1ST_APPROVED = "1ST_APPROVED";//add by zhuohr
	/** 申请状态  DELETED：已删除 */
	public final static String APP_STATUS_DELETED = "DELETED";

	/** 申请状态 ——营业质料录入中：OP_INPUTING*/
	public final static String APP_OP_INPUTING = "OP_INPUTING";
	/** 申请状态 ——营业质料已录入：OP_SUBMITTED */
	public final static String APP_OP_SUBMITTED = "OP_SUBMITTED";
	/** 申请状态 ——营业初审通过：OP_INIT_APPROVED*/
	public final static String APP_OP_INIT_APPROVED = "OP_INIT_APPROVED";
	/** 申请状态 ——营业初审不通过：OP_INIT_REJECTED */
	public final static String APP_OP_INIT_REJECTED = "OP_INIT_REJECTED";
	/** 申请状态 ——营业审批通过：OP_APPROVED*/
	public final static String APP_OP_APPROVED = "OP_APPROVED";
	/** 申请状态 ——营业审批不通过：OP_REJECTED */
	public final static String APP_OP_REJECTED = "OP_REJECTED";
	/** 申请状态 ——营业审批终止：OP_STOP   报备二期  zdm 2016-8-8*/
	public final static String APP_OP_STOP = "OP_STOP";

	/** 平台运作状态  00．新增 */
	public final static String STORE_RUN_STATUS_00 = "00";
	/** 平台运作状态  01．正常运作 */
	public final static String STORE_RUN_STATUS_01 = "01";
	/** 平台运作状态  02．不活跃 */
	public final static String STORE_RUN_STATUS_02 = "02";
	/** 平台运作状态  11．暂停运作*/
	public final static String STORE_RUN_STATUS_11 = "11";
	/** 平台运作状态  12．停止运作 */
	public final static String STORE_RUN_STATUS_12 = "12";
	/** 平台运作状态  99．店铺失效 */
	public final static String STORE_RUN_STATUS_99 = "99";
	/** 平台运作状态  98．其他 */
	public final static String STORE_RUN_STATUS_98 = "98";

	/** 平台停办申请状态  02．待分公司审批*/
	public final static String STORE_STOP_STATUS_02 = "02";
	/** 平台停办申请状态  03．待总公司审批*/
	public final static String STORE_STOP_STATUS_03 = "03";
	/** 待订货小组确认 */
	public static final String DEALER_STOP_DOC_ACHIEVE = "04";
	/** 平台停办申请状态  05．确认结束 , 归档*/
	public final static String STORE_STOP_STATUS_05 = "05";
	/** 平台停办申请状态  99．删除*/
	public final static String STORE_STOP_STATUS_99 = "99";

	/** 平台停办申请状态  12．分公司审批不通过*/
	public final static String STORE_STOP_STATUS_12 = "12";
	/** 平台停办申请状态  13．总公司审批不通过*/
	public final static String STORE_STOP_STATUS_13 = "13";

	/** 平台停办帐户冻结  *FROZEN*/
	public final static String STORE_STOP_AR_FROZEN = "*FROZEN";

	/** 申请类型 ——H：总公司输入 */
	public final static String APP_CLASS_H = "H";
	/** 申请类型 ——B：分公司输入 */
	public final static String APP_CLASS_B = "B";

	/** 平台状态变化类型  00. 初始化*/
	public final static String STATUS_CHG_CLASS_00 = "00";
	/** 平台状态变化类型  11. 自愿终止*/
	public final static String STATUS_CHG_CLASS_11 = "11";
	/** 平台状态变化类型  12. 自动降级*/
	public final static String STATUS_CHG_CLASS_12 = "12";
	/** 平台状态变化类型  13. 违规降级*/
	public final static String STATUS_CHG_CLASS_13 = "13";
	/** 平台状态变化类型  21. 店铺管理*/
	public final static String STATUS_CHG_CLASS_21 = "21";
	/** 平台状态变化类型  24. 身份终止*/
	public final static String STATUS_CHG_CLASS_24 = "24";

	/** 平台管理申请类型  *A. 申请平台*/
	public final static String STORE_APP_TYPE_A = "*A";
	/** 平台管理申请类型  *X. 平台续约申请*/
	public final static String STORE_APP_TYPE_X = "*X";
	/** 平台管理申请类型  *U. 平台资料维护申请*/
	public final static String STORE_APP_TYPE_U = "*U";

	/** 
	 * 平台管理申请职级界定  50 
	 * >=50 经办人职级要求
	 * < 50 需要推荐
	 */
	public final static int ASSURED_POST_CODE = 50;
	/**
	 * 平台管理申请职级>=70  可申请两个平台
	 */
	public final static int APP_TWOSTORE_POST_CODE = 70;

	/**
	 * GROUND_AREA_CLASS 区划类型的枚举值常量
	 */
	public final static String GROUND_AREA_CLASS_A = "省会";
	public final static String GROUND_AREA_CLASS_B = "直辖市";
	public final static String GROUND_AREA_CLASS_C = "地级市";
	public final static String GROUND_AREA_CLASS_D = "县级";

	/** 最大库存金额：125000 */
	public final static Long MAX_STOCK_AMT = 125000L;

	/** 前端续约申请提交开始日期系统参数 */
	public final static String sysParam_dayLimit_start = "EXTEND_DAY_LIMIT_START";
	/** 前端续约申请提交开始日期(默认4月18日) */
	public final static String dayLimit_start = "0418";
	/** 前端续约申请提交结束日期系统参数 */
	public final static String sysParam_dayLimit_end = "EXTEND_DAY_LIMIT_END";
	/** 前端续约申请提交结束日期(默认6月30日) */
	public final static String dayLimit_end = "0630";

	/**  2015.4.28 xuyunbo */
	/** 经办人变更申请审核系统参数 */
	public final static String CHG_LINE_AUDIT_START = "CHG_LINE_AUDIT_START";
	/**经办人变更申请审核开始日期(默认当月6日) */
	public final static String dayAudit_start = "06";
	/** 经办人变更申请审核系统参数 */
	public final static String CHG_LINE_AUDIT_END = "CHG_LINE_AUDIT_END";
	/** 经办人变更申请审核结束日期(默认当月25日) */
	public final static String dayAudit_end = "25";

}

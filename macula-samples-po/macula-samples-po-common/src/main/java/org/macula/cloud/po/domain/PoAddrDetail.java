package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * 订货配送地址
 
 
 * @version
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_ADDR_DETAIL")
public class PoAddrDetail extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = -6299180057844825268L;

	/**
	 * 收货地址区域ID
	 */
	@Column(name = "ADDR_AREA_CODE")
	private String addrAreaCode;

	/**
	 * 收货地址市(县)
	 */
	@Column(name = "ADDR_CITY")
	private String addrCity;

	/**
	 * 收货地址区
	 */
	@Column(name = "ADDR_COUNTY")
	private String addrCounty;

	/**
	 * 收货详细地址
	 */
	@Column(name = "ADDR_DETAIL")
	private String addrDetail;

	/**
	 * 收货地址镇
	 */
	@Column(name = "ADDR_DISTRICT")
	// ADDR_DISTRIC -->ADDR_DISTRICT字段名称修正
	private String addrDistrict;

	/**
	 * 收货地址省
	 */
	@Column(name = "ADDR_PROVINCE")
	private String addrProvince;

	/**
	 * 收货地址ID
	 */
	@Column(name = "ADDR_SEND_ID")
	private String addrSendId;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS")
	private String comments;

	/**
	 * 销售单据配送属性
	 */
	@Column(name = "DELIVERY_ATTR")
	private String deliveryAttr;

	/**
	 * 提货地点/专卖店卡号
	 */
	@Column(name = "DELIVERY_DEALER_NO")
	private String deliveryDealerNo;

	/**
	 * 配送扣库存标志
	 */
	@Column(name = "DELIVERY_HOLD_STOCK_FLAG")
	private String deliveryHoldStockFlag;

	/**
	 * 计划触发处理日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DELIVERY_PLAN_DATE")
	private Date deliveryPlanDate;

	/**
	 * 计划触发处理标志
	 */
	@Column(name = "DELIVERY_PLAN_PROC_FLAG")
	private String deliveryPlanProcFlag;

	/**
	 * 触发交货文件号
	 */
	@Column(name = "DELIVERY_PLAN_DOC_NO")
	private String deliveryPlanDocNo;

	/**
	 * 销售单据配送状态
	 */
	@Column(name = "DELIVERY_STATUS")
	private String deliveryStatus;

	/**
	 * 提货店铺运作类型
	 */
	@Column(name = "DELIVERY_STORE_RUN_TYPE")
	private String deliveryStoreRunType;

	/**
	 * 提货类型
	 */
	@Column(name = "DELIVERY_TYPE")
	private String deliveryType;

	/**
	 * 配送出货RDC
	 */
	@Column(name = "DELIVERY_WH_CODE")
	private String deliveryWhCode;

	/**
	 * 配送出货RDC库区
	 */
	@Column(name = "DELIVERY_WH_LOC_CODE")
	private String deliveryWhLocCode;

	/**
	 * 收货人1证件类型
	 */
	@Column(name = "R01_CERTIFICATE_TYPE")
	private String r01CertificateType;

	/**
	 * 收货人2证件类型
	 */
	@Column(name = "R02_CERTIFICATE_TYPE")
	private String r02CertificateType;

	/**
	 * 收货人3证件类型
	 */
	@Column(name = "R03_CERTIFICATE_TYPE")
	private String r03CertificateType;

	/**
	 * 提货日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PICK_UP_DATE")
	private Date pickUpDate;

	/**
	 * 提货输入用户
	 */
	@Column(name = "PICK_UP_ENTRY_BY")
	private String pickUpEntryBy;

	/**
	 * 提货输入时间
	 */
	@Column(name = "PICK_UP_ENTRY_TIME")
	private Timestamp pickUpEntryTime;

	/**
	 * 持卡客户提货状态
	 */
	@Column(name = "PICK_UP_STATUS")
	private String pickUpStatus;

	/**
	 * 提货验证码
	 */
	@Column(name = "PICK_UP_VERIFY_CODE")
	private String pickUpVerifyCode;

	/**
	 * 订货单号
	 */
	@Column(name = "PO_NO")
	private String poNo;

	/**
	 * 收货人1身份证号
	 */
	@Column(name = "R01_CERTIFICATE_NO")
	private String r01CertificateNo;

	/**
	 * 收货人1姓名
	 */
	@Column(name = "R01_FULL_NAME")
	private String r01FullName;

	/**
	 * 收货人1电话
	 */
	@Column(name = "R01_TELES")
	private String r01Teles;

	/**
	 * 收货人2身份证号
	 */
	@Column(name = "R02_CERTIFICATE_NO")
	private String r02CertificateNo;

	/**
	 * 收货人2姓名
	 */
	@Column(name = "R02_FULL_NAME")
	private String r02FullName;

	/**
	 * 收货人2电话
	 */
	@Column(name = "R02_TELES")
	private String r02Teles;

	/**
	 * 收货人3身份证号
	 */
	@Column(name = "R03_CERTIFICATE_NO")
	private String r03CertificateNo;

	/**
	 * 收货人3姓名
	 */
	@Column(name = "R03_FULL_NAME")
	private String r03FullName;

	/**
	 * 收货人3电话
	 */
	@Column(name = "R03_TELES")
	private String r03Teles;

	/**
	 * 库存处理备注
	 */
	@Column(name = "STOCK_PROC_MEMO")
	private String stockProcMemo;

	/**
	 * 库存处理检查标志
	 */
	@Column(name = "STOCK_RECHECK_FLAG")
	private String stockRecheckFlag;

	/**
	 * 库存处理检查时间
	 */
	@Column(name = "STOCK_RECHECK_TIME")
	private Timestamp stockRecheckTime;

	/**
	 * 运单号
	 */
	@Column(name = "SHIPMENT_NO")
	private String shipmentNo;

	/**
	 * 收货状态
	 */
	@Column(name = "SHIPMENT_RECEIVE_STATUS")
	private String shipmentReceiveStatus;

	/**
	 * 配送打包类型
	 */
	@Column(name = "DELIVERY_PACKAGE_TYPE")
	private String deliveryPackageType;

}
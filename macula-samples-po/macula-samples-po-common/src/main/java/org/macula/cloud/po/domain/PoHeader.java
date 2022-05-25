package org.macula.cloud.po.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * SAP部分Header需要的字段集合表
 
 
 * @version
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PO_HEADER")
@Getter
@Setter
public class PoHeader extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	@Column(name = "PO_NO")
	private String poNo;

	/**
	 * SAP的订货流程代码
	 */
	@Column(name = "SAP_PROCESS_CODE")
	private String sapProcessCode;

	/**
	 * 订货经销商卡号
	 */
	@Column(name = "ORDER_DEALER_NO") // poMaster.getOrderDealerNo()
	private String orderDealerNo;

	/**
	 * 销售片区编号
	 */
	@Column(name = "SALE_ZONE_NO") // poMaster.getOrderDealerNo()
	private String saleZoneNo;

	/**
	 * 所属分公司编号
	 */
	@Column(name = "SALE_BRANCH_NO") // poMaster.getOrderDealerNo()
	private String saleBranchNo;

	/**
	 * 国税注册号
	 */
	@Column(name = "STORE_NTAX_REGISTER_NO") // poMaster.getOrderDealerNo()
	private String dealerStoreNtaxRegisterNo;

	/**
	 * 店铺姓名
	 */
	@Column(name = "STORE_FULL_NAME") // poMaster.getOrderDealerNo()
	private String dealerStoreFullname;

	/**
	 * 优消卡号
	 */
	@Column(name = "CUSTOMER_DEALER_NO")
	private String customerDealerNo;

	/**
	 * 优消手机号码
	 */
	@Column(name = "CUSTOMER_MOBILE") // poMaster.getOrderCustomerNo()
	private String dealerCustomerMobile;

	/**
	 * 优消姓名
	 */
	@Column(name = "CUSTOMER_FULL_NAME") // poMaster.getOrderCustomerNo()
	private String dealerCustomerFullname;

	/**
	 * 到货店铺卡号
	 */
	@Column(name = "DELIVERY_DEALER_NO")
	private String deliveryDealerNo;

	/**
	 * 到货店铺姓名
	 */
	@Column(name = "DELIVERY_FULL_NAME") // poAddrDetail.getDeliveryDealerNo()
	private String dealerDeliveryFullname;

}
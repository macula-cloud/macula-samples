package org.macula.cloud.po.gbss.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>POS_DELIVERY_DETAIL</b> 是
 * </p>
 * 
 
 
 
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "POS_DELIVERY_DETAIL")
public class PosDeliveryDetail extends LegacyUpdateable<Long> {

	private static final long serialVersionUID = 1L;

	/**交货单号*/
	@Column(name = "POS_DELIVERY_NO", nullable = false, length = 15)
	private String deliveryNo;

	/**行号*/
	@Column(name = "LINE_NO", nullable = false, length = 6)
	private Long lineNo;

	/**产品代码*/
	@Column(name = "PRODUCT_CODE", nullable = false, length = 15)
	private String productCode;

	/**产品名称*/
	@Transient
	private String productName;

	/**产品批号*/
	@Column(name = "PRODUCT_LOT_NO", nullable = false, length = 15)
	private String productLotNo;

	/**发货数*/
	@Column(name = "DELIVERY_QTY", nullable = false, length = 8)
	private Long deliveryQty;

	/**参考销售数量*/
	@Column(name = "REF_SALE_QTY", nullable = false, length = 8)
	private Long refSaleQty;

	/**产品单位*/
	@Transient
	private String unitMeasure;

	/**订货配送地址ID*/
	@Transient
	private Long padId;

	/**发货备注说明*/
	@Transient
	private String deliveryMemo;

	/**发货日期*/
	@Transient
	private Date pickUpDate;

	/**发货人*/
	@Transient
	private String pickUpEntryBy;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**
	 * @return deliveryNo 交货单号
	 */
	public String getDeliveryNo() {
		return this.deliveryNo;
	}

	/**
	 * @param deliveryNo 交货单号的设置
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	/**
	 * @return lineNo 行号
	 */
	public Long getLineNo() {
		return this.lineNo;
	}

	/**
	 * @param lineNo 行号的设置
	 */
	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * @return productCode 产品代码
	 */
	public String getProductCode() {
		return this.productCode;
	}

	/**
	 * @param productCode 产品代码的设置
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return productLotNo 产品批号
	 */
	public String getProductLotNo() {
		return this.productLotNo;
	}

	/**
	 * @param productLotNo 产品批号的设置
	 */
	public void setProductLotNo(String productLotNo) {
		this.productLotNo = productLotNo;
	}

	/**
	 * @return deliveryQty 发货数
	 */
	public Long getDeliveryQty() {
		return this.deliveryQty;
	}

	/**
	 * @param deliveryQty 发货数的设置
	 */
	public void setDeliveryQty(Long deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	/**
	 * @return refSaleQty 参考销售数量
	 */
	public Long getRefSaleQty() {
		return this.refSaleQty;
	}

	/**
	 * @param refSaleQty 参考销售数量的设置
	 */
	public void setRefSaleQty(Long refSaleQty) {
		this.refSaleQty = refSaleQty;
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the unitMeasure
	 */
	public String getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * @param unitMeasure the unitMeasure to set
	 */
	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	/**
	 * @return the padId
	 */
	public Long getPadId() {
		return padId;
	}

	/**
	 * @param padId the padId to set
	 */
	public void setPadId(Long padId) {
		this.padId = padId;
	}

	/**
	 * @return the deliveryMemo
	 */
	public String getDeliveryMemo() {
		return deliveryMemo;
	}

	/**
	 * @param deliveryMemo the deliveryMemo to set
	 */
	public void setDeliveryMemo(String deliveryMemo) {
		this.deliveryMemo = deliveryMemo;
	}

	/**
	 * @return the pickUpDate
	 */
	public Date getPickUpDate() {
		return pickUpDate;
	}

	/**
	 * @param pickUpDate the pickUpDate to set
	 */
	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	/**
	 * @return the pickUpEntryBy
	 */
	public String getPickUpEntryBy() {
		return pickUpEntryBy;
	}

	/**
	 * @param pickUpEntryBy the pickUpEntryBy to set
	 */
	public void setPickUpEntryBy(String pickUpEntryBy) {
		this.pickUpEntryBy = pickUpEntryBy;
	}

	public PosDeliveryDetail() {
	};

	public PosDeliveryDetail(Long id, String deliveryNo, Long lineNo, String productCode, String productName, String productLotNo, Long deliveryQty,
			Long refSaleQty, String unitMeasure, Long padId, String deliveryMemo, Date pickUpDate, String pickUpEntryBy, String comments) {
		super.setId(id);
		this.deliveryNo = deliveryNo;
		this.lineNo = lineNo;
		this.productCode = productCode;
		this.productName = productName;
		this.productLotNo = productLotNo;
		this.deliveryQty = deliveryQty;
		this.refSaleQty = refSaleQty;
		this.unitMeasure = unitMeasure;
		this.padId = padId;
		this.deliveryMemo = deliveryMemo;
		this.pickUpDate = pickUpDate;
		this.pickUpEntryBy = pickUpEntryBy;
		this.comments = comments;
	}

}

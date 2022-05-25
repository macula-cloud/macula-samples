/**
 * PosReplenishDetail.java 2012-04-25
 */
package org.macula.cloud.po.gbss.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

/**
 * <p>
 * <b>PosReplenishDetail</b> 是自营店补货申请信息
 * </p>
 * 
 
 
 
 * 
 */
@Entity
@Table(name = "POS_REPLENISH_DETAIL")
@DynamicInsert
@DynamicUpdate
public class PosReplenishDetail extends LegacyUpdateable<Long> {
	private static final long serialVersionUID = 1L;

	/**订货申请单号*/
	@Column(name = "APP_NO", nullable = false, length = 15)
	@javax.validation.constraints.Size(min = 1, max = 15)
	private String appNo;

	/**行号*/
	@Column(name = "LINE_NO", nullable = false, length = 10)
	private Long lineNo;

	/**申请数量*/
	@Column(name = "APP_QTY", nullable = false, length = 10)
	private Long appQty;

	/**产品代码*/
	@Column(name = "PRODUCT_CODE", nullable = false, length = 8)
	@javax.validation.constraints.Size(min = 1, max = 8)
	private String productCode;

	/**产品批号*/
	@Column(name = "PRODUCT_LOT_NO", nullable = true)
	private String productLotNo;

	/**产品原价格*/
	@Column(name = "PRODUCT_PRICE", nullable = false, length = 11)
	private BigDecimal productPrice;

	/**产品原点数*/
	@Column(name = "PRODUCT_POINT", nullable = false, length = 11)
	private BigDecimal productPoint;

	/**SAP处理数量*/
	@Column(name = "SAP_TRAN_QTY", nullable = false, length = 10)
	private long sapTranQty;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

	/**质量原因*/
	@Column(name = "QA_REASON", nullable = true, length = 500)
	private String qaReason;

	@Transient
	private String matnrname;

	/**库存对比日志描述*/
	@Column(name = "LOG_CONTENT")
	private String logContent;

	/**
	 * @return appNo 订货申请单号
	 */
	public String getAppNo() {
		return this.appNo;
	}

	/**
	 * @param appNo 订货申请单号的设置
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
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
	 * @return appQty 申请数量
	 */
	public Long getAppQty() {
		return this.appQty;
	}

	/**
	 * @param appQty 申请数量的设置
	 */
	public void setAppQty(Long appQty) {
		this.appQty = appQty;
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
	 * @return productPrice 产品原价格
	 */
	public BigDecimal getProductPrice() {
		return this.productPrice;
	}

	/**
	 * @param productPrice 产品原价格的设置
	 */
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * @return productPoint 产品原点数
	 */
	public BigDecimal getProductPoint() {
		return this.productPoint;
	}

	/**
	 * @param productPoint 产品原点数的设置
	 */
	public void setProductPoint(BigDecimal productPoint) {
		this.productPoint = productPoint;
	}

	/**
	 * @return sapTranQty SAP处理数量
	 */
	public Long getSapTranQty() {
		return this.sapTranQty;
	}

	/**
	 * @param sapTranQty SAP处理数量的设置
	 */
	public void setSapTranQty(Long sapTranQty) {
		this.sapTranQty = sapTranQty;
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
	 * @return the productLotNo
	 */
	public String getProductLotNo() {
		return productLotNo;
	}

	/**
	 * @param productLotNo the productLotNo to set
	 */
	public void setProductLotNo(String productLotNo) {
		this.productLotNo = productLotNo;
	}

	public String getMatnrname() {
		return matnrname;
	}

	public void setMatnrname(String matnrname) {
		this.matnrname = matnrname;
	}

	/**
	 * @return the logContent 库存对比日志描述
	 */
	public String getLogContent() {
		return logContent;
	}

	/**
	 * @param logContent  库存对比日志描述
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	/**
	 * @return the qaReason
	 */
	public String getQaReason() {
		return qaReason;
	}

	/**
	 * @param qaReason the qaReason to set
	 */
	public void setQaReason(String qaReason) {
		this.qaReason = qaReason;
	}

}

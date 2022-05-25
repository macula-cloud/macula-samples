package org.macula.cloud.po.gbss.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>PlantDnMaster</b>是DN单(非数据库类)
 * 转储收货、顾客退货收货、退仓发货、顾客提货发货都使用此类，但不用保存数据库
 * </p>
 * NOTE: 从旧系统中迁移过来，目前只用于转储收货。by xgd 2012-05-28
 * 
 
 
 */

public class PlantDnMaster {
	/** 交货单单号 */
	private String dnNo;

	/** 交货单日期 */
	private Date dnDate;

	/** 仓库代码 */
	private String whCode;

	/** 自营店名称 */
	private String soDealerName;

	/** 发货仓代码 */
	private String fromWhCode;

	/** 收货仓代码 */
	private String toWhCode;

	/** 交货类型 */
	private String dnType;

	/** 移动类型代码(参考LocMoveInfo) */
	private String moveNo;

	/** 拣货状态 */
	private String dnStatus;

	/** 顾客提货、顾客退货对应销售单号,退仓发货、转储收货对应转储单号 */
	private String refDocNo;

	/** 备注 */
	private String comments;

	/** 明细 */
	private List<PlantDnDetail> plantDnDetails = new ArrayList<PlantDnDetail>();

	/** SAP物料凭证号 */
	private String sapTranNo;

	/** SAP凭证年度 */
	private String sapDocYear;

	/**
	 * @return Returns the dnNo.
	 */
	public String getDnNo() {
		return dnNo;
	}

	/**
	 * @param dnNo The dnNo to set.
	 */
	public void setDnNo(String dnNo) {
		this.dnNo = dnNo;
	}

	/**
	 * @return Returns the dnDate.
	 */
	public Date getDnDate() {
		return dnDate;
	}

	/**
	 * @param dnDate The dnDate to set.
	 */
	public void setDnDate(Date dnDate) {
		this.dnDate = dnDate;
	}

	/**
	 * @return Returns the whCode.
	 */
	public String getWhCode() {
		return whCode;
	}

	/**
	 * @param whCode The whCode to set.
	 */
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	/**
	 * @return Returns the soDealerName.
	 */
	public String getSoDealerName() {
		return soDealerName;
	}

	/**
	 * @param soDealerName The soDealerName to set.
	 */
	public void setSoDealerName(String soDealerName) {
		this.soDealerName = soDealerName;
	}

	/**
	 * @return Returns the fromWhCode.
	 */
	public String getFromWhCode() {
		return fromWhCode;
	}

	/**
	 * @param fromWhCode The fromWhCode to set.
	 */
	public void setFromWhCode(String fromWhCode) {
		this.fromWhCode = fromWhCode;
	}

	/**
	 * @return Returns the toWhCode.
	 */
	public String getToWhCode() {
		return toWhCode;
	}

	/**
	 * @param toWhCode The toWhCode to set.
	 */
	public void setToWhCode(String toWhCode) {
		this.toWhCode = toWhCode;
	}

	/**
	 * @return Returns the dnType.
	 */
	public String getDnType() {
		return dnType;
	}

	/**
	 * @param dnType The dnType to set.
	 */
	public void setDnType(String dnType) {
		this.dnType = dnType;
	}

	/**
	 * @return Returns the dnStatus.
	 */
	public String getDnStatus() {
		return dnStatus;
	}

	/**
	 * @return Returns the moveNo.
	 */
	public String getMoveNo() {
		return moveNo;
	}

	/**
	 * @param moveNo The moveNo to set.
	 */
	public void setMoveNo(String moveNo) {
		this.moveNo = moveNo;
	}

	/**
	 * @param dnStatus The dnStatus to set.
	 */
	public void setDnStatus(String dnStatus) {
		this.dnStatus = dnStatus;
	}

	/**
	 * @return Returns the refDocNo.
	 */
	public String getRefDocNo() {
		return refDocNo;
	}

	/**
	 * @param refDocNo The refDocNo to set.
	 */
	public void setRefDocNo(String refDocNo) {
		this.refDocNo = refDocNo;
	}

	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return Returns the plantDnDetails.
	 */
	public List<PlantDnDetail> getPlantDnDetails() {
		return plantDnDetails;
	}

	/**
	 * @param plantDnDetails The plantDnDetails to set.
	 */
	public void setPlantDnDetails(List<PlantDnDetail> plantDnDetails) {
		this.plantDnDetails = plantDnDetails;
	}

	/**
	 * @return Returns the sapTranNo.
	 */
	public String getSapTranNo() {
		return sapTranNo;
	}

	/**
	 * @param sapTranNo The sapTranNo to set.
	 */
	public void setSapTranNo(String sapTranNo) {
		this.sapTranNo = sapTranNo;
	}

	/**
	 * @return Returns the sapDocYear.
	 */
	public String getSapDocYear() {
		return sapDocYear;
	}

	/**
	 * @param sapDocYear The sapDocYear to set.
	 */
	public void setSapDocYear(String sapDocYear) {
		this.sapDocYear = sapDocYear;
	}

}

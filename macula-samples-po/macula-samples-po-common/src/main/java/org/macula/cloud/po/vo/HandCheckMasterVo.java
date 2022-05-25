package org.macula.cloud.po.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HandCheckMasterVo {

	/**
	 * 对账开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	/**
	 * 对账截止时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	/**
	 * SAP单号/SapPostingDocNo
	 */
	private List<String> sapPostingDocNo;
	/**
	 * 销售订单号
	 */
	private List<String> poNo;
	/**
	 * 流程代码
	 */
	private List<String> poProcessCode;
	/**
	 * 订单状态
	 */
	private List<String> poStatus;
	/**
	 * 购货人卡号
	 */
	private List<String> orderDealerNo;

}

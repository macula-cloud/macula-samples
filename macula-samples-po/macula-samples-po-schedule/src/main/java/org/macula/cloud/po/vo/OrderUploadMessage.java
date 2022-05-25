package org.macula.cloud.po.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回推送结果给GBSS，该信息发送给MQ
 * 2020-07-09
 
 */
@Getter
@Setter
public class OrderUploadMessage {

	/**
	 * 单号
	 */
	private String poNo;

	/**
	 * 推送SAP失败标记:false:失败,true:成功
	 */
	private boolean success;

	/**
	 * 生成SAP单号
	 */
	private String sapPostingDocNo;

	/**
	 * 生成Flag标记
	 */
	private String sapPostingFlag;

	/**
	 * 生成Date时间
	 */
	private Date sapPostingDate;

	/**
	 * 推送SAP失败信息
	 */
	private String errorMessage;

	public OrderUploadMessage() {

	}

	public OrderUploadMessage(String poNo) {
		this.poNo = poNo;
	}

	public OrderUploadMessage updateSuccess(String sapPostingDocNo, Date sapPostingDate) {
		this.success = true;
		this.sapPostingDocNo = sapPostingDocNo;
		this.sapPostingDate = sapPostingDate;
		this.sapPostingFlag = "Y";
		return this;
	}

	public OrderUploadMessage updateFailed(String message) {
		this.success = false;
		this.errorMessage = message;
		return this;
	}
}

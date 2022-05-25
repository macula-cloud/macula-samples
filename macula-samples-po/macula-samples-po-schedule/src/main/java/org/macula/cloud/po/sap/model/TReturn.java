package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

import lombok.ToString;

@BapiStructure
@ToString
public class TReturn {

	/**
	 * S 成功   E 失败
	 */
	@Parameter("TYPE")
	private String type;

	@Parameter("ID")
	private String id;

	@Parameter("NUMBER")
	private String number;

	@Parameter("MESSAGE")
	private String message;

	@Parameter("LOG_NO")
	private String logNo;

	@Parameter("LOG_MSG_NO")
	private String logMsgNo;

	@Parameter("MESSAGE_V1")
	private String messageV1;

	@Parameter("MESSAGE_V2")
	private String messageV2;

	@Parameter("MESSAGE_V3")
	private String messageV3;

	@Parameter("MESSAGE_V4")
	private String messageV4;

	@Parameter("PARAMETER")
	private String parameter;

	@Parameter("ROW")
	private Integer row;

	@Parameter("FIELD")
	private String filed;

	@Parameter("SYSTEM")
	private String system;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getLogMsgNo() {
		return logMsgNo;
	}

	public void setLogMsgNo(String logMsgNo) {
		this.logMsgNo = logMsgNo;
	}

	public String getMessageV1() {
		return messageV1;
	}

	public void setMessageV1(String messageV1) {
		this.messageV1 = messageV1;
	}

	public String getMessageV2() {
		return messageV2;
	}

	public void setMessageV2(String messageV2) {
		this.messageV2 = messageV2;
	}

	public String getMessageV3() {
		return messageV3;
	}

	public void setMessageV3(String messageV3) {
		this.messageV3 = messageV3;
	}

	public String getMessageV4() {
		return messageV4;
	}

	public void setMessageV4(String messageV4) {
		this.messageV4 = messageV4;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

}

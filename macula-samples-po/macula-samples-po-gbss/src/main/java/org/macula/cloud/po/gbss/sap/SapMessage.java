package org.macula.cloud.po.gbss.sap;

/**
 * <p>
 * <b>SapMessage</b> 是SAP返回消息类
 * </p>
 * 
 
 
 
 * 
 */

public class SapMessage {
	private String type;

	private String code;

	private String message;

	private String id;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return SapResult.RESULT_CODE_SUCCESS.equals(getType());
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}

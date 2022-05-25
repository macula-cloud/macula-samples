package org.macula.cloud.po.gbss.sap;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <b>SapResult</b> 是是SAP结果的接口，返回结果必须实现该接口
 * </p>
 * 
 
 
 
 * 
 */

public abstract class SapResult {
	public static final String RESULT_CODE_SUCCESS = "S";

	public static final String RESULT_CODE_ERROR = "E";

	public static final String ERROR_CODE_SUCCESS = "200";

	private List<SapMessage> messages;

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return RESULT_CODE_SUCCESS.equals(getResultCode());
	}

	/**
	 * 结果代码 E,S等
	 * 
	 * @return
	 */
	public String getResultCode() {
		if (messages != null && messages.size() > 0) {
			for (Iterator<SapMessage> iterator = messages.iterator(); iterator.hasNext();) {
				SapMessage msg = iterator.next();
				if (RESULT_CODE_ERROR.equals(msg.getType())) {
					return msg.getType();
				}
			}
		}
		return RESULT_CODE_SUCCESS;
	}

	/**
	 * 获取异常代码 200表示无异常
	 * 
	 * @return
	 */
	public final String getErrorCode() {
		return ERROR_CODE_SUCCESS;
	}

	/**
	 * @return the messages
	 */
	public List<SapMessage> getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(List<SapMessage> messages) {
		this.messages = messages;
	}

}

package org.macula.cloud.po.vo;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PoStatusChange implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 消息ID */
	@Column(name = "MSG_ID")
	private String messageId;
	/** 订单号 */
	@Column(name = "PO_NO")
	private String poNo;
	/** 订单状态 */
	@Column(name = "PO_STATUS")
	private String poStatus;
	/** 流程代码 */
	@Column(name = "PO_PROCESS_CODE")
	private String poProcessCode;
	/** 订单旧状态 */
	@Column(name = "PO_OLD_STATUS")
	private String oldStatus;

}

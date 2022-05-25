package org.macula.cloud.po.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.alibaba.druid.util.StringUtils;

public final class PoProcessStatus {

	// ====================  处理请求状态 ========================

	/** 收到PO MQ订单消息状态 */
	public final static String PO_MQ_REQUEST = "00";

	/** 收到UPL同步订单消息状态 */
	public final static String PO_UPL_REQUEST = "01";

	/** 收到自动重试推送状态 */
	public final static String PO_RETRY_REQUEST = "08";

	/** 收到手工重试推送状态 */
	public final static String PO_HAND_REQUEST = "09";

	/** 订单开始处理状态列表 */
	private final static List<String> START_GROUP = Arrays.asList(PO_MQ_REQUEST, PO_UPL_REQUEST, PO_RETRY_REQUEST, PO_HAND_REQUEST);

	public static boolean hasRequest(String status) {
		return status != null && START_GROUP.contains(status);
	}

	// ====================  处理终结状态 ========================

	/** 订单取消/冻结/删除成功标记 */
	public final static String CHANGED = "10";

	/** 处理成功 */
	public final static String SUCCESS = "88";

	/** 人工标识为处理成功 */
	public final static String SUCCESS_BY_HAND = "81";

	/** 失败后暂停 */
	public final static String PAUSE = "91";

	/** 失败 */
	public final static String ERROR = "99";

	/** 订单处理失败完成状态列表 */
	private final static List<String> ERROR_GROUP = Arrays.asList(ERROR, PAUSE);

	/** 订单处理暂停状态列表 */
	private final static List<String> PAUSE_GROUP = Arrays.asList(PAUSE);

	public static boolean hasError(String status) {
		return status != null && ERROR_GROUP.contains(status);
	}

	public static boolean hasPause(String status) {
		return status != null && PAUSE_GROUP.contains(status);
	}

	// ====================  处理中间状态 ========================
	/** 收到GBSS订单数据 */
	public final static String PO_GET_GBSS_DATA = "51";

	/** 收到保存订单到本地 */
	public final static String PO_SAVE_LOCAL = "52";

	/** 转化订单数据到SAP */
	public final static String PO_DATA_PACKAGE = "53";

	/** 推送订到到SAP */
	public final static String PO_UPLOAD_SAP = "54";

	/**回写到本地数据库 */
	public final static String PO_SAP_REWRITEN = "55";

	/**发送SAP单号给MQ */
	public final static String PO_SEND_GBSS_MQ = "56";

	/** 订单处理中状态列表 */
	private final static List<String> PROCESSING_GROUP = Arrays.asList(PO_GET_GBSS_DATA, PO_SAVE_LOCAL, PO_DATA_PACKAGE, PO_UPLOAD_SAP,
			PO_SAP_REWRITEN, PO_SEND_GBSS_MQ);

	private final static List<String> RETRY_GROUP = new ArrayList<String>();
	static {
		RETRY_GROUP.addAll(START_GROUP);
		RETRY_GROUP.addAll(PROCESSING_GROUP);
		RETRY_GROUP.addAll(ERROR_GROUP);
		RETRY_GROUP.removeAll(PAUSE_GROUP);
	}

	public static List<String> getRetryGroup() {
		return Collections.unmodifiableList(RETRY_GROUP);
	}

	public static boolean shouldStartRequest(String currentStatus, String requestStatus) {
		if (hasRequest(requestStatus)) {
			// 获取到订单
			if (Arrays.asList(PO_MQ_REQUEST, PO_UPL_REQUEST).contains(requestStatus)) {
				return StringUtils.isEmpty(currentStatus);
			}
			// 重推订单 
			if (Arrays.asList(PO_RETRY_REQUEST).contains(requestStatus)) {
				return !Arrays.asList(CHANGED, SUCCESS, SUCCESS_BY_HAND, PAUSE).contains(currentStatus);
			}
			// 强推订单 (均能强推)
			if (Arrays.asList(PO_HAND_REQUEST).contains(requestStatus)) {
				return true;
			}
		}
		return false;
	}

}

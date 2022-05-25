package org.macula.cloud.po.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "macula.cloud.oms")
public class OMSConfig {

	// 接收
	private String orderTopic;
	private String refundTopic;
	private String statusTopic;
	// 发送 
	private String callbackTopic;

	// 重试次数
	private int maxRetryTimes;

	// 对账
	private int poCheckRange;
	private int poValidateRange;

	//  最小ID值  sap_daily_upl_po
	private long minUploadId;
	private long minUpload2Id;

	// 是否模拟单号
	private String sapMock;
}

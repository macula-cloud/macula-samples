package org.macula.cloud.fulfilment.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

import lombok.Data;

@Data
// @Component
// @Configuration
public class AliRocketMqConfiguration {

	/**
	 * MQ 接入参数 accessKey
	 */
	@Value("${MQ.ACCESS_KEY}")
	private String accessKey;

	/**
	 * MQ 接入参数 secretKey
	 */
	@Value("${MQ.SECRET_KEY}")
	private String secretKey;

	/**
	 * MQ 的接入域名
	 */
	@Value("${MQ.NAMESRV_ADDR}")
	private String onsAddr;

	/**
	 * 审核通过新增活动 cid
	 */
	@Value("${MQ.cid}")
	private String cid;

	/**
	 * 审核通过新增活动 pid
	 */
	@Value("${MQ.pid}")
	private String pid;

	/**
	 * 审核通过新增活动 TOPIC
	 */
	@Value("${MQ.ACTIVITY_ADD_TOPIC}")
	private String activity_add_topic;

	/**
	 * 参观申请活动信息变更 TOPIC
	 */
	@Value("${MQ.ACTIVITY_UPDATE_TOPIC}")
	private String activity_update_topic;

	/**
	 * 参观申请活动用户行为记录 TOPIC
	 */
	@Value("${MQ.ACTIVITY_USER_BEHAVIOR_TOPIC}")
	private String activity_user_behavior_topic;

	/**
	 * 参观申请活动调查问卷推送 TOPIC
	 */
	@Value("${MQ.ACTIVITY_QUESTION_TOPIC}")
	private String activity_question_topic;

	/**
	 *  参观申请活动用户入场消息
	 */
	@Value("${MQ.ACTIVITY_ENTER_TOPIC}")
	private String activity_enter_topic;

	/**
	 * 创建MQ的生产方需要的属性
	 *
	 * @return 生产方接入的属性
	 */
	private Properties getProperties() {
		Properties properties = new Properties();
		// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.AccessKey, accessKey);
		// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.SecretKey, secretKey);
		// 设置 TCP 接入域名（此处以公共云生产环境为例）
		properties.put(PropertyKeyConst.ONSAddr, onsAddr);
		return properties;
	}

	public Producer producer() {
		Properties properties = getProperties();
		properties.put(PropertyKeyConst.GROUP_ID, pid);
		Producer producer = ONSFactory.createProducer(properties);
		producer.start();
		return producer;
	}

	/**
	 *  发送消息
	 * @param topic
	 * @param tag
	 * @param key
	 * @param body
	 * @return
	 */
	public SendResult sendMessage(String topic, String tag, String key, byte[] body) {
		Message msg = new Message(topic, tag, body);
		if (!StringUtils.isEmpty(key)) {
			msg.setKey(key);
		}
		return producer().send(msg);
	}

	public Consumer getConsumer(String cid) {
		Properties properties = getProperties();
		properties.put(PropertyKeyConst.GROUP_ID, cid);
		Consumer consumer = ONSFactory.createConsumer(properties);
		return consumer;
	}

}

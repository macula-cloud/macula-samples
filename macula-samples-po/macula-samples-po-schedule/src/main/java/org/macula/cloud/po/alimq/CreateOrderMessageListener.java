package org.macula.cloud.po.alimq;

import java.util.Arrays;
import java.util.List;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.mq.AliMQMessageListener;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.type.PoProcessSource;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.type.TypeSapProcessCode;
import org.macula.cloud.po.util.OmsUtils;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class CreateOrderMessageListener implements AliMQMessageListener {

	private OMSConfig config;
	private OrderProcessingService orderProcessingService;
	private OrderProcessControlService processControlService;

	/**
	 * 监听阿里MQ消息
	 * @param message
	 * @param context
	 * @return
	 */
	@Override
	public Action consume(Message message, ConsumeContext context) {
		String messageId = (String) message.getUserProperties().get("UNIQ_KEY");
		String key = message.getKey();
		String tag = message.getTag();
		String messageBody = new String(message.getBody());
		try {
			log.info("== Received {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} ", getTopic(), messageId, key, tag, messageBody);

			if (!processControlService.shouldMQPaidProcessing()) {
				log.error("== SHOULD NOT PROCESSING  PAID MQ LISTENING ==");
				log.info("== Completed  {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} , ReconsumeLater!!!", getTopic(), messageId, key, tag,
						messageBody);
				return Action.ReconsumeLater;
			}

			List<String> poNoList = OmsUtils.getOrderNoList(messageBody);
			for (String poNo : poNoList) {
				log.info(" === Processing PoNo: {} ", poNo);
				StatusStartRequest request = StatusStartRequest.builder().poNo(poNo).poProcessCode(tag).poSource(PoProcessSource.PO_SOURCE_MQ)
						.refSourceNo(messageId).statusTime(SystemUtils.getCurrentTime()).status(PoProcessStatus.PO_MQ_REQUEST).comments("接收到MQ消息")
						.build();
				Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
				if (log.isInfoEnabled()) {
					log.info(" === StatusStartRequest {} handleResult {}", request, handleResult);
				}
				if (handleResult != null && handleResult.booleanValue()) {
					CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
				}
				log.info("  === Completed PoNo: {} ", poNo);
			}
			log.info("== Completed {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} ", getTopic(), messageId, key, tag, messageBody);
			return Action.CommitMessage;
		} catch (Exception ex) {
			log.error("== Consum {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} ERROR !!!", getTopic(), messageId, key, tag, messageBody, ex);
			return Action.ReconsumeLater;
		}
	}

	@Override
	public String getTopic() {
		return config.getOrderTopic();
	}

	@Override
	public List<String> getSubExpression() {
		return Arrays.asList(TypeSapProcessCode.G001, TypeSapProcessCode.G003, TypeSapProcessCode.G010, TypeSapProcessCode.G018,
				TypeSapProcessCode.G019, TypeSapProcessCode.G022, TypeSapProcessCode.G023, TypeSapProcessCode.G012, TypeSapProcessCode.G013,
				TypeSapProcessCode.G002, TypeSapProcessCode.G101, TypeSapProcessCode.G020, TypeSapProcessCode.G104, TypeSapProcessCode.G108,
				TypeSapProcessCode.G909, TypeSapProcessCode.G910, TypeSapProcessCode.G901, TypeSapProcessCode.G014, TypeSapProcessCode.G103,
				TypeSapProcessCode.G102, TypeSapProcessCode.G904);
	}

}

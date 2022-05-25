package org.macula.cloud.po.alimq;

import java.util.Arrays;
import java.util.List;

import org.macula.cloud.mq.AliMQMessageListener;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.vo.PoStatusChange;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class UpdateOrderMessageListener implements AliMQMessageListener {

	private OMSConfig config;
	private OrderProcessingService orderProcessingService;
	private OrderProcessControlService processControlService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String messageId = (String) message.getUserProperties().get("UNIQ_KEY");
		String key = message.getKey();
		String tag = message.getTag();
		String messageBody = new String(message.getBody());
		try {
			log.info("== Received {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} ", getTopic(), messageId, key, tag, messageBody);

			if (!processControlService.shouldMQUpdateProcessing()) {
				log.error("== SHOULD NOT PROCESSING  OPERA MQ LISTENING ==");
				log.info("== Completed  {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} , ReconsumeLater!!!", getTopic(), messageId, key, tag,
						messageBody);
				return Action.ReconsumeLater;
			}
			List<PoStatusChange> listPoChangState = objectMapper.readValue(messageBody, new TypeReference<List<PoStatusChange>>() {
			});
			for (PoStatusChange poStatusChange : listPoChangState) {
				log.info("  === Updating PoStatusChange PoNo:{} -> {}", poStatusChange.getPoNo(), poStatusChange);
				// GBSS变更状态时，OMS同步更新状态
				String poNo = poStatusChange.getPoNo();
				poStatusChange.setMessageId(messageId);
				orderProcessingService.handlePoStatusChange(poNo, poStatusChange);
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
		return config.getStatusTopic();
	}

	@Override
	public List<String> getSubExpression() {
		return Arrays.asList("*");
	}

}

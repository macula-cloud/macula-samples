package org.macula.cloud.po.alimq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.macula.cloud.mq.AliMQMessageListener;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.util.OmsUtils;
import org.macula.cloud.po.vo.PoStatusChange;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class RefundOrderMessageListener implements AliMQMessageListener {

	private OMSConfig config;
	private OrderProcessingService orderProcessingService;
	private OrderProcessControlService processControlService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String messageId = (String) message.getUserProperties().get("UNIQ_KEY");
		String key = message.getKey();
		String tag = message.getTag();
		String messageBody = new String(message.getBody());
		try {
			log.info("== Received {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} ", getTopic(), messageId, key, tag, messageBody);

			if (!processControlService.shouldMQRefundProcessing()) {
				log.error("== SHOULD NOT PROCESSING  REFUND MQ LISTENING ==");
				log.info("== Completed  {} MQ Id -> {}  Key-> {} Tag -> {} Body -> {} , ReconsumeLater!!!", getTopic(), messageId, key, tag,
						messageBody);
				return Action.ReconsumeLater;
			}

			List<String> poNoList = new ArrayList<String>();
			poNoList.addAll(OmsUtils.getOrderNoList(key));
			poNoList.addAll(OmsUtils.getOrderNoList(messageBody));
			for (String poNo : poNoList) {
				log.info("  === Refunding PoNo: {} ", poNo);
				PoStatusChange poStatusChange = new PoStatusChange();
				poStatusChange.setPoNo(poNo);
				poStatusChange.setPoProcessCode(tag);
				poStatusChange.setMessageId(messageId);
				poStatusChange.setPoStatus("99");
				// GBSS变更状态时，OMS同步更新状态
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
		return config.getRefundTopic();
	}

	@Override
	public List<String> getSubExpression() {
		return Arrays.asList("*");
	}

}

package org.macula.cloud.po.listener;

import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.service.OrderProcessingService;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class OrderProcessingListener  extends  OrderProcessEventListener<OrderProcessRequestEvent> {

	private OrderProcessingService orderProcessingService;

	@Override
	public void doApplicationEvent(OrderProcessRequestEvent event) {
		String poNo = event.getSource();
		if (log.isInfoEnabled()) {
			log.info(" === Processing OrderProcessRequestEvent: {}", poNo);
		}
		orderProcessingService.doProcessingLogic(poNo);
	}

}

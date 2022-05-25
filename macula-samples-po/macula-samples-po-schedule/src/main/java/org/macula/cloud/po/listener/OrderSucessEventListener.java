package org.macula.cloud.po.listener;

import org.macula.cloud.po.event.OrderProcessSuccessEvent;
import org.macula.cloud.po.service.OrderCheckingService;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderSucessEventListener  extends  OrderProcessEventListener<OrderProcessSuccessEvent> {

	private OrderCheckingService orderCheckingService;

	@Override
	public void doApplicationEvent(OrderProcessSuccessEvent event) {
		orderCheckingService.updateCheckDetailAgain(event.getSource());
	}
}

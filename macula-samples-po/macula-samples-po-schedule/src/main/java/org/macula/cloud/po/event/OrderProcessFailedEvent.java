package org.macula.cloud.po.event;

import org.springframework.context.ApplicationEvent;

public class OrderProcessFailedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public OrderProcessFailedEvent(String poNo) {
		super(poNo);
	}

	@Override
	public String getSource() {
		return (String) super.getSource();
	}
}

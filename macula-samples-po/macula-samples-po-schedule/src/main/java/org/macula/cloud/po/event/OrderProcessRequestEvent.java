package org.macula.cloud.po.event;

import org.springframework.context.ApplicationEvent;

public class OrderProcessRequestEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public OrderProcessRequestEvent(String poNo) {
		super(poNo);
	}

	@Override
	public String getSource() {
		return (String) super.getSource();
	}

}

package org.macula.cloud.po.event;

import org.springframework.context.ApplicationEvent;

public class OrderProcessSuccessEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public OrderProcessSuccessEvent(String poNo) {
		super(poNo);
	}

	@Override
	public String getSource() {
		return (String) super.getSource();
	}

}

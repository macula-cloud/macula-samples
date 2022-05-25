package org.macula.cloud.po.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class OrderProcessEventListener<T extends ApplicationEvent> implements ApplicationListener<T> {

	@Override
	public void onApplicationEvent(T event) {
		try {
			doApplicationEvent(event);
		} catch (Throwable e) {
			log.error("onApplicationEvent {} error!", event, e);
		}
	}

	abstract protected void doApplicationEvent(T event);
}

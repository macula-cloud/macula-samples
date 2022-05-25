package org.macula.cloud.fulfilment.log.service;

import org.macula.cloud.fulfilment.event.InventoryCommitEvent;
import org.macula.cloud.fulfilment.event.InventoryReleaseEvent;
import org.macula.cloud.fulfilment.event.InventoryReserveEvent;
import org.macula.cloud.fulfilment.log.domain.StockEventLog;
import org.macula.cloud.fulfilment.log.repository.StockEventLogRepository;
import org.macula.cloud.fulfilment.log.stream.StockEventStreamChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StockEventConsumer {

	@Autowired
	private StockEventLogRepository repository;

	@StreamListener(value = StockEventStreamChannel.STOCK_RESERVE_INPUT)
	public void onStockEventListening(InventoryReserveEvent event) {
		log.info("STOCK-TOPIC-DEV  InventoryReserveEvent Message {}", JSONObject.toJSON(event));

		StockEventLog log = new StockEventLog();
		log.setId(event.getId());
		log.setValue(JSONObject.toJSONString(event));
		repository.save(log);
	}

	@StreamListener(value = StockEventStreamChannel.STOCK_COMMIT_INPUT)
	public void onInventoryCommitEvent(InventoryCommitEvent event) {
		log.info("STOCK-TOPIC-DEV  InventoryReserveEvent Message {}", JSONObject.toJSON(event));

		StockEventLog log = new StockEventLog();
		log.setId(event.getId());
		log.setValue(JSONObject.toJSONString(event));
		repository.save(log);
	}

	@StreamListener(value = StockEventStreamChannel.STOCK_RELEASE_INPUT)
	public void onInventoryReleaseEvent(InventoryReleaseEvent event) {
		log.info("STOCK-TOPIC-DEV  InventoryReserveEvent Message {}", JSONObject.toJSON(event));

		StockEventLog log = new StockEventLog();
		log.setId(event.getId());
		log.setValue(JSONObject.toJSONString(event));
		repository.save(log);
	}
}

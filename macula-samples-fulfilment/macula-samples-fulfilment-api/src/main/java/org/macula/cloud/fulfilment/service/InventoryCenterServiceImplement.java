package org.macula.cloud.fulfilment.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.macula.cloud.fulfilment.event.InventoryReserveEvent;
import org.macula.cloud.fulfilment.stream.StockEventStreamChannel;
import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryCenterServiceImplement implements InventoryCenterService {

	private static final String LOCK_PREFIX = "LOCK:";

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private StockEventStreamChannel channel;

	@Override
	public InventoryResponse status(InventoryRequest inventoryRequest) {
		if (CollectionUtils.isNotEmpty(inventoryRequest.getDetails())) {
			inventoryRequest.getDetails().forEach(o -> {
				String key = o.getRedisKey(inventoryRequest.getAccDate());
				o.setStatus((String) redisTemplate.opsForHash().get(key, "balanceQty"));
			});
		}
		return InventoryResponse.success(inventoryRequest);
	}

	@Override
	public InventoryResponse reserve(InventoryRequest inventoryRequest) {
		InventoryResponse response = null;
		InventoryReserveEvent event = new InventoryReserveEvent();
		event.setRequest(inventoryRequest);
		try {
			SortedMap<String, BigDecimal> productMaps = inventoryRequest.getSortedProductQuantities();
			List<RLock> locks = new ArrayList<RLock>();
			productMaps.keySet().forEach(o -> {
				locks.add(redissonClient.getLock(LOCK_PREFIX + o));
			});

			RLock multiLock = redissonClient.getMultiLock(locks.toArray(new RLock[0]));
			if (log.isInfoEnabled()) {
				log.info("Prepare required lock ... {} ", locks.size());
				log.info("Request: {}", JSON.toJSONString(inventoryRequest));
			}
			boolean validated = true;
			List<String> messages = new ArrayList<String>();
			multiLock.lock();
			if (log.isInfoEnabled()) {
				log.info("Get required lock ...");
			}
			try {
				// check stock
				for (Entry<String, BigDecimal> entry : productMaps.entrySet()) {
					String k = entry.getKey();
					BigDecimal v = entry.getValue();
					String balanceQty = (String) redisTemplate.opsForHash().get(k, "balanceQty");
					if (balanceQty == null || Long.parseLong(balanceQty) < v.longValue()) {
						validated = false;
						messages.add(String.format("%s balanceQty less than %d", k, v.longValue()));
						break;
					}
				}

				// do stock reduce
				productMaps.forEach((k, v) -> {
					redisTemplate.opsForHash().increment(k, "balanceQty", -v.intValue());
					redisTemplate.opsForHash().increment(k, "reservedQty", v.intValue());
				});

			} catch (Exception ex) {
				if (log.isErrorEnabled()) {
					log.info("Request error: {} - {}", JSON.toJSONString(inventoryRequest), ex);
				}
				validated = false;
				messages.add(ex.getMessage());
			} finally {
				multiLock.unlock();
				if (log.isInfoEnabled()) {
					log.info("Release lock ...");
				}
			}
			response = validated ? InventoryResponse.success(inventoryRequest)
					: InventoryResponse.failed("-1", StringUtils.join(messages), inventoryRequest);
		} finally {
			if (response != null) {
				event.setResponse(response);
			}
			event.markPublished();
			channel.stockTopic().send(MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.TAGS, event.getClazz()).build());
		}
		return response;
	}

	@Override
	public InventoryResponse commit(InventoryRequest inventoryRequest) {
		return null;
	}

	@Override
	public InventoryResponse release(InventoryRequest inventoryReauest) {
		return null;
	}

}

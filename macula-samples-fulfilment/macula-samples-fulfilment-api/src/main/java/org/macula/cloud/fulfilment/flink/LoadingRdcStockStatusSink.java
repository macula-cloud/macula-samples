package org.macula.cloud.fulfilment.flink;

import java.util.Map;

import org.apache.flink.shaded.guava18.com.google.common.collect.ImmutableMap;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.macula.cloud.fulfilment.domain.RdcStockStatus;
import org.macula.cloud.fulfilment.repository.RdcStockStatusRepository;
import org.macula.cloud.fulfilment.util.InventoryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadingRdcStockStatusSink extends RichSinkFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	private static RdcStockStatusRepository rdcStockStatusRepository;

	private static StringRedisTemplate redisTemplate;

	private static long start = Long.MAX_VALUE;

	private static long end = Long.MIN_VALUE;

	private static transient long count = 0;

	public static void initializeBeans(RdcStockStatusRepository repository, StringRedisTemplate template) {
		if (rdcStockStatusRepository == null && repository != null) {
			rdcStockStatusRepository = repository;
		}
		if (redisTemplate == null && template != null) {
			redisTemplate = template;
		}
	}

	@Override
	public void invoke(PageRequest pageRequest, @SuppressWarnings("rawtypes") Context context) throws Exception {

		Assert.notNull(rdcStockStatusRepository, "Please initial rdcInventoryStatusDao first!");
		Assert.notNull(redisTemplate, "Please initial redisTemplate first!");

		log.info("Processing PageRequest {}", pageRequest);

		long now = System.currentTimeMillis();
		if (now < start) {
			start = now;
		}

		Page<RdcStockStatus> stockStatusList = rdcStockStatusRepository.findAll(pageRequest);

		count += stockStatusList.getNumberOfElements();

		for (RdcStockStatus rdcStockStatus : stockStatusList) {
			String key = InventoryUtils.getStockRedisKey(rdcStockStatus.getAccDate(), rdcStockStatus.getProductCode(), rdcStockStatus.getWhCode(),
					rdcStockStatus.getWhLocCode());
			Map<String, Integer> stockInfo = ImmutableMap.<String, Integer>builder()
					.put("lastBalanceQty", rdcStockStatus.getLastBalanceQty().intValue()).put("balanceQty", rdcStockStatus.getBalanceQty().intValue())
					.put("thisPoQty", rdcStockStatus.getThisPoQty().intValue()).build();
			redisTemplate.opsForHash().putAll(key, stockInfo);
		}

		now = System.currentTimeMillis();
		if (now > end) {
			end = now;
			long used = end - start;
			if (used > 0) {
				log.info("Complete stock to Redis, total: {}, used: {}, tps: {}", count, used, count * 1000 / used);
			}
		}
	}

}

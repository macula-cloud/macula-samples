package org.macula.cloud.fulfilment.service;

import javax.annotation.PostConstruct;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.macula.cloud.fulfilment.flink.LoadingRdcStockStatusSink;
import org.macula.cloud.fulfilment.flink.LoadingRdcStockStatusSource;
import org.macula.cloud.fulfilment.repository.RdcStockStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisStockLoadingService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private RdcStockStatusRepository stockStatusRepository;

	@PostConstruct
	public void initialFlinkBeans() {
		LoadingRdcStockStatusSink.initializeBeans(stockStatusRepository, redisTemplate);
		LoadingRdcStockStatusSource.initializeBeans(stockStatusRepository);
	}

	public void mysql2Redis() {
		log.info("Start read stock to Redis ...");

		LoadingRdcStockStatusSource source = new LoadingRdcStockStatusSource();
		LoadingRdcStockStatusSink sink = new LoadingRdcStockStatusSink();

		StreamExecutionEnvironment execution = StreamExecutionEnvironment.getExecutionEnvironment();
		execution.enableCheckpointing(5000);
		execution.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		execution.setMaxParallelism(32);
		execution.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
		DataStream<PageRequest> dataStream = execution.addSource(source, TypeInformation.of(PageRequest.class));
		dataStream.addSink(sink);
		try {
			execution.execute("MySQL2Redis");
		} catch (Exception e) {
			log.error("Schedule read stock to Redis error, ", e);
		}

		log.info("Schedule read stock to Redis ...");
	}

}

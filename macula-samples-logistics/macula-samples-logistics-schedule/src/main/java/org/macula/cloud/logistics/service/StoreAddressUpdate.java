package org.macula.cloud.logistics.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionParseRequest;
import org.macula.cloud.cainiao.DivisionParseResponse;
import org.macula.cloud.logistics.domain.StoreAddress;
import org.macula.cloud.logistics.flink.StoreAddressPageSink;
import org.macula.cloud.logistics.flink.StoreAddressPageSource;
import org.macula.cloud.logistics.repository.StoreAddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StoreAddressUpdate {

	@Autowired
	private StoreAddressRepository addressRepository;

	@Autowired
	private CainiaoLinkService service;

	@PostConstruct
	public void initialFlinkBeans() {

	}

	public void updateAllStoreAddress() {

		StoreAddressPageSource.initializeBeans(addressRepository);
		StoreAddressPageSink.initializeBeans(addressRepository, service);

		log.info("Start updateStoreAddress ...");

		StoreAddressPageSource source = new StoreAddressPageSource();
		StoreAddressPageSink sink = new StoreAddressPageSink();

		StreamExecutionEnvironment execution = StreamExecutionEnvironment.getExecutionEnvironment();
		execution.enableCheckpointing(5000);
		execution.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		execution.setMaxParallelism(32);
		execution.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
		DataStream<PageRequest> dataStream = execution.addSource(source, TypeInformation.of(PageRequest.class));
		dataStream.addSink(sink);
		try {
			execution.execute("updateStoreAddress");
		} catch (Exception e) {
			log.error("Schedule  updateStoreAddress  error, ", e);
		}

		log.info("Schedule updateStoreAddress ...");
	}

	public void updateUnloadMappingStoreAddress() {
		Pageable pageRequest = PageRequest.of(0, 1000);
		Page<StoreAddress> addressPage = addressRepository.findUnloadMappings(pageRequest);

		for (StoreAddress address : addressPage) {
			if (StringUtils.isNotEmpty(address.getProvId()) && "0".equals(address.getStatus())) {
				continue;
			}
			DivisionParseResponse response = service.getDivisionParse(DivisionParseRequest.of(address.getAddress(), "LATEST"));
			if (response.isSuccess() && response.getParseDivisionResult() != null) {
				BeanUtils.copyProperties(response.getParseDivisionResult(), address);
				address.setStatus("0");
				addressRepository.save(address);
			} else {
				address.setStatus("-1");
				addressRepository.save(address);
				log.error("Response error: {} ", response);
			}
		}

		if (addressPage.hasNext()) {
			updateUnloadMappingStoreAddress();
		}
	}

}

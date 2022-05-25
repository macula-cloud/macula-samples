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
import org.macula.cloud.logistics.domain.HomeAddress;
import org.macula.cloud.logistics.flink.HomeAddressPageSink;
import org.macula.cloud.logistics.flink.HomeAddressPageSource;
import org.macula.cloud.logistics.repository.HomeAddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HomeAddressUpdate {

	@Autowired
	private HomeAddressRepository addressRepository;

	@Autowired
	private CainiaoLinkService service;

	@PostConstruct
	public void initialFlinkBeans() {

	}

	public void updateAllHomeAddress() {

		HomeAddressPageSource.initializeBeans(addressRepository);
		HomeAddressPageSink.initializeBeans(addressRepository, service);

		log.info("Start updateAllHomeAddress ...");

		HomeAddressPageSource source = new HomeAddressPageSource();
		HomeAddressPageSink sink = new HomeAddressPageSink();

		StreamExecutionEnvironment execution = StreamExecutionEnvironment.getExecutionEnvironment();
		execution.enableCheckpointing(5000);
		execution.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		execution.setMaxParallelism(100);
		execution.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
		DataStream<PageRequest> dataStream = execution.addSource(source, TypeInformation.of(PageRequest.class));
		dataStream.addSink(sink);
		try {
			execution.execute("updateAllHomeAddress");
		} catch (Exception e) {
			log.error("Schedule  updateAllHomeAddress  error, ", e);
		}

		log.info("Schedule updateAllHomeAddress ...");
	}

	public void updateUnloadMappingHomeAddress() {
		Pageable pageRequest = PageRequest.of(0, 1000);
		Page<HomeAddress> addressPage = addressRepository.findUnloadMappings(pageRequest);

		for (HomeAddress address : addressPage) {
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
			updateUnloadMappingHomeAddress();
		}
	}

}

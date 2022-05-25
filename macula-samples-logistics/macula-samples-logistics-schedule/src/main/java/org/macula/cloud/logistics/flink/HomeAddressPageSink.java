package org.macula.cloud.logistics.flink;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionParseRequest;
import org.macula.cloud.cainiao.DivisionParseResponse;
import org.macula.cloud.logistics.domain.HomeAddress;
import org.macula.cloud.logistics.repository.HomeAddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomeAddressPageSink extends RichSinkFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	public static HomeAddressRepository homeAddressRepository;
	public static CainiaoLinkService linkService;

	private static long start = Long.MAX_VALUE;

	private static long end = Long.MIN_VALUE;

	private static transient long count = 0;

	public static void initializeBeans(HomeAddressRepository repository, CainiaoLinkService service) {
		if (homeAddressRepository == null && repository != null) {
			homeAddressRepository = repository;
		}
		if (linkService == null && service != null) {
			linkService = service;
		}
	}

	@Override
	public void invoke(PageRequest pageRequest, @SuppressWarnings("rawtypes") Context context) throws Exception {
		Assert.notNull(homeAddressRepository, "Please initial homeAddressRepository first!");
		log.info("Processing PageRequest {}", pageRequest);
		long now = System.currentTimeMillis();
		if (now < start) {
			start = now;
		}
		Page<HomeAddress> addressPage = homeAddressRepository.findAll(pageRequest);

		count += addressPage.getNumberOfElements();

		for (HomeAddress address : addressPage) {
			if (StringUtils.isNotEmpty(address.getProvId()) && "0".equals(address.getStatus())) {
				continue;
			}
			DivisionParseResponse response = linkService.getDivisionParse(DivisionParseRequest.of(address.getAddress(), "LATEST"));
			if (response.isSuccess() && response.getParseDivisionResult() != null) {
				BeanUtils.copyProperties(response.getParseDivisionResult(), address);
				address.setStatus("0");
				homeAddressRepository.save(address);
			} else {
				address.setStatus("-1");
				homeAddressRepository.save(address);
				log.error("Response error: {} ", response);
			}
		}

		now = System.currentTimeMillis();
		if (now > end) {
			end = now;
			long used = end - start;
			if (used > 0) {
				log.info("Check gbss store address to cainiao, total: {}, used: {}, tps: {}", count, used, count * 1000 / used);
			}
		}
	}

}

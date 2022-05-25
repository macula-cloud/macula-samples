package org.macula.cloud.logistics.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionParseRequest;
import org.macula.cloud.cainiao.DivisionParseResponse;
import org.macula.cloud.logistics.domain.Gbss2Cainiao;
import org.macula.cloud.logistics.repository.Gbss2CainiaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Gbss2CainiaoUpdate {

	@Autowired
	private Gbss2CainiaoRepository addressRepository;

	@Autowired
	private CainiaoLinkService service;

	public void updateGbss2CainiaoMapping() {
		String updateBatchNo = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");
		updateMappingInternal(updateBatchNo);
	}

	public void updateMappingInternal(String batchNo) {
		Pageable pageRequest = PageRequest.of(0, 1000);

		Page<Gbss2Cainiao> addressPage = addressRepository.findUnloadMappings(pageRequest, batchNo);

		for (Gbss2Cainiao address : addressPage) {
			if (StringUtils.isNotEmpty(address.getProvId()) || StringUtils.isEmpty(address.getAddress())) {
				continue;
			}
			log.info("Request address {} ", address.getAddress());
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
			updateMappingInternal(batchNo);
		}
	}
}

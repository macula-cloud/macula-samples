package org.macula.cloud.logistics.service;

import org.macula.cloud.logistics.vo.AddressResponse;
import org.macula.cloud.logistics.vo.LogisticsRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressCenterServiceImplement implements AddressCenterService {

	@Override
	public AddressResponse getChildren(LogisticsRequest request) {
		AddressResponse response = new AddressResponse();
		response.setRequset(request);
		log.info("getChildren Response: {}", response);
		return response;
	}

	@Override
	public AddressResponse getAddress(LogisticsRequest request) {
		AddressResponse response = new AddressResponse();
		response.setRequset(request);
		log.info("getAddress Response: {}", response);
		return response;
	}

	@Override
	public AddressResponse getHomeRDCMapping(LogisticsRequest request) {
		AddressResponse response = new AddressResponse();
		response.setRequset(request);
		log.info("getHomeRDCMapping Response: {}", response);
		return response;
	}

	@Override
	public AddressResponse getStoreRDCMapping(LogisticsRequest request) {
		AddressResponse response = new AddressResponse();
		response.setRequset(request);
		log.info("getStoreRDCMapping Response: {}", response);
		return response;
	}

}

package org.macula.cloud.logistics.service;

import org.apache.dubbo.config.annotation.Reference;
import org.macula.cloud.logistics.vo.Division2RDCRequest;
import org.macula.cloud.logistics.vo.Division2RDCResponse;
import org.springframework.stereotype.Service;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class LogisticsClientService {

	@Reference
	private LogisticsCenterService logisticsCenterService;

	@GlobalTransactional
	public Division2RDCResponse getDivision2RDC(String divisionId) {
		Division2RDCRequest request = new Division2RDCRequest();
		request.setDivisionId(divisionId);
		Division2RDCResponse response = logisticsCenterService.getDivision2RDCMapping(request);
		return response;
	}
}

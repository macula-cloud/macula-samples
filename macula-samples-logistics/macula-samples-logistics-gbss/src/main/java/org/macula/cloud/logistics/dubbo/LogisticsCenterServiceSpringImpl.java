package org.macula.cloud.logistics.dubbo;

import org.apache.dubbo.config.annotation.Service;
import org.macula.cloud.logistics.service.LogisticsCenterService;
import org.macula.cloud.logistics.vo.AddressRequest;
import org.macula.cloud.logistics.vo.AddressResponse;
import org.macula.cloud.logistics.vo.Division2RDCRequest;
import org.macula.cloud.logistics.vo.Division2RDCResponse;
import org.macula.cloud.logistics.vo.Gbss2CainiaoRequest;
import org.macula.cloud.logistics.vo.Gbss2CainiaoResponse;

@Service
public class LogisticsCenterServiceSpringImpl implements LogisticsCenterService {

	@Override
	public AddressResponse getChildrenDivisions(AddressRequest request) {
		AddressResponse response = new AddressResponse();
		response.setRequset(request);
		return response;
	}

	@Override
	public Gbss2CainiaoResponse getGbss2CainiaoMapping(Gbss2CainiaoRequest request) {
		Gbss2CainiaoResponse response = new Gbss2CainiaoResponse();
		response.setRequset(request);
		return response;
	}

	@Override
	public Division2RDCResponse getDivision2RDCMapping(Division2RDCRequest request) {
		Division2RDCResponse response = new Division2RDCResponse();
		response.setRequset(request);
		return response;
	}

}

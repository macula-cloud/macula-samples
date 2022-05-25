package org.macula.cloud.po.feign;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.po.exception.OMSException;
import org.springframework.http.ResponseEntity;

import feign.hystrix.FallbackFactory;

public class OrderScheduleFeignClientFallbackFactory implements FallbackFactory<OrderScheduleFeignClient> {

	@Override
	public OrderScheduleFeignClient create(Throwable cause) {
		return new OrderScheduleFeignClient() {

			@Override
			public ResponseEntity<PoProcessMaster> handleOrderUpload(String poNo) {
				throw new OMSException("HandleOrderUpload error: " + poNo, cause);
			}

			@Override
			public ResponseEntity<PoCheckMaster> afreshChecking(Long id) {
				throw new OMSException("HandleOrderUpload error: " + id, cause);
			}

			@Override
			public ResponseEntity<PoCheckDetail> successByHand(String gbssPoNo, Long checkMasterId) {
				throw new OMSException("HandleOrderUpload error: " + gbssPoNo + "checkMasterId:" + checkMasterId, cause);
			}
		};
	}

}

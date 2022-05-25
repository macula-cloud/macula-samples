package org.macula.cloud.po.feign;

import java.util.List;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GbssOrderServiceClientFallbackFactory implements FallbackFactory<GbssOrderServiceClient> {

	@Override
	public GbssOrderServiceClient create(Throwable cause) {
		log.error("Call GbssPurchaseOrderServiceClient error:", cause);
		return new GbssOrderServiceClient() {

			@Override
			public Object getPurchaseOrder(String poNo) {
				throw new OMSException("Get pono error: " + poNo);
			}

			/**
			 * 根据poNo从GBSS查询所有相关联的数据
			 * @param poNo
			 * @return
			 */
			@Override
			public PoResultDetailVo getResultDetailVo(String poNo) {
				throw new OMSException("Get pono error: " + poNo);
			}

			@Override
			public List<PoCheckDetail> takeGBSSBillingData(String startTime, String endTime) {
				throw new OMSException("Get pono error: " + startTime + ";" + endTime);
			}

			@Override
			public ResponseEntity<PoResultDetailVo> getPoResultDetailVo(String poNo) {
				throw new OMSException("Get pono error: " + poNo);
			}
		};
	}

}

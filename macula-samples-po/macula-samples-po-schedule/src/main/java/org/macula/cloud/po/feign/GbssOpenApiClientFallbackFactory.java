package org.macula.cloud.po.feign;

import java.util.List;

import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GbssOpenApiClientFallbackFactory implements FallbackFactory<GbssOpenApiClient> {

	@Override
	public GbssOpenApiClient create(Throwable cause) {
		log.error("Call GbssOpenApiClient error:", cause);
		return new GbssOpenApiClient() {

			@Override
			public ExecuteResponse<PoResultDetailVo> getGbssOrderResultDetail(String orderNo) {
				throw new OMSException("Get orderNo error: " + orderNo);
			}

			@Override
			public ExecuteResponse<List<PoCheckDetail>> getGbssBillingData(String startTime, String endTime) {
				throw new OMSException("Get orderNo error: " + startTime + ":" + endTime);
			}

			@Override
			public ExecuteResponse<Page<SapDailyUplPo>> getGbssUploadDataList(String v1, long startId, int pageNumber, int pageSize) {
				throw new OMSException("Get uploadDataList error: " + v1 + ":" + startId + ":" + pageNumber + ":" + pageSize);
			}

			@Override
			public ExecuteResponse<Page<SapDailyUplPoV2>> getGbssUpload2DataList(String v2, long startId, int pageNumber, int pageSize) {
				throw new OMSException("Get upload2DataList error: " + v2 + ":" + startId + ":" + pageNumber + ":" + pageSize);
			}
		};
	}
}
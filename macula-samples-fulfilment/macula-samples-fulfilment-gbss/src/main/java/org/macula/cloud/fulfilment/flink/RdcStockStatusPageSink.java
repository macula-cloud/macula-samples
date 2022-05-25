package org.macula.cloud.fulfilment.flink;

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.macula.cloud.fulfilment.feign.InventoryCenterClient;
import org.macula.cloud.fulfilment.gbss.domain.RdcStockStatus;
import org.macula.cloud.fulfilment.gbss.repository.RdcStockStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RdcStockStatusPageSink extends RichSinkFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	public static RdcStockStatusRepository rdcStockStatusRepository;
	public static InventoryCenterClient stockClient;

	private static long start = Long.MAX_VALUE;

	private static long end = Long.MIN_VALUE;

	private static transient long count = 0;

	public static void initializeBeans(RdcStockStatusRepository repository, InventoryCenterClient client) {
		if (rdcStockStatusRepository == null && repository != null) {
			rdcStockStatusRepository = repository;
		}
		if (stockClient == null && client != null) {
			stockClient = client;
		}
	}

	@Override
	public void invoke(PageRequest pageRequest, @SuppressWarnings("rawtypes") Context context) throws Exception {
		Assert.notNull(rdcStockStatusRepository, "Please initial rdcStockStatusRepository first!");
		log.info("Processing PageRequest {}", pageRequest);
		long now = System.currentTimeMillis();
		if (now < start) {
			start = now;
		}
		Page<RdcStockStatus> stockStatusList = rdcStockStatusRepository.findAll(pageRequest);

		count += stockStatusList.getNumberOfElements();

		for (RdcStockStatus status : stockStatusList) {
			stockClient.insertRdcStockStatus(status);
		}

		now = System.currentTimeMillis();
		if (now > end) {
			end = now;
			long used = end - start;
			if (used > 0) {
				log.info("Send gbss RdcStockStatus to inventory, total: {}, used: {}, tps: {}", count, used, count * 1000 / used);
			}
		}
	}

}

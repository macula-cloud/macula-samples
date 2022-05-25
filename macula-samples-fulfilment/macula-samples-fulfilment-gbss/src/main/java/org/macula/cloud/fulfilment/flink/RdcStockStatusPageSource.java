package org.macula.cloud.fulfilment.flink;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.macula.cloud.fulfilment.gbss.repository.RdcStockStatusRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

public class RdcStockStatusPageSource extends RichSourceFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	private static RdcStockStatusRepository rdcStockStatusRepository;

	private static int size = 100;

	public static void initializeBeans(RdcStockStatusRepository repository) {
		if (rdcStockStatusRepository == null && repository != null) {
			rdcStockStatusRepository = repository;
		}
	}

	@Override
	public void run(SourceContext<PageRequest> ctx) throws Exception {
		Assert.notNull(rdcStockStatusRepository, "Please initial rdcStockStatusRepository first!");

		long totalCount = rdcStockStatusRepository.count();
		long pageCount = (totalCount / size) + (totalCount % size != 0 ? 1 : 0);
		for (int i = 0; i < pageCount; i++) {
			ctx.collect(PageRequest.of(i, size));
		}
	}

	@Override
	public void cancel() {

	}

}

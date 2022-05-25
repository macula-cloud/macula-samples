package org.macula.cloud.logistics.flink;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.macula.cloud.logistics.repository.StoreAddressRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

public class StoreAddressPageSource extends RichSourceFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	private static StoreAddressRepository storeAddressRepository;

	private static int size = 100;

	public static void initializeBeans(StoreAddressRepository repository) {
		if (storeAddressRepository == null && repository != null) {
			storeAddressRepository = repository;
		}
	}

	@Override
	public void run(SourceContext<PageRequest> ctx) throws Exception {
		Assert.notNull(storeAddressRepository, "Please initial storeAddressRepository first!");

		long pageCount = storeAddressRepository.count();
		for (int i = 0; i < pageCount; i++) {
			ctx.collect(PageRequest.of(i, size));
		}
	}

	@Override
	public void cancel() {

	}

}

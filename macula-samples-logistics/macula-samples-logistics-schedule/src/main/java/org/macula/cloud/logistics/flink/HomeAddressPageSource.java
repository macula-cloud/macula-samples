package org.macula.cloud.logistics.flink;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.macula.cloud.logistics.repository.HomeAddressRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

public class HomeAddressPageSource extends RichSourceFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	private static HomeAddressRepository homeAddressRepository;

	private static int size = 100;

	public static void initializeBeans(HomeAddressRepository repository) {
		if (homeAddressRepository == null && repository != null) {
			homeAddressRepository = repository;
		}
	}

	@Override
	public void run(SourceContext<PageRequest> ctx) throws Exception {
		Assert.notNull(homeAddressRepository, "Please initial homeAddressRepository first!");

		long pageCount = homeAddressRepository.count();
		for (int i = 0; i < pageCount; i++) {
			ctx.collect(PageRequest.of(i, size));
		}
	}

	@Override
	public void cancel() {

	}

}

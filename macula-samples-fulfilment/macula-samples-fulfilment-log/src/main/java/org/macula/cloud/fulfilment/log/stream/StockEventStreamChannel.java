package org.macula.cloud.fulfilment.log.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StockEventStreamChannel {

	final String STOCK_RESERVE_INPUT = "stock-reserve-event-input";

	final String STOCK_COMMIT_INPUT = "stock-commit-event-input";

	final String STOCK_RELEASE_INPUT = "stock-release-event-input";

	@Input(StockEventStreamChannel.STOCK_RESERVE_INPUT)
	SubscribableChannel stockReserveChannel();

	@Input(StockEventStreamChannel.STOCK_COMMIT_INPUT)
	SubscribableChannel stockCommitChannel();

	@Input(StockEventStreamChannel.STOCK_RELEASE_INPUT)
	SubscribableChannel stockReleaseChannel();
}

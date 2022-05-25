package org.macula.cloud.fulfilment.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StockEventStreamChannel {

	final String OUTPUT = "output";

	@Output(StockEventStreamChannel.OUTPUT)
	MessageChannel stockTopic();
}

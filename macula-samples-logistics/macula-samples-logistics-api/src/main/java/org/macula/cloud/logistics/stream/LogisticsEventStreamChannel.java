package org.macula.cloud.logistics.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface LogisticsEventStreamChannel {

	final String OUTPUT = "output";

	@Output(LogisticsEventStreamChannel.OUTPUT)
	MessageChannel stockTopic();
}

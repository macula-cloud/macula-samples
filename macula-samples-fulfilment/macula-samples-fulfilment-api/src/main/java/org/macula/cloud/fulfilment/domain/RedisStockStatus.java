package org.macula.cloud.fulfilment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class RedisStockStatus {

	private String redisKey;

	private Long initialQty;

	private Long balanceQty;

	private Long reservedQty;

	private Long usedQty;
}

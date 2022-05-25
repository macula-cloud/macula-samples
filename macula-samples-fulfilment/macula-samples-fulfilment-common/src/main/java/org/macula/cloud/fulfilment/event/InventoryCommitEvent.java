package org.macula.cloud.fulfilment.event;

/**
 * 订单库存确认事件，该事件代表确认成功。
 * 订单库存确认的前提是：有与之匹配的锁定数据。
 */
public class InventoryCommitEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

}

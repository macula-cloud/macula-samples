package org.macula.cloud.logistics.event;

/**
 * 订单库存释放事件，该事件代表释放成功。
 * 订单库存释放的前提是：有与之匹配的锁定数据。
 */
public class InventoryReleaseEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

}

package org.macula.cloud.fulfilment.service;

import org.macula.cloud.fulfilment.vo.InventoryRequest;
import org.macula.cloud.fulfilment.vo.InventoryResponse;

public interface InventoryCenterService {

	/**
	 * 请求库存，请求成功将会锁定对应的请求明细。步骤如下：
	 * 1、使用document+version判断是否有调用记录，如有则为重复调用，返回错误；
	 * 2、根据请求获取销售片区；
	 * 3、请求ProductCode排序，执行批量锁定；
	 * 4、判断所有请求库存是否满足，不满足返回错误；
	 * 5、如满足库存要求，执行锁定；
	 * 6、抛出库存锁定事件（扔给MQ去处理）
	 *
	 * @param inventoryRequest 库存锁定请求信息
	 * @return 库存锁定结果
	 */
	InventoryResponse reserve(InventoryRequest inventoryRequest);

	/**
	 * 确认锁定库存，如请求中没有明细，则表示确认全部。步骤如下：
	 * 1、使用document+version判断是否有锁定记录；
	 * 2、如无锁定记录，返回错误；
	 * 3、如找到锁定记录，将库存锁定转为实际消耗；
	 * 4、更新库存数据；
	 * 5、抛出库存确认事件（扔给MQ去处理）
	 *
	 * @param inventoryRequest 库存确认请求信息，如请求信息中没有明细，则表示确认全部。
	 * @return 库存确认结果
	 */
	InventoryResponse commit(InventoryRequest inventoryRequest);

	/**
	 * 释放锁定库存，如请求中没有明细，则表示释放全部。步骤如下：
	 * 1、使用document+version判断是否有锁定记录；
	 * 2、如无锁定记录，返回错误；
	 * 3、如找到锁定记录，将库存锁定释放；
	 * 4、更新库存数据；
	 * 5、抛出库存库存释放事件（扔给MQ去处理）
	 *
	 * @param inventoryRequest 库存释放请求信息，如请求信息中没有明细，则表示释放全部。
	 * @return 库存释放结果
	 */
	InventoryResponse release(InventoryRequest inventoryReauest);

	/**
	 * 获取库存状态
	 */
	InventoryResponse status(InventoryRequest request);

}

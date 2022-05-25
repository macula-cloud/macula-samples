package org.macula.cloud.logistics.service;

import org.macula.cloud.logistics.vo.AddressResponse;
import org.macula.cloud.logistics.vo.LogisticsRequest;

/**
 * 地址中心服务，提供行政四级地址，以及地址对于的RDC仓库
 */
public interface AddressCenterService {

	/**
	 * 请求下级地址服务
	 * @param addressRequest
	 * @return 下级地址信息
	 */
	AddressResponse getChildren(LogisticsRequest addressRequest);

	/**
	 * 获取Code对应的地址信息
	 * @param request
	 * @return Code对应的地址信息
	 */
	AddressResponse getAddress(LogisticsRequest request);

	/**
	 * 获取Code对应的家居配送RDC映射
	 * @param request
	 * @return Code对应的家居配送三级RDC信息
	 */
	AddressResponse getHomeRDCMapping(LogisticsRequest request);

	/**
	 * 获取Code对应的店铺配送RDC映射
	 * @param request
	 * @return Code对应的店铺配送三级RDC信息
	 */
	AddressResponse getStoreRDCMapping(LogisticsRequest request);
}

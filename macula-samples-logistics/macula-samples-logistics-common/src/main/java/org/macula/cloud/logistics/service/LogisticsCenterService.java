package org.macula.cloud.logistics.service;

import org.macula.cloud.logistics.vo.AddressRequest;
import org.macula.cloud.logistics.vo.AddressResponse;
import org.macula.cloud.logistics.vo.Division2RDCRequest;
import org.macula.cloud.logistics.vo.Division2RDCResponse;
import org.macula.cloud.logistics.vo.Gbss2CainiaoRequest;
import org.macula.cloud.logistics.vo.Gbss2CainiaoResponse;

/**
 * 物流中心接口
 */
public interface LogisticsCenterService {

	/**
	 * 查询子节点地址信息
	 * @param request 地址请求信息
	 * @return 返回子节点地址信息
	 */
	AddressResponse getChildrenDivisions(AddressRequest request);

	/**
	 * 通过现有的GBSS AreaCode转化为菜鸟地址代码
	 * @param request GBSS AreaCode 数据
	 * @return 菜鸟地址数据
	 */
	Gbss2CainiaoResponse getGbss2CainiaoMapping(Gbss2CainiaoRequest request);

	/**
	 * 
	 * 通过菜鸟地址divisionId获取对应的三级货仓配置
	 * @param request 包括divisionId 菜鸟地址,accDate 有效时点
	 * @return 返回映射的三级RDC信息
	 */
	Division2RDCResponse getDivision2RDCMapping(Division2RDCRequest request);
}

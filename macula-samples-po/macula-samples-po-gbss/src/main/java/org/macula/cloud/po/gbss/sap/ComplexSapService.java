/**
 * 
 */
package org.macula.cloud.po.gbss.sap;

/**
 * <p>
 * <b>ComplexSapService</b> 是SAP调用接口
 * </p>
 * 
 
 
 
 * 
 */

public interface ComplexSapService {

	/**
	 * 新版触发发货用到的，创建销售订单
	 * @param master
	 * @param procType
	 * @return
	 */
	public PlantResult saveDealerOrderSheetNew(DealerOrderVo dealerOrderVo);

}

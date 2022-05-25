/**
 * 
 */
package org.macula.cloud.po.gbss.service;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.gbss.sap.DealerOrderVo;

/**
 * <p>
 * <b>SapBasicActionService</b> sap接口任务调度统一接口
 * </p>
 * 
 
 
 
 * 
 */

public interface SaveDealerOrderThreadHelpService {

	/**
	 * 非服务中心订单上传执行类
	 * 
	 * @param sapDailyUplPo
	 */
	//	public void action(SapDailyUplPo sapDailyUplPo) throws Exception;

	/**
	 * 上传非服务中心单据 （线程调用的接口方法）
	 * @param List<SapDailyUplPo> sapDailyUplPoList 待上传单据集合
	 * @throws Exception
	 */
	//	public void uploadPoOrder(List<SapDailyUplPo> sapDailyUplPoList) throws Exception;

	public DealerOrderVo prepareDataNotPos(SapDailyUplPo sapDailyUplPo);

	//	/**
	//	 * 根据poNo:获取推送给SAP组合数据的所有相关表数据
	//	 * @param poNo
	//	 * @return
	//	 */
	//	PoResultDetailVo getResultDetailVo(String poNo);
	//
	//	PoResultDetailVo getPoDetailVo(String poNo);
	//
	//	/**
	//	 * 方法说明:查询OMS需要的对账数据
	//	 * @param startTime:开始:销售单据输入时间
	//	 * @param endTime:结束:销售单据输入时间
	//	 * @throws Exception
	//	 */
	//	List<PoCheckDetail> takeGBSSBillingData(String startTime, String endTime) throws ParseException;
}

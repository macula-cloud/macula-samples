package org.macula.cloud.po.service;

import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.po.sap.bapi.DealerOrderZrebBapi;
import org.macula.cloud.po.vo.OrderUploadMessage;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.macula.cloud.po.vo.PoStatusChange;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.macula.cloud.po.vo.StatusChangeRequest;

public interface OrderProcessingService {

	/**
	 * 获取PoProcessMaster日志
	 * @param poNo
	 * @return
	 */
	PoProcessMaster getProcessMaster(String poNo);

	/**
	 *  0.记录处理请求
	 * @param poNo
	 * @param request  
	 * @return
	 */
	Boolean handleStartRequest(String poNo, StatusStartRequest request);

	/**
	 * 1.记录状态变化
	 * @param poNo
	 * @param request  
	 * @return
	 */
	Boolean handleStatusChange(String poNo, StatusChangeRequest request);

	/**
	 * 从本地数据库获取并组装PoResultDetailVo
	 * @param poNo
	 * @return
	 */
	PoResultDetailVo loadLocalPoResultDetailVo(String poNo);

	/**
	 * 2.从业务平台获取PoResultDetailVo
	 * @param poNo
	 * @return
	 */
	PoResultDetailVo loadGbssPoResultDetailVo(String poNo);

	/**
	 * 3.保存从业务平台获取到的PoResultDetailVo到本地数据库
	 * @param vo
	 * @return
	 */
	PoResultDetailVo persistancePoResultDetailVo(PoResultDetailVo vo);

	/**
	 * 4.将PoResultDetailVo转化为SAP上传格式
	 * @param poNo
	 * @param vo
	 * @return
	 */
	DealerOrderZrebBapi translatePoResultDetailVo2Bapi(String poNo, PoResultDetailVo vo);

	/**
	 *5. 执行订单上传SAP接口
	 * @param bapi
	 * @return
	 */
	DealerOrderZrebBapi executeUploadBapi(String poNo, DealerOrderZrebBapi bapi);

	/**
	 * 6.回写本地SAP订单号
	 * @param poNo
	 * @param sapPostingDocNo
	 * @return
	 */
	boolean executeLocaleResultCallback(String poNo, String sapPostingDocNo);

	/**
	 * 7. 回写GBSS系统SAP订单号（发送MQ消息）
	 * @param poNo
	 * @param result
	 * @return
	 */
	String executeGbssResultCallback(String poNo, OrderUploadMessage result);

	/**
	 * 8. GBSS变更状态更新到OMS
	 * @param poNo 
	 */
	PoStatusChange handlePoStatusChange(String poNo, PoStatusChange poStatusChange);

	/**
	 * GBSS变更状态更新到OMS后,再同步修改可能的地址变更
	 */
	void changePoAddrDetail(String poNo);

	/**
	 * 执行订单上传
	 */
	void doProcessingLogic(String poNo);

}

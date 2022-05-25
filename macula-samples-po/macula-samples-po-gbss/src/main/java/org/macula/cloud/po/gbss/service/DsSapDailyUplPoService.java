/**
 * 
 */
package org.macula.cloud.po.gbss.service;

import java.util.List;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;

/**
 * <p>
 * <b>DsSapDailyUplPoService</b> 是
 * </p>
 *
 
 
 
 *
 */

public interface DsSapDailyUplPoService {
	/**
	 * 正常模式下 获取待上传单据
	 * @param queryNum
	 * @return
	 * @throws Exception
	 */
	public List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNum(int queryNum) throws Exception;

	/**
	 * 仅仅获取2013年前的订单（2013年的单据不上传）
	 * @param queryNum
	 * @return
	 */
	public List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNumAndDateFlag(int queryNum, String dateFlag) throws Exception;

	/**
	 * 获取服务中心订单上传队列
	 * 
	 * @param queryNum
	 * @return List<SapDailyUplPoV2>
	 * @throws Exception
	 */
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNum(int queryNum) throws Exception;

	/**
	 * 获取当前所有有效的服务中心列表
	 * @return
	 */
	public List<String> getAllPosStoreList() throws Exception;

	/**
	 * @param poStoreCode
	 * @param maxQueryNum
	 * @return
	 * @throws Exception
	 */
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCode(String poStoreCode, int maxQueryNum) throws Exception;

	/**
	 * 查询指定日期前的单据
	 * @param poStoreCode
	 * @param maxQueryNum
	 * @param dateFlag
	 * @return
	 * @throws Exception
	 */
	public List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCodeAndDateFlag(String poStoreCode, int maxQueryNum, String dateFlag)
			throws Exception;

	/**
	 * 获取服务中心待上传的撤单和退货单
	 * @param queryNum
	 * @return
	 * @throws Exception
	 */
	public List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNum(int queryNum) throws Exception;

	/**
	 * 查询指定日期之前的单据
	 * @param queryNum
	 * @param dateFlag
	 * @return
	 * @throws Exception
	 */
	public List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNumAndDateFlag(int queryNum, String dateFlag) throws Exception;

}

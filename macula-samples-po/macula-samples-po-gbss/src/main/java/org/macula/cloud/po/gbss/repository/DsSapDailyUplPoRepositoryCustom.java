/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDailyUplPoV2;
import org.macula.cloud.po.gbss.domain.PosDeliveryMaster;

/**
 * <p>
 * <b>DsSapDailyUplPoRepositoryCustom</b> 是
 * </p>
 * 
 
 
 
 *          zhangzj $
 * 
 */
public interface DsSapDailyUplPoRepositoryCustom {

	/**
	 * @param maxQueryNum
	 * @return
	 */
	List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNum(int maxQueryNum);

	List<SapDailyUplPo> findBySynTypeAndSynStatusAccordingToNumAndDateFlag(int maxQueryNum, String dateFlag);

	List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNum(int maxQueryNum);

	/**
	 * 查询服务中心上传队列表待上传的撤单和退货单
	 * @param maxQueryNum
	 * @return
	 */
	List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNum(int maxQueryNum);

	/**
	 * 查询服务中心上传队列表待上传的撤单和退货单 查询指定日期钱的单据
	 * @param maxQueryNum
	 * @param dateFlag
	 * @return
	 */
	List<SapDailyUplPoV2> getPosRevokeAndReturnOrderToNumAndDateFlag(int maxQueryNum, String dateFlag);

	/**
	 * 根据服务中心查询单据
	 * @param poStoreCode
	 * @param maxQueryNum
	 * @return
	 */
	List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCode(String poStoreCode, int maxQueryNum);

	/**
	 * 查询指定日期前的单据
	 * @param poStoreCode
	 * @param dateFlag
	 * @return
	 */
	List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNumAndDateFlag(String poStoreCode, int maxQueryNum, String dateFlag);

	/**
	 * 获取自营店交货主信息列表
	 * 
	 * @param sapPostingFlag sap记账标志
	 * @param maxQueryNum 限定查询结果条数
	 * @return List<PosDeliveryMaster>
	 */
	public List<PosDeliveryMaster> getPosDeliveryMasterList(String sapPostingFlag, int maxQueryNum);

	/**
	 * 根据服务中心查询单据
	 * @param poStoreCode
	 * @return
	 */
	List<SapDailyUplPoV2> getBySynTypeAndSynStatusAndpoStoreCode(String poStoreCode);

	/**
	 * 查询指定日期前的单据
	 * @param poStoreCode
	 * @param dateFlag
	 * @return
	 */
	List<SapDailyUplPoV2> getBySynTypeAndSynStatusAccordingToNumAndDateFlag(String poStoreCode, String dateFlag);

	/**
	 * 获取自营店交货主信息
	 * 
	 * @param posDeliveryNo
	 * @return PosDeliveryMaster
	 */
	public PosDeliveryMaster getPosDeliveryMaster(String posDeliveryNo);

	List<SapDailyUplPo> getSapDailyUplPoByPoNo(String poNo);

	List<SapDailyUplPoV2> getSapDailyUplPoV2ByPoNo(String poNo);

}

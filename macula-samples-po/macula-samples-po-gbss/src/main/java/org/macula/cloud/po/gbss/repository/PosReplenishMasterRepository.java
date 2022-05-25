/**
 * PosReplenishMasterRepository.java 2012-4-25
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.PosReplenishMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>PosReplenishMasterRepository</b> 是自营店补货申请主记录Repository
 * </p>
 *
 
 
 
 *
 */
public interface PosReplenishMasterRepository extends JpaRepository<PosReplenishMaster, Long> {

	/**
	 * 通过转储单号查询pos补货申请主单
	 * @param sapTranDocNo 转储单号
	 * @return
	 */
	PosReplenishMaster findBySapTranDocNo(String sapTranDocNo);

	/**
	 * 通过转储单号和类型查询pos补货申请主单
	 * @param sapTranDocNo
	 * @param appTranType
	 * @return
	 */
	PosReplenishMaster findBySapTranDocNoAndAppTranType(String sapTranDocNo, String appTranType);

	/**
	 * 获取自营店补货申请信息
	 * 
	 * @param posStoreNo POS自营店号
	 * @param appTranType 申请单类型
	 * @param sapTranFlag SAP处理标志
	 * @return List<PosReplenishMaster>
	 */
	List<PosReplenishMaster> findByPosStoreNoAndAppTranTypeAndSapTranFlag(String posStoreNo, String appTranType, String sapTranFlag);

}

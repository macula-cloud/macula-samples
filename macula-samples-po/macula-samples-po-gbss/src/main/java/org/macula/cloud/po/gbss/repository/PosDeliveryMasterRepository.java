/**
 * PosDeliveryMasterRepository.java 2012-5-4
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.PosDeliveryMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>PosDeliveryMasterRepository</b> 是
 * </p>
 * 
 
 
 
 *          ky_yx $
 * 
 */
public interface PosDeliveryMasterRepository extends JpaRepository<PosDeliveryMaster, Long> {

	/**
	 * 获取交货主信息
	 * 
	 * @param poNo
	 * @return PosDeliveryMaster
	 */
	public PosDeliveryMaster findByPoNo(String poNo);

	public List<PosDeliveryMaster> findBySapPostingFlag(String sapPostingFlag);

	public PosDeliveryMaster findByPosDeliveryNo(String posDeliveryNo);

}

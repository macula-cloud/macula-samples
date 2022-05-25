/**
 * PosIntercoWhInfoRepository.java 2012-11-1
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.PosIntercoWhInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>PosIntercoWhInfoRepository</b> is
 * </p>
 *
 
 
 
 */
public interface PosIntercoWhInfoRepository extends JpaRepository<PosIntercoWhInfo, Long> {

	/**
	 * 根据S工厂获取对应的V工厂
	 * @param posWhInfo
	 * @return
	 */
	public PosIntercoWhInfo findByPosWhInfo(String posWhInfo);

}

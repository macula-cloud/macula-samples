/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsPoMasterRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface DsMkpDlpPoRelateRepository extends JpaRepository<MkpDlpPoRelate, Long> {

	/**
	 * 通过订单号获取价值表信息
	 * @param poNo
	 * @return
	 
	 */
	public MkpDlpPoRelate findByPoNo(String poNo);

}
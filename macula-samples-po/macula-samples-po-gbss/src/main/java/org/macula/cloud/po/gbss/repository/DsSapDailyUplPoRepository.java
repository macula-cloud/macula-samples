/**
 * DsSapDailyUplPoRepository.java 2011-10-17
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsSapDailyUplPoRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface DsSapDailyUplPoRepository extends JpaRepository<SapDailyUplPo, Long>, DsSapDailyUplPoRepositoryCustom {

	public SapDailyUplPo findByPoNo(String poNo);

	public List<SapDailyUplPo> findBySynTypeAndSynStatusOrderByIdAsc(String synType, String synStatus);
}

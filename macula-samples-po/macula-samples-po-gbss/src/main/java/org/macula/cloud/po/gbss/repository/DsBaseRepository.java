/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsBaseRepository</b> 是公共的repository接口
 * </p>
 * 
 
 
 
 * 
 */
public interface DsBaseRepository extends JpaRepository<Dealer, Long>, DsBaseRepositoryCustom {

	public int queryDeliveryPlanTime();

}

/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.DealerSpDstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>DealerSpDstrRepository</b> 是经销商资料信息Repository
 * </p>
 * 
 
 
 
 * 
 */
public interface DealerSpDstrRepository extends JpaRepository<DealerSpDstr, Long> {

	DealerSpDstr findByDealerNo(String dealerNo);

	@Query("from DealerSpDstr d where d.dealerNo in :dealerNos ")
	public List<DealerSpDstr> findDealerSpDstrByInDealerNos(@Param("dealerNos") List<String> dealerNos);

}

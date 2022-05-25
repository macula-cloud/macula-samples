/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsDealerRepository</b> 是
 * </p>
 */
public interface DsDealerRepository extends JpaRepository<Dealer, Long> {

	/**
	 * 根据用户开号查询会员信息
	 * 
	 * @param dealerNo
	 * @return
	 */
	public Dealer findByDealerNo(String dealerNo);

}

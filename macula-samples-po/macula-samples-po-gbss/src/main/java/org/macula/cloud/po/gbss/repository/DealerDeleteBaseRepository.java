/**
 * DealerAppChgBranchRepository.java 2012-9-11
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.DealerDeleteBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>DealerAppChgBranchRepository</b> 会员所属分公司变更申请及审核repository接口
 * </p>
 * 
 
 
 
 *          ky_pxb $
 */
public interface DealerDeleteBaseRepository extends JpaRepository<DealerDeleteBase, Long> {

	@Query(" from DealerDeleteBase d where d.dealerNo=:dealerNo")
	public DealerDeleteBase getDeletedDealer(@Param("dealerNo") String dealerNo);
}

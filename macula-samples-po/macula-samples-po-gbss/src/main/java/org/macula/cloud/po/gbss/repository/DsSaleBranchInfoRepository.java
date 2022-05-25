/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.SaleBranchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsSaleBranchInfoRepository</b> 
 * </p>
 *
 
 
 
 *
 */

public interface DsSaleBranchInfoRepository extends JpaRepository<SaleBranchInfo, Long> {

	//	public SaleBranchInfo findBySaleOrgCode(String saleOrgCode);

	public SaleBranchInfo findBySaleBranchNo(String saleBranchNo);

	//	@Query("select saleBranchNo from SaleBranchInfo")
	//	List<String> queryBranchNoList();
}

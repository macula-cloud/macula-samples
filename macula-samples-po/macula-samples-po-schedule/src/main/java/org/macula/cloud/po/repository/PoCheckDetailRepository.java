package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * OMS和业务平台GBSS对账数据明细
 */
public interface PoCheckDetailRepository extends JpaRepository<PoCheckDetail, Long> {

	/**
	 * 查询所有异常对账异常数据
	 * @return
	 */
	@Query("from PoCheckDetail p where p.synStatus = '0'")
	List<PoCheckDetail> selectAllAnomaly();

	/**
	 * 根据单号poNo查询对账明细数据
	 * @param poNo
	 * @return
	 */
	PoCheckDetail findByGbssPoNo(String poNo);

	/**
	 * 根据对账主表ID查询所有的对账明细数据
	 * @param checkMasterId
	 * @return
	 */
	List<PoCheckDetail> findByCheckMasterId(Long checkMasterId);

	/**
	 * 删除所有相同check_master_id相同的数据
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("delete  from PoCheckDetail s where  s.checkMasterId = (:checkMasterId) ")
	void deleteCheckMasterId(@Param("checkMasterId") Long checkMasterId);
}

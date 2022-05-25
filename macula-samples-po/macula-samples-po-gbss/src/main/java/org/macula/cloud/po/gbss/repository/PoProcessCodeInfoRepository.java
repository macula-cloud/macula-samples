/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.PoProcessCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>PoProcessCodeInfoRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface PoProcessCodeInfoRepository extends JpaRepository<PoProcessCodeInfo, Long> {

	/**
	 * 查询常用服务中心
	 * @param addressId
	 * @param dealerNo
	 * @return
	 */
	@Query("from PoProcessCodeInfo t where t.isForPo=1 and t.poProcessCode=:poProcess1")
	PoProcessCodeInfo findPoProcessCodeInfo(@Param("poProcess1") String poProcessCode);

	public PoProcessCodeInfo findByPoProcessCode(String poProcessCode);

}

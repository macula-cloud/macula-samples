/**
 * 
 */
package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.SapDsMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 * <b>DsSapDsMappingRepository</b> sap和ds的mapping表
 * </p>
 *
 
 
 
 *
 */
public interface SapDsMappingRepository extends JpaRepository<SapDsMapping, Long> {

	/**
	 * 根据描述查找数据
	 * @param dsDesc
	 * @return
	 */
	public SapDsMapping findByDsDesc(String dsDesc);

	@Query("select sapCode from SapDsMapping where dsDesc like :dsDesc and isEnabled = 1")
	public List<String> findAsDsDescLike(String dsDesc);

	/**
	 * 根据省份编号查询
	 * @param dsCode
	 * @return
	 */
	public SapDsMapping findByDsCode(String dsCode);

}

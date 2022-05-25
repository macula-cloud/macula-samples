/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.SapDsMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsSapDsMappingRepository</b> sap和ds的mapping表
 * </p>
 *
 
 
 
 *
 */
public interface DsSapDsMappingRepository extends JpaRepository<SapDsMapping, Long> {
	/**
	 * 根据描述查找数据
	 * @param dsDesc
	 * @return
	 */
	public SapDsMapping findByDsDesc(String dsDesc);

	/**
	 * 根据编码查找数据
	 * @param dsCode
	 * @return
	 */
	public SapDsMapping findByDsCode(String dsCode);

}

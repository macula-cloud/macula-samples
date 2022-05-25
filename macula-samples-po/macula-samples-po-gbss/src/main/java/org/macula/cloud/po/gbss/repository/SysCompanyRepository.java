/**
 * DsSysCompanyRepository.java 2019-05-21
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.SysCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsSysCompanyRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface SysCompanyRepository extends JpaRepository<SysCompany, Long> {

	/**
	 * @param companyNo
	 */
	public SysCompany findByCompanyNo(String companyNo);

}

package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.SysCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysCompanyRepository extends JpaRepository<SysCompany, Long> {

	/**
	 * 根据poNo查询
	 * @param companyNo
	 */
	public SysCompany findByCompanyNo(String companyNo);
}

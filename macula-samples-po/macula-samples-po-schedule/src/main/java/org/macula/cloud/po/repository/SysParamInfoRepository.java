package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.SysParamInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysParamInfoRepository extends JpaRepository<SysParamInfo, Long> {

	public SysParamInfo findByParaCode(String paraCode);

	/**
	 * 根据表名及字段名查所有枚举类
	 * @param paraScope
	 * @param paraCode
	 * @return
	 */
	public SysParamInfo findByParaScopeAndParaCode(String paraScope, String paraCode);

	/**
	 * 根据表名及字段名查所有枚举类
	 * @param paraScope
	 * @return
	 */
	public List<SysParamInfo> findByParaScope(String paraScope);

}

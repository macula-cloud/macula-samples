/**
 * DsSysParamInfoRepository.java 2011-10-14
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.SysParamInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsSysParamInfoRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface SysParamInfoRepository extends JpaRepository<SysParamInfo, Long> {

	public SysParamInfo findByParaCode(String paraCode);

	/**
	 * 根据表名及字段名查所有枚举类
	 * 
	 * @param tableName
	 * @return
	 */
	public SysParamInfo findByParaScopeAndParaCode(String paraScope, String paraCode);

	/**
	 * 根据表名及字段名查所有枚举类
	 * 
	 * @param tableName
	 * @return
	 */
	public List<SysParamInfo> findByParaScope(String paraScope);
	//
	//	public List<SysParamInfo> findByParaCodeIn(String[] paraCode);
	//
	//	/**
	//	 * 意向报备-下载专区专用查询，查询是paradesc否有重复的
	//	 * 
	//	 * @param paradesc
	//	 * @param paraScope
	//	 * @return List<SysParamInfo>
	//	 */
	//	public List<SysParamInfo> findByParaDescAndParaScope(String paraDesc, String ParaScope);
}

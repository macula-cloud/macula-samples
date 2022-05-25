/**
 * DsSysEnumInfoRepository.java 2011-10-24
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.SysEnumInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsSysEnumInfoRepository</b> is
 * </p>
 *
 
 
 
 */
public interface SysEnumInfoRepository extends JpaRepository<SysEnumInfo, Long> {

	//	/**
	//	 * 根据提条件获取sysEnumInfo枚举表中的数据 (根据dataValue获取dataFullDesc)
	//	 * @param languageCode
	//	 * @param tableName
	//	 * @param fieldName
	//	 * @param dataValue
	//	 * @return
	//	 */
	//	@Query("select a from SysEnumInfo a where a.languageCode = :languageCode and a.tableName = :tableName "
	//			+ "and a.fieldName = :fieldName and a.dataValue = :dataValue and isEnabled = 1")
	//	public SysEnumInfo getSysEnumInfoByFourParam(@Param("languageCode") String languageCode, @Param("tableName") String tableName,
	//			@Param("fieldName") String fieldName, @Param("dataValue") String dataValue);
	//
	//	@Query("select a.dataValue,a.dataFullDesc from SysEnumInfo a where a.languageCode = :languageCode and a.tableName = :tableName "
	//			+ "and a.fieldName = :fieldName  and isEnabled = 1")
	//	public List<?> getSysEnumInfoByThreeParam(@Param("languageCode") String languageCode, @Param("tableName") String tableName,
	//			@Param("fieldName") String fieldName);

	/**
	 * 根据表名及字段名查所有枚举类
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public List<SysEnumInfo> findByTableNameAndFieldName(String tableName, String fieldName);

}

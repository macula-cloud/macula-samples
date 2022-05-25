/**
 * DsPoAreaInfoRepository.java 2011-7-8
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.PoAreaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>DsPoAreaInfoRepository</b> is 四级地址的DAO
 * </p>
 *
 
 
 
 */
public interface DsPoAreaInfoRepository extends JpaRepository<PoAreaInfo, Long> {

	/**
	 * 根据得到该区域编号下的所有子区域
	 * @param parentAreaCode 父区域
	 * @return 
	 */
	List<PoAreaInfo> findByParentAreaCode(String parentAreaCode);

	/**
	 * 根据区域编号获取当前区域对象的信息
	 * @param areaCode
	 * @return
	 */
	PoAreaInfo findByAreaCode(String areaCode);

	/**
	 * 根据区域名称获得区域下的所有子区域
	 */
	@Query("select pai from PoAreaInfo pai where pai.isShow=1 and pai.parentAreaCode = ("
			+ "select p.areaCode from PoAreaInfo p where p.areaDesc=:areaDesc and p.isShow=1) order by pai.areaCode ")
	List<PoAreaInfo> findByAreaDesc(@Param("areaDesc") String areaDesc);

	/**
	 * 根据地区描述和地区备注查询
	 */
	@Query
	List<PoAreaInfo> findByAreaDescAndAreaMemo(@Param("areaDesc") String areaDesc, @Param("areaMemo") String areaMemo);

	/**
	 * 根据区域的父级区域编号获得与该区域同父级区域的集合
	 */
	@Query("select p from PoAreaInfo p where p.parentAreaCode = (select c.parentAreaCode from PoAreaInfo c" + " where c.areaCode=:areaCode)")
	List<PoAreaInfo> findChildAreaInfoByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 根据区域类型和地名获取PoAreaInfo信息
	 * @param areaType
	 * @param areaDesc
	 * @return
	 */
	List<PoAreaInfo> findByAreaTypeAndAreaDesc(String areaType, String areaDesc);

	/**
	 * 根据地址描述和父级地址编码查询地址信息
	 * @param areaDesc 地址描述
	 * @param parentAreaCode 父级地址编码
	 * @return 地址信息
	 */
	List<PoAreaInfo> findByAreaDescAndParentAreaCode(String areaDesc, String parentAreaCode);

	/**
	 * 根据区域编号获取当前区域对象的信息
	 * @param areaCode
	 * @return
	 */
	@Query("select a from PoAreaInfo a where a.areaCode = :areaCode")
	List<PoAreaInfo> getPoAreaInfoByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 根据区域名称获得区域下的所有子区域
	 */
	@Query("select pai from PoAreaInfo pai where pai.isShow=1 and pai.parentAreaCode = ("
			+ "select p.areaCode from PoAreaInfo p where p.areaDesc=:areaDesc and p.areaType=:parentType and p.isShow=1) order by pai.areaCode ")
	List<PoAreaInfo> findByAreaDescAndParentType(@Param("areaDesc") String areaDesc, @Param("parentType") String parentType);

	/**
	 * @return
	 */
	@Query(" select pai.areaCode from PoAreaInfo pai where pai.areaType='4' order by pai.id desc")
	List<?> getPoAreaInfo();

	// API

	/**
	 * 获取有效的所有级别的物流配送区域信息
	 * @return
	 */
	@Query("select a from PoAreaInfo a where a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	public List<PoAreaInfo> getAllPoAreaInfos();

	/**
	 * 根据父级地址代码获取子级所有物流配送区域信息
	 * @param parentAreaCode
	 * @return
	 */
	@Query("select a from PoAreaInfo a where a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate)) and a.parentAreaCode = :parentAreaCode")
	public List<PoAreaInfo> getByParentAreaCode(String parentAreaCode);

	/**
	 * 批量获取areaCode的信息
	 * 
	 * @param poAreaCodeList
	 * @return
	 */
	@Query(value = "select  *  from cbs.po_area_info t  where 1=1 and t.area_type = :areaType start with t.area_code in :poAreaCodeList connect by prior t.parent_area_code = t.area_code", nativeQuery = true)
	List<PoAreaInfo> getPoAreaInfoByAreaCodeList(@Param("areaType") String areaType, @Param("poAreaCodeList") List<String> poAreaCodeList);

	@Query(value = "select p.* from po_area_info p start with p.area_Code = :areaCode connect by prior p.parent_area_code = p.area_code order by level desc", nativeQuery = true)
	public List<PoAreaInfo> listByAreaCode(@Param("areaCode") String areaCode);
}

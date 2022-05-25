
package org.macula.cloud.logistics.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.logistics.domain.PoAreaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PoAreaInfoRepository extends JpaRepository<PoAreaInfo, Long> {
	/**
	 * 获取PoAreaInfo信息
	 * @param areaCode
	 * @return
	 */
	@Query(value = " select /*[PO]-[PoAreaInfoRepository.listPoAreaInfoByAreaCode]*/ p.area_Code as areaCode, p.area_Type as areaType, p.area_Desc as areaDesc, "
			+ " p.area_Memo as areaMemo, p.parent_Area_Code as parentAreaCode, "
			+ " p.effective_Date as effective_Date, p.inactive_Date as inactiveDate, p.is_Show as isShow "
			+ " from PO_AREA_INFO p where sysdate >= p.effective_Date and (p.inactive_Date is null or sysdate < p.inactive_Date) and p.area_Type<>0 "
			+ " start with p.area_Code =:areaCode " + " connect by prior p.parent_Area_Code = p.area_Code "
			+ " order by level desc ", nativeQuery = true)
	public List<?> listPoAreaInfoByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 根据id找PoAreaInfo
	 * @param id
	 * @return
	 */
	@Query("from PoAreaInfo where id=:id")
	public PoAreaInfo getPoAreaInfoById(@Param("id") Long id);

	/**
	 * 根据areaCode找PoAreaInfo
	 * @param areaCode
	 * @return
	 */
	@Query("from PoAreaInfo where areaCode=:areaCode")
	public List<PoAreaInfo> getPoAreaInfoByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 获取该结点的父节点信息
	 * @param areaCode
	 * @return
	 */
	@Query(value = "select * from po_area_info start with area_code =:areaCode  and id =:id  connect by area_code = prior parent_area_code ", nativeQuery = true)
	public List<PoAreaInfo> getParentTree(@Param("id") Long id, @Param("areaCode") String areaCode);

	/**
	 * 查询是否存在将要生效的结点
	 * @param areaCode
	 */
	@Query(value = "SELECT DECODE(COUNT(1), 0, 'N', 'Y') exists_newrec FROM po_area_info WHERE area_code = ? AND effective_date > TRUNC(SYSDATE, 'dd')", nativeQuery = true)
	public String existsFutureNode(@Param("areaCode") String areaCode);

	/**
	 * 查询
	 * @param areaCode
	 */
	@Query(value = "SELECT DECODE (COUNT (1), 0, 'N', 'Y') is_only_future_rec FROM po_area_info a "
			+ "WHERE a.area_code = ? AND a.effective_date > TRUNC (SYSDATE, 'dd') " + " AND (SELECT COUNT (1)"
			+ " FROM po_area_info b WHERE b.area_code = a.area_code AND b.effective_date <= TRUNC (SYSDATE, 'dd')"
			+ " AND b.inactive_date > TRUNC (SYSDATE, 'dd')) = 0", nativeQuery = true)
	public String isOnlyFutureRec(@Param("areaCode") String areaCode);

	/**
	 * 获取下一个areaCode
	 * @return
	 */
	@Query(value = "select seq_po_area_info_area_code.nextval from dual", nativeQuery = true)
	public String getAreaCode();

	/**
	 * 根据area_code找到areaCodeList
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT area_code FROM po_area_info CONNECT BY PRIOR area_code = parent_area_code START WITH area_code =:areaCode", nativeQuery = true)
	public List<String> findAreaCodeList(@Param("areaCode") String areaCode);

	/**
	 * 根据area_code找到parentAreaCodeList
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT area_code FROM po_area_info CONNECT BY PRIOR area_code = parent_area_code START WITH "
			+ "area_code =:areaCode UNION SELECT parent_area_code FROM po_area_info " + "WHERE area_code =:areaCode", nativeQuery = true)
	public List<String> findParentAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 冻结
	 * @param areaCodeList
	 * @param parentAreaCodeList
	 * @param isShow
	 *//*
		@Modifying
		@Query("UPDATE PoAreaInfo SET isShow =:isShow WHERE areaCode in:areaCodeList AND parentAreaCode in:parentAreaCodeList" )
		public void updateFreezeFlag(@Param("areaCodeList") List<String> areaCodeList, @Param("parentAreaCodeList") List<String> parentAreaCodeList,
			@Param("isShow") int isShow);*/

	/**
	 * @param tmpAreaCodeList
	 * @param isShow
	 * @param userName
	 */
	@Transactional
	@Modifying
	@Query(value = "UPDATE po_area_info SET is_Show =:isShow,last_updated_time = SYSDATE,last_updated_by =:userName WHERE area_Code in:tmpAreaCodeList ", nativeQuery = true)
	public void updateFreeAreaCode(@Param("tmpAreaCodeList") List<String> tmpAreaCodeList, @Param("isShow") int isShow,
			@Param("userName") String userName);

	/**
	 * 判断父节点是否冻结
	 */
	@Query(value = "select * from po_area_info i where i.is_show = '0' start with i.area_code =:areaCode  connect by prior i.parent_area_code = i.area_code", nativeQuery = true)
	public List<PoAreaInfo> isfreezeAreaInfo(@Param("areaCode") String areaCode);

	/**
	 * 根据area_code找到parent PoAreaInfo
	 * @param areaCode
	 * @return
	 */
	@Query(value = "select * from po_area_info where area_code  in (select parent_area_code from po_area_info where area_code=:areaCode )", nativeQuery = true)
	public PoAreaInfo findParentAreaByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 查询二级下移/四级平移可选生效日期的最大值
	 */
	@Query(value = "SELECT nvl(to_char(MIN(TRUNC(inactive_date, 'dd')) - 1, 'yyyy-MM-dd'),'9999-12-31') max_effective_date"
			+ " FROM po_area_info WHERE area_type = 4 AND is_show = 1 AND effective_date"
			+ " <= TRUNC (SYSDATE, 'dd') AND inactive_date > TRUNC (SYSDATE, 'dd') CONNECT"
			+ " BY PRIOR area_code = parent_area_code START WITH area_code =:areaCode", nativeQuery = true)
	public String getMaxEffectiveDate(@Param("areaCode") String areaCode);

	/**
	 * 查询二级结点下是否具有待生效的孙子结点
	 */
	@Query(value = "SELECT DECODE(COUNT(1), 0, 'N', 'Y') exists_future_rec_in_tree "
			+ "FROM po_area_info WHERE area_type = 4 AND effective_date > TRUNC(SYSDATE, 'dd') "
			+ "CONNECT BY PRIOR area_code = parent_area_code START WITH area_code =:areaCode", nativeQuery = true)
	public String existsFutureRecInTree(@Param("areaCode") String areaCode);

	/**
	 * 查询二级下移的树
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT p.*, (CASE WHEN is_show = " + "0 THEN 'GRAY' WHEN effective_date > TRUNC(SYSDATE, 'dd') AND area_type = 4 THEN 'RED' "
			+ "ELSE 'BLACK' END) AS color FROM po_area_info p WHERE inactive_date > TRUNC(SYSDATE, 'dd') "
			+ "AND area_type < '3' AND is_show = 1 and area_code" + " <>:areaCode  CONNECT BY PRIOR area_code = parent_area_code START"
			+ " WITH area_code = '000000'", nativeQuery = true)
	public List<PoAreaInfo> findForSecondArea(@Param("areaCode") String areaCode);

	/**
	 * 将临时表符合校验的数据导入到po_area_info表
	 * @param impBatchNum
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO po_area_info(ID, area_code, area_type, area_desc, area_memo, parent_area_code, effective_date, inactive_date, is_show, comments, created_mode, created_time, created_by, last_updated_time, last_updated_by) "
			+ "(SELECT seq_po_area_info_id.NEXTVAL, area_code, area_type, area_desc, area_memo, parent_area_code, effective_date, inactive_date, is_show, imp_batchnum  AS comments, created_mode, created_time, created_by, last_updated_time, last_updated_by FROM po_area_info_imp "
			+ "WHERE imp_batchnum =:impBatchNum and oper_code='#1' )", nativeQuery = true)
	public void insertPoAreaInfo(@Param("impBatchNum") String impBatchNum);

	/**
	 * 
	 * @param areaCode
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE po_area_info WHERE area_code IN (SELECT area_code FROM po_area_info WHERE inactive_date > TRUNC(SYSDATE, 'dd') "
			+ "CONNECT BY PRIOR area_code = parent_area_code START WITH area_code =:areaCode ) "
			+ "AND area_type = '4' AND is_show = 1 AND effective_date > TRUNC(SYSDATE, 'dd')  AND area_type = '4' ", nativeQuery = true)
	public void deleteUnEffctiveLeafNode(@Param("areaCode") String areaCode);

	/**
	 * 根据areaCode查找有效结点
	 * @param areaCode
	 * @return
	 */
	@Query(value = "select * from po_area_info where area_code =:areaCode  and effective_date <= trunc(sysdate ,'dd') and inactive_date > trunc(sysdate ,'dd') ", nativeQuery = true)
	public PoAreaInfo findEffctvieAreaInfoByAreaCode(@Param("areaCode") String areaCode);

	@Query(value = "SELECT f_lss_areapath(:areaCode)||'('||:areaCode||')' as area_path_desc from dual", nativeQuery = true)
	public String findPathByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 复制孙子结点到被下移到三级结点下
	 * @param areaCode
	 * @param areaCode2
	 * @param targetAreaCode
	 * @param effectiveDate
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO po_area_info(ID, area_code, area_type, area_desc, area_memo, parent_area_code,"
			+ " effective_date, inactive_date, comments, created_mode, created_time, created_by, last_updated_time,"
			+ " last_updated_by, is_show) (SELECT seq_po_area_info_id.NEXTVAL, area_code, area_type, DECODE((SELECT "
			+ "COUNT(area_desc) FROM po_area_info WHERE area_type = 4 AND is_show = 1 AND effective_date <= TRUNC(SYSDATE,"
			+ " 'dd') AND inactive_date > TRUNC(SYSDATE, 'dd') AND area_desc = a.area_desc CONNECT BY PRIOR area_code "
			+ "= parent_area_code START WITH area_code =:areaCode), 1, area_desc, area_desc||'('||(SELECT"
			+ " area_desc FROM po_area_info WHERE area_code = a.parent_area_code AND ROWNUM = 1)||')') area_desc, "
			+ "area_memo, :newAreaCode , TO_DATE(:effectiveDate, 'yyyy-mm-dd'), inactive_date, "
			+ "comments, '*U', SYSDATE, :userName, SYSDATE, :userName, 1 FROM po_area_info a "
			+ "WHERE area_type = 4 AND is_show = 1 AND effective_date <= TRUNC(SYSDATE, 'dd') AND inactive_date > "
			+ "TRUNC(SYSDATE, 'dd') CONNECT BY PRIOR area_code = parent_area_code START WITH area_code =:areaCode)", nativeQuery = true)
	public void levelTwoMoveDown(@Param("areaCode") String areaCode, @Param("newAreaCode") String newAreaCode,
			@Param("effectiveDate") String effectiveDate, @Param("userName") String userName);

	/**
	 * 更新孙子结点(四级)失效时间
	 * @param areaCode
	 * @param effectiveDate
	 */
	@Transactional
	@Modifying
	@Query(value = "UPDATE po_area_info s SET inactive_date = TO_DATE(:effectiveDate , 'yyyy-mm-dd'), last_updated_time = SYSDATE,last_updated_by =:userName "
			+ "WHERE area_code IN (SELECT area_code FROM po_area_info WHERE IS_SHOW = 1 AND inactive_date > TRUNC(SYSDATE, 'dd') CONNECT BY PRIOR area_code = parent_area_code  "
			+ "START WITH area_code =:areaCode ) AND area_type = '4' AND s.id = (SELECT MIN(i.id) KEEP(DENSE_RANK FIRST ORDER BY i.effective_date) FROM po_area_info i "
			+ "WHERE i.area_code = s.area_code and i.inactive_date > trunc(sysdate, 'dd'))", nativeQuery = true)
	public void updateLevelFourInactiveDate(@Param("areaCode") String areaCode, @Param("effectiveDate") String effectiveDate,
			@Param("userName") String userName);

	/**
	 * 查询三级结点下是否具有待生效的四级结点
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT DECODE(COUNT(1), 0, 'N', 'Y') exists_future_children FROM "
			+ "po_area_info WHERE area_type = 4 AND effective_date > TRUNC(SYSDATE, 'dd')" + " AND parent_area_code =:areaCode", nativeQuery = true)
	public String existsFutureChildren(@Param("areaCode") String areaCode);

	/**
	 * 根据parentAreaCode获取已生效节点 (包括冻结节点)
	 * @param parentAreaCode
	 * @return
	 */
	@Query(value = "SELECT p.*, (CASE WHEN effective_date > " + "TRUNC(SYSDATE, 'dd') THEN 'RED' ELSE 'BLACK' END) color FROM po_area_info p "
			+ "WHERE area_type = 4 AND is_show = 1 AND effective_date <= TRUNC(SYSDATE, 'dd') AND inactive_date > TRUNC(SYSDATE, 'dd') AND "
			+ "parent_area_code =:parentAreaCode  ORDER BY color DESC, area_desc", nativeQuery = true)
	public List<PoAreaInfo> findEffectiveAreaInfoByParentAreaCode(@Param("parentAreaCode") String parentAreaCode);

	/**
	 * 删除待生效节点
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE po_area_info WHERE area_type = '4' AND is_show = 1 AND effective_date > TRUNC(SYSDATE, 'dd') AND area_code IN (SELECT area_code FROM po_area_info WHERE id IN (:areaId))", nativeQuery = true)
	public void deleteUnEfffectiveNodeByParentAreaCode(@Param("areaId") String[] areaId);

	/**
	 * //将叶子结点复制到新的父节点下
	 * @param nodes
	 * @param parentAreaCode
	 * @param targetAreaCode
	 * @param effectiveDate
	 * @param userName
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO po_area_info(ID, area_code, area_type, area_desc, area_memo, parent_area_code, effective_date,  inactive_date, comments, created_mode, created_time, created_by, last_updated_time, last_updated_by, is_show) "
			+ "( SELECT seq_po_area_info_id.NEXTVAL, area_code, area_type, DECODE((SELECT COUNT(1) FROM po_area_info WHERE parent_area_code =:targetAreaCode AND area_type = '4' AND is_show = 1 AND effective_date <= TRUNC(SYSDATE, 'dd') "
			+ "AND inactive_date > TRUNC(SYSDATE, 'dd') AND area_desc = a.area_desc), 0, area_desc, area_desc||'('|| (SELECT area_desc FROM po_area_info WHERE area_code = a.parent_area_code AND ROWNUM = 1)||')') area_desc,"
			+ " area_memo, :targetAreaCode, TO_DATE(:effectiveDate, 'yyyy-mm-dd'), inactive_date, comments, '*U', SYSDATE, :userName, SYSDATE, :userName,"
			+ " 1 FROM po_area_info a WHERE ID IN (:areaId))", nativeQuery = true)
	public void levelThreeNodeMoveToNewParent(@Param("areaId") String[] areaId, @Param("targetAreaCode") String targetAreaCode,
			@Param("effectiveDate") String effectiveDate, @Param("userName") String userName);

	/**
	 * 
	 * @param queryStr
	 * @param effectiveDate
	 */
	@Transactional
	@Modifying
	@Query(value = "update po_area_info s set inactive_date = to_date( :effectiveDate ,'yyyy-mm-dd') , last_updated_time = sysdate , last_updated_by =:userName where id IN (:areaId) and effective_date <= trunc(sysdate,'dd') "
			+ "and trunc(sysdate,'dd') < inactive_date AND s.id in (SELECT min(i.id) KEEP(DENSE_RANK FIRST ORDER BY i.effective_date) FROM po_area_info i WHERE i.area_code = s.area_code and i.inactive_date > trunc(sysdate, 'dd')) ", nativeQuery = true)
	public void updateInactiveAreaEffectiveDate(@Param("areaId") String[] areaId, @Param("effectiveDate") String effectiveDate,
			@Param("userName") String userName);

	/**
	 * 四级平移的树
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT p.*, (CASE WHEN is_show = " + "0 THEN 'GRAY' WHEN effective_date > TRUNC(SYSDATE, 'dd') AND area_type = 4 THEN 'RED' "
			+ "ELSE 'BLACK' END) AS color FROM po_area_info p WHERE inactive_date > TRUNC(SYSDATE, 'dd') "
			+ "AND area_type <= '3' AND is_show = 1 and area_code" + " <>:areaCode  CONNECT BY PRIOR area_code = parent_area_code START"
			+ " WITH area_code = '000000'", nativeQuery = true)
	public List<PoAreaInfo> findForFourthArea(@Param("areaCode") String areaCode);

	@Query("from PoAreaInfo where areaCode=:areaCode and inactiveDate>:inactiveDate and isShow = '1' ")
	public List<PoAreaInfo> findByAreaCodeAndLtInactiveDate(@Param("areaCode") String areaCode, @Param("inactiveDate") Date inactiveDate);

	@Query(value = "select * from po_area_info i where i.area_code=:areaCode and is_show = 1 and inactive_date > TRUNC(SYSDATE, 'dd')"
			+ " AND i.area_code " + "NOT IN (SELECT DISTINCT area_code FROM po_area_info WHERE area_type = 4 "
			+ "AND inactive_date > TRUNC(SYSDATE, 'dd') " + "MINUS " + "SELECT area_code FROM po_area_info_std "
			+ "WHERE is_for_store = 1 AND inactive_date > TRUNC (SYSDATE, 'dd')) ", nativeQuery = true)
	public List<PoAreaInfo> findByAreaCodeAndLtInactiveDateAndAreaCodeNotIn(@Param("areaCode") String areaCode);

	/**
	 * 找到父节点
	 * @param id
	 * @return
	 */
	@Query(value = "select * from po_area_info where area_code =(select parent_area_code from po_area_info where id=:id )", nativeQuery = true)
	public PoAreaInfo findParentAreainfo(@Param("id") Long id);

	/**
	 * 
	 * @param parentAreaCode
	 * @param areaDesc
	 * @param areaInfoId
	 * @return 
	 */
	@Query(value = "SELECT DECODE(COUNT(1), 0, 'N', 'Y') IS_DUPLICATED FROM po_area_info p WHERE p.parent_area_code =:parentAreaCode "
			+ "AND p.area_desc =:areaDesc  and p.inactive_date > TRUNC(SYSDATE, 'dd') AND id <> :areaInfoId", nativeQuery = true)
	public String existsSameNameNode(@Param("parentAreaCode") String parentAreaCode, @Param("areaDesc") String areaDesc,
			@Param("areaInfoId") String areaInfoId);

	/**
	 * 判断是否存在四级同名结点
	 * @param parentAreaCode
	 * @param areaDesc
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT DECODE (COUNT(1), 0, 'N', 'Y') is_duplicated FROM po_area_info WHERE parent_area_code =:parentAreaCode  AND area_code <> :areaCode  AND area_desc =:areaDesc  AND inactive_date > TRUNC(SYSDATE, 'dd')", nativeQuery = true)
	public String existsSameNameNodeOfFour(@Param("parentAreaCode") String parentAreaCode, @Param("areaDesc") String areaDesc,
			@Param("areaCode") String areaCode);

	/**
	 * 获取该结点的父节点信息
	 * @param areaCode
	 * @return
	 */
	@Query(value = "select * from po_area_info start with area_code =:areaCode connect by area_code = prior parent_area_code ", nativeQuery = true)
	public List<PoAreaInfo> getParentTreePath(String areaCode);

	/**
	 * 判断家居四级页面是否能新增
	 * @param areaCode
	 * @param inactiveDate
	 * @return
	 */
	@Query(value = "select * from po_area_info i where i.area_code=:areaCode and is_show = 1 and inactive_date > TRUNC(SYSDATE, 'dd')"
			+ " AND i.area_code " + "NOT IN (SELECT DISTINCT area_code FROM po_area_info WHERE area_type = 4 "
			+ "AND inactive_date > TRUNC(SYSDATE, 'dd') " + "MINUS " + "SELECT area_code FROM po_area_info_std "
			+ "WHERE IS_FOR_JP = 1 AND inactive_date > TRUNC (SYSDATE, 'dd')) ", nativeQuery = true)
	public List<PoAreaInfo> findByAreaCodeAndLtInactiveDateAndAreaCodeNotIns(@Param("areaCode") String areaCode);

	public PoAreaInfo findByAreaCode(String areaCode);

	/**
	 * 修改待生效记录时跟已生效记录的失效时间
	 * @param areaCode
	 * @param userName
	 * @param effectiveDate
	 */
	@Transactional
	@Modifying
	@Query(value = "update po_area_info a set inactive_date = decode((select count(1) from po_area_info  "
			+ "where area_code = a.area_code  and id <> a.id  and effective_date > trunc(sysdate, 'dd')), 0 , inactive_date, :inactiveArea) , "
			+ "last_updated_time = sysdate , last_updated_by =:userName  where area_code =:areaCode  and effective_date <= trunc(sysdate,'dd') "
			+ "and trunc(sysdate,'dd') < inactive_date ", nativeQuery = true)
	public void updateInactiveArea(@Param("areaCode") String areaCode, @Param("userName") String userName, @Param("inactiveArea") Date inactiveArea);

	/**
	 * #2批量修改 不管是否存在黑色记录，更新红色记录
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_AREA_INFO T SET (T.AREA_DESC, T.AREA_MEMO,"
			+ "T.EFFECTIVE_DATE,T.INACTIVE_DATE,T.CREATED_TIME,T.CREATED_BY,T.LAST_UPDATED_TIME," + "T.LAST_UPDATED_BY,T.CREATED_MODE)= " + "("
			+ "select P.AREA_DESC, P.AREA_MEMO," + "P.EFFECTIVE_DATE,P.INACTIVE_DATE,SYSDATE,P.CREATED_BY,SYSDATE, P.Last_Updated_By,'*U' "
			+ "AS CREATED_MODE FROM PO_AREA_INFO_IMP P WHERE P.AREA_CODE = T.AREA_CODE AND " + "P.OPER_CODE = '#2' and imp_batchnum=:impBatchNum "
			+ ") " + "WHERE T.inactive_date > trunc(sysdate, 'dd') and " + "T.effective_date > trunc(sysdate, 'dd')  "
			+ "and exists (select * from PO_AREA_INFO_IMP p " + " where P.OPER_CODE = '#2' "
			+ "and p.imp_batchnum=:impBatchNum and p.area_code = t.area_code)", nativeQuery = true)
	@Transactional
	public void saveOperCodeUpdateRedRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #2批量修改 存在黑色记录，更新黑色记录
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_AREA_INFO T SET (" + " T.INACTIVE_DATE,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY) =" + "("
			+ "select P.EFFECTIVE_DATE,SYSDATE,P.LAST_UPDATED_BY " + "FROM PO_AREA_INFO_IMP P"
			+ " WHERE P.AREA_CODE = T.AREA_CODE AND P.OPER_CODE = '#2' " + " AND P.IMP_BATCHNUM=:impBatchNum " + ") "
			+ "WHERE  T.inactive_date > trunc(sysdate, 'dd') " + "and T.effective_date <= trunc(sysdate, 'dd') " + "and exists ("
			+ "select * from PO_AREA_INFO_IMP p " + "where P.OPER_CODE = '#2' and p.imp_batchnum=:impBatchNum "
			+ "and p.area_code = t.area_code)", nativeQuery = true)
	@Transactional
	public void saveOperCodeUpdateBlackRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #5批量删除
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "DELETE PO_AREA_INFO T " + "WHERE exists " + "(" + "SELECT P.AREA_CODE " + "FROM PO_AREA_INFO_IMP P "
			+ "WHERE P.IMP_BATCHNUM=:impBatchNum " + "AND P.OPER_CODE = '#5' and p.area_code=t.area_code " + ") "
			+ "AND T.effective_date > trunc(sysdate, 'dd')", nativeQuery = true)
	@Transactional
	public void deleteOperCodeDelete(@Param("impBatchNum") String impBatchNum);

	/**
	 * @param areaDesc
	 * @param areaCode
	 * @return
	 */
	@Query("from PoAreaInfo t where t.areaDesc=:areaDesc and t.parentAreaCode=:areaCode")
	public PoAreaInfo findByNameAndCode(@Param("areaDesc") String areaDesc, @Param("areaCode") String areaCode);

	/**
	 * @param areaCodeList
	 * @return
	 */
	@Query("from PoAreaInfo t where t.parentAreaCode in :areaCodeList")
	public List<PoAreaInfo> findByParentAreaCodes(@Param("areaCodeList") List<String> areaCodeList);

	/**
	 * 找到所有一级的子节点
	 * @return
	 */
	@Query("from PoAreaInfo t where t.parentAreaCode ='000000'")
	public List<PoAreaInfo> findAllChildNodes();

	/**
	 * @param areaCode
	 * @return
	 */
	@Query(value = "select area_desc from (select * from po_area_info where sysdate >= effective_date and (inactive_date is null or sysdate < inactive_date)) where area_type <> 0 "
			+ "start with area_code = :areaCode connect by prior parent_area_code = area_code order by level desc  ", nativeQuery = true)
	public List<String> getAddress(@Param("areaCode") String areaCode);

}

package org.macula.cloud.logistics.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.logistics.domain.PoHomeWhArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PoHomeWhAreaRepository extends JpaRepository<PoHomeWhArea, Long> {

	/**
	 * 根据areaCode获取店铺配送外仓对照信息
	 * @param areaCode
	 * @return
	 */
	@Query("from PoJpWhArea a where a.areaCode = :areaCode and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	public PoHomeWhArea getPoJpWhArea(@Param("areaCode") String areaCode);

	/**
	 * 根据whCode获取店铺配送外仓对照信息
	 * @param whCode
	 * @return
	 */
	@Query("from PoJpWhArea a where a.whCode = :whCode and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	public List<PoHomeWhArea> listPoJpWhArea(@Param("whCode") String whCode);

	@Query(value = "select /*[PO]-[PoJpWhAreaRepository.getPoJpWhAreaByAreaCode]*/ * from po_jp_wh_area where area_code = :areaCode and to_char(sysdate, 'yyyymmddhh24:mi') >= to_char(effective_date - 1, 'yyyymmdd')||(select para_value from sys_param_info where para_code = 'DELIVERY_CLOSE_TIME') and (inactive_date is null or to_char(inactive_date - 1, 'yyyymmdd')||(select para_value from sys_param_info where para_code = 'DELIVERY_CLOSE_TIME') >to_char(sysdate, 'yyyymmddhh24:mi'))", nativeQuery = true)
	public PoHomeWhArea getPoJpWhAreaByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 根据areaCode获取路径
	 * @param areaCode
	 * @return 
	 */
	@Query(value = "SELECT f_lss_areapath(:areaCode)||'('||:areaCode||')' as area_path_desc from dual", nativeQuery = true)
	public String findPathByAreaCode(@Param("areaCode") String areaCode);

	/**
	 * 获取地区描述
	 * @param areaCode
	 * @return
	 */
	@Query(value = "select wh_name from wh_info t where t.wh_type = 'RDC' and t.wh_code in (SELECT * FROM (SELECT area_wh_code FROM po_area_info_std WHERE area_code =:areaCode AND inactive_date > TRUNC(SYSDATE, 'dd') "
			+ "AND is_for_jp = 1 ORDER BY effective_date DESC) WHERE ROWNUM = 1)", nativeQuery = true)
	public String getWhName(@Param("areaCode") String areaCode);

	/**
	 * 获取仓库代码
	 * @param whName
	 * @return
	 */
	@Query(value = "select wh_code from wh_info t where t.wh_name =:whName and wh_type = 'RDC' ", nativeQuery = true)
	public String getWhCodeByWhName(@Param("whName") String whName);

	/**
	 * 获取地区属性
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT area_attr FROM (SELECT * FROM po_area_info_std WHERE area_code =:areaCode AND inactive_date > TRUNC(SYSDATE, 'dd') AND is_for_jp = 1 ORDER BY effective_date DESC) WHERE ROWNUM = 1", nativeQuery = true)
	public String getAreaAttr(@Param("areaCode") String areaCode);

	/**
	 * 查询该四级结点是否已存在生效或待生效的专卖店配送资料记录
	 * @param areaCode
	 * @return 
	 */
	@Query(value = "SELECT DECODE (COUNT(1), 0, 'N', 'Y') exists_avail_rec FROM PO_JP_WH_AREA WHERE area_code =:areaCode AND inactive_date > TRUNC(SYSDATE, 'dd') ", nativeQuery = true)
	public String getExistsAvailRec(@Param("areaCode") String areaCode);

	/**
	 * 获取已生效记录
	 * @param asList
	 * @param currentDate
	 * @return
	 */
	@Query("from PoJpWhArea where areaCode in:areaCodes and inactiveDate>:currentDate and effectiveDate <=:currentDate")
	public List<PoHomeWhArea> getUsingNode(@Param("areaCodes") List<String> areaCodes, @Param("currentDate") Date currentDate);

	/**
	 * 获取待生效记录
	 * @param asList
	 * @param currentDate
	 * @return
	 */
	@Query("from PoJpWhArea where areaCode in:areaCodes and inactiveDate>:currentDate and effectiveDate >:currentDate")
	public List<PoHomeWhArea> getFutureNode(@Param("areaCodes") List<String> areaCodes, @Param("currentDate") Date currentDate);

	@Query(value = "select wh_name from wh_info t where t.wh_code =:whCode  and wh_type = 'RDC' ", nativeQuery = true)
	public String getWhNameByWhCode(@Param("whCode") String whCode);

	/**
	 * 家居配送修改规则校验
	 * @param areaCode
	 * @param effectiveDate
	 * @param inactiveDate
	 * @return
	 */
	@Query(value = "SELECT (CASE WHEN TO_DATE(:effectiveString , 'yyyy/mm/dd') >= MIN(effective_date) AND TO_DATE(:inactiveString , 'yyyy/mm/dd') <= MAX(inactive_date) THEN 'Y' ELSE 'N' END) is_legal_date FROM "
			+ "po_area_info_std WHERE area_code =:areaCode  AND inactive_date > TRUNC(SYSDATE, 'dd') AND is_for_jp = 1", nativeQuery = true)
	public String getLegalDate(@Param("areaCode") String areaCode, @Param("effectiveString") String effectiveString,
			@Param("inactiveString") String inactiveString);

	/**
	 * 家居配送修改规则校验
	 * @param areaCode
	 * @param whCode
	 * @return
	 */
	@Query(value = "select DECODE(COUNT(1), 0, 'N', 'Y') is_legal_wh_code from po_area_info_std p where p.area_code=:areaCode  and p.area_wh_code =:whCode", nativeQuery = true)
	public String getLegalWhCode(@Param("areaCode") String areaCode, @Param("whCode") String whCode);

	/**
	 * 获取已生效结点
	 * @param areaCode
	 * @return
	 */
	@Query(value = "SELECT t.* FROM po_jp_wh_area t WHERE area_code =:areaCode AND inactive_date > TRUNC(SYSDATE, 'dd') AND effective_date <= TRUNC(SYSDATE, 'dd')", nativeQuery = true)
	public List<PoHomeWhArea> getUsingNode(@Param("areaCode") String areaCode);

	/**
	 * 删除待生效结点
	 * @param areaCode
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE po_jp_wh_area WHERE area_code =:areaCode  AND effective_date > TRUNC(SYSDATE, 'dd')", nativeQuery = true)
	public void deleteFutureNode(@Param("areaCode") String areaCode);

	@Query("from PoJpWhArea where areaCode in:areaCodes and inactiveDate>:currentDate and effectiveDate <=:currentDate")
	public List<PoHomeWhArea> findByAreaCodeAndLtInactiveDateAndGteEffectiveDate(@Param("areaCodes") List<String> areaCodes,
			@Param("currentDate") Date currentDate);

	/**
	 * 导入获取批次号
	 * @return
	 */
	@Query(value = "select TO_CHAR(SYSDATE, 'yyyymmdd')||LPAD (seq_PO_JP_WH_AREA_batchnum.NEXTVAL, 12, 0) from dual", nativeQuery = true)
	public String getImpBatchnum();

	/**
	 * #1批量新增
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "INSERT INTO PO_JP_WH_AREA(ID,area_code,area_attr,wh_code,SEND_DISTANCE,SEND_FEE,SEND_ARRIVE_TIME_01,SEND_ARRIVE_TIME_02,JP_MAX_ACTIVE_DAY,JP_MIN_ACTIVE_DAY,"
			+ "WH_MEMO,effective_date,inactive_date,comments,created_mode,created_time,created_by,last_updated_time,last_updated_by,IS_STORE_LOCATE) "
			+ "(SELECT seq_PO_JP_WH_AREA_id.NEXTVAL, area_code, area_attr, wh_code, SEND_DISTANCE,SEND_FEE,SEND_ARRIVE_TIME_01,SEND_ARRIVE_TIME_02, JP_MAX_ACTIVE_DAY, JP_MIN_ACTIVE_DAY,WH_MEMO,"
			+ "effective_date, inactive_date, imp_batchnum ,created_mode, created_time, created_by, last_updated_time,last_updated_by, 0 FROM PO_JP_WH_AREA_IMP WHERE imp_batchnum =:impBatchNum   and oper_code='#1')", nativeQuery = true)
	public void addSql(@Param("impBatchNum") String impBatchNum);

	/**
	 * #2批量修改 不管是否存在黑色记录，更新红色记录
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_JP_WH_AREA T SET (T.WH_CODE,T.SEND_DISTANCE,T.SEND_FEE,T.SEND_ARRIVE_TIME_01,T.SEND_ARRIVE_TIME_02,T.JP_MAX_ACTIVE_DAY,T.JP_MIN_ACTIVE_DAY,"
			+ "T.WH_MEMO,T.EFFECTIVE_DATE,T.INACTIVE_DATE,T.CREATED_TIME,T.CREATED_BY,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY,T.CREATED_MODE) =(select P.WH_CODE,P.SEND_DISTANCE,"
			+ "P.SEND_FEE,P.SEND_ARRIVE_TIME_01,P.SEND_ARRIVE_TIME_02,P.JP_MAX_ACTIVE_DAY,P.JP_MIN_ACTIVE_DAY,P.WH_MEMO,P.EFFECTIVE_DATE,P.INACTIVE_DATE,SYSDATE,P.CREATED_BY,SYSDATE, "
			+ "P.Last_Updated_By,'*U' AS CREATED_MODE FROM PO_JP_WH_AREA_IMP P WHERE P.AREA_CODE = T.AREA_CODE AND P.OPER_CODE = '#2' and imp_batchnum =:impBatchNum ) WHERE T.inactive_date > trunc(sysdate, 'dd') "
			+ "and T.effective_date > trunc(sysdate, 'dd')  and exists (select * from PO_JP_WH_AREA_IMP p where P.OPER_CODE = '#2' and p.imp_batchnum =:impBatchNum  and p.area_code = t.area_code)   ", nativeQuery = true)
	public void editSql2(@Param("impBatchNum") String impBatchNum);

	/**
	 * #2批量修改 存在黑色记录，更新黑色记录
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_JP_WH_AREA T SET ( T.INACTIVE_DATE,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY) = (select P.EFFECTIVE_DATE,SYSDATE,P.LAST_UPDATED_BY FROM PO_JP_WH_AREA_IMP P WHERE P.AREA_CODE = T.AREA_CODE "
			+ "AND P.OPER_CODE = '#2' AND P.IMP_BATCHNUM=:impBatchNum  ) WHERE  T.inactive_date > trunc(sysdate, 'dd') and T.effective_date <= trunc(sysdate, 'dd') "
			+ "and exists (select * from PO_JP_WH_AREA_IMP p where P.OPER_CODE = '#2' and p.imp_batchnum =:impBatchNum    and p.area_code = t.area_code)", nativeQuery = true)
	public void editSql1(@Param("impBatchNum") String impBatchNum);

	/**
	 * #3批量切换 - 删除原有待生效记录，不需先判断是否存在，见参考SQL#4.1（DELETE）
	 * @param impBatchNum
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE PO_JP_WH_AREA WHERE area_code in (SELECT P.AREA_CODE FROM PO_JP_WH_AREA_IMP P WHERE P.IMP_BATCHNUM =:impBatchNum   AND P.OPER_CODE = '#3') "
			+ "AND effective_date > TRUNC(SYSDATE, 'dd')", nativeQuery = true)
	public void switchSql1(@Param("impBatchNum") String impBatchNum);

	/**
	 * #3批量切换 - 数据库中复制原记录部分字段为新记录，插入新记录（INSERT）
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "INSERT INTO PO_JP_WH_AREA (ID,area_attr,area_code,WH_CODE,SEND_DISTANCE ,SEND_FEE,SEND_ARRIVE_TIME_01,SEND_ARRIVE_TIME_02, JP_MAX_ACTIVE_DAY,JP_MIN_ACTIVE_DAY,"
			+ "WH_MEMO ,effective_date,inactive_date,comments,created_mode,created_time,created_by,last_updated_time, last_updated_by,IS_STORE_LOCATE) "
			+ "(SELECT seq_PO_JP_WH_AREA_id.NEXTVAL,area_attr,(select s.area_code from PO_JP_WH_AREA s where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') "
			+ "and s.inactive_date > trunc(sysdate, 'dd') and rownum=1) as area_code,WH_CODE,SEND_DISTANCE,SEND_FEE,SEND_ARRIVE_TIME_01,SEND_ARRIVE_TIME_02,JP_MAX_ACTIVE_DAY,JP_MIN_ACTIVE_DAY,"
			+ "WH_MEMO,effective_date,(select s.inactive_date from PO_JP_WH_AREA s where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') and s.inactive_date > trunc(sysdate, 'dd') and rownum=1) as inactive_date,"
			+ "(select s.comments from PO_JP_WH_AREA s where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') and s.inactive_date > trunc(sysdate, 'dd') and rownum=1) AS comments,'*U' AS created_mode,created_time,created_by,last_updated_time,last_updated_by,"
			+ "(select s.IS_STORE_LOCATE from PO_JP_WH_AREA s where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') and s.inactive_date > trunc(sysdate, 'dd') and rownum=1)as IS_STORE_LOCATE "
			+ "FROM PO_JP_WH_AREA_IMP p WHERE imp_batchnum =:impBatchNum and oper_code = '#3') ", nativeQuery = true)
	public void switchSql2(@Param("impBatchNum") String impBatchNum);

	/**
	 * #3批量切换 - 修改原记录失效日期等字段（UPDATE）
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_JP_WH_AREA T SET ( T.INACTIVE_DATE,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY) = (select P.EFFECTIVE_DATE,SYSDATE,P.LAST_UPDATED_BY FROM PO_JP_WH_AREA_IMP P "
			+ "WHERE P.AREA_CODE = T.AREA_CODE AND P.OPER_CODE = '#3' AND P.IMP_BATCHNUM=:impBatchNum  ) WHERE  T.inactive_date > trunc(sysdate, 'dd') and T.effective_date <= trunc(sysdate, 'dd') "
			+ "and exists (select * from PO_JP_WH_AREA_IMP p where P.OPER_CODE = '#3' and p.imp_batchnum =:impBatchNum  and p.area_code = t.area_code)", nativeQuery = true)
	public void switchSql3(@Param("impBatchNum") String impBatchNum);

	/**
	 * #4批量 失效
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_JP_WH_AREA T SET (T.INACTIVE_DATE,T.LAST_UPDATED_TIME, T.LAST_UPDATED_BY) =(select P.INACTIVE_DATE,SYSDATE,P.LAST_UPDATED_BY FROM PO_JP_WH_AREA_IMP P "
			+ "WHERE P.AREA_CODE = T.AREA_CODE AND P.OPER_CODE = '#4' AND P.IMP_BATCHNUM =:impBatchNum ) WHERE T.inactive_date > trunc(sysdate, 'dd') and T.effective_date <= trunc(sysdate, 'dd') "
			+ "and exists (select * from PO_JP_WH_AREA_IMP p where P.OPER_CODE = '#4' and p.imp_batchnum =:impBatchNum  and p.area_code = t.area_code)", nativeQuery = true)
	public void inactiveSql(@Param("impBatchNum") String impBatchNum);

	/**
	 * #5批量删除
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "DELETE PO_JP_WH_AREA T WHERE exists (SELECT P.AREA_CODE FROM PO_JP_WH_AREA_IMP P WHERE P.IMP_BATCHNUM =:impBatchNum  AND P.OPER_CODE = '#5' and p.area_code=t.area_code) AND T.effective_date > trunc(sysdate, 'dd')", nativeQuery = true)
	public void deleteSql(@Param("impBatchNum") String impBatchNum);

}

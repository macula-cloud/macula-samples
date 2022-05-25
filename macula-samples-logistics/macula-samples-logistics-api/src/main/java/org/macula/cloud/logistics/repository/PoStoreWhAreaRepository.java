package org.macula.cloud.logistics.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.logistics.domain.PoStoreWhArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PoStoreWhAreaRepository extends JpaRepository<PoStoreWhArea, Long> {

	/**
	 * 根据areaCode获取店铺配送外仓对照信息
	 * @param areaCode
	 * @return
	 */
	@Query("from PoStoreWhArea a where a.areaCode = :areaCode and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	public PoStoreWhArea getPoStoreWhArea(@Param("areaCode") String areaCode);

	/**
	 * 根据whCode获取店铺配送外仓对照信息
	 * @param whCode
	 * @return
	 */
	@Query("from PoStoreWhArea a where a.whCode = :whCode and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	public List<PoStoreWhArea> listPoStoreWhArea(@Param("whCode") String whCode);

	/**
	 * 根据areaCode获取店铺配送外仓对照信息
	 * @param areaCode
	 * @return
	 */
	@Query("from PoStoreWhArea a where a.areaCode = :areaCode and a.effectiveDate <= :effectiveDate and (a.inactiveDate is null or a.inactiveDate > :effectiveDate)")
	public PoStoreWhArea getPoStoreWhAreaByAreaCode(@Param("areaCode") String areaCode, @Param("effectiveDate") Date effectiveDate);

	@Query("from PoStoreWhArea where areaCode in:areaCodes and inactiveDate>:currentDate")
	public List<PoStoreWhArea> findByAreaCodeAndLtInactiveDate(@Param("areaCodes") List<String> areaCodes, @Param("currentDate") Date currentDate);

	@Query("from PoStoreWhArea where areaCode in:areaCodes and inactiveDate>:currentDate and effectiveDate <=:currentDate")
	public List<PoStoreWhArea> findByAreaCodeAndLtInactiveDateAndGteEffectiveDate(@Param("areaCodes") List<String> areaCodes,
			@Param("currentDate") Date currentDate);

	@Query("from PoStoreWhArea where areaCode in:areaCodes and inactiveDate>:currentDate and effectiveDate >:currentDate")
	public List<PoStoreWhArea> findByAreaCodeAndLtInactiveDateAndLtEffectiveDate(@Param("areaCodes") List<String> areaCodes,
			@Param("currentDate") Date currentDate);

	@Modifying
	@Query("delete from PoStoreWhArea where areaCode=:areaCode and effectiveDate>:currentDate")
	public void deleteByAreaCodeAndLtEffectiveDate(@Param("areaCode") String areaCode, @Param("currentDate") Date currentDate);

	/**
	 * 批量新增
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "INSERT INTO PO_STORE_WH_AREA(" + "ID,area_code,area_attr,wh_code," + "SEND_DISTANCE,SEND_FEE,SEND_ARRIVE_TIME_01,"
			+ "SEND_ARRIVE_TIME_02,STORE_MAX_ACTIVE_DAY," + "STORE_MIN_ACTIVE_DAY,WH_MEMO,effective_date,"
			+ "inactive_date,comments,created_mode,created_time," + "created_by,last_updated_time,last_updated_by,IS_STORE_LOCATE) "
			+ "(SELECT seq_PO_STORE_WH_AREA_id.NEXTVAL, area_code, area_attr, "
			+ "wh_code, SEND_DISTANCE,SEND_FEE,SEND_ARRIVE_TIME_01,SEND_ARRIVE_TIME_02,"
			+ "STORE_MAX_ACTIVE_DAY, STORE_MIN_ACTIVE_DAY,WH_MEMO,effective_date, " + "inactive_date, imp_batchnum , created_mode, created_time, "
			+ "created_by, last_updated_time,last_updated_by,0 " + "FROM PO_STORE_WH_AREA_IMP " + "WHERE imp_batchnum =:impBatchNum "
			+ "and oper_code='#1')", nativeQuery = true)
	@Transactional
	public void saveOperCodeAdd(@Param("impBatchNum") String impBatchNum);

	/**
	 * #2批量修改 不管是否存在黑色记录，更新红色记录
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_STORE_WH_AREA T " + "SET (T.WH_CODE,T.SEND_DISTANCE," + "T.SEND_FEE,T.SEND_ARRIVE_TIME_01,"
			+ "T.SEND_ARRIVE_TIME_02,T.STORE_MAX_ACTIVE_DAY," + "T.STORE_MIN_ACTIVE_DAY,T.WH_MEMO,"
			+ "T.EFFECTIVE_DATE,T.INACTIVE_DATE,T.CREATED_TIME," + "T.CREATED_BY,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY) ="
			+ "(select P.WH_CODE,P.SEND_DISTANCE,P.SEND_FEE,P.SEND_ARRIVE_TIME_01,"
			+ "P.SEND_ARRIVE_TIME_02,P.STORE_MAX_ACTIVE_DAY,P.STORE_MIN_ACTIVE_DAY," + "P.WH_MEMO,P.EFFECTIVE_DATE,P.INACTIVE_DATE,SYSDATE,"
			+ "P.CREATED_BY,SYSDATE, P.Last_Updated_By " + "FROM PO_STORE_WH_AREA_IMP P " + "WHERE P.AREA_CODE = T.AREA_CODE "
			+ "AND P.OPER_CODE = '#2' " + "and imp_batchnum =:impBatchNum) "
			+ "WHERE T.inactive_date > trunc(sysdate, 'dd') and T.effective_date > trunc(sysdate, 'dd')  " + "and exists (" + "select * from "
			+ "PO_STORE_WH_AREA_IMP p " + "where P.OPER_CODE = '#2' " + "and p.imp_batchnum =:impBatchNum "
			+ "and p.area_code = t.area_code)", nativeQuery = true)
	@Transactional
	public void saveOperCodeUpdateRedRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #2批量修改 存在黑色记录，更新黑色记录
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_STORE_WH_AREA T " + "SET ( T.INACTIVE_DATE," + "T.LAST_UPDATED_TIME," + "T.LAST_UPDATED_BY) ="
			+ "(select P.EFFECTIVE_DATE,SYSDATE," + "P.LAST_UPDATED_BY " + "FROM PO_STORE_WH_AREA_IMP P " + "WHERE P.AREA_CODE = T.AREA_CODE "
			+ "AND P.OPER_CODE = '#2' " + "AND P.IMP_BATCHNUM=:impBatchNum )" + "WHERE  T.inactive_date > trunc(sysdate, 'dd') "
			+ "and T.effective_date <= trunc(sysdate, 'dd') " + "and exists (" + "select * from PO_STORE_WH_AREA_IMP p "
			+ "where P.OPER_CODE = '#2' and p.imp_batchnum =:impBatchNum " + "and p.area_code = t.area_code)", nativeQuery = true)
	@Transactional
	public void saveOperCodeUpdateBlackRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #3批量切换 - 删除原有待生效记录，不需先判断是否存在，见参考SQL#4.1（DELETE）
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "DELETE PO_STORE_WH_AREA " + "WHERE area_code in (" + "SELECT P.AREA_CODE " + "FROM PO_STORE_WH_AREA_IMP P "
			+ "WHERE P.IMP_BATCHNUM =:impBatchNum " + "AND P.OPER_CODE = '#3'" + ") AND effective_date > TRUNC(SYSDATE, 'dd')", nativeQuery = true)
	@Transactional
	public void deleteOperCodeChanageFutureRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #3批量切换 - 数据库中复制原记录部分字段为新记录，插入新记录（INSERT）
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "INSERT INTO PO_STORE_WH_AREA (" + "ID,area_attr,area_code,WH_CODE,SEND_DISTANCE ,SEND_FEE,SEND_ARRIVE_TIME_01,"
			+ "SEND_ARRIVE_TIME_02,STORE_MAX_ACTIVE_DAY,STORE_MIN_ACTIVE_DAY,WH_MEMO ,"
			+ "effective_date,inactive_date,comments,created_mode,created_time,created_by," + "last_updated_time,last_updated_by,IS_STORE_LOCATE) "
			+ "(SELECT seq_po_store_wh_area_id.NEXTVAL,area_attr," + "(select s.area_code from PO_STORE_WH_AREA s "
			+ "where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') "
			+ "and s.inactive_date > trunc(sysdate, 'dd') and rownum=1" + ") as area_code,"
			+ "WH_CODE,SEND_DISTANCE,SEND_FEE,SEND_ARRIVE_TIME_01,SEND_ARRIVE_TIME_02,"
			+ "STORE_MAX_ACTIVE_DAY,STORE_MIN_ACTIVE_DAY,WH_MEMO,effective_date," + "(select s.inactive_date from PO_STORE_WH_AREA s "
			+ "where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') "
			+ "and s.inactive_date > trunc(sysdate, 'dd') and rownum=1" + ") as inactive_date," + "(select s.comments from PO_STORE_WH_AREA s "
			+ "where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') "
			+ "and s.inactive_date > trunc(sysdate, 'dd') and rownum=1" + ") AS comments,  " + "'*U' AS created_mode,created_time, created_by,"
			+ "last_updated_time,last_updated_by," + "(select s.IS_STORE_LOCATE from PO_STORE_WH_AREA s "
			+ "where s.area_code = p.area_code and s.effective_date <= trunc(sysdate, 'dd') "
			+ "and s.inactive_date > trunc(sysdate, 'dd') and rownum=1" + ")as IS_STORE_LOCATE " + "FROM PO_STORE_WH_AREA_imp p "
			+ "WHERE imp_batchnum =:impBatchNum and oper_code = '#3')", nativeQuery = true)
	@Transactional
	public void copyOriginalRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #3批量切换 - 修改原记录失效日期等字段（UPDATE）
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_STORE_WH_AREA T " + "SET ( T.INACTIVE_DATE,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY) ="
			+ "(select P.EFFECTIVE_DATE,SYSDATE,P.LAST_UPDATED_BY " + "FROM PO_STORE_WH_AREA_IMP P "
			+ "WHERE P.AREA_CODE = T.AREA_CODE AND P.OPER_CODE = '#3' " + "AND P.IMP_BATCHNUM=:impBatchNum )"
			+ "WHERE  T.inactive_date > trunc(sysdate, 'dd') " + "and T.effective_date <= trunc(sysdate, 'dd') " + "and exists (select * "
			+ "from PO_STORE_WH_AREA_IMP p " + "where P.OPER_CODE = '#3' " + "and p.imp_batchnum =:impBatchNum "
			+ "and p.area_code = t.area_code)", nativeQuery = true)
	@Transactional
	public void updateOriginalRecord(@Param("impBatchNum") String impBatchNum);

	/**
	 * #4批量 失效
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "UPDATE PO_STORE_WH_AREA T " + "SET (T.INACTIVE_DATE,T.LAST_UPDATED_TIME,T.LAST_UPDATED_BY) ="
			+ "(select P.INACTIVE_DATE,SYSDATE,P.LAST_UPDATED_BY " + "FROM PO_STORE_WH_AREA_IMP P "
			+ "WHERE P.AREA_CODE = T.AREA_CODE AND P.OPER_CODE = '#4' " + "AND P.IMP_BATCHNUM =:impBatchNum) "
			+ "WHERE T.inactive_date > trunc(sysdate, 'dd')  " + "and T.effective_date <= trunc(sysdate, 'dd') "
			+ "and exists (select * from PO_STORE_WH_AREA_IMP p " + "where P.OPER_CODE = '#4' " + "and p.imp_batchnum =:impBatchNum "
			+ "and p.area_code = t.area_code)", nativeQuery = true)
	@Transactional
	public void updateOperCodeInavtive(@Param("impBatchNum") String impBatchNum);

	/**
	 * #5批量删除
	 * @param impBatchNum
	 */
	@Modifying
	@Query(value = "DELETE PO_STORE_WH_AREA T " + "WHERE exists (" + "SELECT P.AREA_CODE FROM PO_STORE_WH_AREA_IMP P "
			+ "WHERE P.IMP_BATCHNUM =:impBatchNum  " + "AND P.OPER_CODE = '#5' " + "and p.area_code=t.area_code" + ") "
			+ "AND T.effective_date > trunc(sysdate, 'dd')", nativeQuery = true)
	@Transactional
	public void deleteOperCodeDelete(@Param("impBatchNum") String impBatchNum);

	//	/**
	//	 * 根据rdc号（whCode）查找出所有对应的专卖店号
	//	 * @param whCode
	//	 * @param pageable
	//	 * @return
	//	 */
	//	@TemplateQuery
	//	public Page<RdcStoreResult> listRdcStoreResult(@Param("whCode") String whCode, Pageable pageable);

	/**
	 * 根据rdc号（whCode）和AREA_ATTR 找出地址
	 * @param whCode
	 * @param areaAttr
	 */
	@Query("from PoStoreWhArea p where p.whCode =:whCode and p.areaAttr =:areaAttr")
	public List<PoStoreWhArea> findWhCodeAndAreaAttr(@Param("whCode") String whCode, @Param("areaAttr") String areaAttr);

	/**
	 * 根据rdc号（whCode）找出地址
	 * @param whCode
	 */
	@Query(value = "select distinct t.area_code from po_area_info t " + "where t.area_type ='1' start with t.area_code in( "
			+ "select a.area_code from PO_JP_WH_AREA a where a.wh_code =:whCode) "
			+ "connect by prior t.parent_area_code = t.area_code", nativeQuery = true)
	public List<String> findJpRdcResult(@Param("whCode") String whCode);

	/*
	 * 根据rdc号（whCode）找出地址
	 * @param whCode
	 */
	@Query(value = "select distinct t.area_code,t.area_desc from po_area_info t " + "where t.area_type =:areaType start with t.area_code in( "
			+ "select a.area_code from PO_JP_WH_AREA a where a.wh_code =:whCode) "
			+ "connect by prior t.parent_area_code = t.area_code", nativeQuery = true)
	public List<?> findJpRdcResult(@Param("whCode") String whCode, @Param("areaType") String areaType);

	/**
	 * 根据rdc号（whCode） parentAreaCode 找出地址
	 * @param whCode
	 * @param code
	 * @param parentAreaCode
	 * @return
	 */
	@Query(value = "select distinct t.area_code,t.area_desc from po_area_info t "
			+ "where t.area_type =:areaType and t.parent_area_code =:parentAreaCode start with t.area_code in( "
			+ "select a.area_code from PO_JP_WH_AREA a where a.wh_code =:whCode) "
			+ "connect by prior t.parent_area_code = t.area_code", nativeQuery = true)
	public List<?> findParentAreaCodeRdcResults(@Param("whCode") String whCode, @Param("areaType") String areaType,
			@Param("parentAreaCode") String parentAreaCode);

}

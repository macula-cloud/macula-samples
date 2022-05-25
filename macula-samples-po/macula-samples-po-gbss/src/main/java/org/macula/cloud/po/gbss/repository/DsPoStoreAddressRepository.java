/**
 * DsPoStoreAddressRepository.java 2011-7-11
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.PoStoreAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>DsPoStoreAddressRepository</b> is 处理专卖店配送地址
 * </p>
 * 
 
 
 
 *          $
 */
public interface DsPoStoreAddressRepository extends JpaRepository<PoStoreAddress, Long> {

	PoStoreAddress findByAddrSendId(String addrSendId);

	/**
	 * 根据addrSendId 获取有效的店铺配送地址
	 * @param addrSendId
	 * @return
	 */
	@Query("from PoStoreAddress a where a.addrSendId = :addrSendId and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	PoStoreAddress getByAddrSendId(@Param("addrSendId") String addrSendId);

	//
	//	/**
	//	 * 获取未失效的地址
	//	 * @param addrSendId
	//	 * @return
	//	 */
	//	@Query("from PoStoreAddress a where a.addrSendId = :addrSendId and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	//	PoStoreAddress getEnableAddrByAddrSendId(@Param("addrSendId") String addrSendId);
	//
	//	/**
	//	 * @param storeNo
	//	 */
	//	@Modifying
	//	@Query("update PoStoreAddress a set a.inactiveDate=trunc(sysdate),a.defaultt='0' where a.storeNo = :storeNo "
	//			+ " and (a.inactiveDate is null or a.inactiveDate > sysdate)")
	//	public void setAllInactive(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 获取默认地址或非默认地址
	//	 * @param storeNo
	//	 * @param defaultt
	//	 * @return
	//	 */
	//	List<PoStoreAddress> findByStoreNoAndDefaultt(String storeNo, boolean defaultt);
	//
	//	/**
	//	 * 获取专卖店默认地址
	//	 * @param storeNo
	//	 * @param defaultt
	//	 * @return
	//	 */
	//	@Query("from PoStoreAddress a where a.storeNo = :storeNo and a.defaultt = true")
	//	PoStoreAddress getByDefaultAddr(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 根据专卖店卡号获得有效的专卖店配送地址
	//	 * 
	//	 * @param dealerNo
	//	 * @param isEnabled
	//	 * @return
	//	 */
	//	List<PoStoreAddress> findByStoreNoAndInactiveDateIsNull(String storeNo);
	//
	//	/**
	//	 * 根据区域第四级地址获得专卖店配送地址
	//	 * 
	//	 * @param addrAreaCode
	//	 * @param isEnabled
	//	 * @return
	//	 */
	//	List<PoStoreAddress> findByAddrAreaCodeAndInactiveDateIsNull(String addrAreaCode);
	//
	//	/**
	//	 * 根据店铺编号获取areaCode
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select addrAreaCode,addrDetail from PoStoreAddress where storeNo=:storeNo")
	//	List<?> findAreaCode(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 根据专卖店卡号列表获得有效的专卖店配送地址
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("from PoStoreAddress t where t.storeNo=:storeNo and t.effectiveDate <= trunc(sysdate)"
	//			+ " and (t.inactiveDate is null or t.inactiveDate > trunc(sysdate))"
	//			+ " and t.storeNo in (select d.storeNo from DealerStoreStatus d where d.storeRunStatus in ('01','02','03')) order by t.defaultt desc")
	//	List<PoStoreAddress> findByStoreNoAndIsEnabledAndEffectiveDateIsValidInactiveDateIsValid(@Param("storeNo") String storeNo);
	//
	//	//	@Query("from PoStoreAddress t where t.storeNo=:storeNo and t.effectiveDate <= trunc(sysdate)" + " and t.addrAreaCode=:addrAreaCode"
	//	//			+ " and (t.inactiveDate is null or t.inactiveDate > trunc(sysdate))"
	//	//			+ " and t.storeNo in (select d.storeNo from DealerStoreStatus d where d.storeRunStatus in ('01','02','03'))"
	//	//			+ " order by t.defaultt desc ")
	//	//	List<PoStoreAddress> findPoStoreAddressInfo(@Param("storeNo") String storeNo, @Param("addrAreaCode") String addrAreaCode);
	//
	//	/**
	//	 * 查询上级地址信息
	//	 * 
	//	 * @param areaCode
	//	 * @return
	//	 */
	//	@Query("select a.areaDesc,a.parentAreaCode from PoAreaInfo a where a.areaCode=:areaCode")
	//	List<?> findParentAddr(@Param("areaCode") String areaCode);
	//
	//	/**
	//	 * 获取未生效地址
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("select a from PoStoreAddress a where (to_char(a.effectiveDate-1, 'yyyymmdd') ||:deliveryCloseTime) >"
	//			+ " to_char(sysdate, 'yyyymmddhh24:mi') and a.storeNo = :storeNo")
	//	List<PoStoreAddress> getUnEffectiveAddress(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
	//
	//	/**
	//	 * 获取已生效地址
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select a from PoStoreAddress a where (to_char(a.effectiveDate-1, 'yyyymmdd') ||:deliveryCloseTime) <="
	//			+ " to_char(sysdate, 'yyyymmddhh24:mi') and (a.inactiveDate is null or (to_char(a.inactiveDate-1, 'yyyymmdd')"
	//			+ " ||:deliveryCloseTime) > to_char(sysdate, 'yyyymmddhh24:mi')) and a.storeNo = :storeNo")
	//	List<PoStoreAddress> getEffectiveAddress(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
	//
	//	/**
	//	 * 
	//	 * 获取已失效地址
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select a from PoStoreAddress a where a.storeNo = :storeNo and a.effectiveDate = (select"
	//			+ " max(b.effectiveDate) from PoStoreAddress b where b.storeNo = :storeNo and (to_char("
	//			+ "b.inactiveDate-1, 'yyyymmdd') ||:deliveryCloseTime) <= to_char(sysdate, 'yyyymmddhh24:mi'))")
	//	List<PoStoreAddress> getInactiveDateAddress(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
	//
	//	@Query("from PoStoreAddress  a " + "where a.storeNo = :dealerNo " + "and a.effectiveDate <= sysdate "
	//			+ "and (a.inactiveDate is null or a.inactiveDate > sysdate) and exists (select s.saleZoneNo from SaleZoneAreaInfo s "
	//			+ "where s.saleZoneAreaCode in (select p.areaCode from PoAreaInfo p where p.effectiveDate <= sysdate "
	//			+ "and (p.inactiveDate is null or sysdate < p.inactiveDate)))")
	//	public List<PoStoreAddress> getPoStoreAddressByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 *
	//	 * @param storeNo
	//	 * @param addrAreaCode
	//	 * @param addrSendId
	//	 * @return
	//	 */
	//	@Query("from PoStoreAddress where storeNo=:storeNo and addrAreaCode=:addrAreaCode and addrSendId=:addrSendId")
	//	public List<PoStoreAddress> queryPoStoreAddressList(@Param("storeNo") String storeNo, @Param("addrAreaCode") String addrAreaCode,
	//			@Param("addrSendId") String addrSendId);
	//
	//	/**
	//	 * 获取parent_id为0的记录数
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	//@Query("select a from PoStoreAddress a where a.parentId = 0 and a.storeNo = :storeNo and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	//	@Query("select a from PoStoreAddress a where a.parentId = 0 and a.storeNo = :storeNo and (to_char(a.effectiveDate-1,"
	//			+ " 'yyyymmdd') ||:deliveryCloseTime) <= to_char(sysdate, 'yyyymmddhh24:mi') and (a.inactiveDate is null or"
	//			+ " (to_char(a.inactiveDate-1, 'yyyymmdd') ||:deliveryCloseTime) > to_char(sysdate, 'yyyymmddhh24:mi'))")
	//	public List<PoStoreAddress> getFirstPoStoreAddresses(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
	//
	//	/**
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query(" from PoStoreAddress a where a.storeNo=:storeNo and (a.inactiveDate is null or a.inactiveDate > sysdate)")
	//	List<PoStoreAddress> findByStoreNo(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 获取parent_id为0的记录数
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	//@Query("select a from PoStoreAddress a where a.parentId = 0 and a.storeNo = :storeNo and a.effectiveDate <= trunc(sysdate) and (a.inactiveDate is null or a.inactiveDate > trunc(sysdate))")
	//	@Query("select a from PoStoreAddress a where a.storeNo = :storeNo and (to_char(a.effectiveDate-1,"
	//			+ " 'yyyymmdd') ||:deliveryCloseTime) <= to_char(sysdate, 'yyyymmddhh24:mi') and (a.inactiveDate is null or"
	//			+ " (to_char(a.inactiveDate-1, 'yyyymmdd') ||:deliveryCloseTime) > to_char(sysdate, 'yyyymmddhh24:mi')) and defaultt='1'")
	//	public List<PoStoreAddress> getPoStoreAddresses(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
	//
	//	/**
	//	 * @param deliveryDealerNo
	//	 * @param addrSendId
	//	 * @return
	//		 */
	public PoStoreAddress findByStoreNoAndAddrSendId(String storeNo, String addrSendId);
	//
	//	/**
	//	 * 获取生效或即将生效地址
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select a from PoStoreAddress a where  (a.inactiveDate is null or (to_char(a.inactiveDate-1, 'yyyymmdd')"
	//			+ " ||:deliveryCloseTime) > to_char(sysdate, 'yyyymmddhh24:mi')) and a.defaultt = 1 and a.storeNo = :storeNo"
	//			+ " and to_char(sysdate, 'yyyymmddhh24:mi') > (to_char(a.effectiveDate-1, 'yyyymmdd')||:deliveryCloseTime)")
	//	List<PoStoreAddress> getNextEffectiveAddress(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
	//
	//	/**
	//	 * 获取还未生效或已生效地址
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 * add by 业务统一平台 BUP-12525 复核通过后配送信息出现【订货生效时间】
	//	 */
	//	@Query("select a from PoStoreAddress a where  (a.inactiveDate is null or (to_char(a.inactiveDate-1, 'yyyymmdd')"
	//			+ " ||:deliveryCloseTime) > to_char(sysdate, 'yyyymmddhh24:mi')) and a.defaultt = 1 and a.storeNo = :storeNo")
	//	List<PoStoreAddress> getAddEffectiveAddress(@Param("storeNo") String storeNo, @Param("deliveryCloseTime") String deliveryCloseTime);
}

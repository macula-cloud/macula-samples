/**
 * PosReplenishDetailRepository.java 2012-4-25
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.gbss.domain.PosReplenishDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>PosReplenishDetailRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface PosReplenishDetailRepository extends JpaRepository<PosReplenishDetail, Long> {

	/**
	 * 据申请单号查询所有明细记录
	 * @param appNo
	 * @return
	 */
	List<PosReplenishDetail> findByAppNo(String appNo);

	/**
	 * 获取某状态下某转储单中的某产品的数量
	 * @param posWhCode
	 * @param appTranStatus
	 * @param productCode
	 * @return
	 */
	@Query("select sum(b.appQty) from PosReplenishMaster a, PosReplenishDetail b where a.appNo = b.appNo and a.appTranType = '01' and a.posWhCode = :posWhCode and a.appTranStatus = :appTranStatus and b.productCode = :productCode")
	Long getStatusQty(@Param("posWhCode") String posWhCode, @Param("appTranStatus") String appTranStatus, @Param("productCode") String productCode);

	/**
	 * GBSQLYH-135
	 * 获取某状态下某转储单中的某产品的数量
	 * @param posWhCode
	 * @param appTranStatus
	 * @param productCode
	 * @return
	 */
	@Query("select sum(b.appQty) from PosReplenishMaster a, PosReplenishDetail b where a.appNo = b.appNo and a.appTranType = '01' and a.posStoreNo = :posStoreNo and a.appTranStatus = :appTranStatus and b.productCode = :productCode and a.appDate >=add_months(sysdate,-12*3)")
	Long getStatusQtyGbSqlYh(@Param("posStoreNo") String posStoreNo, @Param("appTranStatus") String appTranStatus,
			@Param("productCode") String productCode);

	/**
	 * 获取某个POS自营店某个产品的退仓转储单某一状态下（未审核，已审核）的总数量
	 * @param posStoreNo
	 * @param productCode
	 * @return
	 */
	@Query("select nvl(sum(b.appQty),0) from PosReplenishMaster a, PosReplenishDetail b where a.appNo = b.appNo and a.appTranType = '02' and a.appTranStatus = :appTranStatus and a.posStoreNo = :posStoreNo and b.productCode = :productCode")
	Long getPosReplenishSumQty(@Param("posStoreNo") String posStoreNo, @Param("productCode") String productCode,
			@Param("appTranStatus") String appTranStatus);

	/**
	 * 获取某个POS自营店某库区某个产品的退仓转储单某一状态下（未审核，已审核）的总数量
	 * @param posStoreNo
	 * @param productCode
	 * @return
	 */
	@Query("select nvl(sum(b.appQty),0) from PosReplenishMaster a, PosReplenishDetail b where a.appNo = b.appNo and a.comments = :locCode and a.appTranType = '02' and a.appTranStatus = :appTranStatus and a.posStoreNo = :posStoreNo and b.productCode = :productCode")
	Long getPosReplenishSumQtyByLocCode(@Param("posStoreNo") String posStoreNo, @Param("locCode") String locCode,
			@Param("productCode") String productCode, @Param("appTranStatus") String appTranStatus);

	/**
	 * add by zhouhr GBSS-2372
	 * 获取上个月销售量
	 * @param poSDate
	 * @param poEDate
	 * @param poStoreNo
	 * @param productType
	 * @return
	 * */
	@Query("select sum(sd.saleQty) from PoMaster so,PoDetail sd "
			+ "where so.poNo = sd.poNo and so.poPeriod = to_char(ADD_MONTHS(sysdate,-1),'yyyymm')  " + "and so.poStoreNo in (:poStoreNo) "
			+ " and sd.productCode=:productCode " + "group by so.poStoreNo,sd.productCode ")
	Long getPoNumLastMonth(@Param("poStoreNo") String poStoreNo, @Param("productCode") String productCode);

	/**
	 * 查询审核日志
	 * @param appNo
	 * @return
	 */
	@Query("from PosReplenishDetail where appNo=:appNo and logContent is not null")
	List<PosReplenishDetail> queryLogContent(@Param("appNo") String appNo);

}

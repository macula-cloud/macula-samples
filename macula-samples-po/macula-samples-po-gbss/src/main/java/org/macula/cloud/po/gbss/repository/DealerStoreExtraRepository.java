/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.DealerStoreExtra;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DealerStoreExtraRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface DealerStoreExtraRepository extends JpaRepository<DealerStoreExtra, Long> {

	public DealerStoreExtra findByStoreNo(String storeNo);

	//	/**
	//	 * 查询有效店铺附属信息
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" select dse from DealerStoreExtra dse, DealerStoreStatus dss, DealerStore ds " + " where dss.storeNo = ds.storeNo "
	//			+ "   and dse.storeNo = ds.storeNo " + "   and dss.storeRunType = ds.storeRunType"
	//			+ "   and (dss.inactiveDate is null or dss.inactiveDate > sysdate)" + "   and ds.dealerNo = :dealerNo "
	//			+ " order by dss.effectiveDate desc ")
	//	public List<DealerStoreExtra> getDealerNoBeforeJuly(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 同步店铺经度纬度
	//	 */
	//	@Modifying
	//	@Query("update DealerStoreExtra  set locationX=:locationX,locationY=:locationY" + " where storeNo=:storeNo")
	//	public void updateDealerStoreExtraByStoreNo(@Param("locationX") BigDecimal locationX, @Param("locationY") BigDecimal locationY,
	//			@Param("storeNo") String storeNo);
}

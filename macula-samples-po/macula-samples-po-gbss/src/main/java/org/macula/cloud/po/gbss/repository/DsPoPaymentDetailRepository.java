/**
 * DsPoPaymentDetailRepository.java 2011-8-24
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsPoPaymentDetailRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface DsPoPaymentDetailRepository extends JpaRepository<PoPaymentDetail, Long> {

	//	@Query("select a from PoPaymentDetail a where poNo =:poNo")
	//	List<PoPaymentDetail> getBindPoPaymentDetail(@Param("poNo") String poNo);

	/**
	 * 根据交易单号获取订货支付信息
	 * 
	 * @param poNo
	 * @return
	 */
	List<PoPaymentDetail> findByPoNo(String poNo);

	//	List<PoPaymentDetail> findByPoNoAndAccTranType(String poNo, String accTranType);
	//
	//	/**
	//	 * 
	//	 * @param poNo
	//	 */
	//	@Modifying
	//	@Query(value = "delete from cbs.so_payment_detail b where b.so_no = :poNo", nativeQuery = true)
	//	void deleteSoPaymentDetail(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 根据单号和支付类型获取支付详情
	//	 * @param poNo
	//	 * @param poPaymentTypes
	//	 * @return
	//	 */
	//	@Query("from PoPaymentDetail p where p.poNo = :poNo and p.poPaymentType in (:poPaymentTypes)")
	//	List<PoPaymentDetail> findByPoNoAndPoPaymentType(@Param("poNo") String poNo, @Param("poPaymentTypes") List<String> poPaymentTypes);
}

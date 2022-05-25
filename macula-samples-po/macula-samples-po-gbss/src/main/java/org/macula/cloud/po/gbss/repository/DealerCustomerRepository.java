/**
 * DealerCustomerRepository.java 2019年5月15日
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.DealerCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DealerCustomerRepository</b> 是普通顾客基本信息DealerCustomer表对应的repository接口
 * </p>
 *
 
 
 
 */
public interface DealerCustomerRepository extends JpaRepository<DealerCustomer, Long> {

	//	/**
	//	 * 根据手机号查询有效普消资料
	//	 * @param mobile
	//	 * @param status
	//	 * @return
	//	 */
	//	@Query("from DealerCustomer d where d.status = 'NORMAL' and d.mobile = :mobile")
	//	List<DealerCustomer> getValidCustomerByMobile(@Param("mobile") String mobile);

	/**
	 * 根据普消卡号查询普消资料
	 * @param customerNo
	 * @param status
	 * @return
	 */
	DealerCustomer findByCustomerNo(String customerNo);

	//	/**
	//	 * 根据普消卡号查询有效普消资料
	//	 * @param customerNo
	//	 * @param status
	//	 * @return
	//	 */
	//	@Query("from DealerCustomer d where d.status = 'NORMAL' and d.customerNo = :customerNo")
	//	DealerCustomer getValidCustomerByNo(@Param("customerNo") String customerNo);
	//
	//	/**
	//	 * 根据会员号查询普消资料
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("from DealerCustomer d where d.dealerNo = :dealerNo")
	//	List<DealerCustomer> getCustomerByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	@Query("from DealerCustomer d where d.customerNo in :customerNos")
	//	List<DealerCustomer> listByCustomerNo(@Param("customerNos") List<String> customerNos);
	//
	//	/**
	//	 * 根据手机号码查询普消卡号
	//	 * @param mobile
	//	 * @return
	//	 */
	//	@Query("select d.customerNo from DealerCustomer d where d.status = 'NORMAL' and d.mobile = :mobile")
	//	List<String> listCustomerNoByMobile(@Param("mobile") String mobile);
}

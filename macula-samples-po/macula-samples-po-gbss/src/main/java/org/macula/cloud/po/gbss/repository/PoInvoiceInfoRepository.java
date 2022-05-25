package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 
 *
 */
public interface PoInvoiceInfoRepository extends JpaRepository<PoInvoiceInfo, Long> {

	//	@Query("from PoInvoiceInfo p where p.id = (select max(t.id) from PoInvoiceInfo t where t.dealerNo=:dealerNo)")
	//	public PoInvoiceInfo getLastPoInvoiceInfo(@Param("dealerNo") String dealerNo);
	//
	//	@Query(value = "select invoice_title from (select distinct invoice_title from PO_INVOICE_INFO t where dealer_no=:dealerNo and t.created_time > add_months(sysdate,-12) order by invoice_title ) where rownum < 11", nativeQuery = true)
	//	public List<String> getTitleHistory(@Param("dealerNo") String dealerNo);

	public PoInvoiceInfo findByPoAppNo(String poAppNo);

}

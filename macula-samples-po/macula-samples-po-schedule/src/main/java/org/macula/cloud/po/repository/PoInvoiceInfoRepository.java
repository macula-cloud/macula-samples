package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoInvoiceInfoRepository extends JpaRepository<PoInvoiceInfo, Long> {

	public PoInvoiceInfo findByPoAppNo(String poAppNo);

}

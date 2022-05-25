package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoInvoiceInfoRepository extends JpaRepository<PoInvoiceInfo, Long> {
	List<PoInvoiceInfo> findByPoAppNo(String poAppNo);
}

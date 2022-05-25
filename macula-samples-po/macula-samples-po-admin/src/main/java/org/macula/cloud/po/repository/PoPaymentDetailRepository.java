package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoPaymentDetailRepository extends JpaRepository<PoPaymentDetail, Long> {
	List<PoPaymentDetail> findByPoNo(String poNo);
}

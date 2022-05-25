package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoDetailDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoDetailDiscountRepository extends JpaRepository<PoDetailDiscount, Long> {
	List<PoDetailDiscount> findByPoNo(String poNo);
}

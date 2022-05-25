package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MkpDlpPoRelateRepository extends JpaRepository<MkpDlpPoRelate, Long> {
	List<MkpDlpPoRelate> findByPoNo(String poNo);
}

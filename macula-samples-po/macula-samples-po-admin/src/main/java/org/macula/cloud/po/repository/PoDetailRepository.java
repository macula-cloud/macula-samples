package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoDetailRepository extends JpaRepository<PoDetail, Long> {

	List<PoDetail> findByPoNo(String poNo);

}

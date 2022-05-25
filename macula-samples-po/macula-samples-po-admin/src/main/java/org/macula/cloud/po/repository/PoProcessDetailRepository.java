package org.macula.cloud.po.repository;


import java.util.List;

import org.macula.cloud.po.domain.PoProcessDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoProcessDetailRepository extends JpaRepository<PoProcessDetail, Long> {

    List<PoProcessDetail> findByPoNo(String poNo);
}

package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.SapDailyUplPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SapDailyUplPoRepository extends JpaRepository<SapDailyUplPo, Long> {

	boolean existsByPoNo(String poNo);

}

package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.PoMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoMaster2Repository extends JpaRepository<PoMaster, Long> {

	PoMaster findByPoNo(String poNo);

}

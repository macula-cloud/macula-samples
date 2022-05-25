package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.PoProcessMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PoProcessMasterRepository extends JpaRepository<PoProcessMaster, Long>, JpaSpecificationExecutor<PoProcessMaster> {

	PoProcessMaster findByPoNo(String poNo);

}

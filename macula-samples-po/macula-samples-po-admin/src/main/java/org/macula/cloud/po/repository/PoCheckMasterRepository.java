package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.PoCheckMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * OMS和业务平台GBSS对账数据表
 */
public interface PoCheckMasterRepository extends JpaRepository<PoCheckMaster, Long>, JpaSpecificationExecutor<PoCheckMaster> {

}

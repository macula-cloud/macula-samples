package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OMS和业务平台GBSS对账数据明细
 */
public interface PoCheckDetailRepository extends JpaRepository<PoCheckDetail, Long> {

	List<PoCheckDetail> findByCheckMasterId(Long id);
}

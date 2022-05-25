package org.macula.cloud.logistics.repository;

import org.macula.cloud.logistics.domain.Gbss2Cainiao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Gbss2CainiaoRepository extends JpaRepository<Gbss2Cainiao, Long> {

	@Query("from Gbss2Cainiao a where (a.batchNo is null or a.batchNo <> :batchNo) and (a.status <> '0' or a.status is null) ")
	Page<Gbss2Cainiao> findUnloadMappings(Pageable pageRequest, @Param("batchNo") String updateBatchNo);
}
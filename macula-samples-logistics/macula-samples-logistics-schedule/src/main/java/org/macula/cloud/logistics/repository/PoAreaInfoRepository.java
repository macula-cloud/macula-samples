package org.macula.cloud.logistics.repository;

import org.macula.cloud.logistics.domain.PoAreaInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PoAreaInfoRepository extends JpaRepository<PoAreaInfo, Long> {

	@Query("from PoAreaInfo a where (a.batchNo is null or a.batchNo <> :batchNo) or (a.status <> '0' or a.status is null) ")
	Page<PoAreaInfo> findUnloadMappings(Pageable pageRequest, @Param("batchNo") String batchNo);
}
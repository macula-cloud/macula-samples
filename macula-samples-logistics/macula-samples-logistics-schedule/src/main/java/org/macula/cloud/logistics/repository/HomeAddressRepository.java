package org.macula.cloud.logistics.repository;

import org.macula.cloud.logistics.domain.HomeAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeAddressRepository extends JpaRepository<HomeAddress, Long> {

	@Query("from HomeAddress a where a.provId is null and (a.status <> '0' or a.status is null) ")
	Page<HomeAddress> findUnloadMappings(Pageable pageRequest);
}

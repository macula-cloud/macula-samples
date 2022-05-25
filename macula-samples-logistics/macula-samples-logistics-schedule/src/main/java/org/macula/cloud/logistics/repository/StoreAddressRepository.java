package org.macula.cloud.logistics.repository;

import org.macula.cloud.logistics.domain.StoreAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreAddressRepository extends JpaRepository<StoreAddress, Long> {

	@Query("from StoreAddress a where a.provId is null and (a.status <> '0' or a.status is null)")
	Page<StoreAddress> findUnloadMappings(Pageable pageable);

}

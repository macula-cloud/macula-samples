package org.macula.cloud.fulfilment.repository;

import org.macula.cloud.fulfilment.domain.ZoneStockStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionStockStatusRepository extends JpaRepository<ZoneStockStatus, Long> {

}

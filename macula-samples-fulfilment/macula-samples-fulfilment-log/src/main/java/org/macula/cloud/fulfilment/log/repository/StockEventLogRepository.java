package org.macula.cloud.fulfilment.log.repository;

import org.macula.cloud.fulfilment.log.domain.StockEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockEventLogRepository extends JpaRepository<StockEventLog, String> {

}

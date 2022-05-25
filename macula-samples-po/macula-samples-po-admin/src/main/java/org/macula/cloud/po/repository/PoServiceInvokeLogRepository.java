package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.log.domain.ServiceInvokeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoServiceInvokeLogRepository extends JpaRepository<ServiceInvokeLog, Long> {
	List<ServiceInvokeLog> findByDataKey(String dataKey);
}

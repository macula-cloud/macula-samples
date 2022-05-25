package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.PoMessageLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MessageLevelRepository extends JpaRepository<PoMessageLevel, Long>, JpaSpecificationExecutor<PoMessageLevel> {
}

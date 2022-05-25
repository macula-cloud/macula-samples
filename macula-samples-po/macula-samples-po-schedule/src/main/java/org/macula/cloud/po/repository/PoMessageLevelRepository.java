package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoMessageLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoMessageLevelRepository extends JpaRepository<PoMessageLevel, Long> {

	/**
	 * 根据级别查询对应的数据
	 * @param level
	 * @return
	 */
	List<PoMessageLevel> findByLevel(String level);
}

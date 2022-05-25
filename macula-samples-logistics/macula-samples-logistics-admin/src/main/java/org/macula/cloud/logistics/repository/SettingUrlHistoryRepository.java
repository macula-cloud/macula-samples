package org.macula.cloud.logistics.repository;

import java.util.List;

import org.macula.cloud.logistics.domain.SettingUrlHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SettingUrlHistoryRepository extends JpaRepository<SettingUrlHistory, Long> {
	@Modifying
	@Query("update SettingUrlHistory a set a.latestFlag = ?1")
	void updateAllFlag(Boolean latestFlag);

	List<SettingUrlHistory> findByLatestFlag(boolean latestFlag);

}

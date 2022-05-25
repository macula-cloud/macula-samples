package org.macula.cloud.logistics.repository;

import java.util.List;

import org.macula.cloud.logistics.domain.AddressUrlHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressUrlHistoryRepository extends JpaRepository<AddressUrlHistory, Long> {

	@Modifying
	@Query("update AddressUrlHistory a set a.latestFlag = ?1")
	void updateAllFlag(boolean lastestFlag);

	List<AddressUrlHistory> findByLatestFlag(boolean lastestFlag);
}

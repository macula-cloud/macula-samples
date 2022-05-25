package org.macula.cloud.logistics.repository;

import javax.transaction.Transactional;

import org.macula.cloud.logistics.domain.AddressDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressDivisionRepository extends JpaRepository<AddressDivision, Long> {

	AddressDivision findByDivisionId(String divisionId);

	@Modifying
	@Transactional
	@Query("update AddressDivision a set a.deleted = true")
	void markAllDeletedForUpdate();

}

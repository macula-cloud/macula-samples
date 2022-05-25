package org.macula.cloud.logistics.repository;

import java.util.List;

import org.macula.cloud.logistics.domain.CnAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CnAddressRepository extends JpaRepository<CnAddress, Long> {

	CnAddress findByCode(String code);

	List<CnAddress> findByParentCode(String parentCode);
}

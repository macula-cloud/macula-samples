package org.macula.cloud.logistics.repository;

import org.macula.cloud.logistics.domain.AddressChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressChangeRepository extends JpaRepository<AddressChange, Long> {

}

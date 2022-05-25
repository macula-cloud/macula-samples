package org.macula.cloud.logistics.repository;

import org.macula.cloud.logistics.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long> {

}

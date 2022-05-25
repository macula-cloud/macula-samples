package org.macula.cloud.logistics.repository;

import java.util.List;

import org.macula.cloud.logistics.domain.StoreDcSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDcSettingRepository extends JpaRepository<StoreDcSetting, Long> {

	List<StoreDcSetting> findByAddressCode(String addressCode);

}

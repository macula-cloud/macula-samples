package org.macula.cloud.logistics.repository;

import java.util.List;

import org.macula.cloud.logistics.domain.HomeDcSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeDcSettingRepository extends JpaRepository<HomeDcSetting, Long> {

	List<HomeDcSetting> findByAddressCode(String addressCode);

}

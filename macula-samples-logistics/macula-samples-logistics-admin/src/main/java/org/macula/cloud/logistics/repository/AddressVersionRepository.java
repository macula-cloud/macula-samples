package org.macula.cloud.logistics.repository;

import java.util.List;

import org.macula.cloud.logistics.domain.AddressVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressVersionRepository extends JpaRepository<AddressVersion, Long> {

	@Query("from AddressVersion v where v.currentVersion = true")
	AddressVersion findCurrentAddressVersion();

	AddressVersion findByPublishVersion(String version);

	@Query("from AddressVersion v where (v.offset is null or v.offset = '')")
	List<AddressVersion> findUnloadChanges();

	@Modifying
	@Query("update AddressVersion v set v.currentVersion = (case v. publishVersion when ?1 then 1 else 0 end)")
	void updateCurrentAddressMark(String version);
}

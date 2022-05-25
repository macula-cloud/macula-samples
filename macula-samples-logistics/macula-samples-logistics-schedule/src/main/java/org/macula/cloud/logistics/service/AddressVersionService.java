package org.macula.cloud.logistics.service;

import java.util.List;

import javax.transaction.Transactional;

import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionVersionListRequest;
import org.macula.cloud.cainiao.DivisionVersionListResponse;
import org.macula.cloud.cainiao.DivisionVersionListResponse.DivisionVersionListData;
import org.macula.cloud.cainiao.VersionChangeListRequest;
import org.macula.cloud.cainiao.VersionChangeListResponse;
import org.macula.cloud.cainiao.VersionChangeListResponse.VersionChangeDto;
import org.macula.cloud.logistics.domain.AddressChange;
import org.macula.cloud.logistics.domain.AddressVersion;
import org.macula.cloud.logistics.repository.AddressChangeRepository;
import org.macula.cloud.logistics.repository.AddressVersionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AddressVersionService {

	private AddressVersionRepository versionRepository;
	private AddressChangeRepository changeRepository;
	private CainiaoLinkService service;

	@Transactional
	public void updateAddressVersion() {

		log.info("Start updateAddressVersion ...");

		String fromVer = "";
		String offset = null;
		String cpCode = "1";
		String bizCode = "1";

		String currentVersion = service.getConfig().getVersion();
		AddressVersion currentAddressVersion = versionRepository.findCurrentAddressVersion();
		if (currentAddressVersion != null) {
			fromVer = currentAddressVersion.getPublishVersion();
			offset = currentAddressVersion.getOffset();
			currentVersion = currentAddressVersion.getPublishVersion();
		} else {
			versionRepository.updateCurrentAddressMark(currentVersion);
		}

		DivisionVersionListResponse response = service.getDivisionVersionList(DivisionVersionListRequest.of(fromVer, offset, cpCode, bizCode));
		if (response.isSuccess()) {
			DivisionVersionListData versions = response.getData();
			versions.getVersionList().forEach(v -> {
				if (versionRepository.findByPublishVersion(v.getPublishVersion()) == null) {
					AddressVersion entity = new AddressVersion();
					BeanUtils.copyProperties(v, entity);
					versionRepository.save(entity);
				}
			});
		}

		List<AddressVersion> unloadVersions = versionRepository.findUnloadChanges();
		for (AddressVersion addressVersion : unloadVersions) {
			VersionChangeListResponse changes = service.getVersionChangeList(
					VersionChangeListRequest.of(addressVersion.getPublishVersion(), addressVersion.getOffset(), cpCode, bizCode));
			VersionChangeDto changeVo = changes.getData();
			addressVersion.setOffset(changeVo.getOffset());
			versionRepository.save(addressVersion);
			changeVo.getChangeList().forEach(t -> {
				AddressChange change = new AddressChange();
				BeanUtils.copyProperties(t, change);
				BeanUtils.copyProperties(changeVo, change);
				changeRepository.save(change);
			});
		}

		log.info("Complete updateAddressVersion");

	}
}

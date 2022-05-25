package org.macula.cloud.logistics.service;

import java.util.List;

import org.macula.cloud.logistics.domain.SettingUrlHistory;
import org.macula.cloud.logistics.repository.SettingUrlHistoryRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SettingUrlHistoryService {

	private SettingUrlHistoryRepository settingUrlHistoryRepository;

	public void updateAllFlag(Boolean latestFlag) {
		settingUrlHistoryRepository.updateAllFlag(latestFlag);
	}

	public void insertNewInfo(SettingUrlHistory settingUrlHistory) {
		settingUrlHistoryRepository.save(settingUrlHistory);
	}

	public SettingUrlHistory getLatestUrl() {
		List<SettingUrlHistory> histories = settingUrlHistoryRepository.findByLatestFlag(true);
		return histories.iterator().next();
	}
}

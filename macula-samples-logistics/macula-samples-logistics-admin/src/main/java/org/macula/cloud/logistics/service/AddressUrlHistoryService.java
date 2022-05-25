package org.macula.cloud.logistics.service;

import java.util.List;

import org.macula.cloud.logistics.domain.AddressUrlHistory;
import org.macula.cloud.logistics.repository.AddressUrlHistoryRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 
 
 */
@Service
@AllArgsConstructor
public class AddressUrlHistoryService {

	private AddressUrlHistoryRepository addressUrlHistoryRepository;

	public void updateAllFlag(Boolean latestFlag) {
		addressUrlHistoryRepository.updateAllFlag(latestFlag);
	}

	public void insertNewInfo(AddressUrlHistory addressUrlHistory) {
		addressUrlHistoryRepository.save(addressUrlHistory);
	}

	public AddressUrlHistory getLatestUrl() {
		List<AddressUrlHistory> histories = addressUrlHistoryRepository.findByLatestFlag(true);
		return histories.iterator().next();
	}
}
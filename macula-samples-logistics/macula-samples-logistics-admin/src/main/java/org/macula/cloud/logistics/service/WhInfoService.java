package org.macula.cloud.logistics.service;

import java.util.List;

import org.macula.cloud.logistics.domain.WhInfo;
import org.macula.cloud.logistics.repository.WhInfoRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WhInfoService {

	private WhInfoRepository whInfoRepository;

	public List<WhInfo> findWhInfos() {
		return whInfoRepository.findAll();
	}

	public List<WhInfo> updateWhInfo() {
		return null;
	}
}

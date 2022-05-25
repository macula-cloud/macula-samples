package org.macula.cloud.fulfilment.gbss.service;

import javax.annotation.PostConstruct;

import org.macula.cloud.fulfilment.feign.InventoryCenterClient;
import org.macula.cloud.fulfilment.flink.RdcStockStatusPageSink;
import org.macula.cloud.fulfilment.flink.RdcStockStatusPageSource;
import org.macula.cloud.fulfilment.gbss.domain.PoAppMaster;
import org.macula.cloud.fulfilment.gbss.domain.RdcExStockStatus;
import org.macula.cloud.fulfilment.gbss.domain.RdcStockStatus;
import org.macula.cloud.fulfilment.gbss.repository.PoAppMasterRepository;
import org.macula.cloud.fulfilment.gbss.repository.RdcExStockStatusRepository;
import org.macula.cloud.fulfilment.gbss.repository.RdcStockStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PoService {

	@Autowired
	private PoAppMasterRepository poRepository;

	@Autowired
	private RdcExStockStatusRepository rdcExStockStatusRepository;

	@Autowired
	private RdcStockStatusRepository rdcStockStatusRepository;

	@Autowired
	private InventoryCenterClient stockClient;

	@PostConstruct
	public void postConstruct() {
		RdcStockStatusPageSink.initializeBeans(rdcStockStatusRepository, stockClient);
		RdcStockStatusPageSource.initializeBeans(rdcStockStatusRepository);
	}

	public void initial() {
		Page<PoAppMaster> poAppMasterPage = poRepository.findAll(PageRequest.of(0, 100));
		for (PoAppMaster poAppMaster : poAppMasterPage) {
			log.info(JSONArray.toJSONString(poAppMaster));
		}

		Page<RdcStockStatus> rdcStockStatusPage = rdcStockStatusRepository.findAll(PageRequest.of(0, 100));
		for (RdcStockStatus rdcStockStatus : rdcStockStatusPage) {
			log.info(JSONArray.toJSONString(rdcStockStatus));
		}

		Page<RdcExStockStatus> rdcExStockStatusPage = rdcExStockStatusRepository.findAll(PageRequest.of(0, 100));
		for (RdcExStockStatus rdcExStockStatus : rdcExStockStatusPage) {
			log.info(JSONArray.toJSONString(rdcExStockStatus));
		}
	}

}

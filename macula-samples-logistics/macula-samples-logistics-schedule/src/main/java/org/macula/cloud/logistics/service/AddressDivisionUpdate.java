package org.macula.cloud.logistics.service;

import java.util.LinkedList;
import java.util.List;

import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionResponse;
import org.macula.cloud.cainiao.DivisionVO;
import org.macula.cloud.cainiao.DivisionsRequest;
import org.macula.cloud.cainiao.SubDivisionsRequest;
import org.macula.cloud.cainiao.SubDivisionsResponse;
import org.macula.cloud.logistics.domain.AddressDivision;
import org.macula.cloud.logistics.repository.AddressDivisionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AddressDivisionUpdate {

	@Autowired
	private CainiaoLinkService linkService;

	@Autowired
	private AddressDivisionRepository addressRepository;

	// @Scheduled(initialDelay = 1000L, fixedDelay = Long.MAX_VALUE)
	public void syncAddress() {
		long start = System.currentTimeMillis();
		List<DivisionVO> all = new LinkedList<DivisionVO>();
		DivisionResponse response = linkService.getChinaDivision(DivisionsRequest.of("1"));
		DivisionVO vo = response.getChinaDivisionVO();
		all.add(vo);
		AddressDivision addr = new AddressDivision();
		BeanUtils.copyProperties(vo, addr);
		addressRepository.save(addr);
		collectSubDivisions(vo.getDivisionId(), all);
		long end = System.currentTimeMillis();
		log.info("Count: {}  used: {} ms", all.size(), (end - start));
		log.info(JSONObject.toJSONString(all, true));
	}

	private void collectSubDivisions(String parentCode, List<DivisionVO> collect) {
		SubDivisionsResponse response = linkService.getChinaSubDivisions(SubDivisionsRequest.of(parentCode));
		if (response.isSuccess()) {
			for (DivisionVO vo : response.getDivisionsList()) {
				AddressDivision addr = new AddressDivision();
				BeanUtils.copyProperties(vo, addr);
				addressRepository.save(addr);
				collect.add(vo);
				if (vo.getDivisionLevel() < 5) {
					collectSubDivisions(vo.getDivisionId(), collect);
				}
			}
		}
	}
}

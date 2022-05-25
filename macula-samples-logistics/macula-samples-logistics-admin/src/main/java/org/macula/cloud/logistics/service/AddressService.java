package org.macula.cloud.logistics.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.transaction.Transactional;

import org.macula.cloud.cainiao.CainiaoLinkService;
import org.macula.cloud.cainiao.DivisionResponse;
import org.macula.cloud.cainiao.DivisionVO;
import org.macula.cloud.cainiao.DivisionsRequest;
import org.macula.cloud.cainiao.SubDivisionsRequest;
import org.macula.cloud.cainiao.SubDivisionsResponse;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.logistics.configure.LogisticsConfig;
import org.macula.cloud.logistics.domain.AddressDivision;
import org.macula.cloud.logistics.domain.CnAddress;
import org.macula.cloud.logistics.repository.AddressDivisionRepository;
import org.macula.cloud.logistics.repository.CnAddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AddressService {

	private CainiaoLinkService cainiaoLinkService;
	private CnAddressRepository addressRepository;
	private AddressDivisionRepository addressDivisionRepository;
	private SchedulingTaskExecutor schedulingTaskExecutor;

	private LogisticsConfig config;

	@Transactional
	public CnAddress updateAddress(DivisionVO vo, CnAddress parentAddress) {
		CnAddress address = addressRepository.findByCode(vo.getDivisionId());
		if (address == null) {
			address = new CnAddress();
			address.setLeafFlag(false);
			address.setShowFlag(true);
			address.setDowntownFlag(false);
			address.setEffectiveDate(config.getInitialEffectiveDate());
			address.setInactiveDate(config.getInitialInactiveDate());
		}
		address.setAddressType(vo.getDivisionLevel());
		address.setCode(vo.getDivisionId());
		address.setName(vo.getDivisionName());
		Boolean deletedFlag = vo.isDeleted();
		address.setDeletedFlag(deletedFlag);
		if (deletedFlag) {
			address.setInactiveDate(SystemUtils.getTomorrowZeropoint());
		}

		if (parentAddress != null) {
			address.setAddress(parentAddress.getAddress() + '-' + vo.getDivisionName());
			address.setParentCode(parentAddress.getCode());
			address = updateRelationValues(address, parentAddress);
		} else {
			address.setParentCode(null);
			address.setAddress(vo.getDivisionName());
		}
		return addressRepository.save(address);
	}

	@Transactional
	public void syncAddress() {
		long start = System.currentTimeMillis();
		List<DivisionVO> all = new LinkedList<>();
		DivisionResponse response = cainiaoLinkService.getChinaDivision(DivisionsRequest.of("1"));
		DivisionVO vo = response.getChinaDivisionVO();
		all.add(vo);
		final CnAddress address = updateAddress(vo, null);
		doAsyncCollectSubDivisions(address, all);
		updateAddressDivisions(all);
		long end = System.currentTimeMillis();
		log.debug("Count: {}  used: {} ms", all.size(), (end - start));
	}

	private void doAsyncCollectSubDivisions(CnAddress parentAddress, List<DivisionVO> collect) {
		Future<?> task = schedulingTaskExecutor.submit(() -> {
			this.collectSubDivisions(parentAddress, collect);
		});
		try {
			task.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("doAsyncCollectSubDivisions error:", e);
		}
	}

	private CnAddress updateRelationValues(CnAddress address, CnAddress parentAddress) {
		Integer level = address.getAddressType();
		address.setTown(parentAddress.getTown());
		address.setTownId(parentAddress.getTownId());
		address.setDistrict(parentAddress.getDistrict());
		address.setDistrictId(parentAddress.getDistrictId());
		address.setCity(parentAddress.getCity());
		address.setCityId(parentAddress.getCityId());
		address.setProv(parentAddress.getProv());
		address.setProvId(parentAddress.getProvId());
		if (level == 5) {
			address.setTown(address.getName());
			address.setTownId(address.getCode());
		} else if (level == 4) {
			address.setDistrict(address.getName());
			address.setDistrictId(address.getCode());
		} else if (level == 3) {
			address.setCity(address.getName());
			address.setCityId(address.getCode());
		} else if (level == 2) {
			address.setProv(address.getName());
			address.setProvId(address.getCode());
		}
		return address;
	}

	private void collectSubDivisions(CnAddress parentAddress, List<DivisionVO> collect) {
		SubDivisionsResponse response = cainiaoLinkService.getChinaSubDivisions(SubDivisionsRequest.of(parentAddress.getCode()));
		if (response.isSuccess()) {
			if (ObjectUtils.isEmpty(response.getDivisionsList())) {
				updateLeftFlag(parentAddress.getId());
			}
			for (DivisionVO vo : response.getDivisionsList()) {
				if (config.getIgnoreCodes().contains(vo.getDivisionId())) {
					continue;
				}
				CnAddress address = updateAddress(vo, parentAddress);
				collect.add(vo);
				if (vo.getDivisionLevel() == 5l) {
					updateLeftFlag(address.getId());
				} else {
					doAsyncCollectSubDivisions(address, collect);
				}
			}
		}
		if (ObjectUtils.isEmpty(response.getDivisionsList())) {
			updateLeftFlag(parentAddress.getId());
		}
	}

	private void updateLeftFlag(Long id) {
		Optional<CnAddress> address = addressRepository.findById(id);
		if (address != null && address.isPresent()) {
			CnAddress entity = address.get();
			entity.setLeafFlag(true);
			addressRepository.save(entity);
		}
	}

	private void updateAddressDivisions(List<DivisionVO> divisions) {
		addressDivisionRepository.markAllDeletedForUpdate();
		for (DivisionVO vo : divisions) {
			AddressDivision addressDivision = addressDivisionRepository.findByDivisionId(vo.getDivisionId());
			if (addressDivision == null) {
				addressDivision = new AddressDivision();
			}
			BeanUtils.copyProperties(vo, addressDivision);
			addressDivisionRepository.save(addressDivision);
		}
	}
}

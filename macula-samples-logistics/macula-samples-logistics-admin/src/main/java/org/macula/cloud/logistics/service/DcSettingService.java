package org.macula.cloud.logistics.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.macula.cloud.logistics.domain.CnAddress;
import org.macula.cloud.logistics.domain.HomeDcSetting;
import org.macula.cloud.logistics.domain.SettingUrlHistory;
import org.macula.cloud.logistics.domain.StoreDcSetting;
import org.macula.cloud.logistics.feign.FileFeignClient;
import org.macula.cloud.logistics.repository.CnAddressRepository;
import org.macula.cloud.logistics.repository.HomeDcSettingRepository;
import org.macula.cloud.logistics.repository.StoreDcSettingRepository;
import org.macula.cloud.logistics.vo.HomeDcSettingExcelDTO;
import org.macula.cloud.logistics.vo.StoreDcSettingExcelDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;

import io.choerodon.core.excel.ExcelReadConfig;
import io.choerodon.core.excel.ExcelReadHelper;
import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

/**
 
 
 */
@Service
@Slf4j
public class DcSettingService {

	private HomeDcSettingRepository homeDcSettingRepository;
	private StoreDcSettingRepository storeDcSettingRepository;
	private CnAddressRepository cnAddressRepository;
	private SettingUrlHistoryService settingUrlHistoryService;
	private FileFeignClient fileFeignClient;

	private static final String HOME_SHEET = "家居配置";
	private static final String STORE_SHEET = "店铺配置";
	private static final String README_SHEET = "README";
	private static final String EXPORT_FILE_NAME = "导出配置";
	private static final String SUFFIX = ".xls";

	private static final String INPROCESS = "生效中";
	private static final String INACTIVE = "已失效";
	private static final String PLANNING = "计划中";

	@Transactional(rollbackFor = Exception.class)
	public void importHomeAndStoreSetting(MultipartFile file) {
		Map<String, String> importMap = getImportMap();
		String[] homeSkipSheet = { README_SHEET, STORE_SHEET };
		String[] storeSkipSheet = { README_SHEET, HOME_SHEET };
		ExcelReadConfig homeExcelReadConfig = new ExcelReadConfig();
		ExcelReadConfig storeExcelReadConfig = new ExcelReadConfig();
		homeExcelReadConfig.setPropertyMap(importMap);
		homeExcelReadConfig.setSkipSheetNames(homeSkipSheet);
		storeExcelReadConfig.setPropertyMap(importMap);
		storeExcelReadConfig.setSkipSheetNames(storeSkipSheet);
		importHomeSetting(file, homeExcelReadConfig);
		importStoreSetting(file, storeExcelReadConfig);
	}

	public void exportDCSettingExcel() {
		settingUrlHistoryService.updateAllFlag(false);
		List<HomeDcSetting> homeOriginList = homeDcSettingRepository.findAll();
		List<HomeDcSettingExcelDTO> homeList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(homeOriginList)) {
			for (HomeDcSetting settingDTO : homeOriginList) {
				HomeDcSettingExcelDTO excelDTO = new HomeDcSettingExcelDTO();
				BeanUtils.copyProperties(settingDTO, excelDTO);
				homeList.add(excelDTO);
			}
		}
		List<StoreDcSettingExcelDTO> storeList = new ArrayList<>();
		List<StoreDcSetting> storeOriginList = storeDcSettingRepository.findAll();
		if (!CollectionUtils.isEmpty(storeOriginList)) {
			for (StoreDcSetting settingDTO : storeOriginList) {
				StoreDcSettingExcelDTO excelDTO = new StoreDcSettingExcelDTO();
				BeanUtils.copyProperties(settingDTO, excelDTO);
				storeList.add(excelDTO);
			}
		}
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			ExcelWriter excelWriter = new ExcelWriterBuilder().file(os).excelType(ExcelTypeEnum.XLS).build();
			WriteSheet writeSheet1 = new WriteSheet();
			writeSheet1.setSheetNo(1);
			writeSheet1.setClazz(HomeDcSettingExcelDTO.class);
			writeSheet1.setSheetName("家居配置");
			WriteSheet writeSheet2 = new WriteSheet();
			writeSheet2.setSheetNo(2);
			writeSheet2.setClazz(StoreDcSettingExcelDTO.class);
			writeSheet2.setSheetName("店铺配置");
			excelWriter.write(homeList, writeSheet1);
			excelWriter.write(storeList, writeSheet2);
			excelWriter.finish();
			String originalFilename = EXPORT_FILE_NAME + System.currentTimeMillis() + SUFFIX;
			MultipartFile multipartFile = new MockMultipartFile("file", originalFilename, "application/vnd.ms-excel", os.toByteArray());
			String url = fileFeignClient.uploadFile("logistics-service", multipartFile.getOriginalFilename(), multipartFile).getBody();
			SettingUrlHistory settingUrlHistoryDTO = new SettingUrlHistory();
			settingUrlHistoryDTO.setExcelUrl(url);
			settingUrlHistoryDTO.setLatestFlag(true);
			settingUrlHistoryService.insertNewInfo(settingUrlHistoryDTO);
		} catch (IOException e) {
		}
	}

	private void importStoreSetting(MultipartFile file, ExcelReadConfig storeExcelReadConfig) {
		try {
			List<StoreDcSetting> excelList = ExcelReadHelper.read(file, StoreDcSetting.class, storeExcelReadConfig);
			//校验
			this.checkStoreSetting(excelList);
			excelList.forEach(storeDcSettingDTO -> {
				//				this.insertOrUpdateStoreDcSettings(storeDcSettingDTO.getAddressCode(), storeDcSettingDTO);
			});
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException | IOException e) {
			log.error("ImportStoreSetting error:", e);
		}
	}

	private void importHomeSetting(MultipartFile file, ExcelReadConfig homeExcelReadConfig) {
		try {
			List<HomeDcSetting> excelList = ExcelReadHelper.read(file, HomeDcSetting.class, homeExcelReadConfig);
			//校验
			this.checkHomeSetting(excelList);
			excelList.forEach(homeDcSettingDTO -> {
				//				this.insertOrUpdateHomeDcSettings(homeDcSettingDTO.getAddressCode(), homeDcSettingDTO);
			});
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException | IOException e) {
			log.error("ImportStoreSetting error:", e);
		}
	}

	private void checkHomeSetting(List<HomeDcSetting> excelList) {
		//校验是否重复
		List<HomeDcSetting> distinctList = excelList.stream()
				.collect(
						Collectors.collectingAndThen(
								Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getAddressCode() + o.getWarehouseLevel1()
										+ o.getWarehouseLevel2() + o.getWarehouseLevel3() + o.getEffectiveDate() + o.getInactiveDate()))),
								ArrayList::new));
		if (distinctList.size() != excelList.size()) {
			throw new CommonException("家居配置导入存在重复数据!请重新修改后导入");
		}
		//录入失效时间不能小于今天
		if (!CollectionUtils.isEmpty(excelList)) {
			for (HomeDcSetting vo : excelList) {
				if (vo.getInactiveDate().before(new Date())) {
					throw new CommonException("家居配置导入存在失效时期小于今天的数据!请重新修改后导入");
				}

				if (vo.getInactiveDate().before(vo.getEffectiveDate())) {
					throw new CommonException("家居配置导入存在失效时期小于有效时间的数据!请重新修改后导入");
				}
			}
		}
	}

	private void checkStoreSetting(List<StoreDcSetting> excelList) {
		//校验是否重复
		List<StoreDcSetting> distinctList = excelList.stream()
				.collect(
						Collectors.collectingAndThen(
								Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getAddressCode() + o.getWarehouseLevel1()
										+ o.getWarehouseLevel2() + o.getWarehouseLevel3() + o.getEffectiveDate() + o.getInactiveDate()))),
								ArrayList::new));
		if (distinctList.size() != excelList.size()) {
			throw new CommonException("店铺配送导入存在重复数据!请重新修改后导入");
		}
		//录入失效时间不能小于今天
		if (!CollectionUtils.isEmpty(excelList)) {
			for (StoreDcSetting vo : excelList) {
				if (vo.getInactiveDate().before(new Date())) {
					throw new CommonException("店铺配送导入存在失效时期小于今天的数据!请重新修改后导入");
				}
				if (vo.getInactiveDate().before(vo.getEffectiveDate())) {
					throw new CommonException("店铺配送导入存在失效时期小于有效时间的数据!请重新修改后导入");
				}
			}
		}
	}

	private Map<String, String> getImportMap() {
		Map<String, String> importMap = new HashMap<>();
		importMap.put("一级提货仓编码", "warehouseLevel1");
		importMap.put("二级提货仓编码", "warehouseLevel2");
		importMap.put("三级提货仓编码", "warehouseLevel3");
		importMap.put("地址编码", "addressCode");
		importMap.put("生效时间", "effectiveDate");
		importMap.put("失效时间", "inactiveDate");
		return importMap;
	}

	public List<HomeDcSetting> queryHomeDcSettings(String code) {
		List<HomeDcSetting> list = this.parentQuery(code, true);
		//加一个状态
		if (!CollectionUtils.isEmpty(list)) {
			for (HomeDcSetting settingDTO : list) {
				settingDTO.setStatus(this.getStatus(settingDTO.getEffectiveDate(), settingDTO.getInactiveDate()));
			}
		}
		return list;
	}

	public List<StoreDcSetting> queryStoreDcSettings(String code) {
		List<StoreDcSetting> list = this.parentQuery(code, false);
		//加一个状态
		if (!CollectionUtils.isEmpty(list)) {
			for (StoreDcSetting settingDTO : list) {
				settingDTO.setStatus(this.getStatus(settingDTO.getEffectiveDate(), settingDTO.getInactiveDate()));
			}
		}
		return list;
	}

	private String getStatus(Date effectiveDate, Date inactiveDate) {
		Date now = new Date();
		if (Objects.nonNull(effectiveDate) && Objects.nonNull(inactiveDate) && now.after(effectiveDate) && now.before(inactiveDate)) {
			return INPROCESS;
		}
		if (Objects.nonNull(effectiveDate) && now.before(effectiveDate)) {
			return PLANNING;
		}
		if (Objects.nonNull(inactiveDate) && now.after(inactiveDate)) {
			return INACTIVE;
		}
		return PLANNING;
	}

	private List parentQuery(String code, Boolean isHome) {
		List list = new ArrayList<>();
		int queryCount = 0;
		String parentCode = code;
		while (!StringUtils.isEmpty(parentCode)) {
			CnAddress cnAddressDTO = cnAddressRepository.findByCode(parentCode);
			queryCount++;
			List currentList = isHome ? homeDcSettingRepository.findByAddressCode(parentCode)
					: storeDcSettingRepository.findByAddressCode(parentCode);
			if (!CollectionUtils.isEmpty(currentList)) {
				if (isHome) {
					List<HomeDcSetting> hls = currentList;
					for (HomeDcSetting settingDTO : hls) {
						settingDTO.setEditFlag(queryCount <= 1);
					}
					list.addAll(hls);
				} else {
					List<StoreDcSetting> sls = currentList;
					for (StoreDcSetting settingDTO : sls) {
						settingDTO.setEditFlag(queryCount <= 1);
					}
					list.addAll(sls);
				}
				parentCode = cnAddressDTO.getParentCode();
			} else {
				if (cnAddressDTO == null || StringUtils.isEmpty(cnAddressDTO.getParentCode())) {
					break;
				}
				parentCode = cnAddressDTO.getParentCode();
			}
		}
		return list;
	}

	public StoreDcSetting insertOrUpdateStoreDcSettings(String code, StoreDcSetting storeDcSetting) {
		storeDcSetting.setAddressCode(code);
		StoreDcSetting queryOne = new StoreDcSetting();
		queryOne.setAddressCode(code);
		queryOne.setWarehouseLevel1(storeDcSetting.getWarehouseLevel1());
		queryOne.setWarehouseLevel2(storeDcSetting.getWarehouseLevel2());
		queryOne.setWarehouseLevel3(storeDcSetting.getWarehouseLevel3());
		queryOne.setEditFlag(true);
		Optional<StoreDcSetting> exampleOptional = storeDcSettingRepository.findOne(Example.of(queryOne));
		if (exampleOptional.isPresent() && (storeDcSetting.getVersion() != null)) {
			StoreDcSetting example = exampleOptional.get();
			example.setEditFlag(false);
			if (storeDcSetting.getEffectiveDate() != null) {
				example.setEffectiveDate(storeDcSetting.getEffectiveDate());
			}
			if (storeDcSetting.getInactiveDate() != null) {
				example.setInactiveDate(storeDcSetting.getInactiveDate());
			}
			storeDcSetting = storeDcSettingRepository.save(example);
		} else {
			storeDcSetting.setEditFlag(true);
			storeDcSetting = storeDcSettingRepository.save(storeDcSetting);
		}
		return storeDcSetting;
	}

	public HomeDcSetting insertOrUpdateHomeDcSettings(String code, HomeDcSetting homeDcSetting) {
		homeDcSetting.setAddressCode(code);
		HomeDcSetting queryOne = new HomeDcSetting();
		queryOne.setAddressCode(code);
		queryOne.setWarehouseLevel1(homeDcSetting.getWarehouseLevel1());
		queryOne.setWarehouseLevel2(homeDcSetting.getWarehouseLevel2());
		queryOne.setWarehouseLevel3(homeDcSetting.getWarehouseLevel3());
		queryOne.setEditFlag(true);
		Optional<HomeDcSetting> exampleOptional = homeDcSettingRepository.findOne(Example.of(queryOne));
		if (exampleOptional.isPresent() && (homeDcSetting.getVersion() != null)) {
			HomeDcSetting example = exampleOptional.get();
			example.setEditFlag(false);
			if (homeDcSetting.getEffectiveDate() != null) {
				example.setEffectiveDate(homeDcSetting.getEffectiveDate());
			}
			if (homeDcSetting.getInactiveDate() != null) {
				example.setInactiveDate(homeDcSetting.getInactiveDate());
			}
			homeDcSetting = homeDcSettingRepository.save(example);
		} else {
			homeDcSetting.setEditFlag(true);
			homeDcSetting = homeDcSettingRepository.save(homeDcSetting);
		}
		return homeDcSetting;
	}

}
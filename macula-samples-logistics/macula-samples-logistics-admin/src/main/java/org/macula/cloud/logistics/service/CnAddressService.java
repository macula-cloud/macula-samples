package org.macula.cloud.logistics.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.macula.cloud.logistics.domain.AddressUrlHistory;
import org.macula.cloud.logistics.domain.CnAddress;
import org.macula.cloud.logistics.feign.FileFeignClient;
import org.macula.cloud.logistics.repository.CnAddressRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.choerodon.core.excel.ExcelExportHelper;
import io.choerodon.core.excel.ExcelReadConfig;
import io.choerodon.core.excel.ExcelReadHelper;
import io.choerodon.core.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CnAddressService {

	private static final String EXPORT_FILE_NAME = "导出地址";
	private static final String SUFFIX = ".xls";

	private CnAddressRepository addressRepository;
	private AddressUrlHistoryService addressUrlHistoryService;
	private FileFeignClient fileFeignClient;

	public List<CnAddress> listByParentCode(String parentCode) {
		return addressRepository.findByParentCode(parentCode);
	}

	public CnAddress query(String code) {
		return addressRepository.findByCode(code);
	}

	public CnAddress update(String code, CnAddress cnAddressVo) {
		CnAddress cnAddress = query(code);
		cnAddress.setCode(cnAddressVo.getCode());
		cnAddress.setInactiveDate(cnAddressVo.getInactiveDate());
		cnAddress.setEffectiveDate(cnAddressVo.getEffectiveDate());
		cnAddress.setShowFlag(cnAddressVo.getShowFlag());
		cnAddress.setDowntownFlag(cnAddressVo.getDowntownFlag());
		return addressRepository.save(cnAddress);
	}

	public void exportAddressExcel() {
		// 导出最开始将地址存储表中的最新标识更新为 0
		addressUrlHistoryService.updateAllFlag(false);
		Map<String, String> propertyMap = getExportExcelMap();
		HSSFWorkbook hssfWorkbook;
		List<CnAddress> addressList = addressRepository.findAll(Sort.by(Direction.ASC, "code"));
		String url = null;
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			hssfWorkbook = ExcelExportHelper.exportExcel2003(propertyMap, addressList, "ADDRESS", CnAddress.class);
			String originalFilename = EXPORT_FILE_NAME + System.currentTimeMillis() + SUFFIX;
			hssfWorkbook.write(os);
			MockMultipartFile multipartFile = new MockMultipartFile("file", originalFilename, "application/vnd.ms-excel", os.toByteArray());
			url = fileFeignClient.uploadFile("logistics-service", multipartFile.getOriginalFilename(), multipartFile).getBody();
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
			log.error("Export error: ", e);
		}
		// 导出文件上传结束后插入地址历史表
		AddressUrlHistory addressUrlHistory = new AddressUrlHistory();
		addressUrlHistory.setExcelUrl(url);
		addressUrlHistory.setLatestFlag(true);
		addressUrlHistoryService.insertNewInfo(addressUrlHistory);
	}

	public List<CnAddress> importAddressExcel(MultipartFile file) {
		ExcelReadConfig excelReadConfig = new ExcelReadConfig();
		Map<String, String> importExcelMap = getExportExcelMap().entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
		String[] addressSkipSheet = { "README" };
		excelReadConfig.setPropertyMap(importExcelMap);
		excelReadConfig.setSkipSheetNames(addressSkipSheet);
		List<CnAddress> addressesList;
		try {
			addressesList = ExcelReadHelper.read(file, CnAddress.class, excelReadConfig);
			addressesList.forEach(address -> {
				CnAddress dbAddress = addressRepository.findByCode(address.getCode());
				if (dbAddress != null) {
					dbAddress.setShowFlag(address.getShowFlag());
					dbAddress.setDowntownFlag(address.getDowntownFlag());
					dbAddress.setEffectiveDate(address.getEffectiveDate());
					dbAddress.setInactiveDate(address.getInactiveDate());
					addressRepository.save(dbAddress);
				}
			});
		} catch (IOException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			throw new CommonException("excel解析错误", e);
		} catch (IllegalArgumentException e) {
			throw new CommonException("excel行映射错误", e);
		}
		return addressesList;
	}

	private Map<String, String> getExportExcelMap() {
		Map<String, String> propertyMap = new LinkedHashMap<>();
		propertyMap.put("name", "名称");
		propertyMap.put("code", "编码");
		propertyMap.put("addressType", "地址类型");
		propertyMap.put("address", "地址");
		propertyMap.put("prov", "省份");
		propertyMap.put("provId", "省份Id");
		propertyMap.put("city", "城市");
		propertyMap.put("cityId", "城市Id");
		propertyMap.put("district", "区/县");
		propertyMap.put("districtId", "区/县Id");
		propertyMap.put("town", "乡/镇");
		propertyMap.put("townId", "乡/镇Id");
		propertyMap.put("showFlag", "是否显示");
		propertyMap.put("downtownFlag", "是否为城区");
		propertyMap.put("effectiveDate", "生效时间");
		propertyMap.put("inactiveDate", "失效时间");
		propertyMap.put("parentCode", "父亲编码");
		return propertyMap;
	}
}

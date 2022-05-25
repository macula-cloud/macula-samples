package org.macula.cloud.logistics.vo;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDcSettingExcelDTO {

	@ExcelProperty(value = { "一级提货仓编码", "一级提货仓编码" }, index = 0)
	private String warehouseLevel1;

	@ExcelProperty(value = { "二级提货仓编码", "二级提货仓编码" }, index = 1)
	private String warehouseLevel2;

	@ExcelProperty(value = { "三级提货仓编码", "三级提货仓编码" }, index = 2)
	private String warehouseLevel3;

	@ExcelProperty(value = { "地址编码", "地址编码" }, index = 3)
	private String addressCode;

	@ExcelProperty(value = { "生效时间", "生效时间" }, format = "yyyy-MM-dd HH:mm:ss", index = 4)
	private Date effectiveDate;

	@ExcelProperty(value = { "失效时间", "失效时间" }, format = "yyyy-MM-dd HH:mm:ss", index = 5)
	private Date inactiveDate;

}

package org.macula.cloud.po.sap.model;

import java.util.Date;

import org.hibersap.annotations.BapiStructure;

@BapiStructure
public class DateRangeVo {

	@org.hibersap.annotations.Parameter("SIGN")
	private String rangeSign;

	@org.hibersap.annotations.Parameter("OPTION")
	private String rangeOption;

	//查询下限
	@org.hibersap.annotations.Parameter("LOW")
	private Date rangeLow;

	//查询上限
	@org.hibersap.annotations.Parameter("HIGH")
	private Date rangeHigh;

	public String getRangeSign() {
		return rangeSign;
	}

	public String getRangeOption() {
		return rangeOption;
	}

	public Date getRangeLow() {
		return rangeLow;
	}

	public Date getRangeHigh() {
		return rangeHigh;
	}

	public void setRangeSign(String rangeSign) {
		this.rangeSign = rangeSign;
	}

	public void setRangeOption(String rangeOption) {
		this.rangeOption = rangeOption;
	}

	public void setRangeLow(Date rangeLow) {
		this.rangeLow = rangeLow;
	}

	public void setRangeHigh(Date rangeHigh) {
		this.rangeHigh = rangeHigh;
	}

}

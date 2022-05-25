package org.macula.cloud.po.sap.model;

import org.hibersap.annotations.BapiStructure;

@BapiStructure
public class SelectRangeVo {

	//排除或包括的标识，可能的值为：
	//	I:包括（该记录包含的基金中心范围是属于查询的范围），没特殊意外的话，OP一般传输这个值
	//	E:排除（该记录包含的基金中心范围是不属于查询的范围）
	@org.hibersap.annotations.Parameter("SIGN")
	private String rangeSign;

	//选择范围查询方式，可能的值为：
	//	EQ：等于。该基金中心范围是个单选的值，这个情况下只输入LOW字段而不用输入HIGH字段
	//	BT: 在..之间。表示该记录的选择范围是个区间(LOW 和HIGH之间)。这是OP最可能输入的值
	//	CP：包含pattern.用pattern的方式选择查询的基金中心，例如LOW字段输入3001BE*，表示查询3001BE开头的基金中心数据。*001BE*表示查询基金中心包含001BE的数据。
	//	LE：小于或等于，该基金中心范围是个单选的值，这个情况下只输入LOW字段而不用输入HIGH字段
	//	GE：大于或等于。该基金中心范围是个单选的值，这个情况下只输入LOW字段而不用输入HIGH字段
	//	NE：不等于。该基金中心范围是个单选的值，这个情况下只输入LOW字段而不用输入HIGH字段
	//	NB：不在。。。之间。表示该记录的选择范围是个不在区间(LOW 和HIGH之间)之内
	//	NP：不包含包含pattern。和CP相反。
	//	GT：大于。这个情况下只输入LOW字段而不用输入HIGH字段
	//	LT：小于。这个情况下只输入LOW字段而不用输入HIGH字
	@org.hibersap.annotations.Parameter("OPTION")
	private String rangeOption;

	//查询下限
	@org.hibersap.annotations.Parameter("LOW")
	private String rangeLow;

	//查询上限
	@org.hibersap.annotations.Parameter("HIGH")
	private String rangeHigh;

	public String getRangeSign() {
		return rangeSign;
	}

	public String getRangeOption() {
		return rangeOption;
	}

	public String getRangeLow() {
		return rangeLow;
	}

	public String getRangeHigh() {
		return rangeHigh;
	}

	public void setRangeSign(String rangeSign) {
		this.rangeSign = rangeSign;
	}

	public void setRangeOption(String rangeOption) {
		this.rangeOption = rangeOption;
	}

	public void setRangeLow(String rangeLow) {
		this.rangeLow = rangeLow;
	}

	public void setRangeHigh(String rangeHigh) {
		this.rangeHigh = rangeHigh;
	}

}

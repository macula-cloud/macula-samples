/**
 * PoStatusEnum.java 2013-11-5
 */
package org.macula.cloud.po.gbss.type;

/**
 * <p>
 * <b>PoStatusEnum</b> 单据状态枚举类
 * </p>
 *
 
 
 
 */
public enum PoStatusEnum {

	/**
	 * 新增，正常； 默认值
	 */
	PO_STATUS_00("00", "新增，正常"),

	/**
	 * 冻结；
	 */
	PO_STATUS_01("01", "冻结"),

	/**
	 *  删除；
	 */
	PO_STATUS_99("99", "删除");

	private String code;

	private String label;

	/**
	 * @param code
	 * @param label
	 */
	private PoStatusEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 根据code获取label
	 * @param code
	 * @return
	 */
	public static String getLabel(String code) {
		for (PoStatusEnum enumType : PoStatusEnum.values()) {
			if (enumType.code.equals(code)) {
				return enumType.getLabel();
			}
		}
		return code;
	}

	@Override
	public String toString() {
		return this.code;
	}
}

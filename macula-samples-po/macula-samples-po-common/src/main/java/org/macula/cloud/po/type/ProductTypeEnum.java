/**
 * ProductTypeEnum.java 2013-9-17
 */
package org.macula.cloud.po.type;

/**
 * <p>
 * <b>ProductTypeEnum</b> 是产品类型的枚举类
 * </p>
 *
 
 
 
 */
public enum ProductTypeEnum {
	
	/**
	 * 产品
	 */
	P("P", "产品"),
	
	/**
	 * 辅销品
	 */
	M("M", "辅销品"),

	/**
	 * V：服务产品
	 */
	V("V", "服务产品");
	
	private String code;
	
	private String label;

	/**
	 * @param code
	 * @param label
	 */
	private ProductTypeEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	/**
	 * 根据code获取label
	 * @param code
	 * @return
	 */
	public static String getLabel(String code){
		for(ProductTypeEnum enumType : ProductTypeEnum.values()){
			if (enumType.code.equals(code)) {
				return enumType.getLabel();
			}
		}
		return code;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.code;
	}

}

/**
 * 
 */
package org.macula.cloud.po.gbss.sap;

/**
 * <p>
 * <b>TCondition</b> 传入定价条件表:
 * </p>
 *
 
 
 
 *
 */

public class TCondition {

	/**
	 * 申请单行项目:00--记整单，其它对应行号
	 */
	private String itmNumber;

	/**
	 * 定价类型:
	 * "单价:PROO
		原点数:ZK05
		折扣点数:ZK06
		消费积分:ZK91
		启业套装:ZK80
		电子券:ZK20"
	
	 */
	private String condType;

	/**
	 * 定价金额
	 */
	private String condValue;

	/**
	 * 货币单位
	 */
	private String Currency;

	/**
	 * @return the itmNumber
	 */
	public String getItmNumber() {
		return itmNumber;
	}

	/**
	 * @param itmNumber the itmNumber to set
	 */
	public void setItmNumber(String itmNumber) {
		this.itmNumber = itmNumber;
	}

	/**
	 * @return the condType
	 */
	public String getCondType() {
		return condType;
	}

	/**
	 * @param condType the condType to set
	 */
	public void setCondType(String condType) {
		this.condType = condType;
	}

	/**
	 * @return the condValue
	 */
	public String getCondValue() {
		return condValue;
	}

	/**
	 * @param condValue the condValue to set
	 */
	public void setCondValue(String condValue) {
		this.condValue = condValue;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return Currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		Currency = currency;
	}

}

/**
 * 
 */
package org.macula.cloud.po.sap.model;

import java.math.BigDecimal;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

/**
 * <p>
 * <b>TCondition</b> 传入定价条件表:
 * </p>
 *
 
 
 
 *
 */
@BapiStructure
public class TCondition {

	/**
	 * 申请单行项目:00--记整单，其它对应行号
	 */
	@Parameter("ITM_NUMBER")
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
	@Parameter("COND_TYPE")
	private String condType;

	/**
	 * 定价金额
	 */
	@Parameter("COND_VALUE")
	private BigDecimal condValue;

	/**
	 * 货币单位
	 */
	@Parameter("CURRENCY")
	private String currency;

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
	public BigDecimal getCondValue() {
		return condValue;
	}

	/**
	 * @param condValue the condValue to set
	 */
	public void setCondValue(BigDecimal condValue) {
		this.condValue = condValue;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}

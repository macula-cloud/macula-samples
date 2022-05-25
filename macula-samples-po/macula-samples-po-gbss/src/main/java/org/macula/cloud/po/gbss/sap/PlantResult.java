/**
 * 
 */
package org.macula.cloud.po.gbss.sap;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * <b>PlantResult</b> 是SAP接口统一返回的vo类
 * </p>
 * 
 
 
 
 * 
 */

public class PlantResult extends SapResult {
	private String sapTranNo;

	private String sapTranYear;

	private String tranType;

	private Map headValues;

	private List result;

	private List resultExt1;

	private List resultExt2;

	/**
	 * @return the sapTranNo
	 */
	public String getSapTranNo() {
		return sapTranNo;
	}

	/**
	 * @param sapTranNo
	 *            the sapTranNo to set
	 */
	public void setSapTranNo(String sapTranNo) {
		this.sapTranNo = sapTranNo;
	}

	/**
	 * @return Returns the headValues.
	 */
	public Map getHeadValues() {
		return headValues;
	}

	/**
	 * @param headValues
	 *            The headValues to set.
	 */
	public void setHeadValues(Map headValues) {
		this.headValues = headValues;
	}

	/**
	 * @return the sapTranYear
	 */
	public String getSapTranYear() {
		return sapTranYear;
	}

	/**
	 * @param sapTranYear
	 *            the sapTranYear to set
	 */
	public void setSapTranYear(String sapTranYear) {
		this.sapTranYear = sapTranYear;
	}

	/**
	 * @return the result
	 */
	public List getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * @return the tranType
	 */
	public String getTranType() {
		return tranType;
	}

	/**
	 * @param tranType
	 *            the tranType to set
	 */
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	/**
	 * @return Returns the resultExt1.
	 */
	public List getResultExt1() {
		return resultExt1;
	}

	/**
	 * @param resultExt1
	 *            The resultExt1 to set.
	 */
	public void setResultExt1(List resultExt1) {
		this.resultExt1 = resultExt1;
	}

	/**
	 * @return Returns the resultExt2.
	 */
	public List getResultExt2() {
		return resultExt2;
	}

	/**
	 * @param resultExt2
	 *            The resultExt2 to set.
	 */
	public void setResultExt2(List resultExt2) {
		this.resultExt2 = resultExt2;
	}

}

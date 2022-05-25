/**
 * 
 */
package org.macula.cloud.po.sap.bapi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.ParameterType;
import org.hibersap.annotations.Table;
import org.macula.cloud.po.sap.model.POrderHeader;
import org.macula.cloud.po.sap.model.PoHeader;
import org.macula.cloud.po.sap.model.TCondition;
import org.macula.cloud.po.sap.model.TOrderItems;
import org.macula.cloud.po.sap.model.TReturn;
import org.macula.cloud.sap.AbstractBapi;

/**
 
 */
@Bapi("ZTRG_SD_I_101_SO_CREATE")
public class DealerOrderZrebBapi extends AbstractBapi {

	private static final Pattern PATTERN = Pattern.compile("\\[\\d{10}\\]");
	private static final Pattern BALANCE_PATTERN = Pattern.compile("物料[0-9,-]*可用量不足");
	/**
	 * 订单主表
	 */
	@Import
	@Parameter(value = "P_ORDER_HEADER", type = ParameterType.STRUCTURE)
	private POrderHeader porderHeader;

	/**
	 * 定价条件
	 */
	@Table
	@Parameter("T_CONDITION")
	private List<TCondition> tconditionList;

	/**
	 * 订单条目
	 */
	@Table
	@Parameter("T_ORDER_ITEMS")
	private List<TOrderItems> torderItemsList;

	/**
	 * 返回的sap单号
	 */
	@Export
	@Parameter(value = "PO_HEADER", type = ParameterType.STRUCTURE)
	private PoHeader poHeader;

	/**
	 * sap返回的信息表
	 */
	@Table
	@Parameter("T_RETURN")
	private List<TReturn> treturn;

	/**
	 * SAP返回状态
	 * 1:Type全部为"S",则是成功
	 * 2:Type有一项为"E",则是失败
	 * @return
	 */
	@Override
	public boolean hasSuccess() {
		if (treturn != null) {
			for (TReturn rt : treturn) {
				if ("E".equalsIgnoreCase(rt.getType())) {
					return false;
				}
			}
		}
		return super.hasSuccess();
	}

	/**
	 * 调用SAP后,当有多条错误信息时,将错误信息进行拼接
	 * @return
	 */
	public String getErrorMessage() {
		StringBuilder message = new StringBuilder();
		if (treturn != null) {
			for (TReturn tReturn : treturn) {
				if ("E".equalsIgnoreCase(tReturn.getType())) {
					message.append(tReturn.getMessage() + ";");
				}
			}
		}
		return message.toString();
	}

	/**
	 * 获取可用量不足物料清单
	 * @return
	 */
	public Set<String> getInsufficientBalanceMaterials() {
		Set<String> meterials = new HashSet<String>();
		if (treturn != null) {
			for (TReturn tReturn : treturn) {
				Matcher matched = BALANCE_PATTERN.matcher(tReturn.getMessage());
				while (matched.find()) {
					String group = matched.group();
					group = group.substring(3, group.length() - 5).trim();
					group = RegExUtils.removePattern(group, "^0*");
					meterials.add(group);
				}
			}
		}
		return meterials;
	}

	public POrderHeader getPOrderHeader() {
		return porderHeader;
	}

	public void setPOrderHeader(POrderHeader porderHeader) {
		this.porderHeader = porderHeader;
	}

	public List<TCondition> getTConditionList() {
		return tconditionList;
	}

	public void setTConditionList(List<TCondition> tconditionList) {
		this.tconditionList = tconditionList;
	}

	public List<TOrderItems> getTOrderItemsList() {
		return torderItemsList;
	}

	public void setTOrderItemsList(List<TOrderItems> torderItemsList) {
		this.torderItemsList = torderItemsList;
	}

	public List<TReturn> getTReturn() {
		return treturn;
	}

	public void setTReturn(List<TReturn> treturn) {
		this.treturn = treturn;
	}

	/**
	 * @return the poHeader
	 */
	public PoHeader getPoHeader() {
		if ((poHeader == null || StringUtils.isBlank(poHeader.getSalesdocument())) && CollectionUtils.isNotEmpty(this.treturn)) {
			for (TReturn tReturn : treturn) {
				Matcher matched = PATTERN.matcher(tReturn.getMessage());
				if (matched.find()) {
					if (poHeader == null) {
						poHeader = new PoHeader();
					}
					poHeader.setSalesdocument(matched.group().substring(1, 11));
				}
			}
		}
		return poHeader;
	}

	/**
	 * @param poHeader the poHeader to set
	 */
	public void setPoHeader(PoHeader poHeader) {
		this.poHeader = poHeader;
	}

}

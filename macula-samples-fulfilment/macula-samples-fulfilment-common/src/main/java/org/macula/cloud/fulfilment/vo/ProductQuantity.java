package org.macula.cloud.fulfilment.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.macula.cloud.fulfilment.util.InventoryUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductQuantity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 产品SKU
	 */
	private String productCode;

	/**
	 * 货仓代码 (这里先用作测试)
	 */
	private String whCode;

	/**
	 * 产品适用库区 (这里先用作测试)
	 */
	private String locCode;

	/**
	 * 产品数量
	 */
	private BigDecimal quantity = BigDecimal.ZERO;

	/**
	 * 库存状态
	 */
	private String status;

	public static ProductQuantity of(String productCode, String whCode, String locCode) {
		ProductQuantity pq = new ProductQuantity();
		pq.setProductCode(productCode);
		pq.setWhCode(whCode);
		pq.setLocCode(locCode);
		return pq;
	}

	public static ProductQuantity of(String productCode, String whCode, String locCode, BigDecimal quantity) {
		ProductQuantity pq = of(productCode, whCode, locCode);
		pq.setQuantity(quantity);
		return pq;
	}

	@Override
	public boolean equals(Object obj) {
		return productCode != null && obj != null && obj instanceof ProductQuantity && productCode.equals(((ProductQuantity) obj).getProductCode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		return result;
	}

	/**
	 * 获取Redis记录的库存Key
	 * @param accDate
	 * @return
	 */
	public String getRedisKey(String accDate) {
		return InventoryUtils.getStockRedisKey(accDate, getProductCode(), getWhCode(), getLocCode());
	}

}

package org.macula.cloud.fulfilment.vo;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedMap.Builder;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

@Data
public class InventoryRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文档索引号(如PO单单号)
	 */
	private String document;

	/**
	 * 请求版本号，为使PO可以不重复提交及可以多次提交，设计增加此字段
	 */
	private String version;

	/**
	 * 请求创建时间
	 */
	private long timestamp = System.currentTimeMillis();

	/**
	 * 扣减时间
	 */
	private String accDate;

	/**
	 * 配送地址
	 */
	private String deliveryAddress;

	/**
	 * 配送方式
	 */
	private String deliveryMethod;

	/**
	 * 商品明细，以productCode不得重复
	 */
	private Set<ProductQuantity> details;

	/**
	 * 获取明细的产品编号列表用于排序
	 */
	@Transient
	public SortedMap<String, BigDecimal> getSortedProductQuantities() {
		Builder<String, BigDecimal> builder = ImmutableSortedMap.<String, BigDecimal>naturalOrder();
		if (CollUtil.isNotEmpty(this.getDetails())) {
			this.getDetails().forEach(o -> {
				builder.put(o.getRedisKey(accDate), o.getQuantity());
			});
		}
		return builder.build();
	}

	/**
	 * 添加商品数量明细
	 * @param productQuantity
	 */
	public void addProductQuantity(ProductQuantity productQuantity) {
		if (details == null) {
			details = new HashSet<>();
		}
		details.add(productQuantity);
	}

}

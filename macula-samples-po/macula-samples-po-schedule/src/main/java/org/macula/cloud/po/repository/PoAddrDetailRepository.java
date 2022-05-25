package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.PoAddrDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoAddrDetailRepository extends JpaRepository<PoAddrDetail, Long> {

	/**
	 * 根据订单号查询订单配送地址信息
	 * @param poNo
	 * @return
	 */
	public PoAddrDetail findByPoNo(String poNo);
}

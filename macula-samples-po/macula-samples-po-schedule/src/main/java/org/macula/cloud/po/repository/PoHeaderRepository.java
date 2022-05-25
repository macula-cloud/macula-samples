package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.PoHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoHeaderRepository extends JpaRepository<PoHeader, Long> {

	/**
	 * 根据订单号查询相关数据
	 * @param poNo
	 * @return
	 */
	public PoHeader findByPoNo(String poNo);

}

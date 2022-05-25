package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoDetailRepository extends JpaRepository<PoDetail, Long> {

	/**
	 * 根据订单号查询订单详细信息
	 * @param poNo
	 * @return
	 */
	public List<PoDetail> findByPoNoOrderByLineNoAsc(String poNo);

}

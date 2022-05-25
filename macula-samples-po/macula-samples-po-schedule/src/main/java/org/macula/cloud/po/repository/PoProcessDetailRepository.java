package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoProcessDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoProcessDetailRepository extends JpaRepository<PoProcessDetail, Long> {

	/**
	 * 根据订单号查询处理明细信息
	 * @param poNo
	 * @return
	 */
	List<PoProcessDetail> findByPoNo(String poNo);

}

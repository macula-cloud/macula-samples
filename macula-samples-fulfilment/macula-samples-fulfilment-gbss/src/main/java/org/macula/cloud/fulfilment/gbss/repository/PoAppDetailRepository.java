package org.macula.cloud.fulfilment.gbss.repository;

import java.util.List;

import org.macula.cloud.fulfilment.gbss.domain.PoAppDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoAppDetailRepository extends JpaRepository<PoAppDetail, Long> {

	/**
	 * 获取申请单产品明细
	 * @param poAppNo 申请订单号
	 * @return
	 */
	public List<PoAppDetail> findByPoAppNo(String poAppNo);
}

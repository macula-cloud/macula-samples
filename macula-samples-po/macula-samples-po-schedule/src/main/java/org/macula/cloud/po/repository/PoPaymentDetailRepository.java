package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoPaymentDetailRepository extends JpaRepository<PoPaymentDetail, Long> {

	/**
	 * 根据交易单号获取订货支付信息
	 * @param poNo
	 * @return
	 */
	List<PoPaymentDetail> findByPoNo(String poNo);

}

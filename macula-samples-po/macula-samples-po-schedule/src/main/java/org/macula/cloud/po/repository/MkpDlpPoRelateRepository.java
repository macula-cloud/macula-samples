package org.macula.cloud.po.repository;

import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MkpDlpPoRelateRepository extends JpaRepository<MkpDlpPoRelate, Long> {

	/**
	 * 通过订单号获取价值表信息
	 * @param poNo
	 * @return
	 */
	public MkpDlpPoRelate findByPoNo(String poNo);

}
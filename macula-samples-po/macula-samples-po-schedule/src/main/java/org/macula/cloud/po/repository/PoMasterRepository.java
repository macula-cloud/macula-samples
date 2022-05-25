package org.macula.cloud.po.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PoMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PoMasterRepository extends JpaRepository<PoMaster, Long> {
	/**
	 * 根据订单号获取订单信息
	 * @param poNo
	 * @return
	 */
	PoMaster findByPoNo(String poNo);

	/**
	 * 方法说明:根据销售单据输入时间poEntryTime来查询时间范围内数据
	 * @param startTime:销售单据输入时间
	 * @param endTime:销售单据输入时间
	 * @return
	 */
	@Query("from PoMaster p where p.poEntryTime >= :startTime and p.poEntryTime < :endTime and ( p.refPoNo is null or ( p.refPoNo is not null and (p.totalSaleAmt >= 0 or p.sapPostingDocNo is not null) ) )")
	List<PoMaster> findCheckingBillings(Date startTime, Date endTime);

	/**
	 * 根据订单号去查询当前订单是否有辅单
	 * @param poNo
	 * @return
	 */
	PoMaster findByRefSelectedNo(String poNo);

}

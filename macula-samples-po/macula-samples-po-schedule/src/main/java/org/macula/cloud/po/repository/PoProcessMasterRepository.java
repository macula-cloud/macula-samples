package org.macula.cloud.po.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoProcessMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PoProcessMasterRepository extends JpaRepository<PoProcessMaster, Long> {

	/**
	 * 根据订单号查询处理主表信息
	 * @param poNo
	 * @return
	 */
	PoProcessMaster findByPoNo(String poNo);

	@Query("select poNo from PoProcessMaster p where p.status in (:status) and p.statusTime < (current_timestamp - 10) order by p.errorTimes asc, p.id desc")
	List<String> findNeedRetryOrders(List<String> status);

	/**
	 * 获取同步到的GBSS的SAP_DAILY_UPL_PO or V2的Id
	 */
	@Query("select max(p.refSourceId) from PoProcessMaster p where p.poSource= :type ")
	Long getMaxGbssUploadId(@Param("type") String type);

}

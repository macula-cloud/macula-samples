package org.macula.cloud.po.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PosAccTranDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 是自营店交易流水信息repository接口
 */
public interface PosAccTranDetailRepository extends JpaRepository<PosAccTranDetail, Long> {

	/**
	 * 根据销售单号查询交易流水帐信息
	 * @param refDocNo
	 * @return
	 */
	@Query("from PosAccTranDetail a where a.refDocNo = :refDocNo and a.posPaymentType in ('*CARD','*CASH','*CHK','*REMIT','*EPOS')")
	List<PosAccTranDetail> queryByRefDocNoAndPaymentTypeCardCashChkRemit(@Param("refDocNo") String refDocNo);

	/**
	 * @param poDate
	 * @param poStoreNo
	 * @param poNo
	 * @return
	 */
	List<PosAccTranDetail> findByPosTranDateAndPosStoreNoAndRefDocNo(Date poDate, String poStoreNo, String poNo);

}

/**
 * PosAccTranDetailRepository.java 2012-2-23
 */
package org.macula.cloud.po.gbss.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PosAccTranDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>PosAccTranDetailRepository</b> 是自营店交易流水信息repository接口
 * </p>
 * 
 
 
 
 *          ky_yx $
 */
public interface PosAccTranDetailRepository extends JpaRepository<PosAccTranDetail, Long> {

	//	/**
	//	 * 获取收银员当天的所有交易流水(注意，此处也包括了日结及抽大钞的交易)
	//	 * 
	//	 * @param posTranDate
	//	 * @param posUserName
	//	 * @return List<PosAccTranDetail>
	//	 */
	//	List<PosAccTranDetail> findByPosTranDateAndPosUserName(Date posTranDate, String posUserName);
	//
	//	/**
	//	 * 获取收银员当天所有现金交易流水信息
	//	 * 
	//	 * @param posTranDate
	//	 * @param posUserName
	//	 * @param posPaymentType
	//	 * @return List<PosAccTranDetail>
	//	 */
	//	List<PosAccTranDetail> findByPosTranDateAndPosUserNameAndPosPaymentType(Date posTranDate, String posUserName, String posPaymentType);
	//
	//	/**
	//	 * 获取收银员当天指定交易类型总额
	//	 * 
	//	 * @param posTranDate
	//	 * @param soStoreNo
	//	 *            自营店号
	//	 * @param posUserName
	//	 * @param posPaymentType
	//	 * @return BigDecimal
	//	 */
	//	@Query("select sum(posTranAmt) from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posStoreNo=:soStoreNo and posPaymentType=:posPaymentType")
	//	BigDecimal sumPosTranAmtBy(@Param("posTranDate") Date posTranDate, @Param("soStoreNo") String soStoreNo, @Param("posUserName") String posUserName,
	//			@Param("posPaymentType") String posPaymentType);
	//
	//	/**
	//	 * 获取收银员当天指定交易类型收入总额
	//	 * 
	//	 * @param posUserName
	//	 * @param posPaymentType
	//	 * @param posTranDate
	//	 * @return sumPositivePosTranAmt BigDecimal
	//	 */
	//	@Query("select sum(case when sign(posTranAmt)=1 then posTranAmt else 0 end) from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posPaymentType=:posPaymentType")
	//	BigDecimal sumPositivePosTranAmtBy(@Param("posTranDate") Date posTranDate, @Param("posUserName") String posUserName,
	//			@Param("posPaymentType") String posPaymentType);
	//
	//	/**
	//	 * 获取收银员当天指定交易项目总额
	//	 * 
	//	 * @param posTranDate
	//	 * @param posUserName
	//	 * @param posTranType
	//	 * @return BigDecimal
	//	 */
	//	@Query("select sum(posTranAmt) from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posTranType=:posTranType")
	//	BigDecimal sumPosTranAmtBy(@Param("posUserName") String posUserName, @Param("posTranType") String posTranType,
	//			@Param("posTranDate") Date posTranDate);
	//
	//	/**
	//	 * 根据销售单号查询交易流水帐信息
	//	 * 
	//	 * @param refDocNo
	//	 * @return
	//	 */
	//	@Query("from PosAccTranDetail a where a.refDocNo = :refDocNo and a.posPaymentType in ('*CARD','*CASH','*CHK')")
	//	PosAccTranDetail queryPosAccTranDetailByRefDocNo(@Param("refDocNo") String refDocNo);
	//
	//	/**
	//	 * 根据销售单号查询下单确认支付后保存的交易流水账记录
	//	 * 
	//	 * @param refDocNo
	//	 * @return
	//	 */
	//	@Query("from PosAccTranDetail a where a.refDocNo = :refDocNo ")
	//	List<PosAccTranDetail> getPosAccTranDetailsByRefDocNo(@Param("refDocNo") String refDocNo);
	//
	//	/**
	//	 * 获取收银员当天指定交易项目总额
	//	 * 
	//	 * @param posTranDate
	//	 * @param soStoreNo
	//	 * @param posUserName
	//	 * @param posTranType
	//	 * @return BigDecimal
	//	 */
	//	@Query("select sum(posTranAmt) from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posStoreNo=:soStoreNo and posTranType=:posTranType")
	//	BigDecimal getTranTypeSumAmt(@Param("posTranDate") Date posTranDate, @Param("soStoreNo") String soStoreNo,
	//			@Param("posUserName") String posUserName, @Param("posTranType") String posTranType);
	//
	//	/**
	//	 * 获取收银员当天指定交易项目的明细（只适用于只有一条明细的情况，当前只用于获取日结明细流水）
	//	 * 
	//	 * @param posTranDate
	//	 * @param soStoreNo
	//	 * @param posUserName
	//	 * @param posTranType
	//	 * @return
	//	 */
	//	@Query("from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posStoreNo=:soStoreNo and posTranType=:posTranType")
	//	PosAccTranDetail getDayEndTurnInTranDetail(@Param("posTranDate") Date posTranDate, @Param("soStoreNo") String soStoreNo,
	//			@Param("posUserName") String posUserName, @Param("posTranType") String posTranType);
	//
	//	/**
	//	 * 根据销售单号查询交易流水帐信息
	//	 * 
	//	 * @param refDocNo
	//	 * @return
	//	 */
	//	@Query("from PosAccTranDetail a where a.refDocNo = :refDocNo and a.posPaymentType in ('*SLR','*MK01', '*MK03')")
	//	List<PosAccTranDetail> queryByRefDocNoAndPaymentTypeSlrMk01(@Param("refDocNo") String refDocNo);
	//
	/**
	 * 根据销售单号查询交易流水帐信息
	 * @param refDocNo
	 * @return
	 */
	@Query("from PosAccTranDetail a where a.refDocNo = :refDocNo and a.posPaymentType in ('*CARD','*CASH','*CHK','*REMIT','*EPOS')")
	List<PosAccTranDetail> queryByRefDocNoAndPaymentTypeCardCashChkRemit(@Param("refDocNo") String refDocNo);

	//	/**
	//	 * 获取指定单据 收银员当天是否已经撤销的次数
	//	 * 
	//	 * @param posTranDate
	//	 * @param soStoreNo 自营店号
	//	 * @param posUserName 
	//	 * @param posPaymentType 
	//	 * @param posAccTranDetailId 
	//	 * @return int
	//	 */
	//	@Query("select count(t.id) from PosAccTranDetail t where posTranDate=:posTranDate and posUserName=:posUserName and posStoreNo=:soStoreNo and posTranType=:posTranType and comments=:posAccTranDetailId and posPaymentType=:posPaymentType")
	//	int countBackPosTranAmtTimes(@Param("posTranDate") Date posTranDate, @Param("soStoreNo") String soStoreNo,
	//			@Param("posUserName") String posUserName, @Param("posTranType") String posTranType, @Param("posPaymentType") String posPaymentType,
	//			@Param("posAccTranDetailId") String posAccTranDetailId);

	/**
	 * @param poDate
	 * @param poStoreNo
	 * @param poNo
	 * @return
	 */
	List<PosAccTranDetail> findByPosTranDateAndPosStoreNoAndRefDocNo(Date poDate, String poStoreNo, String poNo);

	//	/**
	//	 * 获取应缴金额
	//	 * 
	//	 * @param posTranDate
	//	 * @param soStoreNo
	//	 * @param posUserName
	//	 * @param posTranType
	//	 * @return BigDecimal
	//	 */
	//	@Query("select sum(posTranAmt) from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posStoreNo=:soStoreNo and posTranType in :posTranTypes")
	//	BigDecimal getTranTypeSumAmt(@Param("posTranDate") Date posTranDate, @Param("soStoreNo") String soStoreNo,
	//			@Param("posUserName") String posUserName, @Param("posTranTypes") List<String> posTranTypes);
	//
	//	/**
	//	 * 获取应缴金额
	//	 * 
	//	 * @param posTranDate
	//	 * @param soStoreNo
	//	 * @param posUserName
	//	 * @param posTranType
	//	 * @return BigDecimal
	//	 */
	//	@Query("select sum(posTranAmt) from PosAccTranDetail where posTranDate=:posTranDate and posUserName=:posUserName and posStoreNo=:soStoreNo and posTranType in :posTranTypes and posPaymentType=:posPaymentType")
	//	BigDecimal getTranTypeSumAmts(@Param("posTranDate") Date posTranDate, @Param("soStoreNo") String soStoreNo,
	//			@Param("posUserName") String posUserName, @Param("posTranTypes") List<String> posTranTypes,
	//			@Param("posPaymentType") String posPaymentType);
}

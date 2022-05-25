/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PoMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * <b>DsPoMasterRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface DsPoMasterRepository extends JpaRepository<PoMaster, Long> {
	/**
	 * 根据订单号获取订单信息
	 * @param poNo
	 * @return
	 */
	public PoMaster findByPoNo(String poNo);

	//	/**
	//	 * 根据草稿单号获取订单信息
	//	 * @Deprecated by ray.zhao 2016-05-23
	//	 * @Deprecated reason： po_master表数据超大，poAppNo非索引字段，查询效率极低，远远超过30秒（通联自助刷卡冲正接口中使用了该方法，导致超时，冲正失败）
	//	 * @param poAppNo
	//	 * @return
	//	 */
	//	@Deprecated
	//	public PoMaster findByPoAppNo(String poAppNo);
	//
	//	/**
	//	 * 根据草稿单号获取订单信息
	//	 * add by ray.zhao 2016-05-23
	//	 * 替代findByPoAppNo方法，增加索引字段poPeriod,orderDealerNo,加快查询效率
	//	 * @param poPeriod,orderDealerNo,poAppNo
	//	 * @return
	//	 */
	//	public PoMaster findByPoPeriodAndOrderDealerNoAndPoAppNo(String poPeriod, String orderDealerNo, String poAppNo);
	//
	//	/**
	//	 * 根据主单号码的辅单号码获取订单信息
	//	 * 
	//	 * @param poNo
	//	 * @return
	//	 */
	//	public List<PoMaster> findByRefSelectedNo(String refSelectedNo);
	//
	//	@Query(value = "select * from po_master p where p.ref_selected_no = :refSelectNo for update nowait", nativeQuery = true)
	//	public List<PoMaster> findByRefSelectedNoNoWait(@Param("refSelectNo") String refSelectNo);
	//
	//	/**
	//	 * 根据主单号和单据流程代码查找辅单（随货配送品单和促销赠品单）
	//	 * @param refSelectedNo
	//	 * @param poProcessCode
	//	 * @return
	//	 */
	//	public List<PoMaster> findByRefSelectedNoAndOrderDealerNoAndPoProcessCode(String refSelectedNo, String orderDealerNo, String poProcessCode);
	//
	//	/**
	//	 * 根据购货人卡号查询销售订单信息
	//	 * @param orderByDealerNo
	//	 * @return
	//	 */
	//	@Query("from PoMaster a where a.orderDealerNo = :orderDealerNo and a.poStatus = '00' and a.poProcessCode not in ('G105','G205','G206','G305','G306','G307','G413','G418')")
	//	public List<PoMaster> getNewPoMasterByOrderDealerNo(@Param("orderDealerNo") String orderDealerNo);
	//
	//	@Query("select sum(a.totalSaleAmt) from PoMaster a where a.poNo = :poNo or a.refPoNo = :poNo")
	//	public BigDecimal getResultAmt(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 根据团单号查询数据
	//	 * 
	//	 * @param poGroupNo
	//	 * @param paymentStates
	//	 * @param LoginNo
	//	 * @return
	//	 */
	//	@Query("select pm.id,pm.poAppNo,pm.orderDealerName,pm.totalSaleAmt,pm.totalSaleAmt,pm.poEntryTime,"
	//			+ "pm.poNo from PoMaster pm where pm.poGroupNo=:groupNo and pm.paymentStatus=:paymentStatus and"
	//			+ " pm.poEntryDealerNo=:dealerNo order by pm.poAppNo")
	//	List<?> findBypoGroupNoPaymentStatesIsY(@Param("groupNo") String poGroupNo, @Param("paymentStatus") String paymentStates,
	//			@Param("dealerNo") String LoginNo);
	//
	//	/**
	//	 * 查询已扣库存的并在同一RDC地址下面的产品的产品信息
	//	 * @param orderDealerNo
	//	 * @return
	//	 */
	//	@Query("select new org.macula.cloud.po.gbss.vo.PoAppLclInfo("
	//			+ " aa.productCode,aa.saleQty,n.productFullName,n.productSubType,cc.deliveryWhLocCode,aa.poNo,'1')"
	//			+ " from PoDetail aa,PoMaster bb ,PoAddrDetail cc,ProductInfo n " + " where bb.poNo = aa.poNo "
	//			+ " and bb.poNo = cc.poNo and cc.deliveryStatus = 'A' and cc.deliveryAttr = '1' "
	//			+ " and bb.orderDealerNo = :orderDealerNo and aa.productCode= n.productCode order by aa.poNo")
	//	List<PoAppLclInfo> getLclPoDetailInfo(@Param("orderDealerNo") String orderDealerNo);
	//
	//	/**
	//	 * 查询已扣库存的并在同一RDC地址下面的产品的产品信息
	//	 * @param orderDealerNo
	//	 * @param whCode
	//	 * @return
	//	 */
	//	@Deprecated
	//	@Query("select new org.macula.cloud.po.gbss.vo.PoAppLclInfo("
	//			+ " aa.productCode,aa.saleQty,n.productFullName,n.productSubType,cc.deliveryWhLocCode,aa.poNo,'1')"
	//			+ " from PoDetail aa,PoMaster bb ,PoAddrDetail cc,ProductInfo n " + " where bb.poNo = aa.poNo "
	//			+ " and bb.poNo = cc.poNo and cc.deliveryStatus = 'A' and cc.deliveryAttr = '1' "
	//			+ " and bb.orderDealerNo = :orderDealerNo and aa.productCode= n.productCode and cc.deliveryWhCode = :whCode "
	//			+ " and bb.poNo not in (select holdStockAppNo from PoAppHoldStock) order by aa.poNo")
	//	List<PoAppLclInfo> getLclPoDetailInfoWhCode(@Param("orderDealerNo") String orderDealerNo, @Param("whCode") String whCode);
	//
	//	/**
	//	 * 查询已扣库存的并在同一RDC地址下面的产品的产品信息
	//	 * @param orderDealerNo
	//	 * @param whCode
	//	 * @return
	//	 */
	//	@Query("select new org.macula.cloud.po.gbss.vo.PoAppLclInfo("
	//			+ " a.productCode,a.poTranQty,c.productFullName,d.listFullName,b.deliveryWhCode,a.poLclNo,'1',b.poLclType)"
	//			+ " from PoAppLclDetail a,PoAppLclMaster b,ProductInfo c,ProductListTypeInfo d"
	//			+ " where a.poLclNo = b.poLclNo and a.productCode = c.productCode and c.productListType =d.listTypeCode"
	//			+ " and b.orderDealerNo = :orderDealerNo and a.isEnabled =1 "
	//			+ " and b.poLclStatus ='00' and b.deliveryWhCode=:whCode and b.isFinished<>2" + " and b.deliveryEffecitveDate <=sysdate "
	//			+ " and (b.deliveryInactiveDate is null or sysdate<b.deliveryInactiveDate)"
	//			+ " and a.poLclNo not in (select holdStockAppNo from PoAppHoldStock) order by a.poLclNo desc")
	//	List<PoAppLclInfo> getLclPoDetailInfoWhCodeInfo(@Param("orderDealerNo") String orderDealerNo, @Param("whCode") String whCode);
	//
	//	/**
	//	 * 查询未扣库存的清单列表
	//	 */
	//	@Deprecated
	//	@Query(" select new org.macula.cloud.po.gbss.vo.PoAppLclInfo("
	//			+ " a.productCode,(a.poAppQty - nvl(a.poTranQty,0)),n.productFullName,n.productSubType,'',a.poLclNo,'2')"
	//			+ " from PoAppLclDetail a,ProductInfo n " + " where a.poAppQty > a.poTranQty and a.bindPoSupplyQty = 0 and "
	//			+ " a.productCode = n.productCode and a.poLclNo in ( " + " select b.poLclNo from PoAppLclMaster b Where b.poLclStatus <> '03' "
	//			+ " and b.deliveryEffecitveDate <= sysdate " + " and ((b.deliveryInactiveDate is null) or (b.deliveryInactiveDate > sysdate)) "
	//			+ " and b.orderDealerNo = :orderDealerNo ) " + "  and a.poLclNo not in (select holdStockAppNo from PoAppHoldStock) order by a.poLclNo")
	//	List<PoAppLclInfo> getLclPoAppLclDetailInfo(@Param("orderDealerNo") String orderDealerNo);
	//
	//	/**
	//	 * 查询未扣库存的清单列表
	//	 */
	//	@Query(" select new org.macula.cloud.po.gbss.vo.PoAppLclInfo("
	//			+ " a.productCode,(a.poAppQty - nvl(a.poTranQty,0)),n.productFullName,c.listFullName,'',a.poLclNo,'2',b.poLclType)"
	//			+ " from PoAppLclDetail a,PoAppLclMaster b,ProductInfo n,ProductListTypeInfo c "
	//			+ " where a.poLclNo = b.poLclNo and a.poAppQty > a.poTranQty and a.bindPoSupplyQty = 0 and n.productListType =c.listTypeCode "
	//			+ " and a.bindPoAppNo is null and a.isEnabled =1" + " and a.productCode = n.productCode and b.poLclStatus='01' and b.isFinished<>2 "
	//			+ " and b.deliveryEffecitveDate <= sysdate " + " and ((b.deliveryInactiveDate is null) or (b.deliveryInactiveDate > sysdate)) "
	//			+ " and b.orderDealerNo = :orderDealerNo " + "  and a.poLclNo not in (select holdStockAppNo from PoAppHoldStock) order by a.poLclNo desc")
	//	List<PoAppLclInfo> getLclPoAppLclDetailNewInfo(@Param("orderDealerNo") String orderDealerNo);
	//
	//	/**
	//	 * 查询未扣库存已经绑定申请订单草稿的清单列表
	//	 */
	//	@Query("select a " + " from PoAppLclMaster a where a.poLclNo in (select poLclNo from PoAppLclDetail where bindPoAppNo =:bindPoAppNo)"
	//			+ " and a.poLclStatus='01' and a.isFinished<>2 ")
	//	List<PoAppLclMaster> getBindpoAppLcMastetlInfo(@Param("bindPoAppNo") String bindPoAppNo);
	//
	//	/**
	//	 * 查询已扣库存已经绑定申请订单草稿的清单列表
	//	 */
	//	@Query("select a " + " from PoAppLclMaster a where a.poLclNo in (select poLclNo from PoAppLclDetail where bindPoAppNo =:bindPoAppNo)"
	//			+ " and a.poLclStatus ='00' and a.isFinished<>2 ")
	//	List<PoAppLclMaster> getBindpoAppLcMastetlOtherInfo(@Param("bindPoAppNo") String bindPoAppNo);
	//
	//	/**
	//	 * 根据购货人得到正式订单的数据（不与草稿中的表重复）
	//	 * 从三个月前到现在的数据
	//	 */
	//	@Query("select pm.poNo,pm.orderDealerNo,pm.orderDealerName,pm.totalSaleAmt,pm.poDate " + "from PoMaster pm "
	//			+ "where (pm.orderDealerNo=:dealerNo or pm.poEntryDealerNo=:dealerNo) and pm.poGroupNo is null "
	//			+ "and pm.poDate >= to_date(:from,'yyyy-MM-dd') and pm.poDate<=to_date(:to,'yyyy-MM-dd') " + "order by pm.poDate desc,pm.poNo desc")
	//	List<Object[]> getPoMasterList(@Param("dealerNo") String dealerNo, @Param("from") String from, @Param("to") String to);
	//
	//	/**
	//	 * 根据卡号查询订单，支持分页
	//	 * @param dealerNo
	//	 * @param pageable
	//	 * @return
	//	 */
	//	Page<PoMaster> findByOrderDealerNo(String dealerNo, Pageable pageable);
	//
	//	/**
	//	 * 根据dealerNo和日期范围查询订单数据，过滤掉 *AD 其他辅助单据
	//	 */
	//	@Query("select pm from PoMaster pm " + "where (pm.orderDealerNo=:dealerNo or pm.poEntryDealerNo=:dealerNo) and pm.orderType != '*AD' "
	//			+ "and pm.poDate >= to_date(:from,'yyyy-MM-dd') and pm.poDate<=to_date(:to,'yyyy-MM-dd') " + "order by pm.poDate desc,pm.poNo desc")
	//	Page<PoMaster> findByOrderDealerNoAndDate(@Param("dealerNo") String dealerNo, @Param("from") String from, @Param("to") String to,
	//			Pageable pageable);
	//
	//	/**
	//	 * 得到三个月前得订单（正式订单）
	//	 */
	//	@Query("select pm.poNo,pm.orderDealerNo,pm.orderDealerName,pm.totalSaleAmt,pm.poDate " + "from PoMaster pm "
	//			+ "where (pm.orderDealerNo=:dealerNo or pm.poEntryDealerNo=:dealerNo) and pm.poGroupNo is null "
	//			+ "and pm.poDate<to_date(:threemonthago,'yyyy-MM-dd') " + "order by pm.poDate desc, pm.poNo desc")
	//	List<Object[]> findByPoMasterTMsAgo(@Param("dealerNo") String dealerNo, @Param("threemonthago") String threemonthago);
	//
	//	/**
	//	 * 搜索
	//	 * 根据订单号得到搜索正式订单与产品名称
	//	 * @param dealerNo
	//	 * @param poNo
	//	 * @param fullName
	//	 * @param threeMonthAge
	//	 * @param nowdate
	//	 * @return
	//	 */
	//	@Query("select distinct(pm.poNo),pm.orderDealerNo,pm.orderDealerName,pm.totalSaleAmt,pm.poDate," + "pm.poEntryDealerNo "
	//			+ "from PoMaster pm,PoDetail pd,ProductInfo pi " + "where pm.poNo=pd.poNo and pd.productCode=pi.productCode and "
	//			+ "(pm.orderDealerNo=:dealerNo or pm.poEntryDealerNo=:dealerNo) and pm.poGroupNo is null and "
	//			+ "pm.poNo=:poNo and pi.productFullName like :fullName  and "
	//			+ "pm.poDate >= to_date(:from,'yyyy-MM-dd') and pm.poDate<=to_date(:to,'yyyy-MM-dd')")
	//	List<Object[]> findOrderByPoNoAndfullName(@Param("dealerNo") String dealerNo, @Param("poNo") String poNo, @Param("fullName") String fullName,
	//			@Param("from") String threeMonthAge, @Param("to") String nowdate);
	//
	//	/**
	//	 * 搜索
	//	 * 根据订单号得到搜索正式订单与产品名称
	//	 * @param dealerNo
	//	 * @param poNo
	//	 * @param fullName
	//	 * @param threeMonthAge
	//	 * @return
	//	 */
	//	@Query("select distinct(pm.poNo),pm.orderDealerNo,pm.orderDealerName,pm.totalSaleAmt,pm.poDate," + "pm.poEntryDealerNo "
	//			+ "from PoMaster pm, PoDetail pd,ProductInfo pi " + "where  pm.poNo=pd.poNo and pd.productCode=pi.productCode and "
	//			+ "(pm.orderDealerNo=:dealerNo or pm.poEntryDealerNo=:dealerNo) and pm.poGroupNo is null and "
	//			+ "pm.poNo=:poNo and pi.productFullName like :fullName and pm.poDate<to_date(:to,'yyyy-MM-dd')")
	//	List<Object[]> findOrderByPoNoAndfullNameThreeMonthAgo(@Param("dealerNo") String dealerNo, @Param("poNo") String poNo,
	//			@Param("fullName") String fullName, @Param("to") String threeMonthAge);
	//
	//	/**
	//	 * 根据购货人卡号查询辅单信息
	//	 */
	//	@Query(" select p from PoMaster t,PoAddrDetail p where t.poNo=p.poNo and t.orderDealerNo=:orderDealerNo  "
	//			+ " and p.deliveryStatus='A' and p.deliveryAttr ='1' " + " and p.deliveryWhCode=:deliveryWhCode")
	//	List<PoAddrDetail> findByPOrderDealerNo(@Param("orderDealerNo") String orderDealerNo, @Param("deliveryWhCode") String whcode);
	//
	//	/**
	//	 * 根据购货人卡号和促销活动代码查询订单信息
	//	 * 
	//	 * @param dealerno
	//	 * @param promCode
	//	 * @return
	//	 */
	//	@Query("select count(a.id) from PoMaster a,PoDetail b where a.poNo=b.poNo and a.orderDealerNo=:dealerno " + "and b.promCode=:promCode")
	//	Long getTimesType(@Param("dealerno") String dealerno, @Param("promCode") String promCode);
	//
	//	/**
	//	 * 查询专卖店三个月到现在的个人订货单数量
	//	 */
	//	@Query("select count(p.id) from PoMaster p where p.orderDealerNo=:dealerno and p.poEntryTime>sysdate - 120 " + " and p.orderType=:orderType ")
	//	public long queryNotOrderProductForThreeMonth(@Param("dealerno") String storeNo, @Param("orderType") String orderType);
	//
	//	/**
	//	 * 查询专卖店本月及上个月是否有订货
	//	 * zhuohr
	//	 */
	//	@Query("select count(p.id) from PoMaster p where p.orderDealerNo=:dealerno and p.poPeriod >=to_char(add_months(trunc(sysdate),-1),'yyyymm') "
	//			+ " and p.orderType=:orderType and p.poProcessCode not in('G102','G104')")
	//	public long queryNotOrderProductCurrentMonth(@Param("dealerno") String storeNo, @Param("orderType") String orderType);
	//
	//	/**
	//	 * 查询专卖店本月及之前三个月
	//	 * zhuohr
	//	 */
	//	@Query("select count(p.id) from PoMaster p where p.orderDealerNo=:dealerno and p.poPeriod >=to_char(add_months(trunc(sysdate),-3),'yyyymm') "
	//			+ " and p.orderType=:orderType and p.poProcessCode not in('G102','G104')")
	//	public long queryNotOrderProductForThreeMonthAgo(@Param("dealerno") String storeNo, @Param("orderType") String orderType);
	//
	//	/**
	//	 * 从主单获取辅单所有信息 REF_SELECTED_NO csw 2011-10-25
	//	 * 
	//	 * @param refSelectedNo
	//	 *            主单号码
	//	 * @return
	//	 */
	//	@Query("select pm.id,pm.poNo,pad.deliveryPlanDocNo,pad.deliveryPlanDate from PoMaster pm,PoAddrDetail pad "
	//			+ "where pm.poNo=pad.poNo and pm.refSelectedNo=:refSelectedNo")
	//	List<Object[]> findPoNoDeliveryList(@Param("refSelectedNo") String refSelectedNo);
	//
	//	/**
	//	 * 
	//	 * @param poGroupNo
	//	 * @return
	//	 */
	//	public List<PoMaster> findBypoGroupNo(String poGroupNo);
	//
	//	/**
	//	 * 查询所有未SAP记账的已扣库存的po_master信息
	//	 * @param poNoList
	//	 * @return
	//	 */
	//	@Query("select poNo from PoMaster where poLclNo in :poLclNo and sapPostingFlag='N' ")
	//	public List<String> queryLclPoMasterNotSAPinfo(@Param("poLclNo") List<String> poNoList);
	//
	//	@Query("select poNo from PoMaster where poLclNo in :poLclNo  and poLclNo is not null and poPeriod>=:poPeriod ")
	//	public List<String> queryLclPoMasterinfo(@Param("poLclNo") List<String> poNoList, @Param("poPeriod") String poPeriod);
	//
	//	/**
	//	 * 辅单已扣库存的订单绑定申请订单
	//	 * @param poNo
	//	 * @param poNoList
	//	 */
	//	@Modifying
	//	@Query("update PoMaster set refSelectedNo=:poNo where poNo in :poNoList")
	//	public void updateRefSelectedNoInfo(@Param("poNo") String poNo, @Param("poNoList") List<String> poNoList);
	//
	//	/**
	//	 * 
	//	 * @param poProcessCode
	//	 * @return
	//	 */
	//	@Query("select poProcessDesc from PoProcessCodeInfo where poProcessCode =:poProcessCode")
	//	public String getPoTypeByPoProcessCode(@Param("poProcessCode") String poProcessCode);
	//
	//	/**
	//	 * 查询
	//	 * @param poNo
	//	 * @return
	//	 */
	//	//	@Query("select new Map(pm.poPeriod as poPeriod,pm.orderDealerNo as orderDealerNo,"
	//	//			+ "dsp.servicePwd as servicePwd) from PoMaster pm ,DealerServicePwd dsp where pm.orderDealerNo = dsp.dealerNo and pm.poNo =:poNo")
	//	//	public Map<String, Object> getInvoiceRegInfo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 运单下的每张销售单产品明细
	//	 * @param genPlanDocNo
	//	 * @return
	//	 */
	//	//	//2012-12-20 订单类型取值更改
	//	//	@Query(" select new map(b.poNo as poNo,d.poProcessDesc as poType,c.productCode as productCode,"
	//	//			+ " e.productShortName as productName,c.saleQty as saleQty ,b.orderDealerNo as orderDealerNo,"
	//	//			+ " b.orderDealerName as orderDealerName,to_char(b.poDate,'yyyy-MM-dd') as poDate) "
	//	//			+ " from PoMaster b,PoDetail c,PoProcessCodeInfo d,ProductInfo e "
	//	//			+ " where b.poNo in(select f.poNo from PoShipmentPlanDetail f where f.genPlanDocNo =:genPlanDocNo) "
	//	//			+ " and b.poNo = c.poNo and b.poProcessCode = d.poProcessCode and e.productCode=c.productCode order by b.poNo")
	//	//	public List<Map<String, Object>> findDetailOfEachOrder(@Param("genPlanDocNo") String genPlanDocNo);
	//
	//	/**
	//	 * 发票相关信息
	//	 * @param poNo
	//	 * @return
	//	 */
	//	//	@Query("select new map(a.poNo as poNo,a.orderDealerNo as orderDealerNo,c.certificateNo as certificateNo,c.contactTele as contactTele,"
	//	//			+ "c.contactMobile as contactMobile,a.orderDealerName as orderDealerName,c.addrProvince as addrProvince,c.addrCity as addrCity,"
	//	//			+ "c.addrCounty as addrCounty,c.addrTail01 as addrDetail,a.totalSaleProductAmt as totalSaleProductAmt,c.addrZipCode as addrZipCode,"
	//	//			+ "(a.totalSaleCouponAmt-a.totalCalcDiscountPoint) as totalCouponAmts,a.totalSaleNetAmt as totalSaleNetAmt,a.totalCalcDiscountPoint as totalCalcDiscountPoint) "
	//	//			+ "from PoMaster a,DealerPersonalInfo c,PoAddrDetail b " + "where a.orderDealerNo = c.dealerNo and a.poNo=b.poNo and a.poNo=:poNo")
	//	//	public Map<String, Object> findInvoiceInfo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * @param poNo
	//	 * @return
	//	 */
	//	public PoMaster findByRefPoNo(String poNo);
	//
	//	/**
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select p from PoMaster p where p.refPoNo=:poNo order by p.poNo desc")
	//	public List<PoMaster> getByRefPoNo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 未申请开发票的订单  (购货日起7天内有效 过了7天就不能再申请发票了)
	//	 * @param dealerNo
	//	 * @param poPeriod
	//	 * @return
	//	 */
	//	//	select b.po_no,b.po_date,b.total_sale_amt,(b.total_sale_coupon_amt-b.total_sale_discount_amt),b.total_sale_discount_amt
	//	//	from po_master b 
	//	//	where b.order_dealer_no='333925768' and b.po_period = '201203' and b.po_no not in (select d.po_no from po_invoice_per_detail d where 
	//	//	to_char(d.created_time,'yyyyMM') >= '201203')
	//
	//	//	@Query("select new map(b.poNo as poNo ,b.poDate as poDate,b.totalSaleAmt as totalSaleAmt,(b.totalSaleCouponAmt-b.totalSaleDiscountAmt) as CouponAmt,b.totalSaleDiscountAmt as DiscountAmt) "
	//	//			+ "from PoMaster b where b.orderDealerNo=:orderDealerNo and b.poPeriod=:poPeriod and b.poNo not in "
	//	//			+ "(select d.poNo from PoInvoicePerDetail d where to_char(d.createdDate,'yyyyMM')>= :poPeriod) and b.totalSaleNetAmt > 0 and (trunc(sysdate)-b.poDate) <= 7 order by b.poDate desc, b.poNo desc")
	//	//	public List<Map<String, Object>> getApplyMaster(@Param("orderDealerNo") String dealerNo, @Param("poPeriod") String poPeriod);
	//
	//	/**
	//	 * 已申请开票 (appTranStatus=1已申请开票  appTranStatus=0 发票申请为草稿单状态)
	//	 * @param dealerNo
	//	 * @param poPeriod
	//	 * @return
	//	 */
	//	//	@Query("select new map(c.invoiceTranNo as invoiceTranNo,b.poNo as poNo ,b.poDate as poDate,b.totalSaleAmt as totalSaleAmt,(b.totalSaleCouponAmt-b.totalSaleDiscountAmt) as CouponAmt,b.totalSaleDiscountAmt as DiscountAmt) "
	//	//			+ "from PoMaster b,PoInvoicePerDetail c where b.poNo = c.poNo and b.orderDealerNo=:orderDealerNo and b.poPeriod=:poPeriod and b.poNo in "
	//	//			+ "(select d.poNo from PoInvoicePerMaster pipm, PoInvoicePerDetail d where pipm.invoiceTranNo = d.invoiceTranNo and pipm.appTranStatus = :appTranStatus and to_char(d.createdDate,'yyyyMM')>= :poPeriod) order by b.poDate desc")
	//	//	public List<Map<String, Object>> getAppliedMaster(@Param("orderDealerNo") String dealerNo, @Param("poPeriod") String poPeriod,
	//	//			@Param("appTranStatus") String appTranStatus);
	//
	//	/**
	//	 * 人物信息
	//	 * @param orderDealerNo
	//	 * @return
	//	 */
	//	//	@Query("select new map(c.dealerNo as orderDealerNo,c.certificateNo as certificateNo,c.contactTele as contactTele,"
	//	//			+ "c.contactMobile as contactMobile,a.fullName as orderDealerName,c.addrProvince as addrProvince,c.addrCity as addrCity,"
	//	//			+ "c.addrCounty as addrCounty,c.addrTail01 as addrDetail,c.addrZipCode as addrZipCode) " + "from DealerPersonalInfo c,Dealer a "
	//	//			+ "where a.dealerNo=c.dealerNo and c.dealerNo =:orderDealerNo")
	//	//	public Map<String, Object> getOrderDealerInfo(@Param("orderDealerNo") String orderDealerNo);
	//
	//	/**
	//	 * 查找订单单
	//	 * 
	//	 * @param orderDealerNo
	//	 *            订货卡号
	//	 * @param period
	//	 *            月份
	//	 * @param orderType
	//	 *            订单类型
	//	 * @return
	//	 */
	//	public List<PoMaster> findByOrderDealerNoAndPoPeriodAndOrderTypeOrderByPoNoDesc(String orderDealerNo, String period, String orderType);
	//
	//	/**
	//	 * 根据转单号获取促销品
	//	 * @param poPreNo
	//	 * @return
	//	 */
	//	//	@Query("select new Map(pd.productCode as productCode,pi.productFullName as productFullName,pd.saleQty as orderQty,pi.unitMeasure as unitMeasure) "
	//	//			+ "from PoDetail pd , ProductInfo pi where pd.poNo = (select ppm.poNo  from PoPreMaster ppm where ppm.poPreNo=:poPreNo) "
	//	//			+ "and pd.promProductAttr='2' and pd.productCode = pi.productCode")
	//	//	public List<Map<String, Object>> getPromotionByTranNo(@Param("poPreNo") String poPreNo);
	//
	//	/**
	//	 * 查询网上申请推荐单
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	//	@Query("select a from PoMaster a,DealerCard b where a.poNo = b.appFirstSoNo and b.dealerNo =:dealerNo and b.appStoreNo = '000000000'")
	//	//	public List<PoMaster> queryDealerCardInfoList(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 查询某张单在服务中心退货的记录
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select a from PoMaster a where a.refPoNo = :poNo and (a.poProcessCode = 'G305' or a.poProcessCode = 'G306')"
	//			+ " and a.companyNo = :companyNo")
	//	public List<PoMaster> queryPosReturnPoMaster(@Param("poNo") String poNo, @Param("companyNo") String companyNo);
	//
	//	/**
	//	 * 通过订单号查询所有赠品信息
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select new org.macula.cloud.po.gbss.vo.ProductPriceInfo(pald.productCode,pi.productShortName,pi.unitMeasure,"
	//			+ "pald.poAppQty,pald.poTranQty,pald.poAppQty-pald.poTranQty) from PoAppLclDetail pald, ProductInfo pi where pald.poLclNo in "
	//			+ " (select palm.poLclNo from PoAppLclMaster palm where palm.refDocNo=:poNo) and pald.productCode = pi.productCode")
	//	public List<ProductPriceInfo> getAllPromotionals(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 通过订单号查询本单带走的赠品
	//	 * @param poNo
	//	 * @return
	//	 * SQL优化　modify ky_qrj 2014-7-15
	//	 */
	//	@Query("select new org.macula.cloud.po.gbss.vo.ProductPriceInfo(pald.productCode,pi.productShortName,pi.unitMeasure,pald.saleQty) "
	//			+ "from PoDetail pald, ProductInfo pi, PoMaster pm where pald.poNo = pm.poNo "
	//			+ "and pald.productCode = pi.productCode and pm.refSelectedNo = :poNo " + "and pm.refSelectedNo is not null and pm.poPeriod=:poPeriod ")
	//	public List<ProductPriceInfo> getPromotionals(@Param("poNo") String poNo, @Param("poPeriod") String poPeriod);
	//
	//	/**
	//	 * 本单记欠赠品
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select new org.macula.cloud.po.gbss.vo.ProductPriceInfo(pald.productCode,pi.productShortName,pi.unitMeasure,pald.poAppQty-pald.poTranQty) "
	//			+ "from PoAppLclDetail pald, ProductInfo pi where (pald.poAppQty-pald.poTranQty)>0 and pald.poLclNo in "
	//			+ " (select palm.poLclNo from PoAppLclMaster palm where palm.refDocNo=:poNo) and pald.productCode = pi.productCode")
	//	public List<ProductPriceInfo> getPromotionals_need(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 根据预订单号查询赠品
	//	 * @param poPreNo
	//	 * @return
	//	 */
	//	@Query("select new org.macula.cloud.po.gbss.vo.ProductPriceInfo(pald.productCode,pi.productShortName,"
	//			+ "pi.unitMeasure,pald.poAppQty,pald.poTranQty,pald.poAppQty-pald.poTranQty) "
	//			+ "from PoAppLclDetail pald , ProductInfo pi where pald.poLclNo in "
	//			+ " (select palm.poLclNo from PoAppLclMaster palm where palm.refDocNo=(select ppm.poNo  from PoPreMaster ppm where ppm.poPreNo=:poPreNo)) and pald.productCode = pi.productCode")
	//	public List<ProductPriceInfo> getPromotionByTranNoFromLcl(@Param("poPreNo") String poPreNo);
	//
	//	/**
	//	 * 通过合并单号查询所有赠品信息
	//	 * @param poGroupNo
	//	 * @return
	//	 * 
	//	select b.product_full_name,
	//	   a.qty,
	//	   b.unit_measure from
	//	(select t.product_code,sum(t.po_app_qty) as qty from 
	//	(select pald.po_app_qty,pald.product_code from po_app_lcl_detail pald where pald.po_lcl_no in 
	//	(select palm.po_lcl_no from po_app_lcl_master palm 
	//	where palm.ref_doc_no in 
	//	(select d.po_no from po_master d where d.po_group_no ='PGRP00000162092'))) t  group by t.product_code) a , product_info b
	//	where a.product_code = b.product_code
	//	
	//	 */
	//	//	@Query("select new org.macula.cloud.po.gbss.vo.ProductPriceInfo(b.productCode,b.productShortName,b.unitMeasure,a.qty) from " +
	//	//			" (select t.productCode,sum(t.poAppQty) as qty from (select pald.poAppQty,pald.productCode from PoAppLclDetail pald where pald.poLclNo in(" +
	//	//			" select palm.poLclNo from PoAppLclMaster palm where palm.refDocNo in(select d.poNo from PoMaster d where d.poGroupNo =:poGroupNo ))) t " +
	//	//			" group by t.productCode) a ,ProductInfo b where a.productCode = b.productCode")
	//	//	public List<ProductInfo> getPromotionalsbyPoGroupNo(String poGroupNo);
	//
	//	@Query("select s from PoMaster s where s.orderDealerNo=:dealerNo and poPeriod between :startPeriod and :endPeriod")
	//	List<PoMaster> findPoMasterByDealerNo(@Param("dealerNo") String dealerNo, @Param("startPeriod") String startPeriod,
	//			@Param("endPeriod") String endPeriod);
	//
	//	@Query("select distinct orderDealerNo from PoMaster where poPeriod between :poPeriodStart and :poPeriodEnd")
	//	public List<String> queryDealerNoListBySoPeriod(@Param("poPeriodStart") String poPeriodStart, @Param("poPeriodEnd") String poPeriodEnd);
	//
	//	@Query("  from PoMaster where orderDealerNo=:dealerNo  and poProcessCode  in :poProcessCode  order by id desc")
	//	public List<PoMaster> queryPoMaster4Count(@Param("dealerNo") String dealerNo, @Param("poProcessCode") String poProcessCode);
	//
	//	/**
	//	 * 根据购货人和团购号查询
	//	 * @param dealerNo
	//	 * @param poGroupNo
	//	 * @return
	//	 */
	//	@Query("  from PoMaster where orderDealerNo=:dealerNo  and poGroupNo  =:poGroupNo  order by id desc")
	//	public List<PoMaster> queryPoMaster(@Param("dealerNo") String dealerNo, @Param("poGroupNo") String poGroupNo);
	//
	//	/**
	//	 * 根据购货人和团购号查询是在PoMater创建的10分钟内
	//	 * @param dealerNo
	//	 * @param poGroupNo
	//	 * @return
	//	 */
	//	@Query(nativeQuery = true, value = "select count(id)  from Po_Master where order_Dealer_No=:dealerNo  and po_Group_No  =:poGroupNo and LAST_UPDATED_TIME +numtodsinterval(10,'minute') >= sysdate order by id desc")
	//	public BigDecimal findPoMaster10Minute(@Param("dealerNo") String dealerNo, @Param("poGroupNo") String poGroupNo);
	//
	//	/**
	//	 * 根据购货人和团购号查询是在PoMater创建的10分钟内
	//	 * @param dealerNo
	//	 * @param poGroupNo
	//	 * @return
	//	 */
	//	@Query(nativeQuery = true, value = "select count(id)  from Po_Master where PO_NO=:poNo and LAST_UPDATED_TIME +numtodsinterval(10,'minute') >= sysdate order by id desc")
	//	public BigDecimal findPoMaster10Minute(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 查询 新增状态  个人购货 家居配送  未触发发货的 进行冻结
	//	 * @param addrSendId
	//	 * @return
	//	 */
	//	//GBSSYW-1860 增加dealerNo查询条件
	//	@Query("select pm from PoAddrDetail pad, PoMaster pm where pad.poNo = pm.poNo and pm.poStatus = '00' "
	//			+ "and pad.deliveryType = '12' and pad.deliveryPlanProcFlag <> 'Y' and pad.addrSendId = :addrSendId and pad.deliveryDealerNo = :dealerNo")
	//	public List<PoMaster> findPoNoForFreeze(@Param("addrSendId") String addrSendId, @Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * GBSS-122 GBSS-323
	//	 * 地址失效查询 新增状态  个人购货 家居配送  进行冻结;
	//	 * service中需要检查排除已进入物流的
	//	 * @param addrSendId
	//	 * @param deliveryDealerNo
	//	 * @return
	//	 */
	//	@Query("select pm from PoAddrDetail pad, PoMaster pm where pad.poNo = pm.poNo and pm.poStatus = '00' and pad.deliveryType = '12' "
	//			+ "and pm.orderType='*PO' and pad.addrSendId = :addrSendId and pad.deliveryDealerNo = :deliveryDealerNo")
	//	public List<PoMaster> findInactivePoNoForFreeze(@Param("addrSendId") String addrSendId, @Param("deliveryDealerNo") String deliveryDealerNo);
	//
	//	/**
	//	 * 双十一活动订单查询
	//	 * totalCalcPoint pv点数
	//	 * add by CZH
	//	 */
	//	public List<PoMaster> queryPoMasterElevenList(@Param("startTime") String startTime, @Param("endTime") String endTime);
	//
	//	/**
	//	 * 按时间扫描订单，对未参加促销的订单进行检查补漏
	//	 * add by CZH
	//	 * date 2015-03-19
	//	 * 
	//	 */
	//	public List<PoMaster> queryPoMasterErrList(@Param("startTime") String startTime, @Param("endTime") String endTime,
	//			@Param("poPeriod") String poPeriod);
	//
	//	/**
	//	 * 按原单查询退货单
	//	 * 
	//	 */
	//	@Query("from PoMaster a where a.refPoNo = :poNo")
	//	public List<PoMaster> queryByRefPoNo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 逢5有约促销活动
	//	 * 条件：个人购货单单号尾数逢5（15、25……如此类推）且购货单PV＞0
	//	 * createBy zhangrenlong
	//	 * @return
	//	 */
	//	@Query(value = "select * from Po_Master pm " + "where pm.total_Calc_Point-pm.total_Calc_Discount_Point > 0 "
	//			+ "and pm.po_Process_Code in('G001','G003') " + "and pm.po_No like '%5' " + "and pm.po_Period = :period "
	//			+ "and to_char(pm.po_Entry_Time, 'HH24mi') < '1701' "
	//			+ "and pm.po_Entry_Time+0 > (select case when to_char(max(msp.award_Time),'yyyyMMdd') = to_char(sysdate,'yyyyMMdd') then max(msp.award_Time)+0 "
	//			+ "else to_date(to_char(sysdate,'yyyyMMdd')||' 05:00','yyyyMMdd HH24:MI:SS') end  from Mkp_Prom_Award msp)"
	//			+ "order by pm.po_Entry_Time asc", nativeQuery = true)
	//	public List<PoMaster> getEvery5Date(@Param("period") String period);
	//
	//	/**
	//	 * 逢5有约促销活动
	//	 * 条件：个人购货单单号尾数逢500（1500、2500……如此类推）且购货单PV＞0
	//	 * createBy zhangrenlong
	//	 * @return
	//	 */
	//	@Query(value = "select * from Po_Master pm " + "where pm.total_Calc_Point-pm.total_Calc_Discount_Point > 0 "
	//			+ "and pm.po_Process_Code in('G001','G003') " + "and pm.po_No like '%500' " + "and pm.po_Period = :period "
	//			+ "and to_char(pm.po_Entry_Time, 'HH24mi') < '1701' "
	//			+ "and pm.po_Entry_Time+0 > (select case when to_char(max(msp.award_Time),'yyyyMMdd') = to_char(sysdate,'yyyyMMdd') then max(msp.award_Time)+0 "
	//			+ "else to_date(to_char(sysdate,'yyyyMMdd')||' 05:00','yyyyMMdd HH24:MI:SS') end  from Mkp_Prom_Award msp) ", nativeQuery = true)
	//	public List<PoMaster> getEvery500Date(@Param("period") String period);
	//
	//	/**
	//	 * 
	//	 * 条件：
	//	 * 每月的1号跑一次
	//	 * 每个推广日整点跑，和当天最后跑一次
	//	 * @return
	//	 */
	//	/**
	//	 * 逢5有约促销活动:活动当天单笔购货金额最高排名前n名，每名奖励无限极定制银币二枚
	//	 * @param period	所在月份
	//	 * @param poDate	日期
	//	 * @param number	取有n名
	//	 * @return
	//	 */
	//	@Query(value = "select * from (select p.* from (select t.po_no, sum(t.total_sale_amt) totalAmt "
	//			+ "from (select pm.po_no po_no, pm.total_sale_amt "
	//			+ "from po_master pm where pm.po_process_code in ('G003', 'G001', 'G306', 'G308') and pm.po_period =:period "
	//			+ "union all (select sm.ref_so_no po_no,sm.total_sale_amt from so_master sm where sm.ref_so_no is not null and  sm.so_period =:period) ) t "
	//			+ "group by t.po_no) tab, po_master p " + "where tab.po_no = p.po_no " + "and p.po_period = :period "
	//			+ "and p.po_process_code in ('G003', 'G001') " + "and to_char(p.po_entry_time, 'HH24mi') >= '0500' "
	//			+ "and to_char(p.po_entry_time, 'HH24mi') <= '1700' " + "and to_char(p.po_entry_time, 'yyyyMMdd') = :poDate "
	//			+ "order by tab.totalAmt desc) where rownum<=:num ", nativeQuery = true)
	//	public List<PoMaster> getTotalSaleAmtMax5(@Param("period") String period, @Param("poDate") String poDate, @Param("num") int number);
	//
	//	/**
	//	 * 悦购越享Show促销活动 zr_zrl
	//	 * 查询卡号有没有发生过个人购货行为
	//	 * @param loginDealerNo
	//	 */
	//	@Query("select count(p.id) from PoMaster p where p.orderDealerNo =:loginDealerNo "
	//			+ "and p.poProcessCode in ('G001','G002','G003','G004','G005','G006','G007','G008') ")
	//	public long findByOrderDealerNo(@Param("loginDealerNo") String loginDealerNo);
	//
	//	/**
	//	 * 根据购货人获取海外卡号累计的已用总点数
	//	 * @param orderDealerNo
	//	 * @return
	//	 */
	//	@Query("select sum(a.totalCalcPoint) from PoMaster a where a.orderDealerNo=:orderDealerNo")
	//	public BigDecimal getResultPointByOrderDealerNo(@Param("orderDealerNo") String orderDealerNo);
	//
	//	@Query(value = "select * from po_master p where p.id =:id for update nowait", nativeQuery = true)
	//	public PoMaster findOneForUpdateNowait(@Param("id") Long id);
	//
	//	@Query(value = "select * from po_master p where p.id in :ids for update nowait", nativeQuery = true)
	//	public List<PoMaster> findAllForUpdateNowait(@Param("ids") List<Long> ids);
	//
	/**
	 * @param refPoNo
	 * @return
	 */
	@Query("from PoMaster p where p.refPoNo = :poNo and  p.poProcessCode = :poProcessCode and p.companyNo = :companyNo ")
	public PoMaster findByRefPoNoAndPoProcessCodeAndCompanyNo(@Param("poNo") String poNo, @Param("poProcessCode") String poProcessCode,
			@Param("companyNo") String companyNo);

	/**
	 * 方法说明:测试定时对账,根据当前时间前十分钟的支付完成时间的数据
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@Query("from PoMaster p where p.poEntryTime >= :startTime and p.poEntryTime < :endTime")
	List<PoMaster> takeGBSSCheckData(Date startTime, Date endTime);

	//
	//	/**
	//	 * @param pono
	//	 */
	//	@Query("from PoMaster p where p.poNo like :poNo")
	//	public List<PoMaster> findSubPoMasterByPoNo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 根据单号查询非退货单
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("from PoMaster p where p.poType in ('10','00') and (p.poNo = :poNo or p.refPoNo = :poNo)")
	//	List<PoMaster> listByPoNoOrRefPoNo(@Param("poNo") String poNo);
	//
	//	List<PoMaster> findByRefPoNoAndPoProcessCode(String poNo, String poProcessCode);
	//
	//	/**
	//	 * 根据单号和公司代码获取主单
	//	 * @param refPoNo
	//	 * @param companyNo
	//	 * @return
	//	 */
	//	@Query("from PoMaster p where (p.poNo = :refPoNo or p.refPoNo = :refPoNo)"
	//			+ " and p.companyNo = :companyNo and p.poProcessCode in ('G001','G002','G003','G004','G005',"
	//			+ "'G006','G007','G008','G020','G101','G102','G103','G104','G301','G302','G303','G304','G401','G402',"
	//			+ "'G403','G404','G405','G406','G407','G408','G409','G410','G411','G412')")
	//	PoMaster findByPoNoAndCompanyNo(@Param("refPoNo") String refPoNo, @Param("companyNo") String companyNo);
}

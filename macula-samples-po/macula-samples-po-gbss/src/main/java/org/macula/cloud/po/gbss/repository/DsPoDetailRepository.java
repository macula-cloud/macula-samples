/**
 * 
 */
package org.macula.cloud.po.gbss.repository;

import java.util.List;

import org.macula.cloud.po.domain.PoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsPoDetailRepository</b> 是
 * </p>
 * 
 
 
 
 * 
 */
public interface DsPoDetailRepository extends JpaRepository<PoDetail, Long> {
	//	/**
	//	 * 根据订单号查询订单详细信息
	//	 * 
	//	 * @param poNo
	//	 * @return
	//	 */
	//	public List<PoDetail> findByPoNo(String poNo);
	//
	//	/**
	//	 * 根据订单号查询订单明细，支持分页
	//	 * 
	//	 * @param poNo
	//	 * @param pageable
	//	 * @return
	//	 */
	//	public Page<PoDetail> findByPoNo(String poNo, Pageable pageable);
	//
	//	/**
	//	 * 查找BOM子产品
	//	 * 
	//	 * @param poNo
	//	 * @param productBomCode
	//	 * @return
	//	 */
	//	public List<PoDetail> findByPoNoAndProductBomCode(String poNo, String productBomCode);
	//
	//	/**
	//	 * 根据关联的订单号获取订单详细对象列表
	//	 * 
	//	 * @param refPoNo
	//	 * @return
	//	 */
	//	/* public List<PoDetail> findByRefDocNo(String refDocNo); */
	//
	//	public PoDetail findByPoNoAndProductCode(String poNo, String productCode);
	//
	//	//GBSSYW-3797
	//	/**
	//	 * 根据关联的订单号获取订单详细对象列表,排除subbom
	//	 * @param poNo
	//	 * @param productCode
	//	 * @return
	//	 */
	//	@Query("from PoDetail t where t.poNo in (select poNo from PoMaster where (poNo = :poNo or refPoNo = :poNo)"
	//			+ " and poProcessCode not in ('G305','G306','G105')) and t.productCode=:productCode and t.productAttr<>2")
	//	public PoDetail findByPoNoAndProductCodeNoSubBom(@Param("poNo") String poNo, @Param("productCode") String productCode);
	//
	//	/**
	//	 * 根据订单号得到订单详情与产品详细信息表关联（正式订单）
	//	 */
	//	@Query("select pd.productFullName,pd.packageQty,pd.unitMeasure,pd.packageUnit,t.salePrice,t.salePoint," + "t.saleQty "
	//			+ "from PoMaster p,PoDetail t,ProductInfo pd " + "where p.poNo=t.poNo and t.productCode=pd.productCode and p.poNo=:poNo")
	//	List<Object[]> getPoDetailByPoNo(@Param("poNo") String poNO);
	//
	//	/**
	//	 * 查看订单详细中用到的产品详细信息
	//	 */
	//	@Query("select pd.saleQty,pd.productCode,pi.productFullName,pi.productSubType,pd.salePrice," + "pd.promProductAttr,pd.productAttr,pd.salePoint "
	//			+ "from ProductInfo pi,PoMaster pm,PoDetail pd " + "where pm.poNo=pd.poNo and pd.productCode=pi.productCode and pm.poNo=:poNo")
	//	List<Object[]> getPoDetailByPoNOInLook(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 查看订单详情
	//	 * 
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select b.productCode,c.listFullName, a.productShortName,b.salePrice,b.saleQty,a.unitMeasure,"
	//			+ "b.promProductAttr,b.salePoint,b.productType,b.productAttr,b.lineNo,a.productFullName,b.id,a.directSale,a.epc "
	//			+ "from PoDetail b,ProductListTypeInfo c,ProductInfo a "
	//			+ "where b.productCode=a.productCode and c.listTypeCode = a.productListType and b.poNo=:poNo order by b.lineNo asc")
	//	public List<?> findDetailByPoNo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 查看订单详情
	//	 * 
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select b.productCode,c.listFullName, a.productShortName,b.salePrice,b.saleQty,a.unitMeasure,"
	//			+ "b.promProductAttr,b.salePoint,b.productType,b.productAttr,b.lineNo,a.productFullName,b.id "
	//			+ "from PoDetail b,ProductListTypeInfo c,ProductInfo a "
	//			+ "where b.productCode=a.productCode and c.listTypeCode = a.productListType and b.poNo=:poNo and b.productAttr != 1 order by b.lineNo asc")
	//	public List<?> queryDetailByPoNo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 订单类型
	//	 * 
	//	 * @param poNo
	//	 * @return
	//	 */
	//	@Query("select a.poProcessDesc from PoProcessCodeInfo a " + "where a.poProcessCode = (select p.poProcessCode from PoMaster p where p.poNo=:poNo)")
	//	public List<?> findType(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 选择当前最大的行号
	//	 */
	//	@Query("select max(case when lineNo is null then 0 else lineNo end) from PoDetail where poNo =:poNo")
	//	int findMaxLineNo(@Param("poNo") String poNo);
	//
	//	/**
	//	 * 删除促销产品信息
	//	 * 
	//	 * @param poNo
	//	 */
	//	@Modifying
	//	@Query("delete from PoDetail where poNo =:poNo and promProductAttr='2' ")
	//	public void deletePoDetailList(@Param("poNo") String poNo);
	//
	//	/**
	//	 * jk10推广机
	//	 * 
	//	 * @param jk10codeSpread
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("select sum(pd.saleQty) from PoDetail pd where pd.productCode =:productCode "
	//			+ "and pd.poNo in (select pm.poNo from PoMaster pm where pm.poProcessCode ='G104' " + "and pm.orderDealerNo =:dealerNo)")
	//	public BigDecimal getJkSpecial(@Param("productCode") String jk10codeSpread, @Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 一个专卖店/自营店当前代发货的产品数量
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("select pd.productCode, sum(pd.saleQty) \n" + " from PoDetail pd, PoMaster pm, PoAddrDetail pad \n"
	//			+ " where pd.poNo = pm.poNo and pd.poNo = pad.poNo \n" + " and pad.deliveryType in ('05', '10') and pad.deliveryDealerNo = :dealerNo \n" //专卖店配送
	//			+ " and pad.pickUpStatus = 'N' \n" //未提货
	//			+ " and pm.paymentStatus = 'Y' \n" //已支付
	//			+ " and pm.poStatus in ('00', '01') \n" //订单状态为正常或冻结
	//			+ " and pm.poProcessCode in ('G001','G002','G003','G004','G005','G006','G007','G008','G010') \n"
	//			+ " and (pm.orderType in ('*PO', '*INV') or (pm.orderType ='*AD' and pm.refSelectedNo = pm.poNo )) \n"
	//			+ " and pd.productCode in (:productCodes) \n" + " group by pd.productCode")
	//	public List<?> getDelegateDeliveryProductQty(@Param("dealerNo") String dealerNo, @Param("productCodes") List<String> productCodeList);
	//
	//	/**
	//	 * 多个专卖店/自营店当前代发货的产品数量
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("select pad.deliveryDealerNo, pd.productCode, sum(pd.saleQty) \n" + " from PoDetail pd, PoMaster pm, PoAddrDetail pad \n"
	//			+ " where pd.poNo = pm.poNo and pd.poNo = pad.poNo \n"
	//			+ " and pad.deliveryType in ('05', '10') and pad.deliveryDealerNo in (:dealerNos) \n" //专卖店配送
	//			+ " and pad.pickUpStatus = 'N' \n" //未提货
	//			+ " and pm.paymentStatus = 'Y' \n" //已支付
	//			+ " and pm.poStatus in ('00', '01') \n" //订单状态为正常或冻结
	//			+ " and pm.poProcessCode in ('G001','G002','G003','G004','G005','G006','G007','G008','G010') \n"
	//			+ " and (pm.orderType in ('*PO', '*INV') or (pm.orderType ='*AD' and pm.refSelectedNo = pm.poNo )) \n"
	//			+ " and pd.productCode in (:productCodes) \n" + " group by pad.deliveryDealerNo, pd.productCode")
	//	public List<?> getDelegateDeliveryProductQty(@Param("dealerNos") List<String> dealerNoList, @Param("productCodes") List<String> productCodeList);
	//
	//	/**
	//	 * 分公司当前代发货的产品数量
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("select s.saleBranchDesc, pd.productCode, sum(pd.saleQty) \n"
	//			+ " from PoDetail pd, PoMaster pm, PoAddrDetail pad, Dealer d, SaleBranchInfo s \n" + " where pd.poNo = pm.poNo and pd.poNo = pad.poNo \n"
	//			+ " and pad.deliveryType in ('05', '10') and pad.deliveryDealerNo = d.dealerNo \n"
	//			+ " and d.saleBranchNo = s.saleBranchNo and s.saleBranchDesc in (:salebranchDescs) \n" + " and pad.pickUpStatus = 'N' \n" //未提货
	//			+ " and pm.paymentStatus = 'Y' \n" //已支付
	//			+ " and pm.poStatus in ('00', '01') \n" //订单状态为正常或冻结
	//			+ " and pm.poProcessCode in ('G001','G002','G003','G004','G005','G006','G007','G008','G010') \n"
	//			+ " and (pm.orderType in ('*PO', '*INV') or (pm.orderType ='*AD' and pm.refSelectedNo = pm.poNo )) \n"
	//			+ " and pd.productCode in (:productCodes) \n" + " group by s.saleBranchDesc, pd.productCode")
	//	public List<?> getBranchDelegateDeliveryProductQty(@Param("salebranchDescs") List<String> saleBranchDescList,
	//			@Param("productCodes") List<String> productCodeList);

	/**
	 * 根据订单号查询订单详细信息
	 * @param poNo
	 * @return
	 */
	public List<PoDetail> findByPoNoOrderByLineNoAsc(String poNo);

	//	/**
	//	 * 查询赠品总数
	//	 * @param productCode
	//	 * @param period
	//	 * @return
	//	 
	//	 */
	//	@Query("select sum(pd.saleQty) from PoMaster pm, PoDetail pd  " + "where pm.poNo=pd.poNo and pm.poPeriod=:period "
	//			+ " and pd.productCode=:productCode and pm.poWholePromCode=:promCode")
	//	public BigDecimal getTotalPobling(@Param("productCode") String productCode, @Param("period") String period, @Param("promCode") String promCode);
	//
	//	/**
	//	 * 根据多张单查询非退货单产品详情
	//	 * @param poNos
	//	 * @return
	//	 */
	//	@Query("from PoDetail p where p.poNo in (:poNos) and p.saleQty > 0")
	//	List<PoDetail> listByPoNos(@Param("poNos") List<String> poNos);
	//
	//	@Query("select abs(sum(pd.saleQty)) from PoDetail pd where pd.poNo in (select pm.poNo from PoMaster pm where pm.refPoNo = :poNo) and pd.productCode = :productCode")
	//	BigDecimal CountByPoNoAndProductCode(@Param("poNo") String poNo, @Param("productCode") String productCode);
}

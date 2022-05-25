package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.domain.PoAddrDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsPoAddrDetailRepository</b> 是
 * </p>
 *
 
 
 
 *
 */
public interface DsPoAddrDetailRepository extends JpaRepository<PoAddrDetail, Long> {

	/**
	 * 根据订单号查询订单配送地址信息
	 * @param poNo
	 * @return
	 */
	public PoAddrDetail findByPoNo(String poNo);

	//	/**
	//	 * 查询随货配送关联的正式订单配送信息
	//	 * @param poAppNo
	//	 * @return
	//	 */
	//	@Query("select p from PoAddrDetail p where p.poNo in ( select t.poNo from PoMaster t where t.refSelectedNo=?1)")
	//	List<PoAddrDetail> queryPoAddrDetailInfo(String poAppNo);
	//
	//	/**
	//	 * 更新指定触发交货文件号的PoAddrDetail表数据
	//	 * 
	//	 * @param genPlanDocNo
	//	 * @param deliveryWhLocCode
	//	 * @param deliveryWhLocCode
	//	 */
	//
	//	@Modifying
	//	@Query("update PoAddrDetail set deliveryWhLocCode=:deliveryWhLocCode where genPlanDocNo=:genPlanDocNo")
	//	public void updatePoAddrDetail(@Param("deliveryWhLocCode") String deliveryWhLocCode, @Param("genPlanDocNo") String genPlanDocNo);
	//
	//	/**
	//	 * 查询 收货信息
	//	 * @param poNo
	//	 * @param validate
	//	 * @return
	//	 */
	//	@Query("select p from PoAddrDetail p where p.poNo=:poNo and p.pickUpVerifyCode=:pickUpVerifyCode")
	//	public List<PoAddrDetail> findDetail(@Param("poNo") String poNo, @Param("pickUpVerifyCode") String pickUpVerifyCode);

	/**
	 * 更新订单状态
	 * (属于自己的铺单)
	 * @param poNo
	 * @param validate
	 */
	//	@Modifying
	//	@Query("update PoAddrDetail set pickUpStatus=:status ,pickUpDate=sysdate where poNo in " + "(select a.poNo from PoMaster a where a.poNo=:poNo or "
	//			+ "(a.refSelectedNo=:poNo and a.orderDealerNo=(select b.orderDealerNo from PoMaster b where b.poNo=:poNo)))")
	//	public void updateStatu(@Param("poNo") String poNo, @Param("status") String status);

	//GBSSYW-1930 服务中心提货时，更新PO_ADDR_DETAIL.delivery_plan_proc_flag = Y
	/**
	 * 更新订单状态 服务中心提货使用
	 * (属于自己的铺单)
	 * @param poNo
	 * @param validate
	 */
	//	@Modifying
	//	@Query("update PoAddrDetail set pickUpStatus=:status,deliveryPlanProcFlag=:flag,pickUpDate=sysdate where poNo in "
	//			+ "(select a.poNo from PoMaster a where a.poNo=:poNo or "
	//			+ "(a.refSelectedNo=:poNo and a.orderDealerNo=(select b.orderDealerNo from PoMaster b where b.poNo=:poNo)))")
	//	public void updateStatu_service(@Param("poNo") String poNo, @Param("status") String status, @Param("flag") String flag);

	//	/**
	//	 * 修改订单发货状态
	//	 * @param poNoList
	//	 */
	//	@Modifying
	//	@Query("update PoAddrDetail a set a.deliveryStatus='B' where a.poNo in :poNoList")
	//	public void updateDeliveryStatusInfo(@Param("poNoList") List<String> poNoList);
	//
	//	/**
	//	 * 没有触发发货的PO单
	//	 * @param deliveryDealerNo
	//	 * @return
	//	 */
	//	@Query(" from PoAddrDetail p where p.deliveryDealerNo=:deliveryDealerNo and p.deliveryPlanDocNo is null")
	//	public List<PoAddrDetail> findPoAddrDetails(@Param("deliveryDealerNo") String deliveryDealerNo);

	/**
	 * 检查是否是发货状态？
	 * @param poNo
	 * @return
	 */
	//	@Query("select count(*) from PoAddrDetail pad, PoShipmentMaster psm"
	//			+ " where pad.deliveryPlanDocNo = psm.genPlanDocNo and psm.logisticsStatus = 'C' and pad.poNo = :poNo")
	//	public Long checkSalesReturn(@Param("poNo") String poNo);

	//	/**
	//	 * 运单号查询
	//	 * @param shipmentNo
	//	 * @return
	//	 
	//	 */
	//	@Deprecated
	//	public List<PoAddrDetail> findByShipmentNo(String shipmentNo);

	//GBSQLYH-41 根据shipmentNo获取 List<PoAddrDetail> 此方法替代 findByShipmentNo 方法
	/**
	 * 根据shipmentNo获取 PoAddrDetail 对象 List
	 * @param poNo List
	 * @return
	 */
	//	@Modifying
	//	@Query("select p from PoAddrDetail p where p.poNo in (select distinct t.poNo from PoShipmentDetail t "
	//			+ "where t.shipmentNo=:shipmentNo and t.poNo is not null)")
	//	public List<PoAddrDetail> findListByShipmentNo(@Param("shipmentNo") String shipmentNo);

	/**
	 * 根据单号查询订货配送地址信息
	 * @param poNo 原主单号
	 * @return
	 */
	//	@Query(value = "select * from CBS.PO_ADDR_DETAIL p where p.PICK_UP_STATUS = 'N' "
	//			+ "and p.PO_NO in (select PO_NO from PO_MASTER where PO_PROCESS_CODE = :poProcessCode "
	//			+ "and PO_PERIOD = :period and (PO_NO = :poNo or REF_PO_NO = :poNo))", nativeQuery = true)
	//	List<PoAddrDetail> listNoPickUp(@Param("poNo") String poNo, @Param("period") String period, @Param("poProcessCode") String poProcessCode);
}

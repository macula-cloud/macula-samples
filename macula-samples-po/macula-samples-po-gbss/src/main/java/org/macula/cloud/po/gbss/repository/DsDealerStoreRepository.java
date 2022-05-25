/*
 * Copyright (c) 2013 Infinitus (China) Company Ltd. All rights reserved.
 *
 * This software is the confidential and proprietary information of Infinitus(China) Company Ltd. 
 * You shall not disclose such Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Infinitus.
 * 
 * Amendment History:
 * 
 * Amend by    Amend On      Version    Description
 * Andy        2013.10.24    1.1        RQ1.18 - 增加“是否专卖店”信息
 */

/**
 * DsDealerStoreRepository.java 2011-7-11
 */
package org.macula.cloud.po.gbss.repository;

import org.macula.cloud.po.gbss.domain.DealerStore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <b>DsDealerStoreRepository</b> is 专卖店资格信息的DAO
 * </p>
 * 
 
 
 
 *          $
 * 
 */
public interface DsDealerStoreRepository extends JpaRepository<DealerStore, Long> {
	//	/**
	//	 * 根据条件查找专卖店信息
	//	 * 
	//	 * @param dealerNo
	//	 * @param storeRunType
	//	 * @param storeRunStatus
	//	 * @return
	//	 */
	//	DealerStore findByStoreNoAndStoreRunTypeAndStoreRunStatus(String storeNo, String storeRunType, String storeRunStatus);
	//
	//	/**
	//	 * 根据条件查找专卖店信息
	//	 * 
	//	 * @param storeAuthCode
	//	 * @param storeRunType
	//	 * @param storeRunStatus
	//	 * @return
	//	 */
	//	DealerStore findByStoreAuthCodeAndStoreRunTypeAndStoreRunStatus(String storeAuthCode, String storeRunType, String storeRunStatus);
	//
	//	/**
	//	 * 根据storeRunType查找专卖店信息
	//	 * 
	//	 * @param storeRunType
	//	 * @return
	//	 */
	//	List<DealerStore> findByStoreRunType(String storeRunType);
	//
	//	/**
	//	 * 根据登录卡号查询店信息
	//	 */
	//	@Query("select storeAddrProvince,storeAddrCity,storeAddrCounty,storeAddrTail01,storeTele,dealerFullName,storeAuthCode,storePrincipal"
	//			+ " from DealerStore where storeNo=:loginerNo and rownum <= 1")
	//	List<?> getStoreInfo(String loginerNo);

	/**
	 * 查询店铺信息
	 * 
	 * @param storeNo
	 * @return
	 */
	public DealerStore findByStoreNo(String storeNo);

	//	List<DealerStore> findByDealerNo(String dealerNo);
	//
	//	//Add by Andy 2013.10.24
	//	/**
	//	 * 查询店铺信息，按店铺运作类型增序排序
	//	 */
	//	public List<DealerStore> findByDealerNoOrderByStoreRunTypeAsc(String dealerNo);
	//
	//	/**
	//	 * 查询店铺信息
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	// public DealerStore findByDealerNo(String dealerNo);
	//
	//	@Query(" select a from DealerStore a where a.dealerNo = :dealerNo and  a.storeRunType in ('1','4')")
	//	public DealerStore findStoreByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 查询店铺信息包含服务中心
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	// public DealerStore findByDealerNo(String dealerNo);
	//
	//	@Query(" select a from DealerStore a where a.dealerNo = :dealerNo and  a.storeRunType in ('1','4','8')")
	//	public DealerStore findPosStoreByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 根据卡号查询可订货的店铺(专卖店、可订货工作室)
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" select a from DealerStore a where a.dealerNo = :dealerNo and  a.storeRunType in ('1','4')")
	//	public DealerStore findCanOrderStoreByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 查找根据专卖店卡号查找有效的专卖店(包括经销商)
	//	 */
	//	@Query(" select a from DealerStore a where a.storeNo in (select b.storeNo from DealerStoreStatus b " + " where b.inactiveDate is null "
	//			+ " and (b.storeRunType=:storeRunType or b.storeRunType=2 or b.storeRunType=4)"
	//			+ " and b.storeNo=:dealerNo and b.storeRunStatus in ('00','01','02'))  ")
	//	DealerStore findDealerStoreByDealerNoIsValid(@Param("dealerNo") String dealerNo, @Param("storeRunType") String storeRunType);
	//
	//	/**
	//	 * 查找根据专卖店卡号查找有效的专卖店(包括经销商,自营店)
	//	 */
	//	@Query(" select a from DealerStore a where a.storeNo in (select b.storeNo from DealerStoreStatus b " + " where b.inactiveDate is null "
	//			+ " and (b.storeRunType=1 or b.storeRunType=4 or b.storeRunType=8)"
	//			+ " and b.storeNo=:dealerNo and b.storeRunStatus in ('00','01','02'))  ")
	//	DealerStore findDealerStoreByDealerNoIsValid(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 查找根据专卖店授权编码查找有效的专卖店
	//	 */
	//	@Query("select a from DealerStore a where a.storeAuthCode=:storeAuthCode and a.storeNo in "
	//			+ "(select b.storeNo from DealerStoreStatus b where b.storeRunType='1' and b.inactiveDate is null and "
	//			+ "b.storeRunStatus in ('00','01','02'))")
	//	DealerStore findDealerStoreByStoreAuthCodeIsValid(@Param("storeAuthCode") String storeAuthCode);
	//
	//	/**
	//	 * 查找根据专卖店授权编码查找有效的专卖店
	//	 */
	//	@Query("select a from DealerStore a where a.storeAuthCode=:storeAuthCode")
	//	DealerStore findByAuthCode(@Param("storeAuthCode") String storeAuthCode);
	//
	//	/**
	//	 * 查找根据店铺授权编码查找有效的专卖店或工作室 add by ky_qbq 2014-06-19
	//	 */
	//	@Query("select a from DealerStore a where a.storeAuthCode=:storeAuthCode and a.storeNo in "
	//			+ "(select b.storeNo from DealerStoreStatus b where b.storeRunType in ('1','4','5') and b.inactiveDate is null and "
	//			+ "b.storeRunStatus in ('00','01','02'))")
	//	DealerStore findIsValidDealerStoreByStoreAuthCode(@Param("storeAuthCode") String storeAuthCode);
	//
	//	/**
	//	 * 根据卡号查找有效且有订货权限的专卖店或工作室信息 
	//	 */
	//	@Query("select new map(a.dealerNo as dealerNo,d.fullName as dealerName,a.storeTele as contactTele,"
	//			+ "a.storeRunType as storeRunType) from DealerStore a,DealerStoreStatus b,Dealer d"
	//			+ " where a.storeNo=:dealerNo and d.dealerNo=a.dealerNo and b.inactiveDate is null"
	//			+ " and a.storeRunType in ('1','4') and a.allowPo=true and a.storeNo=b.storeNo) ")
	//	Map<String, Object> findDealerStoreByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 根据卡号查找有效专卖店或工作室信息  add by ky_qbq 2014-06-19
	//	 */
	//	@Query("select new map(a.dealerNo as dealerNo,d.fullName as dealerName,a.storeTele as contactTele,"
	//			+ "a.storeRunType as storeRunType) from DealerStore a,DealerStoreStatus b,Dealer d"
	//			+ " where a.storeNo=:dealerNo and d.dealerNo=a.dealerNo and b.inactiveDate is null"
	//			+ " and a.storeRunType in ('1','4','5') and a.storeNo=b.storeNo) ")
	//	Map<String, Object> findIsValidDealerStoreByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 获取地址
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select new map(a.storeAddrProvince||a.storeAddrCity||a.storeAddrCounty||a.storeAddrTail01||a.storeAddrTail02 "
	//			+ "as storeAddress,a.storeTele as storeTel) from DealerStore a where a.storeNo=:storeNo")
	//	Map<String, Object> getAddressByStoreNo(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 卡号关信息
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("select new map(d.dealerNo as dealerNo,d.fullName as fullName,dti.dealerTypeDesc as dealerType,dpi.certificateNo as certificateNo)"
	//			+ " from Dealer d,DealerPersonalInfo dpi,DealerTypeInfo dti where d.dealerNo=dpi.dealerNo and d.dealerType=dti.dealerType and d.dealerNo=:dealerNo")
	//	Map<String, Object> getDealerPersonalInfo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 寻找一个地区的专卖店（不包括工作室）的店号，用于判断空白地区
	//	 * 
	//	 * @param province
	//	 * @param city
	//	 * @param county
	//	 * @return
	//	 */
	//	@Query("select d.storeNo from DealerStore d where d.storeAddrProvince = :province and d.storeAddrCity = :city and d.storeAddrCounty = :county and d.storeRunType = '1' and d.storeRunStatus in ('00', '01', '02', '11')")
	//	List<String> findStoresInArea(@Param("province") String province, @Param("city") String city, @Param("county") String county);
	//
	//	/**
	//	 * 顾客管理:归属地转换，找出当前卡号的专卖店分公司片区和转换目标不一致的专卖店
	//	 * 
	//	 * @param dealerNo
	//	 * @param toBranchNo
	//	 * @param toZoneNo
	//	 * @return 店号
	//	 */
	//	@Query("select d.storeNo from DealerStore d, DealerStoreStatus s where d.storeRunType = '1' and d.storeRunStatus not in ('12', '99') and s.inactiveDate is null"
	//			+ " and d.storeNo = s.storeNo and d.dealerNo = :dealerNo" + " and ( s.saleBranchNo <>:toBranchNo or s.saleZoneNo <>:toZoneNo )")
	//	public List<String> getStoreNosOfUnmatchedLocale(@Param("dealerNo") String dealerNo, @Param("toBranchNo") String toBranchNo,
	//			@Param("toZoneNo") String toZoneNo);
	//
	//	/**
	//	 * 当前卡号 未关闭的平台 信息
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" from DealerStore ds where ds.dealerNo=:dealerNo and ds.storeRunType in('1','4','5') and ds.storeRunStatus not in('99')")
	//	List<DealerStore> getDealerStoreInfo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 判断当前平台是否是专卖店或者可订货工作室，且资料是否齐全
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select ds.storeNo from DealerStore ds, Dealer d, DealerStoreStatus dss where ds.storeNo = d.dealerNo and ds.storeNo = dss.storeNo and ds.storeRunStatus not in ('99') and ds.storeRunType in ('1','4') and ds.storeNo =:storeNo")
	//	List<String> findPlatformInfo(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 平台列表信息()
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" select new map(ds.storeNo as storeNo,ds.dealerNo as dealerNo,d.fullName as dealerFullName,ds.storeRunType as storeRunType,(case when ds.storeRunType=1 then '专卖店' "
	//			+ " when ds.storeRunType=4 then '可订货工作室' when ds.storeRunType=5 then '不可订货工作室'"
	//			+ " else '其它' end) as runType,ds.storeAddrProvince||ds.storeAddrCity||ds.storeAddrCounty"
	//			+ "||ds.storeAddrTail01||ds.storeAddrTail02 as storeAddr,dss.saleBranchNo as saleBranchNo,"
	//			+ " sbi.saleBranchDesc as saleBranchDesc,sri.saleRegionNo as saleRegionNo,sri.saleRegionDesc as saleRegionDesc)"
	//			+ " from DealerStore ds,DealerStoreStatus dss,SaleRegionInfo sri,SaleBranchInfo sbi,Dealer d "
	//			+ " where ds.dealerNo =:dealerNo and d.dealerNo=ds.dealerNo and ds.storeRunStatus in ('00','01','02','11', '12') and ds.storeRunType in ('1','4','5') and ds.storeNo=dss.storeNo"
	//			+ " and (dss.inactiveDate is null or dss.inactiveDate> sysdate) and dss.saleBranchNo = sbi.saleBranchNo"
	//			+ " and sri.saleRegionNo = sbi.saleRegionNo")
	//	List<Map<String, Object>> findDealerStoreInfo(@Param("dealerNo") String dealerNo);
	//
	//	// /**
	//	// *
	//	// * @param storeNo
	//	// * @return
	//	// */
	//	// //去除查询条件 and ds.storeRunStatus in ('00','01','02','12')
	//	// @Query(" select new map(ds.storeNo as storeNo,ds.dealerNo as dealerNo,d.fullName as dealerFullName,"
	//	// + // ds.dealerFullName as dealerFullName," +
	//	// "(case when ds.storeRunType=1 then '专卖店' " +
	//	// " when ds.storeRunType=4 then '可订货工作室' when ds.storeRunType=5 then '不可订货工作室'"
	//	// +
	//	// " else '其它' end) as runType,ds.storeAddrProvince||ds.storeAddrCity||ds.storeAddrCounty"
	//	// +
	//	// "||ds.storeAddrTail01||ds.storeAddrTail02 as storeAddr,ds.storeRunType as storeRunType,dss.saleBranchNo as saleBranchNo,"
	//	// +
	//	// " sbi.saleBranchDesc as saleBranchDesc,sri.saleRegionNo as saleRegionNo,sri.saleRegionDesc as saleRegionDesc,"
	//	// +
	//	// " dpi.certificateNo as certificateNo,d.dealerPostCode as dealerPostCode,"
	//	// +
	//	// "(case when d.sex='F' then '女' when d.sex='M' then '男' else '' end) as sex,"
	//	// +
	//	// " (case when d.registerSpouse=true then '是' else '否' end) as isRegisterSpouse,"
	//	// +
	//	// " szi.saleZoneDesc  as saleZoneDesc," +
	//	// " (select aab.arAvailableAmt from ArAccBalance aab where aab.dealerNo=d.dealerNo) as arAvailableAmt,"
	//	// +
	//	// "  (select da.bankCode from DealerAccount da where da.dealerNo=d.dealerNo)  as bankCode,"
	//	// +
	//	// "  (select (case when dd.enabled=true then '有效' else '无效' end) from DealerAccount dd where dd.dealerNo=d.dealerNo)  as isEnabled)"
	//	// +
	//	// " from DealerStore ds,DealerStoreStatus dss,SaleRegionInfo sri,SaleBranchInfo sbi,SaleZoneInfo szi,DealerPersonalInfo dpi,"
	//	// +
	//	// " Dealer d " +
	//	// " where ds.storeNo =:storeNo and ds.storeNo=dss.storeNo" +
	//	// " and (dss.inactiveDate is null or dss.inactiveDate> sysdate) and d.dealerNo = ds.dealerNo and d.saleBranchNo = sbi.saleBranchNo"
	//	// +
	//	// " and sri.saleRegionNo = sbi.saleRegionNo and szi.saleZoneNo = d.saleZoneNo and dpi.dealerNo = ds.dealerNo")
	//	// Map<String, Object> getStoreInfoByStoreNo(@Param("storeNo") String
	//	// storeNo);
	//
	//	/**
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" from DealerStore ds where ds.dealerNo=:dealerNo and ds.storeRunStatus <> '99'")
	//	List<DealerStore> getEffectiveStore(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 获取生效的工作室列表
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" from DealerStore ds where ds.dealerNo=:dealerNo and ds.storeRunType in ('" + TypeDealerStore.STORE_RUN_TYPE_04 + "','"
	//			+ TypeDealerStore.STORE_RUN_TYPE_05 + "') and ds.storeRunStatus <> '" + TypeDealerStore.STORE_RUN_STATUS_99 + "'")
	//	List<DealerStore> getEffectiveStudio(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 平台去除订货权限
	//	 * 
	//	 * @param storeNoList
	//	 */
	//	@Modifying
	//	@Query("update DealerStore set allowPo=false,last_updated_time=sysdate,last_updated_by=:lastUpdatedBy where storeNo in (:storeNoList)")
	//	void setAllowPoFalseHQL(@Param("storeNoList") List<String> storeNoList, @Param("lastUpdatedBy") String lastUpdatedBy);
	//
	//	/**
	//	 * 当前卡号 未关闭的平台 信息
	//	 * 
	//	 * @param dealerNo
	//	 * @param storeRunType
	//	 * @return
	//	 */
	//	@Query(" from DealerStore ds where ds.dealerNo=:dealerNo and ds.storeRunType=:storeRunType and ds.storeRunStatus <> '"
	//			+ TypeDealerStore.STORE_RUN_STATUS_99 + "'")
	//	List<DealerStore> getDSInfo(@Param("dealerNo") String dealerNo, @Param("storeRunType") String storeRunType);
	//
	//	/**
	//	 * @param dealerNo
	//	 * @param appStoreRunType
	//	 * @return
	//	 */
	//	DealerStore findByStoreNoAndStoreRunType(String dealerNo, String appStoreRunType);
	//
	//	/**
	//	 * 平台列表信息()
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" select new map(extra.lastApprovalDate as lastApprovalDate, ds.storeNo as storeNo,ds.dealerNo as dealerNo,d.fullName as dealerFullName,ds.storeRunType as storeRunType, (case when ds.storeRunType=1 then '专卖店' "
	//			+ " when ds.storeRunType=4 then '可订货工作室' when ds.storeRunType=5 then '不可订货工作室' else '其它' end) as runType, "
	//			+ "dss.saleBranchNo as saleBranchNo, d.dealerPostCode as dealerPostCode, sbii.saleBranchDesc as dealerSaleBranchDesc, sbi.saleBranchDesc as saleBranchDesc, (CASE when d.sex ='F' then '女' when d.sex='M' then '男' end) AS sex,"
	//			+ " region.saleRegionDesc as saleRegionDesc, ds.storeAddrProvince as storeAddrProvince, ds.storeAddrCity as storeAddrCity, ds.storeAddrCounty as storeAddrCounty,"
	//			+ " (case when ds.storeRunStatus='00' then '新增' when ds.storeRunStatus='01' then '正常运作' when ds.storeRunStatus='02' then '不活跃'"
	//			+ "  when ds.storeRunStatus='11' then '暂停运作' when ds.storeRunStatus='12' then '停止运作' when ds.storeRunStatus='99' then '失效' else '其它' end) AS runStatus) "
	//			+ " from DealerStore ds,DealerStoreStatus dss, SaleBranchInfo sbi, SaleBranchInfo sbii, Dealer d , DealerStoreExtra extra, SaleRegionInfo region"
	//			+ " where ds.storeNo =:storeNo and d.dealerNo=ds.dealerNo and ds.storeRunStatus in ('00','01','02','11', '12') and ds.storeRunType in ('1','4','5') and ds.storeNo=dss.storeNo and extra.storeNo = ds.storeNo"
	//			+ " and (dss.inactiveDate is null or dss.inactiveDate> sysdate) and dss.saleBranchNo = sbi.saleBranchNo and sbii.saleBranchNo = d.saleBranchNo and sbi.saleRegionNo = region.saleRegionNo")
	//	Map<String, Object> fetchStoreBasicInfo(@Param("storeNo") String storeNo);
	//
	//	@Query(" select ds from DealerStore ds where storeNo = :storeNo and storeRunType in ('1','4','5') and storeRunStatus not in ('12', '99')")
	//	DealerStore getCertifiedStore(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 查询送货到达时间
	//	 * 
	//	 * @param addrAreaCode
	//	 * @param deliveryWhCode
	//	 * @return
	//	 */
	//	@Query("select a.sendArriveTime02 from PoJpWhArea a where a.whCode=:whCode and a.areaCode=:areaCode")
	//	List<?> findTime(@Param("areaCode") String addrAreaCode, @Param("whCode") String deliveryWhCode);
	//
	//	/**
	//	 * 获取联系方式
	//	 * 
	//	 * @param deliverWhCode
	//	 * @return
	//	 */
	//	@Query("select a.contactFax,a.contactTel from WhInfo a where a.whCode =:whCode")
	//	List<?> findFashion(@Param("whCode") String deliverWhCode);
	//
	//	/**
	//	 * 判断符合公司订货的专卖店
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select a from DealerStore a, Dealer b where a.storeNo = b.dealerNo and a.storeRunType='1' and a.storeRunStatus in ('00','01','02') "
	//			+ " and b.dealerType ='24' and a.storeNo= :storeNo ")
	//	public DealerStore getCompanyDealerStoreInfo1(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 判断符合公司订货的工作室
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query("select a from DealerStore a, Dealer b where a.storeNo = b.dealerNo and a.storeRunType='4' and a.allowPo=true "
	//			+ " and b.dealerType ='24' and a.storeNo= :storeNo ")
	//	public DealerStore getCompanyDealerStoreInfo2(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 根据卡号查找有效且有订货权限的专卖店或工作室信息
	//	 */
	//	// select
	//	// d.dealer_no,d.full_name,d.is_register_spouse,d.spouse_name,ds.store_run_type,ds.store_mobile,ds.store_tele
	//	// from dealer d,dealer_store ds
	//	// where d.dealer_no ='333925768' and d.dealer_no=ds.dealer_no
	//	@Query("select new map(d.dealerNo as dealerNo,d.fullName as dealerName,d.registerSpouse as registerSpouse,"
	//			+ "d.spouseName as spouseName,dpi.contactTele as tele,dpi.contactMobile as mobile,"
	//			+ "(select sei.dataFullDesc from SysEnumInfo sei where sei.fieldName = 'DEALER_TYPE' and sei.dataValue = d.dealerType) as storeRunTypeValue)"
	//			+ " from Dealer d,DealerPersonalInfo dpi" + " where d.dealerNo=dpi.dealerNo and d.dealerNo=:dealerNo ) ")
	//	Map<String, Object> findDealerStore(@Param("dealerNo") String loginDealerNo);
	//
	//	/**
	//	 * 
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	// 去除查询条件 and ds.storeRunStatus in ('00','01','02','12')
	//	@Query(" select new map(ds.storeNo as storeNo,ds.dealerNo as dealerNo,d.fullName as dealerFullName,dpi.certificateType as certificateType,to_char(dpi.birthday,'yyyy-mm-dd') as birthday,"
	//			+ // ds.dealerFullName as dealerFullName," +
	//			"(case when ds.storeRunType=1 then '专卖店' " + " when ds.storeRunType=4 then '可订货工作室' when ds.storeRunType=5 then '不可订货工作室'"
	//			+ " else '其它' end) as runType,ds.storeAddrProvince||ds.storeAddrCity||ds.storeAddrCounty"
	//			+ "||ds.storeAddrTail01||ds.storeAddrTail02 as storeAddr,ds.storeRunType as storeRunType,dss.saleBranchNo as saleBranchNo,"
	//			+ " sbi.saleBranchDesc as saleBranchDesc,sri.saleRegionNo as saleRegionNo,sri.saleRegionDesc as saleRegionDesc,"
	//			+ " dpi.certificateNo as certificateNo,d.dealerPostCode as dealerPostCode,"
	//			+ "(case when d.sex='F' then '女' when d.sex='M' then '男' else '' end) as sex,"
	//			+ " (case when d.registerSpouse=true then '是' else '否' end) as isRegisterSpouse," + " szi.saleZoneDesc  as saleZoneDesc,"
	//			+ " nvl((select aab.arAvailableAmt from ArAccBalance aab where aab.dealerNo=d.dealerNo),0) as arAvailableAmt,"
	//			+ "  (select da.bankCode from DealerAccount da where da.dealerNo=d.dealerNo)  as bankCode,"
	//			+ "  (select (case when dd.enabled=true then '有效' else '无效' end) from DealerAccount dd where dd.dealerNo=d.dealerNo)  as isEnabled)"
	//			+ " from DealerStore ds,DealerStoreStatus dss,SaleRegionInfo sri,SaleBranchInfo sbi,SaleZoneInfo szi,DealerPersonalInfo dpi,"
	//			+ " Dealer d " + " where ds.storeNo =:storeNo and ds.storeNo=dss.storeNo"
	//			+ " and (dss.inactiveDate is null or dss.inactiveDate> sysdate) and d.dealerNo = ds.dealerNo and dss.saleBranchNo = sbi.saleBranchNo"
	//			+ " and sri.saleRegionNo = sbi.saleRegionNo and szi.saleZoneNo = dss.saleZoneNo and dpi.dealerNo = ds.dealerNo")
	//	Map<String, Object> getStoreInfoByStoreNo(@Param("storeNo") String storeNo);
	//
	//	@Query(" select ds from DealerStore ds where storeNo = :storeNo and storeRunType in ('1', '4', '5') and storeRunStatus not in ('12', '99')")
	//	DealerStore getCertifiedStores(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 获取生效的工作室列表
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" from DealerStore ds where ds.dealerNo=:dealerNo and ds.storeRunType in :typeList and ds.storeRunStatus not in ('12', '99')")
	//	List<DealerStore> getEffectiveStores(@Param("dealerNo") String dealerNo, @Param("typeList") Collection<String> typeList);
	//
	//	// API method
	//
	//	/**
	//	 * 获取所有服务中心列表
	//	 * @return
	//	 */
	//	@Query("select a from DealerStore a where a.storeRunType = '8'")
	//	public List<DealerStore> getAllPosStore();
	//
	//	/**
	//	 * 增量方式获取所有服务中心列表
	//	 * @param lastUpdatedTime
	//	 * @return
	//	 */
	//	@Query("select a from DealerStore a where a.storeRunType = '8' and a.lastModifiedDate >= :lastUpdatedTime")
	//	public List<DealerStore> getAllPosStore(@Param("lastUpdatedTime") Date lastUpdatedTime);
	//
	//	/**
	//	 * @param 根据服务中心代码获取服务中心店铺信息
	//	 * @return
	//	 */
	//	@Query(" select a from DealerStore a where a.storeNo = :storeNo and  a.storeRunType='8' ")
	//	DealerStore getPosStoreByStoreNo(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 获得全部可以下单的卡号
	//	 * @return
	//	 */
	//	@Query(" select distinct a.dealerNo from DealerStore a where a.storeRunType not in ('9','5') and "
	//			+ " a.storeRunStatus not in ('99', '12') and a.allowSo = '1' ")
	//	public List<String> getDealerNoCanOrder();
	//
	//	/**
	//	 * 根据店铺号获取有效的专卖店（不包含可订货工作室）
	//	 * @param storeNo
	//	 * @return
	//	 */
	//	@Query(" select a from DealerStore a where a.storeNo = :storeNo and  a.storeRunType='1' and a.storeRunStatus <> '99' ")
	//	public DealerStore getStoreByStoreNo(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 根据店铺销售所属分公司获取有效的服务中心店铺信息
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("  select a from DealerStore a where a.storeNo =(select  distinct storeNo from  DealerStoreStatus "
	//			+ "where  saleBranchNo in(select  distinct saleBranchNo  from  DealerStoreStatus  where storeNo in("
	//			+ " select storeNo from DealerStore where dealerNo =:dealerNo and  storeRunType in ('1','4')) "
	//			+ "and storeRunType in ('1','4') and storeRunStatus = '01') " + "and storeRunStatus='01' and storeRunType ='8')")
	//	public DealerStore findValidStoreByDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 根据店铺销售所属分公司获取有效的服务中心店铺信息
	//	 * 
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("  select a from DealerStore a where a.storeNo =(select  distinct storeNo from  DealerStoreStatus "
	//			+ "where  saleBranchNo =:saleBranchNo and storeRunType ='8' and storeRunStatus = '01') "
	//			+ "and storeRunStatus='01' and storeRunType ='8')")
	//	public DealerStore findValidStoreBySaleBranchNo(@Param("saleBranchNo") String saleBranchNo);
	//
	//	/**
	//	 * 专卖店信息接口
	//	 * @return
	//	 
	//	 */
	//	@Query("  select new map(d.storeNo as store_no,wi.whCode as wh_code,wi.whName as wh_name,d.storeRunType as runType,"
	//			+ "d.dealerNo as dealerOwnerId,d.storeAuthCode as authCode,d.dealerFullName as ownerName,d.storeAddrZipCode as zipCode,"
	//			+ "d.storeAddrProvince as province,d.storeAddrCity as city,d.storeAddrCounty as country,d.storeAddrTail01 as detail1,"
	//			+ "d.storeAddrTail02 as detail2,d.storeMobile as cellPhone,d.storeTele as tele )"
	//			+ "from DealerStore d,PoStoreAddress psd,PoStoreWhArea pswa,WhInfo wi " + "where d.storeRunType ='1' and d.storeRunStatus = '01' "
	//			+ "and d.storeNo=psd.storeNo and pswa.areaCode=psd.addrAreaCode and wi.whCode=pswa.whCode")
	//	public List<Map<String, Object>> findStores();
	//
	//	/**
	//	 * 服务中心信息接口
	//	 * @return
	//	 
	//	 */
	//	@Query("  select new map(d.storeNo as store_no,wi.whCode as wh_code,wi.whName as wh_name,d.storeRunType as runType,"
	//			+ "d.dealerNo as dealerOwnerId,d.storeAuthCode as authCode,d.dealerFullName as ownerName,d.storeAddrZipCode as zipCode,"
	//			+ "d.storeAddrProvince as province,d.storeAddrCity as city,d.storeAddrCounty as country,d.storeAddrTail01 as detail1,"
	//			+ "d.storeAddrTail02 as detail2,d.storeMobile as cellPhone,d.storeTele as tele )"
	//			+ "from DealerStore d,WhInfo wi where d.storeRunType = '8' and d.storeRunStatus = '01' and wi.whCode=('S'+d.storeNo)")
	//	public List<Map<String, Object>> findPosStores();
	//
	//	@Query(" select dse from DealerStore ds ,DealerStoreExtra dse" + " where ds.storeNo = dse.storeNo " + "   and ds.storeRunType = '1' "
	//			+ "   and ds.storeRunStatus <> '12' " + "   and ds.storeNo = :storeNo ")
	//	DealerStoreExtra getDealerStoreCreateTime(@Param("storeNo") String storeNo);
	//
	//	/**
	//	 * 查询店铺信息，根据地区
	//	 * zdm 报备二期 2016-8-16  修改
	//	 */
	//	@Query("from DealerStore ds where ds.storeAddrCity=:storeAddrCity and ds.storeAddrCounty =:storeAddrCounty and ds.storeRunType = '1' and ds.storeRunStatus in('00','01','02')")
	//	public List<DealerStore> findByStoreAddrCounty(@Param("storeAddrCity") String storeAddrCity, @Param("storeAddrCounty") String storeAddrCounty);
	//
	//	/**
	//	 * 查询店铺信息，根据卡号 zdm 2016-07-13
	//	 */
	//	@Query("from DealerStore ds where ds.dealerNo =:dealerNo and ds.storeRunType in ('1','4') and ds.storeRunStatus in('00','01','02')")
	//	public List<DealerStore> findByStoreDealerNo(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 查询有效店铺运作信
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query(" select ds from DealerStoreStatus dss,DealerStore ds " + " where dss.storeNo = ds.storeNo " + "   and dss.storeRunType = ds.storeRunType"
	//			+ "   and (dss.inactiveDate is null or dss.inactiveDate > sysdate)" + "   and ds.dealerNo = :dealerNo "
	//			+ " order by dss.effectiveDate desc ")
	//	public List<DealerStore> getDealerNoBeforeJuly(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 获取某卡号下可进行销售业务专卖店
	//	 * @param dealerNo
	//	 * @return
	//	 */
	//	@Query("from DealerStore a where a.dealerNo = :dealerNo and a.storeRunType in ('1') and a.storeRunStatus in ('00','01','02','11')")
	//	List<DealerStore> listCanSalesDealerStore(@Param("dealerNo") String dealerNo);
	//
	//	/**
	//	 * 根据店铺编号更新专卖店有效星级及星级有效期
	//	 * @param storeNo 店铺编号
	//	 * @param storeClass01 有效星级
	//	 * @param starLevelBeginPeriod 有效星级开始月份
	//	 * @param starLevelEndPeriod 有效星级结束月份
	//	 */
	//	@Modifying
	//	@Query("update DealerStore ds set ds.storeClass01 = :storeClass01, ds.starLevelBeginPeriod = :starLevelBeginPeriod, ds.starLevelEndPeriod = :starLevelEndPeriod where ds.storeNo = :storeNo ")
	//	public void updateDealerStoreStarLevel(@Param("storeNo") String storeNo, @Param("storeClass01") String storeClass01,
	//			@Param("starLevelBeginPeriod") String starLevelBeginPeriod, @Param("starLevelEndPeriod") String starLevelEndPeriod);
	//
	//	/**
	//	 * 根据店铺号获去生效的店铺信息
	//	 * @param dealerNo
	//	 * @param storeRunTypes
	//	 * @param storeRunStatus
	//	 * @return
	//	 */
	//	@Query(" from DealerStore ds where ds.dealerNo=:dealerNo and ds.storeRunType in :storeRunTypes and ds.storeRunStatus in :storeRunStatus")
	//	public List<DealerStore> listDealerStoreInfo(@Param("dealerNo") String dealerNo, @Param("storeRunTypes") List<String> storeRunTypes,
	//			@Param("storeRunStatus") List<String> storeRunStatus);

}

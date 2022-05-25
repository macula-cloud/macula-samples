/**
 * SaveDealerOrderThreadHelpServiceImpl.java 2012-08-15
 */
package org.macula.cloud.po.gbss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.macula.cloud.po.domain.PoAddrDetail;
import org.macula.cloud.po.domain.PoDetail;
import org.macula.cloud.po.domain.PoDetailDiscount;
import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.domain.PoPaymentDetail;
import org.macula.cloud.po.domain.PoProcessCodeInfo;
import org.macula.cloud.po.domain.PosAccTranDetail;
import org.macula.cloud.po.domain.SaleBranchInfo;
import org.macula.cloud.po.domain.SapDailyUplPo;
import org.macula.cloud.po.domain.SapDsMapping;
import org.macula.cloud.po.domain.SysCompany;
import org.macula.cloud.po.domain.SysEnumInfo;
import org.macula.cloud.po.domain.SysParamInfo;
import org.macula.cloud.po.gbss.domain.Dealer;
import org.macula.cloud.po.gbss.domain.DealerCustomer;
import org.macula.cloud.po.gbss.domain.DealerDeleteBase;
import org.macula.cloud.po.gbss.domain.DealerStore;
import org.macula.cloud.po.gbss.domain.DealerStoreExtra;
import org.macula.cloud.po.gbss.domain.PoAreaInfo;
import org.macula.cloud.po.gbss.domain.PoStoreAddress;
import org.macula.cloud.po.gbss.repository.DealerCustomerRepository;
import org.macula.cloud.po.gbss.repository.DealerDeleteBaseRepository;
import org.macula.cloud.po.gbss.repository.DealerSpDstrRepository;
import org.macula.cloud.po.gbss.repository.DealerStoreExtraRepository;
import org.macula.cloud.po.gbss.repository.DsBaseRepository;
import org.macula.cloud.po.gbss.repository.DsDealerRepository;
import org.macula.cloud.po.gbss.repository.DsDealerStoreRepository;
import org.macula.cloud.po.gbss.repository.DsMkpDlpPoRelateRepository;
import org.macula.cloud.po.gbss.repository.DsPoAddrDetailRepository;
import org.macula.cloud.po.gbss.repository.DsPoAreaInfoRepository;
import org.macula.cloud.po.gbss.repository.DsPoDetailRepository;
import org.macula.cloud.po.gbss.repository.DsPoMasterRepository;
import org.macula.cloud.po.gbss.repository.DsPoPaymentDetailRepository;
import org.macula.cloud.po.gbss.repository.DsPoStoreAddressRepository;
import org.macula.cloud.po.gbss.repository.DsSaleBranchInfoRepository;
import org.macula.cloud.po.gbss.repository.DsSapDailyUplPoRepository;
import org.macula.cloud.po.gbss.repository.DsSapDsMappingRepository;
import org.macula.cloud.po.gbss.repository.PoDetailDiscountRepository;
import org.macula.cloud.po.gbss.repository.PoInvoiceInfoRepository;
import org.macula.cloud.po.gbss.repository.PoProcessCodeInfoRepository;
import org.macula.cloud.po.gbss.repository.PosAccTranDetailRepository;
import org.macula.cloud.po.gbss.repository.PosDeliveryMasterRepository;
import org.macula.cloud.po.gbss.repository.PosIntercoListDetailRepository;
import org.macula.cloud.po.gbss.repository.PosReplenishDetailRepository;
import org.macula.cloud.po.gbss.repository.PosReplenishMasterRepository;
import org.macula.cloud.po.gbss.repository.SysCompanyRepository;
import org.macula.cloud.po.gbss.repository.SysEnumInfoRepository;
import org.macula.cloud.po.gbss.repository.SysParamInfoRepository;
import org.macula.cloud.po.gbss.sap.DealerOrderVo;
import org.macula.cloud.po.gbss.sap.POrderHeader;
import org.macula.cloud.po.gbss.sap.PlantResult;
import org.macula.cloud.po.gbss.sap.TCondition;
import org.macula.cloud.po.gbss.sap.TOrderItems;
import org.macula.cloud.po.gbss.type.PoStatusEnum;
import org.macula.cloud.po.gbss.type.TypeDeliveryType;
import org.macula.cloud.po.gbss.type.TypePoPaymentType;
import org.macula.cloud.po.gbss.type.TypeSapOrderType;
import org.macula.cloud.po.gbss.type.TypeSapPostingFlag;
import org.macula.cloud.po.gbss.type.TypeSapProcessCode;
import org.macula.cloud.po.gbss.util.DateFormatUtil;
import org.macula.cloud.po.gbss.util.DateTimeUtil;
import org.macula.cloud.po.gbss.util.SapFormatUtil;
import org.macula.cloud.po.type.ProductTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * <b>SaveDealerOrderThreadHelpServiceImpl</b>
 * </p>
 *
 
 
 
 *
 */
@Service
public class SaveDealerOrderThreadHelpServiceImpl implements SaveDealerOrderThreadHelpService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	//	@Resource
	//	private ComplexSapService complexSapService;
	@Resource
	private DsPoMasterRepository dsPoMasterRepository;
	@Resource
	private DsPoDetailRepository dsPoDetailRepository;
	@Resource
	private DsSapDsMappingRepository dsSapDsMappingRepository;
	@Resource
	private DsPoAddrDetailRepository dsPoAddrDetailRepository;
	@Resource
	private DsDealerRepository dsDealerRepository;
	@Resource
	private DsPoPaymentDetailRepository dsPoPaymentDetailRepository;
	@Resource
	private SysEnumInfoRepository sysEnumInfoRepository;
	@Resource
	private SysParamInfoRepository sysParamInfoRepository;
	@Resource
	private DsSaleBranchInfoRepository dsSaleBranchInfoRepository;
	@Resource
	private DsSapDailyUplPoRepository dsSapDailyUplPoRepository;
	@Resource
	private DsPoStoreAddressRepository dsPoStoreAddressRepository;
	@Resource
	private DsBaseRepository dsBaseRepository;
	@Resource
	private PosAccTranDetailRepository posAccTranDetailRepository;
	@Resource
	private PosDeliveryMasterRepository posDeliveryMasterRepository;
	@Resource
	private DealerSpDstrRepository dealerSpDstrRepository;
	@Resource
	private PoProcessCodeInfoRepository poProcessCodeInfoRepository;

	@Resource
	private DsSapDailyUplPoService dsSapDailyUplPoService;

	@Resource
	private PosReplenishMasterRepository posReplenishMasterRepository;

	@Resource
	private PosReplenishDetailRepository posReplenishDetailRepository;

	@Resource
	private PosIntercoListDetailRepository posIntercoListDetailRepository;

	@Resource
	private DealerDeleteBaseRepository dealerDeleteBaseRepository;

	@Resource
	private DsMkpDlpPoRelateRepository dsMkpDlpPoRelateRepository;//add by zhuohr 

	@Resource
	private PoInvoiceInfoRepository poInvoiceInfoRepository;
	@Resource
	private DealerStoreExtraRepository dealerStoreExtraRepository;
	@Resource
	private DsDealerStoreRepository dsDealerStoreRepository;
	@Resource
	private SysCompanyRepository sysCompanyRepository;
	@Resource
	private DealerCustomerRepository dealerCustomerRepository;
	@Resource
	private PoDetailDiscountRepository poDetailDiscountRepository;
	@Resource
	private DsPoAreaInfoRepository dsPoAreaInfoRepository;

	//以下三个Map的初始化放在initMap方法里
	private Map<String, String> deliveryTypeMap = new HashMap<String, String>();

	private Map<String, String> branchMap = new HashMap<String, String>();

	private Map<String, String> partnNumbMap = new HashMap<String, String>();

	@PostConstruct
	public void initMap() {
		initDeliveryTypeMap();
		initBranchMap();
		initPartnNumbMap();
	}

	private void initDeliveryTypeMap() {
		List<SysEnumInfo> list = sysEnumInfoRepository.findByTableNameAndFieldName("PO_APP_MASTER", "DELIVERY_TYPE");
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysEnumInfo info : list) {
				deliveryTypeMap.put(info.getDataValue(), info.getDataFullDesc());
			}
		}
	}

	private void initBranchMap() {
		List<SaleBranchInfo> list = dsSaleBranchInfoRepository.findAll();
		if (CollectionUtils.isNotEmpty(list)) {
			for (SaleBranchInfo info : list) {
				branchMap.put(info.getSaleBranchNo(), info.getSaleBranchNo());
			}
		}
	}

	private void initPartnNumbMap() {
		List<SysParamInfo> list = sysParamInfoRepository.findByParaScope("PARTN_NUMB");
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysParamInfo info : list) {
				partnNumbMap.put(info.getParaCode(), info.getParaValue());
			}
		}
	}

	/**
	 * add 非服务中心准备数据  SAP订单利润中心归属改为按照购货人卡号归属  by tanxiong
	 *
	 * @param sapDailyUplPo GBSS订单上传队列
	 * @return DealerOrderVo
	 * @throws Exception
	 */
	public DealerOrderVo prepareDataNotPos(SapDailyUplPo sapDailyUplPo) {
		//		try {
		//			numFormat.setGroupingUsed(false);
		// 上传订单的传入参数
		DealerOrderVo dealerOrderVo = new DealerOrderVo();

		//将全局的parentMap改到这里初始化.然后在参数中传递赋值
		Map<String, BigDecimal> parentMap = new HashMap<String, BigDecimal>();

		// 传入订单主表
		POrderHeader pOrderHeader = new POrderHeader();

		// 根据PONO找到订单
		if (sapDailyUplPo == null) {
			// 修改sapDailyUplPoV2的同步状态为‘1’,直到运行完或出错改回‘0’
			//				sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_1);
		} else {
			// 修改sapDailyUplPo的同步状态为‘1’,直到运行完或出错改回‘0’
			//				sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_1);
			//				dsSapDailyUplPoRepository.save(sapDailyUplPo);
		}
		PoMaster poMaster = dsPoMasterRepository.findByPoNo(sapDailyUplPo.getPoNo());
		if (poMaster == null) {
			return null; // return null; 意味着这张单据不会调上传订单sap接口
		}
		if (PoStatusEnum.PO_STATUS_99.getCode().equals(poMaster.getPoStatus())) {
			//				if (sapDailyUplPo == null) {
			//					sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_1);
			//					sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
			//					sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
			//					sapDailyUplPoV2.setSynErrMsg("订单为删除状态，无需上传SAP");
			//					sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
			//				} else {
			//					sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_1);
			//					sapDailyUplPo.setSynErrCnt(new BigDecimal(sapDailyUplPo.getSynErrCnt().intValue() + 1));
			//					sapDailyUplPo.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
			//					sapDailyUplPo.setSynErrMsg("订单为删除状态，无需上传SAP");
			//					dsSapDailyUplPoRepository.save(sapDailyUplPo);
			//				}
			return null; // 意味着这张单据不会调上传订单sap接口
		}
		dealerOrderVo.setPoNo(poMaster.getPoNo());// 多线程时能取到

		// 取订单类型
		String poProcessCode = poMaster.getPoProcessCode();
		int orderSapType = this.getSapType(poProcessCode);
		// 根据订单号查询订单详细信息
		List<PoDetail> poDetailList = dsPoDetailRepository.findByPoNoOrderByLineNoAsc(poMaster.getPoNo());
		//			// 查找订货支付信息
		//			List<PoPaymentDetail> poPaymentDetailList = dsPoPaymentDetailRepository.findByPoNo(poMaster.getPoNo());
		//			// 服务中心的支付信息
		//						List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository
		//								.queryByRefDocNoAndPaymentTypeSlrMk01(poMaster.getPoNo());
		// 取出产品编号与lineNo
		this.getParentLineNum(poDetailList, parentMap);
		// 根据订单号查询订单配送地址信息
		PoAddrDetail poAddrDetail = dsPoAddrDetailRepository.findByPoNo(poMaster.getPoNo());
		// 根据用户号查询会员信息
		Dealer dealer = dsDealerRepository.findByDealerNo(poMaster.getOrderDealerNo());

		// 推荐的新卡号申业后马上退货，然后又退货推荐单，导致推荐的新卡号被删除。
		if (dealer == null) {
			DealerDeleteBase deletedDeale = dealerDeleteBaseRepository.getDeletedDealer(poMaster.getOrderDealerNo());
			dealer = new Dealer();
			dealer.setSaleZoneNo(deletedDeale.getSaleZoneNo());
			dealer.setSaleBranchNo(deletedDeale.getSaleBranchNo());
		}

		// 公共字段赋值 传入订单主表
		this.theSameBill(pOrderHeader, poMaster, poAddrDetail, dealer);

		// GBSS-17286 
		// 大平台订单加入发票信息，需要上传发票信息至SAP
		// add by kevin.wang
		if (poMaster.getPoAppNo() != null && !"".equals(poMaster.getPoAppNo())) {
			if ("G101".equals(poMaster.getPoProcessCode())) {
				DealerStoreExtra dsd = dealerStoreExtraRepository.findByStoreNo(poMaster.getOrderDealerNo());
				DealerStore ds = dsDealerStoreRepository.findByStoreNo(poMaster.getOrderDealerNo());
				this.setInvoiceBill(pOrderHeader, dsd, ds);
			} else if ("G103".equals(poMaster.getPoProcessCode())) {
				// 企业购增值税发票
				PoInvoiceInfo info = poInvoiceInfoRepository.findByPoAppNo(poMaster.getPoAppNo());
				this.setInvoiceBillVat(pOrderHeader, info);
			} else {
				PoInvoiceInfo info = poInvoiceInfoRepository.findByPoAppNo(poMaster.getPoAppNo());
				this.setInvoiceBill(pOrderHeader, info);
			}
		}

		if (Integer.parseInt(poMaster.getPoPeriod()) >= 201601) {
			pOrderHeader.setSalesOff(dealer.getSaleBranchNo());// 销售部门
			SaleBranchInfo saleBranchInfo = dsSaleBranchInfoRepository.findBySaleBranchNo(dealer.getSaleBranchNo());
			if (saleBranchInfo != null) {
				pOrderHeader.setSalesDist(saleBranchInfo.getSaleRegionNo());// 销售区域
			}
			if (StringUtils.isBlank(poMaster.getOrderDealerBranchNo())) {
				poMaster.setOrderDealerBranchNo(dealer.getSaleBranchNo());
				dsPoMasterRepository.save(poMaster);
			}
		} else {
			pOrderHeader.setSalesOff(poMaster.getPoBranchNo());// 销售部门
		}
		// 根据订单类型赋值个性字段开始
		// 总公司普通单
		if (orderSapType == TypeSapOrderType.ZNF3) {
			this.companyCommonBill(pOrderHeader, poMaster, poAddrDetail);
			//纯服务订单，不需要触发发货
			boolean wholeVasProduct = true;
			if (CollectionUtils.isNotEmpty(poDetailList)) {
				for (PoDetail poDetail : poDetailList) {
					if (wholeVasProduct && !ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
						wholeVasProduct = false;
						break;
					}
				}
			}
			if (wholeVasProduct) {
				pOrderHeader.setDeliveryFlag("N");
			}
			//end
		}
		// 总公司预定／记欠单
		if (orderSapType == TypeSapOrderType.ZYD1) {
			this.companyBookingBill(pOrderHeader, poMaster);
		}
		// 总公司免费单  月刊单  // 月刊当赋值内容和 总公司免费单一样，所以调用和总公司免费单一样的方法
		if (orderSapType == TypeSapOrderType.ZFRE || orderSapType == TypeSapOrderType.ZNF6) {
			this.companyFreeBill(pOrderHeader, poMaster);
		}

		// 服务中心普通单
		if (orderSapType == TypeSapOrderType.ZNF4) {
			this.serviceCenterCommonBill(pOrderHeader, poMaster);
		}
		// 服务中心免费单
		if (orderSapType == TypeSapOrderType.ZGFR) {
			this.serviceCenterFreeBill(pOrderHeader, poMaster);
			//add ky_qrj 2014-05-27
			if (poMaster.getPoProcessCode().equals(TypeSapProcessCode.G311)) {
				pOrderHeader.setOrdReason("");
			}
			//end 2014-05-27
		}
		// 服务中心退货单 G305
		if (orderSapType == TypeSapOrderType.ZRE2) {
			this.serviceCenterReturnBill(pOrderHeader, poMaster);
		}
		// add by yx 2012-11-29 服务中心代退货单 G306 start
		if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
			this.serviceCenterReturnGbss(pOrderHeader, poMaster);
		}
		// add by yx 2012-11-29  end

		//add by Kevin.wang 2015-06-26 因怡瑞召回新增G501类型
		if (orderSapType == TypeSapOrderType.ZREA) {
			this.storeReturnCompany(pOrderHeader, poMaster, poAddrDetail);
		}
		//总公司普通单 纷享荟二期 add by zhuohr
		MkpDlpPoRelate mkpDlpPoRelate = null;
		if (orderSapType == TypeSapOrderType.ZRE1) {
			//add by zhuohr 
			mkpDlpPoRelate = dsMkpDlpPoRelateRepository.findByPoNo(poMaster.getPoNo());
			this.cppCompanyCommonBill(pOrderHeader, poMaster);
		}
		//add end

		if (orderSapType == TypeSapOrderType.ZFD2) {
			this.qualityLossCompany(pOrderHeader, poMaster);
		}

		if (orderSapType == TypeSapOrderType.ZRE4 || orderSapType == TypeSapOrderType.ZRE6) {
			this.lclReturnBill(pOrderHeader, poMaster);
		}

		if (orderSapType == TypeSapOrderType.ZNF8) {
			this.gpOrderBill(pOrderHeader, poMaster, poAddrDetail);
		}

		dealerOrderVo.setOrderHeader(pOrderHeader);
		// 传入订单明细列表
		List<TOrderItems> tOrderItemsList = new ArrayList<TOrderItems>();
		// 传入定价条件列表
		List<TCondition> tConditionList = new ArrayList<TCondition>();
		// bom产品是行号的写法
		// t_condition和T_ORDER_ITEMS的赋值
		Date poDate = poMaster.getPoDate();
		//初始汇总折扣
		BigDecimal totalZf01 = BigDecimal.ZERO;
		//获取订货折扣明细集合
		List<PoDetailDiscount> poDetailDiscountList = this.getPoDetailDiscountListByPoNo(poMaster.getPoNo());
		for (PoDetail poDetail : poDetailList) {
			boolean isProm = poDetail.getPromQty().compareTo(BigDecimal.ZERO) != 0;
			// 传入订单明细 共同的项 (普通项)
			TOrderItems tOrderItems = this.theSameTorderItems(poDetail, poAddrDetail, poMaster, parentMap, false);
			// 传入订单明细 共同的项（活动产品）
			TOrderItems tOrderItems4Prom = this.theSameTorderItems(poDetail, poAddrDetail, poMaster, parentMap, true);
			// 总公司普通单
			if (orderSapType == TypeSapOrderType.ZNF3) {
				if (tOrderItems != null) {
					this.companyCommonTorderItems(poDetail, tOrderItems, poProcessCode);
				}
				if (tOrderItems4Prom != null) {
					this.companyCommonTorderItems(poDetail, tOrderItems4Prom, poProcessCode);
				}
			}
			// 总公司预定／记欠单
			if (orderSapType == TypeSapOrderType.ZYD1) {
				if (tOrderItems != null) {
					this.companyBookingTorderItems(poDetail, tOrderItems);
				}
				if (tOrderItems4Prom != null) {
					this.companyBookingTorderItems(poDetail, tOrderItems4Prom);
				}
			}
			// 总公司免费单
			if (orderSapType == TypeSapOrderType.ZFRE) {
				this.companyFreeTorderItems(poDetail, tOrderItems);
			}

			// 2012-11-15 add by yx 增加总公司 月刊单 start 
			// 总公司 月刊单
			if (orderSapType == TypeSapOrderType.ZNF6) {
				this.companyMonthlyTorderItems(poDetail, tOrderItems);
			}
			// 2012-11-15 add by yx 增加总公司 月刊单 end 

			// 服务中心普通单
			if (orderSapType == TypeSapOrderType.ZNF4) {
				this.serviceCenterCommonTorderItems(poDetail, tOrderItems, poDate);
			}
			// 服务中心免费单
			if (orderSapType == TypeSapOrderType.ZGFR) {
				this.serviceCenterFreeTorderItems(poDetail, tOrderItems, poDate);
				//add ky_qrj 2014-05-27
				if (poMaster.getPoProcessCode().equals(TypeSapProcessCode.G311)) {
					tOrderItems.setItemCateg("ZTAX");
				}
				//end 2014-05-27
			}
			// 服务中心退货单 G305
			if (orderSapType == TypeSapOrderType.ZRE2) {
				this.serviceCenterReturnTorderItems(poDetail, tOrderItems, poDate);
			}

			// add by yx 2012-11-29 服务中心代退货单 G306 无参考退货单G308start
			if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
				this.serviceCenterReturnGbssTorderItems(poDetail, tOrderItems, poDate, poProcessCode);
			}
			// add by yx 2012-11-29 end 

			// add by kevin.wang 2015-06-25 G501
			if (orderSapType == TypeSapOrderType.ZREA) {
				this.storeReturnCompanyItems(poDetail, tOrderItems, poDate);
			}
			//总公司普通单 纷享荟二期 add by zhuohr
			if (orderSapType == TypeSapOrderType.ZRE1) {
				this.cppCompanyCommonTorderItems(poDetail, mkpDlpPoRelate, tOrderItems);
			}
			//全球购订单
			if (orderSapType == TypeSapOrderType.ZNF8) {
				this.gpCompanyCommonTorderItems(poDetail, tOrderItems);
			}
			// add  end 

			//质量补损定价为免费
			if (orderSapType == TypeSapOrderType.ZFD2) {
				tOrderItems.setItemCateg("ZFK7");
			}
			if (tOrderItems != null) {
				tOrderItemsList.add(tOrderItems);
			}
			if (tOrderItems4Prom != null) {
				tOrderItemsList.add(tOrderItems4Prom);
			}
			if (orderSapType == TypeSapOrderType.ZRE1) {//add by zhuohr
				//纷享绘需求要改 （临时方案）
				//modify by kevin
				PoDetailDiscount poDetailDiscount = null;
				if (CollectionUtils.isNotEmpty(poDetailDiscountList)) {
					for (PoDetailDiscount tempPoDetailDiscount : poDetailDiscountList) {
						if (tempPoDetailDiscount.getLineNo() == poDetail.getLineNo().intValue()) {
							poDetailDiscount = tempPoDetailDiscount;
							break;
						}
					}
				}
				this.setCppTConditionListByPoDetail(poDetail, mkpDlpPoRelate, tConditionList, poDetailDiscount);
			} else if (orderSapType == TypeSapOrderType.ZFD2) {
				//质量补损无定价条件
				continue;
			} else if (orderSapType == TypeSapOrderType.ZNF8) {
				PoDetailDiscount poDetailDiscount = null;
				if (CollectionUtils.isNotEmpty(poDetailDiscountList)) {
					for (PoDetailDiscount tempPoDetailDiscount : poDetailDiscountList) {
						if (tempPoDetailDiscount.getLineNo() == poDetail.getLineNo().intValue()) {
							poDetailDiscount = tempPoDetailDiscount;
							break;
						}
					}
				}
				this.setGpTConditionListByPoDetail(poDetail, tConditionList, poDetailDiscount);
			} else {
				// 定价列表根据PoDetail加入2种定价类型：单价、原点数
				PoDetailDiscount poDetailDiscount = null;
				if (CollectionUtils.isNotEmpty(poDetailDiscountList)) {
					for (PoDetailDiscount tempPoDetailDiscount : poDetailDiscountList) {
						if (tempPoDetailDiscount.getLineNo() == poDetail.getLineNo().intValue()) {
							poDetailDiscount = tempPoDetailDiscount;
							break;
						}
					}
				}
				this.setTConditionListByPoDetailNotPos(poDetail, tConditionList, poDetailDiscount);
			}
		}
		//GBSS-17453
		//应财务要求纷享绘订单统一上传信息，
		//add by kevin.wang at 2017-03-07　test
		if (orderSapType == TypeSapOrderType.ZRE1 && mkpDlpPoRelate != null) {
			this.setCppTConditionAddYT01AndZF01(tConditionList, mkpDlpPoRelate);
		}

		dealerOrderVo.settOrderItemsList(tOrderItemsList);

		if ("G304".equals(poMaster.getPoProcessCode())) {
			List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository.findByPosTranDateAndPosStoreNoAndRefDocNo(poMaster.getPoDate(),
					poMaster.getPoStoreNo(), poMaster.getPoNo());
			for (PosAccTranDetail posAccTranDetail : posAccTranDetailList) {
				// 定价列表根据PoPaymentDetail加入剩下的定价类型
				this.setTConditionListByPosAccTranDetail(posAccTranDetail, tConditionList);
			}
		} else {
			// 查找订货支付信息
			List<PoPaymentDetail> poPaymentDetailList = dsPoPaymentDetailRepository.findByPoNo(poMaster.getPoNo());
			// 处理整单T_CONDITION
			for (PoPaymentDetail poPaymentDetail : poPaymentDetailList) {
				// 定价列表根据PoPaymentDetail加入剩下的定价类型
				this.setTConditionListByPoPaymentDetail(poPaymentDetail, tConditionList);
			}
		}

		//			}
		// 
		if (orderSapType == TypeSapOrderType.ZNF3 || orderSapType == TypeSapOrderType.ZYD1 || orderSapType == TypeSapOrderType.ZNF4
				|| orderSapType == TypeSapOrderType.ZRE2 || orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
			//this.setTConditionListByPoMaster(poMaster, tConditionList);
		}

		//ZF01 是合计所有的折扣
		//add by kevin 2016-05-06
		//应吴波要求，暂时不上传ZF01 
		//modify by kevin 2015-05-06 - -
		/*if(orderSapType == TypeSapOrderType.ZRE1&&totalZf01.compareTo(BigDecimal.ZERO)>0){//add by zhuohr
			this.setCppTConditionListByPoDetailAddZF01(totalZf01.toString(), tConditionList);
		}*/

		//GBSS-17238
		//2017新星启航增加折扣参数进去
		//Add by kevin.wang 2017-03-01
		if ("G010".equals(poMaster.getPoProcessCode()) || "G017".equals(poMaster.getPoProcessCode())) {
			this.setTConditionListWithZKBM(poMaster, tConditionList);
		} else if ((TypeSapProcessCode.G306.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G001.equals(poMaster.getPoProcessCode())
				|| TypeSapProcessCode.G002.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G020.equals(poMaster.getPoProcessCode())
				|| TypeSapProcessCode.G022.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G023.equals(poMaster.getPoProcessCode()))
				&& (BigDecimal.ZERO.compareTo(poMaster.getTotalSaleDiscountAmt()) != 0)) {//20170516添加微信折扣金额 add by zhuohr
			//this.setTConditionListWithZKWX(poMaster,tConditionList);
		}

		dealerOrderVo.settConditionList(tConditionList);
		return dealerOrderVo;
	}

	//		catch (Exception e) {
	//			try {
	//				if (sapDailyUplPo == null) {
	//					// 出错写错误信息恢复同步状态0
	//					sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//					// 超次数设删除标志
	//					if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//						sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//					}
	//					/*String mess = e.getMessage() != null
	//					        && e.getMessage().length() >= 500 ? e.getMessage()
	//					        .substring(0, 499) : e.getMessage();*/
	//					//					sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//					//					sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//				} else {
	//					// 出错写错误信息恢复同步状态0
	//					sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPo.setSynErrCnt(new BigDecimal(sapDailyUplPo.getSynErrCnt().intValue() + 1));
	//					// 超次数设删除标志
	//					if (sapDailyUplPo.getSynErrCnt().compareTo(getSynErrCnt()) != -1) {
	//						sapDailyUplPo.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//					}
	//					/*String mess = e.getMessage() != null
	//					        && e.getMessage().length() >= 500 ? e.getMessage()
	//					        .substring(0, 499) : e.getMessage();*/
	//					sapDailyUplPo.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//					dsSapDailyUplPoRepository.save(sapDailyUplPo);
	//				}
	//
	//				log.error("运行异常", e);
	//				throw e;
	//			} catch (Exception e1) {
	//				log.error("运行异常", e1);
	//				throw e1;
	//			}
	//	}

	//	}

	/**
	 * 全球购订单
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void gpCompanyCommonTorderItems(PoDetail poDetail, TOrderItems tOrderItems) {
		if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
			tOrderItems.setItemCateg("ZTAY");
		} else {
			tOrderItems.setItemCateg("ZTAN");
		}
	}

	/**
	 * 全球购定价
	 * @param poDetail
	 * @param tConditionList
	 */
	private void setGpTConditionListByPoDetail(PoDetail poDetail, List<TCondition> tConditionList, PoDetailDiscount poDetailDiscount) {
		// 单价:PR00
		TCondition tCondition1 = new TCondition();
		tCondition1.setItmNumber(poDetail.getLineNo().toString());// 申请单行项目
		tCondition1.setCondType("PR00");// 定价类型
		String value = poDetail.getSalePrice() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePrice());
		tCondition1.setCondValue(value);// 定价金额
		tCondition1.setCurrency("CNY");// 货币单位
		tConditionList.add(tCondition1);
		// 原点数:ZK05
		TCondition tCondition2 = new TCondition();
		tCondition2.setItmNumber(poDetail.getLineNo().toString());
		tCondition2.setCondType("ZK05");
		value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
		tCondition2.setCondValue(value);
		tCondition2.setCurrency("CNY");
		tConditionList.add(tCondition2);
		// 代扣代缴增值税:ZGT1
		TCondition tCondition3 = new TCondition();
		tCondition3.setItmNumber(poDetail.getLineNo().toString());
		tCondition3.setCondType("ZGT1");
		value = poDetail.getVatAmt() == null ? "" : SapFormatUtil.formatNumber(poDetail.getVatAmt());
		tCondition3.setCondValue(value);
		tCondition3.setCurrency("CNY");
		tConditionList.add(tCondition3);
		// 代扣代缴消费税:ZGT2
		TCondition tCondition4 = new TCondition();
		tCondition4.setItmNumber(poDetail.getLineNo().toString());
		tCondition4.setCondType("ZGT2");
		value = poDetail.getConsumTaxAmt() == null ? "" : SapFormatUtil.formatNumber(poDetail.getConsumTaxAmt());
		tCondition4.setCondValue(value);
		tCondition4.setCurrency("CNY");
		tConditionList.add(tCondition4);

		BigDecimal rebateDisAmtProduct = BigDecimal.ZERO;//优差折扣金额（单品）
		BigDecimal rebateDisAmtCoupon = BigDecimal.ZERO;//优差折扣金额（电子券分摊）
		BigDecimal rebateDisAmtWhole = BigDecimal.ZERO;//优差折扣金额（整单减分摊）
		BigDecimal companyDisAmtProduct = BigDecimal.ZERO;//公司折扣金额（单品）
		BigDecimal companyDisAmtCoupon = BigDecimal.ZERO;//公司折扣金额（电子券分摊）
		BigDecimal companyDisAmtWhole = BigDecimal.ZERO;//公司折扣金额（整单减分摊）

		if (poDetailDiscount != null) {
			rebateDisAmtProduct = poDetailDiscount.getRebateDisAmtProduct();//优差折扣金额（单品）
			rebateDisAmtCoupon = poDetailDiscount.getRebateDisAmtCoupon();//优差折扣金额（电子券分摊）
			rebateDisAmtWhole = poDetailDiscount.getRebateDisAmtWhole();//优差折扣金额（整单减分摊）
			companyDisAmtProduct = poDetailDiscount.getCompanyDisAmtProduct();//公司折扣金额（单品）
			companyDisAmtCoupon = poDetailDiscount.getCompanyDisAmtCoupon();//公司折扣金额（电子券分摊）
			companyDisAmtWhole = poDetailDiscount.getCompanyDisAmtWhole();//公司折扣金额（整单减分摊）
		}

		//使用了优惠券
		// ZK74 优差折扣金额（整单减分摊）
		if (poDetailDiscount != null) {
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (rebateDisAmtWhole.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition5 = new TCondition();
					tCondition5.setItmNumber(poDetail.getLineNo().toString());
					tCondition5.setCondType("ZK74");
					value = SapFormatUtil.formatNumber(rebateDisAmtWhole);
					tCondition5.setCondValue(value);
					tCondition5.setCurrency("CNY");
					tConditionList.add(tCondition5);
				}
			}
			// ZK61 优差折扣金额（优惠券分摊）
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (rebateDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition6 = new TCondition();
					tCondition6.setItmNumber(poDetail.getLineNo().toString());
					tCondition6.setCondType("ZK61");
					value = SapFormatUtil.formatNumber(rebateDisAmtCoupon);
					tCondition6.setCondValue(value);
					tCondition6.setCurrency("CNY");
					tConditionList.add(tCondition6);
				}
			}
			// ZKWX 公司折扣金额（整单减分摊）
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (companyDisAmtWhole.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition7 = new TCondition();
					tCondition7.setItmNumber(poDetail.getLineNo().toString());
					tCondition7.setCondType("ZKWX");
					value = SapFormatUtil.formatNumber(companyDisAmtWhole);
					tCondition7.setCondValue(value);
					tCondition7.setCurrency("CNY");
					tConditionList.add(tCondition7);
				}
			}
			// ZK20 公司折扣金额（优惠券分摊）
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (companyDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition8 = new TCondition();
					tCondition8.setItmNumber(poDetail.getLineNo().toString());
					tCondition8.setCondType("ZK20");
					value = SapFormatUtil.formatNumber(companyDisAmtCoupon);
					tCondition8.setCondValue(value);
					tCondition8.setCurrency("CNY");
					tConditionList.add(tCondition8);
				}
			}
		}

	}

	/**
	 * 全球购
	 * @param pOrderHeader
	 * @param poMaster
	 * @param poAddrDetail
	 */
	private void gpOrderBill(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail) {
		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setDeliveryFlag("N");//不需要触发发货
		if (TypeSapProcessCode.G018.equals(poMaster.getPoProcessCode())) {
			pOrderHeader.setKonda("08");//持卡人
		} else {
			pOrderHeader.setKonda("07");//普消
		}
		pOrderHeader.setPayKunn("3015");//付款方卡号
		pOrderHeader.setPartnNumb("3015");//售达方ID
		pOrderHeader.setPartnKunn1("3015");//送达方
		// 普消购货
		if (StringUtils.isNotBlank(poMaster.getOrderCustomerNo())) {
			DealerCustomer dealerCustomer = dealerCustomerRepository.findByCustomerNo(poMaster.getOrderCustomerNo());
			if (dealerCustomer != null) {
				pOrderHeader.setCardYst(dealerCustomer.getMobile());
				if (StringUtils.isNotBlank(dealerCustomer.getFullName())) {
					pOrderHeader.setNameYst(dealerCustomer.getFullName());
				}
			}
		}
	}

	/* (non-Javadoc)
	 
	 */
	//	@Override
	//	public DealerOrderVo action(SapDailyUplPo sapDailyUplPo) throws Exception {
	//		DealerOrderVo dealerOrderVo = this.prepareDataNotPos(sapDailyUplPo, null);
	//		if (dealerOrderVo != null) {
	//			try {
	//
	//				// 判断是否启动新版触发发货
	//				SysParamInfo sysParamInfo = sysParamInfoRepository.findByParaCode("NEW_TRIGGER_DELIVERY");
	//				PlantResult plantResult = null;
	//				boolean newTriggerFlag = false;
	//				if (sysParamInfo != null && "1".equals(sysParamInfo.getParaValue())) {
	//					SysParamInfo sysParamInfo2 = sysParamInfoRepository.findByParaCode("NEW_TRIGGER_TIME");
	//					if (sysParamInfo2 != null) {
	//						try {
	//							Date newTriggerTime = DateFormatUtil.parse(sysParamInfo2.getParaValue(), "yyyy-MM-dd HH:mm:ss");
	//							// 订单时间
	//							PoMaster poMaster = dsPoMasterRepository.findByPoNo(dealerOrderVo.getPoNo());
	//							Date orderTime = new Date(poMaster.getPoEntryTime().getTime());
	//							if ((orderTime).after(newTriggerTime)) {
	//								newTriggerFlag = true;
	//							}
	//						} catch (Exception e) {
	//							log.error("error", e);
	//						}
	//					}
	//				}
	//
	//				return dealerOrderVo;
	//				if (newTriggerFlag) {
	//					plantResult = complexSapService.saveDealerOrderSheetNew(dealerOrderVo);
	//				} else {
	//					plantResult = complexSapService.saveDealerOrderSheet(dealerOrderVo);
	//				}
	//
	//				dealResult(plantResult, dealerOrderVo.getOrderHeader().getPurchNoC(), dealerOrderVo.getOrderHeader().getSoDate());
	//				String mess = "";
	//				List<SapMessage> messages = plantResult.getMessages();
	//				if (messages != null) {
	//					for (int i = 0; i < messages.size(); i++) {
	//						SapMessage message = messages.get(i);
	//						mess = mess + (i + 1) + "." + message.getMessage() + ";";
	//					}
	//				}
	//				mess = mess.length() >= 500 ? mess.substring(0, 499) : mess;
	//				// 准备数据时改了同步状态"1"，运行完时恢复同步状态"0"，并把同步类型改为"U"
	//				if (plantResult.isSuccess()) {
	//					sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPo.setSynType(TypeSapPostingFlag.SYN_TYPE_U);
	//					sapDailyUplPo.setSynErrCnt(new BigDecimal(sapDailyUplPo.getSynErrCnt().intValue() + 1));
	//					sapDailyUplPo.setSynErrMsg("");
	//					dsSapDailyUplPoRepository.save(sapDailyUplPo);
	//				} else {
	//					// 出错写错误信息恢复同步状态0
	//					sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPo.setSynErrCnt(new BigDecimal(sapDailyUplPo.getSynErrCnt().intValue() + 1));
	//					// 超次数设删除标志
	//					if (sapDailyUplPo.getSynErrCnt().compareTo(getSynErrCnt()) != -1) {
	//						sapDailyUplPo.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//					}
	//					sapDailyUplPo.setSynErrMsg(mess);
	//					dsSapDailyUplPoRepository.save(sapDailyUplPo);
	//					// 改状态E
	//					this.savePoMasterWhenWrong(dealerOrderVo.getPoNo(), dealerOrderVo.getOrderHeader().getPosNumber());
	//
	//				}
	//			} catch (Exception e) {
	//				try {
	//					// 出错写错误信息恢复同步状态0
	//					sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPo.setSynErrCnt(new BigDecimal(sapDailyUplPo.getSynErrCnt().intValue() + 1));
	//					// 超次数设删除标志
	//					if (sapDailyUplPo.getSynErrCnt().compareTo(getSynErrCnt()) != -1) {
	//						sapDailyUplPo.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//					}
	//					String mess = e.getMessage() != null && e.getMessage().length() >= 500 ? e.getMessage().substring(0, 499) : e.getMessage();
	//					sapDailyUplPo.setSynErrMsg(mess);
	//					dsSapDailyUplPoRepository.save(sapDailyUplPo);
	//					this.savePoMasterWhenWrong(dealerOrderVo.getPoNo(), dealerOrderVo.getOrderHeader().getPosNumber());
	//					log.error("运行异常", e);
	//					//throw e;
	//				} catch (Exception e1) {
	//					log.error("运行异常", e1);
	//					throw e1;
	//				}
	//			}
	//		}
	//	}

	//	/* (non-Javadoc)
	//	 
	//	 */
	//	@Override
	//	public void actionV2(SapDailyUplPoV2 sapDailyUplPoV2) throws Exception {
	//		// 撤单
	//		if (TypeSapProcessCode.G307.equals(sapDailyUplPoV2.getPoDocType())) {
	//			String poNo = sapDailyUplPoV2.getPoNo();
	//			PoMaster pm = dsPoMasterRepository.findByPoNo(poNo);
	//			String refPoNo = pm.getRefPoNo();
	//			pm = dsPoMasterRepository.findByPoNo(refPoNo);
	//			if (TypeSapPostingFlag.PAY.equals(pm.getSapPostingFlag())) {
	//				try {
	//					String mess = "";
	//					PlantResult plantResult = complexSapService.posRevok(refPoNo);
	//					List<?> sapmessage = plantResult.getResult();
	//					if (sapmessage != null) {
	//						for (int j = 0; j < sapmessage.size(); j++) {
	//							@SuppressWarnings("rawtypes")
	//							Map map = (HashMap) sapmessage.get(j);
	//							// "E": SAP错误
	//							if (TypeSapPostingFlag.ERROR.equals(map.get("type"))) {
	//								mess += (j + 1) + "." + map.get("message") != null ? map.get("message").toString() : "SAP错误，撤单失败！" + "；";
	//							}
	//						}
	//					}
	//
	//					// 准备数据时改了同步状态"1"，运行完时恢复同步状态"0"，并把同步类型改为"U"
	//					if ("".equals(mess)) {
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_U);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						sapDailyUplPoV2.setSynErrMsg("");
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						pm.setPoStatus("99");
	//						dsPoMasterRepository.save(pm);
	//					} else {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(mess, 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//					}
	//				} catch (Exception e) {
	//					try {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						/*String mess = e.getMessage() != null && e.getMessage().length() >= 500
	//						        ? e.getMessage().substring(0, 499)
	//						        : e.getMessage();*/
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						throw e;
	//					} catch (Exception e1) {
	//						log.error("运行异常", e1);
	//						throw e1;
	//					}
	//				}
	//			} else {
	//				sapDailyUplPoV2.setSynStatus("0");
	//				sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//			}
	//
	//		} else {
	//			DealerOrderVo dealerOrderVo = null;
	//			// G305退货单时先判断原单是否发货过账
	//			if (TypeSapProcessCode.G305.equals(sapDailyUplPoV2.getPoDocType())) {
	//				PoMaster pm = dsPoMasterRepository.findByPoNo(sapDailyUplPoV2.getPoNo());
	//				PosDeliveryMaster pdm = posDeliveryMasterRepository.findByPoNo(pm.getRefPoNo());
	//				if (pdm != null && TypeSapPostingFlag.PAY.equals(pdm.getSapPostingFlag())) {
	//					dealerOrderVo = this.prepareData(null, sapDailyUplPoV2);
	//				} else {
	//					sapDailyUplPoV2.setSynStatus("0");
	//					sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//				}
	//			} else if (TypeSapProcessCode.G306.equals(sapDailyUplPoV2.getPoDocType())
	//					|| TypeSapProcessCode.G105.equals(sapDailyUplPoV2.getPoDocType())) {
	//				PoMaster pm = dsPoMasterRepository.findByPoNo(sapDailyUplPoV2.getPoNo());
	//				PoAddrDetail addrDetail = dsPoAddrDetailRepository.findByPoNo(pm.getRefPoNo());
	//				// 判断原单是否服务中心提货并过账
	//				if (addrDetail != null && "10".equals(addrDetail.getDeliveryType())) {
	//					PosDeliveryMaster pdm = posDeliveryMasterRepository.findByPoNo(pm.getRefPoNo());
	//					if (pdm != null && TypeSapPostingFlag.PAY.equals(pdm.getSapPostingFlag())) {
	//						dealerOrderVo = this.prepareData(null, sapDailyUplPoV2);
	//					} else {
	//						sapDailyUplPoV2.setSynStatus("0");
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//					}
	//				} else {
	//					dealerOrderVo = this.prepareData(null, sapDailyUplPoV2); // G306 G105单，且原单非服务中心提货
	//				}
	//			} else {
	//				dealerOrderVo = this.prepareData(null, sapDailyUplPoV2); // G301 ... G304   // 2013-01-07 G308单据
	//			}
	//			if (dealerOrderVo != null) {
	//				try {
	//					//					PlantResult plantResult = complexSapService.saveDealerOrderSheet(dealerOrderVo);
	//					// 判断是否启动新版触发发货
	//					SysParamInfo sysParamInfo = sysParamInfoRepository.findByParaCode("NEW_TRIGGER_DELIVERY");
	//					PlantResult plantResult = null;
	//					boolean newTriggerFlag = false;
	//					if (sysParamInfo != null && "1".equals(sysParamInfo.getParaValue())) {
	//						SysParamInfo sysParamInfo2 = sysParamInfoRepository.findByParaCode("NEW_TRIGGER_TIME");
	//						if (sysParamInfo2 != null) {
	//							try {
	//								Date newTriggerTime = DateFormatUtil.parse(sysParamInfo2.getParaValue(), "yyyy-MM-dd HH:mm:ss");
	//								// 订单时间
	//								PoMaster poMaster = dsPoMasterRepository.findByPoNo(dealerOrderVo.getPoNo());
	//								Date orderTime = new Date(poMaster.getPoEntryTime().getTime());
	//								if ((orderTime).after(newTriggerTime)) {
	//									newTriggerFlag = true;
	//								}
	//							} catch (Exception e) {
	//								log.error("error", e);
	//							}
	//						}
	//					}
	//
	//					if (newTriggerFlag) {
	//						plantResult = complexSapService.saveDealerOrderSheetNew(dealerOrderVo);
	//					} else {
	//						plantResult = complexSapService.saveDealerOrderSheet(dealerOrderVo);
	//					}
	//					dealResult(plantResult, dealerOrderVo.getOrderHeader().getPurchNoC(), dealerOrderVo.getOrderHeader().getSoDate());
	//					String mess = "";
	//					List<SapMessage> messages = plantResult.getMessages();
	//					if (messages != null) {
	//						for (int i = 0; i < messages.size(); i++) {
	//							SapMessage message = messages.get(i);
	//							mess = mess + (i + 1) + "." + message.getMessage() + ";";
	//						}
	//					}
	//
	//					// 准备数据时改了同步状态"1"，运行完时恢复同步状态"0"，并把同步类型改为"U"
	//					if (plantResult.isSuccess()) {
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_U);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						sapDailyUplPoV2.setSynErrMsg("");
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//					} else {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(mess, 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						// 改状态E
	//						this.savePoMasterWhenWrong(dealerOrderVo.getPoNo(), dealerOrderVo.getOrderHeader().getPosNumber());
	//					}
	//				} catch (Exception e) {
	//					try {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						/*String mess = e.getMessage() != null && e.getMessage().length() >= 500
	//						        ? e.getMessage().substring(0, 499)
	//						        : e.getMessage();*/
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						this.savePoMasterWhenWrong(dealerOrderVo.getPoNo(), dealerOrderVo.getOrderHeader().getPosNumber());
	//						log.error("运行异常", e);
	//						throw e;
	//					} catch (Exception e1) {
	//						log.error("运行异常", e1);
	//						throw e1;
	//					}
	//				}
	//			}
	//		}
	//	}

	//	/* (non-Javadoc)
	//	 
	//	 */
	//	@Override
	//	public void actionRevoke(SapDailyUplPoV2 sapDailyUplPoV2) throws Exception {
	//
	//		// 撤单
	//		if (TypeSapProcessCode.G307.equals(sapDailyUplPoV2.getPoDocType())) {
	//			String poNo = sapDailyUplPoV2.getPoNo();
	//			PoMaster pm = dsPoMasterRepository.findByPoNo(poNo);
	//			String refPoNo = pm.getRefPoNo();
	//			pm = dsPoMasterRepository.findByPoNo(refPoNo);
	//			if (TypeSapPostingFlag.PAY.equals(pm.getSapPostingFlag())) {
	//				try {
	//					String mess = "";
	//					PlantResult plantResult = complexSapService.posRevok(refPoNo);
	//					List<?> sapmessage = plantResult.getResult();
	//					if (sapmessage != null) {
	//						for (int j = 0; j < sapmessage.size(); j++) {
	//							@SuppressWarnings("rawtypes")
	//							Map map = (HashMap) sapmessage.get(j);
	//							// "E": SAP错误
	//							if (TypeSapPostingFlag.ERROR.equals(map.get("type"))) {
	//								mess += (j + 1) + "." + map.get("message") != null ? map.get("message").toString() : "SAP错误，撤单失败！" + "；";
	//							}
	//						}
	//					}
	//
	//					// 准备数据时改了同步状态"1"，运行完时恢复同步状态"0"，并把同步类型改为"U"
	//					if ("".equals(mess)) {
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_U);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						sapDailyUplPoV2.setSynErrMsg("");
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						pm.setPoStatus("99");
	//						dsPoMasterRepository.save(pm);
	//					} else {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(mess, 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//					}
	//				} catch (Exception e) {
	//					try {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						/*String mess = e.getMessage() != null && e.getMessage().length() >= 500
	//						        ? e.getMessage().substring(0, 499)
	//						        : e.getMessage();*/
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						throw e;
	//					} catch (Exception e1) {
	//						log.error("运行异常", e1);
	//						throw e1;
	//					}
	//				}
	//			} else {
	//				sapDailyUplPoV2.setSynStatus("0");
	//				sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//			}
	//
	//		} else {
	//			DealerOrderVo dealerOrderVo = null;
	//			// G305退货单时先判断原单是否发货过账
	//			if (TypeSapProcessCode.G305.equals(sapDailyUplPoV2.getPoDocType())) {
	//				PoMaster pm = dsPoMasterRepository.findByPoNo(sapDailyUplPoV2.getPoNo());
	//				PosDeliveryMaster pdm = posDeliveryMasterRepository.findByPoNo(pm.getRefPoNo());
	//				if (pdm != null && TypeSapPostingFlag.PAY.equals(pdm.getSapPostingFlag())) {
	//					dealerOrderVo = this.prepareData(null, sapDailyUplPoV2);
	//				} else {
	//					sapDailyUplPoV2.setSynStatus("0");
	//					sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//				}
	//			} else if (TypeSapProcessCode.G306.equals(sapDailyUplPoV2.getPoDocType())
	//					|| TypeSapProcessCode.G105.equals(sapDailyUplPoV2.getPoDocType())) {
	//				PoMaster pm = dsPoMasterRepository.findByPoNo(sapDailyUplPoV2.getPoNo());
	//				PoAddrDetail addrDetail = dsPoAddrDetailRepository.findByPoNo(pm.getRefPoNo());
	//				// 判断原单是否服务中心提货并过账
	//				if (addrDetail != null && "10".equals(addrDetail.getDeliveryType())) {
	//					PosDeliveryMaster pdm = posDeliveryMasterRepository.findByPoNo(pm.getRefPoNo());
	//					if (pdm != null && TypeSapPostingFlag.PAY.equals(pdm.getSapPostingFlag())) {
	//						dealerOrderVo = this.prepareData(null, sapDailyUplPoV2);
	//					} else {
	//						sapDailyUplPoV2.setSynStatus("0");
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//					}
	//				} else {
	//					dealerOrderVo = this.prepareData(null, sapDailyUplPoV2); // G306 G105单，且原单非服务中心提货
	//				}
	//			} else {
	//				dealerOrderVo = this.prepareData(null, sapDailyUplPoV2); // G301 ... G304   // 2013-01-07 G308单据
	//			}
	//			if (dealerOrderVo != null) {
	//				try {
	//					PlantResult plantResult = complexSapService.saveDealerOrderSheet(dealerOrderVo);
	//					dealResult(plantResult, dealerOrderVo.getOrderHeader().getPurchNoC(), dealerOrderVo.getOrderHeader().getSoDate());
	//					String mess = "";
	//					List<SapMessage> messages = plantResult.getMessages();
	//					if (messages != null) {
	//						for (int i = 0; i < messages.size(); i++) {
	//							SapMessage message = messages.get(i);
	//							mess = mess + (i + 1) + "." + message.getMessage() + ";";
	//						}
	//					}
	//
	//					// 准备数据时改了同步状态"1"，运行完时恢复同步状态"0"，并把同步类型改为"U"
	//					if (plantResult.isSuccess()) {
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_U);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						sapDailyUplPoV2.setSynErrMsg("");
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//					} else {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(mess, 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						// 改状态E
	//						this.savePoMasterWhenWrong(dealerOrderVo.getPoNo(), dealerOrderVo.getOrderHeader().getPosNumber());
	//					}
	//				} catch (Exception e) {
	//					try {
	//						// 出错写错误信息恢复同步状态0
	//						sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//						sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//						// 超次数设删除标志
	//						if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//							sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//						}
	//						/*String mess = e.getMessage() != null && e.getMessage().length() >= 500
	//						        ? e.getMessage().substring(0, 499)
	//						        : e.getMessage();*/
	//						sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//						sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//
	//						this.savePoMasterWhenWrong(dealerOrderVo.getPoNo(), dealerOrderVo.getOrderHeader().getPosNumber());
	//						log.error("运行异常", e);
	//						throw e;
	//					} catch (Exception e1) {
	//						log.error("运行异常", e1);
	//						throw e1;
	//					}
	//				}
	//			}
	//		}
	//
	//	}
	//
	//	@Override
	//	public void actionSto(String poStoreNo, int maxQueryNum, String dateFlag) throws Exception {
	//		// 根据服务中心code 统计对列表中未上传订单集合
	//		List<SapDailyUplPoV2> sapDailyUplPoV2List = null;
	//		if (dateFlag != null && !"".equals(dateFlag)) {
	//			// 上传指定日期前的单据（包含当天）
	//			sapDailyUplPoV2List = dsSapDailyUplPoService.getBySynTypeAndSynStatusAndpoStoreCodeAndDateFlag(poStoreNo, maxQueryNum, dateFlag);
	//		} else {
	//			// 正常模式上传
	//			sapDailyUplPoV2List = dsSapDailyUplPoService.getBySynTypeAndSynStatusAndpoStoreCode(poStoreNo, maxQueryNum);
	//		}
	//
	//		if (sapDailyUplPoV2List != null && sapDailyUplPoV2List.size() > 0) {
	//			// 分情况
	//			List<String> personalPoNoList = new ArrayList<String>(); // 个人推荐、个人购货、零售、申业单等订单做STO转储操作
	//
	//			List<SapDailyUplPoV2> personalSapDailyUplPoV2List = new ArrayList<SapDailyUplPoV2>(); // 个人推荐、个人购货、零售、申业单等订单上传集合
	//			List<SapDailyUplPoV2> otherSapDailyUplPoV2List = new ArrayList<SapDailyUplPoV2>(); // 其它类型的单据上传集合
	//			// 1、个人推荐单、个人购货单、零售单、申业单等上传
	//			for (SapDailyUplPoV2 sapDailyUplPoV2 : sapDailyUplPoV2List) {
	//				String poProcessCode = sapDailyUplPoV2.getPoDocType();
	//				String poNo = sapDailyUplPoV2.getPoNo();
	//				if (commonOrderTypeDetaiService.isPosPurchaseOrder(poProcessCode)) {
	//					// 验证订单是否已经转储过了,若已经转储了,则不能再次被统计做转储，但仍然需要上传
	//					if (!isStoed(poNo)) {
	//						personalPoNoList.add(poNo);
	//					}
	//					personalSapDailyUplPoV2List.add(sapDailyUplPoV2);
	//				} else {
	//					otherSapDailyUplPoV2List.add(sapDailyUplPoV2);
	//				}
	//			}
	//
	//			// 订单上传操作 other
	//			if (otherSapDailyUplPoV2List != null && otherSapDailyUplPoV2List.size() > 0) {
	//				for (SapDailyUplPoV2 sapDailyUplPoV2 : otherSapDailyUplPoV2List) {
	//					actionV2(sapDailyUplPoV2);
	//				}
	//			}
	//
	//			Boolean flag = false;
	//			//  转储
	//			//			System.out.println("====================待转储的订单：" + personalPoNoList);
	//			if (personalPoNoList != null && personalPoNoList.size() > 0) {
	//				// 被统计的待转储的订单
	//				List<SapDailyUplPoV2> v2List = new ArrayList<SapDailyUplPoV2>();
	//				Map<String, Object> returnMap = new HashMap<String, Object>();
	//				try {
	//					// STO 操作
	//					flag = posStoService.posSto(personalPoNoList, returnMap);
	//				} catch (Exception e) {
	//					log.info("STO操作失败！", e);
	//					// 删除sto 转储记录和 sto-po对应关系记录  (一定要删除 sto-po对应关系之后才能还原对里表记录,因为先还原了队列表记录之后,
	//					// 数据就被下一个线程抓取到,会出现下一个线程执行时发现这个记录在sto-po对应关系表中了的现象，这是不对的)
	//					if (returnMap != null) {
	//						// 转储master
	//						PosReplenishMaster posReplenishMaster = (PosReplenishMaster) returnMap.get("posReplenishMaster");
	//						if (posReplenishMaster != null) {
	//							posReplenishMasterRepository.delete(posReplenishMaster);
	//						}
	//						// 转储detail
	//
	//						@SuppressWarnings("unchecked")
	//						List<PosReplenishDetail> posReplenishDetailList = (List<PosReplenishDetail>) returnMap.get("posReplenishDetailList");
	//						if (posReplenishDetailList != null && posReplenishDetailList.size() > 0) {
	//							posReplenishDetailRepository.delete(posReplenishDetailList);
	//						}
	//						// sto-po对应关系
	//						@SuppressWarnings("unchecked")
	//						List<PosIntercoListDetail> posIntercoListDetailList = (List<PosIntercoListDetail>) returnMap.get("posIntercoListDetailList");
	//						if (posIntercoListDetailList != null && posIntercoListDetailList.size() > 0) {
	//							posIntercoListDetailRepository.delete(posIntercoListDetailList);
	//						}
	//
	//					}
	//
	//					// 还原队列表记录
	//					for (String poNo : personalPoNoList) {
	//						PoMaster poMaster = dsPoMasterRepository.findByPoNo(poNo);
	//
	//						if (poMaster != null && !"Y".equals(poMaster.getSapPostingFlag())) {
	//							SapDailyUplPoV2 v2 = sapDailyUplPoV2Repository.findByPoNo(poNo);
	//							v2List.add(v2);
	//						}
	//					}
	//					//			poNosStr = poNosBuf.substring(0, poNosBuf.length() - 2);
	//					// System.out.println("returnMap:" + returnMap);
	//					// System.out.println("1、flag:" + flag);
	//					if (v2List != null && v2List.size() > 0) {
	//						returnSapV2(v2List, returnMap);
	//					}
	//					flag = false;
	//					//System.out.println("2、flag:" + flag);
	//				}
	//				//flagTest = flag;
	//			} else {
	//				// 没有订单需要做转储
	//				flag = true;
	//			}
	//			// 订单上传
	//			// 订单上传操作  personal  转储成功才能做上传操作 或是没有订单需要做转储
	//			if (flag) {
	//				//System.out.println("3、flag:" + flag);
	//				for (SapDailyUplPoV2 sapDailyUplPoV2 : personalSapDailyUplPoV2List) {
	//					actionV2(sapDailyUplPoV2);
	//				}
	//			}
	//		}
	//	}
	//
	//	@Override
	//	public void uploadPosRevokeAndReturnPoOrder(int maxQueryNum, String dateFlag) throws Exception {
	//		// ==============  上传单据准备  ================
	//		// 获取一张订单（撤单或退货单）
	//		List<SapDailyUplPoV2> sapDailyUplPoV2List = null;
	//		if (dateFlag != null && !"".equals(dateFlag)) {
	//			sapDailyUplPoV2List = dsSapDailyUplPoService.getPosRevokeAndReturnOrderToNumAndDateFlag(maxQueryNum, dateFlag);
	//		} else {
	//			sapDailyUplPoV2List = dsSapDailyUplPoService.getPosRevokeAndReturnOrderToNum(maxQueryNum);
	//		}
	//
	//		if (sapDailyUplPoV2List != null && sapDailyUplPoV2List.size() > 0) {
	//			for (SapDailyUplPoV2 sapDailyUplPoV2 : sapDailyUplPoV2List) {
	//				if (sapDailyUplPoV2 == null) {
	//					continue;
	//				}
	//				this.actionRevoke(sapDailyUplPoV2);
	//			}
	//		}
	//	}
	//
	//	/* (non-Javadoc)
	//	 
	//	 */
	//	@Override
	//	public void uploadPoOrder(List<SapDailyUplPo> sapDailyUplPoList) throws Exception {
	//		if (sapDailyUplPoList != null && sapDailyUplPoList.size() > 0) {
	//			for (SapDailyUplPo sapDailyUplPo : sapDailyUplPoList) {
	//				if (sapDailyUplPo == null) {
	//					continue;
	//				}
	//				this.action(sapDailyUplPo);
	//			}
	//		}
	//	}

	//	/* (non-Javadoc)
	//	 
	//	 */
	//	@Override
	//	public void uploadPosPoOrder(String poStroeCode, int maxQueryNum, String dateFlag) throws Exception {
	//		this.actionSto(poStroeCode, maxQueryNum, dateFlag);
	//	}
	//
	//	/**
	//	 * 若sto失败，将队列数据改回    // 设置为独立的子事物
	//	 * @param v2List
	//	 */
	//	private void returnSapV2(List<SapDailyUplPoV2> v2List, Map<String, Object> returnMap) {
	//		if (v2List != null && v2List.size() > 0) {
	//			List<SapDailyUplPoV2> newV2List = new ArrayList<SapDailyUplPoV2>();
	//			for (SapDailyUplPoV2 v2 : v2List) {
	//				v2.setSynStatus("0");
	//				newV2List.add(v2);
	//			}
	//			sapDailyUplPoV2Repository.save(newV2List);
	//		}
	//	}
	//
	//	/**
	//	 * 判断订单是否已经做了转储操作
	//	 * @param poNo
	//	 * @return
	//	 */
	//	private Boolean isStoed(String poNo) {
	//		List<PosIntercoListDetail> intercoListDetailList = posIntercoListDetailRepository.findByPoNo(poNo);
	//		if (intercoListDetailList != null && intercoListDetailList.size() > 0) {
	//			return true;
	//		}
	//		return false;
	//	}

	//	/**
	//	 * 准备数据
	//	 *
	//	 * @param sapDailyUplPo GBSS订单上传队列
	//	 * @param sapDailyUplPoV2 服务中心订单上传队列
	//	 * @return DealerOrderVo
	//	 * @throws Exception
	//	 */
	//	private DealerOrderVo prepareData(SapDailyUplPo sapDailyUplPo, SapDailyUplPoV2 sapDailyUplPoV2) throws Exception {
	//		try {
	//			//			numFormat.setGroupingUsed(false);
	//			// 上传订单的传入参数
	//			DealerOrderVo dealerOrderVo = new DealerOrderVo();
	//
	//			//将全局的parentMap改到这里初始化.然后在参数中传递赋值
	//			Map<String, BigDecimal> parentMap = new HashMap<String, BigDecimal>();
	//
	//			// 传入订单主表
	//			POrderHeader pOrderHeader = new POrderHeader();
	//
	//			// 根据PONO找到订单
	//			if (sapDailyUplPo == null) {
	//				// 修改sapDailyUplPoV2的同步状态为‘1’,直到运行完或出错改回‘0’
	//				//				sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_1);
	//				//				sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//			} else {
	//				// 修改sapDailyUplPo的同步状态为‘1’,直到运行完或出错改回‘0’
	//				//				sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_1);
	//				//				dsSapDailyUplPoRepository.save(sapDailyUplPo);
	//			}
	//			PoMaster poMaster = dsPoMasterRepository.findByPoNo(sapDailyUplPo == null ? sapDailyUplPoV2.getPoNo() : sapDailyUplPo.getPoNo());
	//			if (poMaster == null) {
	//				return null; // return null; 意味着这张单据不会调上传订单sap接口
	//			}
	//			dealerOrderVo.setPoNo(poMaster.getPoNo());// 多线程时能取到
	//
	//			// 取订单类型
	//			String poProcessCode = poMaster.getPoProcessCode();
	//			int orderSapType = this.getSapType(poProcessCode);
	//			// 根据订单号查询订单详细信息
	//			List<PoDetail> poDetailList = dsPoDetailRepository.findByPoNoOrderByLineNoAsc(poMaster.getPoNo());
	//			//			// 查找订货支付信息
	//			//			List<PoPaymentDetail> poPaymentDetailList = dsPoPaymentDetailRepository.findByPoNo(poMaster.getPoNo());
	//			//			// 服务中心的支付信息
	//			//						List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository
	//			//								.queryByRefDocNoAndPaymentTypeSlrMk01(poMaster.getPoNo());
	//			// 取出产品编号与lineNo
	//			this.getParentLineNum(poDetailList, parentMap);
	//			// 根据订单号查询订单配送地址信息
	//			PoAddrDetail poAddrDetail = dsPoAddrDetailRepository.findByPoNo(poMaster.getPoNo());
	//			// 根据用户号查询会员信息
	//			Dealer dealer = dsDealerRepository.findByDealerNo(poMaster.getOrderDealerNo());
	//
	//			// 推荐的新卡号申业后马上退货，然后又退货推荐单，导致推荐的新卡号被删除。
	//			if (dealer == null) {
	//				DealerDeleteBase deletedDeale = dealerDeleteBaseRepository.getDeletedDealer(poMaster.getOrderDealerNo());
	//				dealer = new Dealer();
	//				dealer.setSaleZoneNo(deletedDeale.getSaleZoneNo());
	//			}
	//
	//			// 公共字段赋值 传入订单主表
	//			this.theSameBill(pOrderHeader, poMaster, poAddrDetail, dealer);
	//
	//			// BUPREQ-8521
	//			// 服务中心订单加入发票信息，需要上传发票信息至SAP
	//			// add by mzj
	//			if (PoProcessCodeEnum.G301.getCode().equals(poProcessCode) || PoProcessCodeEnum.G302.getCode().equals(poProcessCode)
	//					|| PoProcessCodeEnum.G303.getCode().equals(poProcessCode)) {
	//				if (StringUtils.isNotBlank(poMaster.getPoNo())) {
	//					PoInvoiceInfo info = poInvoiceInfoRepository.findByPoAppNo(poMaster.getPoNo());
	//					if (info != null && "E".equals(info.getInvoiceType())) {
	//						this.setInvoiceBill(pOrderHeader, info);
	//					}
	//				}
	//			}
	//
	//			// 根据订单类型赋值个性字段开始
	//			// 总公司普通单
	//			if (orderSapType == TypeSapOrderType.ZNF3) {
	//				this.companyCommonBill(pOrderHeader, poMaster, poAddrDetail);
	//			}
	//			// 总公司预定／记欠单
	//			if (orderSapType == TypeSapOrderType.ZYD1) {
	//				this.companyBookingBill(pOrderHeader, poMaster);
	//			}
	//			// 总公司免费单  月刊单  // 月刊当赋值内容和 总公司免费单一样，所以调用和总公司免费单一样的方法
	//			if (orderSapType == TypeSapOrderType.ZFRE || orderSapType == TypeSapOrderType.ZNF6) {
	//				this.companyFreeBill(pOrderHeader, poMaster);
	//			}
	//
	//			// 服务中心普通单
	//			if (orderSapType == TypeSapOrderType.ZNF4) {
	//				this.serviceCenterCommonBill(pOrderHeader, poMaster);
	//			}
	//			// 服务中心免费单
	//			if (orderSapType == TypeSapOrderType.ZGFR) {
	//				this.serviceCenterFreeBill(pOrderHeader, poMaster);
	//				//add ky_qrj 2014-05-27
	//				if (poMaster.getPoProcessCode().equals(TypeSapProcessCode.G311)) {
	//					pOrderHeader.setOrdReason("");
	//				}
	//				//end 2014-05-27
	//			}
	//			// 服务中心退货单 G305
	//			if (orderSapType == TypeSapOrderType.ZRE2) {
	//				this.serviceCenterReturnBill(pOrderHeader, poMaster);
	//			}
	//			// add by yx 2012-11-29 服务中心代退货单 G306 start
	//			if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
	//				this.serviceCenterReturnGbss(pOrderHeader, poMaster);
	//			}
	//			// add by yx 2012-11-29  end
	//
	//			//add by Kevin.wang 2015-06-26 因怡瑞召回新增G501类型
	//			if (orderSapType == TypeSapOrderType.ZREA) {
	//				this.storeReturnCompany(pOrderHeader, poMaster, poAddrDetail);
	//			}
	//			//add end
	//
	//			//add by zdm 2020-01-03 服务中心发票上传
	//			//			PoInvoiceInfo info = poInvoiceInfoRepository.findByPoAppNo(poMaster.getPoAppNo());
	//			//			this.setInvoiceBill(pOrderHeader, info);
	//			//add end
	//
	//			dealerOrderVo.setOrderHeader(pOrderHeader);
	//			// 传入订单明细列表
	//			List<TOrderItems> tOrderItemsList = new ArrayList<TOrderItems>();
	//			// 传入定价条件列表
	//			List<TCondition> tConditionList = new ArrayList<TCondition>();
	//			// bom产品是行号的写法
	//			// t_condition和T_ORDER_ITEMS的赋值
	//			Date poDate = poMaster.getPoDate();
	//			//获取订货折扣明细集合
	//			List<PoDetailDiscount> poDetailDiscountList = this.getPoDetailDiscountListByPoNo(poMaster.getPoNo());
	//			for (PoDetail poDetail : poDetailList) {
	//				boolean isProm = poDetail.getPromQty().compareTo(BigDecimal.ZERO) != 0;
	//				// 传入订单明细 共同的项 (普通项)
	//				TOrderItems tOrderItems = this.theSameTorderItems(poDetail, poAddrDetail, poMaster, parentMap, false);
	//				// 传入订单明细 共同的项（活动产品）
	//				TOrderItems tOrderItems4Prom = this.theSameTorderItems(poDetail, poAddrDetail, poMaster, parentMap, true);
	//				// 总公司普通单
	//				if (orderSapType == TypeSapOrderType.ZNF3) {
	//					this.companyCommonTorderItems(poDetail, tOrderItems, poProcessCode);
	//				}
	//				// 总公司预定／记欠单
	//				if (orderSapType == TypeSapOrderType.ZYD1) {
	//					if (tOrderItems != null) {
	//						this.companyBookingTorderItems(poDetail, tOrderItems);
	//					}
	//					if (tOrderItems4Prom != null) {
	//						this.companyBookingTorderItems(poDetail, tOrderItems4Prom);
	//					}
	//				}
	//				// 总公司免费单
	//				if (orderSapType == TypeSapOrderType.ZFRE) {
	//					this.companyFreeTorderItems(poDetail, tOrderItems);
	//				}
	//
	//				// 2012-11-15 add by yx 增加总公司 月刊单 start 
	//				// 总公司 月刊单
	//				if (orderSapType == TypeSapOrderType.ZNF6) {
	//					this.companyMonthlyTorderItems(poDetail, tOrderItems);
	//				}
	//				// 2012-11-15 add by yx 增加总公司 月刊单 end 
	//
	//				// 服务中心普通单
	//				if (orderSapType == TypeSapOrderType.ZNF4) {
	//					this.serviceCenterCommonTorderItems(poDetail, tOrderItems, poDate);
	//				}
	//				// 服务中心免费单
	//				if (orderSapType == TypeSapOrderType.ZGFR) {
	//					this.serviceCenterFreeTorderItems(poDetail, tOrderItems, poDate);
	//					//add ky_qrj 2014-05-27
	//					if (poMaster.getPoProcessCode().equals(TypeSapProcessCode.G311)) {
	//						tOrderItems.setItemCateg("ZTAX");
	//					}
	//					//end 2014-05-27
	//				}
	//				// 服务中心退货单 G305
	//				if (orderSapType == TypeSapOrderType.ZRE2) {
	//					this.serviceCenterReturnTorderItems(poDetail, tOrderItems, poDate);
	//				}
	//
	//				// add by yx 2012-11-29 服务中心代退货单 G306 无参考退货单G308start
	//				if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
	//					if (tOrderItems != null) {
	//						this.serviceCenterReturnGbssTorderItems(poDetail, tOrderItems, poDate, poProcessCode);
	//					}
	//					if (tOrderItems4Prom != null) {
	//						this.serviceCenterReturnGbssTorderItems(poDetail, tOrderItems4Prom, poDate, poProcessCode);
	//					}
	//				}
	//				// add by yx 2012-11-29 end 
	//
	//				// add by kevin.wang 2015-06-25 G501
	//				if (orderSapType == TypeSapOrderType.ZREA) {
	//					this.storeReturnCompanyItems(poDetail, tOrderItems, poDate);
	//				}
	//				// add  end
	//				if (tOrderItems != null) {
	//					tOrderItemsList.add(tOrderItems);
	//				}
	//				if (tOrderItems4Prom != null) {
	//					tOrderItemsList.add(tOrderItems4Prom);
	//				}
	//				if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
	//					PoDetailDiscount poDetailDiscount = null;
	//					if (CollectionUtils.isNotEmpty(poDetailDiscountList)) {
	//						for (PoDetailDiscount tempPoDetailDiscount : poDetailDiscountList) {
	//							if (tempPoDetailDiscount.getLineNo() == poDetail.getLineNo().intValue()) {
	//								poDetailDiscount = tempPoDetailDiscount;
	//								break;
	//							}
	//						}
	//					}
	//					this.setTConditionListByPoDetailNotPos(poDetail, tConditionList, poDetailDiscount);
	//				} else {
	//					// 定价列表根据PoDetail加入2种定价类型：单价、原点数
	//					this.setTConditionListByPoDetail(poDetail, tConditionList);
	//				}
	//			}
	//			dealerOrderVo.settOrderItemsList(tOrderItemsList);
	//			// posAccTranDetail 与poPaymentDetail可能都写了启业套装
	//			//			if (orderSapType == TypeSapOrderType.ZNF4 || orderSapType == TypeSapOrderType.ZGFR
	//			//					|| orderSapType == TypeSapOrderType.ZRE2 || orderSapType == TypeSapOrderType.ZRE7
	//			//					|| orderSapType == TypeSapOrderType.ZRE8) {
	//			//				// 服务中心的支付类型
	//			//				for (PosAccTranDetail posAccTranDetail : posAccTranDetailList) {
	//			//					// 定价列表根据PoPaymentDetail加入剩下的定价类型
	//			//					this.setTConditionListByPosAccTranDetail(posAccTranDetail, tConditionList);
	//			//				}
	//			//			} else {
	//			if ("G304".equals(poMaster.getPoProcessCode())) {
	//				List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository
	//						.findByPosTranDateAndPosStoreNoAndRefDocNo(poMaster.getPoDate(), poMaster.getPoStoreNo(), poMaster.getPoNo());
	//				for (PosAccTranDetail posAccTranDetail : posAccTranDetailList) {
	//					// 定价列表根据PoPaymentDetail加入剩下的定价类型
	//					this.setTConditionListByPosAccTranDetail(posAccTranDetail, tConditionList);
	//				}
	//			} else {
	//				// 查找订货支付信息
	//				List<PoPaymentDetail> poPaymentDetailList = dsPoPaymentDetailRepository.findByPoNo(poMaster.getPoNo());
	//				// 处理整单T_CONDITION
	//				for (PoPaymentDetail poPaymentDetail : poPaymentDetailList) {
	//					// 定价列表根据PoPaymentDetail加入剩下的定价类型
	//					this.setTConditionListByPoPaymentDetail(poPaymentDetail, tConditionList);
	//				}
	//			}
	//			// 
	//			if (orderSapType == TypeSapOrderType.ZNF3 || orderSapType == TypeSapOrderType.ZYD1 || orderSapType == TypeSapOrderType.ZNF4
	//					|| orderSapType == TypeSapOrderType.ZRE2) {
	//				this.setTConditionListByPoMaster(poMaster, tConditionList);
	//			}
	//
	//			//GBSS-17238
	//			//2017新星启航增加折扣参数进去
	//			//Add by kevin.wang 2017-03-01
	//			if ("G310".equals(poMaster.getPoProcessCode())) {
	//				this.setTConditionListWithZKBM(poMaster, tConditionList);
	//			} else if ((TypeSapProcessCode.G306.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G001.equals(poMaster.getPoProcessCode())
	//					|| TypeSapProcessCode.G002.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G020.equals(poMaster.getPoProcessCode()))
	//					&& (BigDecimal.ZERO.compareTo(poMaster.getTotalSaleDiscountAmt()) != 0)) {//20170516添加微信折扣金额 add by zhuohr
	//				//this.setTConditionListWithZKWX(poMaster,tConditionList);
	//			}
	//			dealerOrderVo.settConditionList(tConditionList);
	//			return dealerOrderVo;
	//		} catch (Exception e) {
	//			try {
	//				if (sapDailyUplPo == null) {
	//					// 出错写错误信息恢复同步状态0
	//					sapDailyUplPoV2.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPoV2.setSynErrCnt(sapDailyUplPoV2.getSynErrCnt() + 1);
	//					// 超次数设删除标志
	//					if (sapDailyUplPoV2.getSynErrCnt() >= getPosSynErrCnt()) {
	//						sapDailyUplPoV2.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//					}
	//					/*String mess = e.getMessage() != null
	//					        && e.getMessage().length() >= 500 ? e.getMessage()
	//					        .substring(0, 499) : e.getMessage();*/
	////					sapDailyUplPoV2.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	////					sapDailyUplPoV2Repository.save(sapDailyUplPoV2);
	//				} else {
	//					// 出错写错误信息恢复同步状态0
	//					sapDailyUplPo.setSynStatus(TypeSapPostingFlag.SYN_STATUS_0);
	//					sapDailyUplPo.setSynErrCnt(new BigDecimal(sapDailyUplPo.getSynErrCnt().intValue() + 1));
	//					// 超次数设删除标志
	//					if (sapDailyUplPo.getSynErrCnt().compareTo(getSynErrCnt()) != -1) {
	//						sapDailyUplPo.setSynType(TypeSapPostingFlag.SYN_TYPE_D);
	//					}
	//					/*String mess = e.getMessage() != null
	//					        && e.getMessage().length() >= 500 ? e.getMessage()
	//					        .substring(0, 499) : e.getMessage();*/
	//					sapDailyUplPo.setSynErrMsg(SapFormatUtil.subByteLen(e.getMessage(), 1000));
	//					dsSapDailyUplPoRepository.save(sapDailyUplPo);
	//				}
	//
	//				log.error("运行异常", e);
	//				throw e;
	//			} catch (Exception e1) {
	//				log.error("运行异常", e1);
	//				throw e1;
	//			}
	//		}
	//
	//	}

	/**
	 * 定价列表根据PosAccTranDetail加入剩下的定价类型
	 *
	 * @param posAccTranDetail
	 * @param tConditionList
	 */
	private void setTConditionListByPosAccTranDetail(PosAccTranDetail posAccTranDetail, List<TCondition> tConditionList) {

		// 消费积分
		if (posAccTranDetail.getPosPaymentType().equals(TypePoPaymentType.SLR)) {
			// 消费积分:ZK91
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK91");
			String value = posAccTranDetail.getPosTranAmt() == null ? "" : SapFormatUtil.formatNumber(posAccTranDetail.getPosTranAmt());
			tCondition3.setCondValue(value);
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}
		// 企业套装折让
		if (posAccTranDetail.getPosPaymentType().equals(TypePoPaymentType.MK01)) {
			// 启业套装:ZK80
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK80");
			String value = posAccTranDetail.getPosTranAmt() == null ? "" : SapFormatUtil.formatNumber(posAccTranDetail.getPosTranAmt());
			tCondition3.setCondValue(value);
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}

		// 电子券 add by jinxu li 2014-02-18
		if (posAccTranDetail.getPosPaymentType().equals(TypePoPaymentType.MK03)) {
			// 电子券:ZK20
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK20");
			String value = posAccTranDetail.getPosTranAmt() == null ? "" : SapFormatUtil.formatNumber(posAccTranDetail.getPosTranAmt());
			tCondition3.setCondValue(value);
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}
	}

	/**
	 * 定价列表根据PoPaymentDetail加入剩下的定价类型
	 *
	 * @param poPaymentDetail
	 * @param tConditionList
	 */
	private void setTConditionListByPoPaymentDetail(PoPaymentDetail poPaymentDetail, List<TCondition> tConditionList) {
		String paymentType = poPaymentDetail.getPoPaymentType();
		// 消费积分
		if (paymentType.equals(TypePoPaymentType.SLR)) {
			// 消费积分:ZK91
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK91");
			String value = poPaymentDetail.getPoPaymentAmt() == null ? "" : SapFormatUtil.formatNumber(poPaymentDetail.getPoPaymentAmt());
			tCondition3.setCondValue(value);
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}
		// 企业套装折让
		if (paymentType.equals(TypePoPaymentType.MK01)) {
			// 启业套装:ZK80
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK80");
			String value = poPaymentDetail.getPoPaymentAmt() == null ? "" : SapFormatUtil.formatNumber(poPaymentDetail.getPoPaymentAmt());
			tCondition3.setCondValue(value);
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
			// 折扣点数:ZK06
			/*
			 * TCondition tCondition4 = new TCondition();
			 * tCondition4.setItmNumber("00"); tCondition4.setCondType("ZK06");
			 * tCondition4 .setCondValue(poPaymentDetail.getDiscountPoint());
			 * tCondition4.setCurrency("CNY"); tConditionList.add(tCondition4);
			 */
		}
		//电子券 add by jinxu li 2014-02-18
		if (paymentType.equals(TypePoPaymentType.MK03)) {

			BigDecimal tempTotalCouponAmt = BigDecimal.ZERO;
			if (CollectionUtils.isNotEmpty(tConditionList)) {
				for (TCondition tCondition : tConditionList) {
					if ("ZK61".equals(tCondition.getCondType()) || "ZK20".equals(tCondition.getCondType())) {
						tempTotalCouponAmt = tempTotalCouponAmt.add(new BigDecimal(tCondition.getCondValue()));
					}
				}
			}

			BigDecimal totalCouponAmt = poPaymentDetail.getPoPaymentAmt();
			totalCouponAmt = totalCouponAmt.subtract(tempTotalCouponAmt);
			if (totalCouponAmt.compareTo(BigDecimal.ZERO) != 0) {
				// 电子券:ZK20
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber("00");
				tCondition3.setCondType("ZK20");
				String value = SapFormatUtil.formatNumber(totalCouponAmt);
				tCondition3.setCondValue(value);
				tCondition3.setCurrency("CNY");
				tConditionList.add(tCondition3);
			}
		}
	}

	/**
	 * 定价列表根据PoDetail加入2种定价类型：单价、原点数(服务中心)
	 *
	 * @param poDetail
	 * @param tConditionList
	 */
	private void setTConditionListByPoDetail(PoDetail poDetail, List<TCondition> tConditionList) {
		String value = "";
		// prodectattr不是1，且价格为不为0的才传
		if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePrice() != null && poDetail.getSalePrice().compareTo(new BigDecimal(0)) != 0) {
			// 单价:PR00
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber(poDetail.getLineNo().toString());// 申请单行项目
			tCondition1.setCondType("PR00");// 定价类型
			value = poDetail.getSalePrice() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePrice());
			tCondition1.setCondValue(value);// 定价金额
			tCondition1.setCurrency("CNY");// 货币单位
			tConditionList.add(tCondition1);
		}
		// // prodectattr不是1，且价格为不为0的才传
		if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePoint() != null && poDetail.getSalePoint().compareTo(new BigDecimal(0)) != 0) {
			// 原点数:ZK05
			TCondition tCondition2 = new TCondition();
			tCondition2.setItmNumber(poDetail.getLineNo().toString());
			tCondition2.setCondType("ZK05");
			value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
			tCondition2.setCondValue(value);
			tCondition2.setCurrency("CNY");
			tConditionList.add(tCondition2);
			// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber(poDetail.getLineNo().toString());
			tCondition3.setCondType("ZK06");
			value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
			tCondition3.setCondValue(value);
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}
	}

	/**
	 * 定价列表根据PoDetail加入2种定价类型：单价、原点数（非服务中心）
	 *
	 * @param poDetail
	 * @param tConditionList
	 */
	private void setTConditionListByPoDetailNotPos(PoDetail poDetail, List<TCondition> tConditionList, PoDetailDiscount poDetailDiscount) {
		BigDecimal promQtyBigDecimal = poDetail.getPromQty();
		BigDecimal saleQtyBigDecimal = poDetail.getSaleQty().subtract(promQtyBigDecimal);
		BigDecimal lineNo = poDetail.getLineNo();
		String value = "";
		BigDecimal saleRebate = saleQtyBigDecimal.multiply(poDetail.getSaleRebate());
		BigDecimal promRebate = promQtyBigDecimal.multiply(poDetail.getPromRebate());
		BigDecimal saleAmt = saleQtyBigDecimal.multiply(poDetail.getSalePrice());
		BigDecimal promAmt = promQtyBigDecimal.multiply(poDetail.getPromPrice());
		BigDecimal rebateDisAmtProduct = BigDecimal.ZERO;//优差折扣金额（单品）
		BigDecimal rebateDisAmtCoupon = BigDecimal.ZERO;//优差折扣金额（电子券分摊）
		BigDecimal rebateDisAmtWhole = BigDecimal.ZERO;//优差折扣金额（整单减分摊）
		BigDecimal companyDisAmtProduct = BigDecimal.ZERO;//公司折扣金额（单品）
		BigDecimal companyDisAmtCoupon = BigDecimal.ZERO;//公司折扣金额（电子券分摊）
		BigDecimal companyDisAmtWhole = BigDecimal.ZERO;//公司折扣金额（整单减分摊）
		BigDecimal rebateDisAmtProductForProm = BigDecimal.ZERO;//优差折扣金额（单品）
		BigDecimal rebateDisAmtCouponForProm = BigDecimal.ZERO;//优差折扣金额（电子券分摊）
		BigDecimal rebateDisAmtWholeForProm = BigDecimal.ZERO;//优差折扣金额（整单减分摊）
		BigDecimal companyDisAmtProductForProm = BigDecimal.ZERO;//公司折扣金额（单品）
		BigDecimal companyDisAmtCouponForProm = BigDecimal.ZERO;//公司折扣金额（电子券分摊）
		BigDecimal companyDisAmtWholeForProm = BigDecimal.ZERO;//公司折扣金额（整单减分摊）
		if (poDetailDiscount != null) {
			rebateDisAmtProduct = poDetailDiscount.getRebateDisAmtProduct();//优差折扣金额（单品）
			rebateDisAmtCoupon = poDetailDiscount.getRebateDisAmtCoupon();//优差折扣金额（电子券分摊）
			rebateDisAmtWhole = poDetailDiscount.getRebateDisAmtWhole();//优差折扣金额（整单减分摊）
			companyDisAmtProduct = poDetailDiscount.getCompanyDisAmtProduct();//公司折扣金额（单品）
			companyDisAmtCoupon = poDetailDiscount.getCompanyDisAmtCoupon();//公司折扣金额（电子券分摊）
			companyDisAmtWhole = poDetailDiscount.getCompanyDisAmtWhole();//公司折扣金额（整单减分摊）
			if (promQtyBigDecimal.compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal totalRebate = promRebate.add(saleRebate);
				//先按比例算出促销占的那部分金额
				BigDecimal promRebateProportion = (totalRebate.compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0.5")
						: promRebate.divide(totalRebate, 8, BigDecimal.ROUND_HALF_UP);
				rebateDisAmtProductForProm = rebateDisAmtProduct;
				rebateDisAmtCouponForProm = rebateDisAmtCoupon.multiply(promRebateProportion).setScale(2, BigDecimal.ROUND_HALF_UP);
				rebateDisAmtWholeForProm = rebateDisAmtWhole.multiply(promRebateProportion).setScale(2, BigDecimal.ROUND_HALF_UP);
				rebateDisAmtProduct = rebateDisAmtProduct.subtract(rebateDisAmtProductForProm);
				rebateDisAmtCoupon = rebateDisAmtCoupon.subtract(rebateDisAmtCouponForProm);
				rebateDisAmtWhole = rebateDisAmtWhole.subtract(rebateDisAmtWholeForProm);
				BigDecimal totalAmt = promAmt.add(saleAmt);
				BigDecimal promAmtProportion = (totalAmt.compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0.5")
						: promAmt.divide(totalAmt, 8, BigDecimal.ROUND_HALF_UP);
				companyDisAmtProductForProm = companyDisAmtProduct;
				companyDisAmtCouponForProm = companyDisAmtCoupon.multiply(promAmtProportion).setScale(2, BigDecimal.ROUND_HALF_UP);
				companyDisAmtWholeForProm = companyDisAmtWhole.multiply(promAmtProportion).setScale(2, BigDecimal.ROUND_HALF_UP);
				companyDisAmtProduct = companyDisAmtProduct.subtract(companyDisAmtProductForProm);
				companyDisAmtCoupon = companyDisAmtCoupon.subtract(companyDisAmtCouponForProm);
				companyDisAmtWhole = companyDisAmtWhole.subtract(companyDisAmtWholeForProm);
			}
		}

		if (saleQtyBigDecimal.compareTo(BigDecimal.ZERO) != 0) {
			// prodectattr不是1，且价格为不为0的才传
			if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePrice() != null
					&& poDetail.getSalePrice().compareTo(BigDecimal.ZERO) != 0) {
				// 单价:PR00
				TCondition tCondition1 = new TCondition();
				tCondition1.setItmNumber(lineNo.toString());// 申请单行项目
				tCondition1.setCondType("PR00");// 定价类型
				value = poDetail.getSalePrice() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePrice());
				tCondition1.setCondValue(value);// 定价金额
				tCondition1.setCurrency("CNY");// 货币单位
				tConditionList.add(tCondition1);
			}
			// // prodectattr不是1，且价格为不为0的才传
			if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePoint() != null
					&& poDetail.getSalePoint().compareTo(BigDecimal.ZERO) != 0) {
				// 原点数:ZK05
				TCondition tCondition2 = new TCondition();
				tCondition2.setItmNumber(lineNo.toString());
				tCondition2.setCondType("ZK05");
				value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
				tCondition2.setCondValue(value);
				tCondition2.setCurrency("CNY");
				tConditionList.add(tCondition2);
				// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber(lineNo.toString());
				tCondition3.setCondType("ZK06");
				value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
				tCondition3.setCondValue(value);
				tCondition3.setCurrency("CNY");
				tConditionList.add(tCondition3);
			}
			/// 单位理论优差金额
			if (!poDetail.getProductAttr().equals("1")) {
				BigDecimal totalSaleRebate = poDetail.getSaleRebate().multiply(saleQtyBigDecimal);
				if (totalSaleRebate.compareTo(BigDecimal.ZERO) != 0) {
					// 单位理论优差:ZK71
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(lineNo.toString());
					tCondition2.setCondType("ZK71");
					totalSaleRebate = totalSaleRebate.abs();
					value = poDetail.getSaleRebate() == null ? "" : SapFormatUtil.formatNumber(totalSaleRebate);
					tCondition2.setCondValue(value);
					tCondition2.setCurrency("CNY");
					tConditionList.add(tCondition2);
				}
			}

			//使用了优惠券
			// ZK74 优差折扣金额（整单减分摊）
			if (poDetailDiscount != null) {
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (rebateDisAmtWhole.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZK74");
						value = SapFormatUtil.formatNumber(rebateDisAmtWhole);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK61 优差折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (rebateDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZK61");
						value = SapFormatUtil.formatNumber(rebateDisAmtCoupon);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZKWX 公司折扣金额（整单减分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtWhole.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZKWX");
						value = SapFormatUtil.formatNumber(companyDisAmtWhole);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK20 公司折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZK20");
						value = SapFormatUtil.formatNumber(companyDisAmtCoupon);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
			}
		}
		if (promQtyBigDecimal.compareTo(BigDecimal.ZERO) != 0) {
			lineNo = lineNo.add(BigDecimal.TEN);
			// prodectattr不是1，且价格为不为0的才传
			if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePrice() != null
					&& poDetail.getSalePrice().compareTo(BigDecimal.ZERO) != 0) {
				// 单价:PR00
				TCondition tCondition1 = new TCondition();
				tCondition1.setItmNumber(lineNo.toString());// 申请单行项目
				tCondition1.setCondType("PR00");// 定价类型
				value = poDetail.getSalePrice() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePrice());
				tCondition1.setCondValue(value);// 定价金额
				tCondition1.setCurrency("CNY");// 货币单位
				tConditionList.add(tCondition1);
			}
			// // prodectattr不是1，且价格为不为0的才传
			if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePoint() != null
					&& poDetail.getSalePoint().compareTo(BigDecimal.ZERO) != 0) {
				// 原点数:ZK05
				TCondition tCondition2 = new TCondition();
				tCondition2.setItmNumber(lineNo.toString());
				tCondition2.setCondType("ZK05");
				value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
				tCondition2.setCondValue(value);
				tCondition2.setCurrency("CNY");
				tConditionList.add(tCondition2);
				// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber(lineNo.toString());
				tCondition3.setCondType("ZK06");
				value = poDetail.getSalePoint() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePoint());
				tCondition3.setCondValue(value);
				tCondition3.setCurrency("CNY");
				tConditionList.add(tCondition3);
			}
			/// 单位理论优差金额（即单位优差 * 购买的数量）
			//单位优差为 销售价与产品价格的差值
			//活动行的zk71应该是与活动数量的乘积，不是销售数量
			if (!poDetail.getProductAttr().equals("1")) {
				BigDecimal totalSaleRebate = poDetail.getSaleRebate().multiply(promQtyBigDecimal);
				if (totalSaleRebate.compareTo(BigDecimal.ZERO) != 0) {
					// 单位理论优差:ZK71
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(lineNo.toString());
					tCondition2.setCondType("ZK71");
					totalSaleRebate = totalSaleRebate.abs();
					value = poDetail.getSaleRebate() == null ? "" : SapFormatUtil.formatNumber(totalSaleRebate);
					tCondition2.setCondValue(value);
					tCondition2.setCurrency("CNY");
					tConditionList.add(tCondition2);
				}
			}
			/// 单位优差折扣金额
			if (!poDetail.getProductAttr().equals("1")) {
				BigDecimal promRebateTotal = BigDecimal.ZERO;
				if (poDetailDiscount != null) {
					promRebateTotal = rebateDisAmtProductForProm;
				} else {
					BigDecimal totalNonRebate = poDetail.getSaleRebate().multiply(poDetail.getSaleQty());
					BigDecimal totalPromRebate = poDetail.getPromRebate().multiply(promQtyBigDecimal)
							.add(poDetail.getSaleRebate().multiply(saleQtyBigDecimal));
					promRebateTotal = totalNonRebate.subtract(totalPromRebate);
				}
				if (promRebateTotal.compareTo(BigDecimal.ZERO) != 0) {
					// 单位优差折扣金额:ZK72
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(lineNo.toString());
					tCondition2.setCondType("ZK72");
					value = SapFormatUtil.formatNumber(promRebateTotal);
					tCondition2.setCondValue(value);
					tCondition2.setCurrency("CNY");
					tConditionList.add(tCondition2);
				}
			}
			/// 公司单品折扣
			if (!poDetail.getProductAttr().equals("1")) {
				BigDecimal totalPromDisAmt = BigDecimal.ZERO;
				if (poDetailDiscount != null) {
					totalPromDisAmt = companyDisAmtProductForProm;
				} else {
					BigDecimal productPrice = poDetail.getProductPrice();
					BigDecimal promPrice = poDetail.getPromPrice();
					totalPromDisAmt = BigDecimal.ZERO;
					if (promPrice.compareTo(productPrice) < 0) {
						totalPromDisAmt = productPrice.subtract(promPrice);
					}
					totalPromDisAmt = totalPromDisAmt.multiply(promQtyBigDecimal);
				}
				if (totalPromDisAmt.compareTo(BigDecimal.ZERO) != 0) {
					// 单品折扣:ZK62
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(lineNo.toString());
					tCondition2.setCondType("ZK62");
					value = SapFormatUtil.formatNumber(totalPromDisAmt);
					tCondition2.setCondValue(value);
					tCondition2.setCurrency("CNY");
					tConditionList.add(tCondition2);
				}
			}
			if (poDetailDiscount != null) {
				//使用了优惠券
				// ZK74 优差折扣金额（整单减分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (rebateDisAmtWholeForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZK74");
						value = SapFormatUtil.formatNumber(rebateDisAmtWholeForProm);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK61 优差折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (rebateDisAmtCouponForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZK61");
						value = SapFormatUtil.formatNumber(rebateDisAmtCouponForProm);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZKWX 公司折扣金额（整单减分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtWholeForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZKWX");
						value = SapFormatUtil.formatNumber(companyDisAmtWholeForProm);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK20 公司折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtCouponForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(lineNo.toString());
						tCondition2.setCondType("ZK20");
						value = SapFormatUtil.formatNumber(companyDisAmtCouponForProm);
						tCondition2.setCondValue(value);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
			}
		}

	}

	/**
	 * 
	 * @param poDetail
	 * @param tConditionList
	 
	 */
	@Deprecated
	private void setCppTConditionListByPoDetail(PoDetail poDetail, List<TCondition> tConditionList, String paymentType) {
		// 单价:PR00
		TCondition tCondition1 = new TCondition();
		tCondition1.setItmNumber(poDetail.getLineNo().toString());// 申请单行项目
		tCondition1.setCondType("PR00");// 定价类型
		tCondition1.setCurrency("CNY");// 货币单位
		if (TypePoPaymentType.CPPCASH.equals(paymentType)) //现金＋积分
			tCondition1.setCondValue(poDetail.getSalePrice().toString());// 基准价
		else
			tCondition1.setCondValue("0");//纯新积分
		tConditionList.add(tCondition1);
	}

	/**
	 * GBSS-17453
	 * OLDGBSS-3764 
	 * 统一PR00获取为纷享绘传过来的
	 * @param poDetail
	 * @param tConditionList
	 
	 */
	private void setCppTConditionListByPoDetail(PoDetail poDetail, MkpDlpPoRelate mkpDlpPoRelate, List<TCondition> tConditionList,
			PoDetailDiscount poDetailDiscount) {
		//OLDGBSS-3764 
		//只有现金与折扣全为0 才是免费单
		//modify by kevin.wang 2019.12.17
		if (mkpDlpPoRelate != null && ((mkpDlpPoRelate.getCashAmt() != null && mkpDlpPoRelate.getCashAmt().compareTo(BigDecimal.ZERO) > 0))) {
			// 单价:PR00
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber(poDetail.getLineNo().toString());// 申请单行项目
			tCondition1.setCondType("PR00");// 定价类型
			tCondition1.setCurrency("CNY");// 货币单位
			tCondition1.setCondValue(poDetail.getSalePrice().toString());// 基准价
			tConditionList.add(tCondition1);

			//记录分享会行项目的折扣
			//add by mzj
			String value = "";
			if (poDetailDiscount != null) {
				if (poDetailDiscount.getCompanyDisAmtWhole().compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(String.valueOf(poDetailDiscount.getLineNo()));
					tCondition2.setCondType("ZF01");
					value = SapFormatUtil.formatNumber(poDetailDiscount.getCompanyDisAmtWhole());
					tCondition2.setCondValue(value);
					tCondition2.setCurrency("CNY");
					tConditionList.add(tCondition2);
				}
			}
			//end
		}
	}

	/**
	 * 
	 * @param tConditionList
	 
	 */
	@Deprecated
	private void setCppTConditionAddYT01AndZF01(List<TCondition> tConditionList, MkpDlpPoRelate mkpDlpPoRelate, String paymentType) {
		if (TypePoPaymentType.CPPCASH.equals(paymentType)) {//现金＋积分
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber("00");// 申请单行项目
			tCondition1.setCondType("YT01");// 定价类型
			tCondition1.setCurrency("CNY");// 货币单位
			if (mkpDlpPoRelate != null) {
				tCondition1.setCondValue(mkpDlpPoRelate.getTotalDlpAmt().toString());//预提值
			}
			tConditionList.add(tCondition1);
			TCondition tCondition2 = new TCondition();
			tCondition2.setItmNumber("00");// 申请单行项目
			tCondition2.setCondType("ZF01");// 定价类型
			tCondition2.setCurrency("CNY");// 货币单位
			if (mkpDlpPoRelate != null) {
				tCondition2.setCondValue(mkpDlpPoRelate.getTotalDiscountAmt().toString());//积分抵扣金额
			}
			tConditionList.add(tCondition2);
		} else {
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber("00");// 申请单行项目
			tCondition1.setCondType("YT01");// 定价类型
			tCondition1.setCurrency("CNY");// 货币单位
			if (mkpDlpPoRelate != null) {
				tCondition1.setCondValue(mkpDlpPoRelate.getTotalDlpAmt().toString());// 预提值
			}
			tConditionList.add(tCondition1);
		}
	}

	/**
	 * GBSS-17453
	 * ZF01,YT01获取为纷享绘传过来的
	 * @param tConditionList
	 * @param mkpDlpPoRelate
	 */
	private void setCppTConditionAddYT01AndZF01(List<TCondition> tConditionList, MkpDlpPoRelate mkpDlpPoRelate) {
		//OLDGBSS-3764 
		//只有现金与折扣全为0 才是免费单
		//modify by kevin.wang 2019.12.17
		if (mkpDlpPoRelate != null && ((mkpDlpPoRelate.getCashAmt() != null && mkpDlpPoRelate.getCashAmt().compareTo(BigDecimal.ZERO) > 0))) {
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber("00");// 申请单行项目
			tCondition1.setCondType("YT01");// 定价类型
			tCondition1.setCurrency("CNY");// 货币单位
			BigDecimal totalDlpAmt = null;
			if (mkpDlpPoRelate != null) {
				totalDlpAmt = mkpDlpPoRelate.getTotalDlpAmt();
			}
			if (totalDlpAmt == null)
				totalDlpAmt = BigDecimal.ZERO;
			tCondition1.setCondValue(totalDlpAmt.toString());//预提值
			tConditionList.add(tCondition1);

			//记录整单折扣
			BigDecimal lineDiscountAmt = BigDecimal.ZERO;
			if (CollectionUtils.isNotEmpty(tConditionList)) {
				for (TCondition tCondition : tConditionList) {
					if ("ZF01".equals(tCondition.getCondType())) {
						lineDiscountAmt = lineDiscountAmt.add(new BigDecimal(tCondition.getCondValue()));
					}
				}
			}

			BigDecimal totalDiscountAmt = null;
			if (mkpDlpPoRelate != null) {
				totalDiscountAmt = mkpDlpPoRelate.getTotalDiscountAmt();
			}
			if (totalDiscountAmt == null)
				totalDiscountAmt = BigDecimal.ZERO;
			totalDiscountAmt = totalDiscountAmt.subtract(lineDiscountAmt);
			if (totalDiscountAmt.compareTo(BigDecimal.ZERO) != 0) {
				TCondition tCondition2 = new TCondition();
				tCondition2.setItmNumber("00");// 申请单行项目
				tCondition2.setCondType("ZF01");// 定价类型
				tCondition2.setCurrency("CNY");// 货币单位
				tCondition2.setCondValue(totalDiscountAmt.toString());//积分抵扣金额
				tConditionList.add(tCondition2);
			}
		}
	}

	private void setCppTConditionListByPoDetailAddZF01(String value, List<TCondition> tConditionList) {
		if (value == null) {
			return;
		}
		// prodectattr不是1，且价格为不为0的才传

		TCondition tCondition4 = new TCondition();
		tCondition4.setItmNumber("00");
		tCondition4.setCondType("ZF01");
		tCondition4.setCondValue(value);
		tCondition4.setCurrency("CNY");
		tConditionList.add(tCondition4);

	}

	/**
	 * 定价列表根据PoMaster的TOTAL_CALC_REBATE 加入1种定价类型：优惠差额:ZK70
	 *
	 * @param poMaster
	 * @param tConditionList
	 */
	private void setTConditionListByPoMaster(PoMaster poMaster, List<TCondition> tConditionList) {
		// 优惠差额:ZK70
		String value = "";
		// TOTAL_CALC_REBATE不为0的才传
		String orderDealerNo = poMaster.getOrderDealerNo();
		BigDecimal totalCalcRebate = poMaster.getTotalCalcRebate();
		if (!"000000000".equals(orderDealerNo) && totalCalcRebate != null && totalCalcRebate.compareTo(BigDecimal.ZERO) != 0) {
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber("00");// 申请单行项目
			tCondition1.setCondType("ZK70");// 定价类型
			totalCalcRebate = totalCalcRebate.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO.subtract(totalCalcRebate) : totalCalcRebate;
			value = SapFormatUtil.formatNumber(totalCalcRebate);
			tCondition1.setCondValue(value);// 定价金额
			tCondition1.setCurrency("CNY");// 货币单位
			tConditionList.add(tCondition1);
		}

		// 2012-12-27 by yx 暂时注销 
		//		// 库存调拨:ZVP3  
		//		Object obj = dsPoDetailRepository.getProductSumAmt(poMaster.getPoNo());
		//		String totalAmt = obj != null ? obj.toString() : "0";
		//		TCondition tCondition2 = new TCondition();
		//		tCondition2.setItmNumber("00");// 申请单行项目
		//		tCondition2.setCondType("ZVP3");// 库存调拨
		//		tCondition2.setCondValue(totalAmt);// 定价金额
		//		tCondition2.setCurrency("CNY");// 货币单位
		//		tConditionList.add(tCondition2);
	}

	/**
	 * 公共的一些 传入订单明细
	 * @param poDetail
	 * @param poAddrDetail
	 * @param poMaster
	 * @param parentMap
	 * @param isProm
	 * @return
	 */
	private TOrderItems theSameTorderItems(PoDetail poDetail, PoAddrDetail poAddrDetail, PoMaster poMaster, Map<String, BigDecimal> parentMap,
			boolean isProm) {
		BigDecimal promQtyBigDecimal = poDetail.getPromQty();
		BigDecimal saleQtyBigDecimal = poDetail.getSaleQty().subtract(promQtyBigDecimal);
		String saleQty = "";
		if (isProm) {
			if (promQtyBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
				return null;
			}
			saleQty = SapFormatUtil.formatNumber(promQtyBigDecimal);
		} else {
			if (saleQtyBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
				return null;
			}
			saleQty = SapFormatUtil.formatNumber(saleQtyBigDecimal);
		}

		TOrderItems tOrderItems = new TOrderItems();
		BigDecimal lineNo = poDetail.getLineNo();
		if (isProm) {
			lineNo = lineNo.add(BigDecimal.TEN);
		}
		tOrderItems.setItmNumber(lineNo.toString());// 销售订单行项目
		tOrderItems.setMaterial(poDetail.getProductCode());// 物料编码

		// 将GBSS中保存的负数转为正数
		if (saleQty.startsWith("-")) {
			saleQty = saleQty.replace("-", "");
		}

		String salePrice = poDetail.getSalePrice() == null ? "" : SapFormatUtil.formatNumber(poDetail.getSalePrice());
		tOrderItems.setTargetQty(saleQty);// 订购数量
		// prodectattr是1，价格传0
		if (poDetail.getProductAttr().equals("1")) {
			salePrice = "0.00";
		}
		tOrderItems.setKwertPr00(salePrice);// 成交价

		// yx 2012-11-29   如果是服务中心销售订单或服务中心本店退货单需要上传到V工厂 其余的订单不需要做修改  change    Start
		//		tOrderItems.setPlant(poAddrDetail.getDeliveryWhCode());// 工厂编码
		String poProcessCode = poMaster.getPoProcessCode();
		//modify qrj G311 传V工厂 2014-5-29
		//		if (commonOrderTypeDetaiService.isPosPurchaseOrder(poProcessCode) || commonOrderTypeDetaiService.isPosReturnOrder(poProcessCode)) { // 如果是销售订单或服务中心本店退货单需要上传到V工厂
		//			tOrderItems.setPlant(posStoService.getVirtualPlant(poAddrDetail.getDeliveryWhCode()));
		//		} else {
		tOrderItems.setPlant(poAddrDetail.getDeliveryWhCode());
		//		}
		// yx 2012-11-1     change    End
		// 2020/6/17 流程码换为产品类型比较
		if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
			tOrderItems.setStoreLoc("");// 仓码
		} else {
			tOrderItems.setStoreLoc(poAddrDetail.getDeliveryWhLocCode());// 仓码
		}

		if (poAddrDetail.getDeliveryPlanDate() != null) {
			//2012-07-27，孔子南说都改成 写入 当前日期.
			//Date reqDate = this.getBranchType(poMaster.getPoBranchNo()) ? poAddrDetail
			//		.getDeliveryPlanDate() : new Date();
			Date reqDate = DateTimeUtil.getCurrentTime();
			tOrderItems.setReqDate(SapFormatUtil.formatDate(reqDate));// 交货日期
		} else {
			tOrderItems.setReqDate("");
		}
		// TODO HgLvItem
		tOrderItems.setHgLvItem("");// 上层项目号
		tOrderItems.setCurrency("CNY");// 币种
		if (poDetail.getProductBomCode() != null && !"".equals(poDetail.getProductBomCode())) {
			if (parentMap.containsKey(poDetail.getProductBomCode())) {
				BigDecimal hgLvItem = parentMap.get(poDetail.getProductBomCode());
				if (isProm) {
					hgLvItem = hgLvItem.add(BigDecimal.TEN);
				}
				tOrderItems.setHgLvItem(hgLvItem.toString());// 上层项目号
			}
		}
		return tOrderItems;
	}

	/**
	 * 服务中心免费单 传入订单明细
	 *
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void serviceCenterFreeTorderItems(PoDetail poDetail, TOrderItems tOrderItems, Date poDate) {
		tOrderItems.setReqDate(SapFormatUtil.formatDate(poDate));// 交货日期
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZTN1");
			} else {
				tOrderItems.setItemCateg("ZFK2");
			}
		}
	}

	/**服务中心普通单  传入订单明细
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void serviceCenterCommonTorderItems(PoDetail poDetail, TOrderItems tOrderItems, Date poDate) {
		tOrderItems.setReqDate(SapFormatUtil.formatDate(poDate));// 交货日期
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZTN1");
			} else {
				tOrderItems.setItemCateg("ZFK2"); // ZFK2
			}
		}
	}

	/**
	 * 服务中心退货单 传入订单明细 G305
	 *
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void serviceCenterReturnTorderItems(PoDetail poDetail, TOrderItems tOrderItems, Date poDate) {
		tOrderItems.setReqDate(SapFormatUtil.formatDate(poDate));// 交货日期
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZTN3");
			} else {
				tOrderItems.setItemCateg("ZFK5");
			}
		}
	}

	/**
	 * 服务中心 退货单 （gbss下单，服务中心退货） 传入订单明细 G306
	 * @param poDetail
	 * @param tOrderItems
	 * @param poDate
	 */
	public void serviceCenterReturnGbssTorderItems(PoDetail poDetail, TOrderItems tOrderItems, Date poDate, String poProcessCode) {
		tOrderItems.setReqDate(SapFormatUtil.formatDate(poDate));// 交货日期

		if (TypeSapProcessCode.G014.equals(poProcessCode)) {
			tOrderItems.setItemCateg("ZREX");
			return;
		}

		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZTN2");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZREX");
				} else {
					tOrderItems.setItemCateg("ZFK4");
				}
			}
		}
	}

	/**
	 * 专卖店退回公司G501
	 * @param poDetail
	 * @param tOrderItems
	 * @param poDate
	 */
	private void storeReturnCompanyItems(PoDetail poDetail, TOrderItems tOrderItems, Date poDate) {
		// TODO Auto-generated method stub
		tOrderItems.setReqDate(SapFormatUtil.formatDate(poDate));// 交货日期
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZTN2");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZREX");
				} else {
					tOrderItems.setItemCateg("ZFK4");
				}
			}
		}
	}

	/**
	 * 总公司免费单 传入订单明细
	 *
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void companyFreeTorderItems(PoDetail poDetail, TOrderItems tOrderItems) {
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZTAX");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
			}
		}
	}

	// 2012-11-15 add by yx 增加总公司月刊单 start
	/**
	 * 总公司 月刊单 传入订单明细
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void companyMonthlyTorderItems(PoDetail poDetail, TOrderItems tOrderItems) {
		tOrderItems.setItemCateg("TANN");
	}

	// 2012-11-15 add by yx 增加总公司月刊单  end

	/**
	 * 总公司预定／记欠单 传入订单明细
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void companyBookingTorderItems(PoDetail poDetail, TOrderItems tOrderItems) {
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("TANN");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
			}
		}
	}

	/**
	 * 总公司普通单 传入订单明细
	 *
	 * @param poDetail
	 * @param tOrderItems
	 */
	private void companyCommonTorderItems(PoDetail poDetail, TOrderItems tOrderItems, String poProcessCode) {
		if (TypeSapProcessCode.G013.equals(poProcessCode)) {
			tOrderItems.setItemCateg("ZTAY");
			return;
		}
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("TANN");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
			}
		}
	}

	/**
	 * 公共字段赋值 传入订单主表
	 *
	 * @param pOrderHeader
	 * @param poMaster
	 * @param poAddrDetail
	 */
	private void theSameBill(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail, Dealer dealer) {
		// 备注
		//pOrderHeader.setBeiZu("");
		// 流程号
		PoProcessCodeInfo poProcessCodeInfo = poProcessCodeInfoRepository.findByPoProcessCode(poMaster.getPoProcessCode());
		if (poProcessCodeInfo != null) {
			pOrderHeader.setProcdName(poProcessCodeInfo.getSapProcessCode());
		}
		SysCompany sysCompany = sysCompanyRepository.findByCompanyNo(poMaster.getCompanyNo());
		if (sysCompany != null) {
			pOrderHeader.setSalesOrg(sysCompany.getSapSaleOrgNo());// 销售组织
		} else {
			pOrderHeader.setSalesOrg("S001");// 销售组织
		}
		pOrderHeader.setDivision("D1");// 产品组
		if (TypeSapProcessCode.G306.equals(poMaster.getPoProcessCode())) {
			pOrderHeader.setSalesOff(poMaster.getOrderDealerBranchNo());// 购货人对应的分公司
		} else {
			pOrderHeader.setSalesOff(poMaster.getPoBranchNo());// 销售部门
		}
		pOrderHeader.setSalesDist(poMaster.getPoRegionNo());// 销售区域
		pOrderHeader.setKdgrp("");// 客户组
		pOrderHeader.setKvgr1("");// 客户组1
		pOrderHeader.setKvgr2("");// 客户组2
		pOrderHeader.setKvgr3("");// 客户组3
		pOrderHeader.setKvgr4("");// 客户组4
		pOrderHeader.setKvgr5("");// 客户组5
		pOrderHeader.setShipType(poAddrDetail.getDeliveryType());// 配送方式
		pOrderHeader.setPurchNoC(poMaster.getPoNo());// 申请单号
		pOrderHeader.setpPlant(poAddrDetail.getDeliveryWhCode());// 出货工厂
		if (TypeSapProcessCode.G013.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G014.equals(poMaster.getPoProcessCode())) {
			pOrderHeader.setStoreLoc("");// 库存地点
		} else {
			pOrderHeader.setStoreLoc(poAddrDetail.getDeliveryWhLocCode());// 库存地点
		}

		pOrderHeader.setBeiZhu("");// 备注
		pOrderHeader.setOrdReason("");// 订单原因
		pOrderHeader.setCollectNo(poMaster.getPoGroupNo());// 团单号
		pOrderHeader.setSoDate(poMaster.getPoDate());// 订单日期
		pOrderHeader.setOldOrder(this.getBranchType(poMaster.getPoBranchNo()) ? "1" : "2");// 非试点的订单标志,试点1，非试点2
		pOrderHeader.setPoState(poMaster.getPoStatus());//订单状态(新增)
		// 售达方
		pOrderHeader.setName(poMaster.getOrderDealerName());// 姓名
		pOrderHeader.setName2(poMaster.getOrderDealerNo());// 卡号
		pOrderHeader.setHomeCity(dealer.getSaleZoneNo());// 片区
		// 送达方信息
		pOrderHeader.setKunn1Name(poAddrDetail.getAddrSendId());// 地址id
		pOrderHeader.setKunn1Name2(poAddrDetail.getAddrAreaCode());// 行政区id
		pOrderHeader.setFreightAmount(poMaster.getTotalTransportAmt().toString());// 运费总值
		pOrderHeader.setKunnrWe(poAddrDetail.getDeliveryDealerNo()); // 提货点
		pOrderHeader.setKunn1Region(this.getSapRegion(poAddrDetail.getAddrAreaCode(), poAddrDetail.getAddrProvince()));// 地区
		pOrderHeader.setKunn1City(poAddrDetail.getAddrCity());// 城市
		pOrderHeader.setKunn1Distric(poAddrDetail.getAddrCounty());// 区域
		// 预订单的主单标志
		// 1、 试点地区，不管那2个字段Kunn1HomeCity、Kunn1HouseNum1
		// 2、 非试点地区，po_addr_detail 里的ADDR_AREA_CODE， 再去找PO_STORE_ADDRESS
		// 看IS_DEFAULT ==1 ，设X，否则 空
		pOrderHeader.setKunn1HomeCity("");
		// 3、po_addr_detail 的DELIVERY_ATTR ==0 主单，1 辅单
		// 00 辅单,01 主单,02 平台录入专卖店订单
		pOrderHeader.setKunn1HouseNum1("");
		if (!this.getBranchType(poMaster.getPoBranchNo())) {
			if (poAddrDetail.getAddrSendId() != null && !"".equals(poAddrDetail.getAddrSendId())) {
				PoStoreAddress poStoreAddress = dsPoStoreAddressRepository.findByStoreNoAndAddrSendId(poAddrDetail.getDeliveryDealerNo(),
						poAddrDetail.getAddrSendId());
				if (poStoreAddress != null && poStoreAddress.isDefaultt()) {
					pOrderHeader.setKunn1HomeCity("X");
				}
			}
			// 总公司预定／记欠单
			if (this.getSapType(poMaster.getPoProcessCode()) == TypeSapOrderType.ZYD1) {
				if (poAddrDetail.getDeliveryAttr().equals("0")) {
					pOrderHeader.setKunn1HouseNum1("01");
				} else if (poAddrDetail.getDeliveryAttr().equals("1")) {
					pOrderHeader.setKunn1HouseNum1("00");
				}
			}
		}

		pOrderHeader.setKunn1PostCode1("");// 邮政编码
		pOrderHeader.setKunn1Street(poAddrDetail.getAddrDistrict());// 详细地址1
		pOrderHeader.setKunn1StrSuppl1(poAddrDetail.getAddrDetail());// 详细地址2
		pOrderHeader.setKunn1StrSuppl2("");// 详细地址3
		pOrderHeader.setKunn1StrSuppl3("");// 详细地址4
		pOrderHeader.setKunn1Location("");// 详细地址5
		// 联系人ap
		pOrderHeader.setPartnerName1(poAddrDetail.getR01FullName());// 姓名1
		pOrderHeader.setPartnerName3(poAddrDetail.getR01CertificateNo());// 身份证1
		pOrderHeader.setPartnerName2(poAddrDetail.getR01Teles());// 电话号码1
		pOrderHeader.setPartnerStrSuppl1(poAddrDetail.getR02FullName());// 姓名2
		pOrderHeader.setPartnerStrSuppl3(poAddrDetail.getR02CertificateNo());// 身份证2
		pOrderHeader.setPartnerStreet(poAddrDetail.getR02Teles());// 电话号码2
		pOrderHeader.setPartnerLocation(poAddrDetail.getR03FullName());// 姓名3
		pOrderHeader.setPartnerHomeCity(poAddrDetail.getR03CertificateNo());// 身份证3
		pOrderHeader.setPartnerCity2(poAddrDetail.getR03Teles());// 电话号码3
		pOrderHeader.setPurchDate(poMaster.getPoDate());// 申请单日期
		pOrderHeader.setPriceDate("");// //价格日期
		pOrderHeader.setField1("");// 备用字段1
		pOrderHeader.setField2("");// 备用字段2
		pOrderHeader.setField3("");// 备用字段3
		pOrderHeader.setField4("");// 备用字段4
		pOrderHeader.setField5("");// 备用字段5
		pOrderHeader.setKonda(poMaster.getPoPriceAttr());// 价格组
		// 1、售达方：根据购货人卡号是否试点地区，试点：888888888，非试点：卡号；
		// 2、送达方：根据配送方式的提货点卡号，若05-专卖店配送：专卖店卡号是否试点地区，试点：888888888，非试点：专卖店卡号；
		// 10-自提(自营店提货)：暂填888888888 12-家居配送：根据购货人卡号是否试点地区，试点：888888888，非试点：卡号；
		// 3、付款方卡号：暂填888888888； 2011.11.03 修改
		if (poMaster.getOrderDealerNo() != null && !"".equals(poMaster.getOrderDealerNo())) {
			Dealer orderDealer = dsDealerRepository.findByDealerNo(poMaster.getOrderDealerNo());
			if (orderDealer != null) {
				//GBSSYW-34 系统中已经不存在试点和非试点的区别
				//pOrderHeader.setPartnNumb(this.getBranchType(orderDealer.getSaleBranchNo()) ? "888888888" : orderDealer
				//		.getDealerNo());// 售达方ID
				//modify by zhuohr G012纷享荟订单的ID为666666666
				if (TypeSapProcessCode.G012.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G017.equals(poMaster.getPoProcessCode())) {
					pOrderHeader.setPartnNumb("666666666");// 售达方ID
				} else {
					pOrderHeader.setPartnNumb("888888888");// 售达方ID
				}
				// 家居配送
				if (this.getSapDeliverType(poAddrDetail.getDeliveryType()).equals(TypeDeliveryType.JP)) {
					//pOrderHeader.setPartnKunn1(this.getBranchType(orderDealer.getSaleBranchNo()) ? "888888888"
					//		: orderDealer.getDealerNo());// 送达方
					//modify by zhuohr G012纷享荟订单的ID为666666666
					if (TypeSapProcessCode.G012.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G017.equals(poMaster.getPoProcessCode())) {
						pOrderHeader.setPartnKunn1("666666666");// 送达方
					} else {
						pOrderHeader.setPartnKunn1("888888888");// 送达方
					}
				}
			}
		}
		// 如果是专卖店提货方式
		if (this.getSapDeliverType(poAddrDetail.getDeliveryType()).equals(TypeDeliveryType.SHOP)) {
			Dealer shopDealer = dsDealerRepository.findByDealerNo(poAddrDetail.getDeliveryDealerNo());
			if (shopDealer != null) {
				//pOrderHeader.setPartnKunn1(this.getBranchType(shopDealer.getSaleBranchNo()) ? "888888888" : shopDealer
				//		.getDealerNo());// 送达方
				//modify by zhuohr G012纷享荟订单的ID为666666666
				if (TypeSapProcessCode.G012.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G017.equals(poMaster.getPoProcessCode())) {
					pOrderHeader.setPartnKunn1("666666666");// 送达方
				} else {
					pOrderHeader.setPartnKunn1("888888888");// 送达方
				}
			}
		}
		//modify by zhuohr G012纷享荟订单的ID为666666666
		if (TypeSapProcessCode.G012.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G017.equals(poMaster.getPoProcessCode())) {
			pOrderHeader.setPayKunn("666666666");// 付款方卡号
		} else {
			pOrderHeader.setPayKunn("888888888");// 付款方卡号
		}

		// sap触发发货新增字段

		// 是否需要触发发货  Y:需要   N:不需要   默认都是Y  延保单以及所有的退单都不需要触发发货
		pOrderHeader.setDeliveryFlag("Y");

		// P单创建时间  HHmmss
		if (poMaster.getPoEntryTime() != null) {
			pOrderHeader.setPoEntryTime(DateFormatUtil.format(poMaster.getPoEntryTime(), "HHmmss"));
		} else {
			pOrderHeader.setPoEntryTime("");
		}

		// 计划发货日期  yyyyMMdd
		if (poAddrDetail.getDeliveryPlanDate() != null) {
			pOrderHeader.setDeliveryDate(DateFormatUtil.format(poAddrDetail.getDeliveryPlanDate(), "yyyyMMdd"));
		} else {
			pOrderHeader.setDeliveryDate("");
		}

		// 易售通业务伙伴卡号
		pOrderHeader.setCardYst("");

		// 易售通业务伙伴姓名
		pOrderHeader.setNameYst("");

		// 收货人1证件类型
		pOrderHeader.setPartnerType1(poAddrDetail.getR01CertificateType());

		// 收货人2证件类型
		pOrderHeader.setPartnerType2(poAddrDetail.getR03CertificateType());

		// 收货人3证件类型
		pOrderHeader.setPartnerType3(poAddrDetail.getR03CertificateType());

		// 打包类型
		pOrderHeader.setZpa1(poAddrDetail.getDeliveryPackageType());
		// 辅单才需要记录主单号
		if (poMaster.getPoProcessCode().startsWith("G9")) {
			pOrderHeader.setMainNumber(poMaster.getRefSelectedNo());
		} else {
			pOrderHeader.setMainNumber("");
		}
		pOrderHeader.setOrderType(poMaster.getOrderType());
		// 预留字段6
		pOrderHeader.setField6("");
	}

	/**
	 * 总公司普通单 传入订单主表
	 *
	 * @param poMaster
	 * @param pOrderHeader
	 */
	private void companyCommonBill(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail) {

		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		pOrderHeader.setPosNumber("");// 订单单号

		// PoPriceAttr 定价属性
		// P--优惠价"//Y000000002(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			if ("G103".equals(poMaster.getPoProcessCode())) {
				pOrderHeader.setKonda("03");// 企业购 优惠价
			} else {
				pOrderHeader.setKonda("08");// 价格组
			}

		}
		// "R--零售价 L000000002(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			if ("G103".equals(poMaster.getPoProcessCode())) {
				pOrderHeader.setKonda("06");// 企业购 优惠价
			} else {
				pOrderHeader.setKonda("07");// 价格组
			}
		}

		if ("G013".equals(poMaster.getPoProcessCode())) {
			pOrderHeader.setDeliveryFlag("N");
		}

		// 是否直达易售单
		if (poMaster.getPoProcessCode().equals("G108")) {
			pOrderHeader.setCardYst(poAddrDetail.getDeliveryDealerNo());
			Dealer ystDealer = dsDealerRepository.findByDealerNo(poAddrDetail.getDeliveryDealerNo());
			if (ystDealer != null) {
				pOrderHeader.setNameYst(ystDealer.getFullName());
			}
		}

		// 普消购货
		if (StringUtils.isNotBlank(poMaster.getOrderCustomerNo())) {
			DealerCustomer dealerCustomer = dealerCustomerRepository.findByCustomerNo(poMaster.getOrderCustomerNo());
			if (dealerCustomer != null) {
				pOrderHeader.setCardYst(dealerCustomer.getMobile());
				if (StringUtils.isNotBlank(dealerCustomer.getFullName())) {
					pOrderHeader.setNameYst(dealerCustomer.getFullName());
				}
			}
		}
	}

	/**
	 * g904质量补损单 传入订单主表
	 *
	 * @param poMaster
	 * @param pOrderHeader
	 */
	private void qualityLossCompany(POrderHeader pOrderHeader, PoMaster poMaster) {

		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组ß
		pOrderHeader.setPosNumber("");// 订单单号
		pOrderHeader.setKonda("");// 价格组

	}

	private void lclReturnBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDeliveryFlag("N");
	}

	/**
	 * 增值税发票
	 * @param header
	 * @param info
	 */
	private void setInvoiceBillVat(POrderHeader header, PoInvoiceInfo info) {
		if (info == null)
			return;

		String invoiceType = info.getInvoiceType();
		if ("PP".equals(invoiceType)) {
			// 增值税专用发票
			if (info.getInvoiceTitle() != null)
				header.setField1(info.getInvoiceTitle());
			if (info.getContactTele() != null)
				header.setField2(info.getContactTele());
			if (info.getBankName() != null)
				header.setField3(info.getBankName());
			if (info.getTaxPayerCode() != null)
				header.setField4(info.getTaxPayerCode());
			if (info.getBankAccount() != null)
				header.setField5(info.getBankAccount());
			if (info.getInvoiceAddrTail() != null)
				header.setField6(info.getInvoiceAddrTail());
		} else {
			// 电子发票或者普通纸质发票
			setInvoiceBill(header, info);
		}
	}

	/**
	 * 电子发票或者普通纸质发票
	 */
	private void setInvoiceBill(POrderHeader header, PoInvoiceInfo info) {
		if (info == null)
			return;

		if (info.getInvoiceTitle() != null)
			header.setField1(info.getInvoiceTitle());
		if (info.getInvoiceEmail() != null)
			header.setField2(info.getInvoiceEmail());
		if (info.getInvoiceMobile() != null)
			header.setField3(info.getInvoiceMobile());
		if (info.getTaxPayerCode() != null)
			header.setField4(info.getTaxPayerCode());
	}

	/**
	 * 总公司预定／记欠单 传入订单主表
	 *
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void companyBookingBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		pOrderHeader.setPosNumber("");// 订单单号

		// P--优惠价"//Y000000002(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("08");// 价格组
		}
		// "R--零售价 L000000002(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("07");// 价格组
		}

		// 普消购货
		if (StringUtils.isNotBlank(poMaster.getOrderCustomerNo())) {
			DealerCustomer dealerCustomer = dealerCustomerRepository.findByCustomerNo(poMaster.getOrderCustomerNo());
			if (dealerCustomer != null) {
				pOrderHeader.setCardYst(dealerCustomer.getMobile());
				if (StringUtils.isNotBlank(dealerCustomer.getFullName())) {
					pOrderHeader.setNameYst(dealerCustomer.getFullName());
				}
			}
		}
	}

	/**
	 * 总公司免费单 传入订单主表
	 *
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void companyFreeBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		pOrderHeader.setPosNumber("");// 订单单号

		// P--优惠价" Y000000002(业务员
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("08");// 价格组
		}
		// "R--零售价 L000000002(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("07");// 价格组
		}
		// TODO还有几种类型没搞：售达方
	}

	/**
	 * 服务中心普通单 传入订单主表
	 *
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void serviceCenterCommonBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C2");// 分销渠道
		pOrderHeader.setSalesGrp(poMaster.getPoStoreNo());// 销售组
		pOrderHeader.setPosNumber(this.createSapPostingDocNo(poMaster));// 订单单号
		pOrderHeader.setOrdReason(this.getPaymentType(poMaster.getPoNo(), TypeSapOrderType.ZNF4));// 支付类型
		if ((TypeSapProcessCode.G301.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G302.equals(poMaster.getPoProcessCode()))
				&& "S".equals(poMaster.getPoEntryClass())) {
			// e购货订单
			pOrderHeader.setOrdReason("024");
		}

		// P--优惠价" Y000000003(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("02");// 价格组
		}
		// "R--零售价 L000000003(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("01");// 价格组
		}
		// 自提(自营店提货) 服务中心提货
		if (poMaster.getOrderDealerNo().equals("000000000")) {// 零售的
			pOrderHeader.setPartnNumb("L000000001");// 售达方
			pOrderHeader.setPartnKunn1("L000000001");// 送达方
			pOrderHeader.setPayKunn("L000000001");// 付款方卡号
			pOrderHeader.setName(pOrderHeader.getPosNumber());//如果是零售，姓名用SAP单号
		} else {
			pOrderHeader.setPartnNumb("Y000000001");// 售达方
			pOrderHeader.setPartnKunn1("Y000000001");// 送达方
			pOrderHeader.setPayKunn("Y000000001");// 付款方卡号
		}
		pOrderHeader.setHomeCity("");// 身份证3
		// 送达方
		pOrderHeader.setKunn1Name("");//
		pOrderHeader.setKunn1Name2("");//
		pOrderHeader.setFreightAmount("");//
		pOrderHeader.setKunn1Region("");//
		pOrderHeader.setKunn1City("");//
		pOrderHeader.setKunn1Distric("");//
		pOrderHeader.setKunn1HomeCity("");//
		pOrderHeader.setKunn1PostCode1("");//
		pOrderHeader.setKunn1Street("");//
		pOrderHeader.setKunn1StrSuppl1("");//
		pOrderHeader.setKunn1StrSuppl2("");//
		pOrderHeader.setKunn1StrSuppl3("");//
		pOrderHeader.setKunn1Location("");//
		// 联系人ap
		pOrderHeader.setPartnerName1("");
		pOrderHeader.setPartnerName3("");
		pOrderHeader.setPartnerName2("");
		pOrderHeader.setPartnerStrSuppl1("");
		pOrderHeader.setPartnerStrSuppl3("");
		pOrderHeader.setPartnerStreet("");
		pOrderHeader.setPartnerLocation("");
		pOrderHeader.setPartnerHomeCity("");
		pOrderHeader.setPartnerCity2("");
		// TODO还有几种类型没搞：售达方
	}

	/**
	 * 服务中心免费单 传入订单主表
	 *
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void serviceCenterFreeBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C2");
		pOrderHeader.setSalesGrp(poMaster.getPoStoreNo());
		pOrderHeader.setPosNumber(this.createSapPostingDocNo(poMaster));
		pOrderHeader.setOrdReason(this.getPaymentType(poMaster.getPoNo(), TypeSapOrderType.ZGFR));// 支付类型
		// P--优惠价"
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("02");
		}
		// "R--零售价
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("01");
		}
		if (poMaster.getOrderDealerNo().equals("000000000")) {// 零售的
			pOrderHeader.setPartnNumb("L000000001");// 售达方
			pOrderHeader.setPartnKunn1("L000000001");// 送达方
			pOrderHeader.setPayKunn("L000000001");// 付款方卡号
			pOrderHeader.setName(pOrderHeader.getPosNumber());//如果是零售，姓名用SAP单号
		} else {
			pOrderHeader.setPartnNumb("Y000000001");// 售达方
			pOrderHeader.setPartnKunn1("Y000000001");// 送达方
			pOrderHeader.setPayKunn("Y000000001");// 付款方卡号
		}
		pOrderHeader.setHomeCity("");
		// 送达方
		pOrderHeader.setKunn1Name("");
		pOrderHeader.setKunn1Name2("");
		pOrderHeader.setFreightAmount("");
		pOrderHeader.setKunn1Region("");
		pOrderHeader.setKunn1City("");
		pOrderHeader.setKunn1Distric("");
		pOrderHeader.setKunn1HomeCity("");
		pOrderHeader.setKunn1PostCode1("");
		pOrderHeader.setKunn1Street("");
		pOrderHeader.setKunn1StrSuppl1("");
		pOrderHeader.setKunn1StrSuppl2("");
		pOrderHeader.setKunn1StrSuppl3("");
		pOrderHeader.setKunn1Location("");
		// 联系人ap
		pOrderHeader.setPartnerName1("");
		pOrderHeader.setPartnerName3("");
		pOrderHeader.setPartnerName2("");
		pOrderHeader.setPartnerStrSuppl1("");
		pOrderHeader.setPartnerStrSuppl3("");
		pOrderHeader.setPartnerStreet("");
		pOrderHeader.setPartnerLocation("");
		pOrderHeader.setPartnerHomeCity("");
		pOrderHeader.setPartnerCity2("");
		// TODO还有几种类型没搞：售达方
	}

	/**
	 * 服务中心退货单 G305 本店退货 传入订单主表
	 *
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void serviceCenterReturnBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C2");// 分销渠道
		pOrderHeader.setSalesGrp(poMaster.getPoStoreNo());// 销售组
		if (poMaster.getRefPoNo() != null && !"".equals(poMaster.getRefPoNo())) {
			PoMaster refPoMaster = dsPoMasterRepository.findByPoNo(poMaster.getRefPoNo());
			pOrderHeader.setPosNumber(refPoMaster.getSapPostingDocNo());// 订单单号
		}
		pOrderHeader.setOrdReason(this.getPaymentType(poMaster.getPoNo(), TypeSapOrderType.ZRE2));// 支付类型

		// P--优惠价" Y000000003(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("02");// 价格组
		}
		// "R--零售价 L000000003(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("01");// 价格组
		}
		if (poMaster.getOrderDealerNo().equals("000000000")) {// 零售的
			pOrderHeader.setPartnNumb("L000000001");// 售达方
			pOrderHeader.setPartnKunn1("L000000001");// 送达方
			pOrderHeader.setPayKunn("L000000001");// 付款方卡号
			pOrderHeader.setName(pOrderHeader.getPosNumber());//如果是零售，姓名用SAP单号
		} else {
			pOrderHeader.setPartnNumb("Y000000001");// 售达方
			pOrderHeader.setPartnKunn1("Y000000001");// 送达方
			pOrderHeader.setPayKunn("Y000000001");// 付款方卡号
		}
		pOrderHeader.setHomeCity("");// 身份证3
		// 送达方
		pOrderHeader.setKunn1Name("");//
		pOrderHeader.setKunn1Name2("");//
		pOrderHeader.setFreightAmount("");//
		pOrderHeader.setKunn1Region("");//
		pOrderHeader.setKunn1City("");//
		pOrderHeader.setKunn1Distric("");//
		pOrderHeader.setKunn1HomeCity("");//
		pOrderHeader.setKunn1PostCode1("");//
		pOrderHeader.setKunn1Street("");//
		pOrderHeader.setKunn1StrSuppl1("");//
		pOrderHeader.setKunn1StrSuppl2("");//
		pOrderHeader.setKunn1StrSuppl3("");//
		pOrderHeader.setKunn1Location("");//
		// 联系人ap
		pOrderHeader.setPartnerName1("");
		pOrderHeader.setPartnerName3("");
		pOrderHeader.setPartnerName2("");
		pOrderHeader.setPartnerStrSuppl1("");
		pOrderHeader.setPartnerStrSuppl3("");
		pOrderHeader.setPartnerStreet("");
		pOrderHeader.setPartnerLocation("");
		pOrderHeader.setPartnerHomeCity("");
		pOrderHeader.setPartnerCity2("");

		// add by yx 2013-01-10
		pOrderHeader.setOldOrder("");
		// TODO还有几种类型没搞：售达方

		pOrderHeader.setDeliveryFlag("N");
	}

	/**
	 * 网上订单 服务中心退货 G306、G308单
	 * @param pOrderHeader
	 * @param poMaster
	 */
	public void serviceCenterReturnGbss(POrderHeader pOrderHeader, PoMaster poMaster) {
		String poProcessCode = poMaster.getPoProcessCode();
		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		if ("G308".equals(poProcessCode)) {
			pOrderHeader.setPosNumber("");
		} else {
			if (poMaster.getRefPoNo() != null && !"".equals(poMaster.getRefPoNo())) {
				String companyNo = poMaster.getCompanyNo();//退货单公司编号
				PoMaster refPoMaster = dsPoMasterRepository.findByPoNo(poMaster.getRefPoNo());
				String refCompanyNo = refPoMaster.getCompanyNo();//参考单公司编号
				String sapPostingDocNo = refPoMaster.getSapPostingDocNo();
				if (!companyNo.equals(refCompanyNo)) {
					PoMaster subPoMaster = dsPoMasterRepository.findByRefPoNoAndPoProcessCodeAndCompanyNo(refPoMaster.getPoNo(),
							refPoMaster.getPoProcessCode(), companyNo);
					sapPostingDocNo = subPoMaster.getSapPostingDocNo();
				}
				pOrderHeader.setPosNumber(sapPostingDocNo);// 订单单号
			}
		}

		// change by yx 2012-12-10 start
		//		pOrderHeader.setOrdReason("");// 支付类型
		if ("G306".equals(poProcessCode) || "G105".equals(poProcessCode) || "G014".equals(poProcessCode)) {
			pOrderHeader.setOrdReason("016");// 支付类型  016--非本自营店销售退货(有参考)
		} else if ("G308".equals(poProcessCode)) {
			pOrderHeader.setOrdReason("017");// 支付类型 017--非本自营店销售退货(无参考)
		} else {
			pOrderHeader.setOrdReason("");// 支付类型
		}
		// change by yx  end 
		// P--优惠价" Y000000003(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("08");// 价格组
		}
		// "R--零售价 L000000003(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("07");// 价格组
		}

		pOrderHeader.setPartnNumb("888888888"); // 售达方
		pOrderHeader.setPartnKunn1("888888888");// 送达方
		pOrderHeader.setPayKunn("888888888");// 付款方卡号

		pOrderHeader.setHomeCity("");// 身份证3
		// 送达方
		pOrderHeader.setKunn1Name("");//
		pOrderHeader.setKunn1Name2("");//
		pOrderHeader.setFreightAmount("");//
		pOrderHeader.setKunn1Region("");//
		pOrderHeader.setKunn1City("");//
		pOrderHeader.setKunn1Distric("");//
		pOrderHeader.setKunn1HomeCity("");//
		pOrderHeader.setKunn1PostCode1("");//
		pOrderHeader.setKunn1Street("");//
		pOrderHeader.setKunn1StrSuppl1("");//
		pOrderHeader.setKunn1StrSuppl2("");//
		pOrderHeader.setKunn1StrSuppl3("");//
		pOrderHeader.setKunn1Location("");//
		// 联系人ap
		pOrderHeader.setPartnerName1("");
		pOrderHeader.setPartnerName3("");
		pOrderHeader.setPartnerName2("");
		pOrderHeader.setPartnerStrSuppl1("");
		pOrderHeader.setPartnerStrSuppl3("");
		pOrderHeader.setPartnerStreet("");
		pOrderHeader.setPartnerLocation("");
		pOrderHeader.setPartnerHomeCity("");
		pOrderHeader.setPartnerCity2("");

		// add by yx 2013-01-10
		pOrderHeader.setOldOrder("");
		// TODO还有几种类型没搞：售达方

		pOrderHeader.setDeliveryFlag("N");
	}

	private void storeReturnCompany(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail) {
		// TODO Auto-generated method stub
		String poProcessCode = poMaster.getPoProcessCode();
		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		pOrderHeader.setPosNumber("");
		pOrderHeader.setOrdReason("009");// 支付类型

		// change by yx  end 
		// P--优惠价" Y000000003(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("08");// 价格组
		}
		// "R--零售价 L000000003(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("07");// 价格组
		}

		pOrderHeader.setPartnNumb("888888888"); // 售达方
		pOrderHeader.setPartnKunn1("888888888");// 送达方
		pOrderHeader.setPayKunn("888888888");// 付款方卡号

		pOrderHeader.setHomeCity("");// 身份证3
		// 送达方
		pOrderHeader.setKunn1Name("");//
		pOrderHeader.setKunn1Name2("");//
		pOrderHeader.setFreightAmount("");//
		pOrderHeader.setKunn1Region("");//
		pOrderHeader.setKunn1City("");//
		pOrderHeader.setKunn1Distric("");//
		pOrderHeader.setKunn1HomeCity("");//
		pOrderHeader.setKunn1PostCode1("");//
		pOrderHeader.setKunn1Street("");//
		pOrderHeader.setKunn1StrSuppl1("");//
		pOrderHeader.setKunn1StrSuppl2("");//
		pOrderHeader.setKunn1StrSuppl3("");//
		pOrderHeader.setKunn1Location("");//
		// 联系人ap
		pOrderHeader.setPartnerName1("");
		pOrderHeader.setPartnerName3("");
		pOrderHeader.setPartnerName2("");
		pOrderHeader.setPartnerStrSuppl1("");
		pOrderHeader.setPartnerStrSuppl3("");
		pOrderHeader.setPartnerStreet("");
		pOrderHeader.setPartnerLocation("");
		pOrderHeader.setPartnerHomeCity("");
		pOrderHeader.setPartnerCity2("");

		// add by yx 2013-01-10
		pOrderHeader.setOldOrder("");
		// TODO还有几种类型没搞：售达方

		pOrderHeader.setDeliveryFlag("N");

		//坑爹，报文里居用的是beizhu字段 add by kevin.wang 
		String dealerName = poMaster.getOrderDealerName();
		String addrDetail = poAddrDetail.getAddrProvince() + poAddrDetail.getAddrCity() + poAddrDetail.getAddrCounty() + poAddrDetail.getAddrDistrict()
				+ poAddrDetail.getAddrDetail();
		pOrderHeader.setBeiZhu(poAddrDetail.getAddrAreaCode() + "^" + dealerName + "^" + addrDetail);
	}

	/**
	 * @param poDetailList
	 */
	private void getParentLineNum(List<PoDetail> poDetailList, Map<String, BigDecimal> parentMap) {
		for (PoDetail poDetail : poDetailList) {
			parentMap.put(poDetail.getProductCode(), poDetail.getLineNo());
		}
	}

	private int getSapType(String typeSapProcessCode) {
		if (typeSapProcessCode.equals(TypeSapProcessCode.G001) || typeSapProcessCode.equals(TypeSapProcessCode.G002)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G003) || typeSapProcessCode.equals(TypeSapProcessCode.G004)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G006) || typeSapProcessCode.equals(TypeSapProcessCode.G008)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G101) || typeSapProcessCode.equals(TypeSapProcessCode.G108)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G103) || typeSapProcessCode.equals(TypeSapProcessCode.G402)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G407) || typeSapProcessCode.equals(TypeSapProcessCode.G403)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G010) || typeSapProcessCode.equals(TypeSapProcessCode.G017)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G013) || typeSapProcessCode.equals(TypeSapProcessCode.G020)) {
			return TypeSapOrderType.ZNF3;//总公司普通单
		}
		if (typeSapProcessCode.equals(TypeSapProcessCode.G005) || typeSapProcessCode.equals(TypeSapProcessCode.G007)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G102) || typeSapProcessCode.equals(TypeSapProcessCode.G104)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G404) || typeSapProcessCode.equals(TypeSapProcessCode.G410)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G022) || typeSapProcessCode.equals(TypeSapProcessCode.G023)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G107)) {
			return TypeSapOrderType.ZYD1;//总公司预定／记欠单
		}
		if (typeSapProcessCode.equals(TypeSapProcessCode.G018) || typeSapProcessCode.equals(TypeSapProcessCode.G019)) {
			return TypeSapOrderType.ZNF8;//全球购订单
		}
		// 2011-11-15 update by yx 将月刊当从总公司免费单中分离出来单独处理 start
		//		if (typeSapProcessCode.equals(TypeSapProcessCode.G901) || typeSapProcessCode.equals(TypeSapProcessCode.G909)
		//				|| typeSapProcessCode.equals(TypeSapProcessCode.G910)) {
		//			return TypeSapOrderType.ZFRE;//总公司免费单
		//		}
		if (typeSapProcessCode.equals(TypeSapProcessCode.G901) || typeSapProcessCode.equals(TypeSapProcessCode.G910)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G011)) {
			return TypeSapOrderType.ZFRE;//总公司免费单
		}
		if (typeSapProcessCode.equals(TypeSapProcessCode.G909)) {
			return TypeSapOrderType.ZNF6;//总公司  月刊单
		}
		// 2011-11-15 update by yx 将月刊当从总公司免费单中分离出来单独处理 end
		if (typeSapProcessCode.equals(TypeSapProcessCode.G301) || typeSapProcessCode.equals(TypeSapProcessCode.G302)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G303) || typeSapProcessCode.equals(TypeSapProcessCode.G304)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G310)) {
			return TypeSapOrderType.ZNF4;//服务中心普通单
		}
		if (typeSapProcessCode.equals(TypeSapProcessCode.G902) || typeSapProcessCode.equals(TypeSapProcessCode.G311)) {
			return TypeSapOrderType.ZGFR;//服务中心免费单
		}
		if (typeSapProcessCode.equals(TypeSapProcessCode.G305)) {
			return TypeSapOrderType.ZRE2;// 服务中心退货单
		}

		// add by yx 2012-11-29 增加 G306退货单类型 start
		if (typeSapProcessCode.equals(TypeSapProcessCode.G306) || typeSapProcessCode.equals(TypeSapProcessCode.G105)
				|| typeSapProcessCode.equals(TypeSapProcessCode.G014)) {
			return TypeSapOrderType.ZRE7;
		}
		// add by yx end

		if (typeSapProcessCode.equals(TypeSapProcessCode.G308)) {
			return TypeSapOrderType.ZRE8;
		}

		if (typeSapProcessCode.equals(TypeSapProcessCode.G501)) {
			return TypeSapOrderType.ZREA;
		}
		//add by zhuohr
		if (typeSapProcessCode.equals(TypeSapProcessCode.G012)) {
			return TypeSapOrderType.ZRE1;
		}

		if (typeSapProcessCode.equals(TypeSapProcessCode.G904)) {
			return TypeSapOrderType.ZFD2;
		}

		if (typeSapProcessCode.equals(TypeSapProcessCode.G905)) {
			return TypeSapOrderType.ZRE4;
		}

		if (typeSapProcessCode.equals(TypeSapProcessCode.G906) || typeSapProcessCode.equals(TypeSapProcessCode.G908)) {
			return TypeSapOrderType.ZRE6;
		}
		return -1;
	}

	/**
	 * 获取sap的提货方式的名称，用作判断
	 *
	 * @param deliveryType
	 * @return
	 */
	private String getSapDeliverType(String deliveryType) {
		if (deliveryTypeMap.containsKey(deliveryType)) {
			return deliveryTypeMap.get(deliveryType);
		} else {
			deliveryTypeMap.clear();
			initDeliveryTypeMap();
			// return getSapDeliverType(deliveryType); // 这样写会死循环的
			if (deliveryTypeMap.containsKey(deliveryType)) {
				return deliveryTypeMap.get(deliveryType);
			}
		}
		return "";
	}

	private String getSapRegion(String addrAreaCode, String addrProvince) {
		// 先根据areaCode查找
		List<PoAreaInfo> poAreaInfos = dsPoAreaInfoRepository.listByAreaCode(addrAreaCode);
		if (CollectionUtils.isNotEmpty(poAreaInfos) && poAreaInfos.size() >= 2) {
			//剔除第一级“中国”，找第二级省份
			String addrProvinceCode = poAreaInfos.get(1).getAreaCode();
			SapDsMapping sapDsMapping = dsSapDsMappingRepository.findByDsCode(addrProvinceCode);
			if (sapDsMapping != null) {
				return sapDsMapping.getSapCode();
			}
		}
		SapDsMapping sapDsMapping = dsSapDsMappingRepository.findByDsDesc(addrProvince);
		if (sapDsMapping == null) {
			return "";
		}
		return sapDsMapping.getSapCode();
	}

	/**
	 * 处理结果
	 * @param plantResult
	 */
	private void dealResult(PlantResult plantResult, String poNo, Date postingDate) {
		if (plantResult != null) {
			if (plantResult.isSuccess()) {
				PoMaster poMaster = dsPoMasterRepository.findByPoNo(poNo);
				if (poMaster != null) {
					poMaster.setSapPostingFlag(TypeSapPostingFlag.PAY);
					poMaster.setSapPostingDate(postingDate);
					poMaster.setSapPostingDocNo(plantResult.getHeadValues().get("SALESDOCUMENT").toString());
					dsPoMasterRepository.save(poMaster);
				}
			}

		}
	}

	/**
	 * 错误了，改状态E
	 */
	private void savePoMasterWhenWrong(String poNo, String sapPostingDocNo) {
		PoMaster poMaster = dsPoMasterRepository.findByPoNo(poNo);
		if (poMaster != null) {
			poMaster.setSapPostingFlag(TypeSapPostingFlag.ERROR);
			poMaster.setSapPostingDocNo(sapPostingDocNo);
			dsPoMasterRepository.save(poMaster);
		}
	}

	//	/**
	//	 * 取售达方的值
	//	 *
	//	 * @param codeName
	//	 */
	//	private String findPartnNumb(String paraCode) {
	//		if (partnNumbMap.containsKey(paraCode)) {
	//			return partnNumbMap.get(paraCode);
	//		} else {
	//			SysParamInfo info = sysParamInfoRepository.findByParaScopeAndParaCode("PARTN_NUMB", paraCode);
	//			return info == null ? "" : info.getParaValue();
	//		}
	//	}

	/**
	 * 判断是否试点地区
	 *
	 * @param branchCode
	 * @return
	 */
	private boolean getBranchType(String branchCode) {
		if (branchCode == null || "".equals(branchCode)) {
			return false;
		}

		if (branchMap.containsKey(branchCode)) {
			return true;
		} else {
			branchMap.clear(); // 清空map
			initBranchMap(); // 重新初始化map
			return branchMap.containsKey(branchCode);
		}
	}

	/**
	 * 取同步次数的值
	 */
	private BigDecimal getSynErrCnt() {
		SysParamInfo info = sysParamInfoRepository.findByParaScopeAndParaCode("SYN_ERR_CNT", "SAP_DAILY_UPL_PO");
		String value = "3";
		if (info != null) {
			if (info.getParaValue() != null && !"".equals(info.getParaValue())) {
				value = info.getParaValue();
			} else if (info.getParaDefault() != null && !"".equals(info.getParaDefault())) {
				value = info.getParaDefault();
			}
		}
		return new BigDecimal(value);
	}

	/**
	 * 取同步次数的值
	 */
	private int getPosSynErrCnt() {
		SysParamInfo info = sysParamInfoRepository.findByParaScopeAndParaCode("SYN_ERR_CNT", "SAP_DAILY_UPL_PO_V2");
		String value = "3";
		if (info != null) {
			if (info.getParaValue() != null && !"".equals(info.getParaValue())) {
				value = info.getParaValue();
			} else if (info.getParaDefault() != null && !"".equals(info.getParaDefault())) {
				value = info.getParaDefault();
			}
		}
		return Integer.parseInt(value);
	}

	/**
	 * 服务中心生成sap订单号
	 */
	private String createSapPostingDocNo(PoMaster poMaster) {
		if (poMaster.getSapPostingDocNo() != null && !poMaster.getSapPostingDocNo().equals("")) {
			return poMaster.getSapPostingDocNo();
		} else {
			long num = dsBaseRepository.getSequenceNo("SEQ_PO_MASTER_EXTRA_DOC_NO");
			return "" + num;
		}
	}

	/**
	 * 服务中心订单号支付类型
	 */
	private String getPaymentType(String poNo, int type) {
		String reVlalue = "001";
		if (poNo != null && !poNo.equals("")) {
			List<PosAccTranDetail> list = posAccTranDetailRepository.queryByRefDocNoAndPaymentTypeCardCashChkRemit(poNo);
			if (list != null && list.size() > 0) {
				PosAccTranDetail obj = list.get(0);
				if (obj != null && obj.getPosPaymentType() != null) {
					if (TypeSapOrderType.ZNF4 == type || TypeSapOrderType.ZGFR == type) {
						if (obj.getPosPaymentType().equals("*CASH") || obj.getPosPaymentType().equals("*SLR")) {
							reVlalue = "001";
						} else if (obj.getPosPaymentType().equals("*CHK")) {
							reVlalue = "002";
						} else if (obj.getPosPaymentType().equals("*CARD")) {
							reVlalue = "003";
						}
					} else if (TypeSapOrderType.ZRE2 == type) {
						if (obj.getPosPaymentType().equals("*REMIT")) {
							reVlalue = "007";
						} else if (obj.getPosPaymentType().equals("*CASH") || obj.getPosPaymentType().equals("*SLR")) {
							reVlalue = "008";
						} else if (obj.getPosPaymentType().equals("*CARD")) {
							reVlalue = "015";
						} else if (obj.getPosPaymentType().equals("*EPOS")) {
							reVlalue = "024";
						}
					}
				}
			}
		}
		return reVlalue;
	}

	/**
	 * 是否服务中心销售订单
	 * @param poProcessCode
	 * @return
	 * 
	 * modify by czh
	 * date 2014-06-05
	 * G310 从S工厂改为V工厂
	 */
	@Deprecated //GBSSYW-1934
	public Boolean isPurchaseOrder(String poProcessCode) {
		if ("G301".equals(poProcessCode) || "G302".equals(poProcessCode) || "G303".equals(poProcessCode) || "G304".equals(poProcessCode)
				|| "G311".equals(poProcessCode) || "G310".equals(poProcessCode)) {
			return true;
		}
		return false;
	}

	/**
	 * 总公司普通单 纷享荟二期 传入订单主表
	 * add by zhuohr
	 * @param poMaster
	 * @param pOrderHeader
	 */
	private void cppCompanyCommonBill(POrderHeader pOrderHeader, PoMaster poMaster) {

		pOrderHeader.setDistrChan("C7");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		pOrderHeader.setPosNumber("");// 订单单号

		// PoPriceAttr 定价属性
		// P--优惠价"//Y000000002(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("13");// 价格组

		}
		// "R--零售价 L000000002(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("07");// 价格组
		}
	}

	/**
	 * 总公司普通单 纷享荟二期 传入订单明细
	 * add by zhuohr
	 * @param poDetail
	 * @param tOrderItems
	 */
	@Deprecated
	private void cppCompanyCommonTorderItems(PoDetail poDetail, TOrderItems tOrderItems, String paymentType) {
		//CPP积分：判断是否有积分价值
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {// 普通
			//纷享绘临时方案
			//modify by kevin  2016-05-06
			//纯CPP
			if (StringUtils.isNotEmpty(paymentType) && TypePoPaymentType.CPP.equals(paymentType)) {
				tOrderItems.setItemCateg("TANN");
				tOrderItems.setKwertPr00("0");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
				tOrderItems.setKwertPr00(poDetail.getSalePrice().toString());//基准价
			}
		}
	}

	/**
	 * GBSS-17453
	 * OLDGBSS-3764
	 * @param poDetail
	 * @param tOrderItems 
	 
	 */
	private void cppCompanyCommonTorderItems(PoDetail poDetail, MkpDlpPoRelate mkpDlpPoRelate, TOrderItems tOrderItems) {
		//CPP积分：判断是否有积分价值
		if (poDetail.getProductAttr().equals("1")) {// BOM产品
			if (poDetail.getSalePrice() == null || poDetail.getSalePrice().compareTo(new BigDecimal(0)) == 0) {// 价格0的免费项，非0标准项
				tOrderItems.setItemCateg("ZBM1");
			} else {
				tOrderItems.setItemCateg("ZBOM");
			}
		} else {
			// OLDGBSS-3764
			// 只有现金与折扣(即用积分了)都为0才是免费的
			//modify by Kevin.wang 2019.12.17
			if (mkpDlpPoRelate != null && (mkpDlpPoRelate.getCashAmt() == null || mkpDlpPoRelate.getCashAmt().compareTo(BigDecimal.ZERO) == 0)) {
				tOrderItems.setItemCateg("TANN");
				tOrderItems.setKwertPr00("0");
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
				tOrderItems.setKwertPr00(poDetail.getSalePrice().toString());//基准价
			}

		}
	}

	private String getPaymentType(MkpDlpPoRelate mkpDlpPoRelate) {
		if (mkpDlpPoRelate == null)
			return "";
		//CPP
		BigDecimal dlp = mkpDlpPoRelate.getTotalDlpAmt();
		if (dlp != null && dlp.compareTo(BigDecimal.ZERO) == 0) {
			return TypePoPaymentType.CPP;
		} else {
			BigDecimal cashAmt = mkpDlpPoRelate.getCashAmt();
			if (cashAmt != null && cashAmt.compareTo(BigDecimal.ZERO) > 0) {//金额＋积分
				return TypePoPaymentType.CPPCASH;
			} else {
				return TypePoPaymentType.NEWCPP;
			}
		}
	}

	private void setTConditionListWithZKBM(PoMaster poMaster, List<TCondition> tConditionList) {
		if (poMaster != null && poMaster.getTotalSaleDiscountAmt() != null && poMaster.getTotalSaleDiscountAmt().compareTo(BigDecimal.ZERO) == 1) {
			// 折扣:ZKBM
			TCondition tCondition = new TCondition();
			tCondition.setItmNumber("00");
			tCondition.setCondType("ZKBM");
			String value = SapFormatUtil.formatNumber(poMaster.getTotalSaleDiscountAmt());
			tCondition.setCondValue(value);
			tCondition.setCurrency("CNY");
			tConditionList.add(tCondition);
		}
	}

	//add by zhuohr添加微信折扣金额 20170516
	private void setTConditionListWithZKWX(PoMaster poMaster, List<TCondition> tConditionList) {
		BigDecimal tempTotalSaleDiscountAmt = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(tConditionList)) {
			for (TCondition tCondition : tConditionList) {
				if ("ZK62".equals(tCondition.getCondType()) || "ZK72".equals(tCondition.getCondType()) || "ZKWX".equals(tCondition.getCondType())) {
					tempTotalSaleDiscountAmt = tempTotalSaleDiscountAmt.add(new BigDecimal(tCondition.getCondValue()));
				}
			}
		}
		BigDecimal totalSaleDiscountAmt = poMaster.getTotalSaleDiscountAmt();
		totalSaleDiscountAmt = totalSaleDiscountAmt.subtract(tempTotalSaleDiscountAmt);

		if (totalSaleDiscountAmt.compareTo(BigDecimal.ZERO) != 0) {
			// 折扣:ZKWX
			TCondition tCondition = new TCondition();
			tCondition.setItmNumber("00");
			tCondition.setCondType("ZKWX");
			String value = SapFormatUtil.formatNumber(totalSaleDiscountAmt);
			tCondition.setCondValue(value);
			tCondition.setCurrency("CNY");
			tConditionList.add(tCondition);
		}
	}

	private void setInvoiceBill(POrderHeader header, DealerStoreExtra info, DealerStore ds) {
		if (ds != null && StringUtils.isNotEmpty(ds.getDealerFullName()))
			header.setField1(ds.getDealerFullName());
		if (info != null && StringUtils.isNotEmpty(info.getCtaxRegisterNo()))
			header.setField4(info.getNtaxRegisterNo());
	}

	/**
	 * 通过单号获取订货折扣明细集合
	 * @param poNo
	 * @return List<PoDetailDiscount>
	 */
	private List<PoDetailDiscount> getPoDetailDiscountListByPoNo(String poNo) {

		Assert.notNull(poNo, "poNo is not empty!");
		List<PoDetailDiscount> poDetailDiscountList = null;
		//明确设置为false才不去查，否则都去查数据
		SysParamInfo sysParamInfo = sysParamInfoRepository.findByParaCode("PO_BATCH_SAP_DISCOUNT_SWITCH");
		if (sysParamInfo != null) {
			String discountSwitch = sysParamInfo.getParaValue();
			if (StringUtils.isNotBlank(discountSwitch)) {
				discountSwitch = discountSwitch.toUpperCase();
				if (!"FALSE".equals(discountSwitch)) {
					poDetailDiscountList = poDetailDiscountRepository.findByPoNo(poNo);
				}
			}
		} else {
			poDetailDiscountList = poDetailDiscountRepository.findByPoNo(poNo);
		}
		return poDetailDiscountList;
	}
}

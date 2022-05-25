/**
 * SaveDealerOrderThreadHelpServiceImpl.java 2012-08-15
 */
package org.macula.cloud.po.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.macula.cloud.po.domain.PoAddrDetail;
import org.macula.cloud.po.domain.PoDetail;
import org.macula.cloud.po.domain.PoDetailDiscount;
import org.macula.cloud.po.domain.PoHeader;
import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.domain.PoPaymentDetail;
import org.macula.cloud.po.domain.PoProcessCodeInfo;
import org.macula.cloud.po.domain.PosAccTranDetail;
import org.macula.cloud.po.domain.SaleBranchInfo;
import org.macula.cloud.po.domain.SapDsMapping;
import org.macula.cloud.po.domain.SysCompany;
import org.macula.cloud.po.domain.SysParamInfo;
import org.macula.cloud.po.repository.MkpDlpPoRelateRepository;
import org.macula.cloud.po.repository.PoAddrDetailRepository;
import org.macula.cloud.po.repository.PoDetailDiscountRepository;
import org.macula.cloud.po.repository.PoDetailRepository;
import org.macula.cloud.po.repository.PoHeaderRepository;
import org.macula.cloud.po.repository.PoInvoiceInfoRepository;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.repository.PoPaymentDetailRepository;
import org.macula.cloud.po.repository.PoProcessCodeInfoRepository;
import org.macula.cloud.po.repository.PosAccTranDetailRepository;
import org.macula.cloud.po.repository.SaleBranchInfoRepository;
import org.macula.cloud.po.repository.SapDailyUplPoRepository;
import org.macula.cloud.po.repository.SapDsMappingRepository;
import org.macula.cloud.po.repository.SysCompanyRepository;
import org.macula.cloud.po.repository.SysParamInfoRepository;
import org.macula.cloud.po.sap.model.POrderHeader;
import org.macula.cloud.po.sap.model.TCondition;
import org.macula.cloud.po.sap.model.TOrderItems;
import org.macula.cloud.po.type.ProductTypeEnum;
import org.macula.cloud.po.type.TypeDeliveryType;
import org.macula.cloud.po.type.TypePoPaymentType;
import org.macula.cloud.po.type.TypeSapOrderType;
import org.macula.cloud.po.type.TypeSapProcessCode;
import org.macula.cloud.po.vo.DealerOrderVo;
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
public class SaveDealerOrderThreadHelpServiceImpl {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private PoMasterRepository dsPoMasterRepository;
	@Resource
	private PoDetailRepository dsPoDetailRepository;
	@Resource
	private SapDsMappingRepository dsSapDsMappingRepository;
	@Resource
	private PoAddrDetailRepository dsPoAddrDetailRepository;
	@Resource
	private PoPaymentDetailRepository dsPoPaymentDetailRepository;
	@Resource
	private SapDailyUplPoRepository dsSapDailyUplPoRepository;
	@Resource
	private SysParamInfoRepository sysParamInfoRepository;
	@Resource
	private SaleBranchInfoRepository dsSaleBranchInfoRepository;
	@Resource
	private PosAccTranDetailRepository posAccTranDetailRepository;
	@Resource
	private PoProcessCodeInfoRepository poProcessCodeInfoRepository;

	@Resource
	private MkpDlpPoRelateRepository dsMkpDlpPoRelateRepository;//add by zhuohr 

	@Resource
	private PoInvoiceInfoRepository poInvoiceInfoRepository;
	@Resource
	private SysCompanyRepository sysCompanyRepository;
	@Resource
	private PoDetailDiscountRepository poDetailDiscountRepository;
	@Resource
	private SapDsMappingRepository sapDsMappingRepository;
	@Resource
	private PoHeaderRepository poHeaderRepository;

	//以下三个Map的初始化放在initMap方法里 
	private Map<String, String> branchMap = new HashMap<String, String>();

	private Map<String, String> partnNumbMap = new HashMap<String, String>();

	@PostConstruct
	public void initMap() {
		initBranchMap();
		initPartnNumbMap();
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
	 * @param poNo 服务中心订单上传队列
	 *
	 * @return DealerOrderVo
	 * @throws Exception
	 */
	private DealerOrderVo prepareDataNotPos(String poNo) throws Exception {
		try {
			//			numFormat.setGroupingUsed(false);
			// 上传订单的传入参数
			DealerOrderVo dealerOrderVo = new DealerOrderVo();

			//将全局的parentMap改到这里初始化.然后在参数中传递赋值
			Map<String, BigDecimal> parentMap = new HashMap<String, BigDecimal>();

			// 传入订单主表
			POrderHeader pOrderHeader = new POrderHeader();

			PoMaster poMaster = dsPoMasterRepository.findByPoNo(poNo);
			if (poMaster == null) {
				return null; // return null; 意味着这张单据不会调上传订单sap接口
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
			//			Dealer dealer = dsDealerRepository.findByDealerNo(poMaster.getOrderDealerNo());
			PoHeader dealer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			// 推荐的新卡号申业后马上退货，然后又退货推荐单，导致推荐的新卡号被删除。
			//			if (dealer == null) {
			//				DealerDeleteBase deletedDeale = dealerDeleteBaseRepository.getDeletedDealer(poMaster.getOrderDealerNo());
			//				dealer = new Dealer();
			//				dealer.setSaleZoneNo(deletedDeale.getSaleZoneNo());
			//				dealer.setSaleBranchNo(deletedDeale.getSaleBranchNo());
			//			}

			// 公共字段赋值 传入订单主表
			this.theSameBill(pOrderHeader, poMaster, poAddrDetail, dealer);

			// GBSS-17286 
			// 大平台订单加入发票信息，需要上传发票信息至SAP
			// add by kevin.wang
			if (poMaster.getPoAppNo() != null && !"".equals(poMaster.getPoAppNo())) {
				if ("G101".equals(poMaster.getPoProcessCode())) {
					PoHeader dsd = poHeaderRepository.findByPoNo(poMaster.getPoNo());
					PoHeader ds = dsd;
					//					DealerStoreExtra dsd = dealerStoreExtraRepository.findByStoreNo(poMaster.getOrderDealerNo());
					//					DealerStore ds = dsDealerStoreRepository.findByStoreNo(poMaster.getOrderDealerNo());
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
			//			if (orderSapType == TypeSapOrderType.ZNF4) {
			//				this.serviceCenterCommonBill(pOrderHeader, poMaster);
			//			}
			// 服务中心免费单
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

			dealerOrderVo.setPOrderHeader(pOrderHeader);
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
				List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository
						.findByPosTranDateAndPosStoreNoAndRefDocNo(poMaster.getPoDate(), poMaster.getPoStoreNo(), poMaster.getPoNo());
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
				this.setCppTConditionListByPoDetailAddZF01(totalZf01, tConditionList);
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

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
		tCondition1.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));// 申请单行项目
		tCondition1.setCondType("PR00");// 定价类型
		tCondition1.setCondValue(poDetail.getSalePrice());// 定价金额
		tCondition1.setCurrency("CNY");// 货币单位
		tConditionList.add(tCondition1);
		// 原点数:ZK05
		TCondition tCondition2 = new TCondition();
		tCondition2.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
		tCondition2.setCondType("ZK05");
		tCondition2.setCondValue(poDetail.getSalePoint());
		tCondition2.setCurrency("CNY");
		tConditionList.add(tCondition2);
		// 代扣代缴增值税:ZGT1
		TCondition tCondition3 = new TCondition();
		tCondition3.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
		tCondition3.setCondType("ZGT1");
		tCondition3.setCondValue(poDetail.getVatAmt());
		tCondition3.setCurrency("CNY");
		tConditionList.add(tCondition3);
		// 代扣代缴消费税:ZGT2
		TCondition tCondition4 = new TCondition();
		tCondition4.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
		tCondition4.setCondType("ZGT2");
		tCondition4.setCondValue(poDetail.getConsumTaxAmt());
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
					tCondition5.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
					tCondition5.setCondType("ZK74");
					tCondition5.setCondValue(rebateDisAmtWhole);
					tCondition5.setCurrency("CNY");
					tConditionList.add(tCondition5);
				}
			}
			// ZK61 优差折扣金额（优惠券分摊）
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (rebateDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition6 = new TCondition();
					tCondition6.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
					tCondition6.setCondType("ZK61");
					tCondition6.setCondValue(rebateDisAmtCoupon);
					tCondition6.setCurrency("CNY");
					tConditionList.add(tCondition6);
				}
			}
			// ZKWX 公司折扣金额（整单减分摊）
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (companyDisAmtWhole.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition7 = new TCondition();
					tCondition7.setItmNumber(String.valueOf(poDetail.getLineNo()));
					tCondition7.setCondType("ZKWX");
					tCondition7.setCondValue(companyDisAmtWhole);
					tCondition7.setCurrency("CNY");
					tConditionList.add(tCondition7);
				}
			}
			// ZK20 公司折扣金额（优惠券分摊）
			if (!poDetailDiscount.getProductAttr().equals("1")) {
				if (companyDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition8 = new TCondition();
					tCondition8.setItmNumber(String.valueOf(poDetail.getLineNo()));
					tCondition8.setCondType("ZK20");
					tCondition8.setCondValue(companyDisAmtCoupon);
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
			//			DealerCustomer dealerCustomer = dealerCustomerRepository.findByCustomerNo(poMaster.getOrderCustomerNo());
			PoHeader dealerCustomer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			if (dealerCustomer != null) {
				pOrderHeader.setCardYst(dealerCustomer.getDealerCustomerMobile());
				if (StringUtils.isNotBlank(dealerCustomer.getDealerCustomerFullname())) {
					pOrderHeader.setNameYst(dealerCustomer.getDealerCustomerFullname());
				}
			}
		}
	}

	public DealerOrderVo action(String poNo) throws Exception {
		DealerOrderVo dealerOrderVo = this.prepareDataNotPos(poNo);
		return dealerOrderVo;
	}

	/**
	 * 准备数据
	 * @param poNo TODO
	 *
	 * @return DealerOrderVo
	 * @throws Exception
	 */
	private DealerOrderVo prepareData(String poNo) throws Exception {
		try {
			//			numFormat.setGroupingUsed(false);
			// 上传订单的传入参数
			DealerOrderVo dealerOrderVo = new DealerOrderVo();

			//将全局的parentMap改到这里初始化.然后在参数中传递赋值
			Map<String, BigDecimal> parentMap = new HashMap<String, BigDecimal>();

			// 传入订单主表
			POrderHeader pOrderHeader = new POrderHeader();

			PoMaster poMaster = dsPoMasterRepository.findByPoNo(poNo);
			if (poMaster == null) {
				return null; // return null; 意味着这张单据不会调上传订单sap接口
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
			//			Dealer dealer = dsDealerRepository.findByDealerNo(poMaster.getOrderDealerNo());
			PoHeader dealer = poHeaderRepository.findByPoNo(poNo);

			// 推荐的新卡号申业后马上退货，然后又退货推荐单，导致推荐的新卡号被删除。
			//			if (dealer == null) {
			//				DealerDeleteBase deletedDeale = dealerDeleteBaseRepository.getDeletedDealer(poMaster.getOrderDealerNo());
			//				dealer = new Dealer();
			//				dealer.setSaleZoneNo(deletedDeale.getSaleZoneNo());
			//			}

			// 公共字段赋值 传入订单主表
			this.theSameBill(pOrderHeader, poMaster, poAddrDetail, dealer);

			// BUPREQ-8521
			// 服务中心订单加入发票信息，需要上传发票信息至SAP
			// add by mzj
			//			if (PoProcessCodeEnum.G301.getCode().equals(poProcessCode) || PoProcessCodeEnum.G302.getCode().equals(poProcessCode)
			//					|| PoProcessCodeEnum.G303.getCode().equals(poProcessCode)) {
			//				if (StringUtils.isNotBlank(poMaster.getPoNo())) {
			//					PoInvoiceInfo info = poInvoiceInfoRepository.findByPoAppNo(poMaster.getPoNo());
			//					if (info != null && "E".equals(info.getInvoiceType())) {
			//						this.setInvoiceBill(pOrderHeader, info);
			//					}
			//				}
			//			}

			// 根据订单类型赋值个性字段开始
			// 总公司普通单
			if (orderSapType == TypeSapOrderType.ZNF3) {
				this.companyCommonBill(pOrderHeader, poMaster, poAddrDetail);
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
			//			if (orderSapType == TypeSapOrderType.ZNF4) {
			//				this.serviceCenterCommonBill(pOrderHeader, poMaster);
			//			}
			// 服务中心免费单
			//			if (orderSapType == TypeSapOrderType.ZGFR) {
			//				this.serviceCenterFreeBill(pOrderHeader, poMaster);
			//				//add ky_qrj 2014-05-27
			//				if (poMaster.getPoProcessCode().equals(TypeSapProcessCode.G311)) {
			//					pOrderHeader.setOrdReason("");
			//				}
			//				//end 2014-05-27
			//			}
			// 服务中心退货单 G305
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
			//add end

			//add by zdm 2020-01-03 服务中心发票上传
			//			PoInvoiceInfo info = poInvoiceInfoRepository.findByPoAppNo(poMaster.getPoAppNo());
			//			this.setInvoiceBill(pOrderHeader, info);
			//add end

			dealerOrderVo.setPOrderHeader(pOrderHeader);
			// 传入订单明细列表
			List<TOrderItems> tOrderItemsList = new ArrayList<TOrderItems>();
			// 传入定价条件列表
			List<TCondition> tConditionList = new ArrayList<TCondition>();
			// bom产品是行号的写法
			// t_condition和T_ORDER_ITEMS的赋值
			Date poDate = poMaster.getPoDate();
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
					this.companyCommonTorderItems(poDetail, tOrderItems, poProcessCode);
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
					if (tOrderItems != null) {
						this.serviceCenterReturnGbssTorderItems(poDetail, tOrderItems, poDate, poProcessCode);
					}
					if (tOrderItems4Prom != null) {
						this.serviceCenterReturnGbssTorderItems(poDetail, tOrderItems4Prom, poDate, poProcessCode);
					}
				}
				// add by yx 2012-11-29 end 

				// add by kevin.wang 2015-06-25 G501
				if (orderSapType == TypeSapOrderType.ZREA) {
					this.storeReturnCompanyItems(poDetail, tOrderItems, poDate);
				}
				// add  end
				if (tOrderItems != null) {
					tOrderItemsList.add(tOrderItems);
				}
				if (tOrderItems4Prom != null) {
					tOrderItemsList.add(tOrderItems4Prom);
				}
				if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
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
				} else {
					// 定价列表根据PoDetail加入2种定价类型：单价、原点数
					this.setTConditionListByPoDetail(poDetail, tConditionList);
				}
			}
			dealerOrderVo.settOrderItemsList(tOrderItemsList);
			// posAccTranDetail 与poPaymentDetail可能都写了启业套装
			//			if (orderSapType == TypeSapOrderType.ZNF4 || orderSapType == TypeSapOrderType.ZGFR
			//					|| orderSapType == TypeSapOrderType.ZRE2 || orderSapType == TypeSapOrderType.ZRE7
			//					|| orderSapType == TypeSapOrderType.ZRE8) {
			//				// 服务中心的支付类型
			//				for (PosAccTranDetail posAccTranDetail : posAccTranDetailList) {
			//					// 定价列表根据PoPaymentDetail加入剩下的定价类型
			//					this.setTConditionListByPosAccTranDetail(posAccTranDetail, tConditionList);
			//				}
			//			} else {
			if ("G304".equals(poMaster.getPoProcessCode())) {
				List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository
						.findByPosTranDateAndPosStoreNoAndRefDocNo(poMaster.getPoDate(), poMaster.getPoStoreNo(), poMaster.getPoNo());
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
			// 
			if (orderSapType == TypeSapOrderType.ZNF3 || orderSapType == TypeSapOrderType.ZYD1 || orderSapType == TypeSapOrderType.ZNF4
					|| orderSapType == TypeSapOrderType.ZRE2) {
				this.setTConditionListByPoMaster(poMaster, tConditionList);
			}

			//GBSS-17238
			//2017新星启航增加折扣参数进去
			//Add by kevin.wang 2017-03-01
			if ("G310".equals(poMaster.getPoProcessCode())) {
				this.setTConditionListWithZKBM(poMaster, tConditionList);
			} else if ((TypeSapProcessCode.G306.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G001.equals(poMaster.getPoProcessCode())
					|| TypeSapProcessCode.G002.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G020.equals(poMaster.getPoProcessCode()))
					&& (BigDecimal.ZERO.compareTo(poMaster.getTotalSaleDiscountAmt()) != 0)) {//20170516添加微信折扣金额 add by zhuohr
				//this.setTConditionListWithZKWX(poMaster,tConditionList);
			}
			dealerOrderVo.settConditionList(tConditionList);
			return dealerOrderVo;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

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
			tCondition3.setCondValue(posAccTranDetail.getPosTranAmt());
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}
		// 企业套装折让
		if (posAccTranDetail.getPosPaymentType().equals(TypePoPaymentType.MK01)) {
			// 启业套装:ZK80
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK80");
			tCondition3.setCondValue(posAccTranDetail.getPosTranAmt());
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}

		// 电子券 add by jinxu li 2014-02-18
		if (posAccTranDetail.getPosPaymentType().equals(TypePoPaymentType.MK03)) {
			// 电子券:ZK20
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK20");
			tCondition3.setCondValue(posAccTranDetail.getPosTranAmt());
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
			tCondition3.setCondValue(poPaymentDetail.getPoPaymentAmt());
			tCondition3.setCurrency("CNY");
			tConditionList.add(tCondition3);
		}
		// 企业套装折让
		if (paymentType.equals(TypePoPaymentType.MK01)) {
			// 启业套装:ZK80
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber("00");
			tCondition3.setCondType("ZK80");
			tCondition3.setCondValue(poPaymentDetail.getPoPaymentAmt());
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
						tempTotalCouponAmt = tempTotalCouponAmt.add(tCondition.getCondValue());
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
				tCondition3.setCondValue(totalCouponAmt);
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
			tCondition1.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));// 申请单行项目
			tCondition1.setCondType("PR00");// 定价类型
			tCondition1.setCondValue(poDetail.getSalePrice());// 定价金额
			tCondition1.setCurrency("CNY");// 货币单位
			tConditionList.add(tCondition1);
		}
		// // prodectattr不是1，且价格为不为0的才传
		if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePoint() != null && poDetail.getSalePoint().compareTo(new BigDecimal(0)) != 0) {
			// 原点数:ZK05
			TCondition tCondition2 = new TCondition();
			tCondition2.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
			tCondition2.setCondType("ZK05");
			tCondition2.setCondValue(poDetail.getSalePoint());
			tCondition2.setCurrency("CNY");
			tConditionList.add(tCondition2);
			// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
			TCondition tCondition3 = new TCondition();
			tCondition3.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));
			tCondition3.setCondType("ZK06");
			tCondition3.setCondValue(poDetail.getSalePoint());
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
				tCondition1.setItmNumber(String.valueOf(lineNo.intValue()));// 申请单行项目
				tCondition1.setCondType("PR00");// 定价类型
				tCondition1.setCondValue(poDetail.getSalePrice());// 定价金额
				tCondition1.setCurrency("CNY");// 货币单位
				tConditionList.add(tCondition1);
			}
			// // prodectattr不是1，且价格为不为0的才传
			if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePoint() != null
					&& poDetail.getSalePoint().compareTo(BigDecimal.ZERO) != 0) {
				// 原点数:ZK05
				TCondition tCondition2 = new TCondition();
				tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
				tCondition2.setCondType("ZK05");
				tCondition2.setCondValue(poDetail.getSalePoint());
				tCondition2.setCurrency("CNY");
				tConditionList.add(tCondition2);
				// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber(String.valueOf(lineNo.intValue()));
				tCondition3.setCondType("ZK06");
				tCondition3.setCondValue(poDetail.getSalePoint());
				tCondition3.setCurrency("CNY");
				tConditionList.add(tCondition3);
			}
			/// 单位理论优差金额
			if (!poDetail.getProductAttr().equals("1")) {
				BigDecimal totalSaleRebate = poDetail.getSaleRebate().multiply(saleQtyBigDecimal);
				if (totalSaleRebate.compareTo(BigDecimal.ZERO) != 0) {
					// 单位理论优差:ZK71
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
					tCondition2.setCondType("ZK71");
					totalSaleRebate = totalSaleRebate.abs();
					tCondition2.setCondValue(totalSaleRebate);
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
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZK74");
						tCondition2.setCondValue(rebateDisAmtWhole);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK61 优差折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (rebateDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZK61");
						tCondition2.setCondValue(rebateDisAmtCoupon);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZKWX 公司折扣金额（整单减分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtWhole.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZKWX");
						tCondition2.setCondValue(companyDisAmtWhole);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK20 公司折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtCoupon.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZK20");
						tCondition2.setCondValue(companyDisAmtCoupon);
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
				tCondition1.setItmNumber(String.valueOf(lineNo.intValue()));// 申请单行项目
				tCondition1.setCondType("PR00");// 定价类型
				tCondition1.setCondValue(poDetail.getSalePrice());// 定价金额
				tCondition1.setCurrency("CNY");// 货币单位
				tConditionList.add(tCondition1);
			}
			// // prodectattr不是1，且价格为不为0的才传
			if (!poDetail.getProductAttr().equals("1") && poDetail.getSalePoint() != null
					&& poDetail.getSalePoint().compareTo(BigDecimal.ZERO) != 0) {
				// 原点数:ZK05
				TCondition tCondition2 = new TCondition();
				tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
				tCondition2.setCondType("ZK05");
				tCondition2.setCondValue(poDetail.getSalePoint());
				tCondition2.setCurrency("CNY");
				tConditionList.add(tCondition2);
				// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber(String.valueOf(lineNo.intValue()));
				tCondition3.setCondType("ZK06");
				tCondition3.setCondValue(poDetail.getSalePoint());
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
					tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
					tCondition2.setCondType("ZK71");
					totalSaleRebate = totalSaleRebate.abs();
					tCondition2.setCondValue(totalSaleRebate);
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
					tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
					tCondition2.setCondType("ZK72");
					tCondition2.setCondValue(promRebateTotal);
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
					tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
					tCondition2.setCondType("ZK62");
					tCondition2.setCondValue(totalPromDisAmt);
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
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZK74");
						tCondition2.setCondValue(rebateDisAmtWholeForProm);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK61 优差折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (rebateDisAmtCouponForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZK61");
						tCondition2.setCondValue(rebateDisAmtCouponForProm);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZKWX 公司折扣金额（整单减分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtWholeForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZKWX");
						tCondition2.setCondValue(companyDisAmtWholeForProm);
						tCondition2.setCurrency("CNY");
						tConditionList.add(tCondition2);
					}
				}
				// ZK20 公司折扣金额（优惠券分摊）
				if (!poDetailDiscount.getProductAttr().equals("1")) {
					if (companyDisAmtCouponForProm.compareTo(BigDecimal.ZERO) != 0) {
						TCondition tCondition2 = new TCondition();
						tCondition2.setItmNumber(String.valueOf(lineNo.intValue()));
						tCondition2.setCondType("ZK20");
						tCondition2.setCondValue(companyDisAmtCouponForProm);
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
		tCondition1.setItmNumber(String.valueOf(poDetail.getLineNo()));// 申请单行项目
		tCondition1.setCondType("PR00");// 定价类型
		tCondition1.setCurrency("CNY");// 货币单位
		if (TypePoPaymentType.CPPCASH.equals(paymentType)) //现金＋积分
			tCondition1.setCondValue(poDetail.getSalePrice());// 基准价
		else
			tCondition1.setCondValue(BigDecimal.ZERO);//纯新积分
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
			tCondition1.setItmNumber(String.valueOf(poDetail.getLineNo().intValue()));// 申请单行项目
			tCondition1.setCondType("PR00");// 定价类型
			tCondition1.setCurrency("CNY");// 货币单位
			tCondition1.setCondValue(poDetail.getSalePrice());// 基准价
			tConditionList.add(tCondition1);

			//记录分享会行项目的折扣
			//add by mzj
			if (poDetailDiscount != null) {
				if (poDetailDiscount.getCompanyDisAmtWhole().compareTo(BigDecimal.ZERO) != 0) {
					TCondition tCondition2 = new TCondition();
					tCondition2.setItmNumber(String.valueOf(poDetailDiscount.getLineNo()));
					tCondition2.setCondType("ZF01");
					tCondition2.setCondValue(poDetailDiscount.getCompanyDisAmtWhole());
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
				tCondition1.setCondValue(mkpDlpPoRelate.getTotalDlpAmt());//预提值
			}
			tConditionList.add(tCondition1);
			TCondition tCondition2 = new TCondition();
			tCondition2.setItmNumber("00");// 申请单行项目
			tCondition2.setCondType("ZF01");// 定价类型
			tCondition2.setCurrency("CNY");// 货币单位
			if (mkpDlpPoRelate != null) {
				tCondition2.setCondValue(mkpDlpPoRelate.getTotalDiscountAmt());//积分抵扣金额
			}
			tConditionList.add(tCondition2);
		} else {
			TCondition tCondition1 = new TCondition();
			tCondition1.setItmNumber("00");// 申请单行项目
			tCondition1.setCondType("YT01");// 定价类型
			tCondition1.setCurrency("CNY");// 货币单位
			if (mkpDlpPoRelate != null) {
				tCondition1.setCondValue(mkpDlpPoRelate.getTotalDlpAmt());// 预提值
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
			tCondition1.setCondValue(totalDlpAmt);//预提值
			tConditionList.add(tCondition1);

			//记录整单折扣
			BigDecimal lineDiscountAmt = BigDecimal.ZERO;
			if (CollectionUtils.isNotEmpty(tConditionList)) {
				for (TCondition tCondition : tConditionList) {
					if ("ZF01".equals(tCondition.getCondType())) {
						lineDiscountAmt = lineDiscountAmt.add(tCondition.getCondValue());
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
				tCondition2.setCondValue(totalDiscountAmt);//积分抵扣金额
				tConditionList.add(tCondition2);
			}
		}
	}

	//	private void setCppTConditionListByPoDetailAddZF01(String value, List<TCondition> tConditionList) {
	//		if (value == null) {
	//			return;
	//		}
	//		// prodectattr不是1，且价格为不为0的才传
	//
	//		TCondition tCondition4 = new TCondition();
	//		tCondition4.setItmNumber("00");
	//		tCondition4.setCondType("ZF01");
	//		tCondition4.setCondValue(value);
	//		tCondition4.setCurrency("CNY");
	//		tConditionList.add(tCondition4);
	//
	//	}

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
			tCondition1.setCondValue(totalCalcRebate);// 定价金额
			tCondition1.setCurrency("CNY");// 货币单位
			tConditionList.add(tCondition1);
		}

		// 2012-12-27 by yx 暂时注销 
		//		// 库存调拨:ZVP3  
		//		Object obj = dsPoDetailRepository.getProductSumAmt(poMaster.getPoNo());
		//		String totalAmt = obj != null ? obj : "0";
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
		BigDecimal saleQty;
		if (isProm) {
			if (promQtyBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
				return null;
			}
			saleQty = promQtyBigDecimal;
		} else {
			if (saleQtyBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
				return null;
			}
			saleQty = saleQtyBigDecimal;
		}

		TOrderItems tOrderItems = new TOrderItems();
		BigDecimal lineNo = poDetail.getLineNo();
		if (isProm) {
			lineNo = lineNo.add(BigDecimal.TEN);
		}
		tOrderItems.setItmNumber(String.valueOf(lineNo.intValue()));// 销售订单行项目
		tOrderItems.setMaterial(poDetail.getProductCode());// 物料编码

		// 将GBSS中保存的负数转为正数
		//		if (saleQty) {
		//			saleQty = saleQty.replace("-", "");
		//		}
		saleQty = saleQty.abs();
		BigDecimal salePrice = poDetail.getSalePrice();
		tOrderItems.setTargetQty(saleQty);// 订购数量
		// prodectattr是1，价格传0
		if (poDetail.getProductAttr().equals("1")) {
			salePrice = BigDecimal.ZERO;
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
			//			Date reqDate = DateTimeUtil.getCurrentTime();
			//			tOrderItems.setReqDate(SapFormatUtil.formatDate(reqDate));// 交货日期
			tOrderItems.setReqDate(SystemUtils.getCurrentTime());
		} else {
			//			tOrderItems.setReqDate("");
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
				tOrderItems.setHgLvItem(String.valueOf(hgLvItem.intValue()));// 上层项目号
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
		tOrderItems.setReqDate(poDate);// 交货日期
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
		tOrderItems.setReqDate(poDate);// 交货日期
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
		tOrderItems.setReqDate(poDate);// 交货日期
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
		tOrderItems.setReqDate(poDate);// 交货日期

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
		tOrderItems.setReqDate(poDate);// 交货日期
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
	private void theSameBill(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail, PoHeader dealer) {
		// 备注
		pOrderHeader.setBeiZhu("");
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
		//		pOrderHeader.setPoState(poMaster.getPoStatus());//订单状态(新增)
		// 售达方
		pOrderHeader.setName(poMaster.getOrderDealerName());// 姓名
		pOrderHeader.setName2(poMaster.getOrderDealerNo());// 卡号
		pOrderHeader.setHomeCity(dealer.getSaleZoneNo());// 片区
		// 送达方信息
		pOrderHeader.setKunn1Name(poAddrDetail.getAddrSendId());// 地址id
		pOrderHeader.setKunn1Name2(poAddrDetail.getAddrAreaCode());// 行政区id
		pOrderHeader.setFreightAmount(poMaster.getTotalTransportAmt());// 运费总值
		pOrderHeader.setKunnrWe(poAddrDetail.getDeliveryDealerNo()); // 提货点
		pOrderHeader.setKunn1Region(this.getSapRegion(poAddrDetail.getAddrAreaCode()));// 地区
		pOrderHeader.setKunn1City(poAddrDetail.getAddrCity());// 城市
		pOrderHeader.setKunn1District(poAddrDetail.getAddrCounty());// 区域
		// 预订单的主单标志
		// 1、 试点地区，不管那2个字段Kunn1HomeCity、Kunn1HouseNum1
		// 2、 非试点地区，po_addr_detail 里的ADDR_AREA_CODE， 再去找PO_STORE_ADDRESS
		// 看IS_DEFAULT ==1 ，设X，否则 空
		pOrderHeader.setKunn1HomeCity("");
		// 3、po_addr_detail 的DELIVERY_ATTR ==0 主单，1 辅单
		// 00 辅单,01 主单,02 平台录入专卖店订单
		pOrderHeader.setKunn1HouseNum1("");

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
//		pOrderHeader.setPriceDate("");// //价格日期
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
			PoHeader orderDealer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			//			Dealer orderDealer = dsDealerRepository.findByDealerNo(poMaster.getOrderDealerNo());
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
				if (poAddrDetail.getDeliveryType().equals(TypeDeliveryType.JP)) {
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
		if ((poAddrDetail.getDeliveryType()).equals(TypeDeliveryType.SHOP)) {
			PoHeader shopDealer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			//			Dealer shopDealer = dsDealerRepository.findByDealerNo(poAddrDetail.getDeliveryDealerNo());
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
			pOrderHeader.setPoEntryTime(poMaster.getPoEntryTime());
		} else {
			//			pOrderHeader.setPoEntryTime("");
		}

		// 计划发货日期  yyyyMMdd
		if (poAddrDetail.getDeliveryPlanDate() != null) {
			pOrderHeader.setDeliveryDate(poAddrDetail.getDeliveryPlanDate());
		} else {
			//			pOrderHeader.setDeliveryDate("");
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
			PoHeader ystDealer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			//			Dealer ystDealer = dsDealerRepository.findByDealerNo(poAddrDetail.getDeliveryDealerNo());
			if (ystDealer != null) {
				pOrderHeader.setNameYst(ystDealer.getDealerCustomerFullname());
			}
		}

		// 普消购货
		if (StringUtils.isNotBlank(poMaster.getOrderCustomerNo())) {
			PoHeader dealerCustomer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			//			DealerCustomer dealerCustomer = dealerCustomerRepository.findByCustomerNo(poMaster.getOrderCustomerNo());
			//			if (dealerCustomer != null) {
			pOrderHeader.setCardYst(dealerCustomer.getDealerCustomerMobile());
			if (StringUtils.isNotBlank(dealerCustomer.getDealerCustomerFullname())) {
				pOrderHeader.setNameYst(dealerCustomer.getDealerCustomerFullname());
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
			PoHeader dealerCustomer = poHeaderRepository.findByPoNo(poMaster.getPoNo());
			//			DealerCustomer dealerCustomer = dealerCustomerRepository.findByCustomerNo(poMaster.getOrderCustomerNo());
			if (dealerCustomer != null) {
				pOrderHeader.setCardYst(dealerCustomer.getDealerCustomerMobile());
				if (StringUtils.isNotBlank(dealerCustomer.getDealerCustomerFullname())) {
					pOrderHeader.setNameYst(dealerCustomer.getDealerCustomerFullname());
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
					//					PoMaster subPoMaster = dsPoMasterRepository.findByRefPoNoAndPoProcessCodeAndCompanyNo(refPoMaster.getPoNo(),
					//							refPoMaster.getPoProcessCode(), companyNo);
					//					sapPostingDocNo = subPoMaster.getSapPostingDocNo();
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
		pOrderHeader.setFreightAmount(BigDecimal.ZERO);//
		pOrderHeader.setKunn1Region("");//
		pOrderHeader.setKunn1City("");//
		pOrderHeader.setKunn1District("");//
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
		pOrderHeader.setFreightAmount(BigDecimal.ZERO);//
		pOrderHeader.setKunn1Region("");//
		pOrderHeader.setKunn1City("");//
		pOrderHeader.setKunn1District("");//
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

	static Map<String, String> SpecialProvinceMapping = new ConcurrentHashMap<String, String>();
	static {
		SpecialProvinceMapping.put("120117001", "130000");
		SpecialProvinceMapping.put("120117113", "130000");
		SpecialProvinceMapping.put("471004", "410000");
		SpecialProvinceMapping.put("471005", "410000");
	}

	private String getSapRegion(String addrAreaCode) {
		String dsCode = SpecialProvinceMapping.getOrDefault(addrAreaCode, addrAreaCode.substring(0, 2) + "0000");
		SapDsMapping sapDsMapping = sapDsMappingRepository.findByDsCode(dsCode);
		return sapDsMapping == null ? "190" : sapDsMapping.getSapCode();
	}

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
				tOrderItems.setKwertPr00(BigDecimal.ZERO);
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
				tOrderItems.setKwertPr00(poDetail.getSalePrice());//基准价
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
				tOrderItems.setKwertPr00(BigDecimal.ZERO);
			} else {
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
				tOrderItems.setKwertPr00(poDetail.getSalePrice());//基准价
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
			tCondition.setCondValue(poMaster.getTotalSaleDiscountAmt());
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
					tempTotalSaleDiscountAmt = tempTotalSaleDiscountAmt.add(tCondition.getCondValue());
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
			tCondition.setCondValue(totalSaleDiscountAmt);
			tCondition.setCurrency("CNY");
			tConditionList.add(tCondition);
		}
	}

	private void setInvoiceBill(POrderHeader header, PoHeader info, PoHeader ds) {
		if (ds != null && StringUtils.isNotEmpty(ds.getDealerStoreFullname()))
			header.setField1(ds.getDealerStoreFullname());
		if (info != null && StringUtils.isNotEmpty(info.getDealerStoreNtaxRegisterNo()))
			header.setField4(info.getDealerStoreNtaxRegisterNo());
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

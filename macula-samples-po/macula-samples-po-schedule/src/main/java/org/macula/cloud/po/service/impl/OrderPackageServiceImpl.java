package org.macula.cloud.po.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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
import org.macula.cloud.po.domain.PosAccTranDetail;
import org.macula.cloud.po.domain.SaleBranchInfo;
import org.macula.cloud.po.domain.SapDsMapping;
import org.macula.cloud.po.domain.SysCompany;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.repository.SaleBranchInfoRepository;
import org.macula.cloud.po.repository.SapDsMappingRepository;
import org.macula.cloud.po.repository.SysCompanyRepository;
import org.macula.cloud.po.sap.model.POrderHeader;
import org.macula.cloud.po.sap.model.TCondition;
import org.macula.cloud.po.sap.model.TOrderItems;
import org.macula.cloud.po.service.OrderPackageService;
import org.macula.cloud.po.type.ProductTypeEnum;
import org.macula.cloud.po.type.TypeDeliveryType;
import org.macula.cloud.po.type.TypePoPaymentType;
import org.macula.cloud.po.type.TypeSapOrderType;
import org.macula.cloud.po.type.TypeSapProcessCode;
import org.macula.cloud.po.vo.DealerOrderVo;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class OrderPackageServiceImpl implements OrderPackageService {

	private SaleBranchInfoRepository saleBranchInfoRepository;
	private SysCompanyRepository sysCompanyRepository;
	private SapDsMappingRepository sapDsMappingRepository;
	private PoMasterRepository dsPoMasterRepository;

	// 流程编号初始化赋值
	private static Map<String, Integer> poProcessCodeMap = new HashMap<>();

	@PostConstruct
	public void initMap() {
		initPoProcessCodeMap();
	}

	/**
	 * 方法说明:根据poNo将本系统中相关数据查询出,并封装成SAP需要的VO对象
	 * @param poNo:单号
	 * @return
	 */
	public DealerOrderVo packageSapOrderVo(String poNo, PoResultDetailVo vo) {
		try {
			if (vo == null) {
				throw new OMSException(String.format("订单[ %s ]数据为空", poNo));
			}
			// 1：查询
			// 上传至SAP的VO数据封装:订单的传入参数
			DealerOrderVo dealerOrderVo = new DealerOrderVo();

			//将全局的parentMap改到这里初始化.然后在参数中传递赋值
			Map<String, BigDecimal> parentMap = new HashMap<String, BigDecimal>();

			// ===============↓ SAP-VO组装对象 ↓=============== //
			// 封装订单主表数据明细
			POrderHeader pOrderHeader = new POrderHeader();
			// 传入订单明细列表
			List<TOrderItems> tOrderItemsList = new ArrayList<>();
			// 传入定价条件列表
			List<TCondition> tConditionList = new ArrayList<>();
			// ===============↑ SAP-VO组装对象 ↑=============== //

			// 1：订货信息主表
			PoMaster poMaster = vo.getPoMaster();
			if (poMaster == null) {
				throw new OMSException(String.format("订单[ %s ]数据为空", poNo));
			}

			// 获取订货流程代码
			String poProcessCode = poMaster.getPoProcessCode();
			if (!poProcessCodeMap.containsKey(poProcessCode)) {
				throw new OMSException(String.format("不支持流程 [ %s ]", poProcessCode));
			}

			// 2：根据订单号查询订单详细信息
			List<PoDetail> poDetailList = vo.getPoDetails();

			// 3：根据订单号查询订单配送地址信息
			PoAddrDetail poAddrDetail = vo.getPoAddrDetail();

			// 4：查询出当前poNo相关的数据
			PoHeader poHeader = vo.getPoHeader();

			// SAP参数1:PoNo
			dealerOrderVo.setPoNo(poMaster.getPoNo());

			// 取出产品编号与lineNo
			this.getParentLineNum(poDetailList, parentMap);

			// ===============↓ pOrderHeader数据的组装 ↓=============== //
			theSameBill(pOrderHeader, poMaster, poAddrDetail, poHeader);
			/**
			 * 大平台订单加入发票信息，需要上传发票信息至SAP
			 */

			// 获取:销售订货申请单号
			if (poMaster.getPoAppNo() != null && !"".equals(poMaster.getPoAppNo())) {
				if (TypeSapProcessCode.G101.equals(poProcessCode)) {

					String ntaxRegisterNo = poHeader.getDealerStoreNtaxRegisterNo();
					String dealerFullName = poHeader.getDealerStoreFullname();
					if (ntaxRegisterNo != null && StringUtils.isNotEmpty(ntaxRegisterNo)) {
						pOrderHeader.setField4(ntaxRegisterNo); // 备用字段4 = 国税注册号
					}
					if (dealerFullName != null && StringUtils.isNotEmpty(dealerFullName)) {
						pOrderHeader.setField1(dealerFullName); // 备用字段1 = 店主卡号姓名
					}
				} else if (TypeSapProcessCode.G103.equals(poProcessCode)) {
					// 企业购增值税发票
					PoInvoiceInfo info = vo.getPoInvoiceInfo();
					// 增值税发票
					this.setInvoiceBillVat(pOrderHeader, info);
				} else {
					PoInvoiceInfo info = vo.getPoInvoiceInfo();
					// 电子发票或者普通纸质发票
					this.setInvoiceBill(pOrderHeader, info);
				}
			}

			pOrderHeader.setSalesOff(poHeader.getSaleBranchNo());// 销售部门
			// 销售分支机构表
			SaleBranchInfo saleBranchInfo = saleBranchInfoRepository.findBySaleBranchNo(poHeader.getSaleBranchNo());
			if (saleBranchInfo != null) {
				pOrderHeader.setSalesDist(saleBranchInfo.getSaleRegionNo());// 销售区域
			}
			if (StringUtils.isBlank(poMaster.getOrderDealerBranchNo())) { // 购货人对应的分公司
				poMaster.setOrderDealerBranchNo(poHeader.getSaleBranchNo()); // 销售分支机构代号
				// dsPoMasterRepository.save(poMaster);
			}
			// 根据:订货流程代码poProcessCode判断订单类型:0:总公司普通单,1:总公司预定／记欠单……
			int orderSapType = poProcessCodeMap.get(poProcessCode);
			// 根据订单类型赋值个性字段开始
			// 总公司普通单
			if (orderSapType == TypeSapOrderType.ZNF3) {
				this.companyCommonBill(pOrderHeader, poMaster, poAddrDetail, poHeader);
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
				if (poProcessCode.equals(TypeSapProcessCode.G311)) {
					pOrderHeader.setOrdReason("");
				}
			}
			// 服务中心退货单 G305
			if (orderSapType == TypeSapOrderType.ZRE2) {
				this.serviceCenterReturnBill(pOrderHeader, poMaster, vo);
			}
			// 服务中心代退货单 G306
			if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
				this.serviceCenterReturnGbss(pOrderHeader, poMaster);
			}
			// 因怡瑞召回新增G501类型
			if (orderSapType == TypeSapOrderType.ZREA) {
				this.storeReturnCompany(pOrderHeader, poMaster, poAddrDetail);
			}
			// 总公司普通单 纷享荟二期
			MkpDlpPoRelate mkpDlpPoRelate = null;
			if (orderSapType == TypeSapOrderType.ZRE1) {
				mkpDlpPoRelate = vo.getMkpDlpPoRelate();
				this.cppCompanyCommonBill(pOrderHeader, poMaster);
			}
			// g904质量补损单 传入订单主表
			if (orderSapType == TypeSapOrderType.ZFD2) {
				pOrderHeader.setDistrChan("C1");// 分销渠道
				pOrderHeader.setSalesGrp("");// 销售组ß
				pOrderHeader.setPosNumber("");// 订单单号
				pOrderHeader.setKonda("");// 价格组
			}
			if (orderSapType == TypeSapOrderType.ZRE4 || orderSapType == TypeSapOrderType.ZRE6) {
				pOrderHeader.setDeliveryFlag("N");
			}
			if (orderSapType == TypeSapOrderType.ZNF8) {
				this.gpOrderBill(pOrderHeader, poMaster, poHeader);
			}
			// ===============↑ pOrderHeader数据的组装 ↑=============== //

			// 2:SAP-VO封装pOrderHeader
			dealerOrderVo.setPOrderHeader(pOrderHeader);

			// ===============↓ List<TOrderItems>数据的组装 ↓=============== //
			Date poDate = poMaster.getPoDate();
			// 获取订货折扣明细集合
			List<PoDetailDiscount> poDetailDiscountList = vo.getPoDetailDiscounts();

			for (PoDetail poDetail : poDetailList) {
				/**
				 * 需要的数据：poDetail:订货信息明细
				 * 需要的数据：poAddrDetail:订货配送地址
				 * 需要的数据：poMaster:订货信息主表
				 * 需要的数据：parentMap:parentMap.put(poDetail.getProductCode(), poDetail.getLineNo());
				 */
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
				// 总公司预定／记欠单 同步业务平台代码：2020/09/15 zjl
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

				// 增加总公司 月刊单
				// 总公司 月刊单 传入订单明细
				if (orderSapType == TypeSapOrderType.ZNF6) {
					tOrderItems.setItemCateg("TANN");
				}

				// 服务中心普通单
				if (orderSapType == TypeSapOrderType.ZNF4) {
					this.serviceCenterCommonTorderItems(poDetail, tOrderItems, poDate);
				}
				// 服务中心免费单
				if (orderSapType == TypeSapOrderType.ZGFR) {
					this.serviceCenterFreeTorderItems(poDetail, tOrderItems, poDate);
					if (poProcessCode.equals(TypeSapProcessCode.G311)) {
						tOrderItems.setItemCateg("ZTAX");
					}
				}
				// 服务中心退货单 G305
				if (orderSapType == TypeSapOrderType.ZRE2) {
					this.serviceCenterReturnTorderItems(poDetail, tOrderItems, poDate);
				}
				// 服务中心代退货单 G306 无参考退货单G308start
				if (orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
					this.serviceCenterReturnGbssTorderItems(poDetail, tOrderItems, poDate, poProcessCode);
				}
				if (orderSapType == TypeSapOrderType.ZREA) {
					this.storeReturnCompanyItems(poDetail, tOrderItems, poDate);
				}
				//总公司普通单 纷享荟二期 add by zhuohr
				if (orderSapType == TypeSapOrderType.ZRE1) {
					this.cppCompanyCommonTorderItems(poDetail, mkpDlpPoRelate, tOrderItems);
				}
				// 全球购订单
				if (orderSapType == TypeSapOrderType.ZNF8) {
					tOrderItems.setItemCateg("ZTAN");
				}
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
					// 纷享绘需求要改 （临时方案）
					// modify by kevin
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

			// GBSS-17453
			// 应财务要求纷享绘订单统一上传信息，
			if (orderSapType == TypeSapOrderType.ZRE1 && mkpDlpPoRelate != null) {
				this.setCppTConditionAddYT01AndZF01(tConditionList, mkpDlpPoRelate);
			}

			// 用户信息组装结束
			dealerOrderVo.settOrderItemsList(tOrderItemsList);

			if (TypeSapProcessCode.G304.equals(poProcessCode)) {
				List<PosAccTranDetail> posAccTranDetailList = vo.getPosAccTranDetails();
				for (PosAccTranDetail posAccTranDetail : posAccTranDetailList) {
					// 定价列表根据PoPaymentDetail加入剩下的定价类型
					this.setTConditionListByPosAccTranDetail(posAccTranDetail, tConditionList);
				}
			} else {
				// 查找订货支付信息
				List<PoPaymentDetail> poPaymentDetailList = vo.getPoPaymentDetails();
				// 处理整单T_CONDITION
				for (PoPaymentDetail poPaymentDetail : poPaymentDetailList) {
					// 定价列表根据PoPaymentDetail加入剩下的定价类型
					this.setTConditionListByPoPaymentDetail(poPaymentDetail, tConditionList);
				}
			}
			if (orderSapType == TypeSapOrderType.ZNF3 || orderSapType == TypeSapOrderType.ZYD1 || orderSapType == TypeSapOrderType.ZNF4
					|| orderSapType == TypeSapOrderType.ZRE2 || orderSapType == TypeSapOrderType.ZRE7 || orderSapType == TypeSapOrderType.ZRE8) {
				//this.setTConditionListByPoMaster(poMaster, tConditionList);
			}
			// GBSS-17238
			// 2017新星启航增加折扣参数进去
			if (TypeSapProcessCode.G010.equals(poProcessCode) || TypeSapProcessCode.G017.equals(poProcessCode)) {
				this.setTConditionListWithZKBM(poMaster, tConditionList);
			} else if ((TypeSapProcessCode.G306.equals(poProcessCode) || TypeSapProcessCode.G001.equals(poProcessCode)
					|| TypeSapProcessCode.G002.equals(poProcessCode) || TypeSapProcessCode.G020.equals(poProcessCode)
					|| TypeSapProcessCode.G022.equals(poProcessCode) || TypeSapProcessCode.G023.equals(poProcessCode))
					&& (BigDecimal.ZERO.compareTo(poMaster.getTotalSaleDiscountAmt()) != 0)) {
				// 20170516添加微信折扣金额 add by zhuohr
				// this.setTConditionListWithZKWX(poMaster, tConditionList);
			}
			// 最后信息组装完毕
			dealerOrderVo.settConditionList(
					tConditionList.stream().filter(c -> c.getCondValue().compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList()));
			return dealerOrderVo;
		} catch (Exception e) {
			throw new OMSException("========== SAP封装VO失败 PO {} ==========", new Object[] { poNo }, e);
		}
	}

	private void theSameBill(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail, PoHeader poHeader) {
		// 备注
		pOrderHeader.setBeiZhu("");
		// 订货流程代码表
		//		PoProcessCodeInfo poProcessCodeInfo = poProcessCodeInfoRepository.findByPoProcessCode(poMaster.getPoProcessCode());
		//		if (poProcessCodeInfo != null) {
		//			pOrderHeader.setProcdName(poProcessCodeInfo.getSapProcessCode()); // 流程类型 = SAP的订货流程代码
		//		}
		pOrderHeader.setProcdName(poHeader.getSapProcessCode()); // 流程类型 = SAP的订货流程代码
		// 公司基本信息表
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
		pOrderHeader.setBeiZhu(""); // 备注
		pOrderHeader.setOrdReason("");// 订单原因
		pOrderHeader.setCollectNo(poMaster.getPoGroupNo());// 团单号
		pOrderHeader.setSoDate(poMaster.getPoDate());// 订单日期
		pOrderHeader.setOldOrder("1"); // 非试点的订单标志,试点1，非试点2(因为已经没有非试点地区,所以这一步都是1)
		// 售达方
		pOrderHeader.setName(poMaster.getOrderDealerName());// 姓名
		pOrderHeader.setName2(poMaster.getOrderDealerNo());// 卡号
		pOrderHeader.setHomeCity(poHeader.getSaleZoneNo());// 片区
		// 送达方信息
		pOrderHeader.setKunn1Name(poAddrDetail.getAddrSendId());// 地址id
		pOrderHeader.setKunn1Name2(poAddrDetail.getAddrAreaCode());// 行政区id
		pOrderHeader.setFreightAmount(poMaster.getTotalTransportAmt());// 运费总值
		pOrderHeader.setKunnrWe(poAddrDetail.getDeliveryDealerNo()); // 提货点
		String province = poAddrDetail.getAddrProvince();
		String city = poAddrDetail.getAddrCity();
		String country = poAddrDetail.getAddrCounty();
		if ("中国".equals(province)) {
			province = poAddrDetail.getAddrCity();
			city = poAddrDetail.getAddrCounty();
			country = poAddrDetail.getAddrDistrict();
		}
		pOrderHeader.setKunn1Region(this.getSapRegion(poAddrDetail.getAddrAreaCode(), province));// 地区 = poAddrDetail.收货地址省
		pOrderHeader.setKunn1City(city);// 城市
		pOrderHeader.setKunn1District(country);// 区域
		// 预订单的主单标志
		pOrderHeader.setKunn1HomeCity("");
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

		/**
		 * OrderDealerNo:购货人卡号:不为空
		 * 根号提货方式来给:"售达方ID","送达方","付款方卡号"赋值
		 */
		if (StringUtils.isNotBlank(poMaster.getOrderDealerNo())) {

			// 查询会员基本信息资料表不为null,则进入赋值
			if (poHeader.getSaleZoneNo() != null || poHeader.getSaleBranchNo() != null) {
				if (TypeSapProcessCode.G012.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G017.equals(poMaster.getPoProcessCode())) {
					pOrderHeader.setPartnNumb("666666666");// 售达方ID
					pOrderHeader.setPayKunn("666666666");// 付款方卡号
				} else {
					pOrderHeader.setPartnNumb("888888888");// 售达方ID
					pOrderHeader.setPayKunn("888888888");// 付款方卡号
				}
				// 获取提货类型:根据"提货类型"判断是否是"家居配送"和"专卖店提货"类型
				String deliveryType = poAddrDetail.getDeliveryType();
				if (deliveryType.equals(TypeDeliveryType.JP) || deliveryType.equals(TypeDeliveryType.SHOP)) {
					if (TypeSapProcessCode.G012.equals(poMaster.getPoProcessCode()) || TypeSapProcessCode.G017.equals(poMaster.getPoProcessCode())) {
						pOrderHeader.setPartnKunn1("666666666");// 送达方
					} else {
						pOrderHeader.setPartnKunn1("888888888");// 送达方
					}
				}
			}
		}
		// sap触发发货新增字段
		// 是否需要触发发货  Y:需要   N:不需要   默认都是Y  延保单以及所有的退单都不需要触发发货
		pOrderHeader.setDeliveryFlag("Y");
		// P单创建时间  HHmmss
		pOrderHeader.setPoEntryTime(poMaster.getPoEntryTime());
		// 计划发货日期  yyyyMMdd
		pOrderHeader.setDeliveryDate(poAddrDetail.getDeliveryPlanDate());
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
		pOrderHeader.setPoState(poMaster.getPoStatus());
	}

	/**
	 * 给parentMap赋值
	 * @param poDetailList
	 */
	private void getParentLineNum(List<PoDetail> poDetailList, Map<String, BigDecimal> parentMap) {
		for (PoDetail poDetail : poDetailList) {
			parentMap.put(poDetail.getProductCode(), poDetail.getLineNo()); // key:产品代码,value:行号
		}
	}

	static Map<String, String> SpecialProvinceMapping = new ConcurrentHashMap<String, String>();
	static {
		SpecialProvinceMapping.put("120117001", "130000");
		SpecialProvinceMapping.put("120117113", "130000");
		SpecialProvinceMapping.put("471004", "410000");
		SpecialProvinceMapping.put("471005", "410000");
	}

	/**
	 * 根据收货地址中的：poAddrDetail.收货地址省,查询出对应的省份编码
	 * @param addrAreaCode
	 * @param province 
	 * @return
	 */
	private String getSapRegion(String addrAreaCode, String province) {
		if (!StringUtils.contains(addrAreaCode, "999999") && !StringUtils.startsWith(addrAreaCode, "0") && StringUtils.length(addrAreaCode) > 2) {
			String dsCode = SpecialProvinceMapping.getOrDefault(addrAreaCode, addrAreaCode.substring(0, 2) + "0000");
			SapDsMapping sapDsMapping = sapDsMappingRepository.findByDsCode(dsCode);
			if (sapDsMapping != null) {
				return sapDsMapping.getSapCode();
			}
		}

		if (StringUtils.isNotEmpty(province)) {
			province = "%" + StringUtils.trim(province).substring(0, 2) + "%";
			List<String> codes = sapDsMappingRepository.findAsDsDescLike(province);
			if (CollectionUtils.isNotEmpty(codes)) {
				return codes.get(0);
			}
		}

		return "";
	}

	/**
	 * 增值税发票
	 * @param pOrderHeader
	 * @param info
	 */
	private void setInvoiceBillVat(POrderHeader pOrderHeader, PoInvoiceInfo info) {
		if (info == null) {
			return;
		}
		String invoiceType = info.getInvoiceType();
		if ("PP".equals(invoiceType)) {
			// 增值税专用发票
			if (info.getInvoiceTitle() != null)
				pOrderHeader.setField1(info.getInvoiceTitle()); // 发票抬头
			if (info.getContactTele() != null)
				pOrderHeader.setField2(info.getContactTele()); // 单位联系电话
			if (info.getBankName() != null)
				pOrderHeader.setField3(info.getBankName()); // 开户行名称
			if (info.getTaxPayerCode() != null)
				pOrderHeader.setField4(info.getTaxPayerCode()); // 纳税人识别号
			if (info.getBankAccount() != null)
				pOrderHeader.setField5(info.getBankAccount()); // 账号
			if (info.getInvoiceAddrTail() != null)
				pOrderHeader.setField6(info.getInvoiceAddrTail()); // 发票地址
		} else {
			// 电子发票或者普通纸质发票
			setInvoiceBill(pOrderHeader, info);
		}
	}

	/**
	 * 电子发票或者普通纸质发票
	 */
	private void setInvoiceBill(POrderHeader pOrderHeader, PoInvoiceInfo info) {
		if (info == null) {
			return;
		}
		if (info.getInvoiceTitle() != null)
			pOrderHeader.setField1(info.getInvoiceTitle()); // 发票抬头
		if (info.getInvoiceEmail() != null)
			pOrderHeader.setField2(info.getInvoiceEmail()); // 收票人邮箱
		if (info.getInvoiceMobile() != null)
			pOrderHeader.setField3(info.getInvoiceMobile()); // 收票人手机号
		if (info.getTaxPayerCode() != null)
			pOrderHeader.setField4(info.getTaxPayerCode()); // 纳税人识别号
	}

	/**
	 * 总公司普通单 传入订单主表
	 * @param poMaster
	 * @param pOrderHeader
	 */
	private void companyCommonBill(POrderHeader pOrderHeader, PoMaster poMaster, PoAddrDetail poAddrDetail, PoHeader poHeader) {

		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		pOrderHeader.setPosNumber("");// 订单单号

		// PoPriceAttr 定价属性
		// P--优惠价"//Y000000002(业务员)
		if (poMaster.getPoPriceAttr().equals("P")) {
			if (TypeSapProcessCode.G103.equals(poMaster.getPoProcessCode())) {
				pOrderHeader.setKonda("03");// 企业购 优惠价
			} else {
				pOrderHeader.setKonda("08");// 价格组
			}

		}
		// "R--零售价 L000000002(零售顾客)
		if (poMaster.getPoPriceAttr().equals("R")) {
			if (TypeSapProcessCode.G103.equals(poMaster.getPoProcessCode())) {
				pOrderHeader.setKonda("06");// 企业购 优惠价
			} else {
				pOrderHeader.setKonda("07");// 价格组
			}
		}

		if (TypeSapProcessCode.G013.equals(poMaster.getPoProcessCode())) {
			pOrderHeader.setDeliveryFlag("N");
		}

		// 是否直达易售单
		if (poMaster.getPoProcessCode().equals(TypeSapProcessCode.G108)) {
			pOrderHeader.setCardYst(poAddrDetail.getDeliveryDealerNo());
			if (poHeader.getDealerDeliveryFullname() != null) {
				pOrderHeader.setNameYst(poHeader.getDealerDeliveryFullname());
			}
		}
		// 普消购货
		if (StringUtils.isNotBlank(poMaster.getOrderCustomerNo())) {
			if (poHeader.getDealerCustomerMobile() != null) {
				pOrderHeader.setCardYst(poHeader.getDealerCustomerMobile()); // 易售通业务伙伴卡号 = 手机号码
				if (StringUtils.isNotBlank(poHeader.getDealerCustomerFullname())) {
					pOrderHeader.setNameYst(poHeader.getDealerCustomerFullname());
				}
			}
		}
	}

	/**
	 * 总公司预定／记欠单 传入订单主表
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
	}

	/**
	 * 总公司免费单 传入订单主表
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
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void serviceCenterCommonBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C2");// 分销渠道
		pOrderHeader.setSalesGrp(poMaster.getPoStoreNo());// 销售组
		pOrderHeader.setPosNumber(this.createSapPostingDocNo(poMaster));// 订单单号
		pOrderHeader.setOrdReason(this.getPaymentType(poMaster.getPoNo(), TypeSapOrderType.ZNF4, null));// 支付类型
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
		// TODO还有几种类型没搞：售达方
	}

	/**
	 * 服务中心免费单 传入订单主表
	 * @param pOrderHeader
	 * @param poMaster
	 */
	private void serviceCenterFreeBill(POrderHeader pOrderHeader, PoMaster poMaster) {
		pOrderHeader.setDistrChan("C2");
		pOrderHeader.setSalesGrp(poMaster.getPoStoreNo());
		pOrderHeader.setPosNumber(this.createSapPostingDocNo(poMaster));
		pOrderHeader.setOrdReason(this.getPaymentType(poMaster.getPoNo(), TypeSapOrderType.ZGFR, null));// 支付类型
		// P--优惠价"
		if (poMaster.getPoPriceAttr().equals("P")) {
			pOrderHeader.setKonda("02");
		}
		// "R--零售价
		if (poMaster.getPoPriceAttr().equals("R")) {
			pOrderHeader.setKonda("01");
		}
		if (poMaster.getOrderDealerNo().equals("000000000")) { // 零售的
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
		pOrderHeader.setFreightAmount(BigDecimal.ZERO);
		pOrderHeader.setKunn1Region("");
		pOrderHeader.setKunn1City("");
		pOrderHeader.setKunn1District("");
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
	 * @param pOrderHeader
	 * @param poMaster
	 * @param vo
	 */
	private void serviceCenterReturnBill(POrderHeader pOrderHeader, PoMaster poMaster, PoResultDetailVo vo) {
		pOrderHeader.setDistrChan("C2");// 分销渠道
		pOrderHeader.setSalesGrp(poMaster.getPoStoreNo());// 销售组
		if (poMaster.getRefPoNo() != null && !"".equals(poMaster.getRefPoNo())) {
			pOrderHeader.setPosNumber(poMaster.getSapPostingDocNo());// 订单单号
		}
		pOrderHeader.setOrdReason(this.getPaymentType(poMaster.getPoNo(), TypeSapOrderType.ZRE2, vo.getPosAccTranDetails()));// 支付类型

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

	/**
	 * 网上订单 服务中心退货 G306、G308单
	 * @param pOrderHeader
	 * @param poMaster
	 */
	public void serviceCenterReturnGbss(POrderHeader pOrderHeader, PoMaster poMaster) {
		String poProcessCode = poMaster.getPoProcessCode();
		pOrderHeader.setDistrChan("C1");// 分销渠道
		pOrderHeader.setSalesGrp("");// 销售组
		if (TypeSapProcessCode.G308.equals(poProcessCode)) {
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

		// 支付类型
		if (TypeSapProcessCode.G306.equals(poProcessCode) || TypeSapProcessCode.G105.equals(poProcessCode)
				|| TypeSapProcessCode.G014.equals(poProcessCode)) {
			pOrderHeader.setOrdReason("016");// 支付类型  016--非本自营店销售退货(有参考)
		} else if (TypeSapProcessCode.G308.equals(poProcessCode)) {
			pOrderHeader.setOrdReason("017");// 支付类型 017--非本自营店销售退货(无参考)
		} else {
			pOrderHeader.setOrdReason("");// 支付类型
		}
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
		String addrDetail = poAddrDetail.getAddrProvince() + poAddrDetail.getAddrCity() + poAddrDetail.getAddrCounty()
				+ poAddrDetail.getAddrDistrict() + poAddrDetail.getAddrDetail();
		pOrderHeader.setBeiZhu(poAddrDetail.getAddrAreaCode() + "^" + dealerName + "^" + addrDetail);
	}

	/**
	 * 总公司普通单 纷享荟二期 传入订单主表
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
	 * 全球购
	 * @param pOrderHeader
	 * @param poMaster
	 * @param poHeader
	 */
	private void gpOrderBill(POrderHeader pOrderHeader, PoMaster poMaster, PoHeader poHeader) {
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
			if (poHeader != null) {
				pOrderHeader.setCardYst(poHeader.getDealerCustomerMobile());
				if (StringUtils.isNotBlank(poHeader.getDealerCustomerFullname())) {
					pOrderHeader.setNameYst(poHeader.getDealerCustomerFullname()); // 易售通业务伙伴姓名 = 姓名
				}
			}
		}
	}

	/**
	 * 公共的一些 传入订单明细
	 *
	 * @param poDetail
	 * @param poAddrDetail
	 * @param
	 */
	private TOrderItems theSameTorderItems(PoDetail poDetail, PoAddrDetail poAddrDetail, PoMaster poMaster, Map<String, BigDecimal> parentMap,
			boolean isProm) {
		BigDecimal promQtyBigDecimal = poDetail.getPromQty(); // 活动优惠数量
		BigDecimal saleQtyBigDecimal = poDetail.getSaleQty().subtract(promQtyBigDecimal); // saleQtyBigDecimal = 订货产品数量 - 活动优惠数量
		BigDecimal saleQty = BigDecimal.ZERO;
		if (isProm) {
			if (promQtyBigDecimal.compareTo(BigDecimal.ZERO) == 0) { // 如果活动优惠数量为0,不继续执行
				return null;
			}
			saleQty = promQtyBigDecimal;
		} else {
			if (saleQtyBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
				return null;
			}
			saleQty = saleQtyBigDecimal;
		}

		// 订单明细
		TOrderItems tOrderItems = new TOrderItems();
		BigDecimal lineNo = poDetail.getLineNo(); // 行号

		log.debug("+++++" + lineNo);
		if (isProm) {
			lineNo = lineNo.add(BigDecimal.TEN);
		}
		tOrderItems.setItmNumber(lineNo.intValue() + "");// 销售订单行项目
		tOrderItems.setMaterial(poDetail.getProductCode());// 物料编码

		// 将GBSS中保存的负数转为正数
		saleQty = saleQty.abs();
		tOrderItems.setTargetQty(saleQty);// 订购数量

		BigDecimal salePrice = poDetail.getSalePrice() == null ? BigDecimal.ZERO : poDetail.getSalePrice();
		// prodectattr是1，价格传0
		if (poDetail.getProductAttr().equals("1")) {
			salePrice = BigDecimal.ZERO;
		}
		tOrderItems.setKwertPr00(salePrice);// 成交价

		//  服务中心单需要转工厂代码，非服务中心单直接传
		tOrderItems.setPlant(poAddrDetail.getDeliveryWhCode());

		// 2020/6/17 流程码换为产品类型比较
		if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
			tOrderItems.setStoreLoc("");// 仓码
		} else {
			tOrderItems.setStoreLoc(poAddrDetail.getDeliveryWhLocCode());// 仓码
		}

		if (poAddrDetail.getDeliveryPlanDate() != null) {
			// 2012-07-27，孔子南说都改成 写入 当前日期.
			Date currentTime = SystemUtils.getCurrentTime();
			tOrderItems.setReqDate(currentTime);// 交货日期
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
	 * 总公司普通单 传入订单明细
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
		} else {// 普通 同步业务平台代码 2020/09/14
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
				tOrderItems.setItemCateg("ZTAY"); // 由TANN改为ZTAY  2020/09/14
			} else {
				tOrderItems.setItemCateg("ZTAN");
			}
		}
	}

	/**
	 * 总公司免费单 传入订单明细
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
			} else { // 同步业务平台代码 2020/09/14
				if (ProductTypeEnum.V.getCode().equals(poDetail.getProductType())) {
					tOrderItems.setItemCateg("ZTAY");
				} else {
					tOrderItems.setItemCateg("ZTAN");
				}
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
				tOrderItems.setItemCateg("ZFK4");
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
				tOrderItems.setItemCateg("ZFK4");
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
			} else { // 同步业务平台代码 2020/09/14
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
			tCondition1.setItmNumber(poDetail.getLineNo().intValue() + "");// 申请单行项目
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
	 * 全球购定价
	 * @param poDetail
	 * @param tConditionList
	 */
	private void setGpTConditionListByPoDetail(PoDetail poDetail, List<TCondition> tConditionList, PoDetailDiscount poDetailDiscount) {
		// 单价:PR00
		TCondition tCondition1 = new TCondition();
		tCondition1.setItmNumber(poDetail.getLineNo().toString());// 申请单行项目
		tCondition1.setCondType("PR00");// 定价类型
		tCondition1.setCondValue(poDetail.getSalePrice());// 定价金额
		tCondition1.setCurrency("CNY");// 货币单位
		tConditionList.add(tCondition1);
		// 原点数:ZK05
		TCondition tCondition2 = new TCondition();
		tCondition2.setItmNumber(poDetail.getLineNo().toString());
		tCondition2.setCondType("ZK05");
		tCondition2.setCondValue(poDetail.getSalePoint());
		tCondition2.setCurrency("CNY");
		tConditionList.add(tCondition2);
		// 代扣代缴增值税:ZGT1
		TCondition tCondition3 = new TCondition();
		tCondition3.setItmNumber(poDetail.getLineNo().toString());
		tCondition3.setCondType("ZGT1");
		tCondition3.setCondValue(poDetail.getVatAmt());
		tCondition3.setCurrency("CNY");
		tConditionList.add(tCondition3);
		// 代扣代缴消费税:ZGT2
		TCondition tCondition4 = new TCondition();
		tCondition4.setItmNumber(poDetail.getLineNo().toString());
		tCondition4.setCondType("ZGT2");
		tCondition4.setCondValue(poDetail.getConsumTaxAmt());
		tCondition4.setCurrency("CNY");
		tConditionList.add(tCondition4);

		BigDecimal rebateDisAmtCoupon = BigDecimal.ZERO;//优差折扣金额（电子券分摊）
		BigDecimal rebateDisAmtWhole = BigDecimal.ZERO;//优差折扣金额（整单减分摊）
		BigDecimal companyDisAmtCoupon = BigDecimal.ZERO;//公司折扣金额（电子券分摊）
		BigDecimal companyDisAmtWhole = BigDecimal.ZERO;//公司折扣金额（整单减分摊）

		if (poDetailDiscount != null) {
			rebateDisAmtCoupon = poDetailDiscount.getRebateDisAmtCoupon();//优差折扣金额（电子券分摊）
			rebateDisAmtWhole = poDetailDiscount.getRebateDisAmtWhole();//优差折扣金额（整单减分摊）
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
					tCondition5.setCondValue(rebateDisAmtWhole);
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
					tCondition6.setCondValue(rebateDisAmtCoupon);
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
					tCondition7.setCondValue(companyDisAmtWhole);
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
					tCondition8.setCondValue(companyDisAmtCoupon);
					tCondition8.setCurrency("CNY");
					tConditionList.add(tCondition8);
				}
			}
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
				tCondition1.setItmNumber(lineNo.intValue() + "");// 申请单行项目
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
				tCondition2.setItmNumber(lineNo.intValue() + "");
				tCondition2.setCondType("ZK05");
				tCondition2.setCondValue(poDetail.getSalePoint());
				tCondition2.setCurrency("CNY");
				tConditionList.add(tCondition2);
				// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber(lineNo.intValue() + "");
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
					tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
				tCondition1.setItmNumber(lineNo.intValue() + "");// 申请单行项目
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
				tCondition2.setItmNumber(lineNo.intValue() + "");
				tCondition2.setCondType("ZK05");
				tCondition2.setCondValue(poDetail.getSalePoint());
				tCondition2.setCurrency("CNY");
				tConditionList.add(tCondition2);
				// 在订单接口中,行项目条件也要有ZK06,其数值等同于ZK05 2011.11.15 吴波
				TCondition tCondition3 = new TCondition();
				tCondition3.setItmNumber(lineNo.intValue() + "");
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
					tCondition2.setItmNumber(lineNo.intValue() + "");
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
					tCondition2.setItmNumber(lineNo.intValue() + "");
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
					tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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
						tCondition2.setItmNumber(lineNo.intValue() + "");
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

	/**
	 * 定价列表根据PosAccTranDetail加入剩下的定价类型
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

	private void setTConditionListWithZKBM(PoMaster poMaster, List<TCondition> tConditionList) {
		if (poMaster != null && poMaster.getTotalSaleDiscountAmt() != null && poMaster.getTotalSaleDiscountAmt().compareTo(BigDecimal.ZERO) > 0) {
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
	//	private void setTConditionListWithZKWX(PoMaster poMaster, List<TCondition> tConditionList) {
	//		BigDecimal tempTotalSaleDiscountAmt = BigDecimal.ZERO;
	//		if (CollectionUtils.isNotEmpty(tConditionList)) {
	//			for (TCondition tCondition : tConditionList) {
	//				if ("ZK62".equals(tCondition.getCondType()) || "ZK72".equals(tCondition.getCondType()) || "ZKWX".equals(tCondition.getCondType())) {
	//					tempTotalSaleDiscountAmt = tempTotalSaleDiscountAmt.add(tCondition.getCondValue());
	//				}
	//			}
	//		}
	//		BigDecimal totalSaleDiscountAmt = poMaster.getTotalSaleDiscountAmt();
	//		totalSaleDiscountAmt = totalSaleDiscountAmt.subtract(tempTotalSaleDiscountAmt);
	//
	//		if (totalSaleDiscountAmt.compareTo(BigDecimal.ZERO) != 0) {
	//			// 折扣:ZKWX
	//			TCondition tCondition = new TCondition();
	//			tCondition.setItmNumber("00");
	//			tCondition.setCondType("ZKWX");
	//			tCondition.setCondValue(totalSaleDiscountAmt);
	//			tCondition.setCurrency("CNY");
	//			tConditionList.add(tCondition);
	//		}
	//	}

	/**
	 * 服务中心生成sap订单号
	 */
	private String createSapPostingDocNo(PoMaster poMaster) {
		if (poMaster.getSapPostingDocNo() != null && !poMaster.getSapPostingDocNo().equals("")) {
			return poMaster.getSapPostingDocNo();
		}
		throw new OMSException("createSapPostingDocNo error");
	}

	/**
	 * 服务中心订单号支付类型
	 * @param list
	 */
	private String getPaymentType(String poNo, int type, List<PosAccTranDetail> list) {
		String reVlalue = "001";
		if (poNo != null && !poNo.equals("")) {
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
	 * 初始化赋值:订货流程代码和订单类型的转换
	 * 这些数值基本是固定的,不同的类别,对应某一个相同的数值,在if判断时,不需要再写一大串的东西了,直接通过"订货流程代码"这个key来获取"订单类型"
	 * zjl
	 */
	private void initPoProcessCodeMap() {
		// 总公司普通单:0
		poProcessCodeMap.put(TypeSapProcessCode.G001, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G002, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G003, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G004, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G006, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G008, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G013, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G017, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G020, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G101, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G103, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G108, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G407, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G402, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G010, TypeSapOrderType.ZNF3);
		poProcessCodeMap.put(TypeSapProcessCode.G403, TypeSapOrderType.ZNF3);

		// 总公司预定／记欠单:1
		poProcessCodeMap.put(TypeSapProcessCode.G005, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G007, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G102, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G104, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G107, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G022, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G023, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G404, TypeSapOrderType.ZYD1);
		poProcessCodeMap.put(TypeSapProcessCode.G410, TypeSapOrderType.ZYD1);

		// 总公司免费单:2
		poProcessCodeMap.put(TypeSapProcessCode.G011, TypeSapOrderType.ZFRE);
		poProcessCodeMap.put(TypeSapProcessCode.G901, TypeSapOrderType.ZFRE);
		poProcessCodeMap.put(TypeSapProcessCode.G910, TypeSapOrderType.ZFRE);

		// 服务中心普通单:3
		poProcessCodeMap.put(TypeSapProcessCode.G301, TypeSapOrderType.ZNF4);
		poProcessCodeMap.put(TypeSapProcessCode.G302, TypeSapOrderType.ZNF4);
		poProcessCodeMap.put(TypeSapProcessCode.G303, TypeSapOrderType.ZNF4);
		poProcessCodeMap.put(TypeSapProcessCode.G304, TypeSapOrderType.ZNF4);
		poProcessCodeMap.put(TypeSapProcessCode.G310, TypeSapOrderType.ZNF4);

		// 服务中心免费单:5
		poProcessCodeMap.put(TypeSapProcessCode.G311, TypeSapOrderType.ZGFR);
		poProcessCodeMap.put(TypeSapProcessCode.G902, TypeSapOrderType.ZGFR);

		// 服务中心退货单:6
		poProcessCodeMap.put(TypeSapProcessCode.G305, TypeSapOrderType.ZRE2);

		// 总公司 月刊单:7
		poProcessCodeMap.put(TypeSapProcessCode.G909, TypeSapOrderType.ZNF6);

		// G306退货单:8
		poProcessCodeMap.put(TypeSapProcessCode.G014, TypeSapOrderType.ZRE7);
		poProcessCodeMap.put(TypeSapProcessCode.G105, TypeSapOrderType.ZRE7);
		poProcessCodeMap.put(TypeSapProcessCode.G306, TypeSapOrderType.ZRE7);

		// G308服务中心退货(店铺销售):9
		poProcessCodeMap.put(TypeSapProcessCode.G308, TypeSapOrderType.ZRE8);

		// G501专卖店退货，因怡瑞召回使用的:10
		poProcessCodeMap.put(TypeSapProcessCode.G501, TypeSapOrderType.ZREA);

		// G012纷享花绘订单:11
		poProcessCodeMap.put(TypeSapProcessCode.G012, TypeSapOrderType.ZRE1);

		// G904质量补损:12
		poProcessCodeMap.put(TypeSapProcessCode.G904, TypeSapOrderType.ZFD2);

		// G905一般补损退货:13
		poProcessCodeMap.put(TypeSapProcessCode.G905, TypeSapOrderType.ZRE4);

		// G908无参照质量补损退货:14
		poProcessCodeMap.put(TypeSapProcessCode.G906, TypeSapOrderType.ZRE6);
		poProcessCodeMap.put(TypeSapProcessCode.G908, TypeSapOrderType.ZRE6);

		// G018全球购:15
		poProcessCodeMap.put(TypeSapProcessCode.G018, TypeSapOrderType.ZNF8);
		poProcessCodeMap.put(TypeSapProcessCode.G019, TypeSapOrderType.ZNF8);
	}

}

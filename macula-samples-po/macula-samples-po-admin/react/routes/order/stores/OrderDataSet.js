export default function () {
  return {
    autoQuery: true,
    selection: false,
    pageSize: 20,
    dataKey: 'content',
    totalKey: 'totalElements',
    transport: {
      read: {
        url: 'order/v1/orders',
        method: 'get',
      },
    },

    fields: [
      {name: 'operation', label: '操作', key: 'operation'},
      {name: 'poNo', label: '订单号', key: 'poNo'},
      {name: 'poProcessCode', label: '流程代码', key: 'poProcessCode'},
      {name: 'poType', label: '销售单类型', key: 'poType'},
      {name: 'poPeriod', label: '销售月份', key: 'poPeriod'},
      {name: 'poDate', label: '销售日期', key: 'poDate'},
      {name: 'poStoreNo', label: '销售店铺', key: 'poStoreNo'},
      {name: 'poBranchNo', label: '销售分公司', key: 'poBranchNo'},
      {name: 'poRegionNo', label: '销售大区代号', key: 'poRegionNo'},
      {name: 'poStatus', label: '状态', key: 'poStatus'},
      {name: 'orderDealerNo', label: '购货人卡号', key: 'orderDealerNo'},
      {name: 'orderDealerName', label: '购货人姓名', key: 'orderDealerName'},
      {name: 'orderType', label: '购货类型', key: 'orderType'},
      {name: 'refPoNo', label: '参考源订货单号', key: 'refPoNo'},
      {name: 'refPoPeriod', label: '参考源订货单月份', key: 'refPoPeriod'},
      {name: 'refPoDate', label: '参考源订货单日期', key: 'refPoDate'},
      {name: 'totalSaleAmt', label: '整单订货总金额', key: 'totalSaleAmt'},
      {name: 'totalSaleProductAmt', label: '整单产品金额', key: 'totalSaleProductAmt'},
      {name: 'totalSaleNonProductAmt', label: '整单非产品金额', key: 'totalSaleNonProductAmt'},
      {name: 'totalSaleDiscountAmt', label: '整单销售折让', key: 'totalSaleDiscountAmt'},
      {name: 'totalSaleCouponAmt', label: '整单使用优惠券', key: 'totalSaleCouponAmt'},

      {name: 'totalSaleNetAmt', label: '整单订货净金额', key: 'totalSaleNetAmt'},
      {name: 'totalTransportAmt', label: '订单实际运费', key: 'totalTransportAmt'},
      {name: 'totalCalcPoint', label: '计分合计总点数', key: 'totalCalcPoint'},
      {name: 'totalCalcDiscountPoint', label: '计分合计折扣点数', key: 'totalCalcDiscountPoint'},
      {name: 'totalCalcRebate', label: '计分合计总优差', key: 'totalCalcRebate'},
      {name: 'poEntryClass', label: '销售办理来源', key: 'poEntryClass'},
      {name: 'poEntryDealerNo', label: '销售办代办人', key: 'poEntryDealerNo'},
      {name: 'poEntryTime', label: '销售单据输入时间', key: 'poEntryTime'},

      {name: 'poEntryBy', label: '销售单据输入用户', key: 'poEntryBy'},
      {name: 'poEntryMemo', label: '销售单据输入备注', key: 'poEntryMemo'},
      {name: 'poAppNo', label: '销售订货申请单号', key: 'poAppNo'},
      {name: 'poLclNo', label: '销售辅单申请单号', key: 'poLclNo'},
      {name: 'poGroupNo', label: '团购单号', key: 'poGroupNo'},
      {name: 'refSelectedNo', label: '参考单据号', key: 'refSelectedNo'},
      {name: 'poPromFlag', label: '销售促销标示', key: 'poPromFlag'},
      {name: 'poWholePromCode', label: '整单促销代码', key: 'poWholePromCode'},
      {name: 'poPriceGroupType', label: '定价价格组', key: 'poPriceGroupType'},
      {name: 'poPriceAttr', label: '定价属性', key: 'poPriceAttr'},
      {name: 'poReturnRestrictFlag', label: '退货约束标志', key: 'poReturnRestrictFlag'},
      {name: 'paymentTotalAmt', label: '支付金额', key: 'paymentTotalAmt'},

      {name: 'paymentStatus', label: '付款状态', key: 'paymentStatus'},
      {name: 'paymentTime', label: '付款完成时间', key: 'paymentTime'},
      {name: 'paymentDocNo', label: '支付文件序号', key: 'paymentDocNo'},
      {name: 'paymentMemo', label: '支付备注', key: 'paymentMemo'},
      {name: 'sapPostingFlag', label: 'SAP标志', key: 'sapPostingFlag'},
      {name: 'sapPostingDate', label: 'SAP日期', key: 'sapPostingDate'},
      {name: 'sapPostingDocNo', label: '销售订单号', key: 'sapPostingDocNo'},/*po_master中叫做SAP凭证，此处方便业务人员*/
      {name: 'comments', label: '备注', key: 'comments'},
      {name: 'lastModifiedDate', label: '最后更新时间', key: 'lastModifiedDate'},
      {name: 'lastModifiedBy', label: '最后更新人', key: 'lastModifiedBy'},
      {name: 'poOweNo', label: '记欠申请号', key: 'poOweNo'},
      {name: 'orderDealerBranchNo', label: '购货人对应的分公司', key: 'orderDealerBranchNo'},
      {name: 'orderDesc', label: '订单摘要', key: 'orderDesc'},
      {name: 'companyNo', label: '所属公司编码', key: 'companyNo'},
      {name: 'profitCenterNo', label: '利润中心编号', key: 'profitCenterNo'},
      {name: 'orderCustomerNo', label: '销售顾客号', key: 'orderCustomerNo'},
      {name: 'totalWeight', label: '订单总重量', key: 'totalWeight'},
      {name: 'totalCalcRebateDs', label: '计分合计直销产品优差', key: 'totalCalcRebateDs'},
      {name: 'totalVatAmt', label: '订单增值税总金额', key: 'totalVatAmt'},
      {name: 'totalConsumTaxAmt', label: '订单消费税总金额', key: 'totalConsumTaxAmt'},

      {name: 'totalCompreTaxAmt', label: '订单综合税总金额', key: 'totalCompreTaxAmt'},
      {name: 'totalRebateDisAmt', label: '总优差折让金额', key: 'totalRebateDisAmt'},
      {name: 'totalTransportCouponAmt', label: '运费优惠金额', key: 'totalTransportCouponAmt'},
      {name: 'poEntryStoreNo', label: '销售代办店铺', key: 'poEntryStoreNo'},
      {name: 'status', label: '订单处理状态', key: 'status'},
      {name: 'companyName', label: '公司名称', key: 'companyName'},

    ],
    queryFields: [
      {name: 'poNo', label: '订单号'},
      {name: 'sapPostingDocNo', label: '销售订单号'},
      {name: 'poProcessCode', label: '流程代码'},
      {name: 'poDate', label: '销售日期'}
    ],

  };
}

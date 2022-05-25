export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/detail_discount/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'poNo', label: '订货单号' },
      { name: 'lineNo', label: '本地行号' },
      { name: 'productCode', label: '产品代码' },
      { name: 'productAttr', label: '产品属性' },
      { name: 'rebateDisAmtProduct', label: '优差折扣金额（单品）' },
      { name: 'rebateDisAmtCoupon', label: '优差折扣金额（电子券分摊）' },
      { name: 'rebateDisAmtWhole', label: '优差折扣金额（整单减分摊）' },
      { name: 'companyDisAmtProduct', label: '公司折扣金额（单品）' },
      { name: 'companyDisAmtCoupon', label: '公司折扣金额（电子券分摊）' },
      { name: 'companyDisAmtWhole', label: '公司折扣金额（整单减分摊）' },
      { name: 'comments', label: '备注说明' },

    ], /* ,
    queryFields: [

    ] */

  };
}

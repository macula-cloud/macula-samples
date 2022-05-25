export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/orderDetail/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'poNo', label: '订货单号' },
      { name: 'lineNo', label: '行号' },
      { name: 'productCode', label: '产品代码' },
      { name: 'productType', label: '产品类型' },
      { name: 'productAttr', label: '产品属性' },
      { name: 'saleQty', label: '订货产品数量' },
      { name: 'salePrice', label: '购买单价' },
      { name: 'saleRebate', label: '购买优差' },
      { name: 'salePoint', label: '购买点数' },

      { name: 'productPrice', label: '购买原优惠价' },
      { name: 'productPoint', label: '产品原点数' },
      { name: 'productBomCode', label: '产品BOM代码' },
      { name: 'promCode', label: '促销代码' },
      { name: 'promProductAttr', label: '促销产品属性' },
      { name: 'comments', label: '备注' },
      { name: 'lastModifiedDate', label: '最后更新时间' },
      { name: 'lastModifiedBy', label: '最后更新用户' },
      { name: 'productUnitWeight', label: '单位产品重量' },
      { name: 'isDirectSale', label: '是否直销产品' },
      { name: 'vatRate', label: '产品增值税率' },
      { name: 'consumTaxRate', label: '产品消费税率' },

      { name: 'compreTaxRate', label: '产品综合税率' },
      { name: 'transportAmt', label: '产品运费总金额' },
      { name: 'vatAmt', label: '产品增值税金额' },
      { name: 'consumTaxAmt', label: '产品消费税金额' },
      { name: 'compreTaxAmt', label: '产品综合税金额' },
      { name: 'promPrice', label: '活动优惠价格' },
      { name: 'promPoint', label: '活动优惠点数' },
      { name: 'promQty', label: '活动优惠数量' },
      { name: 'promRebate', label: '活动单位优差' },

    ], /* ,
    queryFields: [

    ] */

  };
}

export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/mkp_dlp_po_relate/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'poNo', label: '销售单号' },
      { name: 'storeNo', label: '店铺编号' },
      { name: 'dealerNo', label: '会员卡号' },
      { name: 'deliveryType', label: '配送方式' },
      { name: 'cashAmt', label: '现金支付总金额' },
      { name: 'totalDlpAmt', label: '积分金额' },
      { name: 'totalDiscountAmt', label: '积分折扣金额' },
      { name: 'transportAmt', label: '运费金额' },
      { name: 'comments', label: '备注' },

    ],

  };
}

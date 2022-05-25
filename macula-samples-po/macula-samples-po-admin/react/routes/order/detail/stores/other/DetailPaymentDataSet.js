export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/detail_payment/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'poNo', label: '订货单号' },
      { name: 'poPaymentType', label: '支付类型' },
      { name: 'poPaymentAmt', label: '支付金额' },
      { name: 'poPaymentDealerNo', label: '支付卡号' },
      { name: 'accTranType', label: '账户处理类型' },
      { name: 'epayAgentCode', label: '在线支付交易方式' },
      { name: 'epayCardNo', label: '在线支付卡号' },
      { name: 'discountPoint', label: '订单抵扣点数' },
      { name: 'refTranInfo', label: '相关记录信息' },
      { name: 'comments', label: '备注' },

    ],

  };
}

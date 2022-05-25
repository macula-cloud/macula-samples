export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/detail_acc_tran/${poNo}`,
        method: 'get',
      },
    },

    fields: [

      { name: 'posNo', label: 'POS机号' },
      { name: 'posStoreNo', label: 'POS自营店号' },
      { name: 'posUserName', label: 'POS收银员' },
      { name: 'posTranType', label: 'POS交易项目' },
      { name: 'posTranAmt', label: 'POS交易金额' },
      { name: 'posPaymentType', label: 'POS支付类型' },
      { name: 'posTranStatus', label: 'POS交易状态' },
      { name: 'posTranDate', label: 'POS交易日期' },
      { name: 'refDocNo', label: '参考单号' },
      { name: 'posAckBy', label: 'POS交易确认人' },
      { name: 'sapPostingFlag', label: 'SAP记账标志' },
      { name: 'sapPostingDate', label: 'SAP记账日期' },
      { name: 'sapPostingDocNo', label: 'SAP记账凭证' },
      { name: 'posAckTime', label: 'POS交易确认时间' },
      { name: 'comments', label: '备注' },

    ],
  };
}

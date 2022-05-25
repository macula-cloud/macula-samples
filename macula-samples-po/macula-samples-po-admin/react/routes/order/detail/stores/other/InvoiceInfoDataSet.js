export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/detail_invoice_info/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'poAppNo', label: '订货申请单号' },
      { name: 'dealerNo', label: '卡号' },
      { name: 'invoiceType', label: '发票类型' },
      { name: 'invoiceTitle', label: '发票抬头' },

      { name: 'invoiceMobile', label: '收票人手机号' },
      { name: 'invoiceEmail', label: '收票人邮箱' },
      { name: 'invoicePhotoId', label: '电子发票图片ID' },
      { name: 'taxPayerCode', label: '纳税人识别号' },
      { name: 'recipientName', label: '收件人姓名' },
      { name: 'recipientAreaCode', label: '收件人地区编号' },
      { name: 'recipientAddrTail', label: '收件人详细地址' },
      { name: 'recipientTele', label: '收件人联系电话' },
      { name: 'invoiceAddrTail', label: '发票地址' },

      { name: 'bankName', label: '开户行名称' },
      { name: 'bankAccount', label: '账号' },
      { name: 'contactTele', label: '单位联系电话' },
      { name: 'invoiceStatus', label: '发票状态' },
      { name: 'invoiceAppCount', label: '发票申请次数' },

    ],
  };
}

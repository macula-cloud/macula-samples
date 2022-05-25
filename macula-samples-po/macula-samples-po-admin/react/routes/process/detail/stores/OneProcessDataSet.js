export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/process_master/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { label: '订单号', name: 'poNo', key: 'poNo', readOnly: true },
      { label: '流程号', name: 'poProcessCode', key: 'poProcessCode', readOnly: true },
      { label: '来源ID', name: 'refSourceId', key: 'refSourceId', readOnly: true },
      { label: '来源号', name: 'refSourceNo', key: 'refSourceNo', readOnly: true },
      { label: 'PO来源', name: 'poSource', key: 'poSource', readOnly: true },
      { label: '状态', name: 'status', key: 'status', readOnly: true },
      { label: '状态时间', name: 'statusTime', key: 'statusTime', readOnly: true },
      { label: '失败次数', name: 'errorTimes', key: 'errorTimes', readOnly: true },
      { label: '销售订单号', name: 'salesDocument', key: 'salesDocument', readOnly: true },
      { label: 'MQ-ID', name: 'sendMessageId', key: 'sendMessageId', readOnly: true },
      { label: '备注', name: 'comments', key: 'comments', readOnly: true },
      { label: '创建人', name: 'createdBy', key: 'createdBy', readOnly: true },
      { label: '创建时间', name: 'createdDate', key: 'createdDate', readOnly: true },
      { label: '最后更新人', name: 'lastModifiedBy', key: 'lastModifiedBy', readOnly: true },
      { label: '最后更新时间', name: 'lastModifiedDate', key: 'lastModifiedDate', readOnly: true },
      { label: '版本', name: 'version', key: 'version', readOnly: true },
    ],

  };
}

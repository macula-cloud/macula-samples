export default function () {
  return {
    autoQuery: true,
    selection: false,
    pagination: {showQuickJumper: true},
    pageSize: 20,
    dataKey: 'content',
    totalKey: 'totalElements',
    transport: {
      read: {
        url: 'order/v1/orders/process_list',
        method: 'get',

      },
    },

    fields: [
      {label: '操作', name: 'operation', key: 'operation'},
      {label: '订单号', name: 'poNo', key: 'poNo'},
      {label: '流程号', name: 'poProcessCode', key: 'poProcessCode'},
      {label: '来源ID', name: 'refSourceId', key: 'refSourceId'},
      {label: '来源MQ', name: 'refSourceNo', key: 'refSourceNo'},
      {label: '来源', name: 'poSource', key: 'poSource'},
      {label: '处理状态', name: 'status', key: 'status'},
      {label: '状态时间', name: 'statusTime', key: 'statusTime'},
      {label: '失败次数', name: 'errorTimes', key: 'errorTimes'},
      {label: '销售订单号', name: 'salesDocument', key: 'salesDocument'},/*SAP单号*/
      {label: 'MQ-ID', name: 'sendMessageId', key: 'sendMessageId'},
      {label: '备注', name: 'comments', key: 'comments'},
      {label: '创建人', name: 'createdBy', key: 'createdBy'},
      {label: '创建时间', name: 'createdDate', key: 'createdDate'},
      {label: '最后更新人', name: 'lastModifiedBy', key: 'lastModifiedBy'},
      {label: '最后更新时间', name: 'lastModifiedDate', key: 'lastModifiedDate'},
      {label: '版本', name: 'version', key: 'version'},
      {label: '处理时间', name: 'dValue', key: 'dValue'},
    ],
    queryFields: [
      {label: '订单号', name: 'poNo'},
      {label: '销售订单号', name: 'salesDocument'},
      {label: '流程号', name: 'poProcessCode'}

    ],
  };
}

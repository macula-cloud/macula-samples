export default function ({ poNo, processDetailDataSet }) {
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
      { label: '订单号', name: 'poNo', key: 'poNo' },
      { label: '流程号', name: 'poProcessCode', key: 'poProcessCode' },
      { label: '来源id', name: 'refSourceId', key: 'refSourceId' },
      { label: '来源号', name: 'refSourceNo', key: 'refSourceNo' },
      { label: 'PO来源', name: 'poSource', key: 'poSource' },
      { label: '状态', name: 'status', key: 'status' },
      { label: '状态时间', name: 'statusTime', key: 'statusTime' },
      { label: '失败次数', name: 'errorTimes', key: 'errorTimes' },
      { label: 'SAP订单号', name: 'salesDocument', key: 'salesDocument' },
      { label: 'MQ返回SAP单号发送消息ID', name: 'sendMessageId', key: 'sendMessageId' },
      { label: '备注', name: 'comments', key: 'comments' },
      { label: '创建人', name: 'createdBy', key: 'createdBy' },
      { label: '创建时间', name: 'createdTime', key: 'createdTime' },
      { label: '最后更新人', name: 'lastUpdatedBy', key: 'lastUpdatedBy' },
      { label: '最后更新时间', name: 'lastUpdatedTime', key: 'lastUpdatedTime' },
      { label: '版本', name: 'objectVersionNumber', key: 'objectVersionNumber' },
    ],
    queryFields: [
      { label: '订单号', name: 'poNo' },
      { label: '状态', name: 'status' },
      { label: 'SAP订单号', name: 'salesDocument' },
    ], /* ,
    children: {

      detail: processDetailDataSet
    } */
  };
}

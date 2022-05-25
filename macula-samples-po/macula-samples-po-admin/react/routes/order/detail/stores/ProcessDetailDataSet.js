export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/orders/process_detail/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { label: '订单号', name: 'poNo', key: 'poNo' },
      { label: '处理状态', name: 'procState', key: 'procState' },
      { label: '状态时间', name: 'procTime', key: 'procTime' },
      { label: '备注', name: 'comments', key: 'comments' },
      { label: '创建人', name: 'createdBy', key: 'createdBy' },
      { label: '创建时间', name: 'createdTime', key: 'createdTime' },
      { label: '最后更新人', name: 'lastUpdatedBy', key: 'lastUpdatedBy' },
      { label: '最后更新时间', name: 'lastUpdatedTime', key: 'lastUpdatedTime' },
      { label: '版本', name: 'objectVersionNumber', key: 'objectVersionNumber' },

    ],
    queryFields: [
      { label: '订单号', name: 'poNo' },
    ],

  };
}

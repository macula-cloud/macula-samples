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
      { label: '状态', name: 'procState', key: 'procState' },
      { label: '状态时间', name: 'procTime', key: 'procTime' },
      { label: '备注', name: 'comments', key: 'comments' },
      { label: '创建人', name: 'createdBy', key: 'createdBy' },
      { label: '创建时间', name: 'createdDate', key: 'createdDate' },
      { label: '最后更新人', name: 'lastModifiedBy', key: 'lastModifiedBy' },
      { label: '最后更新时间', name: 'lastModifiedDate', key: 'lastModifiedDate' },
      { label: '版本', name: 'version', key: 'version' },

    ],

  };
}

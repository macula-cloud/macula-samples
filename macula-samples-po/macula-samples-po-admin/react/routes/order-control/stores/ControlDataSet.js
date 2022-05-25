export default function () {

  return {
    autoQuery: true,
    selection: false,
    paging: false,
    dataKey: 'content',
    totalKey: 'totalElements',
    transport: {
      read: {
        url: 'order/v1/order_control/all',
        method: 'get',
      },
    },
    fields: [
      {name: 'id', label: '主键'},
      {name: 'paraSystem', label: '参数所属系统'},
      {name: 'paraCode', label: '参数代码'},
      {name: 'paraDefault', label: '参数默认值'},
      {name: 'paraDesc', label: '参数说明'},
      {name: 'paraScope', label: '参数范围'},
      {name: 'paraType', label: '参数类型'},
      {name: 'paraValue', label: '参数值'},
      {name: 'comments', label: '备注'},
      {name: 'operation'},

    ],

  };
}

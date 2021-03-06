export default function () {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    dataKey: 'content',
    totalKey: 'totalElements',
    transport: {
      read: {
        url: '/stock/v1/goods/all',
        method: 'get',
      },
      create: ({data: [data]}) => ({
        url: `/stock/v1/goods/add`,
        method: 'post',
        data,
      }),
    },
    fields: [
      {name: 'id', label: '编号'},
      {name: 'productCode', label: '产品代码'},
      {name: 'sourceCode', label: '原仓'},
      {name: 'deliveryType', label: '提货方式'},
      {name: 'stockCode', label: '目标仓'},
      {name: 'dc0', label: '履行仓'},
      {name: 'dc1', label: '履行仓1'},
      {name: 'dc2', label: '履行仓2'},
      {name: 'dc3', label: '履行仓3'},
      {name: 'dc4', label: '履行仓4'},
      {name: 'dc5', label: '履行仓5'},
      {name: 'dc6', label: '履行仓6'},
      {name: 'dc7', label: '履行仓7'},
      {name: 'dc8', label: '履行仓8'},
      {name: 'dc9', label: '履行仓9'},
      {name: 'active', label: '是否激活'},
      {name: 'effectiveDate', label: '生效时间', type: 'date'},
      {name: 'inactiveDate', label: '失效时间', type: 'date'},
      {name: 'createdBy', label: '创建人'},
      {name: 'createdTime', label: '创建时间', type: 'date'},
      {name: 'lastUpdatedBy', label: '最后更新人'},
      {name: 'lastUpdatedTime', label: '最后更新时间', type: 'date'},
      {name: 'dcX', label: '更多履行仓', type: 'string'},
      {name: 'dcSetting', label: '履行仓配置', type: 'string'},
    ],
  };
}

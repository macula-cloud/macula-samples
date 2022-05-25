export default function ({ poNo }) {
  return {
    autoQuery: true,
    selection: false,
    transport: {
      read: {
        url: `order/v1/orders/mc_service_invoke_log/${poNo}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'dataKey', label: '业务数据值' },
      { name: 'description', label: '描述' },
      { name: 'source', label: '接口调用源' },
      { name: 'sourceMethod', label: '接口调用方法' },
      { name: 'sourceMessage', label: '接口调用信息' },
      { name: 'sourceTimestamp', label: '接口调用时间' },
      { name: 'target', label: '目标系统' },
      { name: 'targetMethod', label: '目标方法' },
      { name: 'targetMessage', label: '返回信息' },
      { name: 'targetTimestamp', label: '返回时间' },
      { name: 'node', label: '执行机器节点' },
      { name: 'status', label: '接口调用状态' },
      { name: 'exceptionMessage', label: '异常信息' },
      { name: 'transactionId', label: '相关线程号' },
      { name: 'comments', label: '备注' },
      { name: 'createdDate', label: '创建时间' },
      { name: 'createdBy', label: '创建人' },
      { name: 'lastModifiedDate', label: '最后更新时间' },
      { name: 'lastModifiedBy', label: '最后更新人' },
      { name: 'version', label: '版本信息' },

    ],

  };
}

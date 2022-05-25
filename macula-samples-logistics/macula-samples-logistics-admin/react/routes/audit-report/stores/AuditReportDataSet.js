import { DataSet } from 'choerodon-ui/pro';

export default function ({ supplierDataSet, supplierUserDataSet }) {
  const statusOptionDs = new DataSet({
    data: [
      { meaning: '已推送', value: true },
      { meaning: '未推送', value: false },
    ],
  });
  return {
    autoQuery: true,
    selection: false,
    transport: {
      read: {
        url: '/scc/v1/audit_reports',
        method: 'get',
      },
      create: ({ data: [data] }) => ({
        url: '/scc/v1/audit_reports',
        method: 'post',
        data: {
          ...data,
          supplierId: supplierDataSet.selected[0]?.get('id') || data.supplierId,
          userIdList: supplierUserDataSet.selected.map(user => user.get('id')),
        },
      }),
      update: ({ data: [data] }) => ({
        url: '/scc/v1/audit_reports',
        method: 'put',
        data: {
          ...data,
          supplierId: supplierDataSet.selected[0]?.get('id') || data.supplierId,
          userIdList: supplierUserDataSet.selected.map(user => user.get('id')),
        },
      }),
    },

    fields: [
      { name: 'fileName', label: '文件名' },
      { name: 'supplierName', label: '供应商' },
      { name: 'supplierId', label: '选择供应商' },
      { name: 'userIdList', label: '选择用户' },
      { name: 'uploadTime', label: '上传时间' },
      { name: 'realName', label: '上传人' },
      { name: 'pushTime', label: '推送时间' },
      { name: 'status', label: '状态', options: statusOptionDs },
    ],
    queryFields: [
      { name: 'fileName', label: '文件名' },
      { name: 'supplierName', label: '供应商' },
      { name: 'status', label: '状态', options: statusOptionDs },
    ],
  };
}

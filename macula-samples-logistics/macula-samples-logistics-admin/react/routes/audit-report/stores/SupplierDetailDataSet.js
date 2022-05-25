import { DataSet } from 'choerodon-ui/pro';

export default function ({ supplierDataSet }) {
  const enableOptionDs = new DataSet({
    data: [
      { meaning: '启用', value: true },
      { meaning: '停用', value: false },
    ],
  });
  return {
    autoQuery: false,
    selection: false,
    paging: false,
    transport: {
      read: ({ data: { supplierId } }) => ({
        url: `/supplier/v1/suppliers/${supplierDataSet.selected[0]?.get('id') || supplierId}`,
        method: 'get',
      }),
    },

    fields: [
      { name: 'organizationName', label: '供应商名称' },
      { name: 'organizationCode', label: '供应商编码' },
      { name: 'organizationType', label: '供应商类型' },
      { name: 'sourceSystemFlag', label: '源系统标识' },
      { name: 'supplierAddress', label: '地址' },
      { name: 'supplierPostalCode', label: '邮政编码' },
      { name: 'enabled', label: '状态', options: enableOptionDs },
    ],
  };
}

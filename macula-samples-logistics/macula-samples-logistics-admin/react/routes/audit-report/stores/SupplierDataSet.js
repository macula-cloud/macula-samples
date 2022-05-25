
export default function () {
  return {
    autoQuery: true,
    selection: 'single',
    transport: {
      read: {
        url: '/supplier/v1/suppliers',
        method: 'get',
      },
    },

    fields: [
      { name: 'organizationName', label: '供应商名称' },
      { name: 'organizationCode', label: '供应商编码' },
      { name: 'organizationType', label: '供应商类型' },
    ],
    queryFields: [
      { name: 'name', label: '名称' },
    ],
  };
}

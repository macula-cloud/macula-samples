export default function ({ id }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/checking/po_check_detail/${id}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'operation', label: '操作' },
      { name: 'checkMasterId', label: '关联ID' },
      { name: 'gbssPoNo', label: 'GBSS/OMS(单号)' },
      { name: 'gbssPoAmount', label: 'GBSS/OMS(金额)' },
      { name: 'gbssPoStatus', label: 'GBSS/OMS(单据状态)' },
      { name: 'gbssSapDocNo', label: 'GBSS/OMS(SAP单号)' },
      // {name: 'omsPoNo', label: 'OMS单号'},
      // {name: 'omsSapDocNo', label: 'OMS(SAP)单号'},
      // {name: 'omsPoStatus', label: 'OMS单据状态'},
      { name: 'synStatus', label: '状态' },
      { name: 'createdDate', label: '创建时间' },
      { name: 'lastModifiedDate', label: '更新时间' },

    ],
  };
}

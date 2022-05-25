export default function ({ id }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `order/v1/checking/po_check_master/find_one/${id}`,
        method: 'get',
      },
    },

    fields: [
      { name: 'id', label: '序号', readOnly: true },
      { name: 'gbssPoCount', label: '业务平台订单总量', readOnly: true },
      { name: 'gbssPoAmount', label: '业务平台订单总金额', readOnly: true },
      { name: 'omsPoCount', label: 'OMS订单总量', readOnly: true },
      { name: 'omsPoAmount', label: 'OMS订单总金额', readOnly: true },
      { name: 'synStatus', label: '状态', readOnly: true }, // GBSS和OMS数据对账状态:0:不一致;1:一致
      { name: 'startTime', label: '开始时间', readOnly: true },
      { name: 'endTime', label: '结束时间', readOnly: true },
      { name: 'createdBy', label: '创建人', readOnly: true },
      { name: 'createdDate', label: '创建时间', readOnly: true },
      { name: 'lastModifiedBy', label: '最后更新人', readOnly: true },
      { name: 'lastModifiedDate', label: '最后更新时间', readOnly: true },
      { name: 'version', label: '数据版本', readOnly: true },
    ],
  };
}

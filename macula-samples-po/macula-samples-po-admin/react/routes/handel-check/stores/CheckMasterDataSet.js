import {DataSet} from 'choerodon-ui/pro';

export default function () {
  const statusOptionDs = new DataSet({
    data: [
      {meaning: '成功', value: '1'},
      {meaning: '失败', value: '0'},
    ],
  });

  return {
    autoQuery: true,
    selection: false,
    dataKey: 'content',
    totalKey: 'totalElements',
    transport: {
      read: {
        url: 'order/v1/checking/po_check_master/all',
        method: 'get',
      },
    },

    fields: [
      {name: 'operation', label: '操作'},
      {name: 'id', label: '序号'},
      {name: 'gbssPoCount', label: 'GBSS/OMS(订单总量)'},
      {name: 'gbssPoAmount', label: 'GBSS/OMS(订单总金额)'},
      // {name: 'omsPoCount', label: 'OMS订单总量'},
      // {name: 'omsPoAmount', label: 'OMS订单总金额'},
      {name: 'synStatus', label: '状态'}, // GBSS和OMS数据对账状态:0:不一致;1:一致
      {name: 'startTime', label: '开始时间'},
      {name: 'endTime', label: '结束时间'},
      // {name: 'createdBy', label: '创建人'},
      {name: 'createdDate', label: '创建时间'},
      // {name: 'lastModifiedBy', label: '最后更新人'},
      {name: 'lastModifiedDate', label: '最后更新时间'},
    ],
    queryFields: [
      {name: 'synStatus', label: '状态', options: statusOptionDs, defaultValue: '0'},
    ],
  };
}

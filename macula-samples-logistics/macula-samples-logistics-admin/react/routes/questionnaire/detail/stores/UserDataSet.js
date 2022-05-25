import { DataSet } from 'choerodon-ui/pro';

export default function ({ questionnaireId }) {
  return {
    autoQuery: false,
    paging: true,
    transport: {
      read: {
        url: `/scc/v1/questionnaires/${questionnaireId}/unassigned_users`,
        method: 'get',
      },
    },
    queryParams: {
      questionnaireId,
    },
    fields: [
      { name: 'realName', label: '用户名' },
      { name: 'loginName', label: '登录名' },
      { name: 'phone', label: '手机' },
      { name: 'supplierName', label: '所属供应商' },
    ],
    queryFields: [
      { name: 'supplierName', label: '所属供应商' },
      { name: 'realName', label: '用户名' },
      { name: 'loginName', label: '登录名' },
    ],
  };
}

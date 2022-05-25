import { DataSet } from 'choerodon-ui/pro';

export default function () {
  const enableOptionDs = new DataSet({
    data: [
      { meaning: '启用', value: true },
      { meaning: '停用', value: false },
    ],
  });
  const booleanOptionDs = new DataSet({
    data: [
      { meaning: '是', value: true },
      { meaning: '否', value: false },
    ],
  });
  return {
    selection: 'multiple',
    autoQuery: false,
    paging: true,
    transport: {
      read: ({ data: { id } }) => ({
        url: `/supplier/v1/suppliers/${id}/users`,
        method: 'get',
      }),
    },
    fields: [
      { name: 'userName', label: '用户名' },
      { name: 'loginName', label: '登录名' },
      { name: 'email', label: '邮箱' },
      { name: 'phone', label: '手机' },
      { name: 'supplierSourceSystemId', label: '源系统标识' },
      { name: 'enabled', label: '状态', options: enableOptionDs },
      { name: 'bindWeChat', label: '已绑定微信', options: booleanOptionDs },
    ],
    queryFields: [
      { name: 'userName', label: '用户名' },
      { name: 'loginName', label: '登录名' },
      { name: 'email', label: '邮箱' },
      { name: 'phone', label: '手机' },
    ],
  };
}

import { DataSet } from 'choerodon-ui/pro';

export default function () {
  const enableOptionDs = new DataSet({
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
        url: '/scc/v1/questionnaires',
        method: 'get',
      },
      create: ({ data: [data] }) => ({
        url: '/scc/v1/questionnaires',
        method: 'post',
        data,
      }),
    },

    fields: [
      { name: 'title', label: '标题', required: true },
      { name: 'description', label: '描述' },
      { name: 'url', type: 'url', label: '链接', required: true },
      { name: 'sourceSystemFlag', label: '创建时间' },
      { name: 'createdName', label: '创建人' },
      { name: 'pushTime', label: '推送时间' },
      { name: 'status', label: '状态', options: enableOptionDs },
    ],
    queryFields: [
      { name: 'title', label: '标题' },
      { name: 'status', label: '状态', options: enableOptionDs },
    ],
  };
}

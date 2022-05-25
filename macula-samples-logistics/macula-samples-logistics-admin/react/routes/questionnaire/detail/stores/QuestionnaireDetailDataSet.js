import { DataSet } from 'choerodon-ui/pro';

export default function ({ questionnaireId, questionnaireUserDataSet }) {
  const enableOptionDs = new DataSet({
    data: [
      { meaning: '启用', value: true },
      { meaning: '停用', value: false },
    ],
  });
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: `/scc/v1/questionnaires/${questionnaireId}/detail`,
        method: 'get',
      },
      update: ({ data: [data] }) => {
        debugger;
        return {
          url: `/scc/v1/questionnaires/${questionnaireId}`,
          method: 'put',
          data: {
            ...data,
            questionnaireUserDTOList: data.questionnaireUserDTOList.map(v => ({
              supplierUserId: v.id,
              // eslint-disable-next-line no-underscore-dangle
              __status: v.__status,
              // ...v.
            })),
          },
        }
      },
    },

    fields: [
      { name: 'title', label: '标题', required: true },
      { name: 'description', label: '描述' },
      { name: 'url', type: 'url', label: '链接', required: true },
      { name: 'sourceSystemFlag', label: '创建时间' },
      { name: 'createdName', label: '创建人' },
      { name: 'pushTime', label: '推送时间' },
    ],
    children: {
      questionnaireUserDTOList: questionnaireUserDataSet,
    },
  };
}

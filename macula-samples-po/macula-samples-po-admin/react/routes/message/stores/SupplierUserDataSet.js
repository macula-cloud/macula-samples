export default function (organizationId) {

  return {
    selection: 'false',
    autoQuery: false,
    paging: true,
    transport: {
      read: (props) => ({
        url: '/base/v1/users/all',
        method: 'get',
        params: {
          organization_id: '0',
          sort: 'id',
          size: 0,
        },
      })
    },
    fields: [
      {name: 'id', label: 'id'},
      {name: 'realName', label: '用户名称'},
      {name: 'email', label: '邮箱'},
      {name: 'phone', label: '手机'},
      {name: 'param', label: '用户名'},
      {name: 'operation', label: '操作'},

    ],
    queryFields: [
      {name: 'param', label: '用户名'},
    ],
  };
}

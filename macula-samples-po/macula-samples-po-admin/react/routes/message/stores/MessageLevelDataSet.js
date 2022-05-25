/* eslint-disable no-underscore-dangle */
import {DataSet} from 'choerodon-ui/pro';

export default function () {
  const levelOptionDs = new DataSet({
    data: [
      {meaning: 'L1', value: 'L1'},
      {meaning: 'L2', value: 'L2'},
      {meaning: 'L3', value: 'L3'},
      {meaning: 'L4', value: 'L4'}
    ],
  });
  return {
    autoQuery: true,
    pageSize: 15,
    dataKey: 'content',
    totalKey: 'totalElements',
    selection: 'multiple',
    transport: {
      read: {
        url: 'order/v1/messages',
        method: 'get',
      },
      update: ({data: [data]}) => ({
        url: 'order/v1/messages/update',
        method: 'put',
        data: {
          ...data,
          _id: data._id,
          __status: data.__status,
        },
      }),
    },

    fields: [
      {name: 'userId', label: '用户id'},
      {name: 'userName', label: '用户名', required: true},
      {
        name: 'mobile',
        label: '手机号码',
        minLength: 7,
        maxLength: 11,
        required: true,
        validator: (value, _name, _record) => {
          if (!(/^1[3456789]\d{9}$/.test(value))) {
            return "手机号码有误,请重填";
          }
        }
      },
      {
        name: 'email', label: '个人邮箱', required: true, validator: (value, _name, _record) => {
          if (!(/^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/.test(value))) {
            return "邮箱格式有误,请重填";
          }
        }
      },
      {name: 'level', label: '级别'},
      {name: 'comments', label: '备注'},
      {name: 'lastModifiedDate', label: '修改时间'},
      {name: 'lastModifiedBy', label: '更新人'},
      {name: 'createdDate', label: '创建时间'},
      {name: 'createdBy', label: '创建人'},

    ],
    queryFields: [
      {name: 'userName', label: '用户名'},
      {name: 'mobile', label: '手机号码'},
      {name: 'level', label: '级别', options: levelOptionDs},
    ],

  };
}

/* eslint-disable no-console */
import React, {} from 'react';
import {Select, AutoComplete} from 'choerodon-ui';
import {observer} from 'mobx-react-lite';
import {axios} from "@buildrun/boot";
import {TextArea, Form, message, Option, Tooltip, DataSet} from 'choerodon-ui/pro';

import './index.less';

export default observer(({dataSet, modal, context}) => {
  const {messageLevelDataSet, supplierUserDataSet} = context;

  let level = 'L1';
  let comments = '备注';
  const ds = new DataSet({
    autoCreate: true,
    fields: [
      {
        name: 'comments',
        type: 'string',
        defaultValue: '备注',
        required: true
      }
    ],
    events: {
      update: ({record, name, value, oldValue}) => comments = value
    },
  });


  modal.handleOk(async () => {

    let data = messageLevelDataSet.created.map(data => ({
      "userId": data.data.userId,
      "userName": data.data.userName,
      "mobile": data.data.mobile,
      "email": data.data.email,
      "level": level,
      "comments": comments
    }));
    console.log(data)
    try {
      const res = await axios.post(`order/v1/messages/add`, JSON.stringify(data));
      if (!res.failed) {
        message.info(`保存成功`);
      }
    } catch (e) {
      message.error(e.message);
    } finally {
      messageLevelDataSet.query();
    }
  });

  modal.handleCancel(() => {
    messageLevelDataSet.reset();
  });


  return (
    <div>
      <Form>
        <Select
          mode='tags'
          showArrow={true}
          allowClear={true}
          placeholder="请输入用户名"
          onSearch={
            async (value) => {
              if (value != "") {
                supplierUserDataSet.setQueryParameter('param', value);
                await supplierUserDataSet.query();
              }
            }
          }
          onSelect={value => {
            let record = supplierUserDataSet.find((record, index, array) => record.get("realName") == value);
            messageLevelDataSet.create({
              "userId": record.get('id'),
              "userName": record.get('realName'),
              "mobile": record.get('phone'),
              "email": record.get('email')
            });
            console.log(value)
            console.log(messageLevelDataSet.created.length)
          }}
          onDeselect={value => {
            let record = messageLevelDataSet.find((record, index, array) => record.get("userName") == value);
            messageLevelDataSet.remove(record);
          }}
        >
          {supplierUserDataSet.data.map(opt => (

            <Select.Option key={opt.data.id} value={`${opt.data.realName}`}>
              <Tooltip placement="left" autoAdjustOverflow={true}
                       title={`${opt.data.email}`}>{`${opt.data.realName}`}</Tooltip>
            </Select.Option>

          ))}</Select>
        <Select name="level" placeholder="请选择" onSelect={(value) => level = value} defaultValue="L1">
          <Option value="L1">L1</Option>
          <Option value="L2">L2</Option>
          <Option value="L3">L3</Option>
          <Option value="L4">L4</Option>
        </Select>
        <TextArea name="comments" dataSet={ds}/>
      </Form>
    </div>
  );
});

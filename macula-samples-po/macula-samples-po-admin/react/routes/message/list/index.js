/* eslint-disable no-console */
/* eslint-disable no-return-assign */
import React, {useContext} from 'react';
import {
  Table,
  Form,
  Modal,
  Button,
  TextField,
  TextArea,
  message,
  Tooltip,
  DataSet
} from 'choerodon-ui/pro';
import {Divider, Select} from 'choerodon-ui';
import {observer} from 'mobx-react-lite';
import {TabPage, axios, Content, Header} from '@buildrun/boot';
import moment from 'moment';
import Store from '../stores';
import CreateModal from './CreateModal';
import './index.less';

const {Column} = Table;
export default observer(() => {
  const context = useContext(Store);
  const {messageLevelDataSet, supplierUserDataSet} = useContext(Store);
  const levelOptionDs = new DataSet({
    data: [
      {meaning: 'L1', value: 'L1'},
      {meaning: 'L2', value: 'L2'},
      {meaning: 'L3', value: 'L3'},
      {meaning: 'L4', value: 'L4'},
    ],
  });

  function openModal() {
    Modal.open({
      title: '新增消息',
      drawer: true,
      width: 600,
      okText: '保存',
      children: (
        <CreateModal context={context} dataSet={messageLevelDataSet} key="addModal"/>
      ),
    });
  }

  function editModal(record, isEdit) {
    let isCancel = false;
    Modal.open({
      title: '编辑消息',
      drawer: true,
      width: 600,
      okText: '保存',
      children: (
        <Form record={record}>

          <Select
            name="userName"
            mode="combobox"
            allowClear={true}
            showArrow={true}
            placeholder="请c输入用户名"
            defaultValue={record.get("userName")}
            onSearch={
              async (value) => {
                if (value != "") {
                  supplierUserDataSet.setQueryParameter('param', value
                  );
                  await supplierUserDataSet.query();
                }
              }
            }
            onSelect={value => {
              let rd = supplierUserDataSet.find((record, index, array) => record.get("realName") == value);
              console.log(rd)
              record.set("userName", value);
              record.set("mobile", rd.get("phone"));
              record.set("email", rd.get("email"));
              console.log(record)
            }}
          >
            {supplierUserDataSet.data.map(opt => (

              <Select.Option key={opt.data.id} value={`${opt.data.realName}`}>
                <Tooltip placement="left" autoAdjustOverflow={true}
                         title={`${opt.data.email}`}>{`${opt.data.realName}`}</Tooltip>
              </Select.Option>

            ))}</Select>
          <TextField name="mobile" defaulValue={record.get("mobile")}/>
          <TextField name="email" defaulValue={record.get("email")}/>
          <Select name="level" defaultValue={record.get("level")} onSelect={(value) => record.set("level", value)}>
            {levelOptionDs.data.map(opt => (
              <Select.Option key={opt.data.value} value={`${opt.data.value}`}>
                {`${opt.data.value}`}
              </Select.Option>

            ))}
          </Select>
          <TextArea name="comments" defaulValue={record.get("comments")}/>
        </Form>
      ),
      onOk: () => messageLevelDataSet.submit(),
      onCancel: () => (isCancel = true),
      afterClose: () => isCancel && isEdit && messageLevelDataSet.remove(record),
    });
  }

  return (
    <TabPage>
      <Header>
        <Button
          key="add"
          icon="playlist_add"
          onClick={openModal}
        >新增
        </Button>
        <Button
          key="delete"
          icon="delete_row"
          onClick={
            async () => {
              const ids = messageLevelDataSet.selected.map(mg => mg.get('id'));
              console.log(ids);
              try {
                const res = await axios.delete('/order/v1/messages/deleteByIds', {data: JSON.stringify(ids)});

                if (!res.failed) {
                  message.info('删除成功');
                }
              } catch (e) {
                message.error(e.message);
              } finally {
                messageLevelDataSet.query();
              }
            }
          }
          disabled={
            !(messageLevelDataSet.selected.length > 0)
          }
        >删除
        </Button>
        <Button
          key="fresh"
          icon="refresh"
          onClick={async () => {
            await messageLevelDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>

      </Header>
      <Content>
        <div className="scc-order-list-content">
          <Table queryBar="normal" pristine queryFieldsLimit={5} dataSet={messageLevelDataSet} key="messageList">
            <Column name="userName" key="userName"/>
            <Column name="mobile" key="mobile"/>
            <Column name="email" key="email" width="180px"/>
            <Column name="level" key="level"/>
            <Column name="comments" key="comments"/>
            <Column
              name="lastModifiedDate"
              key="lastModifiedDate"
              width={140}
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column name="lastModifiedBy" key="lastModifiedBy"/>
            <Column
              name="createdDate"
              key="createdDate"
              width={140}
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column name="createdBy" key="createdBy"/>
            <Column
              header="操作"
              align="center"
              renderer={({record}) => (

                <span>
                  <Button onClick={() => editModal(record)} funcType="flat" icon="mode_edit" key="edit"/>
                  <Divider type="vertical"/>
                  {/* <Button onClick={() => message.info(record.get("id"))} funcType="flat" icon="delete" key="delete"/> */}
                </span>
              )}
              lock="right"
              key="operation"
            />

          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

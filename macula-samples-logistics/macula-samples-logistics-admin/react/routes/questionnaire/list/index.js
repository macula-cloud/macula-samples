import React, { useContext, useState, useRef } from 'react';
import { Table, Form, Modal, Button, DatePicker, TextField, TextArea, message, Select, Output, Row, Col, Tooltip } from 'choerodon-ui/pro';
import { observer, useObserver } from 'mobx-react-lite';
import { TabPage, axios, Content, Header } from '@buildrun/boot';
import moment from 'moment';
import { ClickText } from '@buildrun/components';
import Store from '../stores';
import './index.less';
import CreateModal from './CreateModal';

const { Column } = Table;
export default observer(() => {
  const context = useContext(Store);
  const { questionnaireDataSet, location, location: { search }, history } = context;

  function renderName({ record, text }) {
    return (
      <ClickText
        // disabled={record?.get('presetFlag')}
        record={record}
        onClick={() => {
          history.push({
            pathname: `/scc/questionnaire/${record.get('id')}`,
            search,
          });
        }}
      >{text}
      </ClickText>
    );
  }

  function pushQuestionnaire(record) {
    Modal.confirm({
      title: '立即推送',
      children: `是否确认立即推送问卷"${record.get('title')}"`,
      onOk: async () => {
        try {
          const res = await axios.put(`/scc/v1/questionnaires/pushQuestionnaire/${record.get('id')}`);
          if (!res.failed) {
            questionnaireDataSet.query();
            message.success('推送成功');
          }
        } catch (e) {
          message.error(e.message);
        }
      },
    });
  }

  function openModal(isEdit) {
    questionnaireDataSet.create();
    Modal.open({
      title: !isEdit ? '新增问卷调查' : '修改问卷调查',
      drawer: true,
      style: { width: '3.8rem' },
      okText: '保存',
      children: (
        <CreateModal context={context} dataSet={questionnaireDataSet} />
      ),
    });
  }

  return (
    <TabPage>
      <Header>
        <Button
          icon="playlist_add"
          onClick={openModal}
        >新增
        </Button>
        <Button
          icon="refresh"
          onClick={async () => {
            await questionnaireDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
      </Header>
      <Content>
        <div className="scc-questionnaire-list-content">
          <Table queryBar="normal" dataSet={questionnaireDataSet} pristine queryFieldsLimit={2}>
            <Column name="title" renderer={renderName} />
            <Column name="description" />
            <Column name="url" />
            <Column name="sourceSystemFlag" />
            <Column name="createdName" />
            <Column name="pushTime" />
            <Column width={120} name="status" renderer={({ value }) => value ? '已推送' : '未推送'} />
            <Column
              width={100}
              renderer={({ record }) => moment(record.get('pushTime')).isBefore(moment()) && !record.get('status') && (
                <Tooltip placement="top" title="立即推送">
                  <Button onClick={() => pushQuestionnaire(record)} icon="recover" />
                </Tooltip>
              )}
            />
          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

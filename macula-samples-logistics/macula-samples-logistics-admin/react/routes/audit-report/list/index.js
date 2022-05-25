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
  const { auditReportDataSet, location, location: { search }, history, supplierDetailDataSet } = context;

  function renderName({ record, text }) {
    return (
      <ClickText
        // disabled={record?.get('presetFlag')}
        record={record}
        onClick={() => {
          document.getElementById(record.get('id')).click();
        }}
      >{text}
        <a id={record.get('id')} href={record.get('url')} />
      </ClickText>
    );
  }
  function openModal(isEdit) {
    if (!isEdit) {
      auditReportDataSet.create();
    }
    Modal.open({
      title: !isEdit ? '新增审核报告' : '修改审核报告',
      drawer: true,
      style: { width: '3.8rem' },
      okText: '保存',
      children: (
        <CreateModal context={context} dataSet={auditReportDataSet} />
      ),
    });
  }

  function handleCreate() {
    openModal(false);
  }
  async function handleEdit(record) {
    supplierDetailDataSet.setQueryParameter('supplierId', record.get('supplierId'));
    await supplierDetailDataSet.query();
    openModal(true);
  }

  function pushAuditReport(record) {
    Modal.confirm({
      title: '立即推送',
      children: `是否确认立即推送"${record.get('fileName')}"`,
      onOk: async () => {
        try {
          const res = await axios.put(`/scc/v1/audit_reports/pushAuditReport/${record.get('id')}`);
          if (!res.failed) {
            auditReportDataSet.query();
            message.success('推送成功');
          }
        } catch (e) {
          message.error(e.message);
        }
      },
    });
  }

  function renderAction({ record }) {
    const ret = [];
    if (!record.get('status')) {
      ret.push(<Button icon="mode_edit" onClick={(e) => handleEdit(record)} />);
      if (moment(record.get('pushTime')).isBefore(moment())) {
        ret.push(
          <Tooltip placement="top" title="立即推送">
            <Button onClick={() => pushAuditReport(record)} icon="recover" />
          </Tooltip>,
        );
      }
    }
    return ret;
  }

  return (
    <TabPage>
      <Header>
        <Button
          icon="playlist_add"
          onClick={handleCreate}
        >创建
        </Button>
        <Button
          icon="refresh"
          onClick={async () => {
            await auditReportDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
      </Header>
      <Content>
        <div className="scc-supplier-list-content">
          <Table queryBar="normal" dataSet={auditReportDataSet} pristine queryFieldsLimit={3}>
            <Column name="fileName" renderer={renderName} />
            <Column name="supplierName" />
            <Column name="uploadTime" />
            <Column name="realName" />
            <Column name="pushTime" />
            <Column width={120} name="status" renderer={({ value }) => value ? '已推送' : '未推送'} />
            <Column
              width={120}
              renderer={renderAction}
            />
          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

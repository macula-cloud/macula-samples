import React, { useContext, useState, useRef } from 'react';
import { Table, Form, Upload, Modal, Button, DateTimePicker, TextField, TextArea, message, Select, Output, Row, Col } from 'choerodon-ui/pro';
import { observer, useObserver } from 'mobx-react-lite';
import { TabPage, axios, Content, Header, Choerodon } from '@buildrun/boot';
import { ClickText } from '@buildrun/components';
import './index.less';

const { Column } = Table;
export default observer(({ dataSet, modal, context }) => {
  const uploadRef = useRef();
  const { supplierDataSet, supplierDetailDataSet, supplierUserDataSet, auditReportDataSet } = context;
  modal.handleOk(async () => {
    try {
      // const res = await uploadRef.current.startUpload();
      const res = await auditReportDataSet.submit();
      if (!res) return false;
      // console.log(res);
      if (res.failed) {
        message.error(res.message);
        return false;
      }
    } catch (e) {
      // message.error(e.message);
      return false;
    }
  });
  modal.handleCancel(() => {
    dataSet.reset();
  })
  const uploadProps = {
    headers: {
      'Access-Control-Allow-Origin': '*',
      Authorization: `${Choerodon.getAccessToken()}`,
    },
    // eslint-disable-next-line no-underscore-dangle
    action: `${window._env_.API_HOST}/scc/v1/audit_reports/upload`,
    multiple: false,
    name: 'file',
    accept: ['.deb', '.txt', '.pdf', '.*'],
    uploadImmediately: true,
    partialUpload: false,
    showUploadBtn: false,
    onUploadProgress(percent) {

    },
    onUploadError(e) {
      message.error(`上传失败${e.message}`);
    },
    onUploadSuccess(response) {
      if (response.failed) {
        message.error(response.message);
      } else {
        auditReportDataSet.current.set('fileName', Object.keys(response)[0].slice(0, Object.keys(response)[0].lastIndexOf('.')));
        auditReportDataSet.current.set('url', response[Object.keys(response)[0]]);
      }
    },
  };
  function handleSelectSupplier() {
    const modal2 = Modal.open({
      title: '选择供应商',
      className: 'scc-audit-report-modal-max-height',
      children: (
        <Table
          queryBar="normal"
          style={{ height: 360 }}
          selectionMode="none"
          dataSet={supplierDataSet}
          onRow={({ record }) => ({ onClick: () => {
            supplierDataSet.select(record);
            supplierUserDataSet.setQueryParameter('id', record.get('id'));
          },
          onDoubleClick: () => {
            modal2.close();
          },

          })}
          highLightRow
        >
          <Column name="organizationName" />
          <Column name="organizationCode" />
          <Column name="organizationType" />
        </Table>
      ),
    });
  }
  function handleSelectUser() {
    auditReportDataSet.current.set('userIdList', []);
    supplierUserDataSet.query();
    Modal.open({
      title: '选择用户',
      children: (
        <Table queryBar="normal" style={{ height: 360 }} dataSet={supplierUserDataSet}>
          <Column name="userName" />
          <Column name="loginName" />
          <Column name="phone" />
        </Table>
      ),
    });
  }
  return (
    <div>
      <Upload {...uploadProps} ref={uploadRef} />
      <Form dataSet={dataSet}>
        {dataSet.current?.get('url') && <TextField required name="fileName" />}
        <TextField required value={supplierDataSet.selected[0]?.get('organizationName') || dataSet.current?.get('supplierName')} label="选择供应商" onClick={handleSelectSupplier} />
        <TextField required value={supplierUserDataSet.selected.map((r) => r.get('userName')).join('、')} label="选择用户" onClick={handleSelectUser} />
        <DateTimePicker name="pushTime" />
      </Form>
    </div>
  );
});

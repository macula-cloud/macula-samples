import React, { useContext, useState, useRef } from 'react';
import { TextArea, Form, UrlField, Modal, Button, DateTimePicker, TextField, message, Select, Output, Row, Col } from 'choerodon-ui/pro';
import { observer, useObserver } from 'mobx-react-lite';
import { TabPage, axios, Content, Header, Choerodon } from '@buildrun/boot';
import { ClickText } from '@buildrun/components';
import './index.less';

export default observer(({ dataSet, modal, context }) => {
  const { questionnaireDataSet } = context;
  modal.handleOk(async () => {
    try {
      // const res = await uploadRef.current.startUpload();
      const res = await questionnaireDataSet.submit();
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
    questionnaireDataSet.reset();
  });

  return (
    <div>
      <Form dataSet={dataSet}>
        <TextField name="title" />
        <UrlField name="url" />
        <DateTimePicker name="pushTime" />
        <TextArea name="description" />
      </Form>
    </div>
  );
});

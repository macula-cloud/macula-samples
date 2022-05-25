import React, { useContext, useState, useRef } from 'react';
import { Table, Form, Modal, Button, DatePicker, TextField, TextArea, message, Select, Output, Row, Col } from 'choerodon-ui/pro';
import { observer, useObserver } from 'mobx-react-lite';
import { runInAction } from 'mobx';
import { TabPage, axios, Content, Header } from '@buildrun/boot';
import { ClickText } from '@buildrun/components';
import Store, { StoreProvider } from './stores';
import './index.less';

const { Column } = Table;
const Detail = observer(() => {
  const { questionnaireDetailDataSet, location, questionnaireUserDataSet, userDataSet, questionnaireId } = useContext(Store);
  const [isEdit, setIsEdit] = useState(false);

  function renderName({ record, text }) {
    return (
      <ClickText
        // disabled={record?.get('presetFlag')}
        record={record}
        onClick={() => {

        }}
      >{text}
      </ClickText>
    );
  }

  function handleAddUser() {
    userDataSet.query();
    Modal.open({
      title: '选择用户',
      style: { width: '8rem' },
      children: (
        <Table queryBar="normal" style={{ height: 360 }} queryFieldsLimit={3} dataSet={userDataSet}>
          <Column name="realName" />
          <Column name="loginName" />
          <Column name="phone" />
          <Column name="supplierName" />
        </Table>
      ),
      onOk: async () => {
        await runInAction(() => {
          userDataSet.selected.forEach(user => {
            questionnaireUserDataSet.create(user.toData());
          });
        });
        await questionnaireDetailDataSet.submit();
        userDataSet.unSelectAll();
      },
      onCancel: () => {
        userDataSet.unSelectAll();
      },
    });
  }

  function handleDeleteUser(record) {
    questionnaireUserDataSet.delete(record, {
      title: '删除',
      children: `是否确认将用户${record.get('realName')}从问卷中移除？`,
      onOk: async () => {
        try {
          await setTimeout(() => {
            questionnaireDetailDataSet.submit();
          }, 100);
        } catch (e) {
          return false;
        }
        // setTimeout(() => {
        //   questionnaireDetailDataSet.submit();
        // }, 100);
      },
    });
  }

  function getEditButton() {
    if (questionnaireDetailDataSet.current?.get('status')) {
      return '';
    } else {
      return !isEdit ? (
        <Button
          icon="mode_edit"
          onClick={() => {
            setIsEdit(true);
          }}
        >修改
        </Button>
      ) : (
        <Button
          icon="save"
          onClick={async () => {
            if (!questionnaireDetailDataSet.dirty) {
              setIsEdit(false);
            }
            try {
              const res = await questionnaireDetailDataSet.submit();
              if (!res.failed) {
                setIsEdit(false);
              }
            } catch (e) {
              // d
            }
          }}
        >保存
        </Button>
      );
    }
  }

  return (
    <TabPage>
      <Header backPath={`/scc/questionnaire${location.search}`} backText="返回问卷管理">
        {getEditButton()}
        <Button
          icon="refresh"
          onClick={async () => {
            setIsEdit(false);
            await questionnaireDetailDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
      </Header>
      <Content>
        <div className="scc-questionnaire-list-content">
          <h3>基本信息</h3>
          <div className="scc-questionnaire-detail-form">
            <Form dataSet={questionnaireDetailDataSet}>
              <TextField name="title" disabled={!isEdit} />
              <TextField name="url" disabled={!isEdit} />
              <TextField name="pushTime" disabled={!isEdit} />
              <TextArea name="description" disabled={!isEdit} />
            </Form>
          </div>
          <h3>所包含用户</h3>
          {!questionnaireDetailDataSet.current?.get('status') && <Button icon="playlist_add" color="primary" onClick={handleAddUser}>新增</Button>}
          <Table queryBar="normal" dataSet={questionnaireUserDataSet}>
            <Column name="realName" />
            <Column name="loginName" />
            <Column name="phone" />
            <Column name="email" />
            <Column name="supplierName" />
            <Column renderer={({ record }) => questionnaireDetailDataSet.current?.get('status') ? '' : (
              <Button icon="delete" onClick={() => handleDeleteUser(record)} />
            )}
            />
          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

export default (props) => (
  <StoreProvider {...props}>
    <Detail />
  </StoreProvider>
);

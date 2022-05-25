/* eslint-disable eqeqeq */
import React, {useContext, useState, useRef} from 'react';
import {Tooltip, Icon, Table, Form, Modal, Button, DatePicker, TextField, TextArea, message} from 'choerodon-ui/pro';
import {observer, useObserver} from 'mobx-react-lite';
import {Page, axios, Content, Header} from '@buildrun/boot';
import moment from 'moment';
import Store, {StoreProvider} from './stores';
import './index.less';

const {Column} = Table;
const formatDate = (text) => text ? moment(text).format('MM-DD HH:mm:ss') : '';
const Detail = observer(() => {
  const {oneProcessDataSet, processDetailDataSet, mcSerInvokeLogDataSet, location} = useContext(Store);

  // 单元格样式
  const cell = () => ({
    style: {
      maxWidth: 100,
      overflow: 'hidden',
      whiteSpace: 'nowrap',
      textOverflow: 'ellipsis',
      cursor: 'pointer',
    },
  });
  // 渲染单元格 提示信息
  const showMsg = (text) => <span placement="topLeft" title={text}>{text}</span>;
  return (
    <Page>
      <Header backPath={`/order/process-list${location.search}`} backText="返回主列表">
        <Button
          icon="refresh"
          onClick={async () => {
            await oneProcessDataSet.query();
            await processDetailDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
      </Header>
      <Content>
        <h3>基本信息</h3>
        <div className="scc-process-master-form">
          <Form key="processMaster" dataSet={oneProcessDataSet} columns={4}>
            <TextField name="poNo" key="poNo"/>
            <TextField name="poProcessCode" key="poProcessCode"/>
            <TextField
              name="status"
              key="status"
              renderer={({text}) => {
                if (text.length === 2 && text === '88') {
                  return (<span>成功</span>);
                } else if (text.length === 2 && text === '81') {
                  return (<span style={{color: '#6613a1'}}>忽略</span>);
                } else if (text.length === 2 && text === '99') {
                  return (<span style={{color: '#ff3fa4'}}>失败</span>);
                } else if (text.length === 2 && text === '91') {
                  return (<span style={{color: '#dec910'}}>暂停</span>);
                } else if (text.length === 2 && text === '10') {
                  return (<span style={{color: '#a2d5ff'}}>变更</span>);
                } else if (text.length === 2 && text.substring(0, 1) === '5') {
                  return (<span style={{color: '#37e835'}}>处理中</span>);
                } else if (text.length === 2 && text.substring(0, 1) === '0') {
                  return (<span style={{color: '#a7f1a6'}}>请求处理中</span>);
                } else {
                  return text;
                }
              }}
            />
            <TextField
              name="salesDocument"
              key="salesDocument"
            />
            <TextField name="poSource" key="poSource"/>
            <TextField name="refSourceNo" key="refSourceNo"/>
            <TextField name="refSourceId" key="refSourceId"/>
            <TextField name="errorTimes" key="errorTimes"/>
            <TextArea colSpan={4} name="comments" key="comments"/>
            <TextField
              name="statusTime"
              key="statusTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <TextField
              name="createdDate"
              key="createdDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <TextField
              name="lastModifiedDate"
              key="lastModifiedDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <TextField
              name="lastModifiedBy"
              key="lastModifiedBy"
            />
          </Form>
        </div>
        <br/>
        <br/>
        <h3>处理明细</h3>
        <div className="scc-process-detail-content">
          <Table key="processDetailList" queryBar="normal" dataSet={processDetailDataSet}>
            <Column name="poNo" width="120px" renderer={({record, text}) => (
              <a href={`/#/order/order-list/${record.get('poNo')}`}>{`${text}`}</a>
            )}/>
            <Column name="procState" align="center" width="60px"/>
            <Column
              name="procTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column name="comments" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="createdBy"/>
            <Column
              name="createdDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column name="lastModifiedBy"/>
            <Column
              name="lastModifiedDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
          </Table>
        </div>
        <br/>
        <br/>
        <h3>接口调用日志</h3>
        <div>
          <Table queryBar="normal" dataSet={mcSerInvokeLogDataSet}>
            <Column name="source" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="sourceMethod" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="sourceMessage" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="sourceTimestamp" renderer={({text}) => formatDate(text)}/>
            <Column name="target" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="targetMethod" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="targetMessage" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="targetTimestamp" renderer={({text}) => formatDate(text)}/>
            <Column name="node" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
            <Column name="status" width="100px"/>
            <Column name="exceptionMessage" editor onCell={cell} renderer={({text}) => showMsg(text)}/>
          </Table>
        </div>
      </Content>
    </Page>
  );
});

export default (props) => (
  <StoreProvider {...props}>
    <Detail/>
  </StoreProvider>
);

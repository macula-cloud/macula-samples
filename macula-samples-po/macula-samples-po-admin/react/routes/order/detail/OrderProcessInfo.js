import { Table, Button, Tooltip, message } from 'choerodon-ui';
import { axios } from '@buildrun/boot';
import { observer } from 'mobx-react-lite';
import React, { useContext } from 'react';
import moment from 'moment';
import Store from './stores';

export default observer(() => {
  const { orderProcessDataSet, processDetailDataSet } = useContext(Store);
  const { data } = orderProcessDataSet;
  const { data: data2 } = processDetailDataSet;

  const processData = [];
  const processDetailData = [];

  data.forEach((v1) => {
    processData.push(v1.data);
  });

  data2.forEach((v2) => {
    processDetailData.push(v2.data);
  });

  const expandedRowRender = () => {
    const columns = [

      { title: '订单号', dataIndex: 'poNo', key: 'poNo', align: 'center' },
      { title: '处理状态', dataIndex: 'procState', key: 'procState', align: 'center' },
      {
        title: '状态时间',
        dataIndex: 'procTime',
        key: 'procTime',
        align: 'center',
        render: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : '',
      },
      { title: '备注', dataIndex: 'comments', key: 'comments', align: 'left' },
      /* {title: '创建人', dataIndex: 'createdBy', key: 'createdBy'}, */
      {
        title: '创建时间',
        dataIndex: 'createdDate',
        key: 'createdDate',
        render: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : '',
      },
      /* {title: '最后更l新人', dataIndex: 'lastModifiedBy', key: 'lastModifiedBy'}, */
      {
        title: '最后更新时间',
        dataIndex: 'lastModifiedDate',
        key: 'lastModifiedDate',
        render: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : '',
      },

    ];

    return (
      <Table
        filterBar={false}
        rowKey={record => record.id}
        columns={columns}
        dataSource={processDetailData}
        pagination={false}
      />
    );
  };

  const columns = [
    {
      title: '操作',
      key: 'operation',
      render: (_text, record) => (
        <span className="table-operation">
          <Tooltip placement="top" title="立即推送">
            <Button
              disabled={record.status !== '91'}
              funcType="flat"
              icon="recover"
              onClick={
                async () => {
                  try {
                    // console.log(record);
                    const res = await axios.put(`/order/v1/orders/order_push/master/${record.poNo}`);
                    // console.log(record);
                    if (!res.failed) {
                      message.info(`单号 ${record.poNo} 推送成功`);
                    }
                  } catch (e) {
                    message.error(e.message);
                  } finally {
                    orderProcessDataSet.query();
                    processDetailDataSet.query();
                  }
                }
              }
            />
          </Tooltip>

        </span>
      ),
    },
    { title: '订单号', dataIndex: 'poNo', key: 'poNo', width: '120px' },
    { title: '流程号', dataIndex: 'poProcessCode', key: 'poProcessCode', width: '60px' },
    {
      title: '来源',
      dataIndex: 'poSource',
      key: 'poSource',
      width: '120px',
      align: 'center',
      render: (text, record) => `${text}/${record.refSourceId ? record.refSourceId : record.refSourceNo}`,
    },
    {
      title: '状态',
      dataIndex: 'status',
      align: 'center',
      key: 'status',
      width: '60px',
      render: (text) => {
        if (text.length === 2 && text === '88') {
          return (<span>成功</span>);
        } else if (text.length === 2 && text === '81') {
          return (<span style={{ color: '#6613a1' }}>忽略</span>);
        } else if (text.length === 2 && text === '99') {
          return (<span style={{ color: '#ff3fa4' }}>失败</span>);
        } else if (text.length === 2 && text === '91') {
          return (<span style={{ color: '#dec910' }}>暂停</span>);
        } else if (text.length === 2 && text === '10') {
          return (<span style={{ color: '#a2d5ff' }}>变更</span>);
        } else if (text.length === 2 && text.substring(0, 1) === '5') {
          return (<span style={{ color: '#37e835' }}>处理中</span>);
        } else if (text.length === 2 && text.substring(0, 1) === '0') {
          return (<span style={{ color: '#a7f1a6' }}>请求处理中</span>);
        } else {
          return text;
        }
      },
    },
    {
      title: '状态时间',
      dataIndex: 'statusTime',
      key: 'statusTime',
      render: (text) => text ? moment(text).format('MM-DD HH:mm:ss') : '',
    },
    { title: '失败次数', dataIndex: 'errorTimes', key: 'errorTimes', align: 'center', width: '60px' },
    { title: 'SAP号', dataIndex: 'salesDocument', key: 'salesDocument', align: 'center', width: '120px' },
    { title: 'MQ-ID', dataIndex: 'sendMessageId', key: 'sendMessageId', align: 'center' },
    { title: '备注', dataIndex: 'comments', key: 'comments' },
    {
      title: '创建时间',
      dataIndex: 'createdDate',
      key: 'createdDate',
      render: (text) => text ? moment(text).format('MM-DD HH:mm:ss') : '',
    },
    {
      title: '最后更新时间',
      dataIndex: 'lastModifiedDate',
      key: 'lastModifiedDate',
      render: (text) => text ? moment(text).format('MM-DD HH:mm:ss') : '',
    },
  ];

  return (
    <Table
      filterBar={false}
      rowKey={record => record.poNo}
      className="components-table-demo-nested"
      columns={columns}
      expandedRowRender={expandedRowRender}
      dataSource={processData}
      pagination={false}
    />
  );
});

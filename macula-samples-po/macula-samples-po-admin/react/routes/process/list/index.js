/* eslint-disable array-callback-return */
/* eslint-disable eqeqeq */
import React, {useContext} from 'react';
import {
  Table,
  Button,
  message,
  CheckBox,
  Tooltip,
} from 'choerodon-ui/pro';
import {observer} from 'mobx-react-lite';
import {TabPage, Content, Header, axios} from '@buildrun/boot';
import {ClickText} from '@buildrun/components';
import moment from 'moment';
import Store from '../stores';
import './index.less';

const {Column} = Table;

export default observer(() => {
  const {orderProcessDataSet, location: {search}, history} = useContext(Store);

  let arr = [];
  let status = '';

  function renderName({record, text}) {
    return (
      <ClickText
        record={record}
        onClick={() => {
          history.push({
            pathname: `/order/process-list/${record.get('poNo')}`,
            search,
          });
        }}
      >{text}
      </ClickText>
    );
  }

  const findByStatus = async () => {
    orderProcessDataSet.setQueryParameter('status', status);
    await orderProcessDataSet.query();
  };

  function getStatus(value, oldValue) {
    // 如果当前选中状态值不为空
    if (value != null && value != '') {
      let flag = false;
      arr.forEach((item) => {
        if (value == item) {
          flag = true;
        }
      });
      if (flag) {
        findByStatus();
        return;
      }
      arr.push(value);
      status = arr.join(',');
      findByStatus();

      // 如果当前选中状态值为空
    } else {
      const newArr = arr.filter(item => {
        if (item != oldValue) {
          return item;
        }
      });
      arr = newArr;
      status = newArr.join(',');
      findByStatus();
    }
  }

  return (
    <TabPage>
      <Header>
        <Button
          icon="refresh"
          onClick={async () => {
            await orderProcessDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
        <Button
          icon="recover"
          onClick={async () => {
            console.log(orderProcessDataSet.queryDataSet.reset())
          }}
        >重置
        </Button>
        <div style={{marginLeft: 20}}>
          <CheckBox
            mode="box"
            name="status"
            value="88"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >成功
          </CheckBox>
          <CheckBox
            mode="box"
            name="status"
            value="99"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >失败
          </CheckBox>
          <CheckBox
            mode="box"
            name="status"
            value="91"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >暂停
          </CheckBox>
          <CheckBox
            mode="box"
            name="status"
            value="10"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >变更
          </CheckBox>
          <CheckBox
            mode="box"
            name="status"
            value="51,52,53,54,55,56"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >处理中
          </CheckBox>
        </div>
      </Header>
      <Content>
        <div className="scc-order-process-content">
          <Table
            key="processMasterList"
            queryBar="normal"
            queryFieldsLimit={3}
            dataSet={orderProcessDataSet}
            pagination={{showQuickJumper: true}}
          >
            <Column
              name="operation"
              renderer={({record}) => (
                <span>
                  <Tooltip placement="top"
                           title={record.get('status') === '91' || (record.get('status') === '88') ? '立即推送' : ''}>
                    <Button
                      style={record.get('status') !== '91' ? {} : ({color: '#ff3fa4'})}
                      disabled={(record.get('status') !== '91') & (record.get('status') !== '88')}
                      funcType="flat"
                      icon="recover"
                      onClick={
                        async () => {
                          try {
                            const res = await axios.put(`/order/v1/orders/order_push/master/${record.get('poNo')}`);
                            if (!res.failed) {
                              message.info(`单号 ${record.get('poNo')} 推送成功`);
                            }
                          } catch (e) {
                            message.error(e.message);
                          } finally {
                            orderProcessDataSet.query();
                          }
                        }
                      }
                    />
                  </Tooltip>

                </span>
              )}
              width="60px"
              key="operation"
            />
            <Column name="poNo" renderer={renderName} width="120px"/>
            <Column name="salesDocument" width="120px"/>
            <Column
              width="120px"
              name="status"
              align="center"
              renderer={({text, record}) => {
                console.log((record.get('status') !== '91') && (record.get('status') !== '88'))
                console.log((record.get('status') !== '91'))
                console.log((record.get('status') !== '88'))
                console.log(`~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`)
                if (text.length === 2 && text === '88') {
                  return (<span>{`${text}/成功`}</span>);
                } else if (text.length === 2 && text === '81') {
                  return (<span style={{color: '#6613a1'}}>{`${text}/忽略`}</span>);
                } else if (text.length === 2 && text === '99') {
                  return (<span style={{color: '#ff3fa4'}}>{`${text}/失败`}</span>);
                } else if (text.length === 2 && text === '91') {
                  return (<span style={{color: '#dec910'}}>{`${text}/暂停`}</span>);
                } else if (text.length === 2 && text === '10') {
                  return (<span style={{color: '#a2d5ff'}}>{`${text}/变更`}</span>);
                } else if (text.length === 2 && text.substring(0, 1) === '5') {
                  return (<span style={{color: '#37e835'}}>{`${text}/处理中`}</span>);
                } else if (text.length === 2 && text.substring(0, 1) === '0') {
                  return (<span style={{color: '#a7f1a6'}}>{`${text}/请求处理中`}</span>);
                } else {
                  return text;
                }
              }}
            />
            <Column name="poProcessCode" width="65px"/>
            <Column name="poSource" align="center" width="120px"/>
            <Column
              name="createdDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column
              name="statusTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column
              name="dValue" align="center" width="100px"
              renderer={({record}) => `${moment.duration(moment(record.get("statusTime")).valueOf() - moment(record.get("createdDate")).valueOf()).as("seconds")}s`}
            />
            <Column name="errorTimes" align="center" width="100px"/>
            <Column
              name="lastModifiedDate" width="100px"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
              hidden/>
          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

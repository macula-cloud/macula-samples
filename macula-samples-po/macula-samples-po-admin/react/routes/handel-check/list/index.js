/* eslint-disable no-plusplus */
/* eslint-disable eqeqeq */
/* eslint-disable no-restricted-globals */
/* eslint-disable no-useless-escape */
import React, {useContext} from 'react';
import {
  Table,
  Button,
  message,
  Tooltip
} from 'choerodon-ui/pro';
import {observer} from 'mobx-react-lite';
import {TabPage, Content, Header, axios} from '@buildrun/boot';
import {ClickText} from '@buildrun/components';
import moment from 'moment';
import Store from '../stores';
import './index.less';

const {Column} = Table;

export default observer(() => {
  const {checkMasterDataSet, location: {search}, history} = useContext(Store);

  // 金额保留两位小数
  function formatNumber(val) {
    val = val == null ? '' : val.toString().replace(/\$|\,/g, '');
    if (isNaN(val)) {
      val = '0';
    }
    const sign = (val == (val = Math.abs(val)));
    val = Math.floor(val * 100 + 0.50000000001);
    let cents = val % 100;
    val = Math.floor(val / 100).toString();
    if (cents < 10) {
      cents = `0${cents}`;
    }
    for (let i = 0; i < Math.floor((val.length - (1 + i)) / 3); i++) {
      val = `${val.substring(0, val.length - (4 * i + 3))},${val.substring(val.length - (4 * i + 3))}`;
    }

    return (`${((sign) ? '' : '-') + val}.${cents}`);
  }

  function renderName({record, text}) {
    return (
      <ClickText
        record={record}
        onClick={() => {
          history.push({
            pathname: `/order/handle-check-list/${record.get('id')}`,
            search,
          });
        }}
      >{text}
      </ClickText>
    );
  }

  return (
    <TabPage>
      <Header>
        <Button
          icon="refresh"
          onClick={async () => {
            await checkMasterDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>

      </Header>
      <Content>
        <div className="scc-order-list-content">
          <Table key="checkMaster" queryBar="normal" queryFieldsLimit={1} dataSet={checkMasterDataSet}>
            <Column
              name="operation"
              width={60}
              renderer={({record}) => (
                <span className="table-operation">

                  <Tooltip placement="top"
                           title={record.get('synStatus') === '1' || record.get('synStatus') === '2' ? '' : '重新对账'}>
                    <Button
                      style={record.get('synStatus') === '1' || record.get('synStatus') === '2' ? {} : ({color: '#ff3fa4'})}
                      disabled={record.get('synStatus') === '1' || record.get('synStatus') === '2'}
                      funcType="flat"
                      icon="repeat_one"
                      onClick={
                        async () => {
                          try {
                            const res = await axios.get(`/order/v1/checking/afreshChecking/${record.get('id')}`);
                            if (!res.failed) {
                              message.info(`对账成功`);
                            }
                          } catch (e) {
                            message.error(e.message);
                          } finally {
                            await checkMasterDataSet.query();
                          }
                        }
                      }
                    />
                  </Tooltip>
                </span>
              )}

            />
            <Column name="id" renderer={renderName} align="center" width={100}/>
            <Column
              name="startTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column
              name="endTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />

            <Column
              name="gbssPoCount"
              align="center"
              renderer={({text, record}) => `${text}/${record.get('omsPoCount')}`}
            />
            <Column
              name="gbssPoAmount"
              align="center"
              renderer={({text, record}) => `${formatNumber(text)}/${formatNumber(record.get('omsPoAmount'))}`}
            />

            <Column
              name="synStatus"
              renderer={({text}) => {
                if (text === '0') {
                  return (<span style={{color: '#ff3fa4'}}>失败</span>);
                } else if (text === '1') {
                  return (<span style={{color: '#5dff59'}}>成功</span>);
                } else if (text === '2') {
                  return (<span style={{color: '#5b83bb'}}>忽略</span>);
                } else {
                  return '未知状态';
                }
              }}
            />

            <Column
              name="createdDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column
              name="lastModifiedDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />

          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

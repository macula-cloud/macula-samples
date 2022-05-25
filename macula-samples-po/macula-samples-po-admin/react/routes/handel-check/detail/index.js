/* eslint-disable eqeqeq */
/* eslint-disable no-restricted-globals */
/* eslint-disable no-useless-escape */
import React, {useContext} from 'react';
import {Popconfirm} from 'choerodon-ui';
import {Tooltip, Icon, Table, Form, Button, TextField, message, Modal} from 'choerodon-ui/pro';
import {observer} from 'mobx-react-lite';
import {TabPage, axios, Content, Header} from '@buildrun/boot';
import moment from 'moment';
import Store, {StoreProvider} from './stores';
import './index.less';

const {Column} = Table;

const Detail = observer(() => {
  const {masterDataSet, checkDetailDataSet, location} = useContext(Store);

  // 金额保留两位小数
  function formatNumber(val) {
    val = val == null ? '' : val.toString().replace(/\$|\,/g, '');
    if (isNaN(val)) {
      val = '0';
    }
    // eslint-disable-next-line eqeqeq
    const sign = (val == (val = Math.abs(val)));
    val = Math.floor(val * 100 + 0.50000000001);
    let cents = val % 100;
    val = Math.floor(val / 100).toString();
    if (cents < 10) {
      cents = `0${cents}`;
    }
    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < Math.floor((val.length - (1 + i)) / 3); i++) {
      val = `${val.substring(0, val.length - (4 * i + 3))},${val.substring(val.length - (4 * i + 3))}`;
    }
    return (`${((sign) ? '' : '-') + val}.${cents}`);
  }

  return (
    <TabPage>
      <Header backPath={`/order/handle-check-list${location.search}`} backText="返回对账单列表">
        <Button
          icon="refresh"
          onClick={async () => {
            await checkDetailDataSet.query();
            await masterDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
      </Header>
      <Content>
        <h3>基本信息</h3>
        <div className="scc-po-master-form">
          <Form key="master" dataSet={masterDataSet} columns={4}>

            <TextField
              name="startTime"
              key="startTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <TextField
              name="endTime"
              key="endTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <TextField name="gbssPoCount" key="gbssPoCount"/>
            <TextField name="omsPoCount" key="omsPoCount"/>
            <TextField name="synStatus" key="synStatus" renderer={({text}) => {
              if (text === '0') {
                return (<span style={{color: '#ff3fa4'}}>失败</span>);
              } else if (text === '1') {
                return (<span style={{color: '#5dff59'}}>成功</span>);
              } else if (text === '2') {
                return (<span style={{color: '#5dff59'}}>忽略</span>);
              } else {
                return '未知状态';
              }
            }}/>
            <TextField name="version" key="version"/>
            <TextField name="gbssPoAmount" key="gbssPoAmount"/>
            <TextField name="omsPoAmount" key="omsPoAmount"/>
            <TextField
              name="createdDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
              key="createdDate"
            />

            <TextField
              name="lastModifiedDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
              key="lastModifiedDate"
            />
          </Form>
        </div>
        <br/>
        <br/>
        <h3>账单明细</h3>
        <div className="scc-po-detail-content">
          <Table
            key="checkDetailList"
            queryBar="normal"
            dataSet={checkDetailDataSet}
          >
            <Column
              name="operation"
              width={70}
              renderer={({record}) => (
                <span className="table-operation">
                  <Tooltip placement="top" title="忽略">
                    <Button
                      style={record.get('synStatus') === '1' || record.get('synStatus') === '2' ? {} : ({color: '#ae7be2'})}
                      disabled={record.get('synStatus') === '1' || record.get('synStatus') === '2'}
                      funcType="flat"
                      icon="cancle_b"
                      onClick={
                        async () => {
                          try {
                            const res = await axios.get(`/order/v1/checking/successByHand/${record.get('gbssPoNo')}/${record.get('checkMasterId')}`);

                            if (!res.failed) {
                              message.info(`单号已忽略`);
                            }
                          } catch (e) {
                            message.error(e.message);
                          } finally {
                            await masterDataSet.query();
                            await checkDetailDataSet.query();
                          }
                        }
                      }
                    />
                  </Tooltip>
                </span>
              )}
            />
            <Column name="checkMasterId" align="center" width={90}/>

            <Column
              name="gbssSapDocNo"
              renderer={({text, record}) => `${(text == null || text == '') ? '缺少单号' : text}/${(record.get('omsSapDocNo') == null || record.get('omsSapDocNo') == '') ? '缺少单号' : record.get('omsSapDocNo')}`}
            />
            <Column
              name="gbssPoNo"
              renderer={({text, record}) => `${(text == null || text == '') ? '缺少单号' : text}/${(record.get('omsPoNo') == null || record.get('omsPoNo') == '') ? '缺少单号' : record.get('omsPoNo')}`}
            />
            <Column
              name="gbssPoAmount"
              align="right"
              renderer={({text, record}) => `${formatNumber(text)}/${formatNumber(record.get('omsPoAmount'))}`}
            />
            <Column
              name="gbssPoStatus"
              align="center"
              renderer={({text, record}) => `${text}/${record.get('omsPoStatus')}`}
            />

            <Column
              name="synStatus"
              width={65}
              renderer={({text}) => {
                if (text === '0') {
                  return (<Tooltip placement="top" title="failed"><Icon
                    type="cancel"
                    style={{
                      fontSize: 22,
                      color: '#ff3fa4',
                    }}
                  />
                  </Tooltip>);
                } else if (text === '1') {
                  return (<Tooltip placement="top" title="success"><Icon
                    type="check_circle"
                    style={{
                      fontSize: 22,
                      color: '#5dff59',
                    }}
                  />
                  </Tooltip>);
                } else if (text === '2') {
                  return (<Tooltip placement="top" title="ignore"><Icon
                    type="cancel"
                    style={{
                      fontSize: 22,
                      color: '#5b83bb',
                    }}
                  />
                  </Tooltip>);
                } else {
                  return '未知状态';
                }
              }}
            />

            <Column
              name="createdDate"
              align="left"
              width={140}
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />
            <Column
              name="lastModifiedDate"
              align="left"
              width={140}
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
            />


          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

export default (props) => (
  <StoreProvider {...props}>
    <Detail/>
  </StoreProvider>
);

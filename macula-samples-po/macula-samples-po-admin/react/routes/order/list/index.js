/* eslint-disable eqeqeq */
import React, {useContext} from 'react';
import {
  Table,
  Button,
  Tooltip,
  message,
  CheckBox
} from 'choerodon-ui/pro';
import {observer} from 'mobx-react-lite';
import {TabPage, axios, Content, Header} from '@buildrun/boot';
import {ClickText} from '@buildrun/components';
import moment from 'moment';
import Store from '../stores';
import './index.less';

const {Column} = Table;

export default observer(() => {
  const {orderDataSet, location: {search}, history} = useContext(Store);

  let arr = [];
  let status = '';

  const findByStatus = async () => {
    orderDataSet.setQueryParameter('status', status);
    await orderDataSet.query();
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

  function renderName({record, text}) {
    return (
      <ClickText
        record={record}
        onClick={() => {
          history.push({pathname: `/order/order-list/${record.get('poNo')}`, search});
        }}
      >
        {text}
      </ClickText>
    );
  }

  return (
    <TabPage>
      <Header>
        <Button
          icon="refresh"
          onClick={async () => {
            /*if (orderDataSet.currentPage > 1) {
              orderDataSet.setQueryParameter('page', orderDataSet.currentPage);
            }*/
            await orderDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
        <Button
          icon="recover"
          onClick={async () => {
            console.log(orderDataSet.queryDataSet.reset())
          }}
        >重置
        </Button>
        <div style={{marginLeft: 20}}>
          <CheckBox
            mode="box"
            name="status"
            value="00"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >正常
          </CheckBox>
          <CheckBox
            mode="box"
            name="status"
            value="01"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >变更
          </CheckBox>
          <CheckBox
            mode="box"
            name="status"
            value="99"
            onChange={(value, oldValue) => getStatus(value, oldValue)}
          >删除
          </CheckBox>
        </div>
      </Header>

      <Content>
        <div className="scc-order-list-content">
          <Table
            onRow={({record}) => ({rowKey: () => record.get('poNo')})}
            queryBar="normal"
            queryFieldsLimit={4}
            dataSet={orderDataSet}
            pagination={{showQuickJumper: true}}
          >
            <Column
              name="operation"
              renderer={({record}) => (
                <span className="table-operation">
                  <Tooltip placement="top"
                           title={(record.get('status') === '91') || (record.get('status') === '88') ? '立即推送' : ''}>
                    <Button style={record.get('status') === '91' ? ({color: '#ff3fa4'}) : {}}
                            disabled={(record.get('status') !== '91') && (record.get('status') !== '88')}
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
                                  orderDataSet.query();
                                }
                              }
                            }
                    />
                  </Tooltip>

                </span>
              )}
              width={60}
              key="operation"
            />
            <Column name="poNo" renderer={renderName} width={110} key="poNo"/>
            <Column
              name="poStatus"
              align="center"
              renderer={({text}) => {
                if (text == '99') {
                  return (<span style={{color: '#ff3fa4'}}>{`${text}/删除`}</span>);
                } else if (text == '00') {
                  return (<span>{`${text}/正常`}</span>);
                } else if (text == '01') {
                  return (<span style={{color: '#a2d5ff'}}>{`${text}/变更`}</span>);
                } else {
                  return text;
                }
              }}
              key="poStatus"
            />
            <Column name="status" align="center" key="status" renderer={({text}) => {
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
            }}/>
            <Column name="poProcessCode" align="center" key="poProcessCode"/>
            <Column name="sapPostingDocNo" align="center" key="sapPostingDocNo"/>
            <Column name="poDate" key="poDate"/>
            <Column name="poStoreNo" key="poStoreNo" hidden/>
            <Column name="companyNo" key="companyNo"
                    renderer={({record, text}) => `${text}/${record.get("companyName")}`}/>
            <Column name="orderDealerNo" key="orderDealerNo"/>
            <Column name="poRegionNo" align="center" key="poRegionNo" hidden/>
            <Column name="orderDealerName" align="center" key="orderDealerName" hidden/>
            <Column name="orderType" align="center" key="orderType" hidden/>
            <Column name="refPoNo" align="center" key="refPoNo" hidden/>
            <Column name="refPoPeriod" align="center" key="refPoPeriod" hidden/>
            <Column name="refPoDate" align="center" key="refPoDate" hidden/>
            <Column name="totalSaleAmt" align="center" key="totalSaleAmt" hidden/>
            <Column name="totalSaleProductAmt" align="center" key="totalSaleProductAmt" hidden/>
            <Column name="totalSaleNonProductAmt" align="center" key="totalSaleNonProductAmt" hidden/>
            <Column name="totalSaleDiscountAmt" align="center" key="totalSaleDiscountAmt" hidden/>
            <Column name="totalSaleCouponAmt" align="center" key="totalSaleCouponAmt" hidden/>
            <Column name="totalSaleNetAmt" align="center" key="totalSaleNetAmt" hidden/>
            <Column name="totalTransportAmt" align="center" key="totalTransportAmt" hidden/>
            <Column name="totalCalcPoint" align="center" key="totalCalcPoint" hidden/>
            <Column name="totalCalcDiscountPoint" align="center" key="totalCalcDiscountPoint" hidden/>
            <Column name="totalCalcRebate" align="center" key="totalCalcRebate" hidden/>
            <Column name="poEntryClass" align="center" key="poEntryClass" hidden/>
            <Column name="poEntryDealerNo" align="center" key="poEntryDealerNo" hidden/>
            <Column
              name="poEntryTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
              key="poEntryTime"
              hidden
            />
            <Column name="poEntryBy" align="center" key="poEntryBy" hidden/>
            <Column name="poEntryMemo" align="center" key="poEntryMemo" hidden/>
            <Column name="poAppNo" align="center" key="poAppNo" hidden/>
            <Column name="poLclNo" align="center" key="poLclNo" hidden/>
            <Column name="poGroupNo" align="center" key="poGroupNo" hidden/>
            <Column name="refSelectedNo" align="center" key="refSelectedNo" hidden/>
            <Column name="poPromFlag" align="center" key="poPromFlag" hidden/>
            <Column name="poWholePromCode" align="center" key="poWholePromCode" hidden/>
            <Column name="poPriceGroupType" align="center" key="poPriceGroupType" hidden/>
            <Column name="poPriceAttr" align="center" key="poPriceAttr" hidden/>
            <Column name="poReturnRestrictFlag" align="center" key="poReturnRestrictFlag" hidden/>
            <Column name="paymentTotalAmt" align="center" key="paymentTotalAmt" hidden/>
            <Column name="paymentStatus" align="center" key="paymentStatus" hidden/>
            <Column
              name="paymentTime"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
              key="paymentTime"
              hidden
            />
            <Column name="paymentDocNo" align="center" key="paymentDocNo" hidden/>
            <Column name="paymentMemo" align="center" key="paymentMemo" hidden/>
            <Column name="sapPostingFlag" align="center" key="sapPostingFlag" hidden/>
            <Column name="sapPostingDate" align="center" key="sapPostingDate" hidden/>
            <Column name="comments" align="center" key="comments" hidden/>
            <Column
              name="lastModifiedDate"
              renderer={({text}) => text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : ''}
              key="lastModifiedDate"
              hidden
            />
            <Column name="lastModifiedBy" align="center" key="lastModifiedBy" hidden/>
            <Column name="poOweNo" align="center" key="poOweNo" hidden/>
            <Column name="orderDealerBranchNo" align="center" key="orderDealerBranchNo" hidden/>
            <Column name="orderDesc" align="center" key="orderDesc" hidden/>
            <Column name="companyNo" align="center" key="companyNo" hidden/>
            <Column name="profitCenterNo" align="center" key="profitCenterNo" hidden/>
            <Column name="orderCustomerNo" align="center" key="orderCustomerNo" hidden/>
            <Column name="totalWeight" align="center" key="totalWeight" hidden/>
            <Column name="totalCalcRebateDs" align="center" key="totalCalcRebateDs" hidden/>
            <Column name="totalVatAmt" align="center" key="totalVatAmt" hidden/>
            <Column name="totalConsumTaxAmt" align="center" key="totalConsumTaxAmt" hidden/>
            <Column name="totalCompreTaxAmt" align="center" key="totalCompreTaxAmt" hidden/>
            <Column name="totalRebateDisAmt" align="center" key="totalRebateDisAmt" hidden/>
            <Column name="totalTransportCouponAmt" align="center" key="totalTransportCouponAmt" hidden/>
            <Column name="poEntryStoreNo" align="center" key="poEntryStoreNo" hidden/>
          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

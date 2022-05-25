/* eslint-disable no-console */
import React, {useContext} from 'react';
import {Table, Tabs, Form, Button, TextField, Output, message} from 'choerodon-ui/pro';
import {observer} from 'mobx-react-lite';
import {Page, TabPage, Content, Header} from '@buildrun/boot';
import moment from 'moment';
import Store, {StoreProvider} from './stores';
import './index.less';

const {Column} = Table;
const {TabPane} = Tabs;
const formatDate = (text) => text ? moment(text).format('MM-DD HH:mm:ss') : '';
const Detail = observer(() => {
  const {orderMasterDataSet, orderDetailDataSet, detailDiscountDataSet, detailAccTranDataSet, detailPaymentDataSet, invoiceInfoDataSet, mkpDlpPoRelateDataSet, location} = useContext(Store);

  // po_master展示
  const FormInfo = () => (
    <Form
      dataSet={orderMasterDataSet}
      columns={8}
    >
      <TextField name="poNo"/>
      <TextField name="poProcessCode"/>
      <TextField name="poType"/>
      <TextField name="poPeriod"/>
      <TextField name="poDate"/>
      <TextField name="poStoreNo"/>
      <TextField name="poBranchNo"/>
      <TextField name="poRegionNo"/>
      <TextField name="poStatus"/>

      <TextField name="orderDealerNo"/>
      <TextField name="orderDealerName"/>
      <TextField name="orderType"/>
      <TextField name="refPoNo"/>
      <TextField name="refPoPeriod"/>
      <TextField name="refPoDate"/>
      <TextField name="totalSaleAmt"/>
      <TextField name="totalSaleProductAmt"/>
      <TextField name="totalSaleNonProductAmt"/>
      <TextField name="totalSaleDiscountAmt"/>
      <TextField name="totalSaleCouponAmt"/>
      <TextField name="totalSaleNetAmt"/>
      <TextField name="totalTransportAmt"/>
      <TextField name="totalTransportCouponAmt"/>

      <TextField name="totalCalcPoint"/>
      <TextField name="totalCalcDiscountPoint"/>

      <TextField name="totalCalcRebate"/>
      <TextField name="poEntryClass"/>
      <TextField name="poEntryDealerNo"/>
      <TextField name="poEntryTime" renderer={({text}) => formatDate(text)}/>
      <TextField name="poEntryBy"/>
      <TextField name="poEntryMemo"/>
      <TextField name="poAppNo"/>
      <TextField name="poLclNo"/>
      <TextField name="poGroupNo"/>
      <TextField name="refSelectedNo"/>
      <TextField name="poPromFlag"/>
      <TextField name="poWholePromCode"/>
      <TextField name="poPriceGroupType"/>
      <TextField name="poPriceAttr"/>
      <TextField name="poReturnRestrictFlag"/>
      <TextField name="paymentTotalAmt"/>
      <TextField name="paymentStatus"/>
      <TextField name="paymentTime" renderer={({text}) => formatDate(text)}/>
      <TextField name="paymentDocNo"/>
      <TextField name="paymentMemo"/>

      <TextField name="sapPostingFlag"/>
      <TextField name="sapPostingDate"/>
      <TextField name="sapPostingDocNo"/>
      <TextField name="comments"/>
      <TextField name="lastModifiedDate" renderer={({text}) => formatDate(text)}/>
      <TextField name="lastModifiedBy"/>
      <TextField name="poOweNo"/>
      <TextField name="orderDealerBranchNo"/>
      <TextField name="orderDesc"/>
      <TextField name="companyNo"/>
      <TextField name="profitCenterNo"/>
      <TextField name="orderCustomerNo"/>
      <TextField name="totalWeight"/>
      <TextField name="totalCalcRebateDs"/>
      <TextField name="totalVatAmt"/>
      <TextField name="totalConsumTaxAmt"/>
      <TextField name="totalCompreTaxAmt"/>
      <TextField name="totalRebateDisAmt"/>
      <TextField name="poEntryStoreNo"/>
    </Form>
  );
  const PoDetail = () => (
    <Table queryBar="normal" dataSet={orderDetailDataSet}>
      <Column name="poNo" renderer={({record, text}) => (
        <a href={`/#/order/process-list/${record.get('poNo')}`}>{`${text}`}</a>
      )}/>
      <Column name="lineNo"/>
      <Column name="productCode"/>
      <Column name="productType"/>
      <Column name="productAttr"/>
      <Column name="saleQty"/>
      <Column name="salePrice"/>
      <Column name="saleRebate"/>
      <Column name="salePoint"/>
      <Column name="productPrice"/>
      <Column name="productPoint"/>
      <Column name="productBomCode"/>
      <Column name="promCode"/>
      <Column name="promProductAttr"/>
      <Column name="comments"/>
      <Column name="lastModifiedDate" renderer={({text}) => formatDate(text)}/>
      <Column name="lastModifiedBy"/>
      <Column name="productUnitWeight"/>
      <Column name="isDirectSale"/>
      <Column name="vatRate"/>
      <Column name="consumTaxRate"/>

      <Column name="compreTaxRate"/>
      <Column name="transportAmt"/>
      <Column name="vatAmt"/>
      <Column name="consumTaxAmt"/>
      <Column name="compreTaxAmt"/>
      <Column name="promPrice"/>
      <Column name="promPoint"/>
      <Column name="promQty"/>
      <Column name="promRebate"/>
    </Table>
  );
  const PoDetailDiscounts = () => (
    <Table queryBar="normal" queryFieldsLimit={4} dataSet={detailDiscountDataSet}>
      <Column name="lineNo"/>
      <Column name="productCode"/>
      <Column name="productAttr"/>
      <Column name="rebateDisAmtProduct"/>
      <Column name="rebateDisAmtCoupon"/>
      <Column name="rebateDisAmtWhole"/>
      <Column name="companyDisAmtProduct"/>
      <Column name="companyDisAmtCoupon"/>
      <Column name="companyDisAmtWhole"/>
      <Column name="comments"/>
    </Table>
  );
  const PosAccTranDetail = () => (
    <Table queryBar="normal" queryFieldsLimit={4} dataSet={detailAccTranDataSet} size="small">
      <Column name="posNo"/>
      <Column name="posStoreNo"/>
      <Column name="posUserName"/>
      <Column name="posTranType"/>
      <Column name="posTranAmt"/>
      <Column name="posPaymentType"/>
      <Column name="posTranStatus"/>
      <Column name="posTranDate" renderer={({text}) => formatDate(text)}/>
      <Column name="refDocNo"/>
      <Column name="posAckBy"/>
      <Column name="sapPostingFlag"/>
      <Column name="sapPostingDate" renderer={({text}) => formatDate(text)}/>
      <Column name="sapPostingDocNo"/>
      <Column name="posAckTime" renderer={({text}) => formatDate(text)}/>
      <Column name="comments"/>
    </Table>
  );
  const PoPaymentDetail = () => (
    <Table queryBar="normal" queryFieldsLimit={4} dataSet={detailPaymentDataSet}>
      <Column name="poNo"/>
      <Column name="poPaymentType"/>
      <Column name="poPaymentAmt"/>
      <Column name="poPaymentDealerNo"/>
      <Column name="accTranType"/>
      <Column name="epayAgentCode"/>
      <Column name="epayCardNo"/>
      <Column name="discountPoint"/>
      <Column name="refTranInfo"/>
      <Column name="comments"/>
    </Table>
  );
  const PoInvoiceInfo = () => (
    <Table queryBar="normal" queryFieldsLimit={4} dataSet={invoiceInfoDataSet}>
      <Column name="poAppNo"/>
      <Column name="dealerNo"/>

      <Column name="invoiceType"/>
      <Column name="invoiceTitle"/>
      <Column name="invoiceMobile"/>
      <Column name="invoiceEmail"/>
      <Column name="invoicePhotoId"/>
      <Column name="taxPayerCode"/>

      <Column name="recipientName"/>
      <Column name="recipientAreaCode"/>
      <Column name="recipientAddrTail"/>
      <Column name="recipientTele"/>
      <Column name="invoiceAddrTail"/>

      <Column name="bankName"/>
      <Column name="bankAccount"/>
      <Column name="contactTele"/>
      <Column name="invoiceStatus"/>
      <Column name="invoiceAppCount"/>
    </Table>
  );
  const MkpDlpPoRelate = () => (
    <Table queryBar="normal" queryFieldsLimit={4} dataSet={mkpDlpPoRelateDataSet}>
      <Column name="poNo"/>
      <Column name="storeNo"/>
      <Column name="dealerNo"/>
      <Column name="deliveryType"/>
      <Column name="cashAmt"/>
      <Column name="totalDlpAmt"/>
      <Column name="totalDiscountAmt"/>
      <Column name="transportAmt"/>
      <Column name="comments"/>

    </Table>
  );

  return (
    <Page>

      <Header backPath={`/order/order-list${location.search}`} backText="返回订单列表">
        <Button
          icon="refresh"
          onClick={async () => {
            await orderMasterDataSet.query();
            await orderDetailDataSet.query();
            await detailDiscountDataSet.query();
            await detailAccTranDataSet.query();
            await detailPaymentDataSet.query();
            await invoiceInfoDataSet.query();
            await mkpDlpPoRelateDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
      </Header>
      <Content>
        <h3>基本信息</h3>
        <FormInfo/>
        <br/>
        <br/>
        <h3>订单明细</h3>
        <Tabs
          defaultActiveKey="poDetail"
          onChange={(key) => {
            console.log(key);
          }}
        >
          <TabPane tab={`订货信息明细 (${orderDetailDataSet.totalCount})`} key="poDetail">
            <PoDetail/>
          </TabPane>

          <TabPane tab={`订货折扣明细 (${detailDiscountDataSet.totalCount})`} key="poDetailDiscounts">
            <PoDetailDiscounts/>
          </TabPane>

          <TabPane tab={`订货支付明细 (${detailPaymentDataSet.totalCount})`} key="poPaymentDetail">
            <PoPaymentDetail/>
          </TabPane>

          <TabPane tab={`订货发票明细 (${invoiceInfoDataSet.totalCount})`} key="poInvoiceInfo">
            <PoInvoiceInfo/>
          </TabPane>

          <TabPane tab={`自营店交易流水 (${detailAccTranDataSet.totalCount})`} key="posAccTranDetail">
            <PosAccTranDetail/>
          </TabPane>

          <TabPane tab={`纷享荟订单积分明细 (${mkpDlpPoRelateDataSet.totalCount})`} key="mkpDlpPoRelate">
            <MkpDlpPoRelate/>
          </TabPane>

        </Tabs>
      </Content>
    </Page>
  );
});

export default (props) => (
  <StoreProvider {...props}>
    <Detail/>
  </StoreProvider>
);

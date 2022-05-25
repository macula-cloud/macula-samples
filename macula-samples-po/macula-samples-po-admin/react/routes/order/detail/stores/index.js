import React, {createContext, useContext, useMemo, useEffect} from 'react';
import {inject} from 'mobx-react';
import {injectIntl} from 'react-intl';
import {DataSet} from 'choerodon-ui/pro';
import OrderDetailDataSet from './OrderDetailDataSet';
import OrderMasterDataSet from './OrderMasterDataSet';

import DetailAccTranDataSet from './other/DetailAccTranDataSet';
import DetailDiscountDataSet from './other/DetailDiscountDataSet';
import DetailPaymentDataSet from './other/DetailPaymentDataSet';
import InvoiceInfoDataSet from './other/InvoiceInfoDataSet';
import MkpDlpPoRelateDataSet from './other/MkpDlpPoRelateDataSet';


const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: {currentMenuType: {id, organizationId}},
      match: {params: {poNo}},
      children,
    } = props;
    console.log(`~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~${poNo}~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`)
    const orderDetailDataSet = useMemo(() => new DataSet(OrderDetailDataSet({poNo})), [poNo]);
    const orderMasterDataSet = useMemo(() => new DataSet(OrderMasterDataSet({poNo})), [poNo]);

    const detailAccTranDataSet = useMemo(() => new DataSet(DetailAccTranDataSet({poNo})), [poNo]);
    const detailDiscountDataSet = useMemo(() => new DataSet(DetailDiscountDataSet({poNo})), [poNo]);
    const detailPaymentDataSet = useMemo(() => new DataSet(DetailPaymentDataSet({poNo})), [poNo]);
    const invoiceInfoDataSet = useMemo(() => new DataSet(InvoiceInfoDataSet({poNo})), [poNo]);
    const mkpDlpPoRelateDataSet = useMemo(() => new DataSet(MkpDlpPoRelateDataSet({poNo})), [poNo]);


    const value = {
      ...props,
      orderDetailDataSet,
      orderMasterDataSet,
      detailAccTranDataSet,
      detailDiscountDataSet,
      detailPaymentDataSet,
      invoiceInfoDataSet,
      mkpDlpPoRelateDataSet,
      prefixCls: 'order-detail',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

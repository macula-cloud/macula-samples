/* eslint-disable no-console */
import React, {createContext, useContext, useMemo, useEffect} from 'react';
import {inject} from 'mobx-react';
import {injectIntl} from 'react-intl';
import {DataSet} from 'choerodon-ui/pro';
import OneProcessDataSet from './OneProcessDataSet';
import ProcessDetailDataSet from './ProcessDetailDataSet';
import McSerInvokeLogDataSet from './McSerInvokeLogDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      match: {params: {poNo}},
      children,
    } = props;
    console.log(`~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~${props}~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`);
    const oneProcessDataSet = useMemo(() => new DataSet(OneProcessDataSet({poNo})), [poNo]);
    const processDetailDataSet = useMemo(() => new DataSet(ProcessDetailDataSet({poNo})), [poNo]);
    const mcSerInvokeLogDataSet = useMemo(() => new DataSet(McSerInvokeLogDataSet({poNo})), [poNo]);

    const value = {
      ...props,
      oneProcessDataSet,
      processDetailDataSet,
      mcSerInvokeLogDataSet,
      prefixCls: 'process-detail',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

import React, {createContext, useContext, useMemo, useEffect} from 'react';
import {inject} from 'mobx-react';
import {injectIntl} from 'react-intl';
import {DataSet} from 'choerodon-ui/pro';
import MessageLevelDataSet from './MessageLevelDataSet';
import SupplierUserDataSet from './SupplierUserDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: {currentMenuType: {id, organizationId}},
      children,
    } = props;

    const messageLevelDataSet = useMemo(() => new DataSet(MessageLevelDataSet()), []);
    const supplierUserDataSet = useMemo(() => new DataSet(SupplierUserDataSet(id)), []);

    const value = {
      ...props,
      messageLevelDataSet,
      supplierUserDataSet,
      prefixCls: 'scc-order',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

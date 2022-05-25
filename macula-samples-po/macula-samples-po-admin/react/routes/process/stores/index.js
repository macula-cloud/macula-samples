import React, { createContext, useContext, useMemo, useEffect } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import OrderProcessDataSet from './OrderProcessDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: { currentMenuType: { id, organizationId } },
      children,
    } = props;

    const orderProcessDataSet = useMemo(() => new DataSet(OrderProcessDataSet()), []);

    const value = {
      ...props,
      orderProcessDataSet,
      prefixCls: 'process-order',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

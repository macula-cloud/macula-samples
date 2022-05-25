import React, {createContext, useContext, useMemo, useEffect} from 'react';
import {inject} from 'mobx-react';
import {injectIntl} from 'react-intl';
import {DataSet} from 'choerodon-ui/pro';
import OrderDataSet from './OrderDataSet';


const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: {currentMenuType: {id, organizationId}},
      children,
    } = props;


    const orderDataSet = useMemo(() => new DataSet(OrderDataSet()), []);

    const value = {
      ...props,
      orderDataSet,
      prefixCls: 'scc-order',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

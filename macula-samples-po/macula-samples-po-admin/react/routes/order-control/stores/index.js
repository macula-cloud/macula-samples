import React, {createContext, useMemo,} from 'react';
import {inject} from 'mobx-react';
import {injectIntl} from 'react-intl';
import {DataSet} from 'choerodon-ui/pro';
import ControlDataSet from './ControlDataSet';

const Stores = createContext();
export default Stores;
export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: {currentMenuType: {id, organizationId}},
      children,
    } = props;


    const controlDataSet = useMemo(() => new DataSet(ControlDataSet()), []);

    const value = {
      ...props,
      controlDataSet,
      prefixCls: 'scc-order',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

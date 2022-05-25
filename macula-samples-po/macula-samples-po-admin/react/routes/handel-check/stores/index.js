import React, { createContext, useContext, useMemo, useEffect } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import CheckMasterDataSet from './CheckMasterDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: { currentMenuType: { id, organizationId } },
      children,
    } = props;

    const checkMasterDataSet = useMemo(() => new DataSet(CheckMasterDataSet()), []);

    const value = {
      ...props,
      checkMasterDataSet,
      prefixCls: 'scc-order',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

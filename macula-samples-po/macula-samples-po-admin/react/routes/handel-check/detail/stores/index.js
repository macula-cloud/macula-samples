/* eslint-disable no-console */
import React, { createContext, useMemo } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import CheckDetailDataSet from './CheckDetailDataSet';
import MasterDataSet from './MasterDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      /* AppState: {currentMenuType: {id, organizationId}}, */
      match: { params: { id } },
      children,
    } = props;
    console.log(`~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~${props}~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`);
    const checkDetailDataSet = useMemo(() => new DataSet(CheckDetailDataSet({ id })), [id]);
    const masterDataSet = useMemo(() => new DataSet(MasterDataSet({ id })), [id]);

    const value = {
      ...props,
      checkDetailDataSet,
      masterDataSet,
      prefixCls: 'order-detail',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));

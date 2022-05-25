import React, { createContext, useContext, useMemo, useEffect } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import AuditReportDataSet from './AuditReportDataSet';
import SupplierDataSet from './SupplierDataSet';
import SupplierUserDataSet from './SupplierUserDataSet';
import SupplierDetailDataSet from './SupplierDetailDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: { currentMenuType: { id, organizationId } },
      children,
    } = props;


    const supplierDataSet = useMemo(() => new DataSet(SupplierDataSet()), []);
    const supplierUserDataSet = useMemo(() => new DataSet(SupplierUserDataSet()), []);
    const supplierDetailDataSet = useMemo(() => new DataSet(SupplierDetailDataSet({ supplierDataSet, supplierUserDataSet })), []);
    const auditReportDataSet = useMemo(() => new DataSet(AuditReportDataSet({ supplierDataSet, supplierUserDataSet })), [supplierUserDataSet]);


    const value = {
      ...props,
      auditReportDataSet,
      supplierDataSet,
      supplierDetailDataSet,
      supplierUserDataSet,
      prefixCls: 'scc-supplier',
    };

    return (
      <Stores.Provider value={value}>
        { children }
      </Stores.Provider>
    );
  },
));

import React, { createContext, useContext, useMemo, useEffect } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import QuestionnaireDataSet from './QuestionnaireDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: { currentMenuType: { id, organizationId } },
      children,
    } = props;


    const questionnaireDataSet = useMemo(() => new DataSet(QuestionnaireDataSet()), []);

    const value = {
      ...props,
      questionnaireDataSet,
      prefixCls: 'scc-questionnaire',
    };

    return (
      <Stores.Provider value={value}>
        { children }
      </Stores.Provider>
    );
  },
));

import React, { createContext, useContext, useMemo, useEffect } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import QuestionnaireDetailDataSet from './QuestionnaireDetailDataSet';
import QuestionnaireUserDataSet from './QuestionnaireUserDataSet';
import UserDataSet from './UserDataSet';

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: { currentMenuType: { id, organizationId } },
      match: { params: { questionnaireId } },
      children,
    } = props;


    const questionnaireUserDataSet = useMemo(() => new DataSet(QuestionnaireUserDataSet()), []);
    const userDataSet = useMemo(() => new DataSet(UserDataSet({ questionnaireId })), [questionnaireId]);
    const questionnaireDetailDataSet = useMemo(() => new DataSet(QuestionnaireDetailDataSet({ questionnaireId, questionnaireUserDataSet })), []);

    const value = {
      ...props,
      questionnaireId,
      questionnaireDetailDataSet,
      questionnaireUserDataSet,
      userDataSet,
      prefixCls: 'scc-questionnaire-detail',
    };

    return (
      <Stores.Provider value={value}>
        { children }
      </Stores.Provider>
    );
  },
));

import React, { useContext, useMemo } from 'react';
import { withRouter } from 'react-router-dom';
import { asyncLocaleProvider } from '@buildrun/utils';
import { Route, Switch, asyncRouter, nomatch } from '@buildrun/boot';
import { configure, getConfig } from 'choerodon-ui';
import './index.less';

configure({ tableBorder: false });

const AuditReport = asyncRouter(() => import('./routes/audit-report'));
const Questionnaire = asyncRouter(() => import('./routes/questionnaire'));
const QuestionnaireSetting = asyncRouter(() => import('./routes/questionnaire-setting'));
const Home = asyncRouter(() => import('./routes/home'));

export default withRouter(({ match }) => {
    const IntlProviderAsync = useMemo(() => asyncLocaleProvider('zh_CN', () => import(`./locale/zh_CN`)), []);

    return (
        <IntlProviderAsync>
            <Switch>
                <Route path={`${match.url}/audit-report`} component={AuditReport} />
                <Route path={`${match.url}/questionnaire`} component={Questionnaire} />
                <Route path={`${match.url}/questionnaire-setting`} component={QuestionnaireSetting} />
                <Route path={`${match.url}/home`} component={Home} />
                <Route path="*" component={nomatch} />
            </Switch>
        </IntlProviderAsync>
    )
});

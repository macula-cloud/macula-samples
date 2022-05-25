import React, {useContext, useMemo} from 'react';
import {Route, Switch, withRouter} from 'react-router-dom';
import {asyncLocaleProvider} from '@buildrun/utils';
import {asyncRouter, nomatch} from '@buildrun/boot';
import {configure, getConfig} from 'choerodon-ui';
import './index.less';

configure({tableBorder: false});

const OrderList = asyncRouter(() => import('./routes/order'));
const MessageList = asyncRouter(() => import('./routes/message'));
const CheckList = asyncRouter(() => import('./routes/check'));
const processList = asyncRouter(() => import('./routes/process'));
const orderControl = asyncRouter(() => import('./routes/order-control'));
const handleCheck = asyncRouter(() => import('./routes/handel-check'));

export default withRouter(({match}) => {
  const IntlProviderAsync = useMemo(() => asyncLocaleProvider('zh_CN', () => import(`./locale/zh_CN`)), []);

  return (
    <IntlProviderAsync>
      <Switch>
        <Route path={`${match.url}/order-list`} component={OrderList}/>
        <Route path={`${match.url}/message-list`} component={MessageList}/>
        <Route path={`${match.url}/check-list`} component={CheckList}/>
        <Route path={`${match.url}/process-list`} component={processList}/>
        <Route path={`${match.url}/order-control`} component={orderControl}/>
        <Route path={`${match.url}/handle-check-list`} component={handleCheck}/>
        <Route path="*" component={nomatch}/>
      </Switch>
    </IntlProviderAsync>
  )
});

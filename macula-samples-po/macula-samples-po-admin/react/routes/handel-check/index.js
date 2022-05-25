import React from 'react';
import {ModalProvider} from 'choerodon-ui/pro';
import {Route, Switch} from 'react-router-dom';
import {nomatch} from '@buildrun/boot';
import {asyncRouter} from '@buildrun/utils';
import {StoreProvider} from './stores';
import './index.less';

const list = asyncRouter(() => import('./list'));
const detail = asyncRouter(() => import('./detail'));

export default (props) => {
  const {match} = props;
  return (
    <StoreProvider {...props}>
      <ModalProvider>
        <Switch>
          <Route exact path={`${match.url}`} component={list}/>
          <Route exact path={`${match.url}/:id`} component={detail}/>
          <Route path="*" component={nomatch}/>
        </Switch>
      </ModalProvider>
    </StoreProvider>
  );
};

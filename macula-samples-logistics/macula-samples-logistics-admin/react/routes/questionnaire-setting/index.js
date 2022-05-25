import React from 'react';
import Iframe from 'react-iframe';
import './index.less';

export default (props) => (
  <Iframe
    url={`${window.location.origin}/ques`}
    id="myId"
    width="100%"
    className="no-border"
    display="initial"
    position="relative"
  />
);

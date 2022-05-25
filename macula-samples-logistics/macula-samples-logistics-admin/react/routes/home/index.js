import React, { useContext, useState, useEffect } from 'react';
import { observer, useObserver } from 'mobx-react-lite';
import { TabPage, axios, Content, Header, Choerodon } from '@buildrun/boot';
import { Collapse } from 'choerodon-ui';
import './index.less';

const { Panel } = Collapse;


export default observer(() => {
  const a = 1;
  const [data, setData] = useState([]);
  useEffect(() => {
    async function getData() {
      const res = await axios.get('supplier/v1/index');
      if (!res.failed) {
        setData(res);
      }
    }
    getData();
  }, []);
  return (
    <TabPage>
      <Content className="scc-home">
        <Collapse bordered={false} defaultActiveKey={['1']}>
          <Panel header="已有产品" key="1">
            <div className="scc-home-content">
              {data.map(v => {
                const url = v.type === 'MO' ? `${v.url}?access_token=${Choerodon.getAccessToken().split(' ')[1]}` : v.url;
                return (
                  <a href={url} target="_blank" rel="noopener noreferrer" className="scc-home-card">
                    <div className="scc-home-card-icon">{v.name[0]?.toUpperCase()}</div>
                    <div className="scc-home-card-desc">{v.name}</div>
                  </a>
                );
              })}
            </div>
          </Panel>
        </Collapse>
      </Content>
    </TabPage>
  );
});

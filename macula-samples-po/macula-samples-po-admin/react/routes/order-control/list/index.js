/* eslint-disable eqeqeq */
import React, {useContext} from 'react';
import {Switch, Form, Output, message, Table, Button, Tooltip, Modal, Select} from 'choerodon-ui/pro';
import {observer} from 'mobx-react-lite';
import {TabPage, axios, Content, Header} from '@buildrun/boot';
import Store from '../stores';
import './index.less';

const {Column} = Table;
export default observer(() => {
  const {controlDataSet} = useContext(Store);

  function toggleEnabled(flag, record) {
    Modal.open({
      title: `${flag === 'true' ? '停用' : '启用'}`,
      children: `是否确认${flag === 'true' ? '停用' : '启用'}"  ${record.get('paraCode')}"？`,
      onOk: async () => {
        try {
          const res = await axios.put(`/order/v1/order_control/${record.get('id')}/${flag === 'true' ? '0' : '1'}`);
          if (!res.failed) {
            message.info('操作成功');
          }
        } catch (e) {
          message.error(e.message);
        } finally {
          controlDataSet.query();
        }
      },
      onCancel: () => controlDataSet.query()
    });
  }

  return (
    <TabPage>
      <header style={{
        background: '#edfcff',
        height: '80px',
      }}>
        <Button
          icon="refresh"
          onClick={async () => {
            await controlDataSet.query();
            message.info('刷新完成');
          }}
        >刷新
        </Button>
        <div align="center" style={{
          fontSize: 20,
          color: '#c37dff',
          marginTop: '10px',
          marginBottom: '10px',
        }}>订单中心MQ监听及定时任务控制
        </div>
      </header>
      <Content>
        <div>
          <Table queryBar="bar" dataSet={controlDataSet}>
            <Column name="paraSystem" width={100} align="center"/>
            <Column name="paraCode" width={260}/>
            <Column name="paraValue"/>
            <Column name="paraType"/>
            <Column name="paraScope"/>
            <Column name="paraDesc" width={210}
                    renderer={({text}) => (<span style={{color: '#d18eff'}}>{text}</span>)}/>
            <Column name="paraDefault" align="center"/>
            <Column name="operation" renderer={({record}) => (
              <Tooltip placement="top" title={record.get('paraValue') === 'true' ? '启用' : '停用'}>
                <Switch name="paraValue" value={record.get('paraValue')}
                        onClick={() => toggleEnabled(record.get('paraValue'), record)}
                        defaultChecked={record.get('paraValue') === 'true' ? true : false}
                        unCheckedChildren="OFF">ON</Switch>
              </Tooltip>
            )}/>
          </Table>
        </div>
      </Content>
    </TabPage>
  );
});

import { Layout, Menu } from 'antd';
import * as React from 'react';
import '~/styles/App.less';

export class App extends React.Component {
  public render() {

    const { Header, Content, Footer } = Layout;

    return (
      <Layout>
        <Header className="site-header">
          <div className="logo">
            <span><a href="/">HR</a></span>
          </div>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
            <Menu.Item key="1">Jobs</Menu.Item>
            <Menu.Item key="2">Employees</Menu.Item>
          </Menu>
        </Header>
        <Content className="site-content">
          Hello, World!
        </Content>
        <Footer className="site-footer">
          SpringBoot React HR Sample Application | Created by Alvin Sim
				</Footer>
      </Layout>
    );
  }
}

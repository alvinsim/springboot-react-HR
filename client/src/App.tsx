import { SolutionOutlined, TeamOutlined } from '@ant-design/icons';
import { Layout, Menu } from 'antd';
import Employees from '~/employees';
import Jobs from '~/jobs';
import * as React from 'react';
import '~/common/App.less';

interface IProps {
  selectedMenuItem: string
}

interface IState {
  current: string
}

export class App extends React.Component<IProps, IState> {
  constructor(props: IProps) {
    super(props);

    const { selectedMenuItem } = props;

    this.state = {
      current: selectedMenuItem,
    };

    this.onClickMenu = this.onClickMenu.bind(this);
  }

  public static defaultProps: Partial<IProps> = {
    selectedMenuItem: 'jobs'
  };

  public render() {
    const { Header, Content, Footer } = Layout;
    const { current } = this.state;

    const contentDetails: React.ReactNode = this.getContentDetails(current);

    return (
      <Layout>
        <Header className="site-header">
          <div className="logo">
            <span><a href="/">HR</a></span>
          </div>
          <Menu defaultSelectedKeys={[current]} mode="horizontal" onClick={this.onClickMenu} theme="dark" >
            <Menu.Item key="jobs" icon={<SolutionOutlined />}>Jobs</Menu.Item>
            <Menu.Item key="employees" icon={<TeamOutlined />}>Employees</Menu.Item>
          </Menu>
        </Header>
        <Content className="site-content">
          {contentDetails}
        </Content>
        <Footer className="site-footer">
          SpringBoot React HR Sample Application | Created by Alvin Sim
				</Footer>
      </Layout>
    );
  }

  private onClickMenu(event: React.MouseEvent) {
    console.log('[DEBUG] key: ' + event.key);
    this.setState({ current: event.key });
  }

  private getContentDetails(menuKeys: string) {
    switch (menuKeys) {
      case 'jobs': return <Jobs />;
      case 'employees': return <Employees />;
    }
  }
}

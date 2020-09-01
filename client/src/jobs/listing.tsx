import { Table, Typography } from 'antd';
import * as React from 'react';
import { Job } from '~/common/types';

interface Props {
  data?: Job[];
}

const Listing: React.FC<Props> = (props: Props): React.ReactElement => {
  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id'
    },
    {
      title: 'Job Title',
      dataIndex: 'jobTitle',
      key: 'jobTitle'
    },
    {
      title: 'Min. Salary',
      dataIndex: 'minSalary',
      key: 'minSalary'
    },
    {
      title: 'Max. Salary',
      dataIndex: 'maxSalary',
      key: 'maxSalary'
    }
  ];

  const { Title } = Typography;
  const { data = [] } = props;
  const tableData = data.map(job => {
    const { id } = job;
    return ({
      key: id,
      ...job
    });
  });

  return (
    <React.Fragment>
      <Title level={2}>Listing</Title>
      <Table columns={columns} dataSource={tableData} />
    </React.Fragment>
  );
};


export default Listing;

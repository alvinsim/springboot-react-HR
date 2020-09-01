import { Typography } from 'antd';
import React, { FunctionComponent, ReactElement, useEffect } from 'react';
import { connect } from 'react-redux';
import { Job } from '~/common/types';
import { getJobs } from './actions';
import Listing from './listing';
import { selectJobs } from './selectors';
import { Dispatch, State } from './types';

const { Title } = Typography;

interface Props {
  jobs?: Job[];
  getJobs: () => Job[];
}

const Jobs: FunctionComponent<Props> = (props: Props): ReactElement => {
  let { jobs = [] } = props;

  useEffect(() => {
    getJobsHandler();
  }, []);

  const getJobsHandler = () => {
    const { getJobs } = props;
    jobs = getJobs();
  }

  return (
    <React.Fragment>
      <Title>Jobs</Title>
      <Listing data={jobs} />
    </React.Fragment>
  );
};

const mapStateToProps = (state: State) => ({
  jobs: selectJobs(state),
});

const mapDispathToProps = (dispatch: Dispatch) => ({
  getJobs: () => dispatch(getJobs())
});

export default connect(mapStateToProps, mapDispathToProps)(Jobs);

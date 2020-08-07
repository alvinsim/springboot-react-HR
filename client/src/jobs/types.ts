import { GET_JOBS } from './actions';
import { Job } from '~/common/types';

export interface State {
  jobs: Job[],
}

export interface GetJobsAction {
  jobs: Job[],
  type: GET_JOBS,
}

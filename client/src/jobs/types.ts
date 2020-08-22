import { GET_JOBS } from './actions';
import { Job } from '~/common/types';

export interface State {
  jobs: Job[];
  loading: boolean;
  message: string;
  status: string;
}

interface GetJobsAction {
  type: GET_JOBS;
}

interface FetchedJobsAction {
  jobs: Job[];
  loading: boolean;
  message: string;
  status: string;
  type: FETCHED_JOBS;
}

interface InitJobsAction {
  type: INIT_JOBS;
}

export type JobsActionTypes =
  ReturnType<typeof GetJobsAction | typeof FetchedJobsAction | typeof InitJobsAction>;

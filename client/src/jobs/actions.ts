import { GET_JOBS } from './actions';
import { GetJobsAction } from './types';

export const GET_JOBS = 'GET_JOBS';

export function getJobs(): GetJobsAction {
  return {
    jobs,
    type: GET_JOBS,
  };
}

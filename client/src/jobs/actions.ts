import { Message } from '~/common/types';
import { JobsActionTypes } from './types';

export const GET_JOBS = 'GET_JOBS';
export const GET_JOBS_TRIGGER = 'GET_JOBS_TRIGGER';
export const FETCHED_JOBS = 'FETCHED_JOBS';
export const INIT_JOBS = 'INIT_JOBS';

export function getJobs(): JobsActionTypes {
  return {
    type: GET_JOBS,
  };
}

export function fetchedJobs(action: Message): JobsActionTypes {
  const { data, message, status } = action;
  //  const jobs = (data!.jobs) ? data.jobs : [];
  const jobs = (undefined !== data && undefined !== data.jobs) ? data.jobs : [];

  return {
    jobs,
    message,
    status,
    type: FETCHED_JOBS
  }
}

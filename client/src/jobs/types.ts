import { GET_JOBS, FETCHED_JOBS, INIT_JOBS } from './actions';
import { Job } from '~/common/types';

export interface State {
    jobs: Job[];
    loading: boolean;
    message: string;
    status: string;
}

interface GetJobs {
    type: GET_JOBS;
}

interface FetchedJobs {
    jobs: Job[];
    loading: boolean;
    message: string;
    status: string;
    type: FETCHED_JOBS;
}

interface InitJobs {
    type: INIT_JOBS;
}

export type Dispatch = (action: ReturnType<typeof GetJobs>) => void;

export type JobsAction =
    ReturnType<typeof GetJobs | typeof FetchedJobs | typeof InitJobs>;

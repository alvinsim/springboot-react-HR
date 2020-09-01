import { Message } from '~/common/types';
import { JobsAction } from './types';

export const GET_JOBS = 'GET_JOBS';
export const GET_JOBS_TRIGGER = 'GET_JOBS_TRIGGER';
export const FETCHED_JOBS = 'FETCHED_JOBS';
export const INIT_JOBS = 'INIT_JOBS';

export function getJobs(): JobsAction {
    return { type: GET_JOBS };
}

export function fetchedJobs(action: Message): JobsAction {
    const { data, message, status } = action;

    return {
        data,
        message,
        status,
        type: FETCHED_JOBS
    }
}

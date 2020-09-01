import { State } from './types';

export function selectJobs(state: State) {
    const { jobs = [] } = state;
    return jobs;
}

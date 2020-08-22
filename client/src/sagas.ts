import { all, call, put, takeEvery } from 'redux-saga/effects';
import { GET_JOBS, fetchedJobs } from '~/jobs/actions';
import { fetchJobs } from '~/jobs/services';

export function* getJobsHandler() {
    const payload = yield call(fetchJobs);
    yield put(fetchedJobs(payload));
}

export function* main() {
    yield all([
        takeEvery(GET_JOBS, getJobsHandler),
    ]);
}

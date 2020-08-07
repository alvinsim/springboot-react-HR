import { all, put, takeEvery } from 'redux-saga/effects';
import { GET_JOBS, getJobs } from './actions';

export function* getJobsTrigger(action) {
  const { jobs } = action;
  yield put(getJobs(jobs));
}

export function* main() {
  yield all([
    takeEvery(GET_JOBS, getJobsTrigger),
  ]);
}

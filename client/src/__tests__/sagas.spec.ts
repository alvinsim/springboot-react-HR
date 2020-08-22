import { call } from 'redux-saga/effects';
import { expectSaga } from 'redux-saga-test-plan';
import { fetchedJobs } from '~/jobs/actions';
import { fetchJobs } from '~/jobs/services';
import { getJobsHandler } from '~/sagas';

it('getJobHandler', () => {
    const payload = {
        jobs: [
            {
                id: 1,
                job_title: 'Programmer',
                min_salary: 4000.00,
                max_salary: 5000.00
            }
        ],
        loading: true,
        message: '',
        status: 'success'
    };

    return expectSaga(getJobsHandler)
        .provide([
            [call(fetchJobs), payload]
        ])
        .put(fetchedJobs(payload))
        .run();
});

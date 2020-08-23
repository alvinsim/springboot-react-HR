import { call, take } from 'redux-saga/effects';
import { expectSaga } from 'redux-saga-test-plan';
import { fetchedJobs, GET_JOBS } from '~/jobs/actions';
import { fetchJobs } from '~/jobs/services';
import { getJobsHandler, main } from '~/sagas';

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

    return expectSaga(main)
        .dispatch({ type: GET_JOBS })
        .provide([
            [call(fetchJobs), payload]
        ])
        .put(fetchedJobs(payload))
        .run();
});

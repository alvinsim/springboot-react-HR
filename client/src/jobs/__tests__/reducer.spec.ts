import { GET_JOBS, FETCHED_JOBS, INIT_JOBS } from '~/jobs/actions';
import reducer from '~/jobs/reducer';
import { State } from '~/jobs/types';

describe('Job Reducer test', () => {
    const initialState: State = {
        jobs: [],
        loading: false,
        message: '',
        status: '',
    };

    // Action Type: INIT_JOBS
    it('should handle non-defined types', () => {
        expect(reducer(undefined, { type: INIT_JOBS })).toEqual(initialState);
    });

    // Action Type: GET_JOBS
    it('should handle GET_JOBS action when the jobs array is empty', () => {
        expect(reducer(initialState, { type: GET_JOBS })).toEqual(
            {
                ...initialState,
                loading: true
            }
        );
    });

    it('should handle GET_JOBS action when the jobs array is not empty', () => {
        const currentState = {
            ...initialState,
            jobs: [
                {
                    id: 100,
                    job_title: "Accountant",
                    min_salary: 8000.00,
                    max_salary: 9000.00
                }
            ]
        };
        expect(reducer(currentState, { type: GET_JOBS })).toEqual(
            {
                ...currentState,
                loading: true
            }
        );
    });

    // Action Type: FETCHED_JOBS
    it('should handle FETCHED_JOBS action when state is in an initial state', () => {
        expect(
            reducer(initialState, {
                data: [],
                message: 'Some error',
                status: 'error',
                type: FETCHED_JOBS
            })
        ).toEqual({
            jobs: [],
            loading: false,
            message: 'Some error',
            status: 'error'
        });
    });

    it('should handle FETCHED_JOBS action when state is not in an initial state', () => {
        expect(
            reducer(
                {
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
                }, {
                data: [
                    {
                        id: 10,
                        job_title: "DBA",
                        min_salary: 5000.00,
                        max_salary: 5500.00
                    },
                    {
                        id: 11,
                        job_title: "Accountant",
                        min_salary: 4000.00,
                        max_salary: 6000.00
                    }
                ],
                message: '',
                status: 'success',
                type: FETCHED_JOBS
            })
        ).toEqual({
            jobs: [
                {
                    id: 10,
                    job_title: 'DBA',
                    min_salary: 5000.00,
                    max_salary: 5500.00
                },
                {
                    id: 11,
                    job_title: 'Accountant',
                    min_salary: 4000.00,
                    max_salary: 6000.00
                }
            ],
            loading: false,
            message: '',
            status: 'success'
        });
    });
});

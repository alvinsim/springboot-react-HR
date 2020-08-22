import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();

import { fetchJobs } from '~/jobs/services';

describe('Job service test', () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    it('Successful call that returns an array of jobs', () => {
        const expectedResponse = {
            data: {
                jobs: [
                    {
                        id: 100,
                        job_title: "Manager",
                        min_salary: 10000.00,
                        max_salary: 12000.00
                    }
                ]
            },
            message: "",
            status: "success"
        };
        fetchMock.mockResponse(JSON.stringify(expectedResponse));

        fetchJobs().then(res => {
            expect(res).toEqual(expectedResponse);
        });
    });

    it('Failure call', () => {
        const expectedResponse = {
            data: {},
            message: "Some error happened",
            status: "error"
        };

        fetchMock.mockReject(_ => Promise.reject('Some error happened'));

        fetchJobs().then(res => {
            expect(res).toEqual(expectedResponse);
        });
    });
})

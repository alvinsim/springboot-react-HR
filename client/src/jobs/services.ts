import { BASE_URL } from '~/common/baseServices';
import { Message } from '~/common/types';

export async function fetchJobs(): Promise<Message> {
    const getUrl = 'jobs/';
    const apiUrl = `${BASE_URL}/${getUrl}`;

    const responseData = {
        data: {},
        message: '',
        status: ''
    };

    return await fetch(apiUrl, { method: 'GET' })
        .then(res => res.json())
        .catch(err => ({
            ...responseData,
            message: err,
            status: 'error'
        }));
}

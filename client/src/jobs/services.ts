import { BASE_URL } from '~/common/baseServices';
import { Message } from '~/common/types';

export async function fetchJobs(): Promise<Message> {
    const getUrl = 'jobs/';
    const apiUrl = `${BASE_URL}${getUrl}`;

    return fetch(apiUrl, { method: 'GET' })
        .then(res => res.json())
        .catch(err => ({
            data: {},
            message: err,
            status: 'error'
        }));
}

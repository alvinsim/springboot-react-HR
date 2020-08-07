import { BASE_URL } from '~/common/baseServices';

export async function fetchAllJobs() {
		const getUrl: string = 'jobs/';
		const apiUrl: string = `${BASE_URL}${getUrl}`;

		try {
				const response: any = await fetch(apiUrl, { method: 'GET' });
				let data;

				if (response.ok) {
						data = await response.json();
				}

				return data;
		} catch (e) {
				return {
						error: e
				};
		}
}

export interface Employee {
  id: number;
  first_name: string;
  last_name: string;
  email: string;
  phone_number?: string;
  hire_date: string;
  job: Job;
  salary: number;
  manager?: Employee;
  department: {
    id: number;
    department_name: string;
    location: {
      id: number;
      street_address?: string;
      postal_code?: string;
      city: string;
      state_provice?: string;
      country: {
        id: number;
        country_code: string;
        country_name: string;
        region: {
          id: number;
          region_name: string;
        }
      }
    }
  }
}

export interface Job {
  id: number;
  job_title: string;
  min_salary: number;
  max_salary: number;
}

export interface Jobs {
  jobs: Job[];
}

export interface Message {
  status: string;
  data?: any;
  message?: string;
}

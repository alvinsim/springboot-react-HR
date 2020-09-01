import { Reducer } from 'redux';
import { GET_JOBS, FETCHED_JOBS } from './actions';
import { State, JobsAction } from './types';

const initialState: State = {
    jobs: [],
    loading: false,
    message: "",
    status: ""
};

const reducer: Reducer<State> = (state = initialState, action: JobsAction) => {
    switch (action.type) {
        case GET_JOBS:
            return {
                ...state,
                loading: true
            }
        case FETCHED_JOBS: {
            const { data, status, message } = action;

            return {
                jobs: data,
                loading: false,
                message,
                status
            };
        }
        default:
            return state;
    }
};

export default reducer;

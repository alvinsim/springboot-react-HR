import { GET_JOBS } from './actions';
import { Job } from '~/common/types';
import { State } from './types';

const initialState: State = {
  jobs: []
};

const reducer: State = (state: State = initialState, action) => {
  switch (action.type) {
    case GET_JOBS:
      const { jobs } = action;
      return { ...jobs },
    default:
      return state;
  }
};

export default reducer;

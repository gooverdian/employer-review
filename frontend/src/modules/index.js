import { combineReducers } from 'redux';
import { employerSearch } from './employerSearch';
import { errorMessage } from './errorMessage';
import { topEmployers } from './topEmployers';

const rootReducer = combineReducers({
    employerSearch,
    topEmployers,
    errorMessage,
});

export default rootReducer;

import { combineReducers } from 'redux';
import { employerSearch } from './employerSearch';
import { errorMessage } from './errorMessage';

const rootReducer = combineReducers({
    employerSearch,
    errorMessage,
});

export default rootReducer;

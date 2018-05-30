import { combineReducers } from 'redux';
import { employerSearch } from './employerSearch';
import { errorMessage } from './errorMessage';
import { topEmployers } from './topEmployers';
import { employers } from './employers';
import { employerReviews } from './employerReviews';

const rootReducer = combineReducers({
    employerSearch,
    topEmployers,
    errorMessage,
    employers,
    employerReviews,
});

export default rootReducer;

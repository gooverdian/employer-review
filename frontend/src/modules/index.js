import { combineReducers } from 'redux';
import { employerSearch } from './employerSearch';
import { errorMessage } from './errorMessage';
import { topEmployers } from './topEmployers';
import { employers } from './employers';
import { employerReviews } from './employerReviews';
import { mainStatistics } from './mainStatistics';

const rootReducer = combineReducers({
    employerSearch,
    topEmployers,
    errorMessage,
    employers,
    employerReviews,
    mainStatistics,
});

export default rootReducer;

import { combineReducers } from 'redux';
import { employerSearch } from './employerSearch';
import { errorMessage } from './errorMessage';
import { topEmployers } from './topEmployers';
import { employers } from './employers';
import { employerReviews } from './employerReviews';
import { mainStatistics } from './mainStatistics';
import { employerStatistics } from './employerStatistics';

const rootReducer = combineReducers({
    employerSearch,
    topEmployers,
    errorMessage,
    employers,
    employerReviews,
    mainStatistics,
    employerStatistics,
});

export default rootReducer;

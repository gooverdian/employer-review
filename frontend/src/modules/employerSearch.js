import ExchangeInterface from 'components/exchange/ExchangeInterface';
import { camelizeKeys } from 'humps';
import { setErrorMessage } from './errorMessage';

const defaultState = {
    params: {
        text: undefined,
        page: undefined,
    },
    data: {}
};

const SEARCH_EMPLOYERS_SUCCESS = 'EMPLOYER_SEARCH_SUCCESS';
const SEARCH_EMPLOYERS_RESET = 'EMPLOYER_SEARCH_RESET';

export const searchEmployers = (query, page) => {
    return (dispatch) => {
        if (!query) {
            dispatch({ type: SEARCH_EMPLOYERS_RESET });
            return;
        }

        ExchangeInterface.employerSearch(query, page).then(
            (response) => {
                dispatch({
                    type: SEARCH_EMPLOYERS_SUCCESS,
                    payload: {
                        params: {query, page},
                        data: camelizeKeys(response)
                    }
                });
            },
            (error) => {
                dispatch(setErrorMessage(`${error.status} ${error.statusText}`));
            }
        );
    }
};

export const employerSearch = (state = defaultState, action) => {
    switch (action.type) {
        case SEARCH_EMPLOYERS_RESET:
            return defaultState;
        case SEARCH_EMPLOYERS_SUCCESS:
            return action.payload;
        default:
            return state;
    }
};

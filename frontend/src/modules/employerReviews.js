import ExchangeInterface from 'components/exchange/ExchangeInterface';
import settings from 'config/settings';
import { camelizeKeys } from 'humps';
import { setErrorMessage } from './errorMessage';

const defaultState = {};

const GET_EMPLOYER_REVIEWS_SUCCESS = 'GET_EMPLOYER_REVIEWS_SUCCESS';
const RESET_EMPLOYER_REVIEWS = 'RESET_EMPLOYER_REVIEWS';

export const getEmployerReviews = (employerId, reviewType) => {
    return (dispatch) => {
        ExchangeInterface.getReviews(employerId, reviewType).then(
            (response) => {
                dispatch({
                    type: GET_EMPLOYER_REVIEWS_SUCCESS,
                    payload: {
                        employerId: employerId,
                        reviewType: reviewType,
                        response: camelizeKeys(response),
                    },
                });
            },
            (error) => {
                let errorMessage = error ? `${error.status} ${error.statusText}` : settings.unknownErrorMessage;
                dispatch(setErrorMessage(errorMessage));
            }
        );
    }
};

export const resetEmployerReviews = () => {
    return (dispatch) => {
        dispatch({ type: RESET_EMPLOYER_REVIEWS });
    };
};

export const employerReviews = (state = defaultState, action) => {
    switch (action.type) {
        case GET_EMPLOYER_REVIEWS_SUCCESS:
            return Object.assign({}, {
                ...state,
                employerId: action.payload.employerId,
                [action.payload.reviewType]: action.payload.response
            });
        case RESET_EMPLOYER_REVIEWS:
            return defaultState;
        default:
            return state;
    }
};

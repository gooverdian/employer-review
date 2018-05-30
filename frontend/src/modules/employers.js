import ExchangeInterface from 'components/exchange/ExchangeInterface';
import settings from 'config/settings';
import { camelizeKeys } from 'humps';
import { setErrorMessage } from './errorMessage';

const defaultState = {};

const GET_EMPLOYER_SUCCESS = 'GET_EMPLOYER_SUCCESS';
const RESET_EMPLOYER = 'RESET_EMPLOYER';

export const getEmployer = (employerId) => {
    return (dispatch) => {
        ExchangeInterface.getEmployer(employerId).then(
            (response) => {
                dispatch({
                    type: GET_EMPLOYER_SUCCESS,
                    payload: {
                        employerId: employerId,
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

export const resetEmployer = (employerId) => {
    return (dispatch) => {
        dispatch({
            type: RESET_EMPLOYER,
            payload: {
                employerId: employerId
            },
        });
    };
};


export const employers = (state = defaultState, action) => {
    switch (action.type) {
        case GET_EMPLOYER_SUCCESS:
            return Object.assign({}, {
                ...state,
                [action.payload.employerId] : action.payload.response
            });
        case RESET_EMPLOYER:
            return Object.assign({}, {
                ...state,
                [action.payload.employerId] : undefined
            });
        default:
            return state;
    }
};

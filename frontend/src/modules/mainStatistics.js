import ExchangeInterface from 'components/exchange/ExchangeInterface';
import settings from 'config/settings';
import { setErrorMessage } from './errorMessage';

const defaultState = {};

const GET_GENERAL_STATISTICS_SUCCESS = 'GET_GENERAL_STATISTICS_SUCCESS';
const GET_SALARY_BY_PROF_FIELDS_SUCCESS = 'GET_SALARY_BY_PROF_FIELDS_SUCCESS';

export const getGeneralStatistics = () => {
    return (dispatch) => {
        ExchangeInterface.getGeneralStatistics().then(
            (response) => {
                dispatch({
                    type: GET_GENERAL_STATISTICS_SUCCESS,
                    payload: response,
                });
            },
            (error) => {
                let errorMessage = error ? `${error.status} ${error.statusText}` : settings.unknownErrorMessage;
                dispatch(setErrorMessage(errorMessage));
            }
        );
    }
};

export const getSalaryByProfFields = () => {
    return (dispatch) => {
        ExchangeInterface.getSalaryByProfFields().then(
            (response) => {
                dispatch({
                    type: GET_SALARY_BY_PROF_FIELDS_SUCCESS,
                    payload: response,
                });
            },
            (error) => {
                let errorMessage = error ? `${error.status} ${error.statusText}` : settings.unknownErrorMessage;
                dispatch(setErrorMessage(errorMessage));
            }
        );
    }
};

export const mainStatistics = (state = defaultState, action) => {
    switch (action.type) {
        case GET_GENERAL_STATISTICS_SUCCESS:
            return {
                ...state,
                general: action.payload,
            };
        case GET_SALARY_BY_PROF_FIELDS_SUCCESS:
            return {
                ...state,
                salaryByProfFields: action.payload,
            };
        default:
            return state;
    }
};

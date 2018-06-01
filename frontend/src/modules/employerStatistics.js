import ExchangeInterface from 'components/exchange/ExchangeInterface';
import settings from 'config/settings';
import { setErrorMessage } from './errorMessage';

const defaultState = {};

const GET_EMPLOYER_SALARY_STATISTICS_SUCCESS = 'GET_EMPLOYER_SALARY_STATISTICS_SUCCESS';
const GET_EMPLOYER_DURATION_STATISTICS_SUCCESS = 'GET_EMPLOYER_DURATION_STATISTICS_SUCCESS';
const GET_EMPLOYER_REVIEW_STATISTICS_SUCCESS = 'GET_EMPLOYER_REVIEW_STATISTICS_SUCCESS';

const getEmployerStatistics = (exchangeMethod, actionType, employerId) => {
    return (dispatch) => {
        exchangeMethod(employerId).then(
            (response) => {
                dispatch({
                    type: actionType,
                    payload: {
                        employerId: Number(employerId),
                        response: response
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

export const getSalaryByProfFields = (employerId) => {
    return getEmployerStatistics(
        ExchangeInterface.getSalaryByProfFields,
        GET_EMPLOYER_SALARY_STATISTICS_SUCCESS,
        employerId
    );
};

export const getDurationByProfFields = (employerId) => {
    return getEmployerStatistics(
        ExchangeInterface.getEmploymentDurationByProfField,
        GET_EMPLOYER_DURATION_STATISTICS_SUCCESS,
        employerId
    );
};

export const getReviewsByProfFields = (employerId) => {
    return getEmployerStatistics(
        ExchangeInterface.getReviewsCountByProfField,
        GET_EMPLOYER_REVIEW_STATISTICS_SUCCESS,
        employerId
    );
};

export const employerStatistics = (state = defaultState, action) => {
    switch (action.type) {
        case GET_EMPLOYER_SALARY_STATISTICS_SUCCESS:
            return {
                ...state,
                salaryEmployerId: action.payload.employerId,
                salary: action.payload.response
            };
        case GET_EMPLOYER_DURATION_STATISTICS_SUCCESS:
            return {
                ...state,
                durationEmployerId: action.payload.employerId,
                duration: action.payload.response
            };
        case GET_EMPLOYER_REVIEW_STATISTICS_SUCCESS:
            return {
                ...state,
                reviewsEmployerId: action.payload.employerId,
                reviews: action.payload.response
            };
        default:
            return state;
    }
};

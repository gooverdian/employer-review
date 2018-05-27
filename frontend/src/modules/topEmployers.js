import ExchangeInterface from 'components/exchange/ExchangeInterface';
import settings from 'config/settings';
import { camelizeKeys } from 'humps';
import { setErrorMessage } from './errorMessage';

const defaultState = {
    items: []
};

const GET_TOP_EMPLOYERS_SUCCESS = 'GET_TOP_EMPLOYERS_SUCCESS';
const RESET_TOP_EMPLOYERS = 'RESET_TOP_EMPLOYERS';

export const getTopEmployers = () => {
    return (dispatch) => {
        ExchangeInterface.getTopEmployers().then(
            (response) => {
                dispatch({
                    type: GET_TOP_EMPLOYERS_SUCCESS,
                    payload: {
                        items: camelizeKeys(response)
                    }
                });
            },
            (error) => {
                let errorMessage = error ? `${error.status} ${error.statusText}` : settings.unknownErrorMessage;
                dispatch(setErrorMessage(errorMessage));
            }
        );
    }
};

export const resetTopEmployers = () => {
    return (dispatch) => dispatch({ type: RESET_TOP_EMPLOYERS });
};

export const topEmployers = (state = defaultState, action) => {
    switch (action.type) {
        case RESET_TOP_EMPLOYERS:
            return defaultState;
        case GET_TOP_EMPLOYERS_SUCCESS:
            return action.payload;
        default:
            return state;
    }
};

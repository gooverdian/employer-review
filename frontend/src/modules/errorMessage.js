const defaultState = null;

const RESET_ERROR_MESSAGE = 'RESET_ERROR_MESSAGE';
const SET_ERROR_MESSAGE = 'SET_ERROR_MESSAGE';

export const resetErrorMessage = () => ({
    type: RESET_ERROR_MESSAGE
});

export const setErrorMessage = (error) => ({
    type: SET_ERROR_MESSAGE,
    error
});

export const dispatchError = (error) => {
    return (dispatch) => dispatch(setErrorMessage(error));
};

export const errorMessage = (state = defaultState, action) => {
    switch (action.type) {
        case RESET_ERROR_MESSAGE:
            return defaultState;
        case SET_ERROR_MESSAGE:
            return action.error;
        default:
            return state;
    }
};

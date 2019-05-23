import * as ACTION_TYPES from './Action_Types';

export function SUCCESS() {
    return {
        type: ACTION_TYPES.SUCCESS
    }
}

export function FAILURE() {
    return {
        type: ACTION_TYPES.FAILURE
    }
}

export function USER_INPUT(text) {
    return{
        type: ACTION_TYPES.USER_INPUT,
        payload: text
    }
}

export function loginSuccess() {
    return{
        type: ACTION_TYPES.LOGIN_SUCCESS
    }
}

export function loginFailure() {
    return{
        type: ACTION_TYPES.LOGIN_FAILURE
    }
}
import * as ACTION_TYPE from "../actions/Action_Types/User_Types.js";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function loadUser(user) {
    // console.log('loadEvents()');
                
    //Las acciones devuelve un objeto
    return {
        type: ACTION_TYPE.FETCH_USER,
        user: user
    };

}

export function loginUser(user) {
    console.log('loginUser()');
                
    //Las acciones devuelve un objeto
    return {
        type: ACTION_TYPE.LOGIN_USER,
        user: user
    };

}
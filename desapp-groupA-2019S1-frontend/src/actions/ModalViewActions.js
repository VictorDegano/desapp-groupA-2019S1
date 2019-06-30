import * as ACTION_TYPE from "../actions/Action_Types/ModalView_Types.js";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function openProfileEdition() {
    // console.log("openProfileEdition()");
                
    //Las acciones devuelve un objeto
    return {
        type: ACTION_TYPE.OPEN_PROFILE_EDITION,
    };

}

export function closeProfileEdition() {
    // console.log("closeProfileEdition()");
                
    //Las acciones devuelve un objeto
    return {
        type: ACTION_TYPE.CLOSE_PROFILE_EDITION,
    };

}
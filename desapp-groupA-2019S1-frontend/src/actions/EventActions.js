import * as ACTION_TYPE from "../actions/Action_Types/Event_Types.js";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function loadEvents(events) {
    // console.log('loadEvents()');
                
    //Las acciones devuelve un objeto
    return {
        type: ACTION_TYPE.SHOW_ALL_EVENTS,
        events: events
    }

};
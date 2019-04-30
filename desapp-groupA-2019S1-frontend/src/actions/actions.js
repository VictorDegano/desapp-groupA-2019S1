
//Actions (Se crean como constantes mas que nada para evitar errores de tipeo o de duplicidad de acciones)
export const SHOW_ALL_EVENTS = "SHOW_ALL_EVENTS"

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function loadEvents(events) {
    console.log('loadEvents()');
                
    //Las acciones devuelve un objeto
    return {
        type: SHOW_ALL_EVENTS,
        events: events
    }

}
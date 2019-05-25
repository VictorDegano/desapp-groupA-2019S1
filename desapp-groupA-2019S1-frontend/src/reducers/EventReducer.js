//Todos los reducers tienen que tener un initial state, que no es el mismo que el initial state que se pueda generar en la aplicacion al iniciarla

const initialState = {
    events: []
};

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function eventReducer(state= initialState, action){
    // console.log('eventReducer()');

    //Dependiendo del tipo de accion que recibimos es lo que vamos a realizar
    switch(action.type){
        case "SHOW_ALL_EVENTS": {
            // console.log('case SHOW_ALL_EVENTS');
            const { events: retrievedValue } = action;
            
            return Object.assign({}, state, { 
                events: retrievedValue
            });
        }

        default: 
            return state;
    }
};

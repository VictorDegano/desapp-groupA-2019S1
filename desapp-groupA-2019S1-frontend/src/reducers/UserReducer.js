//Todos los reducers tienen que tener un initial state, que no es el mismo que el initial state 
//que se pueda generar en la aplicacion al iniciarla

const initialState = {
    user: {},
    loggedUser: {}
}

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function userReducer(state= initialState, action){
    console.log('userReducer()');

    //Dependiendo del tipo de accion que recibimos es lo que vamos a realizar
    switch(action.type){
        //TODO: Este fetchUser se tendria que usar para el usuario a ver.
        case "FETCH_USER": {
            console.log('case FETCH_USER');
            const { user: retrievedValue } = action;
            
            return Object.assign({}, state, { 
                user: retrievedValue
            });
        }

        case "LOGIN_USER": {
            console.log('case LOGIN_USER');
            const { loggedUser: retrievedValue } = action;
            
            return Object.assign({}, state, { 
                loggedUser: retrievedValue
            });
        }

        default: 
            return state;
    }
}
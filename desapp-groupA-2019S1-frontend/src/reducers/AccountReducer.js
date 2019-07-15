//Todos los reducers tienen que tener un initial state, que no es el mismo que el initial state que se pueda generar en la aplicacion al iniciarla

const initialState = {
  showAccount: false,
  addMoney: false
};

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function accountReducer(state = initialState, action) {
  //Dependiendo del tipo de accion que recibimos es lo que vamos a realizar
  switch (action.type) {
    case "SHOW_ACCOUNT": {
      // console.log('case SHOW_EVENTS_IN_PROGRESS');

      return Object.assign({}, state, {
        showAccount: true
      });
    }
    case "HIDE_ACCOUNT": {
      // console.log('case SHOW_EVENTS_IN_PROGRESS');

      return Object.assign({}, state, {
        showAccount: false
      });
    }
    case "ADD_MONEY": {
      // console.log('case SHOW_EVENTS_IN_PROGRESS');

      return Object.assign({}, state, {
        addMoney: true
      });
    }

    default:
      return state;
  }
}

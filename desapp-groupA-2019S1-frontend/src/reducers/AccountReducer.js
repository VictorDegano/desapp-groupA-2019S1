//Todos los reducers tienen que tener un initial state, que no es el mismo que el initial state que se pueda generar en la aplicacion al iniciarla

const initialState = {
  showAccount: false,
  addMoney: false,
  balance: 0,
  extractMoney: false,
  showLastMovements: false,
  movements: [],
  loan: false
};

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function accountReducer(state = initialState, action) {
  //Dependiendo del tipo de accion que recibimos es lo que vamos a realizar
  switch (action.type) {
    case "SHOW_ACCOUNT": {
      return Object.assign({}, state, {
        showAccount: true
      });
    }
    case "HIDE_ACCOUNT": {
      return Object.assign({}, state, {
        showAccount: false
      });
    }
    case "ADD_MONEY": {
      return Object.assign({}, state, {
        addMoney: true,
        extractMoney: false,
        showLastMovements: false,
        loan: false
      });
    }
    case "UPDATE_BALANCE": {
      return Object.assign({}, state, {
        balance: action.balance
      });
    }
    case "EXTRACT_MONEY": {
      return Object.assign({}, state, {
        addMoney: false,
        extractMoney: true,
        showLastMovements: false,
        loan: false
      });
    }

    case "SHOW_LAST_MOVEMENTS": {
      return Object.assign({}, state, {
        addMoney: false,
        extractMoney: false,
        showLastMovements: true,
        loan: false
      });
    }
    case "UPDATE_MOVEMENTS": {
      return Object.assign({}, state, {
        movements: action.movements
      });
    }

    case "SHOW_LOAN": {
      return Object.assign({}, state, {
        addMoney: false,
        extractMoney: false,
        showLastMovements: false,
        loan: true
      });
    }

    default:
      return state;
  }
}

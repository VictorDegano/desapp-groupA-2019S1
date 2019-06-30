//Todos los reducers tienen que tener un initial state, que no es el mismo que el initial state que se pueda generar en la aplicacion al iniciarla

const initialState = {
  eventTableTitle: "",
  events: [],
  eventsInProgress: [],
  lastEvents: [],
  mostPopularEvents: []
};

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function eventReducer(state = initialState, action) {
  //Dependiendo del tipo de accion que recibimos es lo que vamos a realizar
  switch (action.type) {
    case "SHOW_EVENTS_IN_PROGRESS": {
      // console.log('case SHOW_EVENTS_IN_PROGRESS');

      return Object.assign({}, state, {
        events: action.events,
        eventTableTitle: action.eventTableTitle
      });
    }

    case "SHOW_EVENTS_LAST": {
      // console.log('case SHOW_EVENTS_LAST');

      return Object.assign({}, state, {
        events: action.events,
        eventTableTitle: action.eventTableTitle
      });
    }

    case "SHOW_EVENTS_MOST_POPULAR": {
      // console.log('case SHOW_EVENTS_MOST_POPULAR');

      return Object.assign({}, state, {
        events: action.events,
        eventTableTitle: action.eventTableTitle
      });
    }

    case "EVENTS_IN_PROGRESS": {
      // console.log('case EVENTS_IN_PROGRESS');

      return Object.assign({}, state, {
        eventsInProgress: action.events
      });
    }

    case "EVENTS_LAST": {
      // console.log('case EVENTS_LAST');

      return Object.assign({}, state, {
        lastEvents: action.events
      });
    }

    case "EVENTS_MOST_POPULAR": {
      // console.log('case EVENTS_MOST_POPULAR');

      return Object.assign({}, state, {
        mostPopularEvents: action.events
      });
    }
    
    default:
      return state;
  }
}

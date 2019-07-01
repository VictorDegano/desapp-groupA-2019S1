const initialState = {
  modalProfileState: null,
  modalEventView: null,
  eventId: 0,
  eventType: ""
};

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function userReducer(state = initialState, action) {
  // console.log('userReducer()');

  switch (action.type) {
    case "OPEN_PROFILE_EDITION": {
      // console.log('case OPEN_PROFILE_EDITION');
      return Object.assign({}, state, {
        modalProfileState: true
      });
    }

    case "CLOSE_PROFILE_EDITION": {
      // console.log('case CLOSE_PROFILE_EDITION');
      return Object.assign({}, state, {
        modalProfileState: false
      });
    }

    case "OPEN_EVENT_VIEW": {
      // console.log('case OPEN_EVENT_VIEW');
      return Object.assign({}, state, {
        modalEventView: true,
        eventId: action.eventId,
        eventType: action.eventType
      });
    }

    case "CLOSE_EVENT_VIEW": {
      // console.log('case CLOSE_EVENT_VIEW');
      return Object.assign({}, state, {
        modalEventView: false,
        eventId: 0,
        eventType: ""
      });
    }

    default:
      return state;
  }
}

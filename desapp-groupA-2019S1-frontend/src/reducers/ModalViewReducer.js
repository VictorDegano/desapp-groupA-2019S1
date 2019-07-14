const emptyEvent = {
  eventName: "",
  creationDate: "",
  goods: [
    {
      good: false,
      name: "",
      pricePerUnit: 0,
      quantityForPerson: 0,
      finalQuantity: 0
    }
  ],
  guests: [
    {
      confirmAsistance: "",
      firstName: "",
      guestId: 0,
      lastName: "",
      mail: "",
      userId: 0
    }
  ],
  id: 0,
  organizer: {
    id: 0,
    fistName: "",
    lastName: "",
    bornDate: "",
    email: ""
  },
  loadedGoods: [],
  representatives: [],
  quantityOfGuest: 0,
  status: "",
  type: ""
};

const initialState = {
  modalCreateEventState: false,
  modalProfileState: false,
  modalEventView: false,
  modify: false,
  event: emptyEvent,
  eventToModify: emptyEvent
};

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function ModalViewReducer(state = initialState, action) {
  // console.log('userReducer()');

  switch (action.type) {
    case "OPEN_PROFILE_EDITION": {
      return Object.assign({}, state, {
        modalProfileState: true
      });
    }

    case "CLOSE_PROFILE_EDITION": {
      return Object.assign({}, state, {
        modalProfileState: false
      });
    }

    case "OPEN_CREATE_EVENT_MODAL": {
      return Object.assign({}, state, {
        modalCreateEventState: true
      });
    }

    case "CLOSE_CREATE_EVENT_MODAL": {
      return Object.assign({}, state, {
        modalCreateEventState: false,
        modify: false
      });
    }

    case "OPEN_EVENT_VIEW": {
      return Object.assign({}, state, {
        modalEventView: true,
        event: action.event
      });
    }

    case "CLOSE_EVENT_VIEW": {
      // console.log('case CLOSE_EVENT_VIEW');
      return Object.assign({}, state, {
        modalEventView: false,
        event: emptyEvent
      });
    }

    case "UPDATE_EVENT": {
      return Object.assign({}, state, {
        event: action.event
      });
    }

    case "CLOSE_EVENT": {
      return Object.assign({}, state, {
        event: Object.assign({}, state.event, {
          status: "CLOSE"
        })
      });
    }

    case "OPEN_MODIFY_EVENT_MODAL": {
      return Object.assign({}, state, {
        modalCreateEventState: true,
        eventToModify: action.event,
        modify: true
      });
    }

    case "CLOSE_MODIFY_EVENT_MODAL": {
      return Object.assign({}, state, {
        eventToModify: emptyEvent,
        modalCreateEventState: false,
        modify: false
      });
    }

    default:
      return state;
  }
}

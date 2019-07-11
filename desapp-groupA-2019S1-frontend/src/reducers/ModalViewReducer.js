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
  quantityOfGuest: 0,
  status: "",
  type: ""
};

const initialState = {
  modalCreateEventState: false,
  modalProfileState: false,
  modalEventView: false,
  event: emptyEvent
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

    case "OPEN_CREATE_EVENT_MODAL": {
      // console.log('case OPEN_PROFILE_EDITION');
      return Object.assign({}, state, {
        modalCreateEventState: true
      });
    }

    case "CLOSE_CREATE_EVENT_MODAL": {
      // console.log('case CLOSE_PROFILE_EDITION');
      return Object.assign({}, state, {
        modalCreateEventState: false
      });
    }

    case "OPEN_EVENT_VIEW": {
      // console.log('case OPEN_EVENT_VIEW');
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
      // Para Modificar un objecto anidado hay que "replicar" la asignacion en cadacapa que se va metiendo
      return Object.assign({}, state, {
        event: Object.assign({}, state.event, {
          goods: state.event.goods.map(
                    (good) => {
                      if (good.id === action.goodId){
                        return Object.assign({}, good, { available: false });
                      } else {
                        return good;
                      }
                    }
          )})
      })
    }

    default:
      return state;
  }
}
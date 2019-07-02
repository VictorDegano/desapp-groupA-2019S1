const initialState = {
  modalProfileState: false,
  modalEventView: false,
  event: {
    eventName: "",
    creationDate: "",
    goods: [],
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
  }
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
        event: action.event
      });
    }

    case "CLOSE_EVENT_VIEW": {
      // console.log('case CLOSE_EVENT_VIEW');
      return Object.assign({}, state, {
        modalEventView: false,
        event: {
          eventName: "",
          creationDate: "",
          goods: [],
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
        }
      });
    }

    default:
      return state;
  }
}

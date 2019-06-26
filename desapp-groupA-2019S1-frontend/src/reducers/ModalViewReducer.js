const initialState = {
    modalProfileState: null
}

//Se recibe un state antiguo, un action y se devuelve el nuevo state
export default function userReducer(state = initialState, action){
    // console.log('userReducer()');

    switch(action.type){

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

        default: 
            return state;
    }
}
//Aca se cargara todos los reducers que estemos creando, este index se crea para "mezclar/combinar" los reducers
import { combineReducers } from "redux";
//App Reducers
import EventReducer from "../reducers/EventReducer";
import UserReducer from "../reducers/UserReducer";

const rootReducer = combineReducers({
    EventReducer,
    UserReducer
});

export default rootReducer;
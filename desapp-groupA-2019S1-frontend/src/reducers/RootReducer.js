//Aca se cargara todos los reducers que estemos creando, este index se crea para "mezclar/combinar" los reducers
import { combineReducers } from "redux";
//App Reducers
import EventReducer from "./EventReducer";
import UserReducer from "./UserReducer";
import ModalViewReducer from "./ModalViewReducer";
import AccountReducer from "./AccountReducer";

const rootReducer = combineReducers({
  EventReducer,
  UserReducer,
  ModalViewReducer,
  AccountReducer
});

export default rootReducer;

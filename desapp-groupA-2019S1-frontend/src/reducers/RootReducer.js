//Aca se cargara todos los reducers que estemos creando, este index se crea para "mezclar/combinar" los reducers
import { combineReducers } from 'redux';

//App Reducers
import EventReducer from '../reducers/EventReducer';

const rootReducer = combineReducers({
    EventReducer
})

export default rootReducer;
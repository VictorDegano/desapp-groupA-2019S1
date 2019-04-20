//Aca se cargara todos los reducers que estemos creando, este index se crea para "mezclar/combinar" los reducers

import { combineReducers } from 'redux'

//App Reducers
import eventList from '../reducers/eventListReducer'

const rootReducer = combineReducers({
    eventList
    //miReducer2

})

export default rootReducer
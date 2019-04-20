import fiestaMock from '../resources/Fiesta.js';


class EventApi {

    static getAllEvents(){
        console.log('getAllEvents()')
        return fiestaMock.Fiestas
    }

}

export default EventApi
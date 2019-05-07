import React, { Component } from 'react'
import { connect }from 'react-redux'
import PropTypes from 'prop-types'
import EventApi from '../api/EventApi.js'

//Actions
import * as actions from '../actions/actions'

class EventList extends Component {

    static propTypes ={
        loadEvents: PropTypes.func.isRequired,
        events: PropTypes.array
    }

    // constructor(props){
    //     super(props)
    // }

    //Ocurre antes de que el componente se monte(o complete de montarse)
    componentWillMount(){
        console.log('componentWilMount()') 
        var eventApi = new EventApi()
        eventApi.fetchEvents().then(events =>{
            this.props.loadEvents(events)
            })
        
    }

    render (){
        console.log('render()')
        
        const eventsLoaded = this.props.events
        
        return <div>
            Eventos:

            <ul>
                {eventsLoaded.map(i => {
                                    return <li key={i.name+i.name.length}>
                                            {i.name}
                                            <br/>
                                            Confirmations: {i.confirmations}
                                            <br/>
                                            {createListOfGoods(i.goodsForGuest,i.name)}
                                           </li>
                                })}
            </ul>
        </div>
    }

}

function createListOfGoods(listOfGoods,name){
    
    if(listOfGoods !== null) {
        return  <ul key={'goodsOf'+name}>
                    {listOfGoods.map(i => {return <li key={i.name+i.name.length}>{i.name}</li>})}
                </ul>
    }      
    
}




function mapStateToProps (state){
    console.log('mapStateToProps()') 
    //state: valor del state (La idea es que el estado se obtiene atravesando el reducer correspondiente, osea state.reducer.xState)
    return {
        events: state.eventList.events
    }
}

//el mapStateToProps son los estados que se conectan a los props
//el mapDispatchToprops son las acciones, si se tiene sino se pasa null
//connect(mapStateToprops, mapDispatchToProps)(ComponentClass)
export default connect(mapStateToProps, actions)(EventList)
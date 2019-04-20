import React, { Component } from 'react'
import { connect }from 'react-redux'
import PropTypes from 'prop-types'

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
        this.props.loadEvents()
    }

    render (){
        console.log('render()')
        
        const eventsLoaded = this.props.events
        
        return <div>
            Eventos:

            <ul>
                {eventsLoaded.map(i => {
                                    return <li>
                                            {i.Nombre}
                                            <ul>
                                                {i.Insumos.map(i => {return <li>{i.Nombre}</li>})}
                                            </ul>
                                           </li>
                                })}
            </ul>
        </div>
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
import React, { Component } from "react";
import { connect }from "react-redux";
import PropTypes from "prop-types";
import EventApi from "../api/EventApi.js";
//Actions
import * as EventActions from "../actions/EventActions.js";

class EventList extends Component {

    static propTypes ={
        loadEvents: PropTypes.func.isRequired,
        events: PropTypes.array
    };

    //Ocurre antes de que el componente se monte(o complete de montarse)
    componentWillMount(){
        // console.log('componentWilMount()');
        var eventApi = new EventApi();

        eventApi.fetchEvents()
                .then((events) => {
                    this.props.loadEvents(events);});
    };

    createListOfGoods(listOfGoods,name){
        // console.log('createListOfGoods()');
        if(listOfGoods !== null) {
            return  <ul key={"goodsOf"+name}>
                        {listOfGoods.map(i => {return <li key={i.name+i.name.length}>{i.name}</li>;})}
                    </ul>;
        }      
    };

    render (){
        // console.log('render()');
        
        const eventsLoaded = this.props.events;
        
        return <div>
            Eventos:

            <ul>
                {eventsLoaded.map((i) => {
                                    return <li key={i.name+i.name.length}>
                                            {i.name}
                                            <br/>
                                            Confirmations: {i.confirmations}
                                            <br/>
                                            {this.createListOfGoods(i.goodsForGuest,i.name)}
                                           </li>;
                                })}
            </ul>
        </div>;
    };

    constructor(props){
        super(props);
        this.componentWillMount = this.componentWillMount.bind(this);
        this.createListOfGoods = this.createListOfGoods.bind(this);
        this.render = this.render.bind(this);
    };
}

function mapStateToProps (state){
    // console.log('mapStateToProps()') 
    //state: valor del state (La idea es que el estado se obtiene atravesando el reducer correspondiente, osea state.reducer.xState)
    return {
        events: state.EventReducer.events
    };
}

//el mapStateToProps son los estados que se conectan a los props
//el mapDispatchToprops son las acciones, si se tiene sino se pasa null
//connect(mapStateToprops, mapDispatchToProps)(ComponentClass)
export default connect(mapStateToProps, EventActions)(EventList);
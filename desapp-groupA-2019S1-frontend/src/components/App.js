import React from "react";
import { withRouter } from "react-router-dom";
import NavigationBar from "./NavigationBar";
import EventsComponent from "./EventsComponent";
import EventApi from "../api/EventApi";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      eventosEnCurso: [],
      misUltimosEventos: []
    };
  }

  //Ocurre antes de que el componente se monte(o complete de montarse)
  componentWillMount() {
    // console.log('componentWilMount()');
    var eventApi = new EventApi();

    eventApi.getEventosEnCurso(1).then(response => {
      this.setState({
        eventosEnCurso: response.data
      });
    });

    eventApi.getMisUltimosEventos(1).then(response => {
      this.setState({
        misUltimosEventos: response.data
      });
    });
  }

  render() {
    return (
      <div>
        <NavigationBar />
        <EventsComponent
          title={"Mis eventos en curso:"}
          arrayDeEventos={this.state.eventosEnCurso}
        />
        <EventsComponent
          title={"Mis ultimos eventos:"}
          arrayDeEventos={this.state.misUltimosEventos}
        />
        {/*<EventsComponent title={"Los eventos mas populares:" } arrayDeEventos={getEventos()}/>*/}
      </div>
    );
  }
}

export default withRouter(App);

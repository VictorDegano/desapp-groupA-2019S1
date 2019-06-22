import React from "react";
import { withRouter } from "react-router-dom";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import EventApi from "../api/EventApi";
import NavigationBar from "./NavigationBar";
import SideBar from "./SideBar";
import MainPanel from "./MainPanel";
import EventsComponent from "./EventsComponent";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "Eventos En Curso",
      eventos: [],
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
        <Row>
          <Col xs={3}>
            <SideBar />
          </Col>
          <Col xs={9}>
            <MainPanel
              title={this.state.title}
              arrayDeEventos={this.state.eventosEnCurso}
            />
          </Col>
        </Row>
        {/*<EventsComponent*/}
        {/*  title="Mis eventos en curso:"*/}
        {/*  arrayDeEventos={this.state.eventosEnCurso}*/}
        {/*/>*/}
        {/*<EventsComponent*/}
        {/*  title="Mis ultimos eventos:"*/}
        {/*  arrayDeEventos={this.state.misUltimosEventos}*/}
        {/*/>*/}
      </div>
    );
  }
}

export default withRouter(App);

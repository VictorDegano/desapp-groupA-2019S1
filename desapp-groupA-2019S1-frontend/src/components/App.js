import React from "react";
import { withRouter } from "react-router-dom";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import EventApi from "../api/EventApi";
import NavigationBar from "./NavigationBar";
import SideBar from "./SideBar";
import MainPanel from "./MainPanel";
import EventsComponent from "./EventsComponent";
import ProfileEdition from "../containers/ProfileEdition";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "Eventos En Curso",
      eventosEnCurso: [],
      misUltimosEventos: [],
      eventosQueSeMuestran: []
    };
  }

  //Ocurre antes de que el componente se monte(o complete de montarse)
  componentWillMount() {
    // console.log('componentWilMount()');
    var eventApi = new EventApi();

    eventApi.getEventosEnCurso(1).then(response => {
      this.setState({
        eventosEnCurso: response.data,
        eventosQueSeMuestran: response.data
      });
    });

    eventApi.getMisUltimosEventos(1).then(response => {
      this.setState({
        misUltimosEventos: response.data
      });
    });
  }

  showMisUltimosEventos() {
    this.setState({
      eventosQueSeMuestran: this.state.misUltimosEventos,
      title: "Mis Ultimos Eventos",
    });
  }
  showEventosEnCurso() {
    this.setState({
      eventosQueSeMuestran: this.state.eventosEnCurso,
      title: "Eventos En Curso",
    });
  }

  render() {
    return (
      <div>
        <NavigationBar />
        <ProfileEdition/>
        <Row>
          <Col xs={2}>
            <SideBar showMisUltimosEventos={this.showMisUltimosEventos.bind(this)}
                     showEventosEnCurso={this.showEventosEnCurso.bind(this)}/>
          </Col>
          <Col xs={10}>
            <MainPanel
              title={this.state.title}
              arrayDeEventos={this.state.eventosQueSeMuestran}
            />
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(App);

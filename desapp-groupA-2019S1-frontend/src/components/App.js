import React from "react";
import { withRouter } from "react-router-dom";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import EventApi from "../api/EventApi";
import NavigationBar from "./NavigationBar";
import SideBar from "./SideBar";
import MainPanel from "./MainPanel";
import ProfileEdition from "../containers/ProfileEdition";
import { updateLoggedUser } from "../actions/UserActions";
import CreateEventModal from "../containers/CreateEventModal";

class App extends React.Component {
  static propTypes = {
    loggedUser: PropTypes.object
  };

  constructor(props) {
    super(props);
    this.state = {
      title: "Eventos En Curso",
      eventosEnCurso: [],
      misUltimosEventos: [],
      eventosMasPopulares: [],
      eventosQueSeMuestran: []
    };
  }

  //Ocurre antes de que el componente se monte(o complete de montarse)
  componentWillMount() {
    // console.log('componentWilMount()');
    var eventApi = new EventApi();

    eventApi.getEventosEnCurso(this.props.loggedUser.id).then(response => {
      this.setState({
        eventosEnCurso: response.data,
        eventosQueSeMuestran: response.data
      });
    });

    eventApi.getMisUltimosEventos(this.props.loggedUser.id).then(response => {
      this.setState({
        misUltimosEventos: response.data
      });
    });

    eventApi.getEventosMasPopulares().then(response => {
      this.setState({
        eventosMasPopulares: response.data
      });
    });
  }

  showMisUltimosEventos() {
    this.setState({
      eventosQueSeMuestran: this.state.misUltimosEventos,
      title: "Mis Ultimos Eventos"
    });
  }
  showEventosEnCurso() {
    this.setState({
      eventosQueSeMuestran: this.state.eventosEnCurso,
      title: "Eventos En Curso"
    });
  }
  showEventosMasPopulares() {
    this.setState({
      eventosQueSeMuestran: this.state.eventosMasPopulares,
      title: "Eventos En Curso"
    });
  }

  render() {
    return (
      <div>
        <NavigationBar />
        <CreateEventModal />
        <ProfileEdition />
        <Row>
          <Col xs={2}>
            <SideBar
              showMisUltimosEventos={this.showMisUltimosEventos.bind(this)}
              showEventosEnCurso={this.showEventosEnCurso.bind(this)}
              showEventosMasPopulares={this.showEventosMasPopulares.bind(this)}
            />
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

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    loggedUser: state.UserReducer.loggedUser
  };
}

const mapDispatchToProps = dispatch => ({
  updateLoggedUser: user => dispatch(updateLoggedUser(user))
});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
    // ModalViewActions,
    // UserActions
  )(App)
);

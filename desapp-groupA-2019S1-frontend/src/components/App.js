import React from "react";
import { withRouter } from "react-router-dom";
// I18n Hook
import { withTranslation } from "react-i18next";
// Bootstrap
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
// Redux
import PropTypes from "prop-types";
import { connect } from "react-redux";
//Actions
import { updateLoggedUser } from "../actions/UserActions";
import { loadEventsInProgress, loadLastEvents, loadMostPopularEvents, showEventsInProgress } from "../actions/EventActions";
// API
import EventApi from "../api/EventApi";
// Eventeando
import NavigationBar from "./NavigationBar";
import SideBar from "./SideBar";
import MainPanel from "./MainPanel";
import ProfileEdition from "../containers/ProfileEdition";

class App extends React.Component {
  static propTypes = {
    loggedUser: PropTypes.object,
    showEventsInProgress:PropTypes.func.isRequired,
    loadEventsInProgress:PropTypes.func.isRequired,
    loadLastEvents:PropTypes.func.isRequired,
    loadMostPopularEvents:PropTypes.func.isRequired,
  };

  constructor(props) {
    super(props);
  }

  //Ocurre antes de que el componente se monte(o complete de montarse)
  componentWillMount() {
    // console.log('componentWilMount()');
    var eventApi = new EventApi();

    eventApi.getEventosEnCurso(this.props.loggedUser.id).then(response => {
      this.props.loadEventsInProgress(response.data);
      this.props.showEventsInProgress();
    });

    eventApi.getMisUltimosEventos(this.props.loggedUser.id).then(response => {
      this.props.loadLastEvents(response.data);
    });

    eventApi.getEventosMasPopulares().then(response => {
      this.props.loadMostPopularEvents(response.data);
    });

  }

  render() {
    
    return (
      <div>
        <NavigationBar />
        <ProfileEdition />
        <Row>
          <Col xs={2}>
            <SideBar/>
          </Col>
          <Col xs={10}>
            <MainPanel/>
          </Col>
        </Row>
      </div>
    );
  }
}

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    loggedUser: state.UserReducer.loggedUser,
    eventTableTitle: state.EventReducer.eventTableTitle,
    events: state.EventReducer.events,
    eventsInProgress: state.EventReducer.eventsInProgress,
    lastEvents: state.EventReducer.lastEvents,
    mostPopularEvents: state.EventReducer.mostPopularEvents
  };
}

const mapDispatchToProps = dispatch => ({
  updateLoggedUser: user => dispatch(updateLoggedUser(user)),
  showEventsInProgress: events => dispatch(showEventsInProgress(events)),
  loadEventsInProgress: events => dispatch(loadEventsInProgress(events)),
  loadLastEvents: events => dispatch(loadLastEvents(events)),
  loadMostPopularEvents: events => dispatch(loadMostPopularEvents(events))
});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(withTranslation()(App))
);

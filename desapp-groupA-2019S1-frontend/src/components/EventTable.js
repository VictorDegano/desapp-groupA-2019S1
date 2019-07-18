import React from "react";
import Table from "react-bootstrap/Table";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import PropTypes from "prop-types";
import { connect } from "react-redux";
import Button from "react-bootstrap/Button";
// action
import {
  openEventView,
  openModifyEventModal
} from "../actions/ModalViewActions";
// API's
import EventApi from "../api/EventApi";
// Eventeando
import Paginator from "../containers/Paginator";
// CSS
import "../css/EventTable.css";

function createDataWithJson(jsonDeEvento) {
  return {
    name: jsonDeEvento.eventName,
    eventType: jsonDeEvento.type,
    organizer:
      jsonDeEvento.organizer.fistName + " " + jsonDeEvento.organizer.lastName,
    guestsAmount: jsonDeEvento.guestsAmount
  };
}
function parseArrayToFunction(rowsArray) {
  //Esto saca los duplicados hasta que se arregle de la api
  var includedIds = [];
  var uniqueElements = [];
  rowsArray.forEach(e => {
    if (!includedIds.includes(e.id)) {
      uniqueElements.push(e);
      includedIds.push(e.id);
    }
  });
  return uniqueElements.flat(row => createDataWithJson(row));
}

class EventTable extends React.Component {
  static propTypes = {
    openEventView: PropTypes.func.isRequired,
    openModifyEventModal: PropTypes.func.isRequired,
    loggedUser: PropTypes.object,
    events: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number,
        eventName: PropTypes.string,
        eventType: PropTypes.string,
        organizer: PropTypes.shape({
          fistName: PropTypes.string,
          lastName: PropTypes.string
        }),
        guestsAmount: PropTypes.number
      })
    )
  };

  constructor(props, context) {
    super(props, context);
    this.getTraduction = this.getTraduction.bind(this);
    this.openEventViewModal = this.openEventViewModal.bind(this);
    this.openModifyEventModal = this.openModifyEventModal.bind(this);
    this.getEventsToShow = this.getEventsToShow.bind(this);

    this.handlePageChange = this.handlePageChange.bind(this);
    this.state = {
      currentPageNumber: 1,
      itemsPerPage: 5
    };
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevProps.events.length !== this.props.events.length){
      this.setState({currentPageNumber: 1});
    }
  }

  getTraduction(eventType) {
    const { t } = this.props;
    if (eventType !== undefined) {
      if (eventType === "FIESTA") {
        return t("eventType->party");
      }
      if (eventType === "CANASTA") {
        return t("eventType->toBasket");
      }
      if (eventType === "BAQUITA_COMUNITARY") {
        return t("eventType->littleCowComunitary");
      }
      if (eventType === "BAQUITA_REPRESENTATIVES") {
        return t("eventType->littleCowRepresentatives");
      }
    }
  }

  openEventViewModal(eventid) {
    const eventApi = new EventApi();
    eventApi.getEvent(eventid).then(response => {
      this.props.openEventView(response.data);
    });
  }

  openModifyEventModal(eventid) {
    const eventApi = new EventApi();
    eventApi.getEvent(eventid).then(response => {
      this.props.openModifyEventModal(response.data);
    });
  }

  handlePageChange(newCurrentPage) {
    this.setState({ currentPageNumber: newCurrentPage });
  }

  getEventsToShow(events, eventsSize) {
    let eventsToShow = [];
    let startIndex =
      this.state.itemsPerPage * (this.state.currentPageNumber - 1);
    let endIndex = Math.min(
      this.state.itemsPerPage * this.state.currentPageNumber,
      eventsSize
    );

    for (startIndex; startIndex < endIndex; startIndex++) {
      eventsToShow.push(events[startIndex]);
    }

    return eventsToShow;
  }

  renderModifyButton(eventId, organizerId) {
    if (organizerId === parseInt(localStorage.getItem("id"))) {
      return (
        <Button onClick={() => this.openModifyEventModal(eventId)}>
          MODIFY
        </Button>
      );
    } else return null;
  }

  render() {
    const { t } = this.props;
    const events = this.props.events;
    const eventsSize = events.length;

    return (<>
      <div className="evenTableDiv">
        <Table responsive striped bordered variant="light">
          <thead>
            <tr>
              <th />
              <th>{t("homePage->eventTable->nameColumn")}</th>
              <th>{t("homePage->eventTable->eventTypeColumn")}</th>
              <th>{t("homePage->eventTable->organizerColumn")}</th>
              <th>{t("homePage->eventTable->quantityGuestColumn")}</th>
            </tr>
          </thead>
          <tbody>
            {this.getEventsToShow(parseArrayToFunction(events), eventsSize).map(row => (
              <tr
                key={
                  row.id +
                  row.eventName +
                  row.organizer.firstName +
                  row.organizer.lastName
                }
              >
                <td>
                  <Button onClick={() => this.openEventViewModal(row.id)}>
                    {t("homePage->viewButton")}
                  </Button>
                  {this.renderModifyButton(row.id, row.organizer.id)}
                </td>
                <td>{row.eventName}</td>
                <td>{this.getTraduction(row.type)}</td>
                <td>{row.organizer.fistName + " " + row.organizer.lastName}</td>
                <td>{row.quantityOfGuest}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
      <Paginator totalItems={eventsSize}
                 itemsPerPage={this.state.itemsPerPage} 
                 currentPageNumber={this.state.currentPageNumber} 
                 pageChangeHandler={this.handlePageChange}/>
      </>
    );
  }
}

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    events: state.EventReducer.events,
    modify: state.ModalViewReducer.modify,
    loggedUser: state.UserReducer.loggedUser
  };
}

function mapDispatchToProps(dispatch) {
  return {
    openEventView: event => dispatch(openEventView(event)),
    openModifyEventModal: event => dispatch(openModifyEventModal(event))
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(EventTable));

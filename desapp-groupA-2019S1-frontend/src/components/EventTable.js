import React from "react";
import Table from "react-bootstrap/Table";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import PropTypes from "prop-types";
import { connect } from "react-redux";
import Button from "react-bootstrap/Button";
// action
import { openEventView } from "../actions/ModalViewActions";
// API's
import EventApi from "../api/EventApi";

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
        return t("eventType->littleCowRepresentatives");
      }
      if (eventType === "BAQUITA_REPRESENTATIVES") {
        return t("eventType->littleCowComunitary");
      }
    }
  }

  openEventViewModal(eventid) {
    const eventApi = new EventApi();
    eventApi.getEvent(eventid).then(response => {
      this.props.openEventView(response.data);
    });
  }

  render() {
    const { t } = this.props;
    const events = this.props.events;
    return (
      <Table striped bordered hover variant="dark">
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
          {parseArrayToFunction(events).map(row => (
            <tr
              key={
                row.eventName + row.organizer.firstName + row.organizer.lastName
              }
            >
              <td>
                <Button onClick={() => this.openEventViewModal(row.id)}>
                  {t("homePage->viewButton")}
                </Button>
              </td>
              <td>{row.eventName}</td>
              <td>{this.getTraduction(row.type)}</td>
              <td>{row.organizer.fistName + " " + row.organizer.lastName}</td>
              <td>{row.quantityOfGuest}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    );
  }
}

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    events: state.EventReducer.events
  };
}

function mapDispatchToProps(dispatch) {
  return {
    openEventView: eventid => dispatch(openEventView(eventid))
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(EventTable));

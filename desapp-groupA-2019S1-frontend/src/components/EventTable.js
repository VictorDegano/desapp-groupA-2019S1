import React from "react";
import Table from "react-bootstrap/Table";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import PropTypes from "prop-types";
import { connect } from "react-redux";
import Button from "react-bootstrap/Button";

function createDataWithJson(jsonDeEvento) {
  return {
    name: jsonDeEvento.name,
    eventType: jsonDeEvento.type,
    organizer: jsonDeEvento.organizer,
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
    events: PropTypes.array,
  };

  constructor(props, context) {
    super(props, context);
    this.getTraduction = this.getTraduction.bind(this);
  }

  getTraduction(eventType){
    const { t } = this.props;

    if(eventType === 'FIESTA'){
      return t("eventType->party");
    }
    if(eventType === 'CANASTA'){
      return t("eventType->toBasket");
    }
    if(eventType === 'BAQUITA_COMUNITARY'){
      return t("eventType->littleCowRepresentatives");
    }
    if(eventType === 'BAQUITA_REPRESENTATIVES'){
      return t("eventType->littleCowComunitary");
    }
  }
  
  render() {
    const { t } = this.props;
    const events = this.props.events;
    return (
      <Table striped bordered hover variant="dark">
        <thead>
          <tr>
            <th/>
            <th>{t("homePage->eventTable->nameColumn")}</th>
            <th>{t("homePage->eventTable->eventTypeColumn")}</th>
            <th>{t("homePage->eventTable->organizerColumn")}</th>
            <th>{t("homePage->eventTable->quantityGuestColumn")}</th>
          </tr>
        </thead>
        <tbody>
          {parseArrayToFunction(events).map(row => (
            <tr key={row.name + row.organizer.firstName}>
              <td><Button>Ver</Button></td>
              <td>{row.name}</td>
              <td>{this.getTraduction(row.type)}</td>
              <td>{row.organizer.firstName}</td>
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
    events: state.EventReducer.events,
  };
}

export default connect(
  mapStateToProps
)(withTranslation()(EventTable));

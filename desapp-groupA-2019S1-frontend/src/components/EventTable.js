import React from "react";
import Table from "react-bootstrap/Table";
import PropTypes from "prop-types";

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
    arrayDeEventos: PropTypes.array
  };
  static defaultProps = {
    arrayDeEventos: []
  };

  constructor(props, context) {
    super(props, context);
  }

  render() {
    return (
      <Table striped bordered hover variant="dark">
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Tipo de evento</th>
            <th>Organiza</th>
            <th>Cantidad de invitados</th>
          </tr>
        </thead>
        <tbody>
          {parseArrayToFunction(this.props.arrayDeEventos).map(row => (
            <tr key={row.name + row.organizer.firstName}>
              <td>{row.name}</td>
              <td>{row.type}</td>
              <td>{row.organizer.firstName}</td>
              <td>{row.quantityOfGuest}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    );
  }
}

export default EventTable;

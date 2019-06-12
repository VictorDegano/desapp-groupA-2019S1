import React from "react";
import Table from "react-bootstrap/Table";

function createDataWithJson(jsonDeEvento) {
  return {
    name: jsonDeEvento.name,
    eventType: jsonDeEvento.type,
    organizer: jsonDeEvento.organizer,
    guestsAmount: jsonDeEvento.guestsAmount
  };
}
function parseArrayToFunction(rowsArray) {
  return rowsArray.flat(row => createDataWithJson(row));
}

function EventTable(props) {
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
        {parseArrayToFunction(props.arrayDeEventos).map(row => (
          <tr key={row.name}>
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

export default EventTable;

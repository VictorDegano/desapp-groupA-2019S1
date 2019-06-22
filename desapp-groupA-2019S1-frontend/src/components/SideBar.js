import React from "react";
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";

class SideBar extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Accordion defaultActiveKey="0">
        <Card>
          <Accordion.Toggle as={Card.Header} eventKey="0">
            Mis Eventos
          </Accordion.Toggle>
          <Accordion.Collapse eventKey="0">
            <Card.Body>En Curso</Card.Body>
          </Accordion.Collapse>
          <Accordion.Collapse eventKey="0">
            <Card.Body>Ultimos</Card.Body>
          </Accordion.Collapse>
          <Accordion.Collapse eventKey="0">
            <Card.Body>Populares</Card.Body>
          </Accordion.Collapse>
        </Card>
        <Card>
          <Accordion.Toggle as={Card.Header} eventKey="1">
            Crear Evento
          </Accordion.Toggle>
        </Card>
      </Accordion>
    );
  }
}

export default SideBar;

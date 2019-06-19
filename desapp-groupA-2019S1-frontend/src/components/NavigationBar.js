import React from "react";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FormControl from "react-bootstrap/es/FormControl";

function NavigationBar() {
  return (
    <Navbar variant="dark">
      <Navbar.Brand href="/home" alt="Home">
        Eventeando
      </Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href="/home">Home</Nav.Link>
        <Nav.Link href="/newEvent">Crear Evento</Nav.Link>
      </Nav>
      <Form inline>
        <FormControl
          type="text"
          placeholder="Buscar Evento"
          className="mr-sm-2"
        />
        <Button variant="outline-light">Buscar</Button>
      </Form>
    </Navbar>
  );
}

export default NavigationBar;

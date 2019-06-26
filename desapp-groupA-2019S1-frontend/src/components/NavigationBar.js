import React from "react";
//Bootstrap
import "bootstrap/dist/css/bootstrap.min.css";
import Dropdown from "react-bootstrap/Dropdown";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FormControl from "react-bootstrap/es/FormControl";
import Image from "react-bootstrap/Image";
// Auth
import { auth } from "./Root";
//Actions Type
import * as ModalView_Type from "../actions/Action_Types/ModalView_Types";
//Store
import { store } from "../index";

function openModal() {
  console.log("openModal()");
  store.dispatch({ type: ModalView_Type.OPEN_PROFILE_EDITION });
}

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

    <Dropdown>
      <Dropdown.Toggle variant="link" id="dropdown-basic">
      <Image bsPrefix="btn dropdown-toggle img-responsive"
             alt="dropdown image"
             data-toggle="dropdown"
             width={50}
             height={50}
             src={localStorage.getItem("picture")}
             roundedCircle/>
      </Dropdown.Toggle>

      <Dropdown.Menu> 
        <Dropdown.Item as="button" onClick={()=>openModal()}>Edit Profile</Dropdown.Item>
        <Dropdown.Item as="button" onClick={()=>auth.logout()}>Log Out</Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>


    </Navbar>
  );
}

export default NavigationBar;

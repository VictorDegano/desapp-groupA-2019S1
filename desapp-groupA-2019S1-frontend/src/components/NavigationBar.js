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
// I18n Hook
import { withTranslation } from "react-i18next";
// Auth
import { auth } from "./Root";
//Actions Type
import * as ModalView_Type from "../actions/Action_Types/ModalView_Types";
//Store
import { store } from "../index";
import history from "../history";

function openModal() {
  console.log("openModal()");
  store.dispatch({ type: ModalView_Type.OPEN_PROFILE_EDITION });
}

function openCreateEventModal() {
  console.log("openCreateEventModal()");
  store.dispatch({ type: ModalView_Type.OPEN_CREATE_EVENT_MODAL });
}

function NavigationBar({ t }) {
  return (
    <Navbar variant="dark">
      <Navbar.Brand onClick={() => history.push("/home")} href="" alt="Home">
        {t("navBar->eventeando")}
      </Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link onClick={() => history.push("/home")} /*href="/home"*/>
          {t("navBar->home")}
        </Nav.Link>
        <Button onClick={() => openCreateEventModal()}>
          {t("navBar->createEvent")}
        </Button>
      </Nav>
      <Form inline>
        <FormControl
          type="text"
          placeholder={t("navBar->searchPlaceholder")}
          className="mr-sm-2"
        />
        <Button variant="outline-light">{t("navBar->searchButton")}</Button>
      </Form>

      <Dropdown>
        <Dropdown.Toggle variant="link" id="dropdown-basic">
          <Image
            bsPrefix="btn dropdown-toggle img-responsive"
            alt="dropdown image"
            data-toggle="dropdown"
            width={50}
            height={50}
            src={localStorage.getItem("picture")}
            roundedCircle
          />
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <Dropdown.Item as="button" onClick={() => openModal()}>
            {t("profileDropdown->editProfile")}
          </Dropdown.Item>
          <Dropdown.Item as="button" onClick={() => auth.logout()}>
            {t("profileDropdown->logOut")}
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </Navbar>
  );
}

export default withTranslation()(NavigationBar);

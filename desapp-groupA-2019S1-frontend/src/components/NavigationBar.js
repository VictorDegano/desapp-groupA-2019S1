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
import * as Account_Type from "../actions/Action_Types/AccountTypes";
// Resources
import logo from "../resources/Eventeando logo.png";

function openModal() {
  store.dispatch({ type: ModalView_Type.OPEN_PROFILE_EDITION });
}

function openCreateEventModal() {
  console.log("openCreateEventModal()");
  store.dispatch({ type: ModalView_Type.OPEN_CREATE_EVENT_MODAL });
}

function showAccount() {
  console.log("showAccount()");
  store.dispatch({ type: Account_Type.SHOW_ACCOUNT });
}
function hideAccount() {
  console.log("hideAccount()");
  store.dispatch({ type: Account_Type.HIDE_ACCOUNT });
}

function NavigationBar({ t }) {
  return (
    <Navbar variant="dark">
      <Navbar.Brand onClick={() => history.push("/home")} href="" alt="Home">
        {t("navBar->eventeando")}
      </Navbar.Brand>
      <Nav className="mr-auto">
        {/* <Nav.Link onClick={() => history.push("/home")} href="/home">
          {t("navBar->home")}
        </Nav.Link> */}
        <Nav.Link onClick={() => openCreateEventModal()}>
        {t("navBar->createEvent")}
        </Nav.Link>
        {/* <Button onClick={() => openCreateEventModal()}>
          {t("navBar->createEvent")}
        </Button> */}
        <Button onClick={() => showAccount()} href="" alt="Account">
          {t("navBar->account")}
        </Button>
        <Button onClick={() => hideAccount()} href="" alt="Events">
          {t("navBar->events")}
        </Button>
      </Nav>
      <Dropdown drop="left">
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

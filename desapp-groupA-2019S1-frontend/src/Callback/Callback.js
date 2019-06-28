import React from "react";
import { Link } from "react-router-dom";
// bootstrap
import Image from "react-bootstrap/Image";
import "bootstrap/dist/css/bootstrap.min.css";
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
// i18next Hook
import { withTranslation } from "react-i18next";
import { auth } from "../components/Root";
import history from "../history";
// resources
import logo from "../resources/Callback Logo.png";
//css
import "../css/Callback.css";

function goToHomePageAtSevenSec() {
  // console.log('goToHomePageAtSevenSec()');
  setTimeout(() => history.push("/home"), 7000);
}

function handleAuthentication() {
  // console.log('handleAuthentication()');
  auth.handleAuthentication();
}

function handleCallbackLoad() {
  // console.log('handleCallbackLoad()');
  handleAuthentication();
  goToHomePageAtSevenSec();
}

const Callback = ({t}, props) => (
  <Container className="h-100 d-flex justify-content-center align-items-center"
             onLoad={ handleCallbackLoad() }>
      <Col className="d-flex flex-column justify-content-center align-items-center">
        <Image src={logo}
               rounded
               className="imageCallback" />
        <h1 className="textCallback text-center">
          {t("callbackPage->title")}
        </h1>
        <h3 className="textCallback text-center">
          {t("callbackPage->description")+" "}
          <Link className="textLink"
                to="/home">{
            t("callbackPage->clickButton")}
          </Link>
        </h3>
      </Col>
  </Container>
);

export default withTranslation()(Callback);

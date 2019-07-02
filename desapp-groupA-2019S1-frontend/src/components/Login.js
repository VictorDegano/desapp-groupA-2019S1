import React from "react";
// Bootstrap
import "bootstrap/dist/css/bootstrap.min.css";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import Dropdown from "react-bootstrap/Dropdown";
// i18next Hook
import { withTranslation } from "react-i18next";
import i18next from "i18next";
// Auth
import { auth } from "../components/Root.js";
// Resources
import logo from "../resources/Eventeando logo.png";
import background from "../resources/Background.png";

//css
import "../css/Login.css";

function loadBackground() {
  document.body.style =
    "background: url(" + background + ") no-repeat 40% fixed / cover;";
}

const Login = ({ t }, props) => (
  <Container className="containerLogin" onLoad={loadBackground()}>
    <Row className="align-items-center upperRowContainer">
      <Col md={{ offset: 11 }}>
        <Dropdown>
          <Dropdown.Toggle
            size="sm"
            variant="outline-success"
            id="dropdown-basic"
          >
            {t("language")}
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item onClick={() => i18next.changeLanguage("en_US")}>
              {t("englishLanguage")}
            </Dropdown.Item>
            <Dropdown.Item onClick={() => i18next.changeLanguage("es_AR")}>
              {t("spanishLanguage")}
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Col>
    </Row>
    <Row className="rowLogin align-items-center">
      <Col>
        <h1 className="slogan"> {t("loginPage->slogan")} </h1>
        <h5 className="description">{t("loginPage->description")}</h5>
      </Col>
      <Col md={{ span: 3, offset: 3 }}>
        <Card className="bg-light mb-3" style={{ width: "19rem" }}>
          <Card.Title>
            <Card.Header>{t("loginPage->panelHeader")}</Card.Header>
          </Card.Title>
          <Card.Img
            className="mx-auto"
            variant="top"
            src={logo}
            style={{ width: "70%" }}
          />
          <Card.Body>
            <Card.Text>{t("loginPage->panelBody")}</Card.Text>
          </Card.Body>
          <Card.Footer className="text-center">
            <Button
              size="lg"
              variant="success"
              onClick={() => auth.login()}
              style={{ minwidth: "auto", width: "100%" }}
            >
              {t("loginPage->button")}
            </Button>
          </Card.Footer>
        </Card>
      </Col>
    </Row>
  </Container>
);

export default withTranslation()(Login);

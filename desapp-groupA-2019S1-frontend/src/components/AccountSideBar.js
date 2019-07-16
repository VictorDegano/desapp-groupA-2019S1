import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
// Actions
import {
  showEventsInProgress,
  showMostPopularEvents,
  showLastEvents
} from "../actions/EventActions";
// Bootstrap
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import {
  addMoney,
  extractMoney,
  showLastMovements
} from "../actions/AccountActions";

class AccountSideBar extends React.PureComponent {
  static propTypes = {
    addMoney: PropTypes.func.isRequired,
    extractMoney: PropTypes.func.isRequired,
    showLastMovements: PropTypes.func.isRequired
  };

  render() {
    const { t } = this.props;
    return (
      <Accordion defaultActiveKey="0">
        <Card>
          <Accordion.Toggle as={Card.Header} eventKey="0">
            {t("sidebar->myEventsLabel")}
          </Accordion.Toggle>
          <Accordion.Collapse eventKey="0">
            <Card.Body>
              <Button onClick={() => this.props.addMoney()}>
                {t("sidebar->addMoney")}
              </Button>
            </Card.Body>
          </Accordion.Collapse>
          <Accordion.Collapse eventKey="0">
            <Card.Body>
              <Button onClick={() => this.props.extractMoney()}>
                {t("sidebar->extractMoney")}
              </Button>
            </Card.Body>
          </Accordion.Collapse>
          <Accordion.Collapse eventKey="0">
            <Card.Body>
              <Button onClick={() => this.props.showLastMovements()}>
                {t("sidebar->showLastMovements")}
              </Button>
            </Card.Body>
          </Accordion.Collapse>
        </Card>
        <Card>
          <Accordion.Toggle as={Card.Header} eventKey="1">
            {t("sidebar->createEventButton")}
          </Accordion.Toggle>
        </Card>
      </Accordion>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  showEventsInProgress: () => dispatch(showEventsInProgress()),
  showMostPopularEvents: () => dispatch(showMostPopularEvents()),
  showLastEvents: () => dispatch(showLastEvents()),
  addMoney: () => dispatch(addMoney()),
  extractMoney: () => dispatch(extractMoney()),
  showLastMovements: () => dispatch(showLastMovements())
});

export default connect(
  null,
  mapDispatchToProps
)(withTranslation()(AccountSideBar));

import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
// Actions
// Bootstrap
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import {
  showEventsInProgress,
  showMostPopularEvents,
  showLastEvents
} from "../actions/EventActions";
import {
  addMoney,
  extractMoney,
  showLastMovements,
  showLoan
} from "../actions/AccountActions";
import { Col, ListGroup } from "react-bootstrap";

class AccountSideBar extends React.PureComponent {
  static propTypes = {
    addMoney: PropTypes.func.isRequired,
    extractMoney: PropTypes.func.isRequired,
    showLastMovements: PropTypes.func.isRequired,
    showLoan: PropTypes.func.isRequired
  };

  render() {
    const { t } = this.props;
    return (
      <Col>
        <ListGroup>
          <ListGroup.Item active>{t("sidebar->myAccountLabel")}</ListGroup.Item>
          <ListGroup.Item as="button" onClick={() => this.props.addMoney()}>
            {t("sidebar->addMoney")}
          </ListGroup.Item>
          <ListGroup.Item as="button" onClick={() => this.props.extractMoney()}>
            {t("sidebar->extractMoney")}
          </ListGroup.Item>
          <ListGroup.Item
            as="button"
            onClick={() => this.props.showLastMovements()}
          >
            {t("sidebar->showLastMovements")}
          </ListGroup.Item>
          <ListGroup.Item as="button" onClick={() => this.props.showLoan()}>
            {t("sidebar->takeALoan")}
          </ListGroup.Item>
        </ListGroup>
      </Col>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  showEventsInProgress: () => dispatch(showEventsInProgress()),
  showMostPopularEvents: () => dispatch(showMostPopularEvents()),
  showLastEvents: () => dispatch(showLastEvents()),
  addMoney: () => dispatch(addMoney()),
  extractMoney: () => dispatch(extractMoney()),
  showLastMovements: () => dispatch(showLastMovements()),
  showLoan: () => dispatch(showLoan())
});

export default connect(
  null,
  mapDispatchToProps
)(withTranslation()(AccountSideBar));

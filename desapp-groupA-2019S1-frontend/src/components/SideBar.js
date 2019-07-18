import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { Col, ListGroup } from "react-bootstrap";
// Actions
import {
  showEventsInProgress,
  showMostPopularEvents,
  showLastEvents
} from "../actions/EventActions";
// css
import "../css/Sidebar.css";
import AccountSideBar from "./AccountSideBar";

class SideBar extends React.PureComponent {
  static propTypes = {
    showEventsInProgress: PropTypes.func.isRequired,
    showMostPopularEvents: PropTypes.func.isRequired,
    showLastEvents: PropTypes.func.isRequired,
    showAccount: PropTypes.bool.isRequired
  };

  render() {
    const { t } = this.props;
    if (this.props.showAccount) {
      return <AccountSideBar />;
    } else {
      return (
        <Col>
          <ListGroup className="w-auto">
            <ListGroup.Item active>
              {t("sidebar->myEventsLabel")}
            </ListGroup.Item>
            <ListGroup.Item
              as="button"
              onClick={() => this.props.showEventsInProgress()}
            >
              {t("sidebar->inProgressButton")}
            </ListGroup.Item>
            <ListGroup.Item
              as="button"
              onClick={() => this.props.showLastEvents()}
            >
              {t("sidebar->lastButton")}
            </ListGroup.Item>
            <ListGroup.Item
              as="button"
              onClick={() => this.props.showMostPopularEvents()}
            >
              {t("sidebar->popularsButton")}
            </ListGroup.Item>
          </ListGroup>
        </Col>
      );
    }
  }
}

const mapDispatchToProps = dispatch => ({
  showEventsInProgress: () => dispatch(showEventsInProgress()),
  showMostPopularEvents: () => dispatch(showMostPopularEvents()),
  showLastEvents: () => dispatch(showLastEvents())
});

export default connect(
  null,
  mapDispatchToProps
)(withTranslation()(SideBar));

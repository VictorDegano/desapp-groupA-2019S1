import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
// Actions
import { showEventsInProgress, showMostPopularEvents, showLastEvents} from "../actions/EventActions";
// Bootstrap
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
class SideBar extends React.PureComponent {
  
  static propTypes = {
    showEventsInProgress: PropTypes.func.isRequired,
    showMostPopularEvents: PropTypes.func.isRequired,
    showLastEvents: PropTypes.func.isRequired
  };

  constructor(props) {
    super(props);
  }

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
              <Button onClick={() => this.props.showEventsInProgress()}>
                {t("sidebar->inProgressButton")}
              </Button>
            </Card.Body>
          </Accordion.Collapse>
          <Accordion.Collapse eventKey="0">
            <Card.Body>
              <Button onClick={() => this.props.showMostPopularEvents()}>
                {t("sidebar->lastButton")}
              </Button>
            </Card.Body>
          </Accordion.Collapse>
          <Accordion.Collapse eventKey="0">
            <Card.Body>
              <Button onClick={() => this.props.showLastEvents()}>
                {t("sidebar->popularsButton")}
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
  showLastEvents: () => dispatch(showLastEvents())
});

export default connect(
  null,
  mapDispatchToProps
)(withTranslation()(SideBar));

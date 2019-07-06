import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
// I18n Hook
import { withTranslation } from "react-i18next";
// Bootstrap
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "react-datepicker/dist/react-datepicker-cssmodules.css";
import ListGroup from "react-bootstrap/ListGroup";
import Badge from "react-bootstrap/Badge";
// Bootstrap
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
//Actions
import { closeEventView } from "../actions/ModalViewActions";
// css
import "../css/ProfileEdition.css";
import GoodItem from "../EventViewer/Components/GoodItem";

class EventViewer extends Component {
  static propTypes = {
    closeEventView: PropTypes.func.isRequired,
    show: PropTypes.bool.isRequired,
    event: PropTypes.shape({
      eventName: PropTypes.string.isRequired,
      creationDate: PropTypes.string.isRequired
    }).isRequired
  };

  constructor(props, context) {
    super(props, context);
    this.handleClose = this.handleClose.bind(this);
    this.getEventTime = this.getEventTime.bind(this);
    this.getBadgeColour = this.getBadgeColour.bind(this);
    this.getOpenColour = this.getOpenColour.bind(this);
    this.limitTimeRendering = this.limitTimeRendering.bind(this);
    this.getConfirmationStateTraslation = this.getConfirmationStateTraslation.bind(
      this
    );
  }

  getEventTime() {
    if (this.props.event.creationDate !== "") {
      return new Date(this.props.event.creationDate);
    }
    return this.props.event.creationDate;
  }

  getBadgeColour(confirmation) {
    if (confirmation !== undefined) {
      if (confirmation === "PENDING") {
        return "warning";
      }
      if (confirmation === "ACCEPTED") {
        return "success";
      }
      if (confirmation === "CANCELLED") {
        return "danger";
      }
    } else {
      return "dark";
    }
  }

  getConfirmationStateTraslation(confirmation) {
    const { t } = this.props;
    if (confirmation !== undefined) {
      if (confirmation === "PENDING") {
        return t("eventView->confirmationState->pending");
      }
      if (confirmation === "ACCEPTED") {
        return t("eventView->confirmationState->accepted");
      }
      if (confirmation === "CANCELLED") {
        return t("eventView->confirmationState->cancelled");
      }
    } else {
      return "";
    }
  }

  getOpenColour(status){
    if (status !== undefined) {
      if (status === "OPEN") {
        return "success";
      }
      if (status === "CLOSED") {
        return "danger";
      }
    } else {
      return "dark";
    }
  }

  handleClose() {
    this.props.closeEventView();
  }

  limitTimeRendering(){
    const event = this.props.event;
    const { t } = this.props;
    
    if(event.type === "FIESTA"){
      return <>
              <Form.Label>Fecha Limite:</Form.Label>
              <div className="containerDatePicker">
                <DatePicker
                  readOnly
                  disabled
                  className="Form.Control"
                  minDate={new Date("01/01/1900")}
                  maxDate={new Date()}
                  selected={this.getEventTime()}
                  dateFormat={t("formatter->date")}
                  showYearDropdown
                  scrollableYearDropdown
                  yearDropdownItemNumber={80}
                  fixedHeight
                />
              </div>
            </>;
    } else {
      return <></>;
    }
  }

  render() {
    const { t } = this.props;
    const show = this.props.show;
    const event = this.props.event;

    let limitConfirmation = this.limitTimeRendering();

    return (
      <>
        <Modal show={show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{event.eventName}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Row>
              <Col xs={3}>
                <Form.Label>
                  {t("eventView->status")}
                  <Badge variant={ this.getOpenColour(event.status) }>
                    {event.status}
                  </Badge>
                </Form.Label>
                {limitConfirmation}
              </Col>
            </Row>
            <Row>
              <Col>
                <Form.Label>{t("eventView->organizer")}</Form.Label>
                <Form.Control
                  readOnly
                  defaultValue={
                    event.organizer.fistName + " " + event.organizer.lastName
                  }/>
              </Col>
            </Row>

            <Row>
              <Col>
                <Form.Label>{t("eventView->creationDate")}</Form.Label>
                <div className="containerDatePicker">
                  <DatePicker readOnly
                              disabled
                              className="Form.Control"
                              minDate={new Date("01/01/1900")}
                              maxDate={new Date()}
                              selected={this.getEventTime()}
                              dateFormat={t("formatter->date")}
                              showYearDropdown
                              scrollableYearDropdown
                              yearDropdownItemNumber={80}
                              fixedHeight/>
                </div>
              </Col>
            </Row>

            <Form.Label> 
              {t("eventView->guestQuantity")}
              <span>{event.quantityOfGuest}</span>
            </Form.Label>

            <Form.Label><h4>{t("eventView->guest")}</h4></Form.Label>
            <ListGroup as="ul" variant="flush">
              {event.guests.map(guest => {
                return (
                  <ListGroup.Item
                    key={guest.firstName + guest.email + guest.lastName}
                    as="li">

                    {guest.firstName + " " + guest.lastName}
                    <Badge variant={this.getBadgeColour(guest.confirmAsistance)}>
                      {this.getConfirmationStateTraslation( guest.confirmAsistance )}
                    </Badge>

                  </ListGroup.Item>
                );
              })}
            </ListGroup>

            <Form.Label><h4>{t("eventView->goods")}</h4></Form.Label>
            {this.listOfGoodsItems(event.goods)}
          </Modal.Body>

          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleClose}>
              {t("eventView->button->close")}
            </Button>
          </Modal.Footer>
          
        </Modal>
      </>
    );
  }

  listOfGoodsItems(goods){
    return <ListGroup as="ul" variant="flush">
              {goods.map(good => {
                return <ListGroup.Item key={good.id + good.name + good.price}
                                      as="li">
                    <GoodItem good={good}/>
                </ListGroup.Item>
              })}
            </ListGroup>
  }

}

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    show: state.ModalViewReducer.modalEventView,
    event: state.ModalViewReducer.event
  };
}

const mapDispatchToProps = dispatch => ({
  closeEventView: () => dispatch(closeEventView())
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(EventViewer));

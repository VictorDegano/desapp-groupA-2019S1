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
//Actions
import { closeEventView } from "../actions/ModalViewActions";
// css
import "../css/ProfileEdition.css";

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
    this.getConfirmationStateTraslation = this.getConfirmationStateTraslation.bind(this);
  }

  getEventTime(){
    if(this.props.event.creationDate!==""){
      return new Date(this.props.event.creationDate);
    }
    return this.props.event.creationDate;
  }

  getBadgeColour(confirmation){
    if(confirmation !== undefined){
      if(confirmation === 'PENDING'){
        return "warning";
      }
      if(confirmation === 'ACCEPTED'){
        return "success";
      }
      if(confirmation === 'CANCELLED'){
        return "danger";
      }
    } else {
      return "dark";
    }
  }

  getConfirmationStateTraslation(confirmation){
    const { t } = this.props;
    if(confirmation !== undefined){
      if(confirmation === 'PENDING'){
        return t("eventView->confirmationState->pending");
      }
      if(confirmation === 'ACCEPTED'){
        return t("eventView->confirmationState->accepted");
      }
      if(confirmation === 'CANCELLED'){
        return t("eventView->confirmationState->cancelled");
      }
    } else {
      return "";
    }
  }

  handleClose() {
    this.props.closeEventView();
  }

  render() {
    const { t } = this.props;
    const show = this.props.show;
    const event = this.props.event;
    return (
      <>
        <Modal show={show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{event.eventName}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Label>
              {t("eventView->status")}
            </Form.Label>
            <Form.Control plaintext
                          readOnly
                          defaultValue={event.status}/>
            <Form.Label>
            {t("eventView->organizer")}
            </Form.Label>
            <Form.Control plaintext
                          readOnly 
                          defaultValue={event.organizer.fistName+" "+event.organizer.lastName}/>
            <Form.Label>
              {t("eventView->creationDate")}
            </Form.Label>
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
            <Form.Label>
              {t("eventView->guestQuantity")}
            </Form.Label>
            <Form.Control plaintext
                          readOnly 
                          defaultValue={event.quantityOfGuest}/>
            <Form.Label>
              {t("eventView->guest")}
            </Form.Label>
            <ListGroup as="ul" variant="flush">
              {event.guests.map(guest => {
                return (
                  <ListGroup.Item as="li">
                    <p>{guest.firstName+" "+guest.lastName}
                      <Badge variant={this.getBadgeColour(guest.confirmAsistance)}>
                        {this.getConfirmationStateTraslation(guest.confirmAsistance)}
                      </Badge>
                    </p>
                  </ListGroup.Item>
                );})}
              
            </ListGroup>
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

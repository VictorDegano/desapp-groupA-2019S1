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
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
//Actions
import { closeEventView, updateEvent } from "../actions/ModalViewActions";
import {
  loadEventsInProgress,
  loadLastEvents,
  loadMostPopularEvents
} from "../actions/EventActions";
// css
import "../css/ProfileEdition.css";
import GoodItem from "../EventViewer/Components/GoodItem";
//API
import EventApi from "../api/EventApi";
import UserOverlay from "../UserOverlayView/UserOverlay";

/* TODO: Cosas que faltan:
  - aceptar/cancelar una invitacion (El boton/link solo apareceria si el usuario es el invitado)
  - Hacer visualizacion de informacion del usuario.
  - Hacer boton para volver a invitar un usuario que cancelo la invitacion
  */
class EventViewer extends Component {
  static propTypes = {
    loadEventsInProgress: PropTypes.func.isRequired,
    loadLastEvents: PropTypes.func.isRequired,
    loadMostPopularEvents: PropTypes.func.isRequired,
    closeEventView: PropTypes.func.isRequired,
    updateEvent: PropTypes.func.isRequired,
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
    this.renderOwnGoodBaquitaRepresentativeButton = this.renderOwnGoodBaquitaRepresentativeButton.bind(
      this
    );
    this.renderOwnGoodCanastaButton = this.renderOwnGoodCanastaButton.bind(
      this
    );
    this.refreshEvents = this.refreshEvents.bind(this);
    this.handleCloseEvent = this.handleCloseEvent.bind(this);
    this.handleAceptInvitation = this.handleAceptInvitation.bind(this);
    this.isAnRepresentative = this.isAnRepresentative.bind(this);
    this.isAnLoadedGood = this.isAnLoadedGood.bind(this);
    this.acceptedInvitation = this.acceptedInvitation.bind(this);
    this.renderResendInvitationButton = this.renderResendInvitationButton.bind(this);
    this.state = {
      totalCost: 0
    };
  }

  componentDidUpdate(prevProps, prevState) {
    const eventApi = new EventApi();
    const eventId = this.props.event.id;
    if (
      eventId !== 0 &&
      (prevProps.event !== this.props.event ||
        prevState.totalCost !== this.state.totalCost)
    ) {
      eventApi
        .getTotalCost(eventId)
        .then(response => this.setState({ totalCost: response.data }));
    }
  }

  getConfirmationStateTraslation(confirmation) {
    const { t } = this.props;
    if (confirmation === undefined) {
      return "";
    }
    if (confirmation === "PENDING") {
      return t("eventView->confirmationState->pending");
    }
    if (confirmation === "ACCEPTED") {
      return t("eventView->confirmationState->accepted");
    }
    if (confirmation === "CANCELLED") {
      return t("eventView->confirmationState->cancelled");
    }
  }

  getBadgeColour(confirmation) {
    if (confirmation === undefined) {
      return "dark";
    }
    if (confirmation === "PENDING") {
      return "warning";
    }
    if (confirmation === "ACCEPTED") {
      return "success";
    }
    if (confirmation === "CANCELLED") {
      return "danger";
    }
  }

  getEventTime() {
    if (this.props.event.creationDate !== "") {
      return new Date(this.props.event.creationDate);
    }
    return this.props.event.creationDate;
  }

  getOpenColour(status) {
    if (status === undefined) {
      return "dark";
    }
    if (status === "OPEN") {
      return "success";
    }
    if (status === "CLOSE") {
      return "danger";
    }
  }

  handleClose() {
    this.props.closeEventView();
  }

  limitTimeRendering() {
    const event = this.props.event;
    const { t } = this.props;

    if (event.type === "FIESTA") {
      return (
        <>
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
        </>
      );
    } else {
      return <></>;
    }
  }

  listOfGoodsItems(event) {
    return (
      <ListGroup as="ul" variant="flush">
        {event.goods.map(good => {
          return (
            <ListGroup.Item key={good.id + good.name + good.price} as="li">
              <GoodItem good={good} eventType={event.type} />
              {this.renderOwnGoodCanastaButton(event, good)}
              {this.renderOwnGoodBaquitaRepresentativeButton(event, good)}
            </ListGroup.Item>
          );
        })}
      </ListGroup>
    );
  }

  ownGood(good) {
    const eventApi = new EventApi();
    const eventId = this.props.event.id;
    const loggedUserId = localStorage.getItem("id");

    eventApi
      .ownGood(eventId, good.id, loggedUserId)
      .then(response => {
        if (response) {
          this.refreshEvents(loggedUserId);
          alert("Se pudo hacer cargo de un articulo");
        } else {
          alert("No se pudo hacerse cargo del articulo");
        }
      })
      .catch(error => {
        alert(error);
      });
  }

  takeGood(good){
    const eventApi = new EventApi();
    const eventId = this.props.event.id;
    const loggedUserId = localStorage.getItem("id");

    eventApi
      .takeGood(eventId, good.id, loggedUserId)
      .then(response => {
        if (response) {
          this.refreshEvents(loggedUserId);
          alert("Te pudiste hacer cargo del articulo");
        } else {
          alert("No se pudo hacerse cargo del articulo");
        }
      })
      .catch(error => {
        alert(error);
      });
  }

  refreshEvents(userId) {
    const eventApi = new EventApi();
    eventApi.getEvent(this.props.event.id).then(response => {
      this.props.updateEvent(response.data);
    });

    eventApi.getEventosEnCurso(userId).then(response => {
      this.props.loadEventsInProgress(response.data);
    });

    eventApi.getMisUltimosEventos(userId).then(response => {
      this.props.loadLastEvents(response.data);
    });

    eventApi.getEventosMasPopulares().then(response => {
      this.props.loadMostPopularEvents(response.data);
    });
  }

  handleCloseEvent() {
    const eventApi = new EventApi();
    const eventId = this.props.event.id;
    const loggedUserId = localStorage.getItem("id");

    eventApi.closeEvent(eventId).then(response => {
      if (response) {
        this.refreshEvents(loggedUserId);
        alert("Se ha cerrado el evento");
      } else {
        alert("No se ha podido cerrar el evento");
      }
    });
  }

  handleAceptInvitation(eventId, guestId) {
    const eventApi = new EventApi();
    const loggedUserId = localStorage.getItem("id");

    eventApi.aceptInvitation(eventId, guestId).then(response => {
      if (response) {
        this.refreshEvents(loggedUserId);
        alert("Invitacion confirmada");
      } else {
        alert("No se ha podido confirmar la invitacion");
      }
    });
  }

  isAnRepresentative(representatives, userId) {
    return representatives.some(function(representative) {
      return representative.userId === parseInt(userId);
    });
  }

  isAnLoadedGood(leadedGoods, goodId) {
    return leadedGoods.some(function(leadedGood) {
      return leadedGood.goodId === goodId;
    });
  }

  resendInvitation(event, guest){
    const eventApi = new EventApi();
    const loggedUserId = localStorage.getItem("id");

    eventApi.resendInvitation(event.id, guest.userId)
            .then(response => {
      if (response) {
        this.refreshEvents(loggedUserId);
        alert("Invitacion Enviada");
      } else {
        alert("No se ha podido enviar la invitacion");
      }
    });
  }

  acceptedInvitation(guests, userId) {
    return guests.some(function(guest) {
      return (
        guest.userId === parseInt(userId) &&
        guest.confirmAsistance === "ACCEPTED"
      );
    });
  }

  variantStyleButton(disabled){
    return disabled ? "outline-dark" : "outline-success";
  }

  renderResendInvitationButton(event, guest) {
    const { t } = this.props;
    const organizerId = this.props.event.organizer.id;
    const loggedUserId = parseInt(localStorage.getItem("id"));
    const disabled = guest.confirmAsistance !== "CANCELLED";

    if (event.status !== "CLOSE" && organizerId === loggedUserId) {
      return (
        <Button
          disabled={disabled}
          onClick={() => this.resendInvitation(event, guest)}
          size="sm"
          variant={this.variantStyleButton(disabled)}
        >
          {t("eventView->button->sendInvitation")}
        </Button>
      );
    } 
    
    return null;
  }

  renderOwnGoodCanastaButton(event, good) {
    const { t } = this.props;
    
    if (
      event.status !== "CLOSE" &&
      event.type === "CANASTA" &&
      this.acceptedInvitation(event.guests, localStorage.getItem("id"))
    ) {
      const disabled = ! good.available;

      return (
        <Button
          disabled={disabled}
          onClick={() => this.ownGood(good)}
          size="sm"
          variant={this.variantStyleButton(disabled)}
        >
          {t("eventView->button->ownGood")}
        </Button>
      );
    } 
    
    return null;
  }

  renderOwnGoodBaquitaRepresentativeButton(event, good) {
    const { t } = this.props;

    if (
      event.status !== "CLOSE" &&
      event.type === "BAQUITA_REPRESENTATIVES" &&
      this.isAnRepresentative(event.representatives, localStorage.getItem("id"))
    ) {
      const disabled = this.isAnLoadedGood(event.loadedGoods, good.id);

      return (
        <Button
          disabled={disabled}
          onClick={() => this.takeGood(good)}
          size="sm"
          variant={this.variantStyleButton(disabled)}
        >
          {t("eventView->button->ownGoodRepresentative")}
        </Button>
      );
    } 
    
    return null;
  }

  renderCloseEventButton() {
    const { t } = this.props;
    const eventStatus = this.props.event.status;
    const organizerId = this.props.event.organizer.id;
    const loggedUserId = parseInt(localStorage.getItem("id"));

    if (organizerId === loggedUserId) {
      const disabled = eventStatus === "CLOSE";

      return (
        <Button
          disabled={disabled}
          variant={disabled ? "dark" : "secondary"}
          onClick={this.handleCloseEvent}
        >
          {t("eventView->button->closeEvent")}
        </Button>
      );
    }

    return null;
  }

  renderAceptInvitationButton(event, guest) {
    const { t } = this.props;
    const loggedUserId = localStorage.getItem("id");

    if (
      event.status !== "CLOSE" &&
      guest.userId === parseInt(loggedUserId) &&
      guest.confirmAsistance === "PENDING"
    ) {
      const disabled = guest.confirmAsistance !== "PENDING";

      return (
        <Button
          onClick={() => this.handleAceptInvitation(event.id, guest.guestId)}
          size="sm"
          variant={this.variantStyleButton(disabled)}
        >
          {t("eventView->button->acceptInvitation")}
        </Button>
      );
    } 
    
    return null;
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
                  <Badge variant={this.getOpenColour(event.status)}>
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
                  }
                />
              </Col>
            </Row>

            <Row>
              <Col>
                <Form.Label>{t("eventView->creationDate")}</Form.Label>
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
              </Col>
            </Row>
            <Row>
              <Form.Label>
                {t("eventView->guestQuantity")}
                <span>{event.quantityOfGuest}</span>
              </Form.Label>
            </Row>
            <Form.Label>
              <h4>{t("eventView->guest")}</h4>
            </Form.Label>
            <ListGroup as="ul" variant="flush">
              {event.guests.map(guest => {
                return (
                  <ListGroup.Item
                    key={guest.id + guest.firstName + guest.email + guest.lastName}
                    as="li"
                  >
                    <UserOverlay guest={guest}/>{/* {guest.firstName + " " + guest.lastName} */}
                    <Badge
                      variant={this.getBadgeColour(guest.confirmAsistance)}
                    >
                      {this.getConfirmationStateTraslation(
                        guest.confirmAsistance
                      )}
                    </Badge>
                    {this.renderResendInvitationButton(event, guest)}
                    {this.renderAceptInvitationButton(event, guest)}
                  </ListGroup.Item>
                );
              })}
            </ListGroup>
            <Form.Label>
              <h4>{t("eventView->goods")}</h4>
            </Form.Label>
            {this.listOfGoodsItems(event)}
            <Form.Label>
              <h4>
                {t("eventView->totalCost")} {t("formatter->currency")}{" "}
                {t("formatter->number", { number: this.state.totalCost })}
              </h4>
            </Form.Label>
          </Modal.Body>

          <Modal.Footer>
            {this.renderCloseEventButton()}
            <Button variant="success" onClick={this.handleClose}>
              {t("eventView->button->close")}
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
}

const mapStateToProps = state => ({
  show: state.ModalViewReducer.modalEventView,
  event: state.ModalViewReducer.event
});

const mapDispatchToProps = dispatch => ({
  closeEventView: () => dispatch(closeEventView()),
  updateEvent: event => dispatch(updateEvent(event)),
  loadEventsInProgress: events => dispatch(loadEventsInProgress(events)),
  loadLastEvents: events => dispatch(loadLastEvents(events)),
  loadMostPopularEvents: events => dispatch(loadMostPopularEvents(events))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(EventViewer));

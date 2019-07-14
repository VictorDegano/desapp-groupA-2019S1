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
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
//Actions
import { closeCreateEventModal } from "../actions/ModalViewActions";
// css
import "../css/ProfileEdition.css";
import UserApi from "../api/UserApi";
import { updateLoggedUser } from "../actions/UserActions";
import EventApi from "../api/EventApi";
import EmailsInput from "./EmailsInput";
import {
  loadEventsInProgress,
  loadLastEvents,
  loadMostPopularEvents,
  showEventsInProgress
} from "../actions/EventActions";

class CreateEventModal extends Component {
  static propTypes = {
    closeCreateEventModal: PropTypes.func.isRequired,
    modify: PropTypes.bool.isRequired,
    show: PropTypes.bool.isRequired,
    event: PropTypes.shape({
      eventName: PropTypes.string.isRequired,
      creationDate: PropTypes.string.isRequired
    }).isRequired,
    loggedUser: PropTypes.object,
    updateLoggedUser: PropTypes.func.isRequired
  };

  constructor(props, context) {
    super(props, context);
    this.handleClose = this.handleClose.bind(this);
    this.getEventTime = this.getEventTime.bind(this);
    this.getBadgeColour = this.getBadgeColour.bind(this);
    this.getConfirmationStateTraslation = this.getConfirmationStateTraslation.bind(
      this
    );
    this.handleSave = this.handleSave.bind(this);
    this.change = this.change.bind(this);
    this.handleChangeOnEventName = this.handleChangeOnEventName.bind(this);
    this.handleConfirmationDayChange = this.handleConfirmationDayChange.bind(
      this
    );
    this.handleGoodNameChange = this.handleGoodNameChange.bind(this);
    this.handleGoodPricePerUnitChange = this.handleGoodPricePerUnitChange.bind(
      this
    );
    this.handleGoodQuantityForPersonChange = this.handleGoodQuantityForPersonChange.bind(
      this
    );
    this.handleChangeNewGoodName = this.handleChangeNewGoodName.bind(this);
    this.handleChangeNewGoodPricePerUnit = this.handleChangeNewGoodPricePerUnit.bind(
      this
    );
    this.handleChangeNewGoodQuantityForPerson = this.handleChangeNewGoodQuantityForPerson.bind(
      this
    );
    this.handleAddNewGood = this.handleAddNewGood.bind(this);
    this.handleDeleteGood = this.handleDeleteGood.bind(this);
    this.EmailsInputRef = React.createRef();
    this.renderFinalDate = this.renderFinalDate.bind(this);
    this.handleUpdateStateToModify = this.handleUpdateStateToModify.bind(this);

    this.state = {
      eventName: "FiestaExample",
      creationDate: new Date(),
      goods: [
        {
          name: "Fernet",
          pricePerUnit: 40,
          quantityForPerson: 1
        }
      ],
      guests: [
        {
          confirmAsistance: "",
          firstName: "",
          guestId: 0,
          lastName: "",
          mail: "",
          userId: 0
        }
      ],
      id: 0,
      organizer: this.props.loggedUser,
      quantityOfGuest: 0,
      status: "OPEN",
      type: "FIESTA",
      confirmationDay: new Date(),
      newGood: {
        name: "",
        pricePerUnit: 0,
        quantityForPerson: 0
      }
    };
  }

  componentDidMount() {
    const loggedUser = this.props.loggedUser;

    if (this.props.loggedUser === null) {
      console.log("EL LOGGED USER ES NULL");
      const userApi = new UserApi();
      userApi.fetchUser(localStorage.getItem("id")).then(user => {
        this.props.updateLoggedUser(user);
        this.setState({
          newEvent: {
            organizer: {
              startDate: new Date(user.bornDay),
              firstName: user.firstName,
              lastName: user.lastName,
              email: user.email
            }
          }
        });
      });
    } else {
      console.log("EL LOGGED USER NO ES NULL");
      this.setState({
        firstName: loggedUser.firstName,
        newEvent: {
          organizer: {
            startDate: loggedUser.bornDate,
            firstName: loggedUser.firstName,
            lastName: loggedUser.lastName,
            email: loggedUser.email
          }
        }
      });
    }
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

  getOrganizerName() {
    let firstName = localStorage.getItem("first_name");
    let lastName = localStorage.getItem("last_name");
    return firstName + " " + lastName;
  }

  change(event) {
    this.setState({ type: event.target.value });
  }

  handleClose() {
    this.props.closeCreateEventModal();
  }

  handleChangeOnEventName(event) {
    this.setState({ eventName: event.target.value });
  }
  handleConfirmationDayChange(date) {
    this.setState({ confirmationDay: date });
  }
  handleChangeNewGoodName(event) {
    let newValue = event.target.value;
    this.setState(prevState => ({
      // object that we want to update
      newGood: {
        ...prevState.newGood, // keep all other key-value pairs
        name: newValue // update the value of specific key
      }
    }));
  }
  handleChangeNewGoodPricePerUnit(event) {
    let newValue = event.target.value;
    this.setState(prevState => ({
      // object that we want to update
      newGood: {
        ...prevState.newGood, // keep all other key-value pairs
        pricePerUnit: parseInt(newValue) // update the value of specific key
      }
    }));
  }

  handleChangeNewGoodQuantityForPerson(event) {
    let newValue = event.target.value;
    this.setState(prevState => ({
      // object that we want to update
      newGood: {
        ...prevState.newGood, // keep all other key-value pairs
        quantityForPerson: parseInt(newValue) // update the value of specific key
      }
    }));
  }

  handleAddNewGood() {
    let newGood = this.state.newGood;
    this.state.goods.push(newGood);
    console.log(newGood);
    this.setState({
      goods: this.state.goods,
      newGood: {
        name: "",
        pricePerUnit: 0,
        quantityForPerson: 0
      }
    });
  }

  handleDeleteGood(event) {
    const eventKey = event.target.attributes.getNamedItem("data-key").value;

    this.state.goods.splice(eventKey);

    this.setState({
      goods: this.state.goods
    });
  }

  handleUpdateStateToModify() {
    if (this.props.modify) {
      this.setState({
        eventName: this.props.event.eventName,
        creationDate: new Date(this.props.event.creationDate),
        goods: this.props.event.goods,
        guests: this.props.event.guests,
        id: this.props.event.id,
        organizer: this.props.event.organizer,
        quantityOfGuest: this.props.event.quantityOfGuest,
        status: this.props.event.status,
        type: this.props.event.type,
        confirmationDay: new Date(this.props.event.limitConfirmationDateTime)
      });
    }
  }

  refreshEventsOnHome() {
    let userId = this.props.loggedUser.id;
    var eventApi = new EventApi();

    eventApi.getEventosEnCurso(userId).then(response => {
      this.props.loadEventsInProgress(response.data);
      this.props.showEventsInProgress();
    });

    eventApi.getMisUltimosEventos(userId).then(response => {
      this.props.loadLastEvents(response.data);
    });

    eventApi.getEventosMasPopulares().then(response => {
      this.props.loadMostPopularEvents(response.data);
    });
  }

  handleSave(event) {
    // console.log("handleSave()");
    event.preventDefault();
    const eventApi = new EventApi();
    console.log(event);
    const currentEmailsInputRef = this.EmailsInputRef.current;

    const eventExample = {
      type: this.state.type,
      id: 1,
      eventName: this.state.eventName,
      organizer: this.props.loggedUser,
      quantityOfGuest: 1,
      goods: this.state.goods,
      guests: this.createJsonOfEmails(currentEmailsInputRef.state.items),
      status: "OPEN",
      creationDate: this.state.creationDate,
      limitConfirmationDateTime: this.state.confirmationDay
    };
    console.log("voy a crear evento con");
    console.log(eventExample);
    eventApi
      .createEvent(eventExample)
      .then(response => {
        console.log(response);
        this.refreshEventsOnHome();
        this.handleClose();
      })
      .catch(e => console.log(e));
  }

  handleGoodNameChange(event) {
    // event.preventDefault();
    // console.log("event:" + event);
    const eventKey = event.target.attributes.getNamedItem("data-key").value;
    const newName = event.target.value;
    const list = this.state.goods.map((item, j) => {
      if (j.toString() === eventKey) {
        item.name = newName;
        return item;
      } else {
        return item;
      }
    });
    this.setState({ goods: list });
  }

  handleGoodQuantityForPersonChange(event) {
    // event.preventDefault();
    // console.log("event:" + event);
    const eventKey = event.target.attributes.getNamedItem("data-key").value;
    const newquantityForPerson = event.target.value;
    const list = this.state.goods.map((item, j) => {
      if (j.toString() === eventKey) {
        item.quantityForPerson = newquantityForPerson;
        return item;
      } else {
        return item;
      }
    });
    this.setState({ goods: list });
  }

  handleGoodPricePerUnitChange(event) {
    // event.preventDefault();
    // console.log("event:" + event);
    const eventKey = event.target.attributes.getNamedItem("data-key").value;
    const newPricePerUnit = event.target.value;
    const list = this.state.goods.map((item, j) => {
      if (j.toString() === eventKey) {
        item.pricePerUnit = newPricePerUnit;
        return item;
      } else {
        return item;
      }
    });
    this.setState({ goods: list });
  }

  getModalTitle() {
    if (this.props.modify) {
      return "Modify Event";
    } else {
      return "Create Event";
    }
  }

  createJsonOfEmails(items) {
    let json = [];
    items.forEach(i => json.push({ mail: i }));

    return json;
  }

  renderFinalDate() {
    const { t } = this.props;
    if (this.state.type === "FIESTA") {
      return (
        <>
          <Form.Label>Confirmation Day</Form.Label>
          <div className="containerDatePicker">
            <DatePicker
              className="Form.Control"
              minDate={new Date()}
              maxDate={new Date("12/12/2020")}
              selected={this.state.confirmationDay}
              onChange={this.handleConfirmationDayChange}
              dateFormat={t("formatter->date")}
              showYearDropdown
              scrollableYearDropdown
              yearDropdownItemNumber={80}
              fixedHeight
            />
          </div>
        </>
      );
    }
    return null;
  }

  render() {
    const { t } = this.props;
    const show = this.props.show;
    const event = this.props.event;
    const { validated } = this.state;
    return (
      <>
        <Modal
          size="lg"
          show={show}
          onHide={this.handleClose}
          onEnter={this.handleUpdateStateToModify}
        >
          <Form onSubmit={this.handleSave} noValidate validated={validated}>
            <Modal.Header closeButton>
              <Modal.Title>{this.getModalTitle()}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Row>
                <Col>
                  <Form.Label className="h5 text-center">
                    Event Name:
                  </Form.Label>
                </Col>
                <Col>
                  <Form.Control
                    onChange={this.handleChangeOnEventName}
                    type="text"
                    defaultValue={this.state.eventName}
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label>Type</Form.Label>
                </Col>
                <Col>
                  <Form.Control
                    as="select"
                    onChange={this.change}
                    value={this.state.type}
                  >
                    <option value="FIESTA">Fiesta</option>
                    <option value="CANASTA">Canasta</option>
                    <option value="BAQUITA_COMUNITARY">
                      Baquita Comunitaria
                    </option>
                    <option value="BAQUITA_REPRESENTATIVES">
                      Baquita Representantes
                    </option>
                    plaintext readOnly defaultValue={this.state.type}
                  </Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label>{t("eventView->organizer")}</Form.Label>
                </Col>
                <Col>
                  <Form.Control
                    plaintext
                    readOnly
                    defaultValue={this.getOrganizerName()}
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label>{t("eventView->creationDate")}</Form.Label>
                </Col>
                <Col>
                  <div className="containerDatePicker">
                    <DatePicker
                      readOnly
                      disabled
                      className="Form.Control"
                      minDate={new Date("01/01/1900")}
                      maxDate={new Date()}
                      selected={this.state.creationDate}
                      dateFormat={t("formatter->date")}
                      showYearDropdown
                      scrollableYearDropdown
                      yearDropdownItemNumber={80}
                      fixedHeight
                    />
                  </div>
                </Col>
              </Row>
              {this.renderFinalDate()}
              <Form.Label>Emails:</Form.Label>
              <EmailsInput ref={this.EmailsInputRef} />
              <Form.Label>Goods:</Form.Label>
              <Form.Group>
                <Row>
                  <Col>
                    <Form.Control
                      value={this.state.newGood.name}
                      onChange={this.handleChangeNewGoodName}
                      type="text"
                      placeholder="Name"
                    />
                  </Col>
                  <Col>
                    <Form.Control
                      value={this.state.newGood.pricePerUnit}
                      onChange={this.handleChangeNewGoodPricePerUnit}
                      type="number"
                      placeholder="Price"
                    />
                  </Col>
                  <Col>
                    <Form.Control
                      value={this.state.newGood.quantityForPerson}
                      onChange={this.handleChangeNewGoodQuantityForPerson}
                      type="number"
                      placeholder="Price"
                    />
                  </Col>
                  <Col>
                    <Button variant="success" onClick={this.handleAddNewGood}>
                      +
                    </Button>
                  </Col>
                </Row>
              </Form.Group>
              <Form.Group>
                {this.state.goods.map((good, index) => {
                  return (
                    <Row key={index}>
                      <Col>
                        <Form.Control
                          key={index}
                          data-key={index}
                          value={good.name}
                          onChange={this.handleGoodNameChange}
                          required
                          type="text"
                        />
                      </Col>
                      <Col>
                        <Form.Control
                          key={index}
                          data-key={index}
                          value={good.pricePerUnit}
                          onChange={this.handleGoodPricePerUnitChange}
                          required
                          type="number"
                        />
                      </Col>
                      <Col>
                        <Form.Control
                          key={index}
                          data-key={index}
                          value={good.quantityForPerson}
                          onChange={this.handleGoodQuantityForPersonChange}
                          required
                          type="number"
                        />
                      </Col>
                      <Col>
                        <Button
                          key={index}
                          data-key={index}
                          variant="danger"
                          onClick={this.handleDeleteGood}
                        >
                          x
                        </Button>
                      </Col>
                    </Row>
                  );
                })}
              </Form.Group>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" type="submit">
                {t("profileEditionModal->buttons->save")}
              </Button>
              <Button variant="secondary" onClick={this.handleClose}>
                {t("eventView->button->close")}
              </Button>
            </Modal.Footer>
          </Form>
        </Modal>
      </>
    );
  }
}

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    show: state.ModalViewReducer.modalCreateEventState,
    event: state.ModalViewReducer.event,
    loggedUser: state.UserReducer.loggedUser,
    modify: state.ModalViewReducer.modify
  };
}

const mapDispatchToProps = dispatch => ({
  closeCreateEventModal: () => dispatch(closeCreateEventModal()),
  updateLoggedUser: user => dispatch(updateLoggedUser(user)),
  showEventsInProgress: events => dispatch(showEventsInProgress(events)),
  loadEventsInProgress: events => dispatch(loadEventsInProgress(events)),
  loadLastEvents: events => dispatch(loadLastEvents(events)),
  loadMostPopularEvents: events => dispatch(loadMostPopularEvents(events))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(CreateEventModal));

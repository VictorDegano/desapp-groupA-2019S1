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
import { closeCreateEventModal } from "../actions/ModalViewActions";
// css
import "../css/ProfileEdition.css";
import UserApi from "../api/UserApi";
import { updateLoggedUser } from "../actions/UserActions";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import EventApi from "../api/EventApi";

class CreateEventModal extends Component {
  static propTypes = {
    closeCreateEventModal: PropTypes.func.isRequired,
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
      type: "Fiesta",
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

  handleSave(event) {
    // console.log("handleSave()");
    event.preventDefault();
    const eventApi = new EventApi();
    console.log(event);
    const eventExample = {
      type: this.handleEventType(),
      id: 1,
      eventName: this.state.eventName,
      organizer: this.props.loggedUser,
      quantityOfGuest: 1,
      goods: this.state.goods,
      guests: [
        {
          guestId: 1,
          userId: 1,
          mail: "jose@gmail.com",
          firstName: "jose",
          lastName: "macana",
          confirmAsistance: "PENDING"
        }
      ],
      status: "OPEN",
      creationDate: this.state.creationDate,
      limitConfirmationDateTime: this.state.confirmationDay
    };
    console.log("voy a crear evento con");
    console.log(eventExample);
    eventApi
      .createEvent(eventExample)
      .then(response => console.log(response))
      .catch(e => console.log(e));
  }

  render() {
    const { t } = this.props;
    const show = this.props.show;
    const event = this.props.event;
    const { validated } = this.state;
    return (
      <>
        <Modal show={show} onHide={this.handleClose}>
          <Form onSubmit={this.handleSave} noValidate validated={validated}>
            <Modal.Header closeButton>
              <Modal.Title>Create Event</Modal.Title>
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
                    plaintext
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
                    <option>Fiesta</option>
                    <option>Canasta</option>
                    <option>Baquita Comunitaria</option>
                    <option>Baquita Representantes</option>
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
              <Form.Label>{t("eventView->guestQuantity")}</Form.Label>
              <Form.Control
                plaintext
                readOnly
                defaultValue={this.state.quantityOfGuest}
              />
              <Form.Label>{t("eventView->guest")}</Form.Label>
              <ListGroup as="ul" variant="flush">
                {this.state.guests.map(guest => {
                  return (
                    <ListGroup.Item
                      key={guest.firstName + guest.email + guest.lastName}
                      as="li"
                    >
                      <p>
                        {guest.firstName + " " + guest.lastName}
                        <Badge
                          variant={this.getBadgeColour(guest.confirmAsistance)}
                        >
                          {this.getConfirmationStateTraslation(
                            guest.confirmAsistance
                          )}
                        </Badge>
                      </p>
                    </ListGroup.Item>
                  );
                })}
              </ListGroup>

              <Form.Label>Goods:</Form.Label>

              <Form>
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
                    <Form.Control placeholder="Prize" />
                  </Col>
                  <Col>
                    <Form.Control placeholder="Quantity" />
                  </Col>
                  <Col>
                    <Button variant="success">+</Button>
                  </Col>
                </Row>
              </Form>
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
                          type="text"
                        />
                      </Col>
                      <Col>
                        <Form.Control
                          key={index}
                          data-key={index}
                          value={good.quantityForPerson}
                          onChange={this.handleGoodQuantityForPersonChange}
                          required
                          type="text"
                        />
                      </Col>
                      <Col>
                        <Button variant="danger">x</Button>
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

  handleEventType() {
    if (this.state.type === "Canasta") {
      return "CANASTA";
    }
    if (this.state.type === "Fiesta") {
      return "FIESTA";
    }
    return "BAD HANDLE TYPE";
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
}

function mapStateToProps(state) {
  // console.log('mapStateToProps()')
  return {
    show: state.ModalViewReducer.modalCreateEventState,
    event: state.ModalViewReducer.event,
    loggedUser: state.UserReducer.loggedUser
  };
}

const mapDispatchToProps = dispatch => ({
  closeCreateEventModal: () => dispatch(closeCreateEventModal()),
  updateLoggedUser: user => dispatch(updateLoggedUser(user))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(CreateEventModal));

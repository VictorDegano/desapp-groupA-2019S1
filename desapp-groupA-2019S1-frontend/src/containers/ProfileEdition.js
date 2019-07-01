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
import "../css/ProfileEdition.css";
import Alert from "react-bootstrap/Alert";
//Actions
import { updateLoggedUser } from "../actions/UserActions";
import { closeProfileEdition } from "../actions/ModalViewActions";
// API's
import UserApi from "../api/UserApi";

class ProfileEdition extends Component {
  static propTypes = {
    updateLoggedUser: PropTypes.func.isRequired,
    closeProfileEdition: PropTypes.func.isRequired,
    show: PropTypes.bool,
    loggedUser: PropTypes.object
  };

  constructor(props, context) {
    super(props, context);
    this.state = {
      startDate: new Date(),
      firstName: "",
      lastName: "",
      email: "",
      showAlert: false,
      messageAlert: ""
    };
    this.handleClose = this.handleClose.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.handleBornDateChange = this.handleBornDateChange.bind(this);
    this.checkValidation = this.checkValidation.bind(this);
    this.handleDismiss = this.handleDismiss.bind(this);
  }

  componentDidMount() {
    const loggedUser = this.props.loggedUser;

    if(this.props.loggedUser === null){
      const userApi = new UserApi();
      userApi.fetchUser(localStorage.getItem("id")).then(user =>{
          this.props.updateLoggedUser(user);
          this.setState({
            startDate: new Date(user.bornDay),
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email
          });
        }
      )
    } else {
      this.setState({
        startDate: new Date(loggedUser.bornDate),
        firstName: loggedUser.fistName,
        lastName: loggedUser.lastName,
        email: loggedUser.email
      });
    }
  }

  handleClose() {
    // console.log("handleClose()");
    this.props.closeProfileEdition();
    this.setState({
      startDate: new Date(),
      showAlert: false,
      messageAlert: ""
    });
  }

  handleBornDateChange(date) {
    // this.selectedDate = date;
    this.setState({
      startDate: date
    });
  }

  handleSave(event) {
    // console.log("handleSave()");
    event.preventDefault();

    if (this.checkValidation(event)) {
      const loggedUser = this.props.loggedUser;

      const userToSave = {
        id: loggedUser.id,
        fistName: event.target[1].value,
        lastName: event.target[2].value,
        email: loggedUser.email,
        bornDate: this.state.startDate.toISOString()
      };

      const userToStateUpdate = {
        id: loggedUser.id,
        fistName: event.target[1].value,
        lastName: event.target[2].value,
        email: loggedUser.email,
        bornDate: this.state.startDate.toJSON()
      };

      const userApi = new UserApi();

      userApi
        .putUser(userToSave)
        .then(response => {
          this.props.updateLoggedUser(userToStateUpdate);
          this.handleClose();
        })
        .catch(error => {
          alert("An error has occurred, please try again");
        });
    } else {
      event.stopPropagation();
      this.setState({
        showAlert: true,
        messageAlert: this.getError(event)
      });
    }
  }

  checkValidation(event) {
    return (
      event.target[1].value !== "" &&
      event.target[2].value !== "" &&
      (this.state.startDate !== null &&
        this.state.startDate <= new Date() &&
        this.state.startDate >= new Date("01/01/1900"))
    );
  }

  getError(event) {
    let error = "";
    if (event.target[1].value === "") {
      error += error !== "" ? "\n" : "";
      error += "El nombre no puede ser vacio";
    }
    if (event.target[2].value === "") {
      error += error !== "" ? "\n" : "";
      error += "El Apellido no puede ser vacio";
    }
    if (
      this.state.startDate === null ||
      (this.state.startDate > new Date()) |
        (this.state.startDate < new Date("01/01/1900"))
    ) {
      error += error !== "" ? "\n" : "";
      error += "La fecha tiene que ser menor a hoy y mayor a 01/01/1990";
    }

    return error;
  }

  handleDismiss() {
    this.setState({
      showAlert: false,
      messageAlert: ""
    });
  }

  render() {
    const { t } = this.props;
    const loggedUser = this.props.loggedUser;
    const show = this.props.show;
    const { validated } = this.state;

    return (
      <>
        <Modal show={show} onHide={this.handleClose}>
          <Alert
            variant="danger"
            show={this.state.showAlert}
            onClose={this.handleDismiss}
            dismissible
          >
            <Alert.Heading>¡Error!</Alert.Heading>
            <p>{this.state.messageAlert}</p>
          </Alert>
          <Form onSubmit={this.handleSave} noValidate validated={validated}>
            <Modal.Header closeButton>
              <Modal.Title>{t("profileEditionModal->title")}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Form.Group controlId="formBasicFirstName">
                <Form.Label>
                  {t("profileEditionModal->form->firstName")}
                </Form.Label>
                <Form.Control
                  required
                  type="text"
                  placeholder={t(
                    "profileEditionModal->form->firstNamePlaceholder"
                  )}
                  defaultValue={this.state.firstName}
                />
              </Form.Group>
              <Form.Group controlId="formBasicLastName">
                <Form.Label>
                  {t("profileEditionModal->form->lastName")}
                </Form.Label>
                <Form.Control
                  required
                  type="text"
                  placeholder={t(
                    "profileEditionModal->form->lastNamePlaceholder"
                  )}
                  defaultValue={this.state.lastName}
                />
              </Form.Group>
              <Form.Group controlId="formBasicBornDate">
                <Form.Label>
                  {t("profileEditionModal->form->bornDate")}
                </Form.Label>
                <div className="containerDatePicker">
                  <DatePicker
                    required
                    className="Form.Control"
                    minDate={new Date("01/01/1900")}
                    maxDate={new Date()}
                    selected={this.state.startDate}
                    onChange={this.handleBornDateChange}
                    dateFormat={t("formatter->date")}
                    placeholderText={t(
                      "profileEditionModal->form->bornDatePlaceholder"
                    )}
                    showYearDropdown
                    scrollableYearDropdown
                    yearDropdownItemNumber={80}
                    fixedHeight
                  />
                </div>
              </Form.Group>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>{t("profileEditionModal->form->email")}</Form.Label>
                <Form.Control
                  required
                  type="email"
                  placeholder={t("profileEditionModal->form->emailPlaceholder")}
                  disabled
                  defaultValue={this.state.email}
                />
              </Form.Group>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={this.handleClose}>
                {t("profileEditionModal->buttons->close")}
              </Button>
              <Button variant="primary" type="submit">
                {t("profileEditionModal->buttons->save")}
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
    show: state.ModalViewReducer.modalProfileState,
    loggedUser: state.UserReducer.loggedUser
  };
}

const mapDispatchToProps = dispatch => ({
  updateLoggedUser: user => dispatch(updateLoggedUser(user)),
  closeProfileEdition: () => dispatch(closeProfileEdition())
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(ProfileEdition));

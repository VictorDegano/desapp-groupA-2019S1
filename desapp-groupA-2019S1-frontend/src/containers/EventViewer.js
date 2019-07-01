import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
// I18n Hook
import { withTranslation } from "react-i18next";
// Bootstrap
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "react-datepicker/dist/react-datepicker-cssmodules.css";
//Actions
import { closeEventView } from "../actions/ModalViewActions";
// API's
import EventApi from "../api/EventApi";

class EventViewer extends Component {
  static propTypes = {
    closeModalEvent: PropTypes.func.isRequired,
    show: PropTypes.bool,
  };

  constructor(props, context) {
    super(props, context);
    this.state = {
      event: null,
    };
    this.handleClose = this.handleClose.bind(this);
  }

  componentWillMount() {
  }

  handleClose() {
    // console.log("handleClose()");
    this.props.closeProfileEdition();
    this.setState({
      startDate: new Date(),
    });
  }

  render() {
    const { t } = this.props;
    const show = this.props.show;
    const { validated } = this.state;

    return (
      <>
        <Modal show={show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{t("profileEditionModal->title")}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {/* nombre
            organizador
            tipo
            cantidad de invitados
            goods
            guests
            limit confirmation
            confirmation */}
                {/* <div className="containerDatePicker">
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
                </div> */}
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={this.handleClose}>
                {t("profileEditionModal->buttons->close")}
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
    show: state.ModalViewReducer.modalEventView
  };
}

const mapDispatchToProps = dispatch => ({
  closeEventView: () => dispatch(closeEventView())
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(EventViewer));

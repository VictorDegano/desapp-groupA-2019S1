import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { Button, Col, Form, InputGroup } from "react-bootstrap";
import AccountApi from "../api/AccountApi";

class AddMoney extends React.PureComponent {
  static propTypes = {
    loggedUser: PropTypes.object
  };

  constructor(...args) {
    super(...args);

    this.state = { validated: false, amount: 0 };
    this.handleChangeAmountValue = this.handleChangeAmountValue.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      // event.stopPropagation();
    }
    this.setState({ validated: true });

    const accountApi = new AccountApi();
    accountApi
      .depositMoney(this.state.amount, this.props.loggedUser.id)
      .then(response => {
        console.log(response);
      })
      .catch(e => console.log(e));
  }
  handleChangeAmountValue(event) {
    this.setState({ amount: event.target.value });
  }

  render() {
    const { t } = this.props;
    const { validated } = this.state;
    return (
      <Form
        noValidate
        validated={validated}
        onSubmit={e => this.handleSubmit(e)}
      >
        <Form.Row>
          <Form.Group as={Col} md="2" controlId="validationCustom01">
            <Form.Label>Amount</Form.Label>
          </Form.Group>
          <Form.Group as={Col} md="4" controlId="validationCustom02">
            <Form.Control
              required
              value={this.state.amount}
              onChange={this.handleChangeAmountValue}
              type="number"
              placeholder="$1000"
            />
            <Form.Control.Feedback type="invalid">
              Enter an amount
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group as={Col} md="4" controlId="validationCustom03">
            <Button type="submit">Submit form</Button>
          </Form.Group>
        </Form.Row>
        <Form.Group as={Col} md="4" controlId="validationCustom02">
          <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
        </Form.Group>
      </Form>
    );
  }
}
function mapStateToProps(state) {
  return {
    loggedUser: state.UserReducer.loggedUser
  };
}

const mapDispatchToProps = dispatch => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(AddMoney));

import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { Button, Col, Form } from "react-bootstrap";
import AccountApi from "../api/AccountApi";
import { updateBalance } from "../actions/AccountActions";

class ExtractMoney extends React.PureComponent {
  static propTypes = {
    loggedUser: PropTypes.object,
    updateBalance: PropTypes.func.isRequired
  };

  constructor(...args) {
    super(...args);

    this.state = { validated: false, amount: 0 };
    this.handleChangeAmountValue = this.handleChangeAmountValue.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false || this.state.amount <= 0) {
      return;
    }
    this.setState({ validated: true });

    const accountApi = new AccountApi();
    accountApi
      .extractMoney(this.state.amount, this.props.loggedUser.id)
      .then(response => {
        console.log(response);
        this.updateUserBalance();
      })
      .catch(e => console.log(e));
  }
  handleChangeAmountValue(event) {
    this.setState({ amount: event.target.value });
  }

  updateUserBalance() {
    const accountApi = new AccountApi();
    accountApi
      .getUserBalance(this.props.loggedUser.id)
      .then(response => {
        console.log(response);
        this.props.updateBalance(response);
      })
      .catch(e => console.log(e));
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
          <h2>Extract Money</h2>
        </Form.Row>
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

const mapDispatchToProps = dispatch => ({
  updateBalance: balance => dispatch(updateBalance(balance))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(ExtractMoney));

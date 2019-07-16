import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { Button, Col, Form } from "react-bootstrap";
import { toast } from "react-toastify";
import AccountApi from "../api/AccountApi";
import { updateBalance, updateLastMovements } from "../actions/AccountActions";

class ExtractMoney extends React.PureComponent {
  static propTypes = {
    loggedUser: PropTypes.object,
    updateBalance: PropTypes.func.isRequired,
    updateLastMovements: PropTypes.func.isRequired
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
        toast("Come on, give me that money back! (Is your's now btw)", {
          type: "success"
        });
        this.updateUserBalance();
        this.updateUserMovements();
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

  amountValidation() {
    return this.state.amount <= 0;
  }

  updateUserMovements() {
    const accountApi = new AccountApi();
    accountApi
      .getUserMovements(this.props.loggedUser.id)
      .then(response => {
        console.log(response);
        this.props.updateLastMovements(response);
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
          <h2>{t("accountComponents->extractMoneyTitle")}</h2>
        </Form.Row>
        <Form.Row>
          <Form.Group as={Col} md="2" controlId="validationCustom01">
            <Form.Label>{t("accountComponents->amount")}</Form.Label>
          </Form.Group>
          <Form.Group as={Col} md="4" controlId="validationCustom02">
            <Form.Control
              required
              value={this.state.amount}
              onChange={this.handleChangeAmountValue}
              type="number"
              isInvalid={this.amountValidation()}
              placeholder="$1000"
              min={0}
            />
            <Form.Control.Feedback type="invalid">
              Enter a positive amount of money
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group as={Col} md="4" controlId="validationCustom03">
            <Button type="submit" disabled={this.amountValidation()}>
              {t("accountComponents->confirm")}
            </Button>
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
  updateBalance: balance => dispatch(updateBalance(balance)),
  updateLastMovements: movements => dispatch(updateLastMovements(movements))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(ExtractMoney));

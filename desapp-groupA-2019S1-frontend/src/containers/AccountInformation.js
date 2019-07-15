import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
// I18n Hook
import { withTranslation } from "react-i18next";
import "../css/ProfileEdition.css";
import { withRouter } from "react-router";
import AccountApi from "../api/AccountApi";
import { updateBalance } from "../actions/AccountActions";
import { updateLoggedUser } from "../actions/UserActions";

class AccountInformation extends Component {
  static propTypes = {
    addMoney: PropTypes.bool,
    balance: PropTypes.number,
    updateBalance: PropTypes.func.isRequired
  };

  constructor(props, context) {
    super(props, context);
  }

  componentDidMount() {
    this.updateUserBalance();
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
    return (
      <>
        <h1>Balance: {this.props.balance}</h1>
      </>
    );
  }
}

function mapStateToProps(state) {
  return {
    loggedUser: state.UserReducer.loggedUser,
    addMoney: state.AccountReducer.addMoney,
    balance: state.AccountReducer.balance
  };
}

const mapDispatchToProps = dispatch => ({
  updateBalance: balance => dispatch(updateBalance(balance))
});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(withTranslation()(AccountInformation))
);

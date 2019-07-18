import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
// I18n Hook
import { withTranslation } from "react-i18next";
import "../css/ProfileEdition.css";
import { withRouter } from "react-router";
import AddMoney from "../components/AddMoney";
import AccountInformation from "./AccountInformation";
import ExtractMoney from "../components/ExtractMoney";
import AccountMovements from "./AccountMovements";
import AccountLoan from "../components/AccountLoan";
import AccountLoanInformation from "../components/AccountLoanInformation";

class AccountViewer extends Component {
  static propTypes = {
    addMoney: PropTypes.bool.isRequired,
    extractMoney: PropTypes.bool.isRequired,
    showLastMovements: PropTypes.bool.isRequired,
    loan: PropTypes.bool.isRequired
  };

  constructor(props, context) {
    super(props, context);
  }

  componentDidMount() {}

  render() {
    if (this.props.addMoney) {
      return (
        <>
          <AccountInformation />
          <AddMoney />
        </>
      );
    }
    if (this.props.extractMoney) {
      return (
        <>
          <AccountInformation />
          <ExtractMoney />
        </>
      );
    }
    if (this.props.showLastMovements) {
      return (
        <>
          <AccountInformation />
          <AccountMovements />
        </>
      );
    }
    if (this.props.loan) {
      return (
        <>
          <AccountInformation />
          <AccountLoan />
          <AccountLoanInformation />
        </>
      );
    } else {
      return (
        <>
          <AccountInformation />
        </>
      );
    }
  }
}

function mapStateToProps(state) {
  return {
    loggedUser: state.UserReducer.loggedUser,
    addMoney: state.AccountReducer.addMoney,
    extractMoney: state.AccountReducer.extractMoney,
    showLastMovements: state.AccountReducer.showLastMovements,
    loan: state.AccountReducer.loan
  };
}

const mapDispatchToProps = dispatch => ({});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(withTranslation()(AccountViewer))
);

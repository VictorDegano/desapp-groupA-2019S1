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

class AccountViewer extends Component {
  static propTypes = {
    addMoney: PropTypes.bool,
    extractMoney: PropTypes.bool,
    showLastMovements: PropTypes.bool
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
    } else {
      return (
        <>
          <h1>Hello Account</h1>
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
    showLastMovements: state.AccountReducer.showLastMovements
  };
}

const mapDispatchToProps = dispatch => ({});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(withTranslation()(AccountViewer))
);

import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
// I18n Hook
import { withTranslation } from "react-i18next";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import "../css/ProfileEdition.css";
import { withRouter } from "react-router";
import AddMoney from "../components/AddMoney";

class AccountViewer extends Component {
  static propTypes = {
    addMoney: PropTypes.bool
  };

  constructor(props, context) {
    super(props, context);
  }

  componentDidMount() {}

  render() {
    if (this.props.addMoney) {
      return <AddMoney />;
    } else {
      return (
        <>
          <h1>Hello Account</h1>
        </>
      );
    }
  }
}

function mapStateToProps(state) {
  return {
    loggedUser: state.UserReducer.loggedUser,
    addMoney: state.AccountReducer.addMoney
  };
}

const mapDispatchToProps = dispatch => ({});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(withTranslation()(AccountViewer))
);

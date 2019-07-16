import React from "react";
// I18n Hook
import { withTranslation } from "react-i18next";
// Redux
import { connect } from "react-redux";
import PropTypes from "prop-types";
import Table from "react-bootstrap/Table";
import moment from "moment";
import AccountApi from "../api/AccountApi";
import {
  updateBalance,
  updateCredits,
  updateLastMovements
} from "../actions/AccountActions";
import { Card } from "react-bootstrap";

class AccountLoanInformation extends React.PureComponent {
  static propTypes = {
    loggedUser: PropTypes.object,
    updateCredits: PropTypes.func.isRequired,
    credits: PropTypes.shape({
      id: PropTypes.number,
      quotasToPay: PropTypes.number,
      hasDefaulted: PropTypes.bool,
      date: PropTypes.string,
      user: PropTypes.shape({
        id: PropTypes.number,
        fistName: PropTypes.string,
        lastname: PropTypes.string,
        bornDate: PropTypes.string,
        email: PropTypes.string
      })
    }).isRequired
  };

  constructor(...args) {
    super(...args);
  }

  componentDidMount() {
    this.updateCredits();
  }

  updateCredits() {
    const accountApi = new AccountApi();
    accountApi
      .creditsOnCourse(this.props.loggedUser.id)
      .then(response => {
        this.props.updateCredits(response);
        console.log(response);
      })
      .catch(e => console.log(e));
  }

  conditionalRenderOnQuotasToPay() {
    const { t } = this.props;
    if (this.props.credits.quotasToPay > 0) {
      return (
        <>
          <h2>{t("accountComponents->creditsOnCourseTitle")}</h2>

          <Table responsive striped bordered hover variant="dark">
            <thead>
              <tr>
                <th>{t("accountComponents->quotasToPay")}</th>
                <th>{t("accountComponents->date")}</th>
                <th>{t("accountComponents->hasDefaulted")}</th>
              </tr>
            </thead>
            <tbody>
              <tr key={this.props.credits.id}>
                <td>{this.props.credits.quotasToPay}</td>
                <td>
                  {moment(this.props.credits.date).format(
                    "DD/MM/YYYY hh:mm:ss"
                  )}
                </td>
                {this.renderHasDefaulted()}
              </tr>
            </tbody>
          </Table>
        </>
      );
    } else {
      return (
        <>
          <Card bg="success" text="white">
            <Card.Title className="text-center">
              <h2>
                {t("accountComponents->creditsOnCourseTitleNoCreditsOnCourse")}
              </h2>
            </Card.Title>
          </Card>
        </>
      );
    }
  }

  renderHasDefaulted() {
    const { t } = this.props;
    if (this.props.credits.hasDefaulted) {
      return (
        <>
          <td> Yes </td>
        </>
      );
    } else {
      return (
        <>
          <td> No </td>
        </>
      );
    }
  }

  render() {
    const { t } = this.props;

    return <>{this.conditionalRenderOnQuotasToPay()}</>;
  }
}

function mapStateToProps(state) {
  return {
    loggedUser: state.UserReducer.loggedUser,
    credits: state.AccountReducer.credits
  };
}

const mapDispatchToProps = dispatch => ({
  updateBalance: balance => dispatch(updateBalance(balance)),
  updateLastMovements: movements => dispatch(updateLastMovements(movements)),
  updateCredits: credits => dispatch(updateCredits(credits))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withTranslation()(AccountLoanInformation));

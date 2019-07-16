import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
// I18n Hook
import { withTranslation } from "react-i18next";
import "../css/ProfileEdition.css";
import { withRouter } from "react-router";
import Table from "react-bootstrap/Table";
import * as moment from "moment";
import AccountApi from "../api/AccountApi";
import { updateBalance, updateLastMovements } from "../actions/AccountActions";
import Paginator from "./Paginator";

class AccountMovements extends Component {
  static propTypes = {
    addMoney: PropTypes.bool,
    balance: PropTypes.number,
    updateLastMovements: PropTypes.func.isRequired,
    movements: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number,
        amount: PropTypes.number,
        flowType: PropTypes.string,
        date: PropTypes.date,
        type: PropTypes.string
      })
    ).isRequired
  };

  constructor(props, context) {
    super(props, context);
    this.state = {
      currentPageNumber: 1,
      itemsPerPage: 5
    };
    this.handlePageChange = this.handlePageChange.bind(this);
  }

  componentDidMount() {
    this.updateUserMovements();
  }

  getMovementsToShow(movements, movementsSize) {
    let movemetsToShow = [];
    let startIndex =
      this.state.itemsPerPage * (this.state.currentPageNumber - 1);
    let endIndex = Math.min(
      this.state.itemsPerPage * this.state.currentPageNumber,
      movementsSize
    );

    for (startIndex; startIndex < endIndex; startIndex++) {
      movemetsToShow.push(movements[startIndex]);
    }

    return movemetsToShow;
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

  handlePageChange(newCurrentPage) {
    this.setState({ currentPageNumber: newCurrentPage });
  }

  render() {
    const movementsList = this.props.movements;
    const movementsSize = movementsList.length;
    const { t } = this.props;
    return (
      <>
        <Table responsive striped bordered hover variant="dark">
          <thead>
            <tr>
              <th>{t("accountComponents->flowType")}</th>
              <th>{t("accountComponents->date")}</th>
              <th>{t("accountComponents->amount")}</th>
              <th>{t("accountComponents->type")}</th>
            </tr>
          </thead>
          <tbody>
            {this.getMovementsToShow(movementsList, movementsSize).map(mov => (
              <tr key={mov.id}>
                <td>{mov.flowType}</td>
                <td>{moment(mov.date).format("DD/MM/YYYY hh:mm:ss")}</td>
                <td>{mov.amount}</td>
                <td>{mov.type}</td>
              </tr>
            ))}
          </tbody>
        </Table>
        <Paginator
          totalItems={movementsSize}
          itemsPerPage={this.state.itemsPerPage}
          currentPageNumber={this.state.currentPageNumber}
          pageChangeHandler={this.handlePageChange}
        />
      </>
    );
  }
}

function mapStateToProps(state) {
  return {
    loggedUser: state.UserReducer.loggedUser,
    addMoney: state.AccountReducer.addMoney,
    balance: state.AccountReducer.balance,
    movements: state.AccountReducer.movements
  };
}

const mapDispatchToProps = dispatch => ({
  updateBalance: balance => dispatch(updateBalance(balance)),
  updateLastMovements: movements => dispatch(updateLastMovements(movements))
});

export default withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(withTranslation()(AccountMovements))
);

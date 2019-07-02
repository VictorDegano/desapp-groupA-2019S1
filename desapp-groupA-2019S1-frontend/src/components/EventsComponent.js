import React from "react";
// Redux
import { connect } from "react-redux";
import EventTable from "./EventTable";
// I18n Hook
import { withTranslation } from "react-i18next";

const EventsComponent = props => (
  <div>
    <h1 className="text-white">{props.t(props.eventTableTitle)}</h1>
    <EventTable />
  </div>
);

function mapStateToProps(state) {
  return { eventTableTitle: state.EventReducer.eventTableTitle };
}

export default connect(mapStateToProps)(withTranslation()(EventsComponent));

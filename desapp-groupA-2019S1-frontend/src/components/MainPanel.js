import React from "react";
import EventsComponent from "./EventsComponent";
import EventViewer from "../containers/EventViewer";
import AccountViewer from "../containers/AccountViewer";

class MainPanel extends React.PureComponent {
  render() {
    if (this.props.showAccount) {
      return <AccountViewer />;
    } else {
      return (
        <div>
          <EventViewer />
          <EventsComponent />
        </div>
      );
    }
  }
}

export default MainPanel;

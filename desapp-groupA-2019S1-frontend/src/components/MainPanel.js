import React from "react";
import EventsComponent from "./EventsComponent";
import EventViewer from "../containers/EventViewer";

class MainPanel extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <EventViewer/>
        <EventsComponent/>
      </div>
    );
  }
}

export default MainPanel;

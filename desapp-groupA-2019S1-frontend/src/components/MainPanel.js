import React from "react";
import EventsComponent from "./EventsComponent";

class MainPanel extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <EventsComponent/>
      </div>
    );
  }
}

export default MainPanel;

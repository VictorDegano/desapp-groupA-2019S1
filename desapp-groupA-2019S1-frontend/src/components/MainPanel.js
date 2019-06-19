import React from "react";
import EventsComponent from "./EventsComponent";

class MainPanel extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <EventsComponent
          title={this.props.title}
          arrayDeEventos={this.props.arrayDeEventos}
        />
      </div>
    );
  }
}

export default MainPanel;

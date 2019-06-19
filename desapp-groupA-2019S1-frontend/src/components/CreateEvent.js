import React from "react";
import NavigationBar from "./NavigationBar";

class CreateEvent extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <NavigationBar />
        <h1>Hello</h1>;
      </div>
    );
  }
}

export default CreateEvent;

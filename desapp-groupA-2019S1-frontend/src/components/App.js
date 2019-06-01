import React from "react";
import EventList from "../containers/EventList";
import {withRouter} from "react-router-dom";


const App = () => (
  <div>
    Hello World! Its The Home Page!
    <EventList/>
  </div>
);

export default withRouter(App);

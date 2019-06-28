import React, { Suspense } from "react";
import { Provider } from "react-redux";
import PropTypes from "prop-types";
// Router
import { Route } from "react-router";
import { Router } from "react-router-dom";
import history from "../history";
// Auth
import Auth from "../Auth/Auth";
// Custom component
import PrivateRoute from "./PrivateRoute";
import Login from "./Login";
import App from "./App";
import Callback from "../Callback/Callback";
import CreateEvent from "./CreateEvent";

export const auth = new Auth();

const Loader = () => <div>loading...</div>;

//El tag provider hace que todo lo que este dentro de este tag, este conectado al store de redux
const Root = ({ store }) => (
  <Suspense fallback={<Loader />}>
    <Provider store={store}>
      <Router history={history}>
        <Route exact path="/" component={Login} />
        <Route path="/callback" component={Callback} />
        <PrivateRoute exact path="/home" component={App} />
        <PrivateRoute path="/newEvent" component={CreateEvent} />
      </Router>
    </Provider>
  </Suspense>
);

Root.propTypes = {
  store: PropTypes.object.isRequired
};

export default Root;

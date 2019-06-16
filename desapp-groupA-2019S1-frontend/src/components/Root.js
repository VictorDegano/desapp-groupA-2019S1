import React, { Suspense } from "react";
import { Provider } from "react-redux";
import { Route } from "react-router";
import { Router } from "react-router-dom";
import PropTypes from "prop-types";
import Login from "./Login";
import App from "./App";
import UserProfile from "../containers/UserProfile";
import Callback from "../Callback/Callback";
import history from "../history";
import Auth from "../Auth/Auth";
import PrivateRoute from "./PrivateRoute"

export const auth = new Auth();

const Loader = () => <div>loading...</div>;

//El tag provider hace que todo lo que este dentro de este tag, este conectado al store de redux
//TODO: el user profile hay que hacer que reciba al usuario una vez se lo traiga del backend
const Root = ({ store }) => (
  <Suspense fallback={<Loader />}>
    <Provider store={store}>
      <Router history={history}>
          <Route exact 
                 path="/" 
                 component={Login} />
          <Route path="/callback" 
                 component={Callback} />

          <PrivateRoute exact
                        path="/home" 
                        component={App} />
          <PrivateRoute path="/profile" 
                        component={UserProfile} />
      </Router>
    </Provider>
  </Suspense>
);

Root.propTypes = {
  store: PropTypes.object.isRequired
};

export default Root;

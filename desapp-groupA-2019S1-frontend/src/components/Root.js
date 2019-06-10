import React from "react";
import { Provider } from "react-redux";
import { Route } from "react-router";
import { Router } from "react-router-dom";
import Login from "../components/Login";
import App from "../components/App";
import UserProfile from "../containers/UserProfile";
import Callback from "../Callback/Callback";
import PropTypes from "prop-types";
import history from "../history";
import Auth from "../Auth/Auth";

export const auth = new Auth();

//El tag provider hace que todo lo que este dentro de este tag, este conectado al store de redux
//TODO: el user profile hay que hacer que reciba al usuario una vez se lo traiga del backend
const Root = ({ store }) => (
  <Provider store={store}>
    <Router history={history}>
        <div>
            <Route  exact 
                    path="/" 
                    component={Login}/>
            <Route  path="/home"
                    component={App}/>
            <Route  path="/callback"
                    component={Callback}/>
            <Route  path="/profile"
                    component={UserProfile}/> 
        </div>
    </Router>
  </Provider>
);

Root.propTypes = {
  store: PropTypes.object.isRequired,
};

export default Root;
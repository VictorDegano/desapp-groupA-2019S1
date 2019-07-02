import React from "react";
import { Route, Redirect } from "react-router-dom";
import { auth } from "../components/Root.js";

/*For this wil be think in three central requirements. First of all, this is 
  builded with out redux, because the authentication status is not in the redux storage
  and the auth.js handle all about the authentication.

  Requirement 1.
  It has the same API as <Route />

  Requirement 2.
  It renders a <Route /> and passes all the props through to it.

  Requirement 3.
  It checks if the user is authenticated, if they are,
  it renders the "component" prop. If not, it redirects
  the user to /.
*/

const PrivateRoute = ({ component: Component, ...rest }) => (
  //TODO: Estaria bueno que te mande a una pagina que diga que no estas logeado y te envie al login,
  <Route
    {...rest}
    component={props =>
      auth.isAuthenticated() === true ? (
        <Component {...props} />
      ) : (
        <Redirect to="/" />
      )
    }
  />
);

export default PrivateRoute;

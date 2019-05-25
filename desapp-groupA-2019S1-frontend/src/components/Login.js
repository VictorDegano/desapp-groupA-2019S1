import React from "react";
import {auth} from "../components/Root.js";

const Login = () => (
  <div>
    <button onClick={ () => auth.login() }>Log In</button>
  </div>
);

export default Login;

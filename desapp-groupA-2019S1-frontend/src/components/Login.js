import React from "react";
import { auth } from "./Root";

const Login = () => (
  <div>
    <button onClick={() => auth.login()}>Log In</button>
  </div>
);

export default Login;

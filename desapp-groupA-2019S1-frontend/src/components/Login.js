import React from 'react'
import Auth from '../Auth/Auth';

const auth = new Auth();

const Login = () => (
  <div>
    <button onClick={()=>auth.login()}>Log In</button>
  </div>
);

export default Login;

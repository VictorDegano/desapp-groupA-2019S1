import React from 'react';
import { Link } from 'react-router-dom';
import history from "../history";
import {auth} from '../components/Root.js';

function goToHomePageAtSevenSec() {
  setTimeout(function(){
    console.log(history.location);
    history.push('/home')}, 7000);
}

function handleAuthentication() {
  auth.handleAuthentication();
}

function handleCallbackLoad() {
  handleAuthentication();
  goToHomePageAtSevenSec(); 
}

const Callback = props => (
  <div onLoad={handleCallbackLoad()}>
    A Simple Callback Page, will put some Loading Screen in the next release :D.
    We redirect you to the home page in 7 sec.
    If you don't be redirected <Link to='/home'>Click Here</Link>
    {console.log(props)}
  </div>
);

export default Callback;
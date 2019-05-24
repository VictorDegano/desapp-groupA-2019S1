import React from 'react';
import { Link } from 'react-router-dom';
import history from "../history";
import {auth} from '../components/Root.js';

function goToHomePageAtSevenSec() {
  console.log('goToHomePageAtSevenSec()');
  
  setTimeout(history.push('/home'), 7000);
}

function handleAuthentication() {
  console.log('handleAuthentication()');
  auth.handleAuthentication();
}

function handleCallbackLoad() {
  console.log('handleCallbackLoad()');
  handleAuthentication();
  goToHomePageAtSevenSec(); 
}

const Callback = props => (
  <div onLoad={handleCallbackLoad()}>
    A Simple Callback Page, will put some Loading Screen in the next release :D.
    We redirect you to the home page in 7 sec.
    If you don't be redirected <Link to='/home'>Click Here</Link>
  </div>
);

export default Callback;
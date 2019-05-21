import React from 'react';
import history from "../history";

function goToHomePageAtSevenSec() {
  setTimeout(function(){
    console.log(history.location);
    history.push('/home')}, 7000);
}

const Callback = props => (
  <div onLoad={goToHomePageAtSevenSec()}>
    A Simple Callback Page, will put some Loading Screen in the next release :D.
    We redirect you to the home page in 7 sec.
    If you don't 
    {console.log(props)}
  </div>
);

export default Callback;
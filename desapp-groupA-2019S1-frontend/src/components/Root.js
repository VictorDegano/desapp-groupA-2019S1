import React from 'react';
import { Provider } from 'react-redux';
import { Route } from 'react-router';
import { Router } from 'react-router-dom';
import Login from '../components/Login';
import App from '../components/App';
import Callback from '../Callback/Callback';
import PropTypes from 'prop-types';
import history from "../history";


//El tag provider hace que todo lo que este dentro de este tag, este conectado al store de redux
const Root = ({ store }) => (
  <Provider store={store}>
    <Router history={history}>
        <div>
            <Route  exact 
                    path='/' 
                    component={Login}/>
            <Route  path='/home'
                    component={App}/>
            <Route  path='/callback'
                    component={Callback}/>
        </div>
    </Router>
  </Provider>
);

Root.propTypes = {
  store: PropTypes.object.isRequired,
};

export default Root;
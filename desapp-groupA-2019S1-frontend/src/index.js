import React from 'react'
import { render } from 'react-dom'
import { createStore } from 'redux'
import { Provider } from 'react-redux'
import App from './components/App'

//Reducers
import rootReducer from '../src/reducers/index'

//Store
const store = createStore(rootReducer)

//El tag provider hace que todo lo que este dentro de este tag, este conectado al store de redux
render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
)

import React from 'react'
import { render } from 'react-dom'
import { createStore } from 'redux'
import Root from './components/Root'
//Reducers
import rootReducer from '../src/reducers/index'

//Store
const store = createStore(rootReducer)

render(
  <Root store={store} />,
  document.getElementById('root')
)

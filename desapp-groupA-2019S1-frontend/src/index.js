import React from "react";
import { render } from "react-dom";
import { createStore, applyMiddleware } from "redux";
import logger from "redux-logger";
import thunk from "redux-thunk";
import Root from "./components/Root";
//Reducers
import rootReducer from "./reducers/RootReducer";
// import i18n (needs to be bundled ;))
import "./i18n";
// bootstrap
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
//css
import "./css/Index.css";
import "./css/root.css";

//Store
const store = createStore(rootReducer, {}, applyMiddleware(logger, thunk));

render(<Root store={store} />, document.getElementById("root"));

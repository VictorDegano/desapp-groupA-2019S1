import React from "react";
import { render } from "react-dom";
import Root from "./components/Root";
//Redux 
import { createStore } from "redux";
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
export const store = createStore(rootReducer);

render(<Root store={store} />, document.getElementById("root"));

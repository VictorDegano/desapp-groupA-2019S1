//Store
import { store } from "../index";
import * as ACTION_TYPE from "./Action_Types/AccountTypes";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function showAccount() {
  // console.log('showLastEvents()');

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.SHOW_ACCOUNT
  };
}

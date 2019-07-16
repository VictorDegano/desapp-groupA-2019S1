import * as ACTION_TYPE from "./Action_Types/AccountTypes";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function showAccount() {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.SHOW_ACCOUNT
  };
}

export function addMoney() {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.ADD_MONEY
  };
}

export function updateBalance(balance) {
  return {
    type: ACTION_TYPE.UPDATE_BALANCE,
    balance: balance
  };
}

export function extractMoney() {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.EXTRACT_MONEY
  };
}

export function showLastMovements() {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.SHOW_LAST_MOVEMENTS
  };
}

export function updateLastMovements(movements) {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.UPDATE_MOVEMENTS,
    movements: movements.reverse()
  };
}

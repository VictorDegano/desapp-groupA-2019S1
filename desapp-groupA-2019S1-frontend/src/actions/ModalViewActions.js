import * as ACTION_TYPE from "./Action_Types/ModalView_Types";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function openProfileEdition() {
  // console.log("openProfileEdition()");

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.OPEN_PROFILE_EDITION
  };
}

export function closeProfileEdition() {
  // console.log("closeProfileEdition()");

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.CLOSE_PROFILE_EDITION
  };
}

export function openCreateEventModal() {
  // console.log("closeProfileEdition()");

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.OPEN_CREATE_EVENT_MODAL
  };
}

export function closeCreateEventModal() {
  // console.log("closeProfileEdition()");

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.CLOSE_CREATE_EVENT_MODAL
  };
}

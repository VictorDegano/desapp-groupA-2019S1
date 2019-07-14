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

export function openEventView(event) {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.OPEN_EVENT_VIEW,
    event: event
  };
}

export function closeEventView() {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.CLOSE_EVENT_VIEW
  };
}

export function updateEvent(event) {
  return {
    type: ACTION_TYPE.UPDATE_EVENT,
    event: event
    // goodId: goodId
  };
}

export function updateEventToStateClose() {
  return {
    type: ACTION_TYPE.CLOSE_EVENT
  };
}

export function openModifyEventModal(event) {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.OPEN_MODIFY_EVENT_MODAL,
    event: event
  };
}

export function closeModifyEventModal() {
  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.CLOSE_MODIFY_EVENT_MODAL
  };
}

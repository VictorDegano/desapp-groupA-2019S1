import * as ACTION_TYPE from "./Action_Types/Event_Types";
//Store
import { store } from "../index";

//Todas las funciones de acciones que hacemos llevan un type que es la accion que realiza y luego la informacion
export function showEventsInProgress() {
  // console.log('showEventsInProgress()');

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.SHOW_EVENTS_IN_PROGRESS,
    eventTableTitle: "homePage->inProgressEventsLabel",
    events: store.getState().EventReducer.eventsInProgress
  };
}

export function showLastEvents() {
  // console.log('showLastEvents()');

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.SHOW_EVENTS_LAST,
    eventTableTitle: "homePage->lastEventsLabel",
    events: store.getState().EventReducer.lastEvents
  };
}

export function showMostPopularEvents() {
  // console.log('showMostPopularEvents()');

  //Las acciones devuelve un objeto
  return {
    type: ACTION_TYPE.SHOW_EVENTS_MOST_POPULAR,
    eventTableTitle: "homePage->mostPopularEventsLabel",
    events: store.getState().EventReducer.mostPopularEvents
  };
}

export function loadEventsInProgress(events) {
  return {
    type: ACTION_TYPE.EVENTS_IN_PROGRESS,
    events: events
  };
}

export function loadLastEvents(events) {
  return {
    type: ACTION_TYPE.EVENTS_LAST,
    events: events
  };
}

export function loadMostPopularEvents(events) {
  return {
    type: ACTION_TYPE.EVENTS_MOST_POPULAR,
    events: events
  };
}

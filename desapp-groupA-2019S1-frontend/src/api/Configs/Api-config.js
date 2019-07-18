import endpointResolver from "./EndpointResolver";

export const API_CONFIG = {
  contentType: "application/json",
  allowMethods: "GET,PUT,POST",
  allowOrigin: "*",
  endPoint: endpointResolver.getURL("/")
};

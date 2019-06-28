import endpointResolver from "./EndpointResolver";

export const API_CONFIG = {
  contentType: "application/json",
  allowMethods: "GET,PUT,POST,DELETE,PATCH,OPTIONS",
  allowOrigin: "*",
  endPoint: endpointResolver.getURL("/")
};

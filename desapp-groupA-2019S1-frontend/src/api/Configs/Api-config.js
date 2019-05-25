import getURL from "../Configs/EndpointResolver.js"

export const API_CONFIG = {
    contentType : "application/json",
    allowMethods: "GET,PUT,POST,DELETE,PATCH,OPTIONS",
    allowOrigin: "*",
    endPoint:  getURL("/")
};


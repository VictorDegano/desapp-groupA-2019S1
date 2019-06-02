import getURL from "../api/Configs/EndpointResolver.js";

export const AUTH_CONFIG = {
    domain: "victor-degano.auth0.com",
    clientID: "5Au9XkgiLXRsTEORGWYLdecQocwOOH8o",
    callbackUrl: getURL("/callback")
};
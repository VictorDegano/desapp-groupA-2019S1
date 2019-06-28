import endpointResolver from "../api/Configs/EndpointResolver";

export const AUTH_CONFIG = {
  domain: "victor-degano.auth0.com",
  clientID: "5Au9XkgiLXRsTEORGWYLdecQocwOOH8o",
  redirectUri: endpointResolver.getRedirectURI(),
  callbackUrl: endpointResolver.getURL("/callback")
};

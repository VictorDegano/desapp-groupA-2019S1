export const AUTH_CONFIG = {
    domain: 'victor-degano.auth0.com',
    clientID: '5Au9XkgiLXRsTEORGWYLdecQocwOOH8o',
    callbackUrl: getCallbackURL()
};

function getCallbackURL(){
    if( window.location.host === "localhost:3000"
        && window.location.hostname === "localhost"
        && window.location.origin === 'http://localhost:3000'
        && window.location.port === '3000'){
        return 'http://localhost:3000/callback';
    } else{
        return 'https://desapp-grupoa-2019s1-backend.herokuapp.com/callback';
    }
}
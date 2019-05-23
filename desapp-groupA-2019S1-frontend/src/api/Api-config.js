export const API_CONFIG = {
    contentType : 'application/json',
    allowMethods: 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
    allowOrigin: '*',
    endPoint: getEndPoint()
};

function getEndPoint(){
    if( window.location.host === "localhost:3000"
        && window.location.hostname === "localhost"
        && window.location.origin === 'http://localhost:3000'
        && window.location.port === '3000'){
        return 'http://localhost:8080/';
    } else{
        return 'https://desapp-grupoa-2019s1-backend.herokuapp.com/';
    }
}
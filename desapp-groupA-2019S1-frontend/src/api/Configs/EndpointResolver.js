const endpointResolver = {
    isInLocalhost (){
        return window.location.host === "localhost:3000"
            && window.location.hostname === "localhost"
            && window.location.origin === "http://localhost:3000"
            && window.location.port === "3000";
    },

    getURL (resourceDirectory) {
        // console.log('getCallbackURL()');
        return endpointResolver.isInLocalhost() 
            ? "http://localhost:8080"+resourceDirectory 
            : "https://desapp-grupoa-2019s1-backend.herokuapp.com"+resourceDirectory
    },

    getRedirectURI(){
        // console.log('getCallbackURL()');
        return endpointResolver.isInLocalhost() 
            ? "http://localhost:3000/callback" 
            : "https://desapp-grupoa-2019s1-frontend.herokuapp.com/callback"
    }
}
export default endpointResolver;
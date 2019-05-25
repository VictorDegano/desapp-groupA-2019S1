const isInLocalhost = () => (
    window.location.host === "localhost:3000"
    && window.location.hostname === "localhost"
    && window.location.origin === "http://localhost:3000"
    && window.location.port === "3000"
)

const getURL = (resourceDirectory) => (
    // console.log('getCallbackURL()');
    isInLocalhost() 
    ? "http://localhost:8080"+resourceDirectory 
    : "https://desapp-grupoa-2019s1-backend.herokuapp.com"+resourceDirectory
)

export default getURL;
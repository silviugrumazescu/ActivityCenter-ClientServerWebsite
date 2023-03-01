export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));

    if(user && user.jwt) {
        console.log("jwt" + user.jwt);
        return {
            //'Access-Control-Allow-Origin': '*',
            Authorization: 'Bearer ' + user.jwt
        };
    } else {
        return {
            //'Access-Control-Allow-Origin': '*',
        };
    }
}
import axios from 'axios'
import authHeader from './auth-header'

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
    login(user) {
        return axios 
            .post(API_URL + 'signin', {
                headers: authHeader(),
                username: user.username,
                password: user.password
            })
            .then(response => {
                if(response.data) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                    console.log(response.data)
                }
                return response.data;
            })
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            headers: authHeader(),
            firstname: user.firstname,
            lastname: user.lastname,
            username: user.username,
            password: user.password,
            email: user.email
        })
    }
}

export default new AuthService();
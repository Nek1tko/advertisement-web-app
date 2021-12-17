import axios from 'axios';
import store from '../store';
import { userSignIn, userSignOut } from '../actions/authActionCreators';

const API_URL = "http://localhost:8080/auth/";

class AuthService {
    signIn(phoneNumber, password) {
        return axios
            .post(API_URL + "signIn", { phoneNumber, password })
            .then(response => {
                if (response.data.token) {
                    store.dispatch(userSignIn(response.data.phoneNumber, response.data.id, response.data.token));
                }
                return response;
            })
    }

    signOut() {
        store.dispatch(userSignOut());
    }

    register(name, surname, phoneNumber, password) {
        return axios
            .post(API_URL + "signUp", {
                name,
                surname,
                phoneNumber,
                password,
            })
            .then(resposne => resposne);
    }

    getUser() {
        return store.getState().user;
    }
}

export default new AuthService();

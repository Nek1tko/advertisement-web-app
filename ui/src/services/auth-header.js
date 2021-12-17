import AuthService from "./auth.service";

export default function authHeader() {
    const user = AuthService.getUser();

    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        return {};
    }
}

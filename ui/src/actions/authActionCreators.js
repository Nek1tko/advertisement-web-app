import { SIGN_IN, SIGN_OUT } from "./types";

export const userSignIn = (phoneNumber, userId, token) => {
    return {
        type: SIGN_IN,
        payload: {
            phoneNumber: phoneNumber,
            userId: userId,
            token: token
        }
    };
};

export const userSignOut = () => {
    return {
        type: SIGN_OUT,
    };
};

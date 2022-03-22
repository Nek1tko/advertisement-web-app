/**
 * @group unit
 */

import axios from 'axios';
import AuthService from "../auth.service";
import store from '../../store';
import * as authActionCreators from '../../actions/authActionCreators';

jest.mock('axios');

describe('AuthServiceTests', () => {
    beforeEach(() => {
        store.dispatch(authActionCreators.userSignOut());
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('AuthServiceSignInTest', () => {
        const mSignInData = {
            phoneNumber: "phone",
            id: 0,
            token: "token",
        };
        axios.post.mockResolvedValue({ data: mSignInData });

        const mPassword = "password";
        AuthService.signIn(mSignInData.phoneNumber, mPassword)
            .then(() => {
                expect(axios.post).toHaveBeenCalledTimes(1);
                expect(store.getState().user).toEqual({
                    phoneNumber: mSignInData.phoneNumber,
                    userId: mSignInData.id,
                    token: mSignInData.token
                });
            });
    });

    test('AuthServiceSignInNegativeTest', () => {
        const mSignInNegativeData = {
            message: "error"
        };
        axios.post.mockResolvedValue({ data: mSignInNegativeData });

        const mPassword = "password", mPhoneNumber = "phone";
        AuthService.signIn(mPhoneNumber, mPassword)
            .then(() => {
                expect(axios.post).toHaveBeenCalledTimes(1);
                expect(store.getState().user).toEqual(null);
            });
    });

    test('AuthServiceSignOutTest', () => {
        const mUser = {
            phoneNumber: "phone",
            userId: 0,
            token: "token"
        }

        store.dispatch(authActionCreators.userSignIn(mUser.phoneNumber, mUser.userId, mUser.token));
        expect(store.getState().user).toEqual(mUser);

        AuthService.signOut();

        expect(store.getState().user).toEqual(null);
    });

    test('AuthServiceRegisterTest', () => {
        axios.post.mockResolvedValue();

        const mUser = {
            name: "name",
            surname: "surname",
            phoneNumber: "phone",
            password: "password"
        }

        AuthService.register(mUser.name, mUser.surname, mUser.phoneNumber, mUser.password);

        expect(axios.post).toHaveBeenCalledTimes(1);
    });

    test('AuthServiceGetUserTest', () => {
        const mUser = {
            phoneNumber: "phone",
            userId: 0,
            token: "token"
        }
        store.dispatch(authActionCreators.userSignIn(mUser.phoneNumber, mUser.userId, mUser.token));

        expect(AuthService.getUser()).toEqual(mUser);
    });
});

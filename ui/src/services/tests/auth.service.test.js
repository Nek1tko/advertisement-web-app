import axios from 'axios';
import AuthService from "../auth.service";
import store from '../../store';
import * as authActionCreators from '../../actions/authActionCreators';

jest.mock('axios');

describe('AuthServiceTests', () => {
    beforeEach(() => {

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

        const mStoreDispatch = jest.spyOn(store, 'dispatch');
        const mUserSignIn = jest.spyOn(authActionCreators, "userSignIn");

        const mPassword = "password";
        AuthService.signIn(mSignInData.phoneNumber, mPassword)
            .then(() => {
                expect(mStoreDispatch).toHaveBeenCalledTimes(1);
                expect(mUserSignIn).toHaveBeenCalledTimes(1);
                expect(axios.post).toHaveBeenCalledTimes(1);

                expect(store.getState().user).toEqual({
                    phoneNumber: mSignInData.phoneNumber,
                    userId: mSignInData.id,
                    token: mSignInData.token
                });
            });
    });

    test('AuthServiceSignOutTest', () => {
        const mUser = {
            phoneNumber: "phone",
            userId: 0,
            token: "token"
        }

        const mStoreDispatch = jest.spyOn(store, 'dispatch');
        const mUserSignOut = jest.spyOn(authActionCreators, "userSignOut");

        store.dispatch(authActionCreators.userSignIn(mUser.phoneNumber, mUser.userId, mUser.token));
        expect(store.getState().user).toEqual(mUser);

        AuthService.signOut();

        expect(mStoreDispatch).toHaveBeenCalledTimes(2);
        expect(mUserSignOut).toHaveBeenCalledTimes(1);

        expect(store.getState().user).toEqual(null);
    });

    test('AuthServiceRegisterTest', () => {
        const mUser = {
            name: "name",
            surname: "surname",
            phoneNumber: "phone",
            password: "password"
        }

        axios.post.mockResolvedValue();

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

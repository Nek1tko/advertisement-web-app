/**
 * Через route Login вход в систему с помощью AuthService с редиректом на route Home
 * Через route Login неудачный вход в систему с помощью AuthService
 * 
 * @group integration
 */

import axios from "axios";
import { createBrowserHistory } from "history";
import React from "react";
import { mount, shallow } from "enzyme";
import { Router } from "react-router-dom";
import { BrowserRouter } from 'react-router-dom';
import Login from "../routes/Login";
import Home from "../routes/Home";
import App from "../App";
import { act } from "react-dom/test-utils";
import store from '../store';
import { waitFor } from '@testing-library/react';

jest.mock("axios");

const mUserId = 1;
const mPhoneNumber = "mock phone";
const mToken = "mock token";

const mSignInResponse = {
    data: {
        id: mUserId,
        phoneNumber: mPhoneNumber,
        token: mToken
    }
};
const mSignInBadResponse = {
    message: "mock bad request"
};

const errorMessage = "Такого пользователя не существует или пароль не верный";

const whenStable = async () =>
    await act(async () => {
        await new Promise((resolve) => setTimeout(resolve, 0));
    });

describe('LoginTests', () => {
    let wrapper;
    const history = createBrowserHistory();

    beforeEach(() => {
        history.push("/login");
        wrapper = mount(
            <Router history={history}>
                <App />
            </Router>
        );
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('LoginTest', async () => {
        axios.post.mockResolvedValue(mSignInResponse);
        expect(history.location.pathname).toBe("/login");

        const phoneInput = wrapper.find("#phoneInput").first();
        phoneInput.props().onChange("+7 (999) 999-99-99");

        const passwordTextField = wrapper.find("#passwordTextField").first();
        passwordTextField.props().onChange({ target: { value: "qwerty" } });

        const loginButton = wrapper.find("#loginButton").first();
        loginButton.simulate('click');

        await whenStable();

        expect(history.location.pathname).toBe("/");
        expect(store.getState().user).toMatchObject({
            phoneNumber: mPhoneNumber,
            userId: mUserId,
            token: mToken
        });
    });

    test('LoginNegativeTest', async () => {
        axios.post.mockRejectedValue(mSignInBadResponse);
        expect(history.location.pathname).toBe("/login");

        const phoneInput = wrapper.find("#phoneInput").first();
        phoneInput.props().onChange("+7 (999) 999-99-99");

        const passwordTextField = wrapper.find("#passwordTextField").first();
        passwordTextField.props().onChange({ target: { value: "qwerty" } });

        const loginButton = wrapper.find("#loginButton").first();
        loginButton.simulate('click');

        await whenStable();

        expect(history.location.pathname).toBe("/login");
        expect(passwordTextField.props().value).toBe("");
        expect(wrapper.text().includes(errorMessage)).toBe(true);
    });
});

/**
 * После нажатия кнопки Выход в component Header должна исчезнуть кнопка в виде иконки профиля
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

const whenStable = async () =>
    await act(async () => {
        await new Promise((resolve) => setTimeout(resolve, 0));
    });

describe('LogoutTests', () => {
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

    test('LogoutTest', async () => {
        axios.post.mockResolvedValue(mSignInResponse);
        expect(history.location.pathname).toBe("/login");

        let settingsCollapse = wrapper.find('#settingsCollapse').first();
        expect(settingsCollapse.prop("in")).toBe(false);

        const phoneInput = wrapper.find("#phoneInput").first();
        phoneInput.props().onChange("+7 (999) 999-99-99");

        const passwordTextField = wrapper.find("#passwordTextField").first();
        passwordTextField.props().onChange({ target: { value: "qwerty" } });

        const loginButton = wrapper.find("#loginButton").first();
        loginButton.simulate('click');

        await whenStable();
        wrapper.update();

        expect(history.location.pathname).toBe("/");
        expect(store.getState().user).toMatchObject({
            phoneNumber: mPhoneNumber,
            userId: mUserId,
            token: mToken
        });
        settingsCollapse = wrapper.find('#settingsCollapse').first();
        expect(settingsCollapse.prop("in")).toBe(true);

        const logoutButton = wrapper.find('[id="Выйти"]').first();
        logoutButton.simulate('click');

        await whenStable();
        wrapper.update();

        expect(history.location.pathname).toBe("/login");
        expect(store.getState().user).toBeNull();
        settingsCollapse = wrapper.find('#settingsCollapse').first();
        expect(settingsCollapse.prop("in")).toBe(false);
    });
});

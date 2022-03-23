/**
 * Через route Registration регистрация в системе с помощью AuthService с редиректом
 * на route Login и component RegistrationConfirmed
 * 
 * @group integration
 */

import axios from "axios";
import { createBrowserHistory, createMemoryHistory } from "history";
import React from "react";
import { mount } from "enzyme";
import { Router } from "react-router-dom";
import App from "../App";
import { act } from "react-dom/test-utils";
import { MemoryRouter } from "react-router-dom";

jest.mock("axios");
jest.useFakeTimers();

const mPhoneNumber = "+7 (999) 999-99-99";
const mName = "mock name";
const mSurname = "mock surname";
const mPassword = "pass1!word";

const mSignUpResponse = {
    data: {}
};

const whenStable = async () =>
    await act(async () => {
        await new Promise((resolve) => setTimeout(resolve, 0));
    });

describe('RegistrationTests', () => {
    let wrapper;
    const history = createBrowserHistory();

    beforeEach(() => {
        history.push("/login");
        wrapper = mount(
            <MemoryRouter initialEntries={["/login"]}>
                <App />
            </MemoryRouter>
        );
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('RegistrationTest', async () => {
        axios.post.mockResolvedValue(mSignUpResponse);
        expect(wrapper.instance().history.location.pathname).toBe("/login");

        await act(async () => {
            const registrationButton = wrapper.find("#registrationButton").first();
            registrationButton.simulate('click');
            wrapper.update();
        });

        expect(wrapper.instance().history.location.pathname).toBe("/sign-up");
        wrapper.update();

        await act(async () => {
            const nameTextField = await wrapper.find("#nameTextField").first();
            nameTextField.props().onChange({ target: { value: mName } });

            const lastNameTextField = wrapper.find("#lastNameTextField").first();
            lastNameTextField.props().onChange({ target: { value: mSurname } });

            const phoneInput = wrapper.find("#phoneInput").first();
            phoneInput.props().onChange(mPhoneNumber);

            const passwordTextField = wrapper.find("#passwordTextField").first();
            passwordTextField.props().onChange({ target: { value: mPassword } });

            const passwordConfirmationTextField = wrapper.find("#passwordConfirmationTextField").first();
            passwordConfirmationTextField.props().onChange({ target: { value: mPassword } });

            const registrationButton = wrapper.find("#registrationButton").first();
            registrationButton.simulate('click');
            wrapper.update();
        });

        expect(wrapper.instance().history.location.pathname).toBe("/registration-confirmed");
        jest.runAllTimers();

        expect(wrapper.instance().history.location.pathname).toBe("/login");
    });
});
